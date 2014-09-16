/**
 * *****************************************************************************
 *
 * Copyright (C) 2014 Copyright 2014 Victor Sonora Pombo
 *
 * This file is part of UNIDA.
 *
 * UNIDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * UNIDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with UNIDA. If not, see <http://www.gnu.org/licenses/>.
 *
 *****************************************************************************
 */
package com.unida.library.operation.gateway;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.unida.library.UniDAFactory;
import com.unida.library.core.IUniDANetworkFacade;
import com.unida.library.device.Gateway;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.manage.IUniDAManagementFacade;
import com.unida.library.operation.OperationTicket;
import com.unida.library.operation.OperationTicketManager;
import com.unida.library.operation.OperationTypes;
import com.unida.protocol.IUniDACommChannel;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.autonomousbehaviour.UniDAABAddMessage;
import com.unida.protocol.message.autonomousbehaviour.UniDAABChangeScenarioMessage;
import com.unida.protocol.message.autonomousbehaviour.UniDAABRemoveMessage;
import com.unida.protocol.message.autonomousbehaviour.UniDAABRuleVO;
import com.unida.protocol.message.discovery.DiscoverUniDAGatewayDevicesRequestMessage;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Victor Sonora Pombo
 */
public class DefaultGatewayOperationFacade implements IGatewayOperationFacade
{

    private IUniDACommChannel commChannel;

    private IUniDAOntologyCodec ontologyCodec;

    private UniDAFactory unidaFactory;

    private IUniDAManagementFacade unidaManager;

    private OperationTicketManager ticketManager;

    private Queue<DefaultGatewayAccessLayerCallback> gatewayAccessLayerCallbackQueue;

    private int opId = 0;

    public DefaultGatewayOperationFacade(IUniDACommChannel commChannel, IUniDAOntologyCodec ontologyCodec,
            UniDAFactory unidaFactory, IUniDAManagementFacade unidaManager)
    {
        this.ticketManager = new OperationTicketManager();
        this.gatewayAccessLayerCallbackQueue = new LinkedList<>();
        this.commChannel = commChannel;
        this.ontologyCodec = ontologyCodec;
        this.unidaFactory = unidaFactory;
        this.unidaManager = unidaManager;
    }

    @Override
    public void forceAnnounce() throws InternalErrorException
    {

        try
        {
            this.commChannel.broadcastMessage(new DiscoverUniDAGatewayDevicesRequestMessage(this.ontologyCodec));
        } catch (CommunicationException ex)
        {
            throw new InternalErrorException(ex);
        }

    }

    @Override
    public void changeABScenario(String scenarioId) throws InternalErrorException
    {

        try
        {
            this.commChannel.broadcastMessage(new UniDAABChangeScenarioMessage(this.ontologyCodec, scenarioId));
        } catch (CommunicationException ex)
        {
            throw new InternalErrorException(ex);
        }

    }

    @Override
    public void requestABScenarios(IAutonomousBehaviourCallback callback) throws InternalErrorException
    {

        try
        {
            Collection<Gateway> gateways = this.unidaManager.findAllDeviceGateways(0, Integer.MAX_VALUE);
            for (Gateway gateway : gateways)
            {
                
                int nextOpId = getOpId();
                OperationTicket ot = new OperationTicket(nextOpId, OperationTypes.QUERY_SCENARIOS);

                IUniDANetworkFacade unidaNetworkInstance = this.unidaFactory.getDALInstance(gateway);
                DefaultGatewayAccessLayerCallback internalCallback = new DefaultGatewayAccessLayerCallback(ot, gateway, this, callback);
                addCallback(internalCallback);
                unidaNetworkInstance.queryAutonomousBehaviourScenarios(nextOpId, gateway.getId(), internalCallback);

            }
        } catch (CommunicationException ex)
        {
            throw new InternalErrorException(ex);
        }

    }

    @Override
    public void addABRule(UniDAAddress gatewayAddress, UniDAABRuleVO rule) throws InternalErrorException
    {

        try
        {
            this.commChannel.sendMessage(gatewayAddress, new UniDAABAddMessage(gatewayAddress, ontologyCodec, getOpId(), rule));
        } catch (CommunicationException ex)
        {
            throw new InternalErrorException(ex);
        }

    }

    @Override
    public void rmABRule(UniDAAddress gatewayAddress, int ruleId) throws InternalErrorException
    {

        try
        {
            this.commChannel.sendMessage(gatewayAddress, new UniDAABRemoveMessage(gatewayAddress, ontologyCodec, (short) ruleId, getOpId()));
        } catch (CommunicationException ex)
        {
            throw new InternalErrorException(ex);
        }

    }

    @Override
    public OperationTicket requestABRules(UniDAAddress gatewayAddress,
            IAutonomousBehaviourCallback callback) throws InternalErrorException
    {

        int nextOpId = getOpId();

        OperationTicket ot = new OperationTicket(nextOpId, OperationTypes.QUERY_AUTONOMOUS_BEHAVIOUR);

        try
        {
            Gateway gateway = this.unidaManager.findDeviceGatewayById(gatewayAddress.toString());
            IUniDANetworkFacade unidaNetworkInstance = this.unidaFactory.getDALInstance(gateway);
            DefaultGatewayAccessLayerCallback internalCallback = new DefaultGatewayAccessLayerCallback(ot, gateway, this, callback);
            addCallback(internalCallback);
            unidaNetworkInstance.queryAutonomousBehaviourRules(nextOpId, gatewayAddress, internalCallback);
            return ot;

        } catch (InstanceNotFoundException ex)
        {
            throw new InternalErrorException(ex);
        }
    }

    void addCallback(DefaultGatewayAccessLayerCallback callback)
    {
        this.gatewayAccessLayerCallbackQueue.add(callback);
    }

    void removeCallback(DefaultGatewayAccessLayerCallback callback)
    {
        this.gatewayAccessLayerCallbackQueue.remove(callback);
    }

    private synchronized int getOpId()
    {
        return opId++;
    }

}

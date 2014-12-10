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
import com.unida.library.UniDANetworkFactory;
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
import com.unida.protocol.message.autonomousbehaviour.UniDAABRemoveMessage;
import com.unida.protocol.message.autonomousbehaviour.UniDAABRuleVO;
import com.unida.protocol.message.autonomousbehaviour.action.RuleActionEnum;
import com.unida.protocol.message.autonomousbehaviour.trigger.StateChangeTrigger;
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

    private UniDANetworkFactory unidaFactory;

    private IUniDAManagementFacade unidaManager;

    private OperationTicketManager ticketManager;

    private Queue<DefaultGatewayAccessLayerCallback> gatewayAccessLayerCallbackQueue;

    private int opId = 0;

    public DefaultGatewayOperationFacade(
            IUniDACommChannel commChannel,
            IUniDAOntologyCodec ontologyCodec,
            UniDANetworkFactory unidaFactory,
            IUniDAManagementFacade unidaManager)
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
    public void changeABScenario(boolean activate, String scenarioId, IAutonomousBehaviourCallback callback) throws InternalErrorException
    {

        try
        {
            Collection<Gateway> gateways = this.unidaManager.findAllDeviceGateways(0, Integer.MAX_VALUE);
            for (Gateway gateway : gateways)
            {
                int nextOpId = getOpId();
                OperationTicket ot = new OperationTicket(nextOpId, OperationTypes.CHANGE_SCENARIO);
                
                IUniDANetworkFacade unidaNetworkInstance = this.unidaFactory.getUniDANetworkInstance(gateway);
                DefaultGatewayAccessLayerCallback internalCallback = new DefaultGatewayAccessLayerCallback(ot, gateway, this, callback);
                addCallback(internalCallback);
                unidaNetworkInstance.changeScenario(nextOpId, gateway.getId(), activate, scenarioId, internalCallback);
            }

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

                IUniDANetworkFacade unidaNetworkInstance = this.unidaFactory.getUniDANetworkInstance(gateway);
                DefaultGatewayAccessLayerCallback internalCallback = new DefaultGatewayAccessLayerCallback(ot, gateway, this, callback);
                addCallback(internalCallback);
                unidaNetworkInstance.queryAutonomousBehaviourScenarios(nextOpId, gateway.getId(), internalCallback);

            }
        } catch (CommunicationException ex)
        {
            throw new InternalErrorException(ex);
        }

    }

    /**
     *  To add a rule an UniDAABAddMessage is sent to the gateway where
     * the "destination device" is hold.
     *  There is an exception to this, however: for rules with scenario_change
     * action, the UniDAABAddMessage is sent to the "source gateway".
     * @param rule The rule to add
     * @throws InternalErrorException 
     */
    @Override
    public void addABRule(UniDAABRuleVO rule) throws InternalErrorException
    {

        try
        {
            if (rule.getAction().getType().equals(RuleActionEnum.SCENARIO_CHANGE))
            {
                StateChangeTrigger trigger = rule.getStateChangeTrigger();
                this.commChannel.sendMessage(
                        trigger.getTriggerSource().getGatewayId(),
                        new UniDAABAddMessage(trigger.getTriggerSource().getGatewayId(), ontologyCodec, getOpId(), rule));
            } else
            {
                this.commChannel.sendMessage(
                        rule.getAction().getActionDestination().getGatewayId(),
                        new UniDAABAddMessage(rule.getAction().getActionDestination().getGatewayId(), ontologyCodec, getOpId(), rule));
            }
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
            IUniDANetworkFacade unidaNetworkInstance = this.unidaFactory.getUniDANetworkInstance(gateway);
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

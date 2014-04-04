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
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.IUniDACommChannel;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.autonomousbehaviour.UniDAABAddMessage;
import com.unida.protocol.message.autonomousbehaviour.UniDAABRuleVO;
import com.unida.protocol.message.discovery.DiscoverUniDAGatewayDevicesRequestMessage;

/**
 *
 * @author Victor Sonora Pombo
 */
public class DefaultGatewayOperationFacade implements IGatewayOperationFacade
{
    
    private IUniDACommChannel commChannel;
    
    private IUniDAOntologyCodec ontologyCodec;
    
    private int opId = 0;
    
    
    public DefaultGatewayOperationFacade(IUniDACommChannel commChannel, IUniDAOntologyCodec ontologyCodec)
    {
        this.commChannel = commChannel;
        this.ontologyCodec = ontologyCodec;
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
    public void addABRule(UniDAAddress gatewayAddress, UniDAABRuleVO rule) throws InternalErrorException
    {
        
        try
        {
            this.commChannel.sendMessage(gatewayAddress, new UniDAABAddMessage(ontologyCodec, getOpId(), rule));
        } catch (CommunicationException ex)
        {
            throw new InternalErrorException(ex);
        }
        
    }
    
    
    private synchronized int getOpId()
    {
        return opId++;
    }

}

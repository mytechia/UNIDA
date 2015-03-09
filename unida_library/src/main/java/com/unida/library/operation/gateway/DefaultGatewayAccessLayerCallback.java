/*******************************************************************************
 *   
 *   Copyright (C) 2014
 *   Copyright 2014 Victor Sonora Pombo
 * 
 *   This file is part of UNIDA.
 *
 *   UNIDA is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   UNIDA is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with UNIDA.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
package com.unida.library.operation.gateway;


import com.unida.library.device.Gateway;
import com.unida.library.operation.OperationFailures;
import com.unida.library.operation.OperationTicket;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.autonomousbehaviour.UniDAABRuleVO;
import java.util.List;

/**
 * <p><b>Description:</b></p>
 * Implementation of the IAutonomousBehaviourInternalCallback that translates it to a
 * IAutonomousBehaviourCallback in order to export the UniDA facade throught the
 * higher level IGatewayOperationFacade. It uses OperationTicket objects to match
 * operation responses with operation requests.
 *
 *
 * <p><b>Creation date:</b> 
 * 08-04-2014 </p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li> 1 , 08-04-2014 - Initial release</li>
 * </ul>
 *
 * @author Victor Sonora Pombo
 * @version 1
 */
public class DefaultGatewayAccessLayerCallback implements IAutonomousBehaviourInternalCallback 
{
    
    private OperationTicket ticket;
    private Gateway gateway;
    private long requestTime;
    private DefaultGatewayOperationFacade gatewayOpFacade;
    private IAutonomousBehaviourCallback operationCallback;
    
    
    public DefaultGatewayAccessLayerCallback(OperationTicket ticket, Gateway gateway, 
            DefaultGatewayOperationFacade gatewayOpFacade, IAutonomousBehaviourCallback operationCallback)
    {
        this.ticket = ticket;
        this.gateway = gateway;
        this.gatewayOpFacade = gatewayOpFacade;
        this.operationCallback = operationCallback;
        this.requestTime = System.currentTimeMillis();
    }

    
    public OperationTicket getTicket()
    {
        return ticket;
    }

    public Gateway getGateway()
    {
        return gateway;
    }

    public IAutonomousBehaviourCallback getOperationCallback()
    {
        return operationCallback;
    }
    
    public long getRequestTime()
    {
        return this.requestTime;
    }
    
    
    @Override
    public void notifyGatewayAutonomousBehaviourRules(long opId, UniDAAddress gatewayAddress, List<UniDAABRuleVO> rules)
    {
        if (isThisOperation(opId, gatewayAddress))
        {
            if (this.operationCallback != null)
            {
                this.operationCallback.notifyGatewayAutonomousBehaviourRules(this.ticket, this.gateway, rules);
            }
            finishCallback();
        }
    }
    
    
    @Override
    public void notifyGatewayAutonomousBehaviourScenarios(long opId, UniDAAddress gatewayAddress, List<String> scenarioIDs)
    {
        if (isThisOperation(opId, gatewayAddress))
        {
            if (this.operationCallback != null)
            {
                this.operationCallback.notifyAutonomousBehaviourScenarios(this.ticket, scenarioIDs);
            }
            finishCallback();
        }
    }
    
    
    private void finishCallback()
    {
        this.gatewayOpFacade.removeCallback(this);
    }
    
    private boolean isThisOperation(long opId, UniDAAddress gatewayAddress)
    {
        return (ticket.getId() == opId) && gatewayAddress.equals(this.gateway.getId());
    }

    @Override
    public void notifyABExecution(long opId, UniDAAddress gatewayAddress)
    {
        if (isThisOperation(opId, gatewayAddress))
        {
            if (this.operationCallback != null)
            {
                this.operationCallback.notifyExecution(this.ticket);
            }
            finishCallback();
        }
    }

    @Override
    public void notifyABFailure(long opId, UniDAAddress gatewayAddress)
    {
        if (isThisOperation(opId, gatewayAddress))
        {
            if (this.operationCallback != null)
            {
                this.operationCallback.notifyFailure(this.ticket, OperationFailures.OPERATION_ERROR);
            }
            finishCallback();
        }
    }

    @Override
    public void notifyExpiration(long opId)
    {
        if (ticket.getId() == opId)
        {
            finishCallback();

            if (this.operationCallback != null)
            {
                this.operationCallback.notifyFailure(
                        this.ticket,
                        OperationFailures.RESPONSE_EXPIRATION);
            }
        }
    }
    

}

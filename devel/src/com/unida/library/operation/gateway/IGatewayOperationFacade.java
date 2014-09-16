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
import com.unida.library.operation.OperationTicket;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.autonomousbehaviour.UniDAABRuleVO;

/**
 * <p>
 * <b>Description:</b>
 * Interface to the operations that act on UniDA gateways
 * </p>
 * 
 * @author Victor Sonora Pombo
 */
public interface IGatewayOperationFacade
{
        
    public void forceAnnounce() throws InternalErrorException;
    
    public void changeABScenario(String scenarioId) throws InternalErrorException;
        
    public void addABRule(UniDAAddress gatewayAddress, UniDAABRuleVO rule) throws InternalErrorException;
    
    public void rmABRule(UniDAAddress gatewayAddress, int ruleId) throws InternalErrorException;
    
    public OperationTicket requestABRules(UniDAAddress gatewayAddress, IAutonomousBehaviourCallback callback) throws InternalErrorException;
    
    public void requestABScenarios(IAutonomousBehaviourCallback callback) throws InternalErrorException;
        
}

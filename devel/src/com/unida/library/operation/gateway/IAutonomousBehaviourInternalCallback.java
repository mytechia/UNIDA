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

import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.autonomousbehaviour.UniDAABRuleVO;
import java.util.List;


/**
 * <p><b>Description:</b>
 * Callback to internally manage the reception of information about
 * autonomous behaviour operations
 * </p>
 *
 * <p><b>Creation date:</b> 
 * 08-04-2014 </p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li> 1 , 08-04-2014 -> Initial release</li>
 * </ul>
 * </p>
 * @author Victor Sonora Pombo
 * @version 1
 */
public interface IAutonomousBehaviourInternalCallback {
    
    
    public void notifyGatewayAutonomousBehaviourRules(long opId, UniDAAddress gatewayAddress, List<UniDAABRuleVO> rules);
    

}

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

package com.unida.library.manage;

import com.unida.library.device.Gateway;

/**
 * <p>
 * <b>Description:</b>
 *  A callback that handles notifications of detected UniDA gateways.
 * </p>
 *
 * <p><b>Creation date:</b> 
 * 05-12-2014 </p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li> 1 , 05-12-2014 -> Initial release</li>
 * </ul>
 * </p>
 * @author Victor Sonora Pombo
 * @version 1
 */
public interface IGatewayDiscoveryListener 
{
    
    public void notifyGatewayDiscovered(Gateway gateway);
    
    public void notifyGatewayLost(Gateway gateway);

}

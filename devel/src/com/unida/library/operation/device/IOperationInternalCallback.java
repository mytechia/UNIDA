/*******************************************************************************
 *   
 *   Copyright (C) 2009-2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2009-2013 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2012-2013 Victor Sonora <victor@vsonora.com>
 *   Copyright (C) 2009-2013 Alejandro Paz <alejandropl@gmail.com>
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

package com.unida.library.operation.device;

import com.unida.library.device.DeviceID;
import com.unida.library.device.ontology.state.DeviceStateValue;


/**
 * <p><b>
 * This callback must be implemented to receive responses from the specific
 * UniDA implementations.
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 15-01-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 15-01-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public interface IOperationInternalCallback
{


    void notifyDeviceState(long opId, DeviceID deviceId, String stateId, DeviceStateValue stateValue);
    
    
    void notifyWriteDeviceState(long opId, DeviceID deviceId);


    void notifyDeviceStates(long opId, DeviceID deviceId, String [] stateIds, DeviceStateValue [] stateValues);


    void notifyCommandExecution(long odId, DeviceID deviceId, String functionalityId, String commandId);

    
    void notifyDeviceFailure(long opId, DeviceID deviceId, String failureId);
    

    void notifyExpiration(long opId);


}

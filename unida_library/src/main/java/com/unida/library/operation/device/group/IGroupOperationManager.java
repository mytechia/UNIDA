/*******************************************************************************
 *   
 *   Copyright (C) 2010 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
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

package com.unida.library.operation.device.group;


import com.mytechia.commons.framework.exception.InternalErrorException;
import com.unida.library.device.ontology.metadata.ControlCommandMetadata;
import com.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.unida.library.device.DeviceGroup;
import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.state.DeviceStateValue;
import com.unida.library.operation.device.IDeviceOperationCallback;
import com.unida.library.operation.OperationTicket;

/**
 * <p><b>
 * Manages the execution of commands and queries over groups of devices.
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 18-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 18-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public interface IGroupOperationManager
{

    	
	public OperationTicket sendCommand(OperationTicket ot, DeviceGroup group, ControlFunctionalityMetadata func,
                ControlCommandMetadata cmd, String[] params, IDeviceOperationCallback callback)
                throws InternalErrorException;

        

        public OperationTicket asyncQueryDeviceState(OperationTicket ticket, IDevice dev, DeviceStateMetadata state, IDeviceOperationCallback callback)
                throws InternalErrorException;
		


        public OperationTicket asyncQueryDeviceStates(OperationTicket ticket, IDevice dev, IDeviceOperationCallback callback)
                throws InternalErrorException;

        
        public OperationTicket asyncWriteDeviceState(OperationTicket ticket, IDevice dev, 
                DeviceStateMetadata state, DeviceStateValue stateValue, IDeviceOperationCallback callback)
                throws InternalErrorException;
        
        public OperationTicket asyncModifyDeviceInfo(OperationTicket ot, IDevice dev, String name, String description, String location)
                throws InternalErrorException;
	
}

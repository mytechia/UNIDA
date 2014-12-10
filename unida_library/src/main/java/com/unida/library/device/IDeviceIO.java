/*******************************************************************************
 *   
 *   Copyright (C) 2010,2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2013 Victor Sonora <victor@vsonora.com>
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

package com.unida.library.device;

import com.unida.protocol.UniDAAddress;
import com.unida.library.device.ontology.metadata.DeviceStateMetadata;
import java.util.Collection;


/**
 * <p><b>
 * This interfaces provides a view of a device as a hardware interface
 * to a device gateway/hub. For example, a digital input/output of a
 * DOMO gateway, or a connection for an especific device (for example an
 * alarm or an oven) in a control gateway.
 *
 * It is used to represent the manual connection of device to general
 * inputs/outputs availabe in the gateways. Those i/o doesn't have capabilities
 * to detect the connection of devices and notifie that to the system, so the
 * device is itself is the i/o with some metadata that uniquely represents the
 * connected device (accesible through the IDevice interface).
 *
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 25-02-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>2 - 7-feb-2013 The interface have been changed to match the new device IO style</li>
 * <li>1 - 25-02-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public interface IDeviceIO
{

    public short getId();


    public UniDAAddress getGatewayId();


    public PhysicalDevice getDevice();
    
    
    public void setDevice(PhysicalDevice device);


    public Collection<DeviceStateMetadata> getCompatibleStates();
    
    
    public Collection<DeviceStateMetadata> getCompatibleWritableStates();
    

}

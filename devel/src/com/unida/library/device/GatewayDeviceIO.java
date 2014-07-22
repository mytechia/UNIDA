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
import java.util.ArrayList;
import java.util.Collection;


/**
 * <p><b>Description:</b></br>
 * Represents a physical connection available in a gateway to connect 
 * physical devices to the UniDA network.
 * 
 * It can support different states, so different devices can be connected
 * to a device IO.
 * </p>
 *
 * <p><b>Creation date:</b> 7-feb-2013</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 7-feb-2013<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class GatewayDeviceIO implements IDeviceIO 
{

    private short id;
    private UniDAAddress gatewayId;
    private PhysicalDevice device; //it can be null if the IO is disconnected
    
    private ArrayList<DeviceStateMetadata> compatibleStates;


    public GatewayDeviceIO(short id, UniDAAddress gatewayId, PhysicalDevice device, ArrayList<DeviceStateMetadata> compatibleStates)
    {
        this.id = id;
        this.gatewayId = gatewayId;
        this.device = device;
        this.compatibleStates = compatibleStates;
    }


    @Override
    public short getId()
    {
        return id;
    }


    @Override
    public UniDAAddress getGatewayId()
    {
        return gatewayId;
    }


    @Override
    public PhysicalDevice getDevice()
    {
        return device;
    }
    
    
    @Override
    public void setDevice(PhysicalDevice device)
    {
        this.device = device;
    }


    @Override
    public Collection<DeviceStateMetadata> getCompatibleStates()
    {
        return new ArrayList<>(compatibleStates);
    }
    
    
    @Override
    public Collection<DeviceStateMetadata> getCompatibleWritableStates()
    {
        Collection<DeviceStateMetadata> writableStates = new ArrayList<>();
        for (DeviceStateMetadata compatibleState : getCompatibleStates())
        {
            if (compatibleState.isIsWritable()) writableStates.add(compatibleState);
        }
        return writableStates;
    }


}

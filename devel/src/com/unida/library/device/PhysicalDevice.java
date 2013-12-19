/*******************************************************************************
 *   
 *   Copyright (C) 2009,2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2009 Gervasio Varela <gervarela@picandocodigo.com>
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
import com.unida.library.device.ontology.DeviceClassMetadata;
import com.unida.library.device.state.OperationalState;
import com.unida.library.location.Location;
import java.util.ArrayList;
import java.util.Collection;


/**
 * <p><b>Description:</b></br>
 * Virtual representation of a hardware device (light switch, presence sensor,
 * etc.).
 *
 * A device can have multiple types of states.
 *
 * </p>
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>2 - 7-feb-2013<\br> Added support for the new style of device IOs<\li>
 * <li>1 - 28-dic-2009<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class PhysicalDevice extends Device
{

    private boolean configured;

    /** Device model according to the manufacturer of the device*/
    private String model;

    /** Manufacturer of the device */
    private String manufacturer;

    private boolean automatic;

    private ArrayList<IDeviceIO> connectedIOs;


    
    public PhysicalDevice(
            Long codId, UniDAAddress gatewayId, short deviceId, Location location, boolean configured, OperationalState operationalState,
            boolean visible, String description, DeviceClassMetadata deviceClass,
            String model, String manufacturer, boolean automatic)
    {
        super(codId, gatewayId, deviceId, location, operationalState, visible, description, deviceClass);
        this.configured = configured;
        this.model = model;
        this.manufacturer = manufacturer;
        this.automatic = automatic;
        connectedIOs = new ArrayList<IDeviceIO>(1);
    }
    
    
    public void connectToIO(IDeviceIO io) //throws UnsupportedDeviceStateErrorException
    {
//        if (io.getCompatibleStates().contains(state)) {
            io.setDevice(this);
            this.connectedIOs.add(io);
//        }
//        else {
//            throw new UnsupportedDeviceStateErrorException(io, state);
//        }
    }
    
    
    public void disconnectFromIO(IDeviceIO io)
    {
        this.connectedIOs.remove(io);
        io.setDevice(null);
    }
    
    
    public Collection<IDeviceIO> getConnectedIOs()
    {
        return new ArrayList<IDeviceIO>(connectedIOs);
    }


    public boolean isConfigured()
    {
        return this.configured;
    }


    @Override
    public String getManufacturer()
    {
        return manufacturer;
    }


    @Override
    public String getModel()
    {
        return model;
    }


    @Override
    public boolean isGroup()
    {
        return false;
    }


    public boolean isAutomatic()
    {
        return this.automatic;
    }


    @Override
    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }


    @Override
    public void setModel(String model)
    {
        this.model = model;
    }


    @Override
    public boolean isConnected()
    {
        return this.getId().getGatewayId() != null;
    }
        
        
    @Override
    public Object clone()
    {
        PhysicalDevice d = new PhysicalDevice(
                getCodId(),
                getId().getGatewayId(),
                getId().getDeviceId(),
                getLocation(),
                isConfigured(),
                getOperationalState(),
                isEnabled(),
                getDescription(),
                getDeviceClass(),
                getModel(),
                getManufacturer(),
                isAutomatic());

        return d;

    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhysicalDevice other = (PhysicalDevice) obj;
        if (this.getId().equals(other.getId())) {
            return true;
        }      
        return false;
    }

    
    
}

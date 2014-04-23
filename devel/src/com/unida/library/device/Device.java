/*******************************************************************************
 *   
 *   Copyright (C) 2010,2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010,2013 Gervasio Varela <gervarela@picandocodigo.com>
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
import com.unida.library.device.ontology.metadata.DeviceClassMetadata;
import com.unida.library.device.state.OperationalState;
import com.unida.library.location.Location;
import java.io.Serializable;

/**
 * <p><b>Description:</b></br>
 * Virtual representation of a device in the UniDA library.
 *
 * A device can have multiple types of states.
 *
 * And is connected to the UniDA network through some device IOs of a gateway
 *
 * </p>
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>2 - 5-feb-2013<\br> From now on a device is connected to a gateway through multiple device IOs</li>
 * <li>1 - 28-dic-2009<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 2
 */
public abstract class Device implements Cloneable, Serializable, IDevice
{

    private Long codId; //the device id in the database

    /** Device's state of operation */
    private OperationalState operationalState;

    //device
    private DeviceID id;

    private Location location;

    private boolean enabled;

    private String description;

    private DeviceClassMetadata deviceClass;



    /** Creates a new device object
     *
     * @param id Device's id
     * @param location  Device's location
     * @param operationalState Current operation state of the device
     * @param enabled Whether the device is visible outside the library or not
     * @param description Human readable description of the device
     * @param deviceClass Type of the device
     * @param hub
     */
    public Device(
            Long codId, UniDAAddress gatewayId, short deviceId, Location location,
            OperationalState operationalState, boolean enabled,
            String description, DeviceClassMetadata deviceClass)
    {
        this(codId, location, operationalState, enabled, description, deviceClass);
        setId(gatewayId, deviceId);
    }


    public Device(
            Long codId, Location location,
            OperationalState operationalState, boolean enabled,
            String description, DeviceClassMetadata deviceClass)
    {
        this.codId = codId;
        this.id = null;
        this.location = location;
        this.operationalState = operationalState;
        this.enabled = enabled;
        this.description = description;
        this.deviceClass = deviceClass;
    }


    private void setId(UniDAAddress gwId, short devId)
    {
        this.id = new DeviceID(gwId, devId);
    }


    @Override
    public String getDescription()
    {
        return description;
    }


    @Override
    public DeviceClassMetadata getDeviceClass()
    {
        return deviceClass;
    }


    @Override
    public Long getCodId()
    {
        return this.codId;
    }


    @Override
    public DeviceID getId()
    {
        return id;
    }


    @Override
    public Location getLocation()
    {
        return location;
    }


    @Override
    public OperationalState getOperationalState()
    {
        return operationalState;
    }


    @Override
    public void setOperationalState(OperationalState state)
    {
        this.operationalState = state;
    }


    @Override
    public boolean isEnabled()
    {
        return enabled;
    }
    


    @Override
    public void setDescription(String desc)
    {
        this.description = new String(desc);
    }


    @Override
    public void setLocation(Location loc)
    {
        this.location = loc;
    }


    public void setDeviceClass(DeviceClassMetadata dcm)
    {
        this.deviceClass = dcm;
    }


    public IDevice getDevice()
    {
        //if the device is enabled, it is connected to a device IO in a gateway
        if (this.isEnabled()) {
            return this;
        }
        else {
            return null;
        }
    }



    @Override
    public abstract boolean isGroup();



    @Override
    public abstract Object clone();



    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Device other = (Device) obj;
        if ((this.codId == null) ? (other.codId != null) : !this.codId.equals(other.codId)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 17 * hash + (this.codId != null ? this.codId.hashCode() : 0);
        return hash;
    }


}

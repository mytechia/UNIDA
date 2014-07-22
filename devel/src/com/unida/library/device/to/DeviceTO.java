/*******************************************************************************
 *   
 *   Copyright (C) 2010,2012 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2012 Victor Sonora <victor@vsonora.com>
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


package com.unida.library.device.to;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.unida.library.device.state.OperationalStatesEnum;
import com.unida.library.location.Location;


/**
 * <p>Representation of a device using basic data types</p>
 *
 * <p><b>Creation date:</b> 08-ene-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 08-ene-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class DeviceTO
{

    private Long codId;

    private Short id;    

    private Location location;

    private boolean enabled;

    private boolean group;

    private boolean configured;

    private String description;

    private boolean automatic;

    private String deviceClass;

    private String model;

    private String manufacturer;
    
    private String gatewayId;

    private int operationalState;

    private Date operationalStateLastChange;
    
    private ArrayList<GatewayDeviceIOTO> connectedIOs;


    
    /** Basic constructor for devices.
     *
     * @param codId CodId subrogate of the device in the database
     * @param id Id of the real device in the network of devices
     * @param ioId Id of the gateway IO the device is connected to
     */
    public DeviceTO(Long codId, Short id, String gatewayId)
    {
        this(codId, id, gatewayId, null, false, false, false, false, null, null, null, null, OperationalStatesEnum.UNKNOWN.getStateValue(), new Date(), new ArrayList<GatewayDeviceIOTO>(0));
    }


    /** Creates a new device to be inserted in the database (it doesn't have
     * a cod_id already).
     */
    public DeviceTO(Short id, String gwId, Location location,
            boolean enabled, boolean group, boolean configured, boolean automatic, 
            String description, String model, String manufacturer,
            String deviceClass, int operationalState, Date operationalStateLastChange,
            Collection<GatewayDeviceIOTO> connectedIOs)
    {
        this.id = id;
        this.location = location;
        this.enabled = enabled;
        this.group = group;
        this.configured = configured;
        this.description = description;
        this.deviceClass = deviceClass;
        this.automatic = automatic;
        this.model = model;
        this.manufacturer = manufacturer;
        this.gatewayId = gwId;
        this.operationalState = operationalState;
        this.operationalStateLastChange = operationalStateLastChange;
        this.connectedIOs = new ArrayList<GatewayDeviceIOTO>(connectedIOs);
    }



    /** Creates a new device from the database (with its cod_id).
     */
    public DeviceTO(Long codId, Short id, String gwId, Location location,
            boolean enabled, boolean group, boolean configured, boolean automatic, 
            String description, String model, String manufacturer,
            String deviceClass, int operationalState, Date operationalStateLastChange,
            Collection<GatewayDeviceIOTO> connectedIOs)
    {
        this(id, gwId, location, enabled, group, configured, automatic, description, model,
                manufacturer, deviceClass, operationalState, operationalStateLastChange, connectedIOs);
        this.codId = codId;
    }


    public Collection<GatewayDeviceIOTO> getConnectedIOs()
    {
        return connectedIOs;
    }


    public String getGatewayId()
    {
        return gatewayId;
    }


    public Date getOperationalStateLastChange()
    {
        return operationalStateLastChange;
    }


    public boolean isAutomatic()
    {
        return this.automatic;
    }


    public Long getCodId()
    {
        return codId;
    }


    public String getDescription()
    {
        return description;
    }


    public String getDeviceClass()
    {
        return deviceClass;
    }


    public boolean isGroup()
    {
        return group;
    }


    public boolean isConfigured()
    {
        return configured;
    }


    public Short getId()
    {
        return id;
    }


    public String getGatewayID()
    {
        return this.gatewayId;
    }


    public Location getLocation()
    {
        return location;
    }


    public String getManufacturer()
    {
        return manufacturer;
    }


    public String getModel()
    {
        return model;
    }


    public int getOperationalState()
    {
        return this.operationalState;
    }


    public Date getOperationalStateLastChangeTime()
    {
        return this.operationalStateLastChange;
    }


    public boolean isEnabled()
    {
        return enabled;
    }


    public void setConfigured(boolean configured)
    {
        this.configured = configured;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public void setDeviceClass(String deviceClass)
    {
        this.deviceClass = deviceClass;
    }


    public void setGroup(boolean group)
    {
        this.group = group;
    }


    public void setId(Short id)
    {
        this.id = id;
    }


    public void setGatewayID(String gatewayId)
    {
        this.gatewayId = gatewayId;
    }


    public void setLocation(Location location)
    {
        this.location = location;
    }


    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }


    public void setModel(String model)
    {
        this.model = model;
    }


    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }


    public void setAutomatic(boolean automatic)
    {
        this.automatic = automatic;
    }


    public void setOperationalState(int state)
    {
        this.operationalState = state;
    }


    public void setOperationalStateLastChange(Date date)
    {
        this.operationalStateLastChange = date;
    }



    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeviceTO other = (DeviceTO) obj;
        if (this.codId != other.codId && (this.codId == null || !this.codId.equals(other.codId))) {
            return false;
        }
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if (this.location != other.location && (this.location == null || !this.location.equals(other.location))) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        if (this.group != other.group) {
            return false;
        }
        if (this.configured != other.configured) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if ((this.deviceClass == null) ? (other.deviceClass != null) : !this.deviceClass.equals(other.deviceClass)) {
            return false;
        }
        if ((this.model == null) ? (other.model != null) : !this.model.equals(other.model)) {
            return false;
        }
        if ((this.manufacturer == null) ? (other.manufacturer != null) : !this.manufacturer.equals(other.manufacturer)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 53 * hash + (this.codId != null ? this.codId.hashCode() : 0);
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.location != null ? this.location.hashCode() : 0);
        hash = 53 * hash + (this.enabled ? 1 : 0);
        hash = 53 * hash + (this.group ? 1 : 0);
        hash = 53 * hash + (this.configured ? 1 : 0);
        hash = 53 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 53 * hash + (this.deviceClass != null ? this.deviceClass.hashCode() : 0);
        hash = 53 * hash + (this.model != null ? this.model.hashCode() : 0);
        hash = 53 * hash + (this.manufacturer != null ? this.manufacturer.hashCode() : 0);
        return hash;
    }
    

}

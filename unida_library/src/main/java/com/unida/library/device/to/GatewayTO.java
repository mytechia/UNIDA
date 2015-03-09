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

package com.unida.library.device.to;

import com.unida.library.location.Location;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * <p><b>
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 08-ene-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 08-ene-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class GatewayTO
{

    private Long codId;
    private String id;
    private String model;
    private String manufacturer;
    private String name;
    private boolean enabled;
    private Location location;
    private String description;
    private String gwClass;
    private Long installationId;
    private String address;
    private int operationalState;
    private Date operationalStateLastChange;
    
    private ArrayList<GatewayDeviceIOTO> ioList;
    private ArrayList<DeviceTO> deviceList;
    


    /** Creates a new device gateway to be inserted in the database.
     */
    public GatewayTO(
            String id, String model, String manufacturer, boolean enabled, String name, String description, Location location,
            String gwType, Long installationId, String address, int operationalState, Date operationalStateLastChange,
            Collection<GatewayDeviceIOTO> ioList, Collection<DeviceTO> deviceList)
    {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.location = location;
        this.gwClass = gwType;
        this.installationId = installationId;
        this.address = address;
        this.operationalState = operationalState;
        this.operationalStateLastChange = operationalStateLastChange;
        this.ioList = new ArrayList<>(ioList);
        this.deviceList = new ArrayList<>(deviceList);
    }


    /** Creates a new device gateway from information of the database.
     */
    public GatewayTO(
            Long codId, String id, String model, String manufacturer, boolean enabled, String name, String description, Location location,
            String gwType, Long installationId, String address, int operationalState, Date operationalStateLastChange,
            Collection<GatewayDeviceIOTO> ioList, Collection<DeviceTO> deviceList)
    {
        this.codId = codId;
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.location = location;
        this.gwClass = gwType;
        this.installationId = installationId;
        this.address = address;
        this.operationalState = operationalState;
        this.operationalStateLastChange = operationalStateLastChange;
        this.ioList = new ArrayList<>(ioList);
        this.deviceList = new ArrayList<>(deviceList);

    }


    public Collection<GatewayDeviceIOTO> getIoList()
    {
        return new ArrayList<>(ioList);
    }
    
    public Collection<DeviceTO> getDeviceList()
    {
        return new ArrayList<>(deviceList);
    }
    
    public Long getCodId()
    {
        return codId;
    }   

    
    public String getGwClass()
    {
        return gwClass;
    }


    public String getId()
    {
        return id;
    }


    public Location getLocation()
    {
        return location;
    }


    public String getManufacturer()
    {
        return manufacturer;
    }


    public boolean isEnabled()
    {
        return this.enabled;
    }


    public String getModel()
    {
        return model;
    }

    public void setGwType(String gwType)
    {
        this.gwClass = gwType;
    }


    public void setId(String id)
    {
        this.id = id;
    }


    public void setLocation(Location location)
    {
        this.location = location;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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


    public Long getInstallationId()
    {
        return this.installationId;
    }


    public void setInstallationId(Long id)
    {
        this.installationId = id;
    }


    public String getAddress()
    {
        return this.address;
    }


    public void setAdress(String addr)
    {
        this.address = addr;
    }


    public int getOperationalState()
    {
        return this.operationalState;
    }


    public void setOperationalState(int state)
    {
        this.operationalState = state;
    }


    public Date getOperationalStateLastChange()
    {
        return this.operationalStateLastChange;
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
        final GatewayTO other = (GatewayTO) obj;
        if (this.codId != other.codId && (this.codId == null || !this.codId.equals(other.codId))) {
            return false;
        }
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.model == null) ? (other.model != null) : !this.model.equals(other.model)) {
            return false;
        }
        if ((this.manufacturer == null) ? (other.manufacturer != null) : !this.manufacturer.equals(other.manufacturer)) {
            return false;
        }
        if (this.location != other.location && (this.location == null || !this.location.equals(other.location))) {
            return false;
        }
        if ((this.gwClass == null) ? (other.gwClass != null) : !this.gwClass.equals(other.gwClass)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString()
    {
        return this.id+" | "+this.gwClass+" | "+this.address;
    }



}

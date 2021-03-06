/**
 * *****************************************************************************
 *
 * Copyright (C) 2010,2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
 * Copyright (C) 2013 Victor Sonora <victor@vsonora.com>
 *
 * This file is part of UNIDA.
 *
 * UNIDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * UNIDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with UNIDA. If not, see <http://www.gnu.org/licenses/>.
 *
 *****************************************************************************
 */
package com.hi3project.unida.library.device;

import com.hi3project.unida.protocol.UniDAAddress;
import com.hi3project.unida.library.device.ontology.metadata.GatewayClassMetadata;
import com.hi3project.unida.library.device.state.OperationalState;
import com.hi3project.unida.library.location.Location;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * <b>Description:</b> A gateway acts as a bridge between the real physical
 * devices and the abstract UniDA network.
 *
 * It can be a physical device that is connected to a classic domotic network
 * and translates the concepts of that network to the UniDA concepts, it can be
 * a native field node such as the DOMO, that natively understand UniDA concepts
 * or even it can be a piece of software that acts a driver to access some
 * hardware device directly connected to a computer (or a virtual software
 * device).
 *
 *
 *
 * <p>
 * <b>Creation date:</b> 28-dic-2009</p>
 *
 * <p>
 * <b>Changelog:</b>  </p>
 * <ul> <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class Gateway implements Cloneable, Serializable
{

    private Long codId;
    private UniDAAddress id;
    private String model;
    private String manufacturer;
    private String description;
    private String name;
    private boolean enabled; //whether the gateway can be used by high-level layers or not -> user configurable
    private Location location;
    private GatewayClassMetadata type;
    private Long installationId;
    private OperationalState operationalState;
    private ArrayList<IDevice> deviceList;
    private ArrayList<GatewayDeviceIO> ioList;

    public Gateway(
            Long codId, UniDAAddress id, String model, String manufacturer, boolean enabled,
            String name, String description, Location location, GatewayClassMetadata type, Long installationId,
            OperationalState operationalState, Collection<GatewayDeviceIO> ioList, Collection<IDevice> deviceList)
    {
        this.codId = codId;
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.enabled = enabled;
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        this.installationId = installationId;
        this.operationalState = operationalState;
        this.ioList = new ArrayList<>(ioList);
        this.deviceList = new ArrayList(deviceList);
    }

    public Collection<GatewayDeviceIO> getIoList()
    {
        return new ArrayList<>(ioList);
    }

    public Long getCodId()
    {
        return this.codId;
    }

    public UniDAAddress getId()
    {
        return id;
    }

    public Location getLocation()
    {
        return location;
    }
    
    public void setLocation(Location location)
    {
        this.location = location;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public String getModel()
    {
        return model;
    }
    
    public String getName()
    {
        return this.name;
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
    
    public GatewayClassMetadata getType()
    {
        return type;
    }

    public Long getInstallationId()
    {
        return this.installationId;
    }

    public byte[] getAddress()
    {
        return this.id.getAddress();
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

    public OperationalState getOperationalState()
    {
        return this.operationalState;
    }

    public void setOperationalState(OperationalState state)
    {
        this.operationalState = state;
    }

    public Collection<IDevice> getDevices()
    {
        return new ArrayList<>(this.deviceList);
    }

    public void setDevices(Collection<IDevice> deviceList)
    {
        this.deviceList.clear();
        this.deviceList.addAll(deviceList);
    }
}

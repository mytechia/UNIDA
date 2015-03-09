/*******************************************************************************
 *   
 *   Copyright (C) 2009 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2009 Gervasio Varela <gervarela@picandocodigo.com>
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


/**
 * <p><b>Description:</b></p>
 * Abstracts a group of devices as one device.
 *
 * Operation can be applied to multiple devices by grouping them in a group
 * and apply operation to that group.
 *
 *
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class DeviceGroup extends Device
{

    public DeviceGroup(
            Long codId, short id, Location location, OperationalState operationalState,
            boolean visible, String name, String description,
            DeviceClassMetadata deviceClass)
    {
        super(codId, UniDAAddress.GROUPS_GATEWAY_ID, id, location, operationalState, visible, name, description, deviceClass);
        //this.members = new ArrayList<Device>();
    }


//    public Collection<Device> getMembers()
//    {
//            return new ArrayList<Device>(this.members);
//    }
//
//
//    public void addMember(Device member)
//    {
//        if (!this.members.contains(member)) {
//            this.members.add(member);
//        }
//    }
//
//
//    public void removeMember(Device member)
//    {
//            this.members.remove(member);
//    }


//    public int getMemberCount()
//    {
//        return this.members.size();
//    }


    @Override
    public boolean isGroup()
    {
        return true;
    }


    @Override
    public boolean isConnected()
    {
        return true;
    }


    @Override
    public String getModel()
    {
        return "UniDA DeviceGroup";
    }


    @Override
    public void setModel(String model)
    {
        
    }


    @Override
    public String getManufacturer()
    {
        return "GII-UDC";
    }


    @Override
    public void setManufacturer(String manufacturer)
    {
        
    }


    public Long getDeviceGatewayId()
    {
        return null;
    }


    @Override
    public Object clone()
    {
        DeviceGroup dg = new DeviceGroup(
                getCodId(),
                getId().getDeviceId(),
                getLocation(),
                getOperationalState(),
                isEnabled(),
                getName(),
                getDescription(),
                getDeviceClass());

        return dg;
    }
    

}

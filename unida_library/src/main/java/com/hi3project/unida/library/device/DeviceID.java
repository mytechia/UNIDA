/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2013 Gervasio Varela <gervarela@picandocodigo.com>
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

package com.hi3project.unida.library.device;

import com.hi3project.unida.protocol.UniDAAddress;
import com.mytechia.commons.util.conversion.EndianConversor;


/**
 * <p><b>Description:</b></p>
 * The ID of a device is composed of the ID of its gateway and a subrogate
 * automatically generated by the gateway
 *
 *
 *
 * <p><b>Creation date:</b> 5-feb-2013</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 5-feb-2013 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela Fernandez - Integrated Group for Engineering Research
 * @version 1
 */
public class DeviceID 
{
    
    public static final int ID_BYTES_LEN = UniDAAddress.ID_BYTES_LEN + EndianConversor.SHORT_SIZE_BYTES;
    
    private final UniDAAddress gatewayId;
    
    private final short deviceId;


    public DeviceID(UniDAAddress gatewayId, short deviceId)
    {
        this.gatewayId = gatewayId;
        this.deviceId = deviceId;
    }


    public UniDAAddress getGatewayId()
    {
        return gatewayId;
    }


    public short getDeviceId()
    {
        return deviceId;
    }
    
    
    
    public byte[] getID()
    {
        byte[] id = new byte[ID_BYTES_LEN];
        byte[] gwId = gatewayId.getID();
        System.arraycopy(gwId, 0, id, 0, gwId.length);
        EndianConversor.shortToLittleEndian(this.deviceId, id, gwId.length);
        return id;
    }
    
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.gatewayId);
        sb.append("-");
        sb.append(this.deviceId);
        return sb.toString();
    }


    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + (this.gatewayId != null ? this.gatewayId.hashCode() : 0);
        hash = 29 * hash + this.deviceId;
        return hash;
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
        final DeviceID other = (DeviceID) obj;
        if (this.gatewayId != other.gatewayId && (this.gatewayId == null || !this.gatewayId.equals(other.gatewayId))) {
            return false;
        }
        if (this.deviceId != other.deviceId) {
            return false;
        }
        return true;
    }
    

}
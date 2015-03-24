/*******************************************************************************
 *   
 *   Copyright (C) 2009-2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2009-2013 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2012-2013 Victor Sonora <victor@vsonora.com>
 *   Copyright (C) 2009-2013 Alejandro Paz <alejandropl@gmail.com>
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


package com.hi3project.unida.protocol.message.statusreport;

import com.hi3project.unida.protocol.message.ErrorCode;


/** description
 *
 * @author Gervasio Varela Fernandez - Integrated Group for Engineering Research
 * @version 1
 *
 * Changelog:
 *      07-feb-2013 -- Initial version
 */
public class DeviceError 
{

    private short deviceId;
    private ErrorCode error;


    public DeviceError(short deviceId, ErrorCode error)
    {
        this.deviceId = deviceId;
        this.error = error;
    }


    public short getDeviceId()
    {
        return deviceId;
    }


    public ErrorCode getError()
    {
        return error;
    }


    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 17 * hash + this.deviceId;
        hash = 17 * hash + (this.error != null ? this.error.hashCode() : 0);
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
        final DeviceError other = (DeviceError) obj;
        if (this.deviceId != other.deviceId) {
            return false;
        }
        return this.error == other.error;
    }

    @Override
    public String toString()
    {
        return "DeviceError{" + "deviceId=" + deviceId + ", error=" + error + '}';
    }            

}

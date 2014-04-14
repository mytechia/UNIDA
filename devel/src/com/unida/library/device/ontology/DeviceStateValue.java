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

package com.unida.library.device.ontology;

import java.io.Serializable;

/**
 * <p><b>Description:</b>
 * Represents a value of a device state.
 *
 * Its ID and values are set according to the ones specified on
 * the device description ontology.
 *
 * </p>
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 28-dic-2009<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class DeviceStateValue implements Serializable
{

    private String id;

    private String value;

    
    public DeviceStateValue(String id, String value)
    {
        this.id = id;
        this.value = value;
    }

    public String getValueID()
    {
        return id;
    }
    
    public String getValueIdShort()
    {
        String[] split = getValueID().split("#");
        if (split.length >= 2) {
            return split[1];
        }
        return id;
    }


    public String getValueRaw()
    {
        return value;
    }
    
    
    public void setValueRaw(String value)
    {
        this.value = value;
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
        final DeviceStateValue other = (DeviceStateValue) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return !((this.value == null) ? (other.value != null) : !this.value.equals(other.value));
    }


    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.id);
        sb.append("-");
        sb.append(this.value);
        return sb.toString();
    }

	
}

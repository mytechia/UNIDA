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
 * <p><b>Description:</b></br>
 * Representation of a state of a hardware device.
 *
 * If the value of the state is unknown, the value field must be null.
 *
 * </p>
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 28-dic-2009<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class DeviceState implements Serializable
{
    
    /** Id of the device state on the device description ontology */
    private DeviceStateMetadata metadata;

    /** Value of the state according to the device description ontology,
     null if the state is unknown*/
    private DeviceStateValue value;


    public DeviceState(DeviceStateMetadata metadata)
    {
        this.metadata = metadata;
        this.value = null;
    }

    
    public DeviceState(DeviceStateMetadata stateId, DeviceStateValue value)
    {
        this.metadata = stateId;
        this.value = value;
    }


    public String getId()
    {
        return this.metadata.getId();
    }


    public DeviceStateMetadata getMetadata()
    {
        return metadata;
    }


    public DeviceStateValue getValue()
    {
        return value;
    }

    public void setValue(DeviceStateValue value)
    {
        this.value = value;
    }


    /** Two device state objects are considered equals if its ids and values
     * are equals.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeviceState other = (DeviceState) obj;
        if (this.metadata != other.metadata && (this.metadata == null || !this.metadata.equals(other.metadata))) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }


    
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 47 * hash + (this.metadata != null ? this.metadata.hashCode() : 0);
        hash = 47 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }


}

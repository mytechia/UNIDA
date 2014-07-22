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

package com.unida.library.device.ontology.metadata;

import com.unida.library.device.ontology.state.DeviceStateValue;
import java.io.Serializable;
import java.util.Arrays;


/**
 * <p><b>Description:</b></br>
 * Identifier of a device state type on the device description ontology.
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
public class DeviceStateMetadata implements Serializable
{


    private String id;

    /** Valid values for this type of state according to the device
     * description ontology. */
    private DeviceStateValue [] possibleValues;
    
    private boolean isWritable;


    public DeviceStateMetadata(String id, DeviceStateValue[] possibleValues)
    {
        this.id = id;
        this.possibleValues = possibleValues;
        this.isWritable = false;
    }

    public String getId()
    {
        return id;
    }
    
    public String getShortId()
    {
        String[] split = getId().split("#");
        if (split.length >= 2) {
            return split[1];
        }
        return id;
    }

    public boolean isIsWritable()
    {
        return isWritable;
    }

    public void setIsWritable(boolean isWritable)
    {
        this.isWritable = isWritable;
    }
    
    

    /** Returns the valid values for this type of state according to the device
     * description ontology.
     *
     * @return the valid values for this type of state according to the device
     * description ontology.
     */
    public DeviceStateValue[] getPossibleValues()
    {
        return possibleValues;
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
        final DeviceStateMetadata other = (DeviceStateMetadata) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + Arrays.deepHashCode(this.possibleValues);
        return hash;
    }

    @Override
    public String toString() {
        return "DeviceStateMetadata{" + "id=" + id + ", possibleValues=" + possibleValues + '}';
    }

    
    
}

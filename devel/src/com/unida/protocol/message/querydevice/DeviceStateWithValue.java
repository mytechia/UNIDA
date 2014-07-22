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


package com.unida.protocol.message.querydevice;

import com.unida.library.device.ontology.state.DeviceStateValue;
import java.util.Objects;

/**
 * <p><b>
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 28-01-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 28-01-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class DeviceStateWithValue
{

    private String stateId;
    
    private DeviceStateValue stateValue;    


    public DeviceStateWithValue(String stateId, DeviceStateValue stateValue)
    {
        this.stateId = stateId;
        this.stateValue = stateValue;
    }

    public String getStateId()
    {
        return stateId;
    }


    public DeviceStateValue getStateValue()
    {
        return stateValue;
    }
    

    protected void setStateId(String stateId)
    {
        this.stateId = stateId;
    }


    protected void setValue(DeviceStateValue value)
    {
        this.stateValue = value;
    }

    
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.stateId);
        hash = 23 * hash + Objects.hashCode(this.stateValue);
        return hash;
    }

    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final DeviceStateWithValue other = (DeviceStateWithValue) obj;
        if (!Objects.equals(this.stateId, other.stateId))
        {
            return false;
        }
        return Objects.equals(this.stateValue, other.stateValue);
    }

}

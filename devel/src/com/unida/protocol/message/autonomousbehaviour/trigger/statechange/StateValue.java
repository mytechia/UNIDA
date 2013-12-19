/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
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

package com.unida.protocol.message.autonomousbehaviour.trigger.statechange;

/**
 *
 * @author Victor Sonora
 */
public class StateValue 
{
    
    String valueID;
    String valueRaw;
    
    public StateValue(String valueID, String valueRaw)
    {
        this.valueID = valueID;
        this.valueRaw = valueRaw;
    }

    
    public String getValueID()
    {
        return valueID;
    }

    public void setValueID(String valueID)
    {
        this.valueID = valueID;
    }

    public String getValueRaw()
    {
        return valueRaw;
    }

    public void setValueRaw(String valueRaw)
    {
        this.valueRaw = valueRaw;
    }
    
    public String getValue()
    {
        if (valueID != null && !valueID.isEmpty())
        {
            return valueID;
        } else
        {
            return valueRaw;
        }
    }
    
}

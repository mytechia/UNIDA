/*******************************************************************************
 *   
 *   Copyright (C) 2014 
 *   Copyright 2014 Victor Sonora Pombo
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

package com.unida.protocol.message.notification;


/**
 * <p><b>Description:</b>
 * </p>
 *
 * <p><b>Creation date:</b> 
 * 06-11-2014 </p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li> 1 , 06-11-2014 -> Initial release</li>
 * </ul>
 * </p>
 * @author Victor Sonora Pombo
 * @version 1
 */
public enum TriggerState 
{
    
    FALSE                ((byte) 0x00),
    TRUE                  ((byte) 0x01),
    UNDEFINED               ((byte) 0x02);
    
    private byte typeValue;
    
    
    
    TriggerState(byte typeValue) {
        this.typeValue = typeValue;
    }
    
    public byte getTypeValue() {
        return typeValue;
    }
    

    public static TriggerState getTypeOf(byte typeValue) {
        if (typeValue == FALSE.typeValue)
            return FALSE;
        else if (typeValue == TRUE.typeValue)
            return TRUE;
        else if (typeValue == UNDEFINED.typeValue)
            return UNDEFINED;
        else
            return null;
    }

    @Override
    public String toString()
    {
        return "TriggerState{" + "typeValue=" + typeValue + '}';
    }
    
}

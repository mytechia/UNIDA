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


package com.unida.protocol.message;

/** 
 *
 * @author Alex
 * @version 1
 *
 * File: CommandType.java
 * Date: 22/02/2008
 * Changelog:
 *
 *      22/02/2008  --  Initial version
 */
public enum ErrorCode {
    
    Null                ((byte) 0x00),
    Ok                  ((byte) 0x01),
    Error               ((byte) 0x02);
    
    private byte typeValue;
    
    
    
    ErrorCode(byte typeValue) {
        this.typeValue = typeValue;
    }
   
    
    
    public byte getTypeValue() {
        return typeValue;
    }
    

    public static ErrorCode getTypeOf(byte typeValue) {
        if (typeValue == Null.typeValue)
            return Null;
        else if (typeValue == Ok.typeValue)
            return Ok;
        else if (typeValue == Error.typeValue)
            return Error;
        else
            return null;
    }

    
}


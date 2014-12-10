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

package com.unida.library.device.state;

import java.io.Serializable;

/**
 * <p><b>Description:</b>
 * The enumeration of possible states of operation of a device.
 * </p>
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public enum OperationalStatesEnum implements Serializable
{

    OK (0),
    ERROR (100),
    UNKNOWN (200),
    DISCONNECTED (300);


    private int stateValue;


    OperationalStatesEnum(int stateValue) {
        this.stateValue = stateValue;
    }


    public int getStateValue()
    {
        return stateValue;
    }


    public static OperationalStatesEnum getStateOf(int stateValue)
    {

        if (stateValue == OK.stateValue) {
            return OK;
        }
        else if (stateValue == ERROR.stateValue) {
            return ERROR;
        }
        else if (stateValue == DISCONNECTED.stateValue) {
            return DISCONNECTED;
        }
        else {
            return UNKNOWN;
        }

    }


}

/*******************************************************************************
 *   
 *   Copyright (C) 2010 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
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
import java.util.Date;


/**
 * <p><b>Description:</b></br>
 * Represents the state of operation of a device.
 * </p>
 *
 * <p><b>Creation date:</b> 13-04-2010</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 13-04-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class OperationalState implements Serializable
{

    private OperationalStatesEnum state;

    private Date changeTime;



    public OperationalState()
    {
        this.state = OperationalStatesEnum.UNKNOWN;
        this.changeTime = new Date();
    }

    public OperationalState(int stateValue)
    {
        this.state = OperationalStatesEnum.getStateOf(stateValue);
        this.changeTime = new Date();
    }
    
    
    public OperationalState(OperationalStatesEnum state)
    {
    	this.state = state;
    	this.changeTime = new Date();
    }


    public OperationalState(int stateValue, Date changeTime)
    {
        this.state = OperationalStatesEnum.getStateOf(stateValue);
        this.changeTime = changeTime;
    }


    public OperationalState(OperationalStatesEnum state, Date changeTime)
    {
        this.state = state;
        this.changeTime = changeTime;
    }

    public Date getChangeTime()
    {
        return changeTime;
    }


    public void setChangeTime(Date changeTime)
    {
        this.changeTime = changeTime;
    }


    public OperationalStatesEnum getState()
    {
        return state;
    }


    public void setState(OperationalStatesEnum state)
    {
        this.state = state;
    }


    public int getStateValue()
    {
        return this.state.getStateValue();
    }

}

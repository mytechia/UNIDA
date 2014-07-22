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

import java.io.Serializable;

/**
 * <p><b>Description:</b></br>
 * A control functionality specifies the commands that can be used to
 * control the behaviour of a device.
 * 
 * Representation of a control functionality according to the
 * device ontology description.
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
public class ControlFunctionalityMetadata implements Serializable
{

    
    /** Id of the functionality as specified in the device ontology */
    private String id;

    /** Commands associated to this type of functionality */
    private ControlCommandMetadata [] availableCommands;


    public ControlFunctionalityMetadata(String id, ControlCommandMetadata[] availableCommands)
    {
        this.id = id;
        this.availableCommands = availableCommands;
    }

    public ControlCommandMetadata[] getAvailableCommands()
    {
        return availableCommands;
    }


    public String getId()
    {
        return id;
    }

    
}

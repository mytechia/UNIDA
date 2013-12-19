/*******************************************************************************
 *   
 *   Copyright (C) 2008 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2008 Gervasio Varela <gervarela@picandocodigo.com>
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

package com.unida.library.device.exception;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;




/**
 * <p><b>Description:</b></br>
 * 
 *
 * </p>
 *
 * <p><b>Creation date:</b> 5-feb-2013</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 5-feb-2013<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez - Integrated Group for Engineering Research
 * @version 1
 */
public class UniDAIDFormatException extends MessageFormatException
{

    private String id;


    public UniDAIDFormatException(String id)
    {
        super("Unable to find UniDA ID in given ontology: " + id);
        this.id = id;
    }


    public UniDAIDFormatException(String id, String message)
    {
        super(message);
        this.id = id;
    }


    public String getId()
    {
        return id;
    }

}

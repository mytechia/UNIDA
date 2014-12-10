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
 * <p><b>Description:</b>
 * Represents the metadata of a notification as espcified in the
 * device ontology.
 *
 * </p>
 *
 * <p><b>Creation date:</b> 29-dic-2009</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 29-dic-2009 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class NotificationMetadata implements Serializable
{


    /** Id of the notification specified in the device ontology */
    private String id;

    /** Number of parameters of the notification as specified in the device ontolgy */
    private int nParams;

    
    public NotificationMetadata(String id, int nParams)
    {
        this.id = id;
        this.nParams = nParams;
    }

    public String getId()
    {
        return id;
    }


    public int getNParams()
    {
        return nParams;
    }
	
}

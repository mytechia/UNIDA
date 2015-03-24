
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

package com.hi3project.unida.library.device.ontology.metadata;

import java.io.Serializable;

/**
 * <p><b>Description:</b></p>
 * A notification functionality specifies the notifications that a device
 * can send.
 *
 * Representation of a notification functionality according to the
 * device ontology description.
 *
 *
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class NotificationFunctionalityMetadata implements Serializable
{

    private String id;

    private NotificationMetadata [] availableNotifications;

    
    public NotificationFunctionalityMetadata(String id, NotificationMetadata [] notifications)
    {
        this.id = id;
        this.availableNotifications = notifications;
    }

    public String getId()
    {
        return id;
    }

    public NotificationMetadata[] getAvailableNotifications()
    {
        return availableNotifications;
    }

}

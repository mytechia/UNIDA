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

package com.unida.library.notification;

import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.state.DeviceState;

/**
 * <p><b>Description:</b>
 * All client classes that want to receive state notification from devices must
 * implement this interface.
 *
 * </p>
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 28-dic-2009<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public interface IDeviceStateNotificationCallback
{

    /**
     * Notifies a state notification.
     *
     * @param nt Id of the notification
     * @param dev The device origin of the notification
     * @param state The state of the device when the notification was triggered
     */
    void notifyState(NotificationTicket nt, IDevice dev, DeviceState state);
	
	
}

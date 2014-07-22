/**
 * *****************************************************************************
 *
 * Copyright (C) 2009 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2009 Gervasio Varela <gervarela@picandocodigo.com>
 *
 * This file is part of UNIDA.
 *
 * UNIDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * UNIDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with UNIDA. If not, see <http://www.gnu.org/licenses/>.
 *
 *****************************************************************************
 */
package com.unida.library.notification;

import com.unida.library.device.DeviceID;
import com.unida.library.device.ontology.state.DeviceStateValue;

/**
 * <p>
 * <b>Description:</b>
 * Callback to internally manage the reception of information about a
 * notification that a given device fires up.
 * </p>
 *
 * <p>
 * <b>Creation date:</b> 15-1-2009</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>1 - 15-1-2009 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public interface INotificationInternalCallback
{

    /**
     * @param nTicketId
     * @param deviceId The device that fired up the notification
     * @param stateId The state of the device when the notification occurred
     * @param stateValue The current value for that state
     */
    void notifyState(long nTicketId, DeviceID deviceId, String stateId, DeviceStateValue stateValue);

}

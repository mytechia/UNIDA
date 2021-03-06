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
package com.hi3project.unida.library.notification;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.hi3project.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.hi3project.unida.library.device.IDevice;
import java.util.Collection;

/**
 * <p>
 * <b>Description:</b>
 * Implementations of this interface manage the suscription of client of the
 * UniDA to notifications fired up by the hardware devices.
 *
 *
 * <p>
 * <b>Creation date:</b> 15-1-2009</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>1 - 15-1-2009 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public interface INotificationSuscriptionManager
{

    /**
     * Suscribes a client to notifications about
     *
     * @param dev
     * @param state
     * @param params
     * @param callback
     * @return
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    NotificationTicket suscribeTo(IDevice dev, DeviceStateMetadata state,
            String[] params, IDeviceStateNotificationCallback callback)
            throws InternalErrorException;

    /**
     * @param nt
     * @param dev
     * @param state
     * @param params
     * @param callback
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    void unsuscribeFrom(NotificationTicket nt, IDevice dev, DeviceStateMetadata state,
            String[] params, IDeviceStateNotificationCallback callback)
            throws InternalErrorException;

    /**
     * @param dev
     * @return
     */
    Collection<DeviceStateSuscription> getDeviceSuscriptions(IDevice dev);

    /**
     * FOR INTERNAL USER ONLY Resends a suscription request to the device. It
     * can be used when is detected that a devices has been moved to other
     * gateway, in order to restablish the suscriptions
     *
     * @param dev
     * @throws InternalErrorException
     */
    void renewSuscriptions(IDevice dev) throws InternalErrorException;

}

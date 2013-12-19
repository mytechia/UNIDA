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


import com.mytechia.commons.framework.exception.InternalErrorException;
import com.unida.library.device.ontology.DeviceStateMetadata;
import com.unida.library.device.IDevice;
import java.util.Collection;

/**
 * <p><b>Description:</b></br>
 * Implementations of this interface manage the suscription of client of the
 * DAL to notifications fired up by the hardware devices.
 * </p>
 *
 * <p><b>Creation date:</b> 15-1-2009</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 15-1-2009<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public interface INotificationSuscriptionManager
{
    
	/** Suscribes a client to notifications about
         *
	 * @param dev
	 * @param state
	 * @param notification
	 * @param params
	 * @param callback
	 */
//	NotificationTicket suscribeTo(Device dev, DeviceStateMetadata state, Collection<String> params, IDeviceStateNotificationCallback callback)
//                throws InternalErrorException;
        NotificationTicket suscribeTo(IDevice dev, DeviceStateMetadata state, 
                String [] params, IDeviceStateNotificationCallback callback)
                throws InternalErrorException;


	/**
	 * @param dev
	 * @param state
	 * @param notification
	 * @param callback
	 */
	void unsuscribeFrom(NotificationTicket nt, IDevice dev, DeviceStateMetadata state,
                String [] params, IDeviceStateNotificationCallback callback)
                throws InternalErrorException;

        
	/**
	 * @param dev
	 * @return 
	 */
	Collection<DeviceStateSuscription> getDeviceSuscriptions(IDevice dev);



        /** FOR INTERNAL USER ONLY
         * Resends a suscription request to the device.
         * It can be used when is detected that a devices has been moved to
         * other gateway, in order to restablish the suscriptions
         *
         * @param ticket
         * @throws InternalErrorException
         */
        void renewSuscriptions(IDevice dev) throws InternalErrorException;
	
	
}

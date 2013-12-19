/*******************************************************************************
 *   
 *   Copyright (C) 2009-2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2009-2013 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2012-2013 Victor Sonora <victor@vsonora.com>
 *   Copyright (C) 2009-2013 Alejandro Paz <alejandropl@gmail.com>
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

package com.unida.library.core;

import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.unida.library.device.DeviceID;
import com.unida.library.device.Gateway;
import com.unida.library.notification.INotificationCallback;
import java.net.UnknownHostException;

/**
 * <p><b>
 * Implementations of this interface provide communication with a specific 
 * network of UniDA gateways.
 * For every technology supported by the platform there should be an implementation
 * of this interface.
 * </b></br>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 11-ene-2010</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>2 - 5-feb-2013<\br> Update to support the new style of device IDs</li>
 * <li>1 - 11-ene-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public interface IUniDANetworkFacade 
{
    

	void queryDeviceState(long opId, DeviceID deviceId, String stateId, IUnidaNetworkFacadeCallback callback)
                throws CommunicationException;

	
	void queryDevice(long opId, DeviceID deviceId, IUnidaNetworkFacadeCallback callback)
                throws CommunicationException;
        
        
        void writeDeviceState(long opId, DeviceID deviceId, String stateId, 
                String stateValueId, String stateValue, IUnidaNetworkFacadeCallback callback)
                throws CommunicationException;

	
	void sendCommand(long opId, DeviceID deviceId, String funcId, String cmdId, String[] params, IUnidaNetworkFacadeCallback callback)
                throws CommunicationException;

        	
        void suscribeTo(long notificationId, DeviceID deviceId, String stateId, String[] params, INotificationCallback callback)
                throws CommunicationException;


        void unsuscribeFrom(long notificationId, DeviceID deviceId, String stateId, String[] params, INotificationCallback callback)
                throws CommunicationException;


        /** FOR INTERNAL USE ONLY
         * Resends a suscription request to the device.
         * It can be used when is detected that a devices has been moved to
         * other gateway, in order to restablish the suscriptions
         */
        void renewSuscription(long notificationId, DeviceID deviceId, String stateId, String[] params, INotificationCallback callback)
                throws CommunicationException;


        /** Checks if this DAL facade instance supports the communication
         * with an specified device.
         */
        boolean supports(Gateway dev) throws UnknownHostException;
	
}

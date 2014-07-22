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
import com.unida.library.device.ontology.metadata.DeviceStateMetadata;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * <p><b>Description:</b>
 * Holds the information required to process state notifications coming
 * from the devices.
 *
 * </p>
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class DeviceStateSuscription
{    

    private IDevice device;

    private DeviceStateMetadata state;
    
    /** parameters that specify when the notification must be triggered */
    private String [] params;
    

    /** suscribers of the notification */
    private LinkedList<IDeviceStateNotificationCallback> callbacks;

    /** Automatically generated unique-id of the notification */
    private NotificationTicket ticket;



    //package visibility
    DeviceStateSuscription(
            NotificationTicket ticket, IDevice device, 
            DeviceStateMetadata state, String [] params)
    {        
        this.device = device;
        this.state = state;
        this.params = Arrays.copyOf(params, params.length);
        this.ticket = ticket;
        this.callbacks = new LinkedList<>();
    }


    public Collection<IDeviceStateNotificationCallback> getCallbacks()
    {
        return new ArrayList<>(callbacks);
    }


    public IDevice getDevice()
    {
        return this.device;
    }


    public DeviceStateMetadata getState()
    {
        return this.state;
    }


    public NotificationTicket getNotificationTicket()
    {
        return this.ticket;
    }


    public String [] getParameters()
    {
        return params;
    }


    void addCallback(IDeviceStateNotificationCallback callback)
    {
        this.callbacks.add(callback);
    }


    void removeCallback(IDeviceStateNotificationCallback callback)
    {
        this.callbacks.remove(callback);
    }   
	
}

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

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.unida.library.device.ontology.state.DeviceState;
import com.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.unida.library.device.ontology.state.DeviceStateValue;
import com.unida.library.core.IUniDANetworkFacade;
import com.unida.library.UniDAFactory;
import com.unida.library.device.DeviceGroup;
import com.unida.library.device.DeviceID;
import com.unida.library.device.Gateway;
import com.unida.library.device.IDevice;
import com.unida.library.operation.device.exception.NotEnabledDeviceErrorException;
import com.unida.library.manage.IUniDAManagementFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * <b>Description:</b> </p>
 *
 * <p>
 * <b>Creation date:</b> 15-1-2009</p>
 *
 * <p>
 * <b>Changelog:</b> <ul> <li>1 - 15-1-2009<\br> Initial release</li>
 * </ul> </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class DefaultNotificationSuscriptionManager implements INotificationSuscriptionManager, INotificationInternalCallback
{

    private UniDAFactory dalFactory;
    private IUniDAManagementFacade deviceManager;
    private NotificationTicketsManager ticketsManager;
    private Map<NotificationTicket, DeviceStateSuscription> suscriptionsByTicket;
    private Map<DeviceID, Set<DeviceStateSuscription>> suscriptionsByDeviceId;

    public DefaultNotificationSuscriptionManager(UniDAFactory dalFactory, IUniDAManagementFacade deviceManager)
    {
        this.dalFactory = dalFactory;
        this.deviceManager = deviceManager;
        this.ticketsManager = new NotificationTicketsManager();
        this.suscriptionsByDeviceId = new HashMap<>();
        this.suscriptionsByTicket = new HashMap<>();
    }

    @Override
    public NotificationTicket suscribeTo(IDevice dev, DeviceStateMetadata state,
            String[] params, IDeviceStateNotificationCallback callback)
            throws InternalErrorException
    {

        Set<DeviceStateSuscription> deviceSuscriptions = suscriptionsByDeviceId.get(dev.getId());
        if (deviceSuscriptions != null)
        {
            //look if the device already has defined a notification about the same state with the same params
            for (DeviceStateSuscription dss : deviceSuscriptions)
            {
                if (dss.getState().getId().equals(state.getId()))
                {
                    if (Arrays.deepEquals(dss.getParameters(), params))
                    { //the notification already exists
                        dss.addCallback(callback); //add the callback to that notification
                        try
                        {
                            suscribeTo(dss.getNotificationTicket(), dss, dev, params); //create the notification on the device (or devices if is a group)                            
                        } catch (InstanceNotFoundException ex)
                        {
                            throw new InternalErrorException(ex);
                        }
                        return dss.getNotificationTicket();
                    }
                    try
                    {
                        suscribeTo(dss.getNotificationTicket(), dss, dev, params); //create the notification on the device (or devices if is a group)                            
                    } catch (InstanceNotFoundException ex)
                    {
                        throw new InternalErrorException(ex);
                    }
                    return dss.getNotificationTicket();
                }
            }
        }

        //if the notification doesn't exists
        NotificationTicket nt = ticketsManager.issueTicket();
        DeviceStateSuscription dss = new DeviceStateSuscription(nt, dev, state, params);
        try
        {
            suscribeTo(nt, dss, dev, params); //create the notification on the device (or devices if is a group)
            addSuscription(dss, callback); //associate the callback with it
        } catch (InstanceNotFoundException ex)
        {
            throw new InternalErrorException(ex);
        }

        return nt;

    }

    private synchronized void addSuscription(DeviceStateSuscription dss, IDeviceStateNotificationCallback callback)
    {
        dss.addCallback(callback);
        Set<DeviceStateSuscription> deviceSuscriptions = this.suscriptionsByDeviceId.get(dss.getDevice().getId());
        if (deviceSuscriptions == null)
        {
            deviceSuscriptions = new HashSet<>();
            this.suscriptionsByDeviceId.put(dss.getDevice().getId(), deviceSuscriptions);
        }
        deviceSuscriptions.add(dss);
        this.suscriptionsByTicket.put(dss.getNotificationTicket(), dss);
    }

    /**
     *
     * @param dss the suscription to remove
     * @param callback the callback to remove from the suscription
     * @return true if the suscription was completely removed, false if some
     * active callback already exists
     */
    private synchronized boolean removeSuscription(DeviceStateSuscription dss, IDeviceStateNotificationCallback callback)
    {
        dss.removeCallback(callback); //remove the callback from the suscription data
        if (dss.getCallbacks().isEmpty())
        { //if there isn't more callbacks, remove the suscription
            Set<DeviceStateSuscription> deviceSuscriptions = this.suscriptionsByDeviceId.get(dss.getDevice().getId());
            deviceSuscriptions.remove(dss);
            this.suscriptionsByTicket.remove(dss.getNotificationTicket());
            return true;
        }
        return false;
    }

    private Gateway getDeviceGateway(IDevice dev) throws NotEnabledDeviceErrorException, InstanceNotFoundException, InternalErrorException
    {

        IDevice realDevice = this.deviceManager.findById(dev.getId().toString());
        return this.deviceManager.findDeviceGatewayById(realDevice.getId().getGatewayId().toString());

    }

    private void suscribeTo(NotificationTicket nt, DeviceStateSuscription dss, IDevice dev, String[] params)
            throws InternalErrorException, InstanceNotFoundException
    {
        if (dev.isGroup())
        {
            //if it is a group, the notification is created on every physical device
            //that is member of the group. But, only one suscription is stored
            //in the manager, the suscription with the group device.
            Collection<IDevice> members = this.deviceManager.findGroupMembers((DeviceGroup) dev);
            for (IDevice d : members)
            {
                suscribeTo(nt, dss, d, params);
            }
        } else
        {
            Gateway devGw = getDeviceGateway(dev);
            IUniDANetworkFacade dalInstance = this.dalFactory.getDALInstance(devGw);
            dalInstance.suscribeTo(nt.getId(), dev.getId(), dss.getState().getId(), params, this);
        }
    }

    @Override
    public void unsuscribeFrom(NotificationTicket nt, IDevice dev, DeviceStateMetadata state,
            String[] params, IDeviceStateNotificationCallback callback)
            throws InternalErrorException
    {

        DeviceStateSuscription dss = this.suscriptionsByTicket.get(nt);
        if (dss != null)
        {

            if (removeSuscription(dss, callback))
            {
                //there isn't more callbacks associated with the suscription -> delete it from the device
                try
                {
                    unsuscribeFrom(nt, dss, dev, params);
                } catch (InstanceNotFoundException ex)
                {
                    throw new InternalErrorException(ex);
                }
            }

        }

    }

    private void unsuscribeFrom(NotificationTicket nt, DeviceStateSuscription dss, IDevice dev, String[] params)
            throws InternalErrorException, InstanceNotFoundException
    {

        if (dev.isGroup())
        {
            //if it is a group, the notification is created on every physical device
            //that is member of the group. But, only one suscription is stored
            //in the manager, the suscription with the group device.
            Collection<IDevice> members = this.deviceManager.findGroupMembers((DeviceGroup) dev);
            for (IDevice d : members)
            { //TODO -> if there is an exception --> we must cancel all the previous ones
                unsuscribeFrom(nt, dss, d, params);
            }
        } else
        {
            Gateway devGw = getDeviceGateway(dev);
            IUniDANetworkFacade dalInstance = this.dalFactory.getDALInstance(devGw);
            dalInstance.unsuscribeFrom(nt.getId(), dev.getId(), dss.getState().getId(), params, this);
        }
    }

    @Override
    public Collection<DeviceStateSuscription> getDeviceSuscriptions(IDevice dev)
    {
        ArrayList<DeviceStateSuscription> result = new ArrayList<>(0);
        Set<DeviceStateSuscription> suscriptions = this.suscriptionsByDeviceId.get(dev.getId());
        if (suscriptions != null)
        {
            result.addAll(suscriptions);
        }
        return result;
    }

    @Override
    public void renewSuscriptions(IDevice dev) throws InternalErrorException
    {

        Collection<DeviceStateSuscription> suscriptions = getDeviceSuscriptions(dev);

        if (dev.isGroup())
        {
            try
            {
                //if it is a group, the notification is created on every physical device
                //that is member of the group. But, only one suscription is stored
                //in the manager, the suscription with the group device.
                Collection<IDevice> members = this.deviceManager.findGroupMembers((DeviceGroup) dev);
                for (IDevice d : members)
                { //TODO -> if there is an exception --> we must cancel all the previous ones
                    renewSuscriptions(d);
                }
            } catch (InstanceNotFoundException ex)
            {
                throw new InternalErrorException(ex);
            }
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.dalFactory.getDALInstance(devGw);
                for (DeviceStateSuscription s : suscriptions)
                {
                    dalInstance.renewSuscription(s.getNotificationTicket().getId(), s.getDevice().getId(), s.getState().getId(), s.getParameters(), this);
                }
            } catch (InstanceNotFoundException ex)
            {
                throw new InternalErrorException(ex);
            }
        }

    }

    @Override
    public synchronized void notifyState(long nTicket, DeviceID id, String stateId, DeviceStateValue stateValue)
    {

        DeviceStateSuscription dss = this.suscriptionsByTicket.get(new NotificationTicket(nTicket));
        if (dss != null)
        {
            if (dss.getDevice().getId().equals(id) && dss.getState().getId().equals(stateId))
            {
                DeviceState ds = new DeviceState(dss.getState(), stateValue);
                for (IDeviceStateNotificationCallback cback : dss.getCallbacks())
                {
                    cback.notifyState(dss.getNotificationTicket(), dss.getDevice(), ds);
                }
            }
        }

    }

    private class NotificationTicketsManager
    {

        private long ticketId = 0;

        public synchronized NotificationTicket issueTicket()
        {
            return new NotificationTicket(ticketId++);
        }
    }
}

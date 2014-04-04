/**
 * *****************************************************************************
 *
 * Copyright (C) 2010 Mytech Ingenieria Aplicada
 * <http://www.mytechia.com>
 * Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
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
package com.unida.library.operation.device;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.unida.library.device.ontology.ControlCommandMetadata;
import com.unida.library.device.ontology.DeviceStateMetadata;
import com.unida.library.core.IUniDANetworkFacade;
import com.unida.library.UniDAFactory;
import com.unida.library.device.DeviceGroup;
import com.unida.library.device.Gateway;
import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.DeviceStateValue;
import com.unida.library.operation.device.group.IGroupOperationManager;
import com.unida.library.notification.IDeviceStateNotificationCallback;
import com.unida.library.notification.INotificationSuscriptionManager;
import com.unida.library.notification.NotificationTicket;
import com.unida.library.operation.device.exception.NotEnabledDeviceErrorException;
import com.unida.library.manage.IUniDAManagementFacade;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p><b>Description:</b>
 * Default implementation of the IDeviceOperationFacade. It provides an entry
 * point to interact with the real hardware devices through send command
 * operations, read state operations and notification suscription.
 * </p>
 *
 * <p><b>Creation date:</b> 15-01-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 15-01-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class DefaultDeviceOperationFacade implements IDeviceOperationFacade
{

    private INotificationSuscriptionManager suscriptionManager;
    private UniDAFactory dalFactory;
    private IGroupOperationManager groupManager;
    private IUniDAManagementFacade deviceManager;
    private OperationTicketManager ticketManager;
    private Queue<DefaultDeviceAccessLayerCallback> dalCallbackQueue;

    public DefaultDeviceOperationFacade(
            UniDAFactory dalFactory,
            IGroupOperationManager groupManager,
            INotificationSuscriptionManager suscriptionManager,
            IUniDAManagementFacade deviceManager)
    {
        this.ticketManager = new OperationTicketManager();
        this.dalFactory = dalFactory;
        this.groupManager = groupManager;
        this.suscriptionManager = suscriptionManager;
        this.deviceManager = deviceManager;
        this.dalCallbackQueue = new LinkedList<>();
    }

    void addDALCallback(DefaultDeviceAccessLayerCallback callback)
    {
        this.dalCallbackQueue.add(callback);
    }

    void removeDALCallback(DefaultDeviceAccessLayerCallback callback)
    {
        this.dalCallbackQueue.remove(callback);
    }

    private Gateway getDeviceGateway(IDevice dev) throws NotEnabledDeviceErrorException, InstanceNotFoundException, InternalErrorException
    {

        IDevice realDevice = this.deviceManager.findById(dev.getId().toString());
        return this.deviceManager.findDeviceGatewayById(realDevice.getId().getGatewayId().toString());

    }

    @Override
    public OperationTicket asyncQueryDeviceState(IDevice dev, DeviceStateMetadata state, IDeviceOperationCallback callback)
            throws InternalErrorException
    {

        OperationTicket ot = this.ticketManager.issueTicket(OperationTypes.QUERY_STATE);

        if (dev.isGroup())
        {
            return this.groupManager.asyncQueryDeviceState(ot, dev, state, callback);
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.dalFactory.getDALInstance(devGw);
                DefaultDeviceAccessLayerCallback dalCback = new DefaultDeviceAccessLayerCallback(this, ot, dev, state, callback);
                addDALCallback(dalCback);
                dalInstance.queryDeviceState(ot.getId(), dev.getId(), state.getId(), dalCback);
                return ot;
            } catch (InstanceNotFoundException ex)
            {
                throw new InternalErrorException(ex);
            }
        }

    }

    @Override
    public OperationTicket asyncWriteDeviceState(IDevice dev, DeviceStateMetadata state, 
            DeviceStateValue stateValue, IDeviceOperationCallback callback)
            throws InternalErrorException
    {
        OperationTicket ot = this.ticketManager.issueTicket(OperationTypes.WRITE_STATE);

        if (dev.isGroup())
        {
            return this.groupManager.asyncWriteDeviceState(ot, dev, state, stateValue, callback);
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.dalFactory.getDALInstance(devGw);
                DefaultDeviceAccessLayerCallback dalCback = new DefaultDeviceAccessLayerCallback(this, ot, dev, state, callback);
                addDALCallback(dalCback);
                dalInstance.writeDeviceState(ot.getId(), dev.getId(), state.getId(), 
                        stateValue.getValueID(), stateValue.getValueRaw(), dalCback);
                return ot;
            } catch (InstanceNotFoundException ex)
            {
                throw new InternalErrorException(ex);
            }
        }
    }

    @Override
    public OperationTicket asyncQueryDeviceStates(IDevice dev, IDeviceOperationCallback callback)
            throws InternalErrorException
    {

        OperationTicket ot = this.ticketManager.issueTicket(OperationTypes.QUERY_STATE);

        if (dev.isGroup())
        {
            return this.groupManager.asyncQueryDeviceStates(ot, dev, callback);
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.dalFactory.getDALInstance(devGw);
                DefaultDeviceAccessLayerCallback dalCback = new DefaultDeviceAccessLayerCallback(this, ot, dev, callback);
                addDALCallback(dalCback);
                dalInstance.queryDevice(ot.getId(), dev.getId(), dalCback);
                return ot;
            } catch (InstanceNotFoundException ex)
            {
                throw new InternalErrorException(ex);
            }
        }

    }

    @Override
    public OperationTicket asyncSendCommand(IDevice dev, ControlFunctionalityMetadata func, ControlCommandMetadata cmd, String[] params, IDeviceOperationCallback callback)
            throws InternalErrorException
    {

        OperationTicket ot = this.ticketManager.issueTicket(OperationTypes.SEND_COMMAND);

        if (dev.isGroup())
        {
            return this.groupManager.sendCommand(ot, (DeviceGroup) dev, func, cmd, params, callback);
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.dalFactory.getDALInstance(devGw);
                DefaultDeviceAccessLayerCallback dalCback = new DefaultDeviceAccessLayerCallback(this, ot, dev, func, cmd, callback);
                addDALCallback(dalCback);
                dalInstance.sendCommand(ot.getId(), dev.getId(), func.getId(), cmd.getId(), params, dalCback);
                return ot;
            } catch (InstanceNotFoundException ex)
            {
                throw new InternalErrorException(ex);
            }
        }

    }

    @Override
    public NotificationTicket suscribeTo(
            IDevice dev, DeviceStateMetadata state, String[] params,
            IDeviceStateNotificationCallback callback)
            throws InternalErrorException
    {
        return this.suscriptionManager.suscribeTo(dev, state, params, callback);
    }

    @Override
    public void unsuscribeFrom(
            NotificationTicket nt, IDevice dev, DeviceStateMetadata state,
            String[] params, IDeviceStateNotificationCallback callback)
            throws InternalErrorException
    {
        this.suscriptionManager.unsuscribeFrom(nt, dev, state, params, callback);
    }

    private class OperationTicketManager
    {

        private long ticketId = 0;

        public synchronized OperationTicket issueTicket(OperationTypes type)
        {
            return new OperationTicket(ticketId++, type);
        }
    }
}

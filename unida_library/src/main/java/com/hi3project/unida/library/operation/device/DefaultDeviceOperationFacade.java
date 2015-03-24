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
package com.hi3project.unida.library.operation.device;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.hi3project.unida.library.UniDANetworkFactory;
import com.hi3project.unida.library.core.IUniDANetworkFacade;
import com.hi3project.unida.library.device.DeviceGroup;
import com.hi3project.unida.library.device.Gateway;
import com.hi3project.unida.library.device.IDevice;
import com.hi3project.unida.library.device.ontology.metadata.ControlCommandMetadata;
import com.hi3project.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import com.hi3project.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.hi3project.unida.library.device.ontology.state.DeviceStateValue;
import com.hi3project.unida.library.manage.IUniDAManagementFacade;
import com.hi3project.unida.library.notification.IDeviceStateNotificationCallback;
import com.hi3project.unida.library.notification.INotificationSuscriptionManager;
import com.hi3project.unida.library.notification.NotificationTicket;
import com.hi3project.unida.library.operation.OperationTicket;
import com.hi3project.unida.library.operation.OperationTicketManager;
import com.hi3project.unida.library.operation.OperationTypes;
import com.hi3project.unida.library.operation.device.exception.NotEnabledDeviceErrorException;
import com.hi3project.unida.library.operation.device.group.IGroupOperationManager;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p><b>Description:</b></p>
 * Default implementation of the IDeviceOperationFacade. It provides an entry
 * point to interact with the real hardware devices through send command
 * operations, read state operations and notification suscription.
 *
 *
 * <p><b>Creation date:</b> 15-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 15-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class DefaultDeviceOperationFacade implements IDeviceOperationFacade
{

    private INotificationSuscriptionManager notificationSuscriptionManager;
    private UniDANetworkFactory unidaFactory;
    private IGroupOperationManager groupOperationManager;
    private IUniDAManagementFacade unidaManager;
    private OperationTicketManager ticketManager;
    private Queue<DefaultDeviceAccessLayerCallback> deviceAccessLayerCallbackQueue;

    public DefaultDeviceOperationFacade(
            UniDANetworkFactory unidaFactory,
            IGroupOperationManager groupManager,
            INotificationSuscriptionManager suscriptionManager,
            IUniDAManagementFacade unidaManager)
    {
        this.ticketManager = new OperationTicketManager();
        this.unidaFactory = unidaFactory;
        this.groupOperationManager = groupManager;
        this.notificationSuscriptionManager = suscriptionManager;
        this.unidaManager = unidaManager;
        this.deviceAccessLayerCallbackQueue = new LinkedList<>();
    }

    void addCallback(DefaultDeviceAccessLayerCallback callback)
    {
        this.deviceAccessLayerCallbackQueue.add(callback);
    }

    void removeCallback(DefaultDeviceAccessLayerCallback callback)
    {
        this.deviceAccessLayerCallbackQueue.remove(callback);
    }

    private Gateway getDeviceGateway(IDevice dev) throws NotEnabledDeviceErrorException, InstanceNotFoundException, InternalErrorException
    {

        IDevice realDevice = this.unidaManager.findById(dev.getId().toString());
        return this.unidaManager.findDeviceGatewayById(realDevice.getId().getGatewayId().toString());

    }

    @Override
    public OperationTicket asyncQueryDeviceState(IDevice dev, DeviceStateMetadata state, IDeviceOperationCallback callback)
            throws InternalErrorException
    {

        OperationTicket ot = this.ticketManager.issueTicket(OperationTypes.QUERY_STATE);

        if (dev.isGroup())
        {
            return this.groupOperationManager.asyncQueryDeviceState(ot, dev, state, callback);
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.unidaFactory.getUniDANetworkInstance(devGw);
                DefaultDeviceAccessLayerCallback dalCback = new DefaultDeviceAccessLayerCallback(this, ot, dev, state, callback);
                addCallback(dalCback);
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
            return this.groupOperationManager.asyncWriteDeviceState(ot, dev, state, stateValue, callback);
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.unidaFactory.getUniDANetworkInstance(devGw);
                DefaultDeviceAccessLayerCallback dalCback = new DefaultDeviceAccessLayerCallback(this, ot, dev, state, callback);
                addCallback(dalCback);
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
            return this.groupOperationManager.asyncQueryDeviceStates(ot, dev, callback);
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.unidaFactory.getUniDANetworkInstance(devGw);
                DefaultDeviceAccessLayerCallback dalCback = new DefaultDeviceAccessLayerCallback(this, ot, dev, callback);
                addCallback(dalCback);
                dalInstance.queryDevice(ot.getId(), dev.getId(), dalCback);
                return ot;
            } catch (InstanceNotFoundException ex)
            {
                throw new InternalErrorException(ex);
            }
        }

    }
    
    @Override
    public OperationTicket asyncModifyDeviceInfo(IDevice dev, String name, String description, String location, IDeviceOperationCallback callback) throws InternalErrorException
    {
        OperationTicket ot = this.ticketManager.issueTicket(OperationTypes.MODIFY_DEVICE_INFO);
        
        if (dev.isGroup())
        {
            return this.groupOperationManager.asyncModifyDeviceInfo(ot, dev, name, description, location);
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.unidaFactory.getUniDANetworkInstance(devGw);
                DefaultDeviceAccessLayerCallback dalCback = new DefaultDeviceAccessLayerCallback(this, ot, dev, callback);
                addCallback(dalCback);
                dalInstance.modifyDeviceInfo(ot.getId(), dev.getId(), name, description, location, dalCback);
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
            return this.groupOperationManager.sendCommand(ot, (DeviceGroup) dev, func, cmd, params, callback);
        } else
        {
            try
            {
                Gateway devGw = getDeviceGateway(dev);
                IUniDANetworkFacade dalInstance = this.unidaFactory.getUniDANetworkInstance(devGw);
                DefaultDeviceAccessLayerCallback dalCback = new DefaultDeviceAccessLayerCallback(this, ot, dev, func, cmd, callback);
                addCallback(dalCback);
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
        return this.notificationSuscriptionManager.suscribeTo(dev, state, params, callback);
    }

    @Override
    public void unsuscribeFrom(
            NotificationTicket nt, IDevice dev, DeviceStateMetadata state,
            String[] params, IDeviceStateNotificationCallback callback)
            throws InternalErrorException
    {
        this.notificationSuscriptionManager.unsuscribeFrom(nt, dev, state, params, callback);
    }    

    
}

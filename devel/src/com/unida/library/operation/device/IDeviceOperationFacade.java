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
package com.unida.library.operation.device;

import com.unida.library.operation.OperationTicket;
import com.mytechia.commons.framework.exception.InternalErrorException;
import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.unida.library.device.ontology.metadata.ControlCommandMetadata;
import com.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.state.DeviceStateValue;
import com.unida.library.notification.IDeviceStateNotificationCallback;
import com.unida.library.notification.NotificationTicket;

/**
 * <p>
 * <b>Description:</b>
 * Interface to access the devices through the UniDA library. It provides
 * support to issue commands to the devices, access to its state, notifications,
 * etc.
 *
 * </p>
 *
 * <p>
 * <b>Creation date:</b> 28-dic-2009</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public interface IDeviceOperationFacade
{

    /**
     * Queries a state of a device asynchronously
     *
     * @param dev The device which state is required
     * @param state The state to query
     * @param callback Callback to receive the results of the operation
     * @return
     * @throws InternalErrorException if there was a critical error processing
     * the request, like a device whose gateway is not supported.
     */
    OperationTicket asyncQueryDeviceState(IDevice dev, DeviceStateMetadata state, IDeviceOperationCallback callback)
            throws InternalErrorException;

    /**
     * Queries the states of a device asynchronously
     *
     * @param dev The device whose states are required
     * @param callback Callback to receive the results of the operation
     * @return
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    OperationTicket asyncQueryDeviceStates(IDevice dev, IDeviceOperationCallback callback)
            throws InternalErrorException;

    /**
     * Writes a state to a device asynchronously
     *
     * @param dev The device which state is required
     * @param state The state to write
     * @param stateValue
     * @param callback Callback to receive the results of the operation
     * @return
     * @throws InternalErrorException if there was a critical error processing
     * the request, like a device whose gateway is not supported.
     */
    OperationTicket asyncWriteDeviceState(IDevice dev, DeviceStateMetadata state,
            DeviceStateValue stateValue, IDeviceOperationCallback callback)
            throws InternalErrorException;

    /**
     * Sends a command to a device
     *
     * @param dev The referenced device
     * @param cfm
     * @param cmd The command to send to the device
     * @param params Paramters of the command
     * @param callback
     * @return
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    OperationTicket asyncSendCommand(IDevice dev, ControlFunctionalityMetadata cfm, ControlCommandMetadata cmd,
            String[] params, IDeviceOperationCallback callback)
            throws InternalErrorException;

    /**
     * Suscribes a component to a notification from a device.
     *
     * @param dev The referenced device
     * @param state The state whose notification are of interest
     * @param params
     * @param callback Callback to receive the notifications
     * @return
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    //NotificationTicket suscribeTo(Device dev, DeviceStateMetadata state, Collection<String> params, IDeviceStateNotificationCallback callback) throws InternalErrorException;
    NotificationTicket suscribeTo(IDevice dev, DeviceStateMetadata state, String[] params, IDeviceStateNotificationCallback callback) throws InternalErrorException;

    /**
     * Unsuscribes a component from a device's notification
     *
     * @param nt
     * @param dev The referenced device
     * @param state The state whose notification are of interest
     * @param params
     * @param callback Callback to receive the notifications
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    void unsuscribeFrom(NotificationTicket nt, IDevice dev, DeviceStateMetadata state, String[] params, IDeviceStateNotificationCallback callback) throws InternalErrorException;

}

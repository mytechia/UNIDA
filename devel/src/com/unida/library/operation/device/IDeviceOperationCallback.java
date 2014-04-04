/*******************************************************************************
 *   
 *   Copyright (C) 2010 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
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

package com.unida.library.operation.device;

import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.DeviceState;
import com.unida.library.device.ontology.ControlCommandMetadata;
import com.unida.library.device.ontology.ControlFunctionalityMetadata;
import java.util.Collection;

/**
 * <p><b>Description:</b></br>
 * This interface must be implemented by all classes that want to execute
 * asynchronous operations on the devices controlled by the DAL.
 *
 * </p>
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 28-dic-2009<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public interface IDeviceOperationCallback {

    /** Notifies the result of a query device state operation
     *
     * @param dev The device whose state is required
     * @param state The state to be queried
     * @param value The value of the state
     */
    void notifyQueryDeviceStateResult(OperationTicket ticket, IDevice dev, DeviceState state);

    /** Notifies the result of a query device all states operation
     *
     * @param dev The device whose states are required
     * @param value The value of the device's states
     */
    void notifyQueryDeviceStatesResult(OperationTicket ticket, IDevice dev, Collection<DeviceState> states);
    
    /** Notifies the result of a write device state operation
     *
     * @param dev The device whose state is required
     * @param state The state to be queried
     * @param value The value of the state
     */
    void notifyWriteDeviceStateResult(OperationTicket ticket, IDevice dev);

    /** Notifies the states of a device after a send comand operation
     *
     * NOTE: This kind of command is not supported yet
     * 
     * @param dev The device
     * @param cmd The command to execute on the device
     * @param params Paramters of the command
     */
    void notifySendCommandQueryStateResult(OperationTicket ticket, IDevice dev, ControlFunctionalityMetadata func, 
            ControlCommandMetadata cmd, Collection<String> params, Collection<DeviceState> states);

    /**
     *
     * @param ticket
     * @param dev
     * @param cmd
     */
    void notifyCommandExecution(OperationTicket ticket, IDevice dev, ControlFunctionalityMetadata func, ControlCommandMetadata cmd);

    /**
     * 
     * @param ticket
     * @param dev
     * @param failure
     * @param failureDescription
     */
    void notifyOperationFailure(OperationTicket ticket, IDevice dev, OperationFailures failure, String failureDescription);
}

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

import com.unida.library.operation.OperationFailures;
import com.unida.library.operation.OperationTicket;
import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.state.DeviceState;
import com.unida.library.device.ontology.metadata.ControlCommandMetadata;
import com.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import java.util.Collection;

/**
 * <p><b>Description:</b></p>
 * This interface must be implemented by all classes that want to execute
 * asynchronous operations on the devices controlled by the UniDA library.
 *
 *
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public interface IDeviceOperationCallback 
{

    /** Notifies the result of a query device state operation
     *
     * @param ticket
     * @param dev The device whose state is required
     * @param state The state to be queried     
     */
    void notifyQueryDeviceStateResult(OperationTicket ticket, IDevice dev, DeviceState state);

    /** Notifies the result of a query device all states operation
     *
     * @param ticket
     * @param dev The device whose states are required
     * @param states The value of the device's states
     */
    void notifyQueryDeviceStatesResult(OperationTicket ticket, IDevice dev, Collection<DeviceState> states);
    
    /** Notifies the result of a write device state operation
     *
     * @param ticket
     * @param dev The device whose state was written
     */
    void notifyWriteDeviceStateResult(OperationTicket ticket, IDevice dev);

    /** Notifies the states of a device after a send comand operation
     *
     * NOTE: This kind of command is not supported yet
     * 
     * @param ticket
     * @param dev The device
     * @param func The functionality where this command is classified
     * @param cmd The command to execute on the device
     * @param params Paramters of the command
     * @param states The value of the device states after the command is executed
     */
    void notifySendCommandQueryStateResult(OperationTicket ticket, IDevice dev, ControlFunctionalityMetadata func, 
            ControlCommandMetadata cmd, Collection<String> params, Collection<DeviceState> states);

    /** Notifies the result of a command operation
     *
     * @param ticket
     * @param dev The device
     * @param func The functionality where the command belongs
     * @param cmd The command
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

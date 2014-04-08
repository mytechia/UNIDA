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

import com.unida.library.operation.OperationFailures;
import com.unida.library.operation.OperationTicket;
import com.unida.library.operation.OperationTypes;
import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.DeviceState;
import com.unida.library.device.ontology.ControlCommandMetadata;
import com.unida.library.device.ontology.DeviceStateMetadata;
import com.unida.library.device.ontology.DeviceStateValue;
import com.unida.library.device.DeviceID;
import com.unida.library.device.ontology.ControlFunctionalityMetadata;
import java.util.ArrayList;

/**
 * <p><b>
 * Implementation of the IOperationInternalCallback that translates it to a
 * IDeviceOperationCallback in order to export the UniDA facade throught the
 * higher level IDeviceOperationFacade. It uses OperationTicket objects to match
 * operation responses with operation requests.
 * </b>
 * </p>
 *
 * <p><b>Creation date:</b> 18-01-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 18-01-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class DefaultDeviceAccessLayerCallback implements IOperationInternalCallback
{

    private OperationTicket ticket;
    private IDevice device;
    private DeviceStateMetadata state;
    private ControlCommandMetadata command;
    private ControlFunctionalityMetadata functionality;
    private IDeviceOperationCallback operationCallback;
    private long requestTime;
    private DefaultDeviceOperationFacade deviceOpFacade;

    private DefaultDeviceAccessLayerCallback(DefaultDeviceOperationFacade deviceOpFacade)
    {
        this.deviceOpFacade = deviceOpFacade;
        this.requestTime = System.currentTimeMillis();
    }

    public DefaultDeviceAccessLayerCallback(DefaultDeviceOperationFacade deviceOpFacade, OperationTicket ticket, IDevice device, IDeviceOperationCallback operationCallback)
    {
        this(deviceOpFacade);
        this.ticket = ticket;
        this.device = device;
        this.operationCallback = operationCallback;
    }

    public DefaultDeviceAccessLayerCallback(DefaultDeviceOperationFacade deviceOpFacade, OperationTicket ticket, IDevice device, DeviceStateMetadata state, IDeviceOperationCallback operationCallback)
    {
        this(deviceOpFacade);
        this.ticket = ticket;
        this.device = device;
        this.state = state;
        this.operationCallback = operationCallback;
    }

    public DefaultDeviceAccessLayerCallback(DefaultDeviceOperationFacade deviceOpFacade, OperationTicket ticket,
            IDevice device, ControlFunctionalityMetadata functionality,
            ControlCommandMetadata command, IDeviceOperationCallback operationCallback)
    {
        this(deviceOpFacade);
        this.ticket = ticket;
        this.device = device;
        this.functionality = functionality;
        this.command = command;
        this.operationCallback = operationCallback;
    }

    public OperationTicket getTicket()
    {
        return this.ticket;
    }

    public ControlCommandMetadata getCommand()
    {
        return command;
    }

    public ControlFunctionalityMetadata getFunctionality()
    {
        return functionality;
    }

    public IDevice getDevice()
    {
        return device;
    }

    public IDeviceOperationCallback getOperationCallback()
    {
        return operationCallback;
    }

    public DeviceStateMetadata getState()
    {
        return state;
    }

    public long getRequestTime()
    {
        return this.requestTime;
    }

    private void finishCallback()
    {
        this.deviceOpFacade.removeCallback(this);
    }

    @Override
    public void notifyDeviceState(long opId, DeviceID deviceId, String stateId, String valueId, String value)
    {
        if (isThisOperation(opId, deviceId) && stateId.equals(state.getId()))
        {
            DeviceState devState = new DeviceState(this.state, new DeviceStateValue(valueId, value));
            if (this.operationCallback != null)
            {
                this.operationCallback.notifyQueryDeviceStateResult(this.ticket, this.device, devState);
            }
            finishCallback();
        }
    }

    @Override
    public void notifyDeviceStates(long opId, DeviceID deviceId, String[] stateIds, String[] valuesIds, String[] values)
    {
        if (isThisOperation(opId, deviceId))
        {
            DeviceStateMetadata[] stateList = this.device.getDeviceClass().getStates();
            ArrayList<DeviceState> resultStates = new ArrayList<>(stateList.length);
            for (DeviceStateMetadata stateList1 : stateList)
            {
                for (int j = 0; j < stateIds.length; j++)
                {
                    if (stateIds[j].equals(stateList1.getId()) || stateIds[j].contains(stateList1.getId()))
                    {
                        resultStates.add(new DeviceState(stateList1, new DeviceStateValue(valuesIds[j], values[j])));
                    }
                }
            }
            if (this.operationCallback != null)
            {
                this.operationCallback.notifyQueryDeviceStatesResult(this.ticket, this.device, resultStates);
            }
            finishCallback();
        }
    }

    @Override
    public void notifyWriteDeviceState(long opId, DeviceID deviceId)
    {
        if (isThisOperation(opId, deviceId))
        {
            if (this.operationCallback != null)
            {
                this.operationCallback.notifyWriteDeviceStateResult(this.ticket, this.device);
            }
            finishCallback();
        }
    }

    @Override
    public void notifyExpiration(long opId)
    {
        if (ticket.getId() == opId)
        {
            finishCallback();

            if (this.operationCallback != null)
            {
                this.operationCallback.notifyOperationFailure(
                        this.ticket,
                        this.device,
                        OperationFailures.RESPONSE_EXPIRATION,
                        "The operation wait time has expired.");
            }
        }
    }

    @Override
    public void notifyDeviceFailure(long opId, DeviceID deviceId, String failureId)
    {
        if (isThisOperation(opId, deviceId))
        {
            if (this.operationCallback != null)
            {
                //TODO -> include the definition of device errors in the ontology
                this.operationCallback.notifyOperationFailure(ticket, device, OperationFailures.OPERATION_ERROR, failureId);
            }
            finishCallback();
        }
    }

    @Override
    public void notifyCommandExecution(long odId, DeviceID deviceId, String functionalityId, String commandId)
    {
        if (isThisOperation(odId, deviceId))
        {
            if (null != this.operationCallback)
            {
                if (this.ticket.getType() == OperationTypes.SEND_COMMAND)
                {
                    this.operationCallback.notifyCommandExecution(ticket, device, functionality, command);
                } 
                //OperationTypes.SEND_COMMAND_QUERY_STATE -> are processed as responses to query state operations
            }
            
            finishCallback();
        }
    }

    private boolean isThisOperation(long opId, DeviceID deviceId)
    {
        return (ticket.getId() == opId) && deviceId.equals(this.device.getId());
    }
}

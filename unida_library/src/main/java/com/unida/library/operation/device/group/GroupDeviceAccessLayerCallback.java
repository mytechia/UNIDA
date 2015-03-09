/*******************************************************************************
 *   
 *   Copyright (C) 2010,2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010,2013 Gervasio Varela <gervarela@picandocodigo.com>
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

package com.unida.library.operation.device.group;

import com.unida.library.device.DeviceGroup;
import com.unida.library.device.DeviceID;
import com.unida.library.device.PhysicalDevice;
import com.unida.library.device.ontology.metadata.ControlCommandMetadata;
import com.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.unida.library.device.ontology.state.DeviceStateValue;
import com.unida.library.operation.OperationFailures;
import com.unida.library.operation.OperationTicket;
import com.unida.library.operation.device.IDeviceOperationCallback;
import com.unida.library.operation.device.IOperationInternalCallback;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;


/**
 * <p><b>
 * Implementation of the UniDACallback that translates it to a
 * IDeviceOperationCallback in order to export the UniDA facade throught
 * the higher level IDeviceOperationFacade.
 * It uses OperationTicket objects to match operation responses with
 * operation requests.
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 18-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>2 - 5-feb-2013 Update to support the new style of device IDs</li>
 * <li>1 - 18-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class GroupDeviceAccessLayerCallback implements IOperationInternalCallback
{

    private OperationTicket ticket;
    private DeviceGroup group;
    private Queue<DeviceID> memberIds;
    private DeviceStateMetadata state;
    private ControlFunctionalityMetadata functionality;
    private ControlCommandMetadata command;
    private IDeviceOperationCallback operationCallback;
    private long requestTime;


    private GroupDeviceAccessLayerCallback()
    {
        this.requestTime = System.currentTimeMillis();
    }
    

    public GroupDeviceAccessLayerCallback(
            OperationTicket ticket, DeviceGroup group,
            Collection<PhysicalDevice> members, IDeviceOperationCallback operationCallback)
    {
        this();
        this.ticket = ticket;
        this.group = group;
        this.memberIds = new ArrayDeque<>(members.size());
        for(PhysicalDevice d : members) {
            this.memberIds.add(d.getId());
        }
        this.operationCallback = operationCallback;
    }


    public GroupDeviceAccessLayerCallback(
            OperationTicket ticket, DeviceGroup group,
            Collection<PhysicalDevice> members, 
            ControlFunctionalityMetadata functionality,
            ControlCommandMetadata command,
            IDeviceOperationCallback operationCallback)
    {
        this(ticket, group, members, operationCallback);
        this.operationCallback = operationCallback;
    }


    public OperationTicket getTicket()
    {
        return this.ticket;
    }

    
    public ControlFunctionalityMetadata getFunctionality()
    {
        return functionality;
    }
    

    public ControlCommandMetadata getCommand()
    {
        return command;
    }


    public DeviceGroup getDeviceGroup()
    {
        return group;
    }


    public Collection<DeviceID> getGroupMembers()
    {
        return memberIds;
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


    @Override
    public void notifyDeviceState(long opId, DeviceID deviceId, String stateId, DeviceStateValue stateValue)
    {
//        if (isThisOperation(opId, deviceId) && stateId.equals(state.getId())) {
//            DeviceState devState = new DeviceState(this.state, new DeviceStateValue(valueId, value));
//            this.operationCallback.notifyQueryDeviceStatesResult(this.ticket, this.device, devState);
//        }
    }


    @Override
    public void notifyDeviceStates(long opId, DeviceID deviceId, String[] stateIds, DeviceStateValue[] stateValues)
    {
//        if (isThisOperation(opId, deviceId)) {
//            DeviceState [] stateList = this.device.getStates();
//            ArrayList<DeviceState> resultStates = new ArrayList<DeviceState>(stateList.length);
//            for(int i=0; i<stateList.length; i++) {
//                for(int j=0; j<stateIds.length; j++) {
//                    if (stateIds[j].equals(stateList[i].getId())) {
//                        resultStates.add(new DeviceState(
//                                stateList[i].getMetadata(),
//                                new DeviceStateValue(valuesIds[j], values[j])));
//                    }
//                }
//            }
//            this.operationCallback.notifyQueryDeviceStatesResult(this.ticket, this.device, resultStates);
//        }
    }


    @Override
    public void notifyExpiration(long opId)
    {
        if (ticket.getId() == opId) {
            this.operationCallback.notifyOperationFailure(
                    this.ticket,
                    this.group,
                    OperationFailures.RESPONSE_EXPIRATION,
                    "The operation wait time has expired.");
        }
    }


    @Override
    public void notifyDeviceFailure(long opId, DeviceID deviceId, String failureId)
    {
        if (isThisOperation(opId, deviceId)) {
            //TODO -> include the definition of device errors in the ontology
            //TODO -> cancel the command in the previous devices?
            this.operationCallback.notifyOperationFailure(ticket, group, OperationFailures.OPERATION_ERROR, failureId);
        }
    }


    @Override
    public void notifyCommandExecution(long odId, DeviceID deviceId, String functionalityId, String commandId)
    {
        if (odId == this.ticket.getId()) {
            this.memberIds.remove(deviceId);
            if (this.memberIds.isEmpty()) {
                this.operationCallback.notifyCommandExecution(ticket, group, functionality, command);
            }
        }
    }


    private boolean isThisOperation(long opId, DeviceID deviceId)
    {
        return (ticket.getId() == opId) && deviceId.equals(this.group.getId());
    }

    @Override
    public void notifyWriteDeviceState(long opId, DeviceID deviceId)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2013 Gervasio Varela <gervarela@picandocodigo.com>
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


import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.unida.library.UniDAFactory;
import com.unida.library.core.IUniDANetworkFacade;
import com.unida.library.device.ontology.ControlCommandMetadata;
import com.unida.library.device.ontology.DeviceStateMetadata;
import com.unida.library.device.DeviceGroup;
import com.unida.library.device.Gateway;
import com.unida.library.device.IDevice;
import com.unida.library.device.PhysicalDevice;
import com.unida.library.device.ontology.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.DeviceStateValue;
import com.unida.library.manage.IUniDAManagementFacade;
import com.unida.library.operation.device.IDeviceOperationCallback;
import com.unida.library.operation.OperationTicket;
import java.util.Collection;
import java.util.LinkedList;

/**
 * <p><b>
 * Manages the execution of commands and queries over groups of devices.
 * </b></br>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 18-01-2010</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 18-01-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class DefaultGroupOperationManager implements IGroupOperationManager
{


    private UniDAFactory dalFactory;

    private IUniDAManagementFacade deviceManager;


    public DefaultGroupOperationManager(UniDAFactory dalFactory, IUniDAManagementFacade deviceManager)
    {
        this.dalFactory = dalFactory;
        this.deviceManager = deviceManager;
    }

    

    @Override
    public OperationTicket sendCommand(
            OperationTicket ot, DeviceGroup group, ControlFunctionalityMetadata func,
            ControlCommandMetadata cmd, String[] params, IDeviceOperationCallback callback)
            throws InternalErrorException
    {

        try {

            //get only the members that are real physical devices
            LinkedList<PhysicalDevice> members = getAllPhysicalMembers(group);

            GroupDeviceAccessLayerCallback dalCback =
                    new GroupDeviceAccessLayerCallback(ot, group, members, func, cmd, callback);

            for(PhysicalDevice pd : members) {
                Gateway devGw = this.deviceManager.findDeviceGatewayByCodId(pd.getCodId());
                IUniDANetworkFacade dalInstance = this.dalFactory.getDALInstance(devGw);                
                dalInstance.sendCommand(ot.getId(), pd.getId(), func.getId(), cmd.getId(), params, dalCback);
            }
            
            return ot;
        }
        catch (InstanceNotFoundException ex) {
            throw new InternalErrorException(ex);
        }

    }


    private LinkedList<PhysicalDevice> getAllPhysicalMembers(DeviceGroup group)
            throws InternalErrorException, InstanceNotFoundException
    {

        LinkedList<PhysicalDevice> res = new LinkedList<>();

        Collection<IDevice> members = this.deviceManager.findGroupMembers(group);

        for(IDevice m : members) {
            if (m.isGroup()) {
                res.addAll(getAllPhysicalMembers((DeviceGroup) m));
            }
            else {
                res.add((PhysicalDevice) m);
            }
        }

        return res;
        
    }


    @Override
    public OperationTicket asyncQueryDeviceState(OperationTicket ticket, IDevice dev, DeviceStateMetadata state, IDeviceOperationCallback callback)
            throws InternalErrorException
    {
        throw new InternalErrorException(new UnsupportedOperationException("Not supported yet."));
    }


    @Override
    public OperationTicket asyncQueryDeviceStates(OperationTicket ticket, IDevice dev, IDeviceOperationCallback callback)
            throws InternalErrorException
    {
        throw new InternalErrorException(new UnsupportedOperationException("Not supported yet."));
    }

    @Override
    public OperationTicket asyncWriteDeviceState(OperationTicket ticket, IDevice dev, DeviceStateMetadata state, DeviceStateValue stateValue, IDeviceOperationCallback callback) throws InternalErrorException
    {
        throw new InternalErrorException(new UnsupportedOperationException("Not supported yet."));
    }
	

	
	
}
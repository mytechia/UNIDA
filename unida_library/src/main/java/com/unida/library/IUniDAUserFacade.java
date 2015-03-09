/*******************************************************************************
 *   
 *   Copyright (C) 2014 
 *   Copyright 2014 Victor Sonora Pombo
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

package com.unida.library;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.unida.library.device.Gateway;
import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.metadata.DeviceClassMetadata;
import com.unida.library.device.ontology.metadata.GatewayClassMetadata;
import com.unida.library.location.Location;
import com.unida.library.manage.IGatewayDiscoveryListener;
import java.util.Collection;

/**
 * <p>
 * <b>Description:</b>
 *  Interface with methods to find UniDA gateways and devices.
 *
 *
 * <p><b>Creation date:</b> 
 * 05-12-2014 </p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li> 1 , 05-12-2014 - Initial release</li>
 * </ul>
 *
 * @author Victor Sonora Pombo
 * @version 1
 */
public interface IUniDAUserFacade 
{

    /**
     * Finds a device by its id
     *
     * @param id The id of the device
     * @return The device with the especified id
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     * @throws
     * com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException
     */
    IDevice findById(String id) throws InternalErrorException, InstanceNotFoundException;

    /**
     *
     * @param deviceIdList
     * @return
     * @throws InternalErrorException
     * @throws InstanceNotFoundException
     */
    Collection<IDevice> findById(Collection<String> deviceIdList)
            throws InternalErrorException, InstanceNotFoundException;
    
    /**
     * Gets all the devices managed by the UniDA by using indexes to navigate the
     * device collection.
     *
     * @param startIndex First device to retrieve
     * @param length Number of devices to retrieve
     * @return the devices found
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    Collection<IDevice> findAll(int startIndex, int length) throws InternalErrorException;

    /**
     * Gets all the devices managed by the UniDA that aren't configured yet by
     * using indexes to navigate the device collection.
     *
     * @param startIndex First device to retrieve
     * @param length Number of devices to retrieve
     * @return the devices found
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    Collection<IDevice> findNotConfiguredDevices(int startIndex, int length) throws InternalErrorException;

    /**
     * Gets the devices managed by the UniDA that are at the especified location
     * by using indexes to navigate the device collection.
     *
     * @param startIndex First device to retrieve
     * @param length Number of devices to retrieve
     * @param location The location of the device to retrieve
     * @return the devices found with the especified location
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    Collection<IDevice> findByLocation(int startIndex, int length, Location location) throws InternalErrorException;

    /**
     * Finds a device by its functionality
     *
     * @param startIndex First device to retrieve
     * @param length Number of devices to retrieve
     * @param functionality Functionality to look for
     * @return the devices found with the especified functionality
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    Collection<IDevice> findByFunctionality(int startIndex, int length, ControlFunctionalityMetadata functionality) throws InternalErrorException;

    /**
     * Find devices by the gateway to wich they are associated
     *
     * @param devGw
     * @return
     * @throws InstanceNotFoundException
     * @throws InternalErrorException
     */
    Collection<IDevice> findByDeviceGateway(Gateway devGw) throws InstanceNotFoundException, InternalErrorException;

    Collection<IDevice> findNotEnabledDevices(DeviceClassMetadata devClass, Location loc, int startIndex, int length) throws InternalErrorException;
    
    /**
     * Find device gateway
     *
     * @param id
     * @return
     * @throws InternalErrorException
     * @throws InstanceNotFoundException
     */
    Gateway findDeviceGatewayById(String id) throws InternalErrorException, InstanceNotFoundException;

    /**
     * Retrieves all devices gateways
     *
     * @param startIndex
     * @param length
     * @return
     * @throws InternalErrorException
     */
    Collection<Gateway> findAllDeviceGateways(int startIndex, int length) throws InternalErrorException;

    /**
     * Retrieves device gateways by its class
     *
     * @param gcm
     * @param startIndex
     * @param length
     * @return
     * @throws InternalErrorException
     */
    Collection<Gateway> findDeviceGatewaysByClassId(GatewayClassMetadata gcm, int startIndex, int length) throws InternalErrorException;
        
    /**
     *  Register a gateway detected listener
     * 
     * @param listener 
     */
    void addGatewayDiscoveryListener(IGatewayDiscoveryListener listener);
    
}

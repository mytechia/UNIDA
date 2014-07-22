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
package com.unida.library.manage;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.metadata.DeviceClassMetadata;
import com.unida.library.device.ontology.metadata.GatewayClassMetadata;
import com.unida.library.device.DeviceGroup;
import com.unida.library.device.Gateway;
import com.unida.library.device.GatewayDevices;
import com.unida.library.device.IDevice;
import com.unida.library.device.IDeviceIO;
import com.unida.library.location.Location;
import com.unida.library.manage.exception.IncompatibleDevicesErrorException;
import java.util.Collection;

/**
 * <p>
 * <b>Description:</b>
 * Interface to manage the devices controlled by the UniDA library. It provides
 * support to add, remove and edit device and groups of devices.
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
public interface IUniDAManagementFacade
{

    /**
     * Adds a new device to the DAL
     *
     * @param dev The device to add
     * @return
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    IDevice addNewDevice(IDevice dev) throws InternalErrorException;

    /**
     * Adds a new device to the DAL, associating it to a device IO of a gateway
     *
     * @param dev The device to add
     * @param devIO
     * @return
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    IDevice addNewDevice(IDevice dev, IDeviceIO devIO) throws InternalErrorException;

    /**
     * Removes a device from the DAL
     *
     * @param dev The device to remove
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     * @throws
     * com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException
     */
    void removeDevice(IDevice dev) throws InternalErrorException, InstanceNotFoundException;

    /**
     * Edits a device
     *
     * @param dev The device to edit
     * @throws
     * com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    void editDevice(IDevice dev) throws InstanceNotFoundException, InternalErrorException;

    /**
     * Adds a member to a device group
     *
     * @param dev The device to add to the group
     * @param group The group to add the device to
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     * @throws
     * com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException
     */
    void addMember(IDevice dev, DeviceGroup group) throws InternalErrorException, InstanceNotFoundException;

    /**
     * Removes a member from a device
     *
     * @param dev The device to remove from the group
     * @param group The group to remove the device from
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     * @throws
     * com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException
     */
    void removeMember(IDevice dev, DeviceGroup group) throws InternalErrorException, InstanceNotFoundException;

    Collection<IDevice> findGroupMembers(DeviceGroup group) throws InternalErrorException, InstanceNotFoundException;

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
     * Finds a device by its internal cod id
     *
     * @param codId The id of the device
     * @return The device with the especified id
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     * @throws
     * com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException
     */
    IDevice findByCodId(Long codId) throws InternalErrorException, InstanceNotFoundException;

    /**
     * Gets all the devices managed by the DAL by using indexes to navigate the
     * device collection.
     *
     * @param startIndex First device to retrieve
     * @param length Number of devices to retrieve
     * @return the devices found
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    Collection<IDevice> findAll(int startIndex, int length) throws InternalErrorException;

    /**
     * Gets all the devices managed by the DAL that aren't configured yet by
     * using indexes to navigate the device collection.
     *
     * @param startIndex First device to retrieve
     * @param length Number of devices to retrieve
     * @return the devices found
     * @throws com.mytechia.commons.framework.exception.InternalErrorException
     */
    Collection<IDevice> findNotConfiguredDevices(int startIndex, int length) throws InternalErrorException;

    /**
     * Gets the devices managed by the DAL that are at the especified location
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
     * Adds a new device gateway
     *
     * @param devGw
     * @return
     * @throws InternalErrorException
     */
    Gateway addNewDeviceGateway(Gateway devGw) throws InternalErrorException;

    /**
     * Removes a device gateway
     *
     * @param devGw
     * @throws InternalErrorException
     * @throws InstanceNotFoundException
     */
    void removeDeviceGateway(Gateway devGw) throws InternalErrorException, InstanceNotFoundException;

    /**
     * Updates a device gateway
     *
     * @param devGw
     * @throws InternalErrorException
     * @throws InstanceNotFoundException
     */
    void editDeviceGateway(Gateway devGw) throws InternalErrorException, InstanceNotFoundException;

    /**
     * Retrieves a device gateway
     *
     * @param codId
     * @return
     * @throws InternalErrorException
     * @throws InstanceNotFoundException
     */
    Gateway findDeviceGatewayByCodId(Long codId) throws InternalErrorException, InstanceNotFoundException;

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
     * Associates a previously created device with a device IO in a device
     * gateway. In case that the device is moved from another IO, the source IO
     * is disconnected from it.
     *
     * If 'deleteDestionation' is false, in case that the destination IO has a
     * previously associated device, it is changed to not-enabled state,
     * otherwise it is deleted.
     *
     * @param device
     * @param deviceIO
     * @param deleteDestination
     * @throws InstanceNotFoundException
     * @throws InternalErrorException
     * @throws
     * com.unida.library.manage.exception.IncompatibleDevicesErrorException
     */
    void associateDevice2DeviceIO(IDevice device, IDeviceIO deviceIO, boolean deleteDestination)
            throws InstanceNotFoundException, InternalErrorException, IncompatibleDevicesErrorException;

    void deassociateDeviceFromDeviceIO(IDevice device, IDeviceIO deviceIO) throws InstanceNotFoundException, InternalErrorException;

    /**
     * Adds a location
     *
     * @param loc
     * @return
     * @throws InternalErrorException
     */
    Location addLocation(Location loc) throws InternalErrorException;

    /**
     * Updates a location
     *
     * @param loc
     * @throws InstanceNotFoundException
     * @throws InternalErrorException
     */
    void editLocation(Location loc) throws InstanceNotFoundException, InternalErrorException;

    /**
     * Removes a location
     *
     * @param loc
     * @throws InstanceNotFoundException
     * @throws InternalErrorException
     */
    void removeLocation(Location loc) throws InstanceNotFoundException, InternalErrorException;

    /**
     * Retrieves a location by its id
     *
     * @param id
     * @return
     * @throws InstanceNotFoundException
     * @throws InternalErrorException
     */
    Location findLocationById(Long id) throws InstanceNotFoundException, InternalErrorException;

    /**
     * Retrieves all locations
     *
     * @return
     * @throws InternalErrorException
     */
    Collection<Location> findAllLocations() throws InternalErrorException;

    /**
     * It is called when a gateway is discovered in the device network. If the
     * gateway was not previously known, it creates a new gateway and its
     * associated devices. If it was known, it updates the gateway and its
     * devices.
     *
     * @param devGw
     * @return
     * @throws InternalErrorException
     */
    Gateway newGatewayDiscovered(Gateway devGw)
            throws InternalErrorException;

    /**
     * Marks a device gateway and its associated devices as been lost because we
     * are not receiving the periodic annoucement messages from the gateway.
     *
     * @param devGw
     * @throws InternalErrorException
     */
    void markDeviceGatewayAsLost(Gateway devGw) throws InternalErrorException;

    /**
     * Retrieves all the device gateways and its associated devices.
     *
     * @param startIndex
     * @param lenght
     * @return
     * @throws InternalErrorException
     */
    Collection<GatewayDevices> findAllDeviceGatewayDevices(int startIndex, int lenght) throws InternalErrorException;

    Collection<GatewayDevices> findDeviceGatewayDevicesByFilters(
            int startIndex, int count,
            Location gwLoc, Location devLoc, String gwType, String devClass,
            Boolean configured, Boolean enabled, Boolean automatic)
            throws InternalErrorException;

    /**
     * Retrieves the device gateways that have the indicated location and its
     * associated devices
     *
     * @param startIndex
     * @param lenght
     * @param lcoation
     * @return
     * @throws InternalErrorException
     */
    Collection<GatewayDevices> findDeviceGatewayDevicesByGatewayLocation(int startIndex, int lenght, Location lcoation) throws InternalErrorException;

    /**
     * Retrieves the device that have the indicated location and its devices
     * gateways
     *
     * @param startIndex
     * @param lenght
     * @param lcoation
     * @return
     * @throws InternalErrorException
     */
    Collection<GatewayDevices> findDeviceGatewayDevicesByDeviceLocation(int startIndex, int lenght, Location lcoation) throws InternalErrorException;

    /**
     * Retrieves the device gateways of an specified type and its devices
     *
     * @param startIndex
     * @param lenght
     * @param type
     * @return
     * @throws InternalErrorException
     */
    Collection<GatewayDevices> findDeviceGatewayDevicesByGatewayType(int startIndex, int lenght, GatewayClassMetadata type) throws InternalErrorException;

    /**
     * Retrieves the devices of an specified class and its device gateways
     *
     * @param startIndex
     * @param lenght
     * @param devClass
     * @return
     * @throws InternalErrorException
     */
    Collection<GatewayDevices> findDeviceGatewayDevicesByDeviceClass(int startIndex, int lenght, DeviceClassMetadata devClass) throws InternalErrorException;

    /**
     * Retrieves the device that aren't configured yet and its device gateways
     *
     * @param startIndex
     * @param lenght
     * @return
     * @throws InternalErrorException
     */
    Collection<GatewayDevices> findDeviceGatewayDevicesByNotConfiguredDevices(int startIndex, int lenght) throws InternalErrorException;

}

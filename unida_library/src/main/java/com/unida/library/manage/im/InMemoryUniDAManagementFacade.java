package com.unida.library.manage.im;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.metadata.DeviceClassMetadata;
import com.unida.library.device.ontology.metadata.GatewayClassMetadata;
import com.unida.library.device.DeviceGroup;
import com.unida.library.device.Gateway;
import com.unida.library.device.IDevice;
import com.unida.library.device.IDeviceIO;
import com.unida.protocol.UniDAAddress;
import com.unida.library.device.state.OperationalState;
import com.unida.library.device.state.OperationalStatesEnum;
import com.unida.library.location.Location;
import com.unida.library.manage.IGatewayDiscoveryListener;
import com.unida.library.manage.IUniDAManagementFacade;
import com.unida.library.manage.exception.IncompatibleDevicesErrorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * <p>
 * <b>
 * In memory implementation of the UniDA management operations.
 * </b>
 *
 *
 *
 * <p>
 * <b>Creation date:</b> 8-feb-2013</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>1 - 8-feb-2013 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela, Alejandro Paz
 * @version 1
 */
public class InMemoryUniDAManagementFacade implements IUniDAManagementFacade
{

    private static final String NOT_SUPPORTED_MSG = "The In-Memory implementation does not support this feature.";
    private static final String NOT_SUPPORTED_YET = "This feature is not supported yet.";

    private HashMap<UniDAAddress, Gateway> gatewayMap;
    private Collection<IGatewayDiscoveryListener> gatewayDiscoveryListeners;

    public InMemoryUniDAManagementFacade()
    {
        this.gatewayMap = new HashMap<>();
        this.gatewayDiscoveryListeners = new ArrayList<>();
    }

    @Override
    public synchronized IDevice addNewDevice(IDevice dev) throws InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized IDevice addNewDevice(IDevice dev, IDeviceIO devIO) throws InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized void removeDevice(IDevice dev) throws InternalErrorException, InstanceNotFoundException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized void editDevice(IDevice dev) throws InstanceNotFoundException, InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized void addMember(IDevice dev, DeviceGroup group) throws InternalErrorException, InstanceNotFoundException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    @Override
    public synchronized void removeMember(IDevice dev, DeviceGroup group) throws InternalErrorException, InstanceNotFoundException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    @Override
    public synchronized Collection<IDevice> findGroupMembers(DeviceGroup group) throws InternalErrorException, InstanceNotFoundException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    @Override
    public synchronized IDevice findById(String id) throws InternalErrorException, InstanceNotFoundException
    {
        for (IDevice dev : findAll(0, Integer.MAX_VALUE))
        {
            if (dev.getId().toString().equals(id))
            {
                return dev;
            }
        }

        throw new InstanceNotFoundException(id, IDevice.class.getSimpleName());
    }

    @Override
    public synchronized Collection<IDevice> findById(Collection<String> deviceIdList) throws InternalErrorException, InstanceNotFoundException
    {
        ArrayList<IDevice> devList = new ArrayList<>(deviceIdList.size());
        for (String devId : deviceIdList)
        {
            devList.add(findById(devId));
        }
        return devList;
    }

    @Override
    public synchronized IDevice findByCodId(Long codId) throws InternalErrorException, InstanceNotFoundException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized Collection<IDevice> findAll(int startIndex, int length) throws InternalErrorException
    {
        Collection<IDevice> devices = new ArrayList<>();
        for (Gateway gw : this.gatewayMap.values())
        {
            devices.addAll(gw.getDevices());
        }

        return devices;
    }

    @Override
    public synchronized Collection<IDevice> findNotConfiguredDevices(int startIndex, int length) throws InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized Collection<IDevice> findByLocation(int startIndex, int length, Location location) throws InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized Collection<IDevice> findByFunctionality(int startIndex, int length, ControlFunctionalityMetadata functionality) throws InternalErrorException
    {
        ArrayList<IDevice> result = new ArrayList<>();
        for (IDevice dev : findAll(0, Integer.MAX_VALUE))
        {
            if (Arrays.asList(dev.getDeviceClass().getControlFunctionalities()).contains(functionality))
            {
                result.add(dev);
            }
        }

        return result;
    }

    @Override
    public synchronized Collection<IDevice> findByDeviceGateway(Gateway devGw) throws InstanceNotFoundException, InternalErrorException
    {
        return new ArrayList<>(devGw.getDevices());
    }

    @Override
    public synchronized Collection<IDevice> findNotEnabledDevices(DeviceClassMetadata devClass, Location loc, int startIndex, int length) throws InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized Gateway addNewDeviceGateway(Gateway devGw) throws InternalErrorException
    {
        this.gatewayMap.put(devGw.getId(), devGw);
        return devGw;
    }

    @Override
    public synchronized void removeDeviceGateway(Gateway devGw) throws InternalErrorException, InstanceNotFoundException
    {
        this.gatewayMap.remove(devGw.getId());
    }

    @Override
    public synchronized void editDeviceGateway(Gateway devGw) throws InternalErrorException, InstanceNotFoundException
    {
        this.gatewayMap.remove(devGw.getId());
        this.gatewayMap.put(devGw.getId(), devGw);
    }

    @Override
    public synchronized Gateway findDeviceGatewayByCodId(Long codId) throws InternalErrorException, InstanceNotFoundException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized Gateway findDeviceGatewayById(String id) throws InternalErrorException, InstanceNotFoundException
    {

        for (Gateway gw : this.gatewayMap.values())
        {
            if (gw.getId().toString().equals(id))
            {
                return gw;
            }
        }

        throw new InstanceNotFoundException(id, Gateway.class.getSimpleName());

    }

    /**
     *
     * @param startIndex Index starts in 1.
     * @param length If length is 0 the metoth returns all values.
     * @return
     * @throws InternalErrorException
     */
    @Override
    public synchronized Collection<Gateway> findAllDeviceGateways(int startIndex, int length) throws InternalErrorException
    {
        //Index starts in 1
        startIndex--;

        if (length <= 0)
        {
            return new ArrayList<>(this.gatewayMap.values());
        } else
        {
            Collection<Gateway> gateways = new ArrayList();
            int i = 0;
            for (Gateway g : this.gatewayMap.values())
            {
                if ((i >= (startIndex)) && (i < startIndex + length))
                {
                    gateways.add(g);
                } else if (i >= startIndex + length)
                {
                    break;
                }
            }
            return gateways;
        }
    }

    @Override
    public synchronized Collection<Gateway> findDeviceGatewaysByClassId(GatewayClassMetadata gcm, int startIndex, int length) throws InternalErrorException
    {
        ArrayList<Gateway> gwList = new ArrayList<>();
        for (Gateway gw : this.gatewayMap.values())
        {
            if (gw.getType().equals(gcm))
            {
                gwList.add(gw);
            }
        }

        return gwList;
    }

    @Override
    public synchronized void associateDevice2DeviceIO(IDevice device, IDeviceIO deviceIO, boolean deleteDestination) throws InstanceNotFoundException, InternalErrorException, IncompatibleDevicesErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized void deassociateDeviceFromDeviceIO(IDevice device, IDeviceIO deviceIO) throws InstanceNotFoundException, InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized Location addLocation(Location loc) throws InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized void editLocation(Location loc) throws InstanceNotFoundException, InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized void removeLocation(Location loc) throws InstanceNotFoundException, InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized Location findLocationById(Long id) throws InstanceNotFoundException, InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized Collection<Location> findAllLocations() throws InternalErrorException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized Gateway newGatewayDiscovered(Gateway devGw) throws InternalErrorException
    {
        try
        {
            editDeviceGateway(devGw);
            for (IGatewayDiscoveryListener listener : this.gatewayDiscoveryListeners)
            {
                listener.notifyGatewayDiscovered(devGw);
            }
        } catch (InstanceNotFoundException ex)
        {
            throw new InternalErrorException(ex);
        }

        return devGw;
    }

    @Override
    public synchronized void markDeviceGatewayAsLost(Gateway devGw) throws InternalErrorException
    {
        this.gatewayMap.get(devGw.getId()).setOperationalState(new OperationalState(OperationalStatesEnum.UNKNOWN));
        for (IGatewayDiscoveryListener listener : this.gatewayDiscoveryListeners)
        {
            listener.notifyGatewayLost(devGw);
        }
    }

    @Override
    public void addGatewayDiscoveryListener(IGatewayDiscoveryListener listener)
    {
        if (!this.gatewayDiscoveryListeners.contains(listener))
        {
            this.gatewayDiscoveryListeners.add(listener);
        }
    }

}

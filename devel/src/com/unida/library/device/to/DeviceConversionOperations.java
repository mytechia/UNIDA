/**
 * *****************************************************************************
 *
 * Copyright (C) 2010,2012 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
 * Copyright (C) 2012 Victor Sonora <victor@vsonora.com>
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
package com.unida.library.device.to;

import com.unida.library.device.ontology.DeviceClassMetadata;
import com.unida.library.device.ontology.DeviceStateMetadata;
import com.unida.library.device.ontology.GatewayClassMetadata;
import com.unida.library.device.ontology.IDeviceAccessLayerOntologyFacade;
import com.unida.library.device.ontology.exception.ClassNotFoundInOntologyException;
import com.unida.library.device.Device;
import com.unida.library.device.DeviceGroup;
import com.unida.library.device.Gateway;
import com.unida.library.device.GatewayDeviceIO;
import com.unida.library.device.GatewayDevices;
import com.unida.library.device.IDevice;
import com.unida.library.device.IDeviceIO;
import com.unida.library.device.PhysicalDevice;
import com.unida.protocol.UniDAAddress;
import com.unida.library.device.exception.UniDAIDFormatException;
import com.unida.library.device.state.OperationalState;
import com.unida.library.device.state.OperationalStatesEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 * Functions to convert from model objects to TO and viceversa
 *
 * </p>
 *
 * <p>
 * <b>Creation date:</b> 11-ene-2010</p>
 *
 * <p>
 * <b>Changelog:</b></br>
 * <ul>
 * <li>1 - 11-ene-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public final class DeviceConversionOperations
{

    private DeviceConversionOperations()
    {
    }

    public static PhysicalDevice createPhysicalDevice(
            DeviceTO devTO, DeviceClassMetadata deviceClass)
    {
        try
        {
            if (!devTO.isGroup())
            {
                PhysicalDevice d = new PhysicalDevice(
                        devTO.getCodId(), new UniDAAddress(devTO.getGatewayID()), devTO.getId(), devTO.getLocation(),
                        devTO.isConfigured(), new OperationalState(devTO.getOperationalState(), devTO.getOperationalStateLastChangeTime()),
                        devTO.isEnabled(), devTO.getDescription(), deviceClass,
                        devTO.getModel(), devTO.getManufacturer(), devTO.isAutomatic());
                return d;
            } else
            {
                return null;
            }
        } catch (UniDAIDFormatException ex)
        {
            return null;
        }
    }

    public static Collection<IDevice> createPhysicalDevices(Collection<DeviceTO> deviceTOList,
            Collection<GatewayDeviceIO> deviceIOTOList, IDeviceAccessLayerOntologyFacade dalOntFacade)
            throws ClassNotFoundInOntologyException
    {
        ArrayList<IDevice> devices = new ArrayList<>(deviceTOList.size());

        HashMap<Short, GatewayDeviceIO> ioMap = new HashMap<>();
        for (GatewayDeviceIO io : deviceIOTOList)
        {
            ioMap.put(io.getId(), io);
        }

        for (DeviceTO devTO : deviceTOList)
        {
            DeviceClassMetadata deviceClass = dalOntFacade.getDeviceClassById(devTO.getDeviceClass());
            PhysicalDevice physicalDevice = createPhysicalDevice(devTO, deviceClass);
            devices.add(physicalDevice);
            for (GatewayDeviceIOTO ioTO : devTO.getConnectedIOs())
            {
                physicalDevice.connectToIO(ioMap.get(ioTO.getId()));
            }
        }

        return devices;
    }

    public static Device createDeviceIO(
            DeviceTO devTO, DeviceClassMetadata deviceClass, DeviceStateMetadata[] supportedStates)
    {
        if (!devTO.isGroup())
        {
            PhysicalDevice d = new PhysicalDevice(
                    devTO.getCodId(), null, devTO.getId(), devTO.getLocation(),
                    devTO.isConfigured(), new OperationalState(OperationalStatesEnum.DISCONNECTED, new Date()),
                    devTO.isEnabled(), devTO.getDescription(), deviceClass,
                    devTO.getModel(), devTO.getManufacturer(), devTO.isAutomatic());
            return d;
        } else
        {
            return null;
        }
    }

    public static DeviceGroup createDeviceGroup(
            DeviceTO devTO, DeviceClassMetadata deviceClass)
    {
        if (devTO.isGroup())
        {
            return new DeviceGroup(
                    devTO.getCodId(), devTO.getId(), devTO.getLocation(),
                    new OperationalState(OperationalStatesEnum.OK, new Date()),
                    devTO.isEnabled(), devTO.getDescription(), deviceClass);
        } else
        {
            return null;
        }
    }

    public static DeviceTO createDeviceTO(PhysicalDevice device)
    {
        return new DeviceTO(
                device.getCodId(), device.getId().getDeviceId(), null, device.getLocation(),
                device.isEnabled(), false, device.isConfigured(), device.isAutomatic(),
                device.getDescription(), device.getModel(),
                device.getManufacturer(),
                device.getDeviceClass().getClassId(),
                device.getOperationalState().getStateValue(), device.getOperationalState().getChangeTime(),
                createDeviceIOTOCollection(device.getConnectedIOs()));
    }

    public static DeviceTO createDeviceTO(PhysicalDevice device, IDeviceIO devIO)
    {

        return new DeviceTO(
                device.getId().getDeviceId(), device.getId().getGatewayId().toString(), device.getLocation(),
                device.isEnabled(), false, device.isConfigured(), device.isAutomatic(),
                device.getDescription(), device.getModel(),
                device.getManufacturer(), device.getDeviceClass().getClassId(),
                device.getOperationalState().getStateValue(), device.getOperationalState().getChangeTime(),
                createDeviceIOTOCollection(device.getConnectedIOs()));

    }

    public static DeviceTO createDeviceTO(DeviceGroup devGroup)
    {
        return new DeviceTO(
                devGroup.getCodId(), devGroup.getId().getDeviceId(), null, devGroup.getLocation(),
                true, true, true, true,
                devGroup.getDescription(), "unida-group", "GII",
                devGroup.getDeviceClass().getClassId(),
                devGroup.getOperationalState().getStateValue(), devGroup.getOperationalState().getChangeTime(), new ArrayList<GatewayDeviceIOTO>(0));
    }

    public static Collection<DeviceTO> createDeviceTOList(Collection<IDevice> devices)
    {
        ArrayList<DeviceTO> deviceTOList = new ArrayList<>(devices.size());

        for (IDevice dev : devices)
        {
            if (dev instanceof PhysicalDevice)
            {
                deviceTOList.add(createDeviceTO((PhysicalDevice) dev));
            } else if (dev instanceof DeviceGroup)
            {
                deviceTOList.add(createDeviceTO((DeviceGroup) dev));
            }
        }

        return deviceTOList;
    }

    public static Gateway createDeviceGateway(
            GatewayTO devGwTO, IDeviceAccessLayerOntologyFacade dalOntFacade) throws ClassNotFoundInOntologyException, UniDAIDFormatException
    {

        Collection<GatewayDeviceIO> deviceIOCollection = createDeviceIOCollection(devGwTO.getIoList(), new UniDAAddress(devGwTO.getId()), null, dalOntFacade);

        return new Gateway(
                devGwTO.getCodId(), new UniDAAddress(devGwTO.getId()), devGwTO.getModel(),
                devGwTO.getManufacturer(), devGwTO.isEnabled(), devGwTO.getLocation(), dalOntFacade.getGatewayClassById(devGwTO.getGwClass()),
                devGwTO.getInstallationId(),
                new OperationalState(devGwTO.getOperationalState(), devGwTO.getOperationalStateLastChange()),
                deviceIOCollection,
                createPhysicalDevices(devGwTO.getDeviceList(), deviceIOCollection, dalOntFacade));

    }

    public static GatewayTO createDeviceGatewayTO(Gateway devGw)
    {

        ArrayList<IDeviceIO> ioList = new ArrayList<IDeviceIO>(devGw.getIoList().size());
        for (GatewayDeviceIO io : devGw.getIoList())
        {
            ioList.add(io);
        }

        return new GatewayTO(
                devGw.getCodId(), devGw.getId().toString(), devGw.getModel(),
                devGw.getManufacturer(), devGw.isEnabled(), devGw.getLocation(),
                devGw.getType().getClassId(), devGw.getInstallationId(), new String(devGw.getId().getAddress()),
                devGw.getOperationalState().getStateValue(),
                devGw.getOperationalState().getChangeTime(), createDeviceIOTOCollection(ioList), createDeviceTOList(devGw.getDevices()));

    }

    public static DeviceStateMetadata[] readDeviceStates(DeviceTO devTO, IDeviceAccessLayerOntologyFacade dalOntFacade) throws ClassNotFoundInOntologyException
    {

        DeviceClassMetadata classMd = dalOntFacade.getDeviceClassById(devTO.getDeviceClass());
        return classMd.getStates();

    }

    @Deprecated
    public static GatewayDevices createDeviceGatewayDevices(GatewayDevicesCTO cto, IDeviceAccessLayerOntologyFacade dalOntFacade) throws ClassNotFoundInOntologyException, UniDAIDFormatException
    {

        Collection<DeviceTO> dTOs = cto.getDevices();
        ArrayList<IDevice> devices = new ArrayList<IDevice>(dTOs.size());

        for (DeviceTO dto : dTOs)
        {

            DeviceClassMetadata deviceClass = dalOntFacade.getDeviceClassById(dto.getDeviceClass());

            if (dto.isGroup())
            {
                devices.add(createDeviceGroup(dto, deviceClass));
            } else if (dto.getId() == null)
            {
                DeviceStateMetadata[] supportedStates = DeviceConversionOperations.readDeviceStates(dto, dalOntFacade);
                devices.add(createDeviceIO(dto, deviceClass, supportedStates));
            } else
            {
                DeviceStateMetadata[] supportedStates = DeviceConversionOperations.readDeviceStates(dto, dalOntFacade);
                devices.add(createPhysicalDevice(dto, deviceClass));
            }
        }

        GatewayClassMetadata gcm = null;
        if (cto.getDeviceGateway().getGwClass() != null)
        {
            gcm = dalOntFacade.getGatewayClassById(cto.getDeviceGateway().getGwClass());
        } else
        {
            gcm = new GatewayClassMetadata(null);
        }
        Gateway devGw = createDeviceGateway(cto.getDeviceGateway(), dalOntFacade);

        return new GatewayDevices(devGw, devices);

    }

    public static Collection<GatewayDevices> createDeviceGatewayDevicesCollection(Collection<GatewayDevicesCTO> devs, IDeviceAccessLayerOntologyFacade dalOntFacade) throws ClassNotFoundInOntologyException, UniDAIDFormatException
    {

        ArrayList<GatewayDevices> result = new ArrayList<GatewayDevices>(devs.size());

        for (GatewayDevicesCTO devCTO : devs)
        {
            result.add(createDeviceGatewayDevices(devCTO, dalOntFacade));
        }

        return result;

    }

    public static GatewayDeviceIOTO createDeviceIOTOFromDeviceIO(IDeviceIO deviceIO)
    {

        Collection<DeviceStateMetadata> stateList = deviceIO.getCompatibleStates();
        ArrayList<String> strStateList = new ArrayList<String>(stateList.size());
        for (DeviceStateMetadata dsm : stateList)
        {
            strStateList.add(dsm.getId());
        }

        return new GatewayDeviceIOTO(deviceIO.getId(), strStateList);
    }

    public static Collection<GatewayDeviceIOTO> createDeviceIOTOCollection(Collection<IDeviceIO> deviceIOList)
    {
        ArrayList<GatewayDeviceIOTO> ioTOList = new ArrayList<GatewayDeviceIOTO>(deviceIOList.size());
        for (IDeviceIO io : deviceIOList)
        {
            ioTOList.add(createDeviceIOTOFromDeviceIO(io));
        }
        return ioTOList;
    }

    public static GatewayDeviceIO createDeviceIOFromDeviceIOTO(
            GatewayDeviceIOTO deviceIO, UniDAAddress gatewayId, PhysicalDevice device, IDeviceAccessLayerOntologyFacade dalOntFacade) throws ClassNotFoundInOntologyException
    {
        ArrayList<DeviceStateMetadata> stateList = new ArrayList<DeviceStateMetadata>();
        for (String stateId : deviceIO.getCompatibleStates())
        {
            stateList.add(dalOntFacade.getDeviceStateById(stateId));
        }
        return new GatewayDeviceIO(deviceIO.getId(), gatewayId, device, stateList);
    }

    public static Collection<GatewayDeviceIO> createDeviceIOCollection(
            Collection<GatewayDeviceIOTO> deviceIOList, UniDAAddress gatewayId, PhysicalDevice device, IDeviceAccessLayerOntologyFacade dalOntFacade) throws ClassNotFoundInOntologyException
    {
        ArrayList<GatewayDeviceIO> ioList = new ArrayList<GatewayDeviceIO>(deviceIOList.size());
        for (GatewayDeviceIOTO ioTO : deviceIOList)
        {
            ioList.add(createDeviceIOFromDeviceIOTO(ioTO, gatewayId, device, dalOntFacade));
        }
        return ioList;
    }

}

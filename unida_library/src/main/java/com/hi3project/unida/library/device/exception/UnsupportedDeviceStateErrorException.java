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

package com.hi3project.unida.library.device.exception;

import com.mytechia.commons.framework.exception.ModelException;
import com.hi3project.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.hi3project.unida.library.device.IDeviceIO;


/**
 * <p><b>Description:</b></p>
 * A device can be connected to the specified device IO using the specified state
 *
 *
 * <p><b>Creation date:</b> 7-feb-2013</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 7-feb-2013 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UnsupportedDeviceStateErrorException extends ModelException
{

    private IDeviceIO deviceIO;
    private DeviceStateMetadata state;


    public UnsupportedDeviceStateErrorException(IDeviceIO deviceIO, DeviceStateMetadata state)
    {
        this.deviceIO = deviceIO;
        this.state = state;
    }


    public UnsupportedDeviceStateErrorException(IDeviceIO deviceIO, DeviceStateMetadata state, String message)
    {
        super(message);
        this.deviceIO = deviceIO;
        this.state = state;
    }


    public IDeviceIO getDeviceIO()
    {
        return deviceIO;
    }


    public DeviceStateMetadata getState()
    {
        return state;
    }

}

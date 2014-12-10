/*******************************************************************************
 *   
 *   Copyright (C) 2010 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2010 Victor Sonora <victor@vsonora.com>
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

package com.unida.library.exception;

import com.mytechia.commons.framework.exception.InternalErrorException;




/**
 * <p><b>
 * Specified that a device gateway is not currently supported by the UniDA
 * because there isn't any availabe IDevicesAccessLayerFacade implementation
 * compatible with it.
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 18-01-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 18-01-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UnsupportedDeviceGatewayErrorException extends InternalErrorException
{

    public UnsupportedDeviceGatewayErrorException(Exception exception, String message)
    {
        super(exception, message);
    }


    public UnsupportedDeviceGatewayErrorException(String message)
    {
        super(message);
    }


    public UnsupportedDeviceGatewayErrorException(Exception exception)
    {
        super(exception);
    }

}

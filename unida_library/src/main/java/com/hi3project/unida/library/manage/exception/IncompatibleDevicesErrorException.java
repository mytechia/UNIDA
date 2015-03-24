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

package com.hi3project.unida.library.manage.exception;


import com.mytechia.commons.framework.exception.ModelException;
import com.hi3project.unida.library.device.Device;


/**
 * <p><b>
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 25-02-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 25-02-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class IncompatibleDevicesErrorException extends ModelException
{

//    private Device d1;
//    private Device d2;


    public IncompatibleDevicesErrorException(Device d1, Device d2)
    {
        super("The states supported by device '"+d1.getId()+
                "' are not compatible with the states of the device '"+d2.getId()+"'");
    }


}

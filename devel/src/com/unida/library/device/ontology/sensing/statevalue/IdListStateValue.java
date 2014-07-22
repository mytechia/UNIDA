/*******************************************************************************
 *   
 *   Copyright (C) 2010,2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010,2013 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2013 Victor Sonora <victor@vsonora.com>
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

package com.unida.library.device.ontology.sensing.statevalue;

import com.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.unida.library.device.ontology.state.DeviceStateValue;
import java.util.ArrayList;
import java.util.Collection;


/**
 * <p><b>
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 07-02-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 07-02-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class IdListStateValue extends StringListStateValue
{

    private static final String MACLIST = "stringlist";

    public static final String DEFAULT_STATE_ID = "StringListState";
    public static final String DEFAULT_STATE_VALUE_ID = "StringStateValue";



    public IdListStateValue(String valueId, String value)
    {
        super(MACLIST, valueId, value);
    }


    public IdListStateValue(Collection<String> macList)
    {
        super(MACLIST, DEFAULT_STATE_VALUE_ID, macList);
    }


    public Collection<String> getIdList()
    {
        return this.getList();
    }



    public static DeviceStateValue createDefaultStateValue(String value)
    {
        return new DeviceStateValue(DEFAULT_STATE_VALUE_ID, value);
    }


    public static DeviceStateMetadata createDeviceStateMetadata()
    {
        DeviceStateValue[] possibleValues = new DeviceStateValue[] {createDefaultStateValue("")};
        return new DeviceStateMetadata(DEFAULT_STATE_ID, possibleValues);
    }


}

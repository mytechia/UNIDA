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


import com.unida.library.device.ontology.state.DeviceStateValue;
import java.util.ArrayList;
import java.util.Collection;


/**
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
public class StringListStateValue extends DeviceStateValue
{

    private String header;

    private ArrayList<String> macList;


    public StringListStateValue(
            String header, 
            String valueId, String value)
    {
        super(valueId, value);
        this.header = header;      
        decodeListValue(value);
    }


    public StringListStateValue(
            String header, 
            String valueId, Collection<String> macList)
    {
        super(valueId, "");
        this.header = header;
        this.macList = new ArrayList<>(macList);
        this.setValueRaw(codeListValue());
    }


    private void decodeListValue(String value)
    {
        int offset = 0;
        String [] parts = value.split(";");
        if ((parts.length > 2) && (parts[0] != null) && (parts[0].equalsIgnoreCase(this.header))) {
            offset += 1;
            int numMacs = Integer.parseInt(parts[offset]);
            offset += 1;
            this.macList = new ArrayList<>(numMacs);
            for(int i=0; (i+offset+1<parts.length && i<numMacs); i++) {
                this.macList.add(parts[i+offset+1]);
            }
        }
    }


    private String codeListValue()
    {
        StringBuilder sb = new StringBuilder(this.header);sb.append(";");
        sb.append(macList.size()); sb.append(";");
        for(String mac : macList) {
            sb.append(mac);
            sb.append(";");
        }
        return sb.toString();
    }


    public Collection<String> getList()
    {
        if (this.macList != null)
            return new ArrayList<>(this.macList);
        else
            return new ArrayList<>(0);
    }


}

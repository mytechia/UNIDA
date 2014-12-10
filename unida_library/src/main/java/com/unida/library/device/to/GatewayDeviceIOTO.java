/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2013 Gervasio Varela <gervarela@picandocodigo.com>
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


package com.unida.library.device.to;

import java.util.ArrayList;
import java.util.Collection;



/**
 * <p>A device IO with basic types</p>
 *
 * <p><b>Creation date:</b> 07-feb-2013</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 07-feb-2013 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class GatewayDeviceIOTO 
{
    
    
    private short id;
    
    private ArrayList<String> compatibleStates;


    public GatewayDeviceIOTO(short id, Collection<String> compatibleStates)
    {
        this.id = id;
        this.compatibleStates = new ArrayList<>(compatibleStates);
    }


    public short getId()
    {
        return id;
    }


    public ArrayList<String> getCompatibleStates()
    {
        return compatibleStates;
    }

    @Override
    public String toString()
    {
        return "GatewayDeviceIOTO{" + "id=" + id + ", compatibleStates=" + compatibleStates + '}';
    }

}

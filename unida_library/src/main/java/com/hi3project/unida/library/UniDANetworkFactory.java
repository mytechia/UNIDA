/*******************************************************************************
 *   
 *   Copyright (C) 2009-2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2009-2013 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2012-2013 Victor Sonora <victor@vsonora.com>
 *   Copyright (C) 2009-2013 Alejandro Paz <alejandropl@gmail.com>
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

package com.hi3project.unida.library;


import com.hi3project.unida.library.core.DefaultUniDANetworkFacade;
import com.hi3project.unida.library.core.IUniDANetworkFacade;
import com.hi3project.unida.library.device.Gateway;
import com.hi3project.unida.library.exception.UnsupportedDeviceGatewayErrorException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p><b>
 * Manages the access to the UniDAFacade implementations available in the system.
 * The clients of the IUniDANetworkFacade shoud ask this factory for an
 * instance of the correct IUniDANetworkFacade implementation.
 * This factory manages a cache of instances, if no one is valid, a valid one
 * will be created and cached.
 *
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 15-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 15-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDANetworkFactory
{

    private Set<IUniDANetworkFacade> unidaNetworkFacadeInstances;

    /** Default UniDAFacade to use with UniDA device that are connected
     * directly through ethernet-TCP/IP */
    private DefaultUniDANetworkFacade unidaNetworkFacade;

    
    public UniDANetworkFactory(DefaultUniDANetworkFacade unidaNetworkFacade)
    {
        this.unidaNetworkFacade = unidaNetworkFacade;
        this.unidaNetworkFacadeInstances = new CopyOnWriteArraySet<>();
    }


    /** Obtains a IUniDANetworkFacade implementation adequate to interact
     * with the specified device gateway.
     * 
     * @param gw the gateway for wich a UniDA instance is needed
     * @return 
     * @throws com.hi3project.unida.library.exception.UnsupportedDeviceGatewayErrorException
     */
    public IUniDANetworkFacade getUniDANetworkInstance(Gateway gw) throws UnsupportedDeviceGatewayErrorException
    {
        
        //In the future many facade could exist, one for each concrete implementation
        //of the UNIDA protocol (right now only UDP). In that case, this method
        //must select the appropriate one for the specified gateway
        
        return this.unidaNetworkFacade;
        
    }


    public void addUniDANetworkInstance(IUniDANetworkFacade dal)
    {
        this.unidaNetworkFacadeInstances.add(dal);
    }


    public void removeUniDANetworkInstance(IUniDANetworkFacade dal)
    {
        this.unidaNetworkFacadeInstances.remove(dal);
    }

    
}

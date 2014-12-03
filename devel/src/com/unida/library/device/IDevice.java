/*******************************************************************************
 *   
 *   Copyright (C) 2010,2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010,2013 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2012 Victor Sonora <victor@vsonora.com>
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

package com.unida.library.device;

import com.unida.library.device.state.OperationalState;
import com.unida.library.device.ontology.metadata.DeviceClassMetadata;
import com.unida.library.location.Location;
import java.io.Serializable;


/**
 * <p><b>
 * This interfaces represents a device in its more natural way. It is, a real
 * (physical) device that exists in an installation, like a window blind, a door,
 * a fridge, etc. and is in a known location.
 *
 * Ab object of this type is used as a unique identifier of an actual device,
 * also containing the descripting information about the device (model, manufacturer, etc).
 *
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 25-02-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>2 - 5-feb-2013 The device IOs are known only at the physical device level</li>
 * <li>1 - 25-02-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public interface IDevice extends Serializable
{

    /** Gets the device id from the database.
     * 
     * Can be null when using the UniDA library on-line
     * 
     * @return the device id from the database or null if the lib is used on-line
     */
    Long getCodId();


    /** 
     * 
     * @return 
     */
    DeviceID getId();
    
    
    String getName();
    
    
    void setName(String name);
    
    
    String getDescription();


    void setDescription(String desc);


    Location getLocation();


    void setLocation(Location loc);


    String getModel();


    void setModel(String model);


    String getManufacturer();


    void setManufacturer(String manufacturer);


    DeviceClassMetadata getDeviceClass();

    
    boolean isEnabled();


    boolean isConnected();
    

    boolean isGroup();


    OperationalState getOperationalState();


    void setOperationalState(OperationalState state);


}

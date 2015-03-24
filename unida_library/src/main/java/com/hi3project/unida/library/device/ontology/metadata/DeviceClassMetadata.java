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

package com.hi3project.unida.library.device.ontology.metadata;

import com.hi3project.unida.library.device.ontology.metadata.NotificationFunctionalityMetadata;
import com.hi3project.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.hi3project.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import java.io.Serializable;
import java.util.Arrays;


/**
 * <p><b>Description:</b></p>
 * Represents the type of a device according to the device description ontology
 *
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class DeviceClassMetadata implements Serializable
{
    
    private String classId;

    /** Control functionalyties supported by this device type */
    private ControlFunctionalityMetadata [] controlFunctionalities;

    /** Device states type supported by this device type*/
    private DeviceStateMetadata [] states;

    /** Notification functionalities supported by this device type */
    private NotificationFunctionalityMetadata [] notificationFunctionality;


    public DeviceClassMetadata(
            String classId,
            ControlFunctionalityMetadata[] controlFunctionalities,
            DeviceStateMetadata[] states,
            NotificationFunctionalityMetadata[] notificationFunctionality)
    {
        this.classId = classId;

        setControlFunctionalities(controlFunctionalities);
        setStates(states);
        setNotificationFunctionality(notificationFunctionality);
    }

    public String getClassId()
    {
        return classId;
    }
    
    public String getShortClassId()
    {
        String[] split = getClassId().split("#");
        if (split.length >= 2) {
            return split[1];
        }
        return classId;
    }


    public ControlFunctionalityMetadata[] getControlFunctionalities()
    {
        return Arrays.copyOf(controlFunctionalities, controlFunctionalities.length);
    }


    public NotificationFunctionalityMetadata[] getNotificationFunctionality()
    {
        return Arrays.copyOf(notificationFunctionality, notificationFunctionality.length);
    }


    public DeviceStateMetadata[] getStates()
    {
        return Arrays.copyOf(states, states.length);
    }

    private void setControlFunctionalities(ControlFunctionalityMetadata[] controlFunctionalities)
    {
        if (controlFunctionalities == null) {
            this.controlFunctionalities = new ControlFunctionalityMetadata[0];
        } else {
            this.controlFunctionalities = Arrays.copyOf(
                controlFunctionalities, controlFunctionalities.length);
        }

    }

    private void setNotificationFunctionality(NotificationFunctionalityMetadata[] notificationFunctionality)
    {
        if (notificationFunctionality == null) {
            this.notificationFunctionality = new NotificationFunctionalityMetadata[0];
        } else {
            this.notificationFunctionality = Arrays.copyOf(
                notificationFunctionality, notificationFunctionality.length);
        }
    }

    private void setStates(DeviceStateMetadata[] states)
    {
        if (states == null) {
            this.states = new DeviceStateMetadata[0];
        } else {
            this.states = Arrays.copyOf(
                states, states.length);
        }
    }

    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeviceClassMetadata other = (DeviceClassMetadata) obj;
        if ((this.classId == null) ? (other.classId != null) : !this.classId.equals(other.classId)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 41 * hash + (this.classId != null ? this.classId.hashCode() : 0);
        return hash;
    }



}

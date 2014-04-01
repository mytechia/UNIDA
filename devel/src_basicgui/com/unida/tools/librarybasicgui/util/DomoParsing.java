/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
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


package com.unida.tools.librarybasicgui.util;


import com.unida.library.device.IDeviceIO;
import com.unida.library.device.ontology.DeviceStateMetadata;
import java.util.Collection;

/**
 *  Utility methods and a Singleton object that holds a soft of global variable:
 * the prefix used to complete the ontology axioms introduced by the user in 
 * different parts if this application
 *  
 * @author victor
 */
public class DomoParsing 
{
    
    private final static String VALUES_SEPARATOR = ";";
    
    private static DomoParsing instance = null;
    
    private String defaultOntologyNamespace = null;        
    
    
    /*
     *  Constructor: it places a default value for the defaultOntologyNamespace
     */
    public DomoParsing() 
    {
        defaultOntologyNamespace = "http://elite.polito.it/ontologies/dogont.owl#";
    }
    
    
    public static DomoParsing instance() 
    {
        if (null == instance) 
        {
            instance = new DomoParsing();
        }
        return instance;
    }
    
    
    public String getDefaultOntologyNamespace() 
    {
        return defaultOntologyNamespace;
    }
    
    public void changeDefaultOntologyNamespace(String newDefaultOntologyNamespace) 
    {
        defaultOntologyNamespace = newDefaultOntologyNamespace;
    }
    
    
    /***********************************************************************************************************/    
    /************************************* Class utility methods     *******************************************/
    /***********************************************************************************************************/
    
    public static String connectedIOsToString(Collection<IDeviceIO> deviceIOs) 
    {
        String s = "";
        for (IDeviceIO deviceIO : deviceIOs) 
        {
            s += deviceIO.getId() + "; ";
        }
        if (s.length() > 2) 
        {
            return s.substring(0, s.length() - 2);
        } else 
        {
            return s;
        }
    }

    public static String compatibleStatesToString(Collection<DeviceStateMetadata> statesMetadata) 
    {        
        String s = "";
        if (statesMetadata.isEmpty()) return s;
        for (DeviceStateMetadata stateMetadata : statesMetadata) 
        {
            s += stateMetadata.getShortId() + "[";
            for (int i = 0; i < stateMetadata.getPossibleValues().length; i++) 
            {
                s += stateMetadata.getPossibleValues()[i].getValueIdShort() + ": " + stateMetadata.getPossibleValues()[i].getValueRaw();
                if (i < stateMetadata.getPossibleValues().length - 1) 
                {
                    s += ", ";
                }
            }
            s += "]; ";
        }
        return s.substring(0, s.length() - 2);
    }
    
    public static String [] valuesInStringToArray(String values)
    {
        
        String[] valuesArray = values.split(VALUES_SEPARATOR);
        return valuesArray;
        
    }
    
    
}

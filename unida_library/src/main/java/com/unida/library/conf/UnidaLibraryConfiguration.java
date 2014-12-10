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


package com.unida.library.conf;

import com.mytechia.commons.util.configuration.IUserConfiguration;
import java.io.IOException;
import java.util.HashMap;


/** Manages the configuration of the UNIDA library.
 * 
 * The user can set properties programmatically and access to them.
 *
 * @author Gervasio Varela Fernandez - Integrated Group for Engineering Research
 * @version 1
 *
 * Changelog:
 *      09-jul-2013 -- Initial version
 */
public class UnidaLibraryConfiguration implements IUserConfiguration
{
    
    public static final String LOCAL_IP = "Unida.Library.Local.Ip";
    

    private static UnidaLibraryConfiguration instance = null;
    
    private HashMap<String, String> properties;


    private UnidaLibraryConfiguration()
    {
        this.properties = new HashMap<>();
    }
    
    
    public static UnidaLibraryConfiguration getInstance()
    {
        
        if (instance == null) {
            instance = new UnidaLibraryConfiguration();
        }
        
        return instance;
        
    }
    

    @Override
    public void storeConfiguration() throws IOException
    {
        
    }


    @Override
    public String getParam(String key)
    {
        return this.properties.get(key);
    }


    @Override
    public void setParam(String key, String value)
    {
        this.properties.put(key, value);
    }

}

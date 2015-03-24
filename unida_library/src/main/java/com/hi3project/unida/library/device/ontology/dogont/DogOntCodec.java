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


package com.hi3project.unida.library.device.ontology.dogont;

import com.hi3project.unida.library.device.exception.UniDAIDFormatException;
import com.hi3project.unida.library.device.ontology.IUniDAOntologyCodec;
import com.hi3project.unida.library.device.ontology.exception.OntologyLoadingErrorException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author victor_local
 */
public class DogOntCodec implements IUniDAOntologyCodec {
    
    Properties properties;
    HashMap<Long, String> inverseProperties;

    public DogOntCodec() throws OntologyLoadingErrorException {
        properties = new Properties();
        try {
            properties.load(DogOntCodec.class.getResourceAsStream(DogOntFacade.DOGONT_ONTOLOGY_PROPERTIES_FILE_PATH));
        } catch (IOException ex) {
            throw new OntologyLoadingErrorException("Error opening ontology file");
        }
        inverseProperties = new HashMap<>();
        Set<Entry<Object, Object>> entrySet = properties.entrySet();
        for (Entry entry: entrySet) {
            inverseProperties.put(Long.valueOf(entry.getValue().toString()), (String)entry.getKey());
        }
    }        

    @Override
    public long encodeId(String stringId) throws UniDAIDFormatException {
        String property = properties.getProperty(stringId);
        if (null == property) 
        {
            throw new UniDAIDFormatException(stringId);
        }
        return Long.valueOf(property);
    }

    @Override
    public String decodeId(long longId) throws UniDAIDFormatException {
        String property = inverseProperties.get(longId);
        if (null == property) 
        {
            throw new UniDAIDFormatException(String.valueOf(longId));
        }
        return property;
    }
    
}

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

import java.io.Serializable;

/**
 * 
 */
public class GatewayClassMetadata implements Serializable
{

    private String classId;

    
    public GatewayClassMetadata(String classId)
    {
        this.classId = classId;
    }

    public String getClassId()
    {
        return classId;
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
        final GatewayClassMetadata other = (GatewayClassMetadata) obj;
        if ((this.classId == null) ? (other.classId != null) : !this.classId.equals(other.classId)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 13 * hash + (this.classId != null ? this.classId.hashCode() : 0);
        return hash;
    }



}

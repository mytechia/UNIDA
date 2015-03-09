/*******************************************************************************
 *   
 *   Copyright (C) 2010 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010 Gervasio Varela <gervarela@picandocodigo.com>
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

package com.unida.library.operation;

import com.mytechia.commons.util.id.Identifier;


/**
 * <p><b>
 * A ticket that the operation facade issue for every operation. It is a
 * representation of asynchronous operations to the client. It serves to 
 * request information about its pending operation and it will be used as
 * an identifier when the UniDA notifies back results or errors about the
 * operation.
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 18-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 18-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class OperationTicket extends Identifier
{

    private OperationTypes type;

    
    public OperationTicket(long id, OperationTypes type)
    {
        super(id);
        this.type = type;
    }

    
    public OperationTypes getType()
    {
        return type;
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
        final OperationTicket other = (OperationTicket) obj;
        return !super.equals(other) || (this.type == other.type || (this.type != null && this.type.equals(other.type)));
    }


    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + (this.type != null ? this.type.hashCode() : 0);
        return hash;
    }


}

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

package com.unida.library.device.ontology;

import java.io.Serializable;
import java.util.Arrays;


/**
 * <p><b>Description:</b></br>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 25-nov-2010</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 25-nov-2010</br> Initial release.</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class ControlCommand implements Serializable
{

    private Long id;

    private ControlCommandMetadata metadata;

    private String [] params;


    public ControlCommand(Long id, ControlCommandMetadata metadata, String[] params)
    {
        this.metadata = metadata;
        this.params = params;
    }


    public ControlCommand(ControlCommandMetadata metadata, String[] params)
    {
        this(null, metadata, params);
    }


    public ControlCommandMetadata getMetadata()
    {
        return metadata;
    }

    public String[] getParams()
    {
        return Arrays.copyOf(params, params.length);
    }


    public Long getId()
    {
        return this.id;
    }



}

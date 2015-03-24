/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

package com.hi3project.unida.library.device.ontology;

import com.hi3project.unida.library.device.exception.UniDAIDFormatException;

/**
 *
 * @author victor_local
 */
public interface IUniDAOntologyCodec {
    
    long encodeId(String stringId) throws UniDAIDFormatException;
    
    String decodeId(long longId) throws UniDAIDFormatException;
    
}

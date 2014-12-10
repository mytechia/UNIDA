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


package com.unida.protocol;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.unida.protocol.message.UniDAMessage;


/**
 * <p><b>
 * UniDA protocol message factory.
 *
 * It contains the logic to create every type of message that is used
 * by the UniDA communications protocol.
 * 
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 26-01-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 26-01-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public interface IMessageFactory
{


    UniDAMessage createUnidaMessage(byte[] msgData) throws MessageFormatException;
    

}

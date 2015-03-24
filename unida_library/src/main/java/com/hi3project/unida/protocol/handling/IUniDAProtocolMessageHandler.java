
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


package com.hi3project.unida.protocol.handling;

import com.hi3project.unida.protocol.message.UniDAMessage;
import com.hi3project.unida.protocol.handling.exception.UnsupportedMessageTypeErrorException;

/**
 * <p><b>
 * Concrete implementations of this interfaces containts the logic to
 * process different types of UniDA protocol messages.
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 30-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>2 - 29-09-2010: Removed the method getSupportedMessageType, added the method supports</li>
 * <li>1 - 30-01-2010: Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public interface IUniDAProtocolMessageHandler
{

    
	/** Tests whether a message msg can be processed by this message handler
	 * @return true if the message can be processed by this handler, false otherwise
	 */
	boolean supports(UniDAMessage msg);


	/** Processes a message
	 * @param msg the message to process
         * @return the response of the message, or null in case that there isn't
         * response.
	 */
	UniDAMessage handle(UniDAMessage msg) throws UnsupportedMessageTypeErrorException;
		
}

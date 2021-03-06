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

package com.hi3project.unida.log;

import java.util.logging.Logger;


/**
 * <p>This class provide static loggers for different purposes
 * 
 * LIBRARY - logger for UniDA library code
 * GATEWAY - logger for UniDA gateway code
 * 
 * They can be accessed directly as public static attributes:
 * UniDALoggers.LIBRARY.log(Level.INFO, "Info message");
 *
 *
 *
 * <p><b>Creation date:</b> 11-03-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 11-03-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDALoggers
{

    public final static String LIBRARY_LOGGER_NAME = "UniDA-library-Logger";
    public final static String GATEWAY_LOGGER_NAME = "UniDA-gateway-Logger";
    
    public final static Logger LIBRARY = Logger.getLogger(LIBRARY_LOGGER_NAME);
    public final static Logger GATEWAY = Logger.getLogger(GATEWAY_LOGGER_NAME);

}

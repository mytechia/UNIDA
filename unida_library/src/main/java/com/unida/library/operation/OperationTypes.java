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

/**
 * <p><b>
 * Enumeration of the different types of operation supported by UniDA
 * devices and gateways.
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 18-01-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 18-01-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public enum OperationTypes
{

    QUERY_STATE, //a query of the states (one or more) of a device
    WRITE_STATE, //write the value for one state of a device
    SEND_COMMAND, //send a command to a device
    SEND_COMMAND_QUERY_STATE, //send a command to a device and receive its state after the command execution
    SUSCRIBE_TO, //suscribe to a device notification    
    QUERY_AUTONOMOUS_BEHAVIOUR, //a query of the autonomous behaviour rules handled by a gateway
    QUERY_SCENARIOS, //a query of the autonomous behaviour scenarios used by rules in a gateway
    CHANGE_SCENARIO //a command to change the current autonomous behaviour scenario of a gateway
    
}

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


package com.unida.protocol.udp;


import com.mytechia.commons.framework.simplemessageprotocol.udp.UDPCommunicationChannelImplementation;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * <p><b>
 * </b></br>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 29-01-2010</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 29-01-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UDPUniDACommunicationChannelImplementation extends UDPCommunicationChannelImplementation
{

    
    public static final int UNIDA_PROTOCOL_UDP_PORT = 4103;
  


    /**
     * @param endPointIP the IP of the commuter that is at the other side of the communication channel
     */
    public UDPUniDACommunicationChannelImplementation(int port) throws UnknownHostException, SocketException
    {
        super(port);
    }  


    public UDPUniDACommunicationChannelImplementation() throws UnknownHostException, SocketException
    {
        this(UNIDA_PROTOCOL_UDP_PORT);
    }
	
	
}

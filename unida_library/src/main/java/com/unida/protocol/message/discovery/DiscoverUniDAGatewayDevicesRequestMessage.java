/*******************************************************************************
 *   
 *   Copyright (C) 2009-2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2009-2013 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2012-2013 Victor Sonora <victor@vsonora.com>
 *   Copyright (C) 2009-2013 Alejandro Paz <alejandropl@gmail.com>
 *
 * This file is part of UNIDA.
 *
 * UNIDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * UNIDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with UNIDA. If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/


package com.unida.protocol.message.discovery;


import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDAMessage;


/**
 * <p>
 * <b>
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 27-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 27-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class DiscoverUniDAGatewayDevicesRequestMessage extends UniDAMessage
{

    public DiscoverUniDAGatewayDevicesRequestMessage(IUniDAOntologyCodec ontologyCodec)
    {
        super(ontologyCodec);       
        setCommandType(MessageType.DiscoverGatewayDevices.getTypeValue());
        setErrorCode(ErrorCode.Null.getTypeValue()); //null error code indicates a request
        setData(new byte[0]);
        this.destination = UniDAAddress.BROADCAST_ADDRESS;
    }


    public DiscoverUniDAGatewayDevicesRequestMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }

    
    @Override
    protected int decodeMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {
        return initIndex;
    }


    @Override
    protected byte[] codeMessagePayload()
    {
        return new byte[0];
    }

    @Override
    public String toString()
    {
        return super.toString() + "<-DiscoverUniDAGatewayDevicesRequestMessage{" + '}';
    }

}

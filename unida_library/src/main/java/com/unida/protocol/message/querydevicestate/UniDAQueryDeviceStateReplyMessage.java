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


package com.unida.protocol.message.querydevicestate;


import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.DeviceID;
import com.unida.library.device.ontology.state.DeviceStateValue;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;


/**
 * <p><b>
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 27-01-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 27-01-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDAQueryDeviceStateReplyMessage extends UniDAQueryDeviceStateRequestMessage
{

    private DeviceStateValue stateValue;


    public UniDAQueryDeviceStateReplyMessage(UniDAMessage request, IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId, String stateId, DeviceStateValue stateValue, ErrorCode err)
    {
        super(ontologyCodec, opId, deviceId, stateId);
        this.setDestination(request.getSource());
        setErrorCode(err.getTypeValue());
        setCommandType(MessageType.QueryDeviceStateReply.getTypeValue());
        this.stateValue = stateValue;
    }


    public UniDAQueryDeviceStateReplyMessage(UniDAMessage request, IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId, String stateId, DeviceStateValue stateValue)
    {
        this(request, ontologyCodec, opId, deviceId, stateId, stateValue, ErrorCode.Ok);
    }

    
    public UniDAQueryDeviceStateReplyMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }
    

    public DeviceStateValue getValue()
    {
        return stateValue;
    }

    
    @Override
    public byte[] codeDeviceMessagePayload() throws MessageFormatException
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try {

            // create the data payload of the father class
            dataStream.write(super.codeDeviceMessagePayload());

            // state value
            dataStream.write(stateValue.code(ontologyCodec));

        }
        catch(IOException ioEx) {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();

    }


    @Override
    protected int decodeDeviceMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {

        int offset = super.decodeDeviceMessagePayload(bytes, initIndex);

        this.stateValue = new DeviceStateValue();
        
        // state value
        offset = this.stateValue.decode(bytes, offset, ontologyCodec);
        
        this.stateValue = this.stateValue.getSpecificImpl();

        return offset;

    }

    @Override
    protected MessageRType getMessageType() {
        return MessageRType.REPLY;
    }

    
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + this.stateValue.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final UniDAQueryDeviceStateReplyMessage other = (UniDAQueryDeviceStateReplyMessage) obj;
        return Objects.equals(this.stateValue, other.stateValue);
    }

    @Override
    public String toString()
    {
        return super.toString() + "<-UniDAQueryDeviceStateReplyMessage{" + "stateValue=" + stateValue + '}';
    }
            
}

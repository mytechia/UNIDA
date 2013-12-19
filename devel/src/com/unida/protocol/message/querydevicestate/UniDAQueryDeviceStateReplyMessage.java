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
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.DeviceID;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * <p><b>
 * </b></br>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 27-01-2010</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 27-01-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDAQueryDeviceStateReplyMessage extends UniDAQueryDeviceStateRequestMessage
{

    private String valueId;
    private String value;


    public UniDAQueryDeviceStateReplyMessage(UniDAMessage request, IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId, String stateId, String valueId, String value, ErrorCode err)
    {
        super(ontologyCodec, opId, deviceId, stateId);
        this.setDestination(request.getSource());
        setErrorCode(err.getTypeValue());
        setCommandType(MessageType.QueryDeviceStateReply.getTypeValue());
        this.valueId = valueId;
        this.value = value;
    }


    public UniDAQueryDeviceStateReplyMessage(UniDAMessage request, IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId, String stateId, String valueId, String value)
    {
        this(request, ontologyCodec, opId, deviceId, stateId, valueId, value, ErrorCode.Ok);
    }

    
    public UniDAQueryDeviceStateReplyMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }
    

    public String getValue()
    {
        return value;
    }


    public String getValueId()
    {
        return valueId;
    }

    
    @Override
    public byte[] codeDeviceMessagePayload() throws MessageFormatException
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try {

            //create the data payload of the father class
            dataStream.write(super.codeDeviceMessagePayload());

            byte [] idData = new byte[EndianConversor.INT_SIZE_BYTES];
            EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(this.valueId), idData, 0);          
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);
            
            writeString(dataStream, this.value);

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

        StringBuilder string = new StringBuilder(10);

        //value id
        this.valueId = getOntologyCodec().decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, offset));
        offset += EndianConversor.INT_SIZE_BYTES;

        //value
        offset += readString(string, bytes, offset);
        this.value = string.toString();

        return offset;

    }

    @Override
    protected MessageRType getMessageType() {
        return MessageRType.REPLY;
    }

}

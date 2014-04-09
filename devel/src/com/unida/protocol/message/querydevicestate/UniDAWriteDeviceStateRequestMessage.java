/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
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
import com.unida.library.device.DeviceID;
import com.unida.library.device.ontology.DeviceStateValue;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDADeviceMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;



/**
 *
 * @author Victor Sonora
 */
public class UniDAWriteDeviceStateRequestMessage extends UniDADeviceMessage
{
    
    private long opId;
    private String stateId;
    private String valueId;
    private String value;
    
        
    public UniDAWriteDeviceStateRequestMessage(IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId, String stateId, String valueId, String value, ErrorCode err)
    {
        super(ontologyCodec, deviceId);
        setCommandType(MessageType.WriteState.getTypeValue());
        this.opId = opId;
        this.stateId = stateId;
        this.valueId = valueId;
        this.value = value;
    }


    public UniDAWriteDeviceStateRequestMessage(IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId, String stateId, String valueId, String value)
    {
        this(ontologyCodec, opId, deviceId, stateId, valueId, value, ErrorCode.Ok);
    }

    
    public UniDAWriteDeviceStateRequestMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }

    
    public String getStateId() 
    {
        return stateId;
    }

    public DeviceStateValue getStateValue()
    {
        return new DeviceStateValue(this.valueId, this.value);
    }
               

    @Override
    protected MessageRType getMessageType()
    {
        return MessageRType.REQUEST;
    }

    @Override
    protected int decodeDeviceMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {
        int offset = initIndex;        

        this.opId = EndianConversor.byteArrayLittleEndianToLong(bytes, offset);
        offset += EndianConversor.LONG_SIZE_BYTES;

        //stateId        
        this.stateId = getOntologyCodec().decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, offset));
        offset += EndianConversor.INT_SIZE_BYTES;
        
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
    protected byte[] codeDeviceMessagePayload() throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try {

            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(opId, idData, 0);
            dataStream.write(idData);

            EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(this.stateId), idData, 0);
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);
                        
            EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(this.valueId), idData, 0);          
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);
            
            writeString(dataStream, this.value);

        } catch (IOException ioEx) {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();
    }
        

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + (int) (this.opId ^ (this.opId >>> 32));
        hash = 29 * hash + Objects.hashCode(this.stateId);
        hash = 29 * hash + Objects.hashCode(this.valueId);
        hash = 29 * hash + Objects.hashCode(this.value);
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
        final UniDAWriteDeviceStateRequestMessage other = (UniDAWriteDeviceStateRequestMessage) obj;
        if (this.opId != other.opId)
        {
            return false;
        }
        if (!Objects.equals(this.stateId, other.stateId))
        {
            return false;
        }
        if (!Objects.equals(this.valueId, other.valueId))
        {
            return false;
        }
        return Objects.equals(this.value, other.value);
    }
    
    
    
}

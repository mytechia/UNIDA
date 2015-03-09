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


package com.unida.protocol.message.ack;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.DeviceID;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDADeviceMessage;
import com.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;


/**
 * <p><b>
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 28-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 28-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDAOperationAckMessage extends UniDADeviceMessage
{

    private ErrorCode errorCode;
    private Long opId;


    public UniDAOperationAckMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
        this.setCommandType(MessageType.GenericReply.getTypeValue());
        this.errorCode = ErrorCode.getTypeOf(getErrorCode());
    }
    

    public UniDAOperationAckMessage(UniDAMessage request, IUniDAOntologyCodec ontologyCodec, ErrorCode errorCode, 
            DeviceID deviceID)
    {
        super(ontologyCodec, deviceID);
        this.setCommandType(MessageType.GenericReply.getTypeValue());
        this.setDestination(request.getSource());
        this.setSequenceNumber(request.getSequenceNumber());
        this.errorCode = errorCode;
        this.setErrorCode(this.errorCode.getTypeValue());
    }
    

    public UniDAOperationAckMessage(UniDAMessage request, IUniDAOntologyCodec ontologyCodec, 
            ErrorCode errorCode, long opId, DeviceID deviceID)
    {
        this(request, ontologyCodec, errorCode, deviceID);
        this.opId = opId;
        setOntologyCodec(ontologyCodec);
    }


    public Long getOperationId()
    {
        return this.opId;
    }


    @Override
    public byte[] codeDeviceMessagePayload()
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try {          
            
            byte [] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(this.opId, idData, 0);
            dataStream.write(idData);                        

        }
        catch(IOException ioEx) {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();

    }


    @Override
    protected int decodeDeviceMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {

        int offset = initIndex;

        this.opId = EndianConversor.byteArrayLittleEndianToLong(bytes, offset);
        offset += EndianConversor.LONG_SIZE_BYTES;

        return offset;

    }

    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UniDAOperationAckMessage other = (UniDAOperationAckMessage) obj;
        if (this.errorCode != other.errorCode && (this.errorCode == null || !this.errorCode.equals(other.errorCode))) {
            return false;
        }
        return !(!Objects.equals(this.opId, other.opId) && (this.opId == null || !this.opId.equals(other.opId)));
    }


    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 59 * hash + (this.errorCode != null ? this.errorCode.hashCode() : 0);
        hash = 59 * hash + (this.opId != null ? this.opId.hashCode() : 0);
        return hash;
    }

    @Override
    protected MessageRType getMessageType() {
        return MessageRType.REPLY;
    }

    @Override
    public String toString() {
        return super.toString() + "<-UniDAOperationAckMessage{" + "errorCode=" + errorCode + ", opId=" + opId + '}';
    }
    
    


}

/*******************************************************************************
 *   
 *   Copyright (C) 2014 
 *   Copyright 2014 Victor Sonora Pombo
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

package com.hi3project.unida.protocol.message.autonomousbehaviour;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.hi3project.unida.library.device.ontology.IUniDAOntologyCodec;
import com.hi3project.unida.protocol.UniDAAddress;
import com.hi3project.unida.protocol.message.ErrorCode;
import com.hi3project.unida.protocol.message.MessageType;
import com.hi3project.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p><b>Creation date:</b> 
 * 17-09-2014 </p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li> 1 , 17-09-2014 - Initial release</li>
 * </ul>
 *
 * @author Victor Sonora Pombo
 * @version 1
 */
public class UniDAABACKMessage extends UniDAMessage
{

    private ErrorCode errorCode;
    private Long opId;
    
    
    public UniDAABACKMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
        this.setCommandType(MessageType.ABACK.getTypeValue());
        this.errorCode = ErrorCode.getTypeOf(getErrorCode());
    }
    
    public UniDAABACKMessage(
            IUniDAOntologyCodec ontologyCodec, 
            UniDAAddress gatewayAddress, 
            long opId, 
            ErrorCode errorCode)
    {
        super(ontologyCodec);
        this.opId = opId;
        setCommandType(MessageType.ABACK.getTypeValue());
        this.errorCode = errorCode;
        this.setErrorCode(this.errorCode.getTypeValue());
        this.setDestination(gatewayAddress);
    }
    
    public Long getOperationId()
    {
        return this.opId;
    }
    
    @Override
    public byte[] codeMessagePayload()
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
    protected int decodeMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {

        int offset = initIndex;

        this.opId = EndianConversor.byteArrayLittleEndianToLong(bytes, offset);
        offset += EndianConversor.LONG_SIZE_BYTES;

        return offset;

    }

    @Override
    public String toString()
    {
        return super.toString() + "<-UniDAABACKMessage{" + "errorCode=" + errorCode + ", opId=" + opId + '}';
    }        
    
}

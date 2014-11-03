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

package com.unida.protocol.message.autonomousbehaviour;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * <p><b>Creation date:</b> 
 * 16-09-2014 </p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li> 1 , 16-09-2014 -> Initial release</li>
 * </ul>
 * </p>
 * @author Victor Sonora Pombo
 * @version 1
 */
public class UniDAABQueryScenariosRequestMessage extends UniDAMessage
{
    
    private long opId;

    public UniDAABQueryScenariosRequestMessage(UniDAAddress gatewayAddress, IUniDAOntologyCodec ontologyCodec, long opId)
    {
        super(ontologyCodec);
        this.opId = opId;
        setCommandType(MessageType.ABQueryScenariosRequest.getTypeValue());
        setErrorCode(ErrorCode.Ok.getTypeValue());
        this.setDestination(gatewayAddress);
    }

    
    public UniDAABQueryScenariosRequestMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }
    
    
    public long getOpId()
    {
        return this.opId;
    }
    

    @Override
    protected int decodeMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {
        // op ID
        this.opId = EndianConversor.byteArrayLittleEndianToLong(bytes, initIndex);
        initIndex += EndianConversor.LONG_SIZE_BYTES;
        
        return initIndex;
    }

    
    @Override
    protected byte[] codeMessagePayload() throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        
        try
        {
            // op ID
            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(opId, idData, 0);
            dataStream.write(idData);
        } catch (IOException ioEx)
        {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();
    }

    @Override
    public String toString()
    {
        return super.toString() + "<-UniDAABQueryScenariosRequestMessage{" + "opId=" + opId + '}';
    }
            
}

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

import com.mytechia.commons.framework.simplemessageprotocol.Message;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class UniDAABQueryScenariosReplyMessage extends UniDAMessage
{

    private List<String> scenarioIDs;
    private long opId;
    
    
    public UniDAABQueryScenariosReplyMessage(UniDAAddress gatewayAddress, IUniDAOntologyCodec ontologyCodec, List<String> scenarioIDs, long opId)
    {
        super(ontologyCodec);
        this.scenarioIDs = scenarioIDs;
        this.opId = opId;
        setCommandType(MessageType.ABQueryScenariosReply.getTypeValue());
        this.setErrorCode(ErrorCode.Ok.getTypeValue());
        this.setDestination(gatewayAddress);
    }

    
    public UniDAABQueryScenariosReplyMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }
    
    
    public List<String> getScenarioIDs()
    {
        return this.scenarioIDs;
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
        
        // Scenarios count
        short scenariosNumber = EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        initIndex += EndianConversor.SHORT_SIZE_BYTES;
        
        // Scenarios
        this.scenarioIDs = new ArrayList<>();
        StringBuilder string = new StringBuilder(20);
        for (short i = 0; i < scenariosNumber; i++)
        {
            initIndex += Message.readStringFromBytes(string, bytes, initIndex);
            this.scenarioIDs.add(string.toString());
        }
        
        return initIndex;
    }

    @Override
    protected byte[] codeMessagePayload() throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {
            //opId
            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(opId, idData, 0);
            dataStream.write(idData);

            // Scenarios count
            EndianConversor.shortToLittleEndian((short) this.getScenarioIDs().size(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);
            
            // Scenarios
            for (String scenarioId : this.getScenarioIDs())
            {
                writeString(dataStream, scenarioId);
            }
        } catch (IOException ioEx)
        {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();
    }

    @Override
    public String toString()
    {
        return super.toString() + "<-UniDAABQueryScenariosReplyMessage{" + "scenarioIDs=" + Arrays.toString(scenarioIDs.toArray()) + ", opId=" + opId + '}';
    }
        
}

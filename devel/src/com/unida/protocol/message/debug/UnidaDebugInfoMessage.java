/**
 * *****************************************************************************
 *
 * Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
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
 *****************************************************************************
 */
package com.unida.protocol.message.debug;

import com.mytechia.commons.logger.LogInfo;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Victor Sonora
 */
public class UnidaDebugInfoMessage extends UniDAMessage
{

    Collection<LogInfo> logInfos;

    public UnidaDebugInfoMessage(IUniDAOntologyCodec ontologyCodec,
            ErrorCode errCode, Collection<LogInfo> logInfos)
    {
        super(ontologyCodec);
        setCommandType(MessageType.DebugData.getTypeValue());
        setErrorCode(errCode.getTypeValue());
        this.logInfos = new ArrayList<>();
        this.logInfos.addAll(logInfos);
        setDestination(UniDAAddress.BROADCAST_ADDRESS);
    }

    public UnidaDebugInfoMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }

    @Override
    protected int decodeMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {
        int offset = initIndex;
        
        logInfos = new ArrayList<>();

        short logInfoNumber = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
        offset += EndianConversor.SHORT_SIZE_BYTES;

        for (int i = 0; i < logInfoNumber; i++)
        {
            LogInfo logInfo = new LogInfo();
            logInfo.setTime(EndianConversor.byteArrayLittleEndianToShort(bytes, offset));
            offset += EndianConversor.SHORT_SIZE_BYTES;
                        
            logInfo.setKey(EndianConversor.byteArrayLittleEndianToShort(bytes, offset));
            offset += EndianConversor.SHORT_SIZE_BYTES;
            
            logInfo.setValue(EndianConversor.byteArrayLittleEndianToUInt(bytes, offset));
            offset += EndianConversor.INT_SIZE_BYTES;
            
            logInfos.add(logInfo);
        }

        return offset;
    }

    @Override
    protected byte[] codeMessagePayload()
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        byte[] idData = new byte[EndianConversor.INT_SIZE_BYTES];

        EndianConversor.shortToLittleEndian((short) logInfos.size(), idData, 0);
        dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

        for (LogInfo logInfo : logInfos)
        {
            EndianConversor.shortToLittleEndian(logInfo.getTime(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

            EndianConversor.shortToLittleEndian(logInfo.getKey(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

            EndianConversor.uintToLittleEndian(logInfo.getValue(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);
        }


        return dataStream.toByteArray();

    }
}

/**
 * *****************************************************************************
 *
 * Copyright (C) 2009-2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2009-2013 Gervasio Varela <gervarela@picandocodigo.com>
 * Copyright (C) 2012-2013 Victor Sonora <victor@vsonora.com>
 * Copyright (C) 2009-2013 Alejandro Paz <alejandropl@gmail.com>
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author victor_local
 */
public class UniDAABQueryReplyMessage extends UniDAMessage
{

    private long opId;
    private List<UniDAABRuleVO> rules;

    public UniDAABQueryReplyMessage(UniDAAddress destination, IUniDAOntologyCodec ontologyCodec, long opId, List<UniDAABRuleVO> rules)
    {
        super(destination, ontologyCodec);
        setCommandType(MessageType.ABQueryReply.getTypeValue());
        setErrorCode(ErrorCode.Ok.getTypeValue());
        this.opId = opId;
        this.rules = rules;
    }

    public UniDAABQueryReplyMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }

    public long getOpId()
    {
        return opId;
    }

    public List<UniDAABRuleVO> getABRules()
    {
        return rules;
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

            EndianConversor.shortToLittleEndian((short) this.getABRules().size(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

            //ABRules
            for (UniDAABRuleVO rule : this.getABRules())
            {
                dataStream.write(rule.codeRulePayload(getOntologyCodec()));
            }

        } catch (IOException ioEx)
        {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();
    }

    @Override
    protected int decodeMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {
        int offset = initIndex;

        //opId
        this.opId = EndianConversor.byteArrayLittleEndianToLong(bytes, offset);
        offset += EndianConversor.LONG_SIZE_BYTES;

        //number of AB rules
        short rulesNumber = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
        offset += EndianConversor.SHORT_SIZE_BYTES;

        //ABRules contents
        this.rules = new ArrayList<>();
        for (short i = 0; i < rulesNumber; i++)
        {
            UniDAABRuleVO rule = new UniDAABRuleVO();
            offset = rule.decodePayload(bytes, offset, ontologyCodec);
            this.getABRules().add(rule);
        }

        return offset;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<-UniDAABQueryReplyMessage{" + "opId=" + opId + ", rules=" + Arrays.toString(rules.toArray()) + '}';
    }
        
}

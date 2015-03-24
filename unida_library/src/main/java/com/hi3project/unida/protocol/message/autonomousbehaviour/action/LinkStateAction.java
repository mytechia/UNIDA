/**
 * *****************************************************************************
 *
 * Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2013 Victor Sonora <victor@vsonora.com>
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
package com.hi3project.unida.protocol.message.autonomousbehaviour.action;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.hi3project.unida.library.device.ontology.IUniDAOntologyCodec;
import java.io.ByteArrayOutputStream;


/**
 *
 * @author Victor Sonora
 */
public class LinkStateAction extends RuleAction
{

    private String stateId;
    
    
    public LinkStateAction() {}
    
    
    public LinkStateAction(String stateId)
    {
        this.stateId = stateId;
    }
    

    public String getStateId()
    {
        return stateId;
    }

    public void setStateId(String stateId)
    {
        this.stateId = stateId;
    }

    @Override
    byte[] codeAction(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];

        EndianConversor.uintToLittleEndian(ontologyCodec.encodeId(this.getStateId()), idData, 0);
        dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);

        return dataStream.toByteArray();
    }

    @Override
    public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {

        initIndex = super.decodePayload(bytes, initIndex, ontologyCodec);

        // state ID        
        this.setStateId(ontologyCodec.decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, initIndex)));
        initIndex += EndianConversor.INT_SIZE_BYTES;

        return initIndex;
    }

    @Override
    public RuleActionEnum getType()
    {
        return RuleActionEnum.LINK_STATE;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<-LinkStateAction{" + "stateId=" + stateId + "}";
    }
        
}

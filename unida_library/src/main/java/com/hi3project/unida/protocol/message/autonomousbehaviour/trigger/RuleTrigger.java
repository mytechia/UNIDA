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

package com.hi3project.unida.protocol.message.autonomousbehaviour.trigger;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.hi3project.unida.library.device.ontology.IUniDAOntologyCodec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 *
 * @author Victor Sonora
 */
public abstract class RuleTrigger
{

    
    public byte [] codePayload(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        
        byte[] idData = new byte[EndianConversor.SHORT_SIZE_BYTES];
        
        EndianConversor.shortToLittleEndian((short)this.getType().getValue(), idData, 0);
        dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);
        try
        {
            dataStream.write(codeRule(ontologyCodec));
        } catch (IOException ex)
        {
            
        }
        
        return dataStream.toByteArray();
    }
    
    
    abstract byte [] codeRule(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException;
    
    
    abstract public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException;
    
        
    abstract public RuleTriggerEnum getType();

    
    @Override
    public String toString()
    {
        return " RuleTrigger";
    }
    
}

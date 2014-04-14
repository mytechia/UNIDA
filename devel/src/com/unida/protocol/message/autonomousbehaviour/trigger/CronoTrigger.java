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
package com.unida.protocol.message.autonomousbehaviour.trigger;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import java.io.ByteArrayOutputStream;


/**
 *
 * @author Victor Sonora
 */
public class CronoTrigger extends RuleTrigger
{

    private short weekday;
    private short hour;
    private short min;

    
    public CronoTrigger(){}
    
    
    public CronoTrigger(short weekday, short hour, short min)
    {
        this.weekday = weekday;
        this.hour = hour;
        this.min = min;
    }
    
    
    public short getWeekday()
    {
        return weekday;
    }
   
    public short getHour()
    {
        return hour;
    }
    
    public short getMin()
    {
        return min;
    }
  

    @Override
    byte[] codeRule(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();        
        byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];

        EndianConversor.shortToLittleEndian(this.getWeekday(), idData, 0);
        dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

        EndianConversor.shortToLittleEndian(this.getHour(), idData, 0);
        dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

        EndianConversor.shortToLittleEndian(this.getMin(), idData, 0);
        dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

        return dataStream.toByteArray();
    }

    @Override
    public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        // week day        
        this.weekday = EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        initIndex += EndianConversor.SHORT_SIZE_BYTES;
        
        // hour
        this.hour = EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        initIndex += EndianConversor.SHORT_SIZE_BYTES;
        
        // min
        this.min = EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        initIndex += EndianConversor.SHORT_SIZE_BYTES;
        
        return initIndex;
    }

    @Override
    public RuleTriggerEnum getType()
    {
        return RuleTriggerEnum.TEMPORAL;
    }

    @Override
    public String toString()
    {
        return super.toString() + "CronoTrigger{" + "weekday=" + weekday + ", hour=" + hour + ", min=" + min + "}";
    }
    
    
}

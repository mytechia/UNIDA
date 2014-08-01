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
package com.unida.protocol.message.autonomousbehaviour.trigger.statechange;


import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.state.DeviceStateValue;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Victor Sonora
 */
public class StateConditionNary extends StateCondition
{

    private Collection<DeviceStateValue> stateValues;
    
    
    public StateConditionNary(StateConditionEnum type)
    {
        this.type = type;
    }
    

    public StateConditionNary(StateConditionEnum type, Collection<DeviceStateValue> stateValues)
    {
        this.type = type;
        this.stateValues = stateValues;
    }
    
    
    public Collection<DeviceStateValue> getStateValues()
    {
        return this.stateValues;
    }
    

    @Override
    byte[] codeStateCondition(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {
            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];

            // number of stateValues
            EndianConversor.shortToLittleEndian((short)stateValues.size(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

            for (DeviceStateValue stateValue : stateValues)
            {
                dataStream.write(stateValue.code(ontologyCodec));
            }

        } catch (IOException ioEx)
        {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();
    }

    @Override
    public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        int numberOfValues = EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        initIndex += EndianConversor.SHORT_SIZE_BYTES;

        this.stateValues = new ArrayList<>();
        for (int i = 0; i < numberOfValues; i++)
        {
            DeviceStateValue stateValue = new DeviceStateValue();
            
            initIndex = stateValue.decode(bytes, initIndex, ontologyCodec);
            
            stateValue = stateValue.getSpecificImpl();
            
            this.stateValues.add(stateValue);
        }

        return initIndex;
    }

    @Override
    public String toString()
    {
        return super.toString() + "StateConditionNary{" + "stateValues=" + stateValues + "}";
    }        
    
}

/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2013 Victor Sonora <victor@vsonora.com>
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

package com.unida.protocol.message.autonomousbehaviour.trigger.statechange;

import com.mytechia.commons.framework.simplemessageprotocol.Message;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.state.DeviceStateValue;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author Victor Sonora
 */
public class StateConditionBinary extends StateCondition
{    
    
    private DeviceStateValue stateValue1;
    
    private DeviceStateValue stateValue2;
    
    
    public StateConditionBinary(StateConditionEnum type)
    {
        this.type = type;
    }
    
    
    public StateConditionBinary(StateConditionEnum type, DeviceStateValue stateValue1, DeviceStateValue stateValue2)
    {
        this.type = type;
        this.stateValue1 = stateValue1;
        this.stateValue2 = stateValue2;
    }

    @Override
    byte[] codeStateCondition(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {
            
            dataStream.write(stateValue1.code(ontologyCodec));
            dataStream.write(stateValue2.code(ontologyCodec));
            
        } catch (IOException ioEx)
        {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();
    }

    @Override
    public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {        
        
        this.stateValue1 = new DeviceStateValue();
        initIndex = this.stateValue1.decode(bytes, initIndex, ontologyCodec);
        
        this.stateValue1 = new DeviceStateValue();
        initIndex = this.stateValue2.decode(bytes, initIndex, ontologyCodec);
        
        return initIndex;
    }

    @Override
    public String toString()
    {
        return super.toString() + "StateConditionBinary{" + "stateValue1=" + stateValue1 + ", stateValue2=" + stateValue2 + "}";
    }
    
    
    
    
}

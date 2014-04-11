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
package com.unida.protocol.message.autonomousbehaviour.trigger;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.DeviceID;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateCondition;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionBinary;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionEnum;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionNary;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionNull;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionUnary;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author Victor Sonora
 */
public class StateChangeTrigger extends RuleTrigger
{

    private DeviceID triggerSource;

    private String stateId;

    private StateCondition stateCondition;
    
    
    public StateChangeTrigger()
    {        
        this.triggerSource = null;
        this.stateId = null;
        this.stateCondition = null;
    }
    
    public StateChangeTrigger(DeviceID triggerSource, String stateId, StateCondition stateCondition)
    {
        this.triggerSource = triggerSource;
        this.stateId = stateId;
        this.stateCondition = stateCondition;
    }
    

    public DeviceID getTriggerSource()
    {
        return triggerSource;
    }  

    public String getStateId()
    {
        return stateId;
    }   

    public StateCondition getStateCondition()
    {
        return stateCondition;
    }
   

    @Override
    byte[] codeRule(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {
            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];

            dataStream.write(this.getTriggerSource().getGatewayId().getID());
            EndianConversor.shortToLittleEndian(this.getTriggerSource().getDeviceId(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

            EndianConversor.uintToLittleEndian(ontologyCodec.encodeId(this.getStateId()), idData, 0);
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);

            dataStream.write(this.stateCondition.codePayload(ontologyCodec));

        } catch (IOException ioEx)
        {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();
    }

    @Override
    public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        // device ID for the trigger source
        UniDAAddress triggerSourceAddress = new UniDAAddress();
        initIndex += triggerSourceAddress.decodeAddress(bytes, initIndex);
        short dId = EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        initIndex += EndianConversor.SHORT_SIZE_BYTES;
        this.triggerSource = new DeviceID(triggerSourceAddress, dId);

        // state ID
        this.stateId = ontologyCodec.decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, initIndex));
        initIndex += EndianConversor.INT_SIZE_BYTES;

        // type of the state condition
        StateConditionEnum stateConditionType = StateConditionEnum.fromValue(EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex));
        initIndex += EndianConversor.SHORT_SIZE_BYTES;
        switch (stateConditionType)
        {
            case NO_CONDITION:
                this.stateCondition = new StateConditionNull();
                break;
            case EQUALS:
            case DIFFERENT_TO:
            case GREATER_THAN:
            case LESSER_THAN:
                
                this.stateCondition = new StateConditionUnary(stateConditionType);
                break;
            case BETWEEN:
                this.stateCondition = new StateConditionBinary(stateConditionType);
                break;
            case SOME_OF:
                this.stateCondition = new StateConditionNary(stateConditionType);
                break;
        }
        initIndex = this.stateCondition.decodePayload(bytes, initIndex, ontologyCodec);

        return initIndex;
    }        

    @Override
    public RuleTriggerEnum getType()
    {
        return RuleTriggerEnum.STATE_CHANGE;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.triggerSource);
        hash = 71 * hash + Objects.hashCode(this.stateId);
        hash = 71 * hash + Objects.hashCode(this.stateCondition);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final StateChangeTrigger other = (StateChangeTrigger) obj;
        if (!Objects.equals(this.triggerSource, other.triggerSource))
        {
            return false;
        }
        if (!Objects.equals(this.stateId, other.stateId))
        {
            return false;
        }
        return Objects.equals(this.stateCondition, other.stateCondition);
    }

    
    @Override
    public String toString()
    {
        return super.toString() + " StateChangeTrigger{" + "triggerSource=" + triggerSource + ", stateId=" + stateId + ", stateCondition=" + stateCondition + "}.";
    }
    
}

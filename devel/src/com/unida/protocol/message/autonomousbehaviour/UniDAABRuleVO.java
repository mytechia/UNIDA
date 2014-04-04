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
package com.unida.protocol.message.autonomousbehaviour;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.message.autonomousbehaviour.action.CommandExecutionAction;
import com.unida.protocol.message.autonomousbehaviour.action.RuleAction;
import com.unida.protocol.message.autonomousbehaviour.action.RuleActionEnum;
import com.unida.protocol.message.autonomousbehaviour.trigger.RuleTrigger;
import com.unida.protocol.message.autonomousbehaviour.trigger.RuleTriggerEnum;
import com.unida.protocol.message.autonomousbehaviour.trigger.StateChangeTrigger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author victor_local
 */
public class UniDAABRuleVO
{

    private long ruleID = 0;

    private RuleTrigger trigger = null;

    private RuleAction action = null;

    
    public UniDAABRuleVO() {}
    
    
    public UniDAABRuleVO(RuleTrigger trigger, RuleAction action)
    {
        this.trigger = trigger;
        this.action = action;
    }
    

    public byte[] codeRulePayload(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        try
        {
            // Rule ID
            byte[] idData = new byte[EndianConversor.INT_SIZE_BYTES];
            EndianConversor.uintToLittleEndian(ruleID, idData, 0);
            dataStream.write(idData);

            // Trigger
            dataStream.write(trigger.codePayload(ontologyCodec));

            // Action
            dataStream.write(action.codePayload(ontologyCodec));
        } catch (IOException ex)
        {

        }
        return dataStream.toByteArray();
    }

    public int decodePayload(byte[] bytes, int initIndex,
            IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {

        // Rule ID
        this.ruleID = EndianConversor.byteArrayLittleEndianToUInt(bytes, initIndex);
        initIndex += EndianConversor.INT_SIZE_BYTES;

        // Trigger type
        RuleTriggerEnum triggerType = RuleTriggerEnum.fromValue(EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex));
        initIndex += EndianConversor.INT_SIZE_BYTES;

        // Trigger payload
        if (triggerType != RuleTriggerEnum.UNKNOWN)
        {
            this.trigger = new StateChangeTrigger();
        }
        if (null != this.trigger)
        {
            this.trigger.decodePayload(bytes, initIndex, ontologyCodec);
        }

        // Action Type
        RuleActionEnum actionType = RuleActionEnum.fromValue(EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex));
        initIndex += EndianConversor.INT_SIZE_BYTES;

        // Action payload
        if (actionType != RuleActionEnum.UNKNOWN)
        {
            this.action = new CommandExecutionAction();
        }
        if (null != this.action)
        {
            initIndex = this.action.decodePayload(bytes, initIndex, ontologyCodec);
        }

        return initIndex;
    }

    public RuleTrigger getTrigger()
    {
        return trigger;
    }

    public void setTrigger(RuleTrigger trigger)
    {
        this.trigger = trigger;
    }

    public RuleAction getAction()
    {
        return action;
    }

    public void setAction(RuleAction action)
    {
        this.action = action;
    }

}

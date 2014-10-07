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
import com.unida.protocol.message.autonomousbehaviour.action.ChangeScenarioAction;
import com.unida.protocol.message.autonomousbehaviour.action.CommandExecutionAction;
import com.unida.protocol.message.autonomousbehaviour.action.LinkStateAction;
import com.unida.protocol.message.autonomousbehaviour.action.RuleAction;
import com.unida.protocol.message.autonomousbehaviour.action.RuleActionEnum;
import com.unida.protocol.message.autonomousbehaviour.action.WriteStateAction;
import com.unida.protocol.message.autonomousbehaviour.trigger.CronoTrigger;
import com.unida.protocol.message.autonomousbehaviour.trigger.RuleTrigger;
import com.unida.protocol.message.autonomousbehaviour.trigger.RuleTriggerEnum;
import com.unida.protocol.message.autonomousbehaviour.trigger.ScenarioChangeTrigger;
import com.unida.protocol.message.autonomousbehaviour.trigger.StateChangeTrigger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author victor_local
 */
public final class UniDAABRuleVO
{

    private int ruleID = 0;

    private Collection<RuleTrigger> triggers = null;

    private RuleAction action = null;

    public UniDAABRuleVO()
    {
        this.triggers = new ArrayList<>();
    }

    public UniDAABRuleVO(RuleTrigger trigger, RuleAction action)
    {
        this();
        this.addTrigger(trigger);
        this.action = action;
    }
    
    public UniDAABRuleVO(Collection<RuleTrigger> triggers, RuleAction action)
    {
        this();
        this.triggers = triggers;
        this.action = action;
    }

    public void addTrigger(RuleTrigger trigger)
    {
        this.triggers.add(trigger);
    }
    
    public StateChangeTrigger getStateChangeTrigger()
    {
        for (RuleTrigger trigger : this.triggers)
        {
            if (trigger.getType().equals(RuleTriggerEnum.STATE_CHANGE) && trigger instanceof StateChangeTrigger)
            {
                return (StateChangeTrigger)trigger;
            }
        }
        return null;
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

            // Triggers number
            EndianConversor.shortToLittleEndian((short)triggers.size(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

            // Triggers
            for (RuleTrigger trigger : this.triggers)
            {
                dataStream.write(trigger.codePayload(ontologyCodec));
            }

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
        this.ruleID = (int) EndianConversor.byteArrayLittleEndianToUInt(bytes, initIndex);
        initIndex += EndianConversor.INT_SIZE_BYTES;

        // Triggers number
        int triggersNumber = (int) EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        initIndex += EndianConversor.SHORT_SIZE_BYTES;

        // for each trigger...
        for (int i = 0; i < triggersNumber; i++)
        {
            RuleTrigger trigger = null;
            
            // Trigger type
            RuleTriggerEnum triggerType = RuleTriggerEnum.fromValue(EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex));
            initIndex += EndianConversor.SHORT_SIZE_BYTES;

            // Trigger payload       
            switch (triggerType)
            {
                case STATE_CHANGE:
                    trigger = new StateChangeTrigger();
                    break;
                case SCENARIO_CHANGE:
                    trigger = new ScenarioChangeTrigger();
                    break;
                case TEMPORAL:
                    trigger = new CronoTrigger();
                    break;
            }
            if (null != trigger)
            {
                initIndex = trigger.decodePayload(bytes, initIndex, ontologyCodec);
                this.addTrigger(trigger);
            }
        }

        // Action Type
        RuleActionEnum actionType = RuleActionEnum.fromValue(EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex));
        initIndex += EndianConversor.SHORT_SIZE_BYTES;

        // Action payload
        switch (actionType)
        {
            case COMMAND_EXECUTION:
                this.action = new CommandExecutionAction();
                break;
            case LINK_STATE:
                this.action = new LinkStateAction();
                break;
            case SCENARIO_CHANGE:
                this.action = new ChangeScenarioAction();
                break;
            case WRITE_STATE:
                this.action = new WriteStateAction();
                break;
        }
        if (null != this.action)
        {
            initIndex = this.action.decodePayload(bytes, initIndex, ontologyCodec);
        }

        return initIndex;
    }
    
    public Collection<RuleTrigger> getTriggers()
    {
        return this.triggers;
    }

    public RuleAction getAction()
    {
        return action;
    }

    public void setAction(RuleAction action)
    {
        this.action = action;
    }

    public int getRuleId()
    {
        return this.ruleID;
    }

    public void setRuleId(int ruleId)
    {
        this.ruleID = ruleId;
    }

}

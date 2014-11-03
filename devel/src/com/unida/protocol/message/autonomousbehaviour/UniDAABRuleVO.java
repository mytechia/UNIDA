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

import com.mytechia.commons.framework.simplemessageprotocol.Message;
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

/**
 *
 * @author victor_local
 */
public class UniDAABRuleVO
{

    private int ruleID = 0;
    
    private UniDAABScenarioVO scenario = null;

    private RuleTrigger trigger = null;

    private RuleAction action = null;

    
    public UniDAABRuleVO() {}
    
    
    public UniDAABRuleVO(RuleTrigger trigger, RuleAction action)
    {
        this();
        this.trigger = trigger;
        this.action = action;
    }
    
    public UniDAABRuleVO(RuleTrigger trigger, RuleAction action, UniDAABScenarioVO scenario)
    {
        this(trigger, action);
        this.scenario = scenario;
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
            
            // Scenario
            Message.writeStringInStream(dataStream, this.getScenarioID());
            
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

        // Trigger type
        RuleTriggerEnum triggerType = RuleTriggerEnum.fromValue(EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex));
        initIndex += EndianConversor.SHORT_SIZE_BYTES;

        // Trigger payload       
        switch(triggerType)
        {
            case STATE_CHANGE:
                this.trigger = new StateChangeTrigger();
                break;
            case SCENARIO_CHANGE:
                this.trigger = new ScenarioChangeTrigger();
                break;
            case TEMPORAL:
                this.trigger = new CronoTrigger();
                break;
        }
        if (null != this.trigger)
        {
            initIndex = this.trigger.decodePayload(bytes, initIndex, ontologyCodec);
        }

        // Action Type
        RuleActionEnum actionType = RuleActionEnum.fromValue(EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex));
        initIndex += EndianConversor.SHORT_SIZE_BYTES;

        // Action payload
        switch(actionType)
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
        
        // Scenario
        StringBuilder string = new StringBuilder(20);
        initIndex += Message.readStringFromBytes(string, bytes, initIndex);
        this.scenario = new UniDAABScenarioVO(string.toString());

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
    
    public int getRuleId()
    {
        return this.ruleID;
    }
    
    public void setRuleId(int ruleId)
    {
        this.ruleID = ruleId;
    }
    
    public UniDAABScenarioVO getScenario()
    {
        return this.scenario;
    }
    
    public String getScenarioID()
    {
        return (null != this.scenario)?this.scenario.getId():UniDAABScenarioVO.NULL;
    }

    @Override
    public String toString()
    {
        return "UniDAABRuleVO{" + "ruleID=" + ruleID + ", scenario=" + scenario + ", trigger=" + trigger + ", action=" + action + '}';
    }      
    
}

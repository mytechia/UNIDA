/*******************************************************************************
 *   
 *   Copyright (C) 2014 
 *   Copyright 2014 Victor Sonora Pombo
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

package com.unida.protocol.message.autonomousbehaviour.action;

import com.mytechia.commons.framework.simplemessageprotocol.Message;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Victor Sonora Pombo
 * @version 1
 */
public class ChangeScenarioAction extends RuleAction
{

    private String scenarioID;
    
    
    public ChangeScenarioAction()
    {
        this.scenarioID = null;
    }
    
    public ChangeScenarioAction(String scenarioID)
    {
        this.scenarioID = scenarioID;
    }
    
    
    @Override
    byte[] codeAction(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        try
        {
            // Scenario
            Message.writeStringInStream(dataStream, this.scenarioID);
            
        } catch (IOException ex)
        {

        }
        return dataStream.toByteArray();
    }
    
    @Override
    public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        
        initIndex = super.decodePayload(bytes, initIndex, ontologyCodec);
        
        // Scenario
        StringBuilder string = new StringBuilder(20);
        initIndex += Message.readStringFromBytes(string, bytes, initIndex);
        this.scenarioID = string.toString();
        
        return initIndex;
    }

    @Override
    public RuleActionEnum getType()
    {
        return RuleActionEnum.SCENARIO_CHANGE;
    }
    
    public String getScenarioID()
    {
        return this.scenarioID;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.scenarioID);
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
        final ChangeScenarioAction other = (ChangeScenarioAction) obj;
        return Objects.equals(this.scenarioID, other.scenarioID);
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "ChangeScenarioAction{" + "scenario ID=" + scenarioID + "}";
    }  

}

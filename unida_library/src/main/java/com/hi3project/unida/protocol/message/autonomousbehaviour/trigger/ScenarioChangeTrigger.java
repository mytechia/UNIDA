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

package com.hi3project.unida.protocol.message.autonomousbehaviour.trigger;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.hi3project.unida.library.device.ontology.IUniDAOntologyCodec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Victor Sonora Pombo
 * @version 1
 */
public class ScenarioChangeTrigger extends RuleTrigger
{

    private String scenarioID;
    
    
    public ScenarioChangeTrigger()
    {
        this.scenarioID = null;
    }
    
    public ScenarioChangeTrigger(String scenarioID)
    {
        this.scenarioID = scenarioID;
    }
    
    
    @Override
    byte[] codeRule(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        try
        {
            // Scenario
            Command.writeStringInStream(dataStream, this.scenarioID);
            
        } catch (IOException ex)
        {

        }
        return dataStream.toByteArray();
    }

    @Override
    public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        // Scenario
        StringBuilder string = new StringBuilder(20);
        initIndex += Command.readStringFromBytes(string, bytes, initIndex);
        this.scenarioID = string.toString();
        
        return initIndex;
    }

    @Override
    public RuleTriggerEnum getType()
    {
        return RuleTriggerEnum.SCENARIO_CHANGE;
    }
    
    public String getScenarioID()
    {
        return this.scenarioID;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 71 * hash + this.scenarioID.hashCode();
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
        final ScenarioChangeTrigger other = (ScenarioChangeTrigger) obj;
        if (!Objects.equals(this.scenarioID, other.scenarioID))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "<-ScenarioChangeTrigger{" + "scenario ID=" + scenarioID + "}";
    }

}

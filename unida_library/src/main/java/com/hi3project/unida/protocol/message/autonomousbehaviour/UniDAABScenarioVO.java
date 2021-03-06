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

package com.hi3project.unida.protocol.message.autonomousbehaviour;

/**
 * <p>
 *  <b>Description:</b>
 *  Simple VO class that holds information about scenarios for autonomous
 * behaviour rules.
 *  An scenario is intented to handle a group of rules.
 *
 * 
 * <p>
 *  Responsabilities:
 *
 * <ul>
 * <li>know its rules (not implemented for this version.</li>
 * <li>know its identifier.</li>
 * <li>know a description.</li>
 * </ul>
 * 
 * <p>
 * 
 *
 *
 * <p><b>Creation date:</b> 
 * 11-09-2014 </p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li> 1 , 11-09-2014 - Initial release</li>
 * </ul>
 *
 * @author Victor Sonora Pombo
 * @version 1
 */
public class UniDAABScenarioVO 
{
    
    public static final String NULL = "";    
    
    private String scenarioID = "";
    
    private String description = null;
    
    
    public UniDAABScenarioVO(String scenarioID)
    {
        this.scenarioID = scenarioID;
    }
    
    
    public String getId()
    {
        return this.scenarioID;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "UniDAABScenarioVO{" + "scenarioID=" + scenarioID + ", description=" + description + '}';
    }

}

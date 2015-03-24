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

package com.hi3project.unida.protocol.message.autonomousbehaviour.trigger;

/**
 *
 * @author Victor Sonora
 */
public enum RuleTriggerEnum 
{
    
    UNKNOWN(0),
    STATE_CHANGE(1),
    TEMPORAL(2),
    SCENARIO_CHANGE(3);
    
    
    private int triggerType;


    RuleTriggerEnum(int triggerType) {
        this.triggerType = triggerType;
    }


    public int getValue()
    {
        return triggerType;
    }
    
    public static RuleTriggerEnum fromValue(int givenValue)
    {

        if (givenValue == STATE_CHANGE.ordinal()) {
            return STATE_CHANGE;
        }
        else if (givenValue == TEMPORAL.ordinal()) {
            return TEMPORAL;
        }
        else if (givenValue == SCENARIO_CHANGE.ordinal()) {
            return SCENARIO_CHANGE;
        }
        return UNKNOWN;
    }

    @Override
    public String toString()
    {
        return "RuleTriggerEnum{" + "triggerType=" + triggerType + '}';
    }
        
}

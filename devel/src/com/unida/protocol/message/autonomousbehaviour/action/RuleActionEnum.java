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

package com.unida.protocol.message.autonomousbehaviour.action;

/**
 *
 * @author Victor Sonora
 */
public enum RuleActionEnum 
{
    
    UNKNOWN(0), LINK_STATE(1), WRITE_STATE(2), COMMAND_EXECUTION(3), SCENARIO_CHANGE(4);

    
    private int actionType;
    

    RuleActionEnum(int actionType) {
        this.actionType = actionType;
    }


    public int getValue()
    {
        return actionType;
    }
    
    public static RuleActionEnum fromValue(int givenValue)
    {

        if (givenValue == LINK_STATE.ordinal()) {
            return LINK_STATE;
        }
        if (givenValue == WRITE_STATE.ordinal()) {
            return WRITE_STATE;
        }
        else if (givenValue == COMMAND_EXECUTION.ordinal()) {
            return COMMAND_EXECUTION;
        }
        else if (givenValue == SCENARIO_CHANGE.ordinal()) {
            return SCENARIO_CHANGE;
        }
        return UNKNOWN;
    }
    
}

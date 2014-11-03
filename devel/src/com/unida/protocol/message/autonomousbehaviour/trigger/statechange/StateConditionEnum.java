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


/**
 *
 * @author Victor Sonora
 */
public enum StateConditionEnum 
{
    
    NO_CONDITION(0),
    EQUALS(1),
    DIFFERENT_TO(2),
    GREATER_THAN(3),
    LESSER_THAN(4),
    BETWEEN(5),
    SOME_OF(6);
    
    private int value;
    
    StateConditionEnum(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public static StateConditionEnum fromValue(int givenValue)
    {

        if (givenValue == NO_CONDITION.ordinal()) {
            return NO_CONDITION;
        }
        else if (givenValue == DIFFERENT_TO.ordinal()) {
            return DIFFERENT_TO;
        }
        else if (givenValue == GREATER_THAN.ordinal()) {
            return GREATER_THAN;
        }
        else if (givenValue == LESSER_THAN.ordinal()) {
            return LESSER_THAN;
        }
        else if (givenValue == BETWEEN.ordinal()) {
            return BETWEEN;
        }
        else if (givenValue == SOME_OF.ordinal()) {
            return SOME_OF;
        }
        return EQUALS;
    }

    @Override
    public String toString()
    {
        return "StateConditionEnum{" + "value=" + value + '}';
    }
    
}

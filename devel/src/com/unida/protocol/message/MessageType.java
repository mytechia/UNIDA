/*******************************************************************************
 *   
 *   Copyright (C) 2009-2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2009-2013 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2012-2013 Victor Sonora <victor@vsonora.com>
 *   Copyright (C) 2009-2013 Alejandro Paz <alejandropl@gmail.com>
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


package com.unida.protocol.message;

/** 
 *
 * @author Alex
 * @version 1
 *
 * File: CommandType.java
 * Date: 22/02/2008
 * Changelog:
 *
 *      22/02/2008  --  Initial version
 */
public enum MessageType {
    
    NoCommand                       ((byte) 0),
    GenericReply                    ((byte) 1),
    DiscoverGatewayDevices          ((byte) 2),
    DiscoverGatewayDevicesReply     ((byte) 3),
    GatewayHeartbeat                ((byte) 4),
    GatewayStatusReport             ((byte) 5),
    GatewayStatusReportReply        ((byte) 6),
    QueryDeviceState                ((byte) 7),
    QueryDeviceStateReply           ((byte) 8),
    QueryDeviceStates               ((byte) 9),
    QueryDeviceStatesReply          ((byte) 10),
    ExecuteCommand                  ((byte) 11),
    SuscribeTo                      ((byte) 12),
    UnsuscribeFrom                  ((byte) 13),
    Notification                    ((byte) 14),
    WriteState                      ((byte) 15),
    ABAddRule                       ((byte) 17),
    ABRemoveRule                    ((byte) 18),
    ABQueryRequest                  ((byte) 19),
    ABQueryReply                    ((byte) 20),
    DebugInit                       ((byte) 101),
    DebugData                       ((byte) 106);
    
    
    private byte typeValue;
    
    
    
    MessageType(byte typeValue) {
        this.typeValue = typeValue;
    }
   
    
    
    public byte getTypeValue() {
        return typeValue;
    }
    

    public static MessageType getTypeOf(byte typeValue) {
        if (typeValue == NoCommand.typeValue)
            return NoCommand;
        else if (typeValue == GenericReply.typeValue)
            return GenericReply;
        else if (typeValue == DiscoverGatewayDevices.typeValue)
            return DiscoverGatewayDevices;
        else if (typeValue == DiscoverGatewayDevicesReply.typeValue)
            return DiscoverGatewayDevicesReply;
        else if (typeValue == GatewayHeartbeat.typeValue)
            return GatewayHeartbeat;
        else if (typeValue == GatewayStatusReport.typeValue)
            return GatewayStatusReport;
        else if (typeValue == QueryDeviceState.typeValue)
            return QueryDeviceState;
        else if (typeValue == QueryDeviceStates.typeValue)
            return QueryDeviceStates;
        else if (typeValue == QueryDeviceStateReply.typeValue)
            return QueryDeviceStateReply;
        else if (typeValue == QueryDeviceStatesReply.typeValue)
            return QueryDeviceStatesReply;
        else if (typeValue == ExecuteCommand.typeValue)
            return ExecuteCommand;
        else if (typeValue == SuscribeTo.typeValue)
            return SuscribeTo;
        else if (typeValue == UnsuscribeFrom.typeValue)
            return UnsuscribeFrom;
        else if (typeValue == Notification.typeValue)
            return Notification;
        else if (typeValue == ABAddRule.typeValue)
            return ABAddRule;
        else if (typeValue == ABRemoveRule.typeValue)
            return ABRemoveRule;
        else if (typeValue == ABQueryRequest.typeValue)
            return ABQueryRequest;
        else if (typeValue == ABQueryReply.typeValue)
            return ABQueryReply;
        else
            return null;
    }

    
}


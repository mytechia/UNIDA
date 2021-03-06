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
package com.hi3project.unida.protocol;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.hi3project.unida.library.device.ontology.IUniDAOntologyCodec;
import com.hi3project.unida.protocol.message.ErrorCode;
import com.hi3project.unida.protocol.message.MessageType;
import com.hi3project.unida.protocol.message.UniDAMessage;
import com.hi3project.unida.protocol.message.ack.UniDAOperationAckMessage;
import com.hi3project.unida.protocol.message.autonomousbehaviour.UniDAABACKMessage;
import com.hi3project.unida.protocol.message.autonomousbehaviour.UniDAABAddMessage;
import com.hi3project.unida.protocol.message.autonomousbehaviour.UniDAABChangeScenarioMessage;
import com.hi3project.unida.protocol.message.autonomousbehaviour.UniDAABQueryReplyMessage;
import com.hi3project.unida.protocol.message.autonomousbehaviour.UniDAABQueryRequestMessage;
import com.hi3project.unida.protocol.message.autonomousbehaviour.UniDAABQueryScenariosReplyMessage;
import com.hi3project.unida.protocol.message.autonomousbehaviour.UniDAABQueryScenariosRequestMessage;
import com.hi3project.unida.protocol.message.autonomousbehaviour.UniDAABRemoveMessage;
import com.hi3project.unida.protocol.message.debug.UnidaDebugInfoMessage;
import com.hi3project.unida.protocol.message.debug.UnidaDebugInitMessage;
import com.hi3project.unida.protocol.message.discovery.DiscoverUniDAGatewayDevicesReplyMessage;
import com.hi3project.unida.protocol.message.discovery.DiscoverUniDAGatewayDevicesRequestMessage;
import com.hi3project.unida.protocol.message.discovery.UniDAGatewayHeartbeatMessage;
import com.hi3project.unida.protocol.message.modifyinfo.UniDAModifyDeviceInfoMessage;
import com.hi3project.unida.protocol.message.modifyinfo.UniDAModifyGatewayInfoMessage;
import com.hi3project.unida.protocol.message.notification.UniDANotificationMessage;
import com.hi3project.unida.protocol.message.notification.UniDANotificationSuscriptionRequestMessage;
import com.hi3project.unida.protocol.message.notification.UniDANotificationUnsuscriptionRequestMessage;
import com.hi3project.unida.protocol.message.querydevice.UniDAQueryDeviceReplyMessage;
import com.hi3project.unida.protocol.message.querydevice.UniDAQueryDeviceRequestMessage;
import com.hi3project.unida.protocol.message.querydevicestate.UniDAQueryDeviceStateReplyMessage;
import com.hi3project.unida.protocol.message.querydevicestate.UniDAQueryDeviceStateRequestMessage;
import com.hi3project.unida.protocol.message.querydevicestate.UniDAWriteDeviceStateRequestMessage;
import com.hi3project.unida.protocol.message.sendcommand.UniDASendCommandToDeviceMessage;
import com.hi3project.unida.protocol.message.statusreport.UniDAGatewayStatusReportReplyMessage;
import com.hi3project.unida.protocol.message.statusreport.UniDAGatewayStatusReportRequestMessage;

/**
 * <p>
 * <b>
 * UniDA protocol message factory.
 *
 * It contains the logic to create every type of message that is used by the UniDA
 * UniDA communications protocol.
 *
 * </b>
 *
 *
 *
 * <p>
 * <b>Creation date:</b> 26-01-2010</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>1 - 26-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class DefaultMessageFactory implements IMessageFactory
{

    private IUniDAOntologyCodec ontologyCodec;

    public DefaultMessageFactory(IUniDAOntologyCodec ontologyCodec)
    {
        this.ontologyCodec = ontologyCodec;
    }

    @Override
    public UniDAMessage createUnidaMessage(byte[] msgData) throws MessageFormatException
    {

        byte msgType = Command.getMessageType(msgData);

        if (msgType == MessageType.DiscoverGatewayDevices.getTypeValue())
        {
            return createDiscoverUnidaGatewayMessage(msgType, msgData);
        } else if (msgType == MessageType.DiscoverGatewayDevicesReply.getTypeValue())
        {
            return createDiscoverUnidaGatewayReplyMessage(msgType, msgData);
        } else if (msgType == MessageType.GenericReply.getTypeValue())
        {
            return createUnidaOperationAckMessage(msgType, msgData);
        } else if (msgType == MessageType.GatewayHeartbeat.getTypeValue())
        {
            return createUnidaGatewayHeartbeatMessage(msgType, msgData);
        } else if (msgType == MessageType.SuscribeTo.getTypeValue())
        {
            return createUnidaNotificationSuscriptionRequestMessage(msgType, msgData);
        } else if (msgType == MessageType.UnsuscribeFrom.getTypeValue())
        {
            return createUnidaNotificationUnsuscriptionRequestMessage(msgType, msgData);
        } else if (msgType == MessageType.QueryDeviceStates.getTypeValue())
        {
            return createUnidaQueryDeviceMessage(msgType, msgData);
        } else if (msgType == MessageType.QueryDeviceStatesReply.getTypeValue())
        {
            return createUnidaQueryDeviceReplyMessage(msgType, msgData);
        } else if (msgType == MessageType.QueryDeviceState.getTypeValue())
        {
            return createUnidaQueryDeviceStateMessage(msgType, msgData);
        } else if (msgType == MessageType.QueryDeviceStateReply.getTypeValue())
        {
            return createUnidaQueryDeviceStateReplyMessage(msgType, msgData);
        } else if (msgType == MessageType.WriteState.getTypeValue())
        {
            return createUnidaWriteStateRequestMessage(msgType, msgData);
        } else if (msgType == MessageType.GatewayStatusReport.getTypeValue())
        {
            return createUnidaGatewayStatusReportMessage(msgType, msgData);
        } else if (msgType == MessageType.Notification.getTypeValue())
        {
            return createUnidaNotificationMessage(msgType, msgData);
        } else if (msgType == MessageType.ExecuteCommand.getTypeValue())
        {
            return createUnidaSendCommandMessage(msgType, msgData);
        } else if (msgType == MessageType.DebugInit.getTypeValue())
        {
            return createUnidaDebugInitMessage(msgType, msgData);
        } else if (msgType == MessageType.DebugData.getTypeValue())
        {
            return createUnidaDebugDataMessage(msgType, msgData);
        } else if (msgType == MessageType.ABAddRule.getTypeValue())
        {
            return createUnidaAddABRuleMessage(msgType, msgData);
        } else if (msgType == MessageType.ABRemoveRule.getTypeValue())
        {
            return createUnidaRemoveABRuleMessage(msgType, msgData);
        } else if (msgType == MessageType.ABQueryRequest.getTypeValue())
        {
            return createUnidaABRulesQueryRequestMessage(msgType, msgData);
        } else if (msgType == MessageType.ABQueryReply.getTypeValue())
        {
            return createUnidaABRulesQueryReplyMessage(msgType, msgData);
        } else if (msgType == MessageType.ABChangeScenario.getTypeValue())
        {
            return createUnidaABChangeScenarioRequestMessage(msgType, msgData);
        } else if (msgType == MessageType.ABQueryScenariosRequest.getTypeValue())
        {
            return createUnidaABQueryScenariosRequest(msgType, msgData);
        } else if (msgType == MessageType.ABQueryScenariosReply.getTypeValue())
        {
            return createUnidaABQueryScenariosReply(msgType, msgData);
        } else if (msgType == MessageType.ModifyGatewayInfo.getTypeValue())
        {
            return createModifyUnidaGatewayMessage(msgType, msgData);
        } else if (msgType == MessageType.ModifyDeviceInfo.getTypeValue())
        {
            return createModifyUnidaDeviceMessage(msgType, msgData);
        } else if (msgType == MessageType.ABACK.getTypeValue())
        {
            return createUnidaABACKMessage(msgType, msgData);
        } else
        {
            throw new MessageFormatException("Unsupported message type: " + msgType + ".");
        }

    }

    UniDASendCommandToDeviceMessage createUnidaSendCommandMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {
        if (msgType == MessageType.ExecuteCommand.getTypeValue())
        {
            return new UniDASendCommandToDeviceMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }
    }

    UniDAGatewayHeartbeatMessage createUnidaHeartbeatMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.GatewayHeartbeat.getTypeValue())
        {

            return new UniDAGatewayHeartbeatMessage(msgData, ontologyCodec);

        } else
        {
            return null;
        }

    }

    DiscoverUniDAGatewayDevicesRequestMessage createDiscoverUnidaGatewayMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.DiscoverGatewayDevices.getTypeValue())
        {

            return new DiscoverUniDAGatewayDevicesRequestMessage(msgData, ontologyCodec);

        } else
        {
            return null;
        }

    }

    DiscoverUniDAGatewayDevicesReplyMessage createDiscoverUnidaGatewayReplyMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.DiscoverGatewayDevicesReply.getTypeValue())
        {
            return new DiscoverUniDAGatewayDevicesReplyMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }
    
    UniDAModifyGatewayInfoMessage createModifyUnidaGatewayMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ModifyGatewayInfo.getTypeValue())
        {
            return new UniDAModifyGatewayInfoMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }
    
    UniDAModifyDeviceInfoMessage createModifyUnidaDeviceMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ModifyDeviceInfo.getTypeValue())
        {
            return new UniDAModifyDeviceInfoMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAOperationAckMessage createUnidaOperationAckMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.GenericReply.getTypeValue())
        {
            return new UniDAOperationAckMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAGatewayHeartbeatMessage createUnidaGatewayHeartbeatMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.GatewayHeartbeat.getTypeValue())
        {
            return new UniDAGatewayHeartbeatMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDANotificationSuscriptionRequestMessage createUnidaNotificationSuscriptionRequestMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.SuscribeTo.getTypeValue())
        {
            return new UniDANotificationSuscriptionRequestMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDANotificationUnsuscriptionRequestMessage createUnidaNotificationUnsuscriptionRequestMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.UnsuscribeFrom.getTypeValue())
        {
            return new UniDANotificationUnsuscriptionRequestMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDANotificationMessage createUnidaNotificationMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.Notification.getTypeValue())
        {
            return new UniDANotificationMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAQueryDeviceRequestMessage createUnidaQueryDeviceMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        byte errCode = Command.getMessageErrorCode(msgData);

        if (msgType == MessageType.QueryDeviceStates.getTypeValue())
        {
            return new UniDAQueryDeviceRequestMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAQueryDeviceReplyMessage createUnidaQueryDeviceReplyMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        byte errCode = Command.getMessageErrorCode(msgData);

        if (msgType == MessageType.QueryDeviceStatesReply.getTypeValue())
        {
            return new UniDAQueryDeviceReplyMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAQueryDeviceStateRequestMessage createUnidaQueryDeviceStateMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.QueryDeviceState.getTypeValue())
        {
            return new UniDAQueryDeviceStateRequestMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAQueryDeviceStateReplyMessage createUnidaQueryDeviceStateReplyMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.QueryDeviceStateReply.getTypeValue())
        {
            return new UniDAQueryDeviceStateReplyMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAWriteDeviceStateRequestMessage createUnidaWriteStateRequestMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.WriteState.getTypeValue())
        {
            return new UniDAWriteDeviceStateRequestMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAMessage createUnidaGatewayStatusReportMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        byte errCode = Command.getMessageErrorCode(msgData);

        if (msgType == MessageType.GatewayStatusReport.getTypeValue())
        {
            if (errCode == ErrorCode.Null.getTypeValue())
            {
                return new UniDAGatewayStatusReportRequestMessage(msgData, ontologyCodec);
            } else
            {
                return new UniDAGatewayStatusReportReplyMessage(msgData, ontologyCodec);
            }
        } else
        {
            return null;
        }

    }

    UniDAABAddMessage createUnidaAddABRuleMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ABAddRule.getTypeValue())
        {
            return new UniDAABAddMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAABRemoveMessage createUnidaRemoveABRuleMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ABRemoveRule.getTypeValue())
        {
            return new UniDAABRemoveMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAABQueryReplyMessage createUnidaABRulesQueryReplyMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ABQueryReply.getTypeValue())
        {
            return new UniDAABQueryReplyMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAABQueryRequestMessage createUnidaABRulesQueryRequestMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ABQueryRequest.getTypeValue())
        {
            return new UniDAABQueryRequestMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UniDAABChangeScenarioMessage createUnidaABChangeScenarioRequestMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ABChangeScenario.getTypeValue())
        {
            return new UniDAABChangeScenarioMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }
    
    UniDAABQueryScenariosRequestMessage createUnidaABQueryScenariosRequest(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ABQueryScenariosRequest.getTypeValue())
        {
            return new UniDAABQueryScenariosRequestMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }
    
    UniDAABQueryScenariosReplyMessage createUnidaABQueryScenariosReply(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ABQueryScenariosReply.getTypeValue())
        {
            return new UniDAABQueryScenariosReplyMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }
   
    UniDAABACKMessage createUnidaABACKMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.ABACK.getTypeValue())
        {
            return new UniDAABACKMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UnidaDebugInitMessage createUnidaDebugInitMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.DebugInit.getTypeValue())
        {
            return new UnidaDebugInitMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

    UnidaDebugInfoMessage createUnidaDebugDataMessage(byte msgType, byte[] msgData) throws MessageFormatException
    {

        if (msgType == MessageType.DebugData.getTypeValue())
        {
            return new UnidaDebugInfoMessage(msgData, ontologyCodec);
        } else
        {
            return null;
        }

    }

}

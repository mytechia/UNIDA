/**
 * *****************************************************************************
 *
 * Copyright (C) 2009-2013 Mytech Ingenieria Aplicada
 * <http://www.mytechia.com>
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
package com.unida.library.core;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.unida.library.device.ontology.IDeviceAccessLayerOntologyFacade;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.ontology.exception.ClassNotFoundInOntologyException;
import com.unida.library.device.DeviceID;
import com.unida.library.device.Gateway;
import com.unida.library.device.exception.UniDAIDFormatException;
import com.unida.library.notification.INotificationCallback;
import com.unida.library.device.to.DeviceConversionOperations;
import com.unida.library.device.to.GatewayTO;
import com.unida.log.UniDALoggers;
import com.unida.library.manage.IUniDAManagementFacade;
import com.unida.protocol.IUniDACommChannel;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDAMessage;
import com.unida.protocol.message.ack.UniDAOperationAckMessage;
import com.unida.protocol.message.discovery.DiscoverUniDAGatewayDevicesReplyMessage;
import com.unida.protocol.handling.IUniDAProtocolMessageHandler;
import com.unida.protocol.handling.UniDAProtocolMessageProcessor;
import com.unida.protocol.message.discovery.DiscoverUniDAGatewayDevicesRequestMessage;
import com.unida.protocol.message.discovery.UniDAGatewayHeartbeatMessage;
import com.unida.protocol.message.notification.UniDANotificationMessage;
import com.unida.protocol.message.notification.UniDANotificationSuscriptionRequestMessage;
import com.unida.protocol.message.notification.UniDANotificationUnsuscriptionRequestMessage;
import com.unida.protocol.message.querydevice.DeviceStateWithValue;
import com.unida.protocol.message.querydevice.UniDAQueryDeviceReplyMessage;
import com.unida.protocol.message.querydevice.UniDAQueryDeviceRequestMessage;
import com.unida.protocol.message.querydevicestate.UniDAQueryDeviceStateReplyMessage;
import com.unida.protocol.message.querydevicestate.UniDAQueryDeviceStateRequestMessage;
import com.unida.protocol.message.querydevicestate.UniDAWriteDeviceStateRequestMessage;
import com.unida.protocol.reception.IUniDAProtocolMessageProcessingQueue;
import com.unida.protocol.reception.LinkedListUniDAProtocolMessageProcessingQueue;
import com.unida.protocol.reception.UniDAProtocolMessageReceiver;
import com.unida.protocol.message.sendcommand.UniDASendCommandToDeviceMessage;
import java.util.Collection;
import java.util.logging.Level;

/**
 * <p><b> </b></br>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 19-01-2010</p> <p><b>Changelog:</b></br> <ul> <li>1
 * - 19-01-2010<\br> Initial release</li> </ul> </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class DefaultUniDAFacade extends AbstractUniDAFacadeHelper implements IUniDANetworkFacade
{

    protected IUniDACommChannel commChannel;
    protected UniDAProtocolMessageReceiver msgReceiver;
    protected UniDAProtocolMessageProcessor msgProcessor;
    protected IUniDAProtocolMessageProcessingQueue msgQueue;
    protected IUniDAManagementFacade devManagement;
    protected IDeviceAccessLayerOntologyFacade ontologyFacade;
    protected IUniDAOntologyCodec ontologyCodec;

    public DefaultUniDAFacade(
            IUniDACommChannel commChannel,
            IUniDAManagementFacade devManagement,
            IDeviceAccessLayerOntologyFacade ontologyFacade,
            IUniDAOntologyCodec ontologyCodec)
    {

        super();

        this.commChannel = commChannel;
        this.devManagement = devManagement;
        this.msgQueue = new LinkedListUniDAProtocolMessageProcessingQueue();

        this.ontologyFacade = ontologyFacade;
        this.ontologyCodec = ontologyCodec;

        this.msgProcessor = new UniDAProtocolMessageProcessor(commChannel, this.msgQueue);
        this.msgReceiver = new UniDAProtocolMessageReceiver(commChannel, this.msgQueue);

        registerMessageProcessors();

    }

    private void registerMessageProcessors()
    {

        this.msgProcessor.registerMessageHandler(new DiscoverUniDAGatewayReplyMessageHandler());
        this.msgProcessor.registerMessageHandler(new UniDANotificationMessageHandler());
        this.msgProcessor.registerMessageHandler(new UniDAOperationAckMessageHandler());
        this.msgProcessor.registerMessageHandler(new UniDAQueryDeviceReplyMessageHandler());
        this.msgProcessor.registerMessageHandler(new UniDAQueryDeviceStateReplyMessageHandler());
        this.msgProcessor.registerMessageHandler(new UnidaHeartbeatMessageHandler());        

    }

    public void start()
    {
        this.msgProcessor.startProcessing();
        this.msgReceiver.startReception();
    }

    public void stop()
    {
        this.msgProcessor.startProcessing();
        this.msgReceiver.startReception();
    }

    public IUniDACommChannel getCommChannel()
    {
        return this.commChannel;
    }

    public UniDAProtocolMessageProcessor getMessageProcessor()
    {
        return this.msgProcessor;
    }

    public UniDAProtocolMessageReceiver getMessageReceiver()
    {
        return this.msgReceiver;
    }

    @Override
    public void queryDeviceState(long opId, DeviceID deviceId, String stateId, IUnidaNetworkFacadeCallback callback) throws CommunicationException 
    {
        addOperationCallback(opId, deviceId, callback);
        this.commChannel.sendMessage(deviceId, new UniDAQueryDeviceStateRequestMessage(this.ontologyCodec, opId, deviceId, stateId));
    }    

    @Override
    public void queryDevice(long opId, DeviceID deviceId, IUnidaNetworkFacadeCallback callback) throws CommunicationException 
    {
        addOperationCallback(opId, deviceId, callback);
        this.commChannel.sendMessage(deviceId, new UniDAQueryDeviceRequestMessage(this.ontologyCodec, opId, deviceId));
    }
    
    @Override
    public void writeDeviceState(long opId, DeviceID deviceId, String stateId, 
        String stateValueId, String stateValue, IUnidaNetworkFacadeCallback callback) throws CommunicationException
    {
        addOperationCallback(opId, deviceId, callback);
        this.commChannel.sendMessage(deviceId, 
                new UniDAWriteDeviceStateRequestMessage(this.ontologyCodec, opId, deviceId, stateId, stateValueId, stateValue));
    }

    @Override
    public void sendCommand(long opId, DeviceID deviceId, String funcId, String cmdId,
            String[] params, IUnidaNetworkFacadeCallback callback) throws CommunicationException 
    {
        addOperationCallback(opId, deviceId, callback);
        this.commChannel.sendMessage(deviceId, new UniDASendCommandToDeviceMessage(this.ontologyCodec, opId, deviceId, funcId, cmdId, params));
    }

    @Override
    public void suscribeTo(long notificationId, DeviceID deviceId, String stateId, String[] params, INotificationCallback callback) throws CommunicationException
    {
        addNotificationCallback(notificationId, deviceId, callback);
        this.commChannel.sendMessage(deviceId, new UniDANotificationSuscriptionRequestMessage(this.ontologyCodec, notificationId, deviceId, stateId, params));
    }

    @Override
    public void unsuscribeFrom(long notificationId, DeviceID deviceId, String stateId, String[] params, INotificationCallback callback) throws CommunicationException
    {
        removeNotificationCallback(notificationId, deviceId);
        this.commChannel.sendMessage(deviceId, new UniDANotificationUnsuscriptionRequestMessage(this.ontologyCodec, notificationId, deviceId, stateId, params));
    }

    @Override
    public void renewSuscription(long notificationId, DeviceID deviceId, String stateId, String[] params, INotificationCallback callback) throws CommunicationException
    {
        removeNotificationCallback(notificationId, deviceId);
        suscribeTo(notificationId, deviceId, stateId, params, callback);
    }

    @Override
    public boolean supports(Gateway dev)
    {
        return true;
    }
    

    protected class OperationEntry
    {

        private long ticketId;
        private String deviceId;

        public OperationEntry(long ticketId, String deviceId)
        {
            this.ticketId = ticketId;
            this.deviceId = deviceId;
        }

        public String getDeviceId()
        {
            return deviceId;
        }

        public long getTicketId()
        {
            return ticketId;
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
            final OperationEntry other = (OperationEntry) obj;
            if (this.ticketId != other.ticketId)
            {
                return false;
            }
            if ((this.deviceId == null) ? (other.deviceId != null) : !this.deviceId.equals(other.deviceId))
            {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode()
        {
            int hash = 3;
            hash = 83 * hash + (int) (this.ticketId ^ (this.ticketId >>> 32));
            hash = 83 * hash + (this.deviceId != null ? this.deviceId.hashCode() : 0);
            return hash;
        }
    }

    private class UniDAQueryDeviceStateReplyMessageHandler implements IUniDAProtocolMessageHandler
    {

        @Override
        public boolean supports(UniDAMessage msg)
        {
            return MessageType.getTypeOf(msg.getCommandType()) == MessageType.QueryDeviceStateReply;
        }

        @Override
        public UniDAMessage handle(UniDAMessage msg)
        {

            if (msg instanceof UniDAQueryDeviceStateReplyMessage)
            {

                UniDAQueryDeviceStateReplyMessage reply = (UniDAQueryDeviceStateReplyMessage) msg;
                IUnidaNetworkFacadeCallback cback = removeOperationCallback(reply.getOpId(), reply.getDeviceID());

                if (cback != null)
                {
                    if (reply.getErrorCode() == ErrorCode.Ok.getTypeValue())
                    {
                        cback.notifyDeviceState(reply.getOpId(), reply.getDeviceID(), reply.getStateId(), reply.getValueId(), reply.getValue());
                    } else
                    {
                        cback.notifyDeviceFailure(reply.getOpId(), reply.getDeviceID(), "Error"); //TODO --> add specific error messages
                    }
                }

            }

            return null;

        }
    }

    private class UniDAQueryDeviceReplyMessageHandler implements IUniDAProtocolMessageHandler
    {

        @Override
        public boolean supports(UniDAMessage msg)
        {
            return MessageType.getTypeOf(msg.getCommandType()) == MessageType.QueryDeviceStatesReply;
        }

        @Override
        public UniDAMessage handle(UniDAMessage msg)
        {

            if (msg instanceof UniDAQueryDeviceReplyMessage)
            {

                UniDAQueryDeviceReplyMessage reply = (UniDAQueryDeviceReplyMessage) msg;
                IUnidaNetworkFacadeCallback cback = removeOperationCallback(reply.getOpId(), reply.getDeviceID());

                if (null != cback)
                {
                    if (reply.getErrorCode() == ErrorCode.Ok.getTypeValue())
                    {

                        Collection<DeviceStateWithValue> states = reply.getStateValues();

                        String[] stateIds = new String[states.size()];
                        String[] valueIds = new String[states.size()];
                        String[] values = new String[states.size()];
                        int i = 0;
                        for (DeviceStateWithValue stv : states)
                        {
                            stateIds[i] = stv.getStateId();
                            valueIds[i] = stv.getValueId();
                            values[i] = stv.getValue();
                            i++;
                        }

                        cback.notifyDeviceStates(reply.getOpId(), reply.getDeviceID(), stateIds, valueIds, values);
                    } else
                    {
                        cback.notifyDeviceFailure(reply.getOpId(), reply.getDeviceID(), "Error"); //TODO --> add specific error messages
                    }
                }

            }

            return null;

        }
    }
       

    private class UniDAOperationAckMessageHandler implements IUniDAProtocolMessageHandler
    {

        @Override
        public boolean supports(UniDAMessage msg)
        {
            return MessageType.getTypeOf(msg.getCommandType()) == MessageType.GenericReply;
        }

        @Override
        public synchronized UniDAMessage handle(UniDAMessage msg)
        {

            if (msg instanceof UniDAOperationAckMessage)
            {

                UniDAOperationAckMessage reply = (UniDAOperationAckMessage) msg;
                IUnidaNetworkFacadeCallback cback = removeOperationCallback(reply.getOperationId(), reply.getDeviceID());

                if (cback != null)
                {
                    if (reply.getErrorCode() == ErrorCode.Ok.getTypeValue())
                    {
                        cback.notifyCommandExecution(reply.getOperationId(), reply.getDeviceID(), null, null);
                    } else
                    {
                        cback.notifyDeviceFailure(reply.getOperationId(), reply.getDeviceID(), "Error"); //TODO --> add specific error messages
                    }
                }

            }

            return null;

        }
    }

    private class UniDANotificationMessageHandler implements IUniDAProtocolMessageHandler
    {

        @Override
        public boolean supports(UniDAMessage msg)
        {
            return MessageType.getTypeOf(msg.getCommandType()) == MessageType.Notification;
        }

        @Override
        public UniDAMessage handle(UniDAMessage msg)
        {

            if (msg instanceof UniDANotificationMessage)
            {

                UniDANotificationMessage reply = (UniDANotificationMessage) msg;
                INotificationCallback cback = getNotificationCallback(reply.getOpId(), reply.getDeviceID());

                if (cback != null)
                {
                    if ((reply.getErrorCode() == ErrorCode.Ok.getTypeValue()) || (reply.getErrorCode() == ErrorCode.Null.getTypeValue()))
                    {
                        cback.notifyState(reply.getOpId(), reply.getDeviceID(), reply.getStateId(), reply.getValueId(), reply.getValueRaw());
                    }
                } else
                {
                    try
                    {
                        unsuscribeFrom(reply.getOpId(), reply.getDeviceID(), reply.getStateId(), new String[0], cback);
                    } catch (CommunicationException ex)
                    {
                        UniDALoggers.LIBRARY.log(Level.WARNING, "Unable to unsuscribe from notification with null callback: ", ex.getLocalizedMessage());
                    }
                    UniDALoggers.LIBRARY.log(Level.WARNING, "Callback is null for notification {0} : {1}", new Object[]
                    {
                        reply.getDeviceID().toString(), reply.getStateId()
                    });
                }

            }

            return null;

        }
    }

    private class UnidaHeartbeatMessageHandler implements IUniDAProtocolMessageHandler
    {

        @Override
        public boolean supports(UniDAMessage msg)
        {
            return MessageType.getTypeOf(msg.getCommandType()) == MessageType.GatewayHeartbeat;
        }

        @Override
        public UniDAMessage handle(UniDAMessage msg)
        {
            if (msg instanceof UniDAGatewayHeartbeatMessage)
            {
                UniDAGatewayHeartbeatMessage heartbeat = (UniDAGatewayHeartbeatMessage) msg;
                try
                {
                    devManagement.findDeviceGatewayById(heartbeat.getSource().toString());
                } catch (InternalErrorException ex) {
                    return null;
                }
                catch (InstanceNotFoundException ex)
                {
                    DiscoverUniDAGatewayDevicesRequestMessage discoverRequest = new DiscoverUniDAGatewayDevicesRequestMessage(ontologyCodec);
                    discoverRequest.setDestination(heartbeat.getSource());
//                    discoverRequest.setSource(heartbeat.getDestination());
                    try
                    {
                        commChannel.sendMessage(heartbeat.getSource(), discoverRequest);
                    } catch (CommunicationException ex1)
                    {
                        return null;
                    }
                }
            }
            return null;
        }
    }

    private class DiscoverUniDAGatewayReplyMessageHandler implements IUniDAProtocolMessageHandler
    {

        @Override
        public boolean supports(UniDAMessage msg)
        {
            return MessageType.getTypeOf(msg.getCommandType()) == MessageType.DiscoverGatewayDevicesReply;
        }

        @Override
        public UniDAMessage handle(UniDAMessage msg)
        {

            if (msg instanceof DiscoverUniDAGatewayDevicesReplyMessage)
            {

                DiscoverUniDAGatewayDevicesReplyMessage reply = (DiscoverUniDAGatewayDevicesReplyMessage) msg;

                try
                {
                    reply.getGateway().setAdress(msg.getSource().toString());

                    GatewayTO gatewayTO = reply.getGateway();

                    Gateway gateway = DeviceConversionOperations.createDeviceGateway(gatewayTO, ontologyFacade);
                    
//                    HashMap<Short, GatewayDeviceIO> ioMap = new HashMap<>();
//                    for (GatewayDeviceIO io : gateway.getIoList())
//                    {
//                        ioMap.put(io.getId(), io);
//                    }

//                    for (DeviceTO devTO : reply.getDevices())
//                    {
//
//                        PhysicalDevice device = DeviceConversionOperations.createPhysicalDevice(devTO, ontologyFacade.getDeviceClassById(devTO.getDeviceClass()));
//                        for (GatewayDeviceIOTO ioTO : devTO.getConnectedIOs())
//                        {
//                            device.connectToIO(ioMap.get(ioTO.getId()));
//                        }
//
//                    }

                    devManagement.newGatewayDiscovered(gateway);

                } catch (UniDAIDFormatException ex)
                {
                } catch (ClassNotFoundInOntologyException | InternalErrorException ex)
                {
                }

            }

            return null;

        }
    }
}

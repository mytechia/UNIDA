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
package com.hi3project.unida.protocol.udp;

import com.mytechia.commons.framework.simplemessageprotocol.udp.IUDPCommunicationChannel;
import com.mytechia.commons.framework.simplemessageprotocol.channel.IAddress;
import com.mytechia.commons.framework.simplemessageprotocol.channel.ReceiveResult;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.hi3project.unida.library.device.DeviceID;
import com.hi3project.unida.protocol.UniDAAddress;
import com.hi3project.unida.log.UniDALoggers;
import com.hi3project.unida.protocol.IUniDACommChannel;
import com.hi3project.unida.protocol.message.UniDAMessage;
import com.hi3project.unida.protocol.IMessageFactory;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;

/**
 * <p>
 * <b>
 * Sends and receives UniDA protocol messages using UDP sockets
 * </b>
 *
 *
 *
 * <p>
 * <b>Creation date:</b> 30-01-2010</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>1 - 30-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UDPUniDACommChannel implements IUniDACommChannel
{

    private static final short UNIDA_LEAF_NUMBER = 0;

    private IUDPCommunicationChannel commChannel;

    private IMessageFactory msgFactory;

    public UDPUniDACommChannel(int port, IMessageFactory msgFactory) throws CommunicationException
    {
        try
        {
            this.commChannel = new UDPUniDACommunicationChannelImplementation(port);
            this.msgFactory = msgFactory;
        } catch (UnknownHostException | SocketException ex)
        {
            throw new CommunicationException(ex);
        }
    }

    public UDPUniDACommChannel(IMessageFactory msgFactory) throws CommunicationException
    {
        this(UDPUniDACommunicationChannelImplementation.UNIDA_PROTOCOL_UDP_PORT, msgFactory);
    }

    public UDPUniDACommChannel(String ip, IMessageFactory msgFactory) throws CommunicationException
    {
        try
        {
            this.commChannel = new UDPUniDACommunicationChannelImplementation(ip);
            this.msgFactory = msgFactory;
        } catch (UnknownHostException | SocketException ex)
        {
            throw new CommunicationException(ex);
        }
    }

    @Override
    public void sendMessage(DeviceID dev, UniDAMessage msg) throws CommunicationException
    {

        try
        {

            this.commChannel.send(createUDPAddress(dev.getGatewayId()), msg);

            UniDALoggers.LIBRARY.log(Level.INFO, "Sent message: {0}", msg.toString());

        } catch (UnknownHostException ex)
        {
            throw new CommunicationException(ex);
        }

    }

    @Override
    public void sendMessage(UniDAAddress gw, UniDAMessage msg) throws CommunicationException
    {

        try
        {

            this.commChannel.send(createUDPAddress(gw), msg);

            UniDALoggers.LIBRARY.log(Level.INFO, "Sent message: {0}", msg.toString());

        } catch (UnknownHostException ex)
        {
            throw new CommunicationException(ex);
        }

    }

    private IAddress createUDPAddress(UniDAAddress uaddr) throws UnknownHostException
    {
        return uaddr.toUDPAddress(UDPUniDACommunicationChannelImplementation.UNIDA_PROTOCOL_UDP_PORT);
    }

    @Override
    public void broadcastMessage(UniDAMessage msg) throws CommunicationException
    {
        this.commChannel.broadcast(msg);
        UniDALoggers.LIBRARY.log(Level.INFO, "Broadcast message: {0}", msg.toString());
    }

    @Override
    public UniDAMessage receiveMessage() throws CommunicationException
    {
        ReceiveResult rr = this.commChannel.receive();

        UniDAMessage msg = this.msgFactory.createUnidaMessage(rr.getData());

        UniDALoggers.LIBRARY.log(Level.INFO, "Message received: {0}", msg.toString());

        return msg;

    }

    @Override
    public UniDAAddress getAddress()
    {
        return new UniDAAddress(this.commChannel.getIPAddress().toString(), UNIDA_LEAF_NUMBER);
    }

}

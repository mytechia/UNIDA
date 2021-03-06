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
package com.hi3project.unida.protocol.handling;

import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.hi3project.unida.log.UniDALoggers;
import com.hi3project.unida.protocol.IUniDACommChannel;
import com.hi3project.unida.protocol.message.UniDAMessage;
import com.hi3project.unida.protocol.handling.exception.UnsupportedMessageTypeErrorException;
import com.hi3project.unida.protocol.reception.IUniDAProtocolMessageProcessingQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

/**
 * <p>
 * <b>
 * It is a thread in charge of providing computing resources to process UniDA
 * protocol messages.
 *
 * It associated with a message reception queue from wich it obtains the
 * messages to process.
 *
 * It can be used in conjunction with a thread pool, in order to achieve
 * multi-core processing capabilities.
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
public class UniDAProtocolMessageProcessor extends Thread
{

    private boolean processing = false;

    private IUniDAProtocolMessageProcessingQueue msgQueue;
    private ArrayList<IUniDAProtocolMessageHandler> handlers;
    protected IUniDACommChannel commChannel;

    public UniDAProtocolMessageProcessor(
            IUniDACommChannel commChannel, IUniDAProtocolMessageProcessingQueue msgQueue)
    {
        super("UniDA-Protocol-Message-Processor");
        this.commChannel = commChannel;
        this.msgQueue = msgQueue;
        this.handlers = new ArrayList<>();
    }

    /**
     * @param msgHandler
     */
    public void registerMessageHandler(IUniDAProtocolMessageHandler msgHandler)
    {
        this.handlers.add(msgHandler);
    }

    /**
     * @param msgHandler
     */
    public void unregisterMessageHandler(IUniDAProtocolMessageHandler msgHandler)
    {
        this.handlers.remove(msgHandler);
    }

    /**
     *
     */
    public void startProcessing()
    {
        this.processing = true;
        this.start();
    }

    /**
     *
     */
    public void stopProcessing()
    {
        this.processing = false;
    }

    /**
     * Uses the available message handlers to process the new message received
     *
     * @param msg
     * @return
     * @throws
     * com.hi3project.unida.protocol.handling.exception.UnsupportedMessageTypeErrorException
     */
    protected Collection<UniDAMessage> processMessage(UniDAMessage msg) throws UnsupportedMessageTypeErrorException
    {

        Collection<UniDAMessage> responses = new ArrayList<UniDAMessage>();

        for (IUniDAProtocolMessageHandler h : this.handlers)
        {
            if (h.supports(msg))
            {
                UniDAMessage response = h.handle(msg);
                responses.add(response);
                break;
            }
        }

        return responses;

    }

    @Override
    public void run()
    {

        while (this.processing)
        {

            UniDAMessage msg = this.msgQueue.pollMessageOrWait();

            try
            {

                Collection<UniDAMessage> responses = processMessage(msg);

                if (responses != null)
                {
                    for (UniDAMessage response : responses)
                    {
                        if (null != response)
                        {
                            this.commChannel.sendMessage(msg.getSource(), response);
                        }
                    }

                }

            } catch (UnsupportedMessageTypeErrorException ex)
            {
                UniDALoggers.LIBRARY.log(
                        Level.WARNING,
                        "Unsupported message received: " + msg.toString(),
                        ex.getLocalizedMessage());
            } catch (CommunicationException ex)
            {
                UniDALoggers.LIBRARY.log(
                        Level.SEVERE,
                        "Error processing message: " + msg.toString() + ". " + ex.getLocalizedMessage(),
                        ex.getLocalizedMessage());
            }

        }

    }

}

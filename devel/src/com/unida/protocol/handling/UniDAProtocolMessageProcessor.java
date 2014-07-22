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


package com.unida.protocol.handling;


import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.unida.log.UniDALoggers;
import com.unida.protocol.IUniDACommChannel;
import com.unida.protocol.message.UniDAMessage;
import com.unida.protocol.handling.exception.UnsupportedMessageTypeErrorException;
import com.unida.protocol.reception.IUniDAProtocolMessageProcessingQueue;
import java.util.ArrayList;
import java.util.logging.Level;


/**
 * <p><b>
 * It is a thread in charge of providing computing resources to process
 * HI3 DAL protocol messages.
 *
 * It associated with a message reception queue from wich it obtains the
 * messages to process.
 *
 * It can be used in conjunction with a thread pool, in order to achieve
 * multi-core processing capabilities.
 * </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 30-01-2010</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 30-01-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDAProtocolMessageProcessor extends Thread
{

    private boolean processing = false;

    private IUniDAProtocolMessageProcessingQueue msgQueue;
    private ArrayList<IUniDAProtocolMessageHandler> handlers;
    private IUniDACommChannel commChannel;


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


    /** Uses the available message handlers to process the new message received
     * @param msg
     * @return 
     * @throws com.unida.protocol.handling.exception.UnsupportedMessageTypeErrorException
     */
    protected UniDAMessage processMessage(UniDAMessage msg) throws UnsupportedMessageTypeErrorException
    {

        UniDAMessage response = null;
        for(IUniDAProtocolMessageHandler h : this.handlers) {
            if (h.supports(msg)) {
                response = h.handle(msg);
                break;
            }
        }

        return response;

    }


    @Override
    public void run()
    {

        while(this.processing) {

            UniDAMessage msg = this.msgQueue.pollMessageOrWait();

            try{

                UniDAMessage response = processMessage(msg);

                if (response != null) {
                    this.commChannel.sendMessage(msg.getSource(), response);
                }

            }
            catch (UnsupportedMessageTypeErrorException ex) {
                UniDALoggers.LIBRARY.log(Level.WARNING, "Unsupported message received:{0}", msg.toString());
            }
            catch (CommunicationException ex) {
                UniDALoggers.LIBRARY.log(Level.SEVERE, "Error sending message: {0}", ex.getLocalizedMessage());
            }

        }

    }


}

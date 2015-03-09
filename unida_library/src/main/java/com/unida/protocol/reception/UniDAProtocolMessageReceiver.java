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


package com.unida.protocol.reception;

import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.unida.log.UniDALoggers;
import com.unida.protocol.IUniDACommChannel;
import com.unida.protocol.message.UniDAMessage;
import java.util.logging.Level;


/**
 * <p><b>
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 31-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 31-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDAProtocolMessageReceiver extends Thread
{

    private boolean receiving = false;

    private IUniDACommChannel commChannel;

    private IUniDAProtocolMessageProcessingQueue msgQueue;


    public UniDAProtocolMessageReceiver(IUniDACommChannel commChannel, IUniDAProtocolMessageProcessingQueue msgQueue)
    {
        super("UniDA-Message-Receiver");
        this.commChannel = commChannel;
        this.msgQueue = msgQueue;
    }


    /**
     *
     */
    public void startReception()
    {
        this.receiving = true;
        this.start();
    }


    /**
     *
     */
    public void stopReception()
    {
        this.receiving = false;
    }


    @Override
    public void run()
    {

        while(this.receiving) {

            try {

                UniDAMessage msg = this.commChannel.receiveMessage();

                if (msg != null)
                    this.msgQueue.putMessage(msg);                    

            }
            catch (MessageFormatException ex) {
                UniDALoggers.LIBRARY.log(Level.WARNING, "Unknown message received:{0}", ex.getMessage());
            }
            catch (CommunicationException ex) {
                UniDALoggers.LIBRARY.log(Level.SEVERE, "Communication error");
            }

        }

    }
    

}

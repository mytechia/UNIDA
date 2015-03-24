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


package com.hi3project.unida.protocol.reception;

import com.hi3project.unida.protocol.message.UniDAMessage;
import java.util.LinkedList;
import java.util.Queue;


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
public class LinkedListUniDAProtocolMessageProcessingQueue implements IUniDAProtocolMessageProcessingQueue
{

    private final Integer lock = new Integer(0);
    private final Integer wait = new Integer(0);
    private final Queue<UniDAMessage> queue;

    
    public LinkedListUniDAProtocolMessageProcessingQueue()
    {
        this.queue = new LinkedList<UniDAMessage>();
    }


    public void putMessage(UniDAMessage rMsg)
    {
        
        synchronized(this.queue) {
            this.queue.add(rMsg);
            this.queue.notify();
        }

    }


    public UniDAMessage pollMessage()
    {

        synchronized(this.queue) {
            return this.queue.poll();
        }

    }


    public UniDAMessage pollMessageOrWait()
    {

        synchronized(this.queue) {

            while (this.queue.isEmpty()) {
                try {
                        this.queue.wait();
                }
                catch (InterruptedException e) {}
            }

            return this.queue.poll();
        }
        
    }

    
}

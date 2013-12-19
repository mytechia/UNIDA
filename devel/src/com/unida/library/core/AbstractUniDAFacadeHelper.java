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

package com.unida.library.core;

import com.unida.library.device.DeviceID;
import com.unida.library.notification.INotificationCallback;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * <p><b>
 * </b></br>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 01-02-2010</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 01-02-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public abstract class AbstractUniDAFacadeHelper
{

    private static final long OP_EXPIRATION_TIME = 150000; //2 secs


    protected Map<OperationEntry, IUnidaNetworkFacadeCallback> opCallbacks;
    protected Map<OperationEntry, INotificationCallback> ntCallbacks;


    protected long opExpirationTime = OP_EXPIRATION_TIME;

    private OperationExpirationChecker expirationCheckerThread;

    
    public AbstractUniDAFacadeHelper(long operationExpTime)
    {

        this.opExpirationTime = operationExpTime;
        this.expirationCheckerThread = new OperationExpirationChecker();
        this.opCallbacks = Collections.synchronizedMap(new HashMap<OperationEntry, IUnidaNetworkFacadeCallback>());
        this.ntCallbacks = Collections.synchronizedMap(new HashMap<OperationEntry, INotificationCallback>());
        this.expirationCheckerThread.start();

    }


    public AbstractUniDAFacadeHelper()
    {

        this(OP_EXPIRATION_TIME);

    }


    protected synchronized void addOperationCallback(long opTicketId, DeviceID deviceId, IUnidaNetworkFacadeCallback callback)
    {
        synchronized(opCallbacks) {
            this.opCallbacks.put(new OperationEntry(opTicketId, deviceId), callback);
        }
    }


    protected IUnidaNetworkFacadeCallback getOperationCallback(long opTicketId, DeviceID deviceId)
    {
        synchronized(opCallbacks) {
            return this.opCallbacks.get(new OperationEntry(opTicketId, deviceId));
        }
    }


    protected synchronized IUnidaNetworkFacadeCallback removeOperationCallback(long opTicketId, DeviceID deviceId)
    {
        synchronized(opCallbacks) {
            return this.opCallbacks.remove(new OperationEntry(opTicketId, deviceId));
        }
    }


    protected void addNotificationCallback(long opTicketId, DeviceID deviceId, INotificationCallback callback)
    {
        synchronized(opCallbacks) {
            this.ntCallbacks.put(new OperationEntry(opTicketId, deviceId), callback);
        }
    }


    protected INotificationCallback getNotificationCallback(long opTicketId, DeviceID deviceId)
    {
        synchronized(opCallbacks) {
            return this.ntCallbacks.get(new OperationEntry(opTicketId, deviceId));
        }
    }


    protected INotificationCallback removeNotificationCallback(long opTicketId, DeviceID deviceId)
    {
        synchronized(opCallbacks) {
            return this.ntCallbacks.remove(new OperationEntry(opTicketId, deviceId));
        }
    }



    protected class OperationEntry
    {

        public long ticketId;
        public DeviceID deviceId;
        public long timestamp;

        
        public OperationEntry(long ticketId, DeviceID deviceId)
        {
            this(ticketId, deviceId, System.currentTimeMillis());
        }



        public OperationEntry(long ticketId, DeviceID deviceId, long timestamp)
        {
            this.ticketId = ticketId;
            this.deviceId = deviceId;
            this.timestamp = timestamp;
        }


        @Override
        public boolean equals(Object obj)
        {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final OperationEntry other = (OperationEntry) obj;
            if (this.ticketId != other.ticketId) {
                return false;
            }
            return true;
        }


        @Override
        public int hashCode()
        {
            int hash = 3;
            hash = 83 * hash + (int) (this.ticketId ^ (this.ticketId >>> 32));
            return hash;
        }

    }


    /**
     * <p><b>
     * Periodically checks if a device request (command, query, etc.)
     * has expired because it hasn't been answered in time.
     * </b></br>
     *
     * </p>
     *
     * <p><b>Creation date:</b> 15-02-2010</p>
     *
     * <p><b>Changelog:</b></br>
     * <ul>
     * <li>1 - 15-02-2010<\br> Initial release</li>
     * </ul>
     * </p>
     *
     * @author Gervasio Varela
     * @version 1
     */
    private class OperationExpirationChecker extends Thread
    {

        public OperationExpirationChecker()
        {
            super("UniDA-Operation-Expiration-Checker-Thread");
        }


        @Override
        public void run()
        {


            long expTime = opExpirationTime;
            long currentTime = 0;

            while(true) {

                currentTime = System.currentTimeMillis();

                Set<Entry<OperationEntry, IUnidaNetworkFacadeCallback>> cBacks = opCallbacks.entrySet();

                synchronized(opCallbacks) {
                
                    Iterator<Entry<OperationEntry, IUnidaNetworkFacadeCallback>> ite = cBacks.iterator();
                    
                    while (ite.hasNext()) {
                        Entry<OperationEntry, IUnidaNetworkFacadeCallback> cb = ite.next();

                        OperationEntry op = cb.getKey();

                        if ( (op.timestamp + expTime) < currentTime) {
                            cb.getValue().notifyExpiration(op.ticketId);
                            //removeOperationCallback(op.ticketId, op.deviceId);
                            ite.remove();
                        }

                    }
                }

                try {
                    Thread.sleep(250);
                }
                catch (InterruptedException ex) { }

            }

        }


    }

}

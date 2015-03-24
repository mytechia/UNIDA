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
package com.hi3project.unida.library.core;

import com.hi3project.unida.library.operation.device.IOperationInternalCallback;
import com.hi3project.unida.library.device.DeviceID;
import com.hi3project.unida.library.notification.INotificationInternalCallback;
import com.hi3project.unida.library.operation.gateway.IAutonomousBehaviourInternalCallback;
import com.hi3project.unida.protocol.UniDAAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <p>
 * <b>
 * </b>
 *
 *
 *
 * <p>
 * <b>Creation date:</b> 01-02-2010</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>1 - 01-02-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public abstract class AbstractUniDAFacadeHelper
{

    private static final long OP_EXPIRATION_TIME = 2 * 1000;

    protected long opExpirationTime = OP_EXPIRATION_TIME;

    private OperationExpirationChecker expirationCheckerThread;

    protected final Map<DeviceOperationEntry, IOperationInternalCallback> opCallbacks;

    protected final Map<DeviceOperationEntry, INotificationInternalCallback> ntCallbacks;

    protected final Map<GatewayOperationEntry, IAutonomousBehaviourInternalCallback> abCallbacks;

    public AbstractUniDAFacadeHelper(long operationExpTime)
    {

        this.opExpirationTime = operationExpTime;
        this.expirationCheckerThread = new OperationExpirationChecker();
        this.opCallbacks = Collections.synchronizedMap(new HashMap<DeviceOperationEntry, IOperationInternalCallback>());
        this.ntCallbacks = Collections.synchronizedMap(new HashMap<DeviceOperationEntry, INotificationInternalCallback>());
        this.abCallbacks = Collections.synchronizedMap(new HashMap<GatewayOperationEntry, IAutonomousBehaviourInternalCallback>());

    }

    public AbstractUniDAFacadeHelper()
    {

        this(OP_EXPIRATION_TIME);

    }

    public void start()
    {
        this.expirationCheckerThread.start();
    }

    protected void addOperationCallback(long opTicketId, DeviceID deviceId, IOperationInternalCallback callback)
    {
        synchronized (opCallbacks)
        {
            this.opCallbacks.put(new DeviceOperationEntry(opTicketId, deviceId), callback);
        }
    }

    protected IOperationInternalCallback getOperationCallback(long opTicketId, DeviceID deviceId)
    {
        synchronized (opCallbacks)
        {
            return this.opCallbacks.get(new DeviceOperationEntry(opTicketId, deviceId));
        }
    }

    protected IOperationInternalCallback removeOperationCallback(long opTicketId, DeviceID deviceId)
    {
        synchronized (opCallbacks)
        {
            return this.opCallbacks.remove(new DeviceOperationEntry(opTicketId, deviceId));
        }
    }

    protected void addAutonomousBehaviourCallback(long opTickectId, UniDAAddress gatewayAddress, IAutonomousBehaviourInternalCallback callback)
    {
        synchronized (abCallbacks)
        {
            this.abCallbacks.put(new GatewayOperationEntry(opTickectId, gatewayAddress), callback);
        }
    }

    protected IAutonomousBehaviourInternalCallback getAutonomousBehaviourCallback(long opTicketId, UniDAAddress gatewayAddress)
    {
        synchronized (abCallbacks)
        {
            return this.abCallbacks.get(new GatewayOperationEntry(opTicketId, gatewayAddress));
        }
    }

    protected IAutonomousBehaviourInternalCallback removeAutonomousBehaviourCallback(long opTicketId, UniDAAddress gatewayAddress)
    {
        synchronized (abCallbacks)
        {
            return this.abCallbacks.remove(new GatewayOperationEntry(opTicketId, gatewayAddress));
        }
    }

    protected void addNotificationCallback(long opTicketId, DeviceID deviceId, INotificationInternalCallback callback)
    {
        synchronized (opCallbacks)
        {
            this.ntCallbacks.put(new DeviceOperationEntry(opTicketId, deviceId), callback);
        }
    }

    protected INotificationInternalCallback getNotificationCallback(long opTicketId, DeviceID deviceId)
    {
        synchronized (opCallbacks)
        {
            return this.ntCallbacks.get(new DeviceOperationEntry(opTicketId, deviceId));
        }
    }

    protected INotificationInternalCallback removeNotificationCallback(long opTicketId, DeviceID deviceId)
    {
        synchronized (opCallbacks)
        {
            return this.ntCallbacks.remove(new DeviceOperationEntry(opTicketId, deviceId));
        }
    }

    protected class DeviceOperationEntry
    {

        public long ticketId;
        public DeviceID deviceId;
        public long timestamp;

        public DeviceOperationEntry(long ticketId, DeviceID deviceId)
        {
            this(ticketId, deviceId, System.currentTimeMillis());
        }

        public DeviceOperationEntry(long ticketId, DeviceID deviceId, long timestamp)
        {
            this.ticketId = ticketId;
            this.deviceId = deviceId;
            this.timestamp = timestamp;
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
            final DeviceOperationEntry other = (DeviceOperationEntry) obj;
            return this.ticketId == other.ticketId;
        }

        @Override
        public int hashCode()
        {
            int hash = 3;
            hash = 83 * hash + (int) (this.ticketId ^ (this.ticketId >>> 32));
            return hash;
        }

    }

    protected class GatewayOperationEntry
    {

        public long ticketId;
        public UniDAAddress gatewayAddress;
        public long timestamp;

        public GatewayOperationEntry(long ticketId, UniDAAddress gatewayAddress, long timestamp)
        {
            this.ticketId = ticketId;
            this.gatewayAddress = gatewayAddress;
            this.timestamp = timestamp;
        }

        public GatewayOperationEntry(long ticketId, UniDAAddress gatewayAddress)
        {
            this(ticketId, gatewayAddress, System.currentTimeMillis());
        }

        @Override
        public int hashCode()
        {
            int hash = 5;
            hash = 37 * hash + (int) (this.ticketId ^ (this.ticketId >>> 32));
            return hash;
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
            final GatewayOperationEntry other = (GatewayOperationEntry) obj;
            return this.ticketId == other.ticketId;
        }
    }

    /**
     * <p>
     * <b>
     * Periodically checks if a device request (command, query, etc.) has
     * expired because it hasn't been answered in time.
     * </b>
     *
     *
     *
     * <p>
     * <b>Creation date:</b> 15-02-2010</p>
     *
     * <p>
     * <b>Changelog:</b>
     * <ul>
     * <li>1 - 15-02-2010 Initial release</li>
     * </ul>
     *
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

            while (true)
            {

                long currentTime = System.currentTimeMillis();

                Set<Entry<DeviceOperationEntry, IOperationInternalCallback>> setOpCallbacks = opCallbacks.entrySet();

                synchronized (opCallbacks)
                {

                    Iterator<Entry<DeviceOperationEntry, IOperationInternalCallback>> ite = setOpCallbacks.iterator();

                    while (ite.hasNext())
                    {
                        Entry<DeviceOperationEntry, IOperationInternalCallback> cb = ite.next();

                        DeviceOperationEntry op = cb.getKey();

                        if ((op.timestamp + expTime) < currentTime)
                        {
                            cb.getValue().notifyExpiration(op.ticketId);
                            //removeOperationCallback(op.ticketId, op.deviceId);
                            ite.remove();
                        }

                    }
                }

                Set<Entry<GatewayOperationEntry, IAutonomousBehaviourInternalCallback>> setAbCallbacks = abCallbacks.entrySet();

                synchronized (setAbCallbacks)
                {

                    Iterator<Entry<GatewayOperationEntry, IAutonomousBehaviourInternalCallback>> ite = setAbCallbacks.iterator();
                    
                    while (ite.hasNext())
                    {
                        Entry<GatewayOperationEntry, IAutonomousBehaviourInternalCallback> cb = ite.next();
                        
                        GatewayOperationEntry op = cb.getKey();
                        if ((op.timestamp + expTime) < currentTime)
                        {
                            cb.getValue().notifyExpiration(op.ticketId);
                            ite.remove();
                        }
                    }
                }

                try
                {
                    Thread.sleep(250);
                } catch (InterruptedException ex)
                {
                }

            }

        }

    }

}

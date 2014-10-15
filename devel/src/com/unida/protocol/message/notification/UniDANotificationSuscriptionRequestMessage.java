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
package com.unida.protocol.message.notification;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.DeviceID;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDADeviceMessage;
import com.unida.protocol.message.autonomousbehaviour.trigger.RuleTriggerEnum;
import com.unida.protocol.message.autonomousbehaviour.trigger.StateChangeTrigger;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * <b>
 * UniDANotificationSuscriptionRequestMessage
 * </b>
 * </p>
 *
 * <p>
 * <b>Creation date:</b> 27-01-2010</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>1 - 27-01-2010 Initial release</li>
 * <li>2 - 02-04-2014 Refactor to use StateChangeTrigger </li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @author Victor Sonora
 * @version 2
 */
public class UniDANotificationSuscriptionRequestMessage extends UniDADeviceMessage
{

    private long notificationId;
    private StateChangeTrigger stateChangeTrigger;

    public UniDANotificationSuscriptionRequestMessage(IUniDAOntologyCodec ontologyCodec,
            long notificationId, DeviceID deviceId, String stateId)
    {
        super(ontologyCodec, deviceId);
        setCommandType(MessageType.SuscribeTo.getTypeValue());
        this.notificationId = notificationId;
        this.stateChangeTrigger = new StateChangeTrigger(deviceId, stateId, new StateConditionNull());
    }

    public UniDANotificationSuscriptionRequestMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }

    public long getNotificationId()
    {
        return this.notificationId;
    }

    public StateChangeTrigger getTrigger()
    {
        return this.stateChangeTrigger;
    }

    public void changeTrigger(StateChangeTrigger stateChangeTrigger)
    {
        this.stateChangeTrigger = stateChangeTrigger;
    }

    @Override
    public byte[] codeDeviceMessagePayload() throws MessageFormatException
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {

            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(notificationId, idData, 0);
            dataStream.write(idData);
            
            // Trigger
            dataStream.write(stateChangeTrigger.codePayload(ontologyCodec));

        } catch (IOException ioEx)
        {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();

    }

    @Override
    protected int decodeDeviceMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {     

        this.notificationId = EndianConversor.byteArrayLittleEndianToLong(bytes, initIndex);
        initIndex += EndianConversor.LONG_SIZE_BYTES;

        // Trigger type
        RuleTriggerEnum triggerType = RuleTriggerEnum.fromValue(EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex));
        initIndex += EndianConversor.SHORT_SIZE_BYTES;

        // Trigger payload
        if (triggerType != RuleTriggerEnum.UNKNOWN)
        {
            this.stateChangeTrigger = new StateChangeTrigger();
        }
        if (null != this.stateChangeTrigger)
        {
            this.stateChangeTrigger.decodePayload(bytes, initIndex, ontologyCodec);
        }

        return initIndex;
    }

    
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 31 * hash + (int) (this.notificationId ^ (this.notificationId >>> 32));
        hash = 31 * hash + Objects.hashCode(this.stateChangeTrigger);
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
        final UniDANotificationSuscriptionRequestMessage other = (UniDANotificationSuscriptionRequestMessage) obj;
        if (this.notificationId != other.notificationId)
        {
            return false;
        }
        if (!Objects.equals(this.stateChangeTrigger, other.stateChangeTrigger))
        {
            return false;
        }
        return true;
    }

    @Override
    protected MessageRType getMessageType()
    {
        return MessageRType.REQUEST;
    }
}

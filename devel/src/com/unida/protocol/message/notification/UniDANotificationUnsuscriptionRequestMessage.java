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


package com.unida.protocol.message.notification;


import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.DeviceID;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDADeviceMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;


/**
 * <p><b>
 * </b></br>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 27-01-2010</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 27-01-2010<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDANotificationUnsuscriptionRequestMessage extends UniDADeviceMessage
{

    private long notificationId;
    private String stateId;
    private String[] params;



    public UniDANotificationUnsuscriptionRequestMessage(IUniDAOntologyCodec ontologyCodec,
            long notificationId, DeviceID deviceId, String stateId, String[] params)
    {
        super(ontologyCodec, deviceId);
        setCommandType(MessageType.UnsuscribeFrom.getTypeValue());
        this.notificationId = notificationId;
        this.stateId = stateId;
        this.params = Arrays.copyOf(params, params.length);
    }

    
    public UniDANotificationUnsuscriptionRequestMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }


    public long getNotificationId()
    {
        return this.notificationId;
    }


    public String getStateId()
    {
        return stateId;
    }


    public String[] getParams()
    {
        return this.params;
    }

    
    @Override
    public byte[] codeDeviceMessagePayload() throws MessageFormatException
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try {

            byte [] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(notificationId, idData, 0);
            dataStream.write(idData);
                       
            EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(this.stateId), idData, 0);          
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);

            byte [] lenData = new byte[EndianConversor.SHORT_SIZE_BYTES];
            EndianConversor.shortToLittleEndian((short) this.params.length, lenData, 0);
            dataStream.write(lenData);

            for(int i=0; i<this.params.length; i++) {
                writeString(dataStream, params[i]);
            }

        }
        catch(IOException ioEx) {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();

    }


    @Override
    protected int decodeDeviceMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {

        int offset = initIndex;
        StringBuilder string = new StringBuilder(10);

        this.notificationId = EndianConversor.byteArrayLittleEndianToLong(bytes, offset);
        offset += EndianConversor.LONG_SIZE_BYTES;

        //stateId        
        this.stateId = getOntologyCodec().decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, offset));
        offset += EndianConversor.INT_SIZE_BYTES;

        int numParams = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
        offset += EndianConversor.SHORT_SIZE_BYTES;

        this.params = new String[numParams];
        for(int i=0; i<numParams; i++) {
            offset += readString(string, bytes, offset);
            this.params[i] = string.toString();
        }

        return offset;

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
        final UniDANotificationUnsuscriptionRequestMessage other = (UniDANotificationUnsuscriptionRequestMessage) obj;
        if (this.notificationId != other.notificationId) {
            return false;
        }
        if ((this.getDeviceID() == null) ? (other.getDeviceID() != null) : !this.getDeviceID().equals(other.getDeviceID())) {
            return false;
        }
        if ((this.stateId == null) ? (other.stateId != null) : !this.stateId.equals(other.stateId)) {
            return false;
        }
        if (!Arrays.deepEquals(this.params, other.params)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + (int) (this.notificationId ^ (this.notificationId >>> 32));
        hash = 67 * hash + (this.getDeviceID() != null ? this.getDeviceID().hashCode() : 0);
        hash = 67 * hash + (this.stateId != null ? this.stateId.hashCode() : 0);
        hash = 67 * hash + Arrays.deepHashCode(this.params);
        return hash;
    }

    
    @Override
    protected MessageRType getMessageType() {
        return MessageRType.REQUEST;
    }
    
}

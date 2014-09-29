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


package com.unida.protocol.message.querydevicestate;


import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.DeviceID;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDADeviceMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p><b> </b>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 27-01-2010</p>
 *
 * <p><b>Changelog:</b> <ul> <li>1 - 27-01-2010 Initial release</li>
 * </ul> </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDAQueryDeviceStateRequestMessage extends UniDADeviceMessage {

    protected long opId;
    protected String stateId;

    public UniDAQueryDeviceStateRequestMessage(IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId, String stateId) {
        super(ontologyCodec, deviceId);
        setCommandType(MessageType.QueryDeviceState.getTypeValue());
        this.opId = opId;
        this.stateId = stateId;
    }

    public UniDAQueryDeviceStateRequestMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException {
        super(message, ontologyCodec);
    }


    public long getOpId() {
        return opId;
    }

    public String getStateId() {
        return stateId;
    }

    @Override
    public byte[] codeDeviceMessagePayload() throws MessageFormatException
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try {

            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(opId, idData, 0);
            dataStream.write(idData);

            EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(this.stateId), idData, 0);
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);

        } catch (IOException ioEx) {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();

    }

    @Override
    protected int decodeDeviceMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException {

        int offset = initIndex;        

        this.opId = EndianConversor.byteArrayLittleEndianToLong(bytes, offset);
        offset += EndianConversor.LONG_SIZE_BYTES;

        //stateId        
        this.stateId = getOntologyCodec().decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, offset));
        offset += EndianConversor.INT_SIZE_BYTES;

        return offset;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UniDAQueryDeviceStateRequestMessage other = (UniDAQueryDeviceStateRequestMessage) obj;
        if (this.opId != other.opId) {
            return false;
        }
        if ((this.getDeviceID() == null) ? (other.getDeviceID() != null) : !this.getDeviceID().equals(other.getDeviceID())) {
            return false;
        }
        if ((this.stateId == null) ? (other.stateId != null) : !this.stateId.equals(other.stateId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.opId ^ (this.opId >>> 32));
        hash = 89 * hash + (this.getDeviceID() != null ? this.getDeviceID().hashCode() : 0);
        hash = 89 * hash + (this.stateId != null ? this.stateId.hashCode() : 0);
        return hash;
    }
    
    @Override
    protected MessageRType getMessageType() {
        return MessageRType.REQUEST;
    }
}

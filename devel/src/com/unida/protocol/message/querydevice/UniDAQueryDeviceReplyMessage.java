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


package com.unida.protocol.message.querydevice;


import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.DeviceID;
import com.unida.library.device.exception.UniDAIDFormatException;
import com.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p><b> </b></br>
 *
 * </p>
 *
 * <p><b>Creation date:</b> 27-01-2010</p>
 *
 * <p><b>Changelog:</b></br> <ul> <li>1 - 27-01-2010<\br> Initial release</li>
 * </ul> </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDAQueryDeviceReplyMessage extends UniDAQueryDeviceRequestMessage {

    private Collection<DeviceStateValue> stateValues;

    public UniDAQueryDeviceReplyMessage(UniDAMessage request, IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId,
            Collection<DeviceStateValue> stateValues, ErrorCode errorCode) {
        super(ontologyCodec, opId, deviceId);
        this.setDestination(request.getSource());
        this.setCommandType(MessageType.QueryDeviceStatesReply.getTypeValue());
        this.setErrorCode(errorCode.getTypeValue());
        this.stateValues = new ArrayList<DeviceStateValue>(stateValues.size());
        this.stateValues.addAll(stateValues);
    }

    public UniDAQueryDeviceReplyMessage(UniDAMessage request, IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId, Collection<DeviceStateValue> stateValues) {
        this(request, ontologyCodec, opId, deviceId, stateValues, ErrorCode.Ok);
    }

    public UniDAQueryDeviceReplyMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException {
        super(message, ontologyCodec);
    }

    public Collection<DeviceStateValue> getStateValues() {
        return new ArrayList<DeviceStateValue>(this.stateValues);
    }

    @Override
    public byte[] codeDeviceMessagePayload() throws MessageFormatException {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try {

            //create the data payload of the father class
            dataStream.write(super.codeDeviceMessagePayload());

            //2 bytes -> num states
            byte[] lenData = new byte[EndianConversor.SHORT_SIZE_BYTES];
            EndianConversor.shortToLittleEndian((short) this.stateValues.size(), lenData, 0);
            dataStream.write(lenData);

            for (DeviceStateValue dsv : this.stateValues) {
                byte[] idData = new byte[EndianConversor.INT_SIZE_BYTES];
                EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(dsv.getStateId()), idData, 0);
                dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);
               
                EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(dsv.getValueId()), idData, 0);
                dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);
 
                writeString(dataStream, dsv.getValue());

            }

        } catch (IOException ioEx) {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();

    }

    @Override
    protected int decodeDeviceMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException {

        int offset = super.decodeDeviceMessagePayload(bytes, initIndex);

        StringBuilder string = new StringBuilder(10);

        int numDevs = EndianConversor.byteArrayLittleEndianToShort(bytes, offset); //num devices
        offset += EndianConversor.SHORT_SIZE_BYTES;

        this.stateValues = new ArrayList<>(numDevs);
        for (int i = 0; i < numDevs; i++) {

            //state id            
            String stateId = getOntologyCodec().decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, offset));
            offset += EndianConversor.INT_SIZE_BYTES;

            //value id            
            String valueId = getOntologyCodec().decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, offset));
            offset += EndianConversor.INT_SIZE_BYTES;

            //value
            offset += readString(string, bytes, offset);
            String value = string.toString();

            this.stateValues.add(new DeviceStateValue(stateId, valueId, value));

        }

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
        final UniDAQueryDeviceReplyMessage other = (UniDAQueryDeviceReplyMessage) obj;
        if (this.stateValues != other.stateValues && (this.stateValues == null || !(this.stateValues.size() == other.stateValues.size()))) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.stateValues != null ? this.stateValues.hashCode() : 0);
        return hash;
    }
    
    @Override
    protected MessageRType getMessageType() {
        return MessageRType.REPLY;
    }
}

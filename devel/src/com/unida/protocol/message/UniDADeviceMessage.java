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


package com.unida.protocol.message;


import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.DeviceID;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p>UniDADeviceMessage<b> </b> A UniDA msg with a device ID field. It
 * should be used for message that have a device as destination. </p>
 *
 * <p><b>Creation date:</b> 28-09-2010</p>
 *
 * <p><b>Changelog:</b> <ul> <li>1 - 28-09-2010 Initial release</li>
 * </ul> </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public abstract class UniDADeviceMessage extends UniDAMessage {

    public enum MessageRType {
        REQUEST,
        REPLY
    };
    private DeviceID deviceId;
    

    public UniDADeviceMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException {       
        super(message, ontologyCodec);        
    }

    public UniDADeviceMessage(IUniDAOntologyCodec ontologyCodec,
            DeviceID deviceId) {
        super(deviceId.getGatewayId(), ontologyCodec);
        this.deviceId = deviceId;        
    }

    public DeviceID getDeviceID() {
        return this.deviceId;
    }
    
    protected abstract MessageRType getMessageType();
    
    @Override
    protected final int decodeMessagePayload(byte[] bytes, int initIndex)
            throws MessageFormatException {

        int offset = initIndex;

        short dId = EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        offset += EndianConversor.SHORT_SIZE_BYTES;

        if (this.getMessageType() == MessageRType.REPLY) {
            this.deviceId = new DeviceID(this.source, dId); // using source IP for DeviceID
        } else {
            this.deviceId = new DeviceID(this.destination, dId);
        }



        return decodeDeviceMessagePayload(bytes, offset);

    }

    protected abstract int decodeDeviceMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException;

    @Override
    protected final byte[] codeMessagePayload() throws MessageFormatException {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try {
            byte[] deviceIdData = new byte[EndianConversor.SHORT_SIZE_BYTES];
            EndianConversor.shortToLittleEndian(this.deviceId.getDeviceId(), deviceIdData, 0);
            dataStream.write(deviceIdData);
            dataStream.write(codeDeviceMessagePayload());
        } catch (IOException ex) {
            // 
        }

        return dataStream.toByteArray();

    }

    protected abstract byte[] codeDeviceMessagePayload() throws MessageFormatException;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UniDADeviceMessage other = (UniDADeviceMessage) obj;
        if ((this.deviceId == null) ? (other.deviceId != null) : !this.deviceId.equals(other.deviceId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.deviceId != null ? this.deviceId.hashCode() : 0);
        return hash;
    }
}

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


package com.unida.protocol.message.statusreport;


import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.to.DeviceTO;
import com.unida.library.device.to.GatewayTO;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.discovery.DiscoverUniDAGatewayDevicesRequestMessage;
import com.unida.protocol.message.discovery.DiscoverUniDAGatewayDevicesReplyMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 
 *
 *
 *
 * <p><b>Creation date:</b> 27-01-2010</p>
 *
 * <p><b>Changelog:</b></p> <ul> <li>1 - 27-01-2010 Initial release</li>
 * </ul>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class UniDAGatewayStatusReportReplyMessage extends DiscoverUniDAGatewayDevicesReplyMessage {

    private ErrorCode gwErrorCode;
    private Collection<DeviceError> devicesErrorCodes;

    public UniDAGatewayStatusReportReplyMessage(IUniDAOntologyCodec ontologyCodec,
            GatewayTO gw, ErrorCode gwErrorCode, Collection<DeviceTO> devices, Collection<DeviceError> devicesErrorCodes) {
        super(ontologyCodec, gw, devices);
        setCommandType(MessageType.GatewayStatusReportReply.getTypeValue());
        setErrorCode(gwErrorCode.getTypeValue());
        this.gwErrorCode = gwErrorCode;
        this.devicesErrorCodes = new ArrayList<>(devicesErrorCodes);
    }

    public UniDAGatewayStatusReportReplyMessage(IUniDAOntologyCodec ontologyCodec,
            GatewayTO gw, Collection<DeviceTO> devices, Collection<DeviceError> devicesErrorCodes) {
        this(ontologyCodec, gw, ErrorCode.Ok, devices, devicesErrorCodes);
    }

    public UniDAGatewayStatusReportReplyMessage(IUniDAOntologyCodec ontologyCodec,
            DiscoverUniDAGatewayDevicesRequestMessage request,
            GatewayTO gw, ErrorCode gwErrorCode, Collection<DeviceTO> devices, Collection<DeviceError> devicesErrorCodes) {
        this(ontologyCodec, gw, gwErrorCode, devices, devicesErrorCodes);
        this.setSequenceNumber(request.getSequenceNumber());
    }

    public UniDAGatewayStatusReportReplyMessage(IUniDAOntologyCodec ontologyCodec,
            DiscoverUniDAGatewayDevicesRequestMessage request,
            GatewayTO gw, Collection<DeviceTO> devices, Collection<DeviceError> devicesErrorCodes) {
        this(ontologyCodec, request, gw, ErrorCode.Ok, devices, devicesErrorCodes);
    }

    public UniDAGatewayStatusReportReplyMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException {
        super(message, ontologyCodec);
    }

    @Override
    public byte[] codeMessagePayload() throws MessageFormatException {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();                        
        
        byte[] lenData = new byte[EndianConversor.SHORT_SIZE_BYTES]; //will be reused for all lens

        try {
            
            dataStream.write(super.codeMessagePayload());
            
            //gateway error code
            dataStream.write(this.gwErrorCode.getTypeValue());

            
            //2 bytes -> num devices
            EndianConversor.shortToLittleEndian((short) this.devicesErrorCodes.size(), lenData, 0);
            dataStream.write(lenData);

            for (DeviceError err : this.devicesErrorCodes) {

                EndianConversor.shortToLittleEndian(err.getDeviceId(), lenData, 0);
                dataStream.write(lenData);
                
                dataStream.write(err.getError().getTypeValue()); //errCode -> 1 byte
            }

        } catch (IOException ioEx) {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();

    }

    @Override
    protected int decodeMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException {

        try {

            int offset = initIndex;
            StringBuilder string = new StringBuilder(10);

            offset = super.decodeMessagePayload(bytes, offset);
            
            this.gwErrorCode = ErrorCode.getTypeOf(bytes[offset]);
            offset += 1;
            
            short numDevs = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
            offset += EndianConversor.SHORT_SIZE_BYTES;
            
            this.devicesErrorCodes = new ArrayList<>(numDevs);
            for(int i=0; i<numDevs; i++) {
                
                short devId = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
                offset += EndianConversor.SHORT_SIZE_BYTES;
                
                this.devicesErrorCodes.add(new DeviceError(devId, ErrorCode.getTypeOf(bytes[offset])));
                offset +=1;
                
            }

            return offset;

        } catch (Exception ex) {
            throw new MessageFormatException(ex.getLocalizedMessage());
        }

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
        final UniDAGatewayStatusReportReplyMessage other = (UniDAGatewayStatusReportReplyMessage) obj;
        if (this.gwErrorCode != other.gwErrorCode) {
            return false;
        }
        return !(this.devicesErrorCodes != other.devicesErrorCodes && (this.devicesErrorCodes == null || !this.devicesErrorCodes.equals(other.devicesErrorCodes)));
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.gwErrorCode != null ? this.gwErrorCode.hashCode() : 0);
        hash = 83 * hash + (this.devicesErrorCodes != null ? this.devicesErrorCodes.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<-UniDAGatewayStatusReportReplyMessage{" + "gwErrorCode=" + gwErrorCode + ", devicesErrorCodes=" + Arrays.toString(devicesErrorCodes.toArray()) + '}';
    }
        
}

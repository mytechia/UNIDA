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


package com.unida.protocol.message.discovery;


import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.UniDAAddress;
import com.unida.library.device.to.DeviceTO;
import com.unida.library.device.to.GatewayDeviceIOTO;
import com.unida.library.device.to.GatewayTO;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <p><b>Description:</b></br>
 * This type of message are also the reply messages to a UniDAGatewayDiscoveryRequest
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
public class DiscoverUniDAGatewayDevicesReplyMessage extends UniDAMessage {

    private GatewayTO gw;
    private Collection<DeviceTO> devices;

    public DiscoverUniDAGatewayDevicesReplyMessage(IUniDAOntologyCodec ontologyCodec,
            ErrorCode errCode, GatewayTO gw, Collection<DeviceTO> devices) {
        super(ontologyCodec);
        setCommandType(MessageType.DiscoverGatewayDevicesReply.getTypeValue());
        setErrorCode(errCode.getTypeValue());
        this.gw = gw;
        this.devices = new ArrayList<DeviceTO>(devices.size());
        this.devices.addAll(devices);
        setDestination(UniDAAddress.BROADCAST_ADDRESS);
    }

    public DiscoverUniDAGatewayDevicesReplyMessage(IUniDAOntologyCodec ontologyCodec,
            GatewayTO gw, Collection<DeviceTO> devices) {
        this(ontologyCodec, ErrorCode.Ok, gw, devices);
    }

    public DiscoverUniDAGatewayDevicesReplyMessage(IUniDAOntologyCodec ontologyCodec,
            DiscoverUniDAGatewayDevicesRequestMessage request, ErrorCode errCode,
            GatewayTO gw, Collection<DeviceTO> devices) {
        this(ontologyCodec, errCode, gw, devices);
        this.setSequenceNumber(request.getSequenceNumber());
    }

    public DiscoverUniDAGatewayDevicesReplyMessage(IUniDAOntologyCodec ontologyCodec,
            DiscoverUniDAGatewayDevicesRequestMessage request,
            GatewayTO gw, Collection<DeviceTO> devices) {
        this(ontologyCodec, request, ErrorCode.Ok, gw, devices);
    }

    public DiscoverUniDAGatewayDevicesReplyMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException {
        super(message, ontologyCodec);
    }

    public Collection<DeviceTO> getDevices() {
        return devices;
    }

    public GatewayTO getGateway() {
        return gw;
    }

    @Override
    public byte[] codeMessagePayload() throws MessageFormatException
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();


        byte[] lenData = new byte[EndianConversor.SHORT_SIZE_BYTES]; //will be reused for all lens

        try {
            
            byte[] idData = new byte[EndianConversor.INT_SIZE_BYTES];

            //gateway class
            EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(this.gw.getGwClass()), idData, 0);
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);

            //gateway model
            writeString(dataStream, gw.getModel());

            //gateway manufacturer
            writeString(dataStream, gw.getManufacturer());

            //operational state of the gateway
            EndianConversor.shortToLittleEndian((short) this.gw.getOperationalState(), lenData, 0);
            dataStream.write(lenData);

            //list of device IOs
            Collection<GatewayDeviceIOTO> ioList = this.gw.getIoList();
            
            //2 bytes -> num device IOs
            EndianConversor.shortToLittleEndian((short) ioList.size(), lenData, 0);
            dataStream.write(lenData);
            
            //for every io --> id | statesLen | stateId * n
            for(GatewayDeviceIOTO io : ioList) {
                
                EndianConversor.shortToLittleEndian(io.getId(), lenData, 0);
                dataStream.write(lenData);
                
                ArrayList<String> states = io.getCompatibleStates();                                
                EndianConversor.shortToLittleEndian((short) states.size(), lenData, 0);
                dataStream.write(lenData);
                
                for(String st : states) {
                    EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(st), idData, 0);
                    dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);
                }
                
            }
            
            
            //list of devices connected
            //2 bytes -> num devices
            EndianConversor.shortToLittleEndian((short) devices.size(), lenData, 0);
            dataStream.write(lenData);

            //for every device -> | devId | clasId |  devModelLen | devModel | devManufLen | devManuf | opState | numIOs | ioId * n
            for (DeviceTO dev : devices) {

                EndianConversor.shortToLittleEndian(dev.getId(), idData, 0);
                dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

                EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(dev.getDeviceClass()), idData, 0);
                dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);                

                writeString(dataStream, dev.getModel());
                
                writeString(dataStream, dev.getManufacturer());
                
                writeString(dataStream, dev.getDescription());

                //operational state of the device
                EndianConversor.shortToLittleEndian((short) this.gw.getOperationalState(), lenData, 0);
                dataStream.write(lenData);
                
                Collection<GatewayDeviceIOTO> devIOs = dev.getConnectedIOs();
                EndianConversor.shortToLittleEndian((short) devIOs.size(), lenData, 0);
                dataStream.write(lenData);

                for (GatewayDeviceIOTO io : devIOs) {
                    EndianConversor.shortToLittleEndian(io.getId(), idData, 0);
                    dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);
                }

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

            
            //DECODE GATEWAY INFORMATION
            
            //gateway class
            long gwClassId = EndianConversor.byteArrayLittleEndianToUInt(bytes, offset);
            String gwClass = this.ontologyCodec.decodeId(gwClassId);
            offset += EndianConversor.INT_SIZE_BYTES;

            //gateway model
            offset += readString(string, bytes, offset);
            String gwModel = string.toString();

            //gateway manufacturer
            offset += readString(string, bytes, offset);
            String gwManufacturer = string.toString();

            int opState = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
            offset += EndianConversor.SHORT_SIZE_BYTES;
            
            int numIOs = EndianConversor.byteArrayLittleEndianToShort(bytes, offset); //num IOs
            offset += EndianConversor.SHORT_SIZE_BYTES;
            
            ArrayList<GatewayDeviceIOTO> gwIoList = new ArrayList<GatewayDeviceIOTO>(numIOs);
            short ioId, statesLen;
            long stateId;
            for(int i=0; i<numIOs; i++) {
                
                ioId = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
                offset += EndianConversor.SHORT_SIZE_BYTES;
                
                statesLen = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
                offset += EndianConversor.SHORT_SIZE_BYTES;
                
                ArrayList<String> statesList = new ArrayList<String>(statesLen);
                for(int j=0; j<statesLen; j++) {
                    stateId = EndianConversor.byteArrayLittleEndianToUInt(bytes, offset);
                    offset += EndianConversor.INT_SIZE_BYTES;
                    statesList.add(this.ontologyCodec.decodeId(stateId));
                }
                
                gwIoList.add(new GatewayDeviceIOTO(ioId, statesList));
                
            }
            
                        
            
            
            //DECODE DEVICES INFORMATION
            
            int numDevs = EndianConversor.byteArrayLittleEndianToShort(bytes, offset); //num devices
            offset += EndianConversor.SHORT_SIZE_BYTES;

            short devId, devOpState;
            int numUsedIOs;
            long classId;
            String devClass, devModel, devManufacturer, devDesc;
            ArrayList<GatewayDeviceIOTO> devIoList = null;
            this.devices = new ArrayList<DeviceTO>(numDevs);
            for (int i = 0; i < numDevs; i++) {

                devId = EndianConversor.byteArrayLittleEndianToShort(bytes, offset); //device id
                offset += EndianConversor.SHORT_SIZE_BYTES;
                
                classId = EndianConversor.byteArrayLittleEndianToUInt(bytes, offset);
                offset += EndianConversor.INT_SIZE_BYTES;
                devClass = this.ontologyCodec.decodeId(classId);
                
                offset += readString(string, bytes, offset);
                devModel = string.toString();

                offset += readString(string, bytes, offset);
                devManufacturer = string.toString();
                
                offset += readString(string, bytes, offset);
                devDesc = string.toString();
                    
                devOpState = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
                offset += EndianConversor.SHORT_SIZE_BYTES;
                
                
                numUsedIOs = EndianConversor.byteArrayLittleEndianToShort(bytes, offset); //num states
                offset += EndianConversor.SHORT_SIZE_BYTES;

                devIoList = new ArrayList<GatewayDeviceIOTO>(numUsedIOs);
                for (int j = 0; j < numUsedIOs; j++) {
                    ioId = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
                    offset += EndianConversor.SHORT_SIZE_BYTES;
                    GatewayDeviceIOTO gatewayIO = getGatewayIO(gwIoList, ioId);
                    if (null != gatewayIO)  {
                        devIoList.add(gatewayIO);
                    }
                }

                this.devices.add(new DeviceTO(devId, this.source.toString(), null, true, false, true, true, devDesc, devModel, devManufacturer, devClass, devOpState, new Date(), devIoList));

            }

            this.gw = new GatewayTO(this.source.toString(), gwModel, gwManufacturer, true, null, gwClass, 0l, "1", opState, new Date(), gwIoList, devices);
            
            return offset;
        } catch (Exception ex) {
            throw new MessageFormatException(ex.getLocalizedMessage());
        }

    }
    
    
    private GatewayDeviceIOTO getGatewayIO(ArrayList<GatewayDeviceIOTO> gwIOList, short ioId)
    {
        for(GatewayDeviceIOTO gw : gwIOList) {
            if (gw.getId() == ioId) {
                return gw;
            }
        }
        
        return null;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DiscoverUniDAGatewayDevicesReplyMessage other = (DiscoverUniDAGatewayDevicesReplyMessage) obj;
        if (this.gw != other.gw && (this.gw == null || !this.gw.equals(other.gw))) {
            return false;
        }
        if (this.devices.size() != other.devices.size() && (this.devices == null || !this.devices.equals(other.devices))) {
            return false;
        }
        return true;
    }
}

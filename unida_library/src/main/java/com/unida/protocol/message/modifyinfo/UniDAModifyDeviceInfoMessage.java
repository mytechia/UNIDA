/**
 * *****************************************************************************
 *
 * Copyright (C) 2014 Copyright 2014 Victor Sonora Pombo
 *
 *****************************************************************************
 */
package com.unida.protocol.message.modifyinfo;

import com.mytechia.commons.framework.simplemessageprotocol.Message;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.DeviceID;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDADeviceMessage;
import com.unida.protocol.message.querydevice.UniDAQueryDeviceRequestMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * <b>Creation date:</b>
 * 12-12-2014 </p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li> 1 , 12-12-2014 -> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Victor Sonora Pombo
 * @version 1
 */
public class UniDAModifyDeviceInfoMessage extends UniDADeviceMessage
{

    private long opId;

    private String name;

    private String description;

    private String location;

    public UniDAModifyDeviceInfoMessage(
            IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId,
            String name, String description, String location)
    {
        super(ontologyCodec, deviceId);
        setCommandType(MessageType.QueryDeviceStates.getTypeValue());
        setErrorCode(ErrorCode.Null.getTypeValue());
        this.opId = opId;
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public UniDAModifyDeviceInfoMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }

    public long getOpId()
    {
        return opId;
    }

    @Override
    public byte[] codeDeviceMessagePayload() throws MessageFormatException
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {

            byte[] opData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(opId, opData, 0);
            dataStream.write(opData);
            
            writeString(dataStream, name);

            writeString(dataStream, description);

            writeString(dataStream, location);

        } catch (IOException ioEx)
        {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();

    }

    @Override
    protected int decodeDeviceMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {

        int offset = initIndex;
        StringBuilder string = new StringBuilder(100);

        this.opId = EndianConversor.byteArrayLittleEndianToLong(bytes, offset);
        offset += EndianConversor.LONG_SIZE_BYTES;
        
        initIndex += Message.readStringFromBytes(string, bytes, initIndex);
        this.name = string.toString();

        initIndex += Message.readStringFromBytes(string, bytes, initIndex);
        this.description = string.toString();

        initIndex += Message.readStringFromBytes(string, bytes, initIndex);
        this.location = string.toString();

        return offset;

    }

    @Override
    protected MessageRType getMessageType()
    {
        return MessageRType.REQUEST;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<-UniDAModifyDeviceInfoMessage{" + "opId=" + opId + ", name=" + name + ", description=" + description + ", location=" + location + '}';
    }
    
    

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + (int) (this.opId ^ (this.opId >>> 32));
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.location);
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
        final UniDAModifyDeviceInfoMessage other = (UniDAModifyDeviceInfoMessage) obj;
        if (this.opId != other.opId)
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!Objects.equals(this.description, other.description))
        {
            return false;
        }
        return Objects.equals(this.location, other.location);
    }

}

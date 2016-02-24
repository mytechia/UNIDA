/**
 * *****************************************************************************
 *
 * Copyright (C) 2014 Copyright 2014 Victor Sonora Pombo
 *
 *****************************************************************************
 */
package com.hi3project.unida.protocol.message.modifyinfo;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.hi3project.unida.library.device.ontology.IUniDAOntologyCodec;
import com.hi3project.unida.protocol.UniDAAddress;
import com.hi3project.unida.protocol.message.ErrorCode;
import com.hi3project.unida.protocol.message.MessageType;
import com.hi3project.unida.protocol.message.UniDAMessage;
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
 * <li> 1 , 12-12-2014 - Initial release</li>
 * </ul>
 *
 *
 * @author Victor Sonora Pombo
 * @version 1
 */
public class UniDAModifyGatewayInfoMessage extends UniDAMessage
{

    private long opId;

    private String name;

    private String description;

    private String location;

    public UniDAModifyGatewayInfoMessage(UniDAAddress destination, IUniDAOntologyCodec ontologyCodec,
            long opId,
            String name, String description, String location)
    {
        super(destination, ontologyCodec);
        this.opId = opId;
        setCommandType(MessageType.ModifyGatewayInfo.getTypeValue());
        setErrorCode(ErrorCode.Ok.getTypeValue());
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public UniDAModifyGatewayInfoMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }

    @Override
    protected int decodeMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {

        this.opId = EndianConversor.byteArrayLittleEndianToLong(bytes, initIndex);
        initIndex += EndianConversor.LONG_SIZE_BYTES;

        StringBuilder string = new StringBuilder(100);

        initIndex += Command.readStringFromBytes(string, bytes, initIndex);
        this.name = string.toString();

        initIndex += Command.readStringFromBytes(string, bytes, initIndex);
        this.description = string.toString();

        initIndex += Command.readStringFromBytes(string, bytes, initIndex);
        this.location = string.toString();

        return initIndex;
    }

    @Override
    protected byte[] codeMessagePayload()
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {
            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(opId, idData, 0);
            dataStream.write(idData);

            writeString(dataStream, name);

            writeString(dataStream, description);

            writeString(dataStream, location);

        } catch (IOException ioEx)
        {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getLocation()
    {
        return location;
    }
        

    @Override
    public String toString()
    {
        return super.toString() + "<-UniDAModifyGatewayInfoMessage{" + "opId=" + opId + ", name=" + name + ", description=" + description + ", location=" + location + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + (int) (this.opId ^ (this.opId >>> 32));
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.location);
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
        final UniDAModifyGatewayInfoMessage other = (UniDAModifyGatewayInfoMessage) obj;
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
        if (!Objects.equals(this.location, other.location))
        {
            return false;
        }
        return true;
    }

    
    
}

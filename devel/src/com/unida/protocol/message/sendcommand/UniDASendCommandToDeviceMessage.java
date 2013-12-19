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
package com.unida.protocol.message.sendcommand;

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
public class UniDASendCommandToDeviceMessage extends UniDADeviceMessage
{

    private long opId;
    private String funcId;
    private String cmdId;
    private String[] params;

    public UniDASendCommandToDeviceMessage(IUniDAOntologyCodec ontologyCodec,
            long opId, DeviceID deviceId, String funcId, String cmdId, String[] params)
    {
        super(ontologyCodec, deviceId);
        setCommandType(MessageType.ExecuteCommand.getTypeValue());
        this.opId = opId;
        this.funcId = funcId;
        this.cmdId = cmdId;        
        this.params = Arrays.copyOf(params, params.length);
    }

    public UniDASendCommandToDeviceMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super(message, ontologyCodec);
    }

    public long getOpId()
    {
        return opId;
    }

    public String getFuncUd()
    {
        return funcId;
    }

    public String getCmdId()
    {
        return cmdId;
    }

    public String[] getParams()
    {
        return params;
    }

    @Override
    public byte[] codeDeviceMessagePayload() throws MessageFormatException
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {
            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
            EndianConversor.longToLittleEndian(opId, idData, 0);
            dataStream.write(idData);
            
            EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(this.funcId), idData, 0);
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);

            EndianConversor.uintToLittleEndian(getOntologyCodec().encodeId(this.cmdId), idData, 0);
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);

            byte[] lenData = new byte[EndianConversor.SHORT_SIZE_BYTES];
            EndianConversor.shortToLittleEndian((short) this.params.length, lenData, 0);
            dataStream.write(lenData);

            for (int i = 0; i < this.params.length; i++)
            {
                writeString(dataStream, params[i]);
            }
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

        StringBuilder string = new StringBuilder(10);

        this.opId = EndianConversor.byteArrayLittleEndianToLong(bytes, offset);
        offset += EndianConversor.LONG_SIZE_BYTES;

        this.funcId = getOntologyCodec().decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, offset));
        offset += EndianConversor.INT_SIZE_BYTES;
          
        this.cmdId = getOntologyCodec().decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, offset));
        offset += EndianConversor.INT_SIZE_BYTES;

        int numParams = EndianConversor.byteArrayLittleEndianToShort(bytes, offset);
        offset += EndianConversor.SHORT_SIZE_BYTES;

        this.params = new String[numParams];
        for (int i = 0; i < numParams; i++)
        {
            offset += readString(string, bytes, offset);
            this.params[i] = string.toString();
        }

        return offset;

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
        final UniDASendCommandToDeviceMessage other = (UniDASendCommandToDeviceMessage) obj;
        if (this.opId != other.opId)
        {
            return false;
        }
        if ((this.getDeviceID() == null) ? (other.getDeviceID() != null) : !this.getDeviceID().equals(other.getDeviceID()))
        {
            return false;
        }
        if ((this.funcId == null) ? (other.funcId != null) : !this.funcId.equals(other.funcId))
        {
            return false;
        }
        if ((this.cmdId == null) ? (other.cmdId != null) : !this.cmdId.equals(other.cmdId))
        {
            return false;
        }
        if (!Arrays.deepEquals(this.params, other.params))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 17 * hash + (int) (this.opId ^ (this.opId >>> 32));
        hash = 17 * hash + (this.getDeviceID() != null ? this.getDeviceID().hashCode() : 0);
        hash = 17 * hash + (this.cmdId != null ? this.cmdId.hashCode() : 0);
        hash = 17 * hash + (this.funcId != null ? this.funcId.hashCode() : 0);
        hash = 17 * hash + Arrays.deepHashCode(this.params);
        return hash;
    }

    @Override
    protected MessageRType getMessageType()
    {
        return MessageRType.REQUEST;
    }

    @Override
    public String toString()
    {
        return super.toString() + "; UniDASendCommandToDeviceMessage{" + "opId=" + opId
                + ", cmdId=" + cmdId + ", params=" + paramsToString() + '}';
    }

    private String paramsToString()
    {
        String pparams = "";
        for (int i = 0; i < this.getParams().length; i++)
        {
            pparams += this.getParams()[i] + " - ";
        }
        if (pparams.length() > 4)
        {
            return pparams.substring(0, pparams.length() - 3);
        } else
        {
            return pparams;
        }
    }
}

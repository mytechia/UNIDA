/**
 * *****************************************************************************
 *
 * Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
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
package com.hi3project.unida.protocol.message.autonomousbehaviour.action;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.hi3project.unida.library.device.DeviceID;
import com.hi3project.unida.library.device.ontology.IUniDAOntologyCodec;
import com.hi3project.unida.protocol.UniDAAddress;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author Victor Sonora
 */
public abstract class RuleAction
{

    private DeviceID actionDestination;

    public DeviceID getActionDestination()
    {
        return actionDestination;
    }

    public void setActionDestination(DeviceID actionDestination)
    {
        this.actionDestination = actionDestination;
    }

    public byte[] codePayload(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {
            byte[] idData = new byte[EndianConversor.INT_SIZE_BYTES];

            EndianConversor.shortToLittleEndian((short)this.getType().getValue(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);
            
            // UniDA address for action destination
            dataStream.write(this.getActionDestination().getGatewayId().getID());
            // device ID for action destination
            EndianConversor.shortToLittleEndian(this.getActionDestination().getDeviceId(), idData, 0);
            dataStream.write(idData, 0, EndianConversor.SHORT_SIZE_BYTES);

            dataStream.write(codeAction(ontologyCodec));
        } catch (IOException ex)
        {
        }

        return dataStream.toByteArray();
    }

    
    abstract byte[] codeAction(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException;

    
    public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {

        // UniDA address for action destination
        UniDAAddress actionDestinationAddress = new UniDAAddress();
        initIndex += actionDestinationAddress.decodeAddress(bytes, initIndex);
        // device ID for action destination
        short dId = EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        initIndex += EndianConversor.SHORT_SIZE_BYTES;
        
        this.setActionDestination(new DeviceID(actionDestinationAddress, dId));

        return initIndex;
    }

    
    abstract public RuleActionEnum getType();

    
    @Override
    public String toString()
    {
        return "RuleAction{" + "actionDestination=" + actionDestination + "}";
    }            
    
}

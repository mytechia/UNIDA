/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
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

package com.unida.protocol.message.debug;

import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.ErrorCode;
import com.unida.protocol.message.MessageType;
import com.unida.protocol.message.UniDAMessage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Victor Sonora
 */
public class UnidaDebugInitMessage extends UniDAMessage
{
    
    public UnidaDebugInitMessage(IUniDAOntologyCodec ontologyCodec,
            ErrorCode errCode)
    {
        super(ontologyCodec);
        setCommandType(MessageType.DebugInit.getTypeValue());
        setErrorCode(errCode.getTypeValue());
        setDestination(UniDAAddress.BROADCAST_ADDRESS);
    }
    
    public UnidaDebugInitMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException {
        super(message, ontologyCodec);
    }
    

    @Override
    protected int decodeMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException
    {
        return initIndex;
    }

    @Override
    protected byte[] codeMessagePayload()
    {
        return new ByteArrayOutputStream().toByteArray();
    }

}

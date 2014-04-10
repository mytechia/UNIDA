/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2013 Victor Sonora <victor@vsonora.com>
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

package com.unida.protocol.message.autonomousbehaviour.action;

import com.mytechia.commons.framework.simplemessageprotocol.Message;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Victor Sonora
 */
public class CommandExecutionAction extends RuleAction
{
        
    
    private String funcId;
    
    private String cmdId;
    
    private String[] params;
    
    
    public CommandExecutionAction() {}
    
    
    public CommandExecutionAction(String funcId, String cmdId, String[] params)
    {
        this.funcId = funcId;
        this.cmdId = cmdId;
        this.params = params;
    }
    

    public String getCmdId() {
        return cmdId;
    }
    
    public String getFuncId()
    {
        return funcId;
    }
           
    public String[] getParams() {
        return params;
    }
       

    @Override
    byte[] codeAction(IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        
        try {
            byte[] idData = new byte[EndianConversor.LONG_SIZE_BYTES];
                        
            // functionality ID
            EndianConversor.uintToLittleEndian(ontologyCodec.encodeId(this.funcId), idData, 0);
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);
            
            // command ID
            EndianConversor.uintToLittleEndian(ontologyCodec.encodeId(this.getCmdId()), idData, 0);          
            dataStream.write(idData, 0, EndianConversor.INT_SIZE_BYTES);

            // command parameters
            byte [] lenData = new byte[EndianConversor.SHORT_SIZE_BYTES];
            EndianConversor.shortToLittleEndian((short) this.getParams().length, lenData, 0);
            dataStream.write(lenData);
            for (String param : this.getParams())
            {
                Message.writeStringInStream(dataStream, param);
            }
            
            } catch (IOException ioEx) {
            //ByteArrayOutputStream doesn't throw exceptions in its write methods
        }

        return dataStream.toByteArray();
    }
    
    
    @Override
    public int decodePayload(byte[] bytes, int initIndex, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        
        initIndex = super.decodePayload(bytes, initIndex, ontologyCodec);
        
        // functionality ID
        this.funcId = ontologyCodec.decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, initIndex));
        initIndex += EndianConversor.INT_SIZE_BYTES;
        
        // command ID      
        this.cmdId = ontologyCodec.decodeId(EndianConversor.byteArrayLittleEndianToUInt(bytes, initIndex));
        initIndex += EndianConversor.INT_SIZE_BYTES;

        // command parameters
        StringBuilder string = new StringBuilder(10);
        int numParams = EndianConversor.byteArrayLittleEndianToShort(bytes, initIndex);
        initIndex += EndianConversor.SHORT_SIZE_BYTES;
        this.params = new String[numParams];
        for(int i=0; i<numParams; i++) {
            initIndex += Message.readStringFromBytes(string, bytes, initIndex);
            this.getParams()[i] = string.toString();
        }

        return initIndex;
    }
    

    @Override
    public RuleActionEnum getType()
    {
        return RuleActionEnum.COMMAND_EXECUTION;
    }

    @Override
    public String toString()
    {
        return super.toString() + " CommandExecutionAction{" + "funcId=" + funcId + ", cmdId=" + cmdId + ", params=" + Arrays.toString(params) + "}.";
    }        

}

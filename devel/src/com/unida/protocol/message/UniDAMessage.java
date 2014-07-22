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
package com.unida.protocol.message;

import com.mytechia.commons.framework.simplemessageprotocol.Command;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.protocol.UniDAAddress;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p>
 * <b>Description:</b>
 * Represents a basic message in the UniDA network protocol
 *
 * </p>
 *
 * <p>
 * <b>Creation date:</b> 29-01-2010</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>2 - 7-feb-2013 Added source and destination fields</li>
 * <li>1 - 29-01-2010 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public abstract class UniDAMessage extends Command
{

    protected IUniDAOntologyCodec ontologyCodec;
    protected UniDAAddress source = new UniDAAddress();
    protected UniDAAddress destination = null;

    public IUniDAOntologyCodec getOntologyCodec()
    {
        return ontologyCodec;
    }

    public final void setOntologyCodec(IUniDAOntologyCodec ontologyCodec)
    {
        this.ontologyCodec = ontologyCodec;
    }

    public UniDAMessage(byte[] message, IUniDAOntologyCodec ontologyCodec) throws MessageFormatException
    {
        super();
        setOntologyCodec(ontologyCodec);
        super.decodeMessage(message);
    }

    public UniDAMessage(IUniDAOntologyCodec ontologyCodec)
    {
        super();
        setOntologyCodec(ontologyCodec);
    }

    public UniDAMessage(UniDAAddress destination, IUniDAOntologyCodec ontologyCodec)
    {
        this(ontologyCodec);
        this.destination = destination;
    }

    public void setSource(UniDAAddress source)
    {
        this.source = source;
    }

    public void setDestination(UniDAAddress destination)
    {
        this.destination = destination;
    }

    public UniDAAddress getSource()
    {
        return source;
    }

    public UniDAAddress getDestination()
    {
        return destination;
    }

    protected int decodeMessageSourceDestinationAddresses(byte[] bytes, int initIndex) throws MessageFormatException
    {

        int offset = initIndex;

        //source
        this.source = new UniDAAddress();
        offset += this.source.decodeAddress(bytes, offset);

        //destination
        this.destination = new UniDAAddress();
        offset += this.destination.decodeAddress(bytes, offset);

        return offset;
    }

    @Override
    protected final int decodeMessageData(byte[] bytes, int initIndex)
            throws MessageFormatException
    {

        int offset = initIndex;

        offset = this.decodeMessageSourceDestinationAddresses(bytes, offset);

        return decodeMessagePayload(bytes, offset);

    }

    protected abstract int decodeMessagePayload(byte[] bytes, int initIndex) throws MessageFormatException;

    @Override
    protected final byte[] codeMessageData() throws MessageFormatException
    {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

        try
        {
            dataStream.write(this.source.getID());
            dataStream.write(this.destination.getID());
            dataStream.write(codeMessagePayload());
        } catch (IOException ex)
        {
            // 
        }

        return dataStream.toByteArray();

    }

    protected abstract byte[] codeMessagePayload() throws MessageFormatException;

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 89 * hash + (this.source != null ? this.source.hashCode() : 0);
        hash = 89 * hash + (this.destination != null ? this.destination.hashCode() : 0);
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
        final UniDAMessage other = (UniDAMessage) obj;
        if (this.source != other.source && (this.source == null || !this.source.equals(other.source)))
        {
            return false;
        }
        return this.destination == other.destination || (this.destination != null && this.destination.equals(other.destination));
    }

    @Override
    public String toString()
    {
        return "UniDAMessage{" + "type=" + getCommandType() + ", source=" + source + ", destination=" + destination + '}';
    }

}

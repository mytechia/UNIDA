/**
 * *****************************************************************************
 *
 * Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2013 Gervasio Varela <gervarela@picandocodigo.com>
 * Copyright (C) 2013 Victor Sonora <victor@vsonora.com>
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
package com.unida.protocol;

import com.mytechia.commons.framework.simplemessageprotocol.udp.UDPAddress;
import com.mytechia.commons.util.conversion.EndianConversor;
import com.mytechia.commons.util.net.IPUtil;
import com.unida.library.device.exception.UniDAIDFormatException;
import com.unida.library.conf.UnidaLibraryConfiguration;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * <p><b>Description:</b></br>
 * Univocally identifies a UniDA gateway in the network
 *
 * </p>
 *
 * <p><b>Creation date:</b> 5-feb-2013</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 5-feb-2013<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez - Integrated Group for Engineering Research
 * @version 1
 */
public class UniDAAddress
{

    private static final String SEPARATOR = "-";
    public static final int ADDRESS_LEN = 4;
    public static final int ID_BYTES_LEN = ADDRESS_LEN + EndianConversor.SHORT_SIZE_BYTES;
    private byte[] address;
    private short leafNumber; //2bytes - unsigned short
    public static final UniDAAddress GROUPS_GATEWAY_ID = new UniDAAddress(new byte[]
    {
        (byte) 0, (byte) 0, (byte) 0, (byte) 0
    }, (short) 0);
    public static final UniDAAddress BROADCAST_ADDRESS = new UniDAAddress(new byte[]
    {
        (byte) 255, (byte) 255, (byte) 255, (byte) 255
    }, (short) 0);

    public UniDAAddress()
    {
        leafNumber = 0;

        try
        {

            String localIP = UnidaLibraryConfiguration.getInstance().
                    getParam(UnidaLibraryConfiguration.LOCAL_IP);

            if (localIP != null)
            {
                address = InetAddress.getByName(localIP).getAddress();
            } else
            {
                address = IPUtil.getLocalIP().getAddress();
            }

        } catch (UnknownHostException ex)
        {
            // some default IP ?
        }
    }

    public UniDAAddress(byte[] address, short leafNumber)
    {
        this.address = new byte[ADDRESS_LEN];
        System.arraycopy(address, 0, this.address, 0, ADDRESS_LEN);
        this.leafNumber = leafNumber;
    }

    public UniDAAddress(String address, short leafNumber)
    {
        setAddress(address);
        this.leafNumber = leafNumber;
    }

    public UniDAAddress(String gatewayId) throws UniDAIDFormatException
    {

        String[] parts = gatewayId.split(SEPARATOR);
        if (parts.length == 2)
        {
            setAddress(parts[0]);
            this.leafNumber = Short.valueOf(parts[1]);
        } else
        {
            throw new UniDAIDFormatException(gatewayId);
        }

    }

    public UDPAddress toUDPAddress(int port) throws UnknownHostException
    {
        return new UDPAddress(InetAddress.getByAddress(getAddress()), port);
    }

    public int decodeAddress(byte[] data, int offset)
    {
        this.address = Arrays.copyOfRange(data, offset, offset + ADDRESS_LEN);
        this.leafNumber = EndianConversor.byteArrayLittleEndianToShort(data, offset + ADDRESS_LEN);
        return ADDRESS_LEN + EndianConversor.SHORT_SIZE_BYTES;
    }

    private void setAddress(String address)
    {
        String[] bytes = address.split("\\.");
        if (bytes.length == ADDRESS_LEN)
        {
            this.address = new byte[ADDRESS_LEN];
            this.address[0] = (byte) (Integer.valueOf(bytes[0]) & 0xff);
            this.address[1] = (byte) (Integer.valueOf(bytes[1]) & 0xff);
            this.address[2] = (byte) (Integer.valueOf(bytes[2]) & 0xff);
            this.address[3] = (byte) (Integer.valueOf(bytes[3]) & 0xff);
        }
    }

    public byte[] getAddress()
    {
        return address;
    }

    public short getLeafNumber()
    {
        return leafNumber;
    }

    public byte[] getID()
    {
        byte[] id = new byte[ID_BYTES_LEN];
        byte[] addressData;
        if (this.address == null)
        {
            addressData = new byte[ADDRESS_LEN];
            Arrays.fill(addressData, (byte) 0);
        } else
        {
            addressData = this.address;
        }
        System.arraycopy(addressData, 0, id, 0, addressData.length);
        EndianConversor.shortToLittleEndian(this.leafNumber, id, addressData.length);
        return id;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.address.length; i++)
        {
            sb.append(this.address[i] & 0xFF);
            if (i != this.address.length - 1)
            {
                sb.append(".");
            }
        }
        sb.append(SEPARATOR);
        sb.append(this.leafNumber);
        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Arrays.hashCode(this.address);
        hash = 37 * hash + this.leafNumber;
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
        final UniDAAddress other = (UniDAAddress) obj;
        if (!Arrays.equals(this.address, other.address))
        {
            return false;
        }
        if (this.leafNumber != other.leafNumber)
        {
            return false;
        }
        return true;
    }
}

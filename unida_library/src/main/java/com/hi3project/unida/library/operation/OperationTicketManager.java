/**
 * *****************************************************************************
 *
 * Copyright (C) 2014 Copyright 2014 Victor Sonora Pombo
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
package com.hi3project.unida.library.operation;

/**
 * <p>
 * <b>Description:</b>
 *
 *
 * <p>
 * <b>Creation date:</b>
 * 08-04-2014 </p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li> 1 , 08-04-2014 - Initial release</li>
 * </ul>
 *
 *
 * @author Victor Sonora Pombo
 * @version 1
 */
public class OperationTicketManager
{

    private long ticketId = 0;

    public synchronized OperationTicket issueTicket(OperationTypes type)
    {
        return new OperationTicket(ticketId++, type);
    }

}

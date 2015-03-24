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
package com.hi3project.unida.library.manage.discovery;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.action.periodic.PeriodicAction;
import com.hi3project.unida.library.device.Gateway;
import com.hi3project.unida.library.manage.IUniDAManagementFacade;
import com.hi3project.unida.library.device.state.OperationalStatesEnum;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * <b>Description:</b>
 * Periodically checks if some gateway has been lost because it is not sending
 * anymore its periodic annoucement messages.
 *
 *
 * <p>
 * <b>Creation date:</b> 14-04-2010</p>
 *
 * <p>
 * <b>Changelog:</b>
 * <ul>
 * <li>1 - 14-04-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class PeriodicCheckGatewayStateAction extends PeriodicAction
{

    private final static int STEP = 50;

    private final static int EXPIRATION_TIME = 1800 * 1000;

    private IUniDAManagementFacade deviceManagament;

    public PeriodicCheckGatewayStateAction(int tickTime, IUniDAManagementFacade deviceMangament)
    {
        super(tickTime);
        this.deviceManagament = deviceMangament;
    }

    @Override
    public void executePeriodic()
    {

        for (int i = 1;; i += STEP)
        {
            try
            {

                Collection<Gateway> gateways
                        = this.deviceManagament.findAllDeviceGateways(i, STEP);

                if (!gateways.isEmpty())
                {

                    long currentTime = System.currentTimeMillis();

                    for (Gateway devGw : gateways)
                    {
                        Date changeDate = devGw.getOperationalState().getChangeTime();
                        if ((devGw.getOperationalState().getState() != OperationalStatesEnum.DISCONNECTED)
                                && (changeDate.getTime() + EXPIRATION_TIME < currentTime))
                        {

                            /* if the gateway is not marked as disconnected and its announce has expired
                             mark its state as unkown */
                            try
                            {
                                this.deviceManagament.markDeviceGatewayAsLost(devGw);
                                Logger.getAnonymousLogger().log(Level.INFO, "The gateway with id ''{0}'' has been lost.", devGw.getId());
                            } catch (InternalErrorException ex)
                            {
                                Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to edit gateway operational state for gateway '" + devGw.getId() + "'.", ex);
                            }

                            //notify the state change in a callback!
                        }
                    }

                } else
                {
                    break;
                }

            } catch (InternalErrorException ex)
            {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to retrieve gateway list.", ex);
            }
        }

    }

}

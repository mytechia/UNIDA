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

package com.unida.library.manage.im;


import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.action.periodic.PeriodicAction;
import com.mytechia.commons.framework.modelaction.action.periodic.PeriodicActionsProcessor;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.unida.library.device.ontology.IDeviceAccessLayerOntologyFacade;
import com.unida.library.device.ontology.IUniDAOntologyCodec;
import com.unida.library.device.ontology.exception.OntologyLoadingErrorException;
import com.unida.library.IUniDAInstantiationFacade;
import com.unida.library.UniDAFactory;
import com.unida.library.operation.group.DefaultGroupOperationManager;
import com.unida.library.operation.group.IGroupOperationManager;
import com.unida.library.notification.DefaultNotificationSuscriptionManager;
import com.unida.library.notification.INotificationSuscriptionManager;
import com.unida.library.operation.DefaultDeviceOperationFacade;
import com.unida.library.operation.IDeviceOperationFacade;
import com.unida.library.manage.IUniDAManagementFacade;
import com.unida.library.manage.discovery.PeriodicCheckGatewayStateAction;
import com.unida.protocol.IUniDACommChannel;
import com.unida.library.core.DefaultUniDAFacade;
import com.unida.protocol.DefaultMessageFactory;
import com.unida.protocol.message.discovery.DiscoverUniDAGatewayDevicesRequestMessage;
import com.unida.protocol.udp.UDPUniDACommChannel;
import com.unida.library.device.ontology.dogont.DogOntFacade;
import com.unida.library.device.ontology.dogont.DogOntCodec;
import java.util.ArrayList;

/**
 * <p><b>Description:</b></br>
 * Default implementation of the IUniDAInstantiationFacade interface.
 * </p>
 *
 * <p><b>Creation date:</b> 27-01-2013</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 27-01-2013<\br> Initial release.</li>
 * </ul>
 * </p>
 *
 * @author Victor Sonora
 * @version 1
 */
public class InMemoryUniDAInstantiationFacade implements IUniDAInstantiationFacade
{



    private INotificationSuscriptionManager notificationManager = null;
    private IDeviceOperationFacade deviceOperationFacade = null;

    private InMemoryUniDAManagementFacade deviceManageFacade = null;

    private IDeviceAccessLayerOntologyFacade ontologyFacade = null;


    protected UniDAFactory dalFactory = null;

    protected DefaultUniDAFacade dalProtocolFacade;

    protected IUniDACommChannel commChannel;

    protected PeriodicActionsProcessor periodicActionsProcessor;
    
    private IUniDAOntologyCodec ontologyCodec;


    private boolean initialized = false;
    


    /**
     * Constructor.
     * 
     * @param dogOntologyPath
     * @param deviceDBPath
     */

    public InMemoryUniDAInstantiationFacade()
    {

    }


    public IUniDAManagementFacade getDeviceManageFacade()
    {
        return deviceManageFacade;
    }

    public IDeviceOperationFacade getDeviceOperationFacade()
    {
        return deviceOperationFacade;
    }

    public IDeviceAccessLayerOntologyFacade getOntologyFacade()
    {
        return ontologyFacade;
    }


    public boolean isInitialized()
    {
        return this.initialized;
    }

    

    /**
     * 
     * @throws InternalErrorException
     */
    public void initialize() throws InternalErrorException
    {
        setupOntologyFacade();

        setupDeviceManageFacade();

        setupDeviceOperationFacade();

        setupPeriodicActions();

        this.initialized = true;
    }

    

    public void free() throws InternalErrorException
    {
        this.initialized = false;
    }

    


    /* PRIVATE METHODS */ 
    

    private void setupOntologyFacade() throws OntologyLoadingErrorException
    {

        this.ontologyFacade = new DogOntFacade();

        this.ontologyFacade.loadOntology();
        
        this.ontologyCodec = new DogOntCodec();
    }


    private void setupDeviceManageFacade() throws InternalErrorException
    {
         
        this.deviceManageFacade = new InMemoryUniDAManagementFacade();
    }


    protected void setupDeviceOperationFacade() throws CommunicationException
    {
        
        this.commChannel = new UDPUniDACommChannel(new DefaultMessageFactory(ontologyCodec)); //Default port
        this.dalProtocolFacade = new DefaultUniDAFacade(this.commChannel, this.deviceManageFacade, this.ontologyFacade, this.ontologyCodec);
        this.dalProtocolFacade.start();

        this.dalFactory = new UniDAFactory(this.dalProtocolFacade);

        IGroupOperationManager gom = 
                new DefaultGroupOperationManager(dalFactory, this.deviceManageFacade);

        this.notificationManager =
                new DefaultNotificationSuscriptionManager(dalFactory, this.deviceManageFacade);

        this.deviceOperationFacade =
                new DefaultDeviceOperationFacade(dalFactory, gom, this.notificationManager, this.deviceManageFacade);

        //this.deviceManageFacade.setDeviceOperationFacade(deviceOperationFacade);
        //this.deviceManageFacade.setNotificationSuscriptionManager(notificationManager);

    }


    protected void setupPeriodicActions()
    {

        ArrayList<PeriodicAction> actions = new ArrayList<PeriodicAction>(1);
        actions.add(new PeriodicCheckGatewayStateAction(90*1000, deviceManageFacade));
        this.periodicActionsProcessor = new PeriodicActionsProcessor(actions);
        this.periodicActionsProcessor.startChecking();

    }

    
    @Override
    public void forceAnnounce() throws InternalErrorException {
        if (this.isInitialized()) {
            try {
                this.commChannel.broadcastMessage(new DiscoverUniDAGatewayDevicesRequestMessage(this.ontologyCodec));
            } catch (CommunicationException ex) {
                throw new InternalErrorException(ex);
            }
        }
    }
    
    
}

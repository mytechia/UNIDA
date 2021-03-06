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

package com.hi3project.unida.library.manage.im;


import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.action.periodic.PeriodicAction;
import com.mytechia.commons.framework.modelaction.action.periodic.PeriodicActionsProcessor;
import com.mytechia.commons.framework.simplemessageprotocol.exception.CommunicationException;
import com.hi3project.unida.library.IUniDAInstantiationFacade;
import com.hi3project.unida.library.IUniDAUserFacade;
import com.hi3project.unida.library.UniDANetworkFactory;
import com.hi3project.unida.library.conf.UnidaLibraryConfiguration;
import com.hi3project.unida.library.core.DefaultUniDANetworkFacade;
import com.hi3project.unida.library.device.ontology.IDeviceAccessLayerOntologyFacade;
import com.hi3project.unida.library.device.ontology.IUniDAOntologyCodec;
import com.hi3project.unida.library.device.ontology.dogont.DogOntCodec;
import com.hi3project.unida.library.device.ontology.dogont.DogOntFacade;
import com.hi3project.unida.library.device.ontology.exception.OntologyLoadingErrorException;
import com.hi3project.unida.library.manage.discovery.PeriodicCheckGatewayStateAction;
import com.hi3project.unida.library.notification.DefaultNotificationSuscriptionManager;
import com.hi3project.unida.library.notification.INotificationSuscriptionManager;
import com.hi3project.unida.library.operation.device.DefaultDeviceOperationFacade;
import com.hi3project.unida.library.operation.device.IDeviceOperationFacade;
import com.hi3project.unida.library.operation.device.group.DefaultGroupOperationManager;
import com.hi3project.unida.library.operation.device.group.IGroupOperationManager;
import com.hi3project.unida.library.operation.gateway.DefaultGatewayOperationFacade;
import com.hi3project.unida.library.operation.gateway.IGatewayOperationFacade;
import com.hi3project.unida.protocol.DefaultMessageFactory;
import com.hi3project.unida.protocol.IUniDACommChannel;
import com.hi3project.unida.protocol.udp.UDPUniDACommChannel;
import java.util.ArrayList;

/**
 * <p><b>Description:</b></p>
 * Default implementation of the IUniDAInstantiationFacade interface.
 *
 *
 * <p><b>Creation date:</b> 27-01-2013</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 27-01-2013 Initial release.</li>
 * </ul>
 *
 *
 * @author Victor Sonora
 * @version 1
 */
public class InMemoryUniDAInstantiationFacade implements IUniDAInstantiationFacade
{


    private boolean initialized = false;
    
    private String ip = null;
    

    private INotificationSuscriptionManager notificationManager = null;
    
    private IDeviceOperationFacade deviceOperationFacade = null;
    
    private IGatewayOperationFacade gatewayOperationFacade = null;

    private InMemoryUniDAManagementFacade deviceManageFacade = null;

    private IDeviceAccessLayerOntologyFacade ontologyFacade = null;
    
    private IUniDAOntologyCodec ontologyCodec;


    protected UniDANetworkFactory unidaFactory = null;

    protected DefaultUniDANetworkFacade unidaProtocolFacade;

    protected IUniDACommChannel commChannel;

    protected PeriodicActionsProcessor periodicActionsProcessor;
              
     
    public InMemoryUniDAInstantiationFacade() 
    {
        this.ip = null;
    }
    
    public InMemoryUniDAInstantiationFacade(String ip)
    {
        this.ip = ip;
        UnidaLibraryConfiguration.getInstance().setParam(UnidaLibraryConfiguration.LOCAL_IP, ip);
    }


    @Override
    public IUniDAUserFacade getDeviceManageFacade()
    {
        return deviceManageFacade;
    }

    @Override
    public IDeviceOperationFacade getDeviceOperationFacade()
    {
        return deviceOperationFacade;
    }

    @Override
    public IDeviceAccessLayerOntologyFacade getOntologyFacade()
    {
        return ontologyFacade;
    }
    
    @Override
    public IGatewayOperationFacade getGatewayOperationFacade()
    {
        return gatewayOperationFacade;
    }


    @Override
    public boolean isInitialized()
    {
        return this.initialized;
    }

    

    /**
     * 
     * @throws InternalErrorException
     */
    @Override
    public void initialize() throws InternalErrorException
    {
        setupOntologyFacade();

        setupDeviceManageFacade();

        setupDeviceOperationFacade();
        
        setupGatewayOperationFacade();

        setupPeriodicActions();        

        this.initialized = true;
    }

    

    @Override
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
        
        if (null != ip)
        {
            this.commChannel = new UDPUniDACommChannel(ip, new DefaultMessageFactory(ontologyCodec)); //Default port
        } else
        {
            this.commChannel = new UDPUniDACommChannel(new DefaultMessageFactory(ontologyCodec)); //Default port
        }
        
        this.unidaProtocolFacade = new DefaultUniDANetworkFacade(this.commChannel, this.deviceManageFacade, this.ontologyFacade, this.ontologyCodec);
        this.unidaProtocolFacade.start();

        this.unidaFactory = new UniDANetworkFactory(this.unidaProtocolFacade);

        IGroupOperationManager gom = 
                new DefaultGroupOperationManager(unidaFactory, this.deviceManageFacade);

        this.notificationManager =
                new DefaultNotificationSuscriptionManager(unidaFactory, this.deviceManageFacade);

        this.deviceOperationFacade =
                new DefaultDeviceOperationFacade(unidaFactory, gom, this.notificationManager, this.deviceManageFacade);
        
    }
    
    protected void setupGatewayOperationFacade()
    {
        
        this.gatewayOperationFacade = new DefaultGatewayOperationFacade(commChannel, ontologyCodec, unidaFactory, deviceManageFacade);
        
    }


    protected void setupPeriodicActions()
    {

        ArrayList<PeriodicAction> actions = new ArrayList<>(1);
        actions.add(new PeriodicCheckGatewayStateAction(90*1000, deviceManageFacade));
        this.periodicActionsProcessor = new PeriodicActionsProcessor(actions);
        this.periodicActionsProcessor.startChecking();

    }
    
    
}

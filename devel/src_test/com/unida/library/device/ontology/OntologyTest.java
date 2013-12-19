/*******************************************************************************
 *   
 *   Copyright (C) 2010,2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2010,2013 Gervasio Varela <gervarela@picandocodigo.com>
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


package com.unida.library.device.ontology;

import com.unida.library.device.ontology.DeviceStateMetadata;
import com.unida.library.device.ontology.DeviceStateValue;
import com.unida.library.device.ontology.NotificationMetadata;
import com.unida.library.device.ontology.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.GatewayClassMetadata;
import com.unida.library.device.ontology.DeviceClassMetadata;
import com.unida.library.device.ontology.NotificationFunctionalityMetadata;
import com.unida.library.device.ontology.ControlCommandMetadata;
import com.unida.library.device.ontology.dogont.DogOntFacade;
import com.unida.library.device.ontology.exception.ClassNotFoundInOntologyException;
import com.unida.library.device.ontology.exception.OntologyLoadingErrorException;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gervasio
 */
public class OntologyTest {


    private static final DeviceStateMetadata OPEN_CLOSE_STATE =
            new DeviceStateMetadata(
                "http://elite.polito.it/ontologies/dogont.owl#OpenCloseState",
                new DeviceStateValue[] {
                    new DeviceStateValue("CloseStateValue", "Close"),
                    new DeviceStateValue("OpenStateValue", "Open")
            });

    private static final ControlFunctionalityMetadata TUNER_FUNCTIONALITY =
            new ControlFunctionalityMetadata(
                "http://elite.polito.it/ontologies/dogont.owl#TunerFunctionality",
                new ControlCommandMetadata[] {
                    new ControlCommandMetadata("SetCommand", 1),
                    new ControlCommandMetadata("DownCommand", 0),
                    new ControlCommandMetadata("UpCommand", 0),
                    new ControlCommandMetadata("SetChannelCommand", 0)
            });

    private static final NotificationFunctionalityMetadata FANCONTROL_NOTIFICATION_FUNCTIONALITY =
            new NotificationFunctionalityMetadata(
                "http://elite.polito.it/ontologies/dogont.owl#FanControlNotificationFunctionality",
                new NotificationMetadata[] {
                    new NotificationMetadata("FanSetSpeedNotification", 1),
                    new NotificationMetadata("FanSpeedDownNotification", 0),
                    new NotificationMetadata("FanSpeedUpNotification", 0)
            });


    private DogOntFacade ontDalFacade;


    public OntologyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }


    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() {

        this.ontDalFacade = new DogOntFacade();

        try {
            ontDalFacade.loadOntology();
        }
        catch (OntologyLoadingErrorException ex) {
            fail(ex.getLocalizedMessage());
        }

    }

    @After
    public void tearDown() {
    }


    @Test
    public void testRetieveStateValues() 
    {

        DeviceStateMetadata openCloseState = this.ontDalFacade.getDeviceStateById("http://elite.polito.it/ontologies/dogont.owl#OpenCloseState");

        assertEquals(OPEN_CLOSE_STATE, openCloseState);

    }


    @Test
    public void testRetrieveControlFunctionalityMetadata()
    {

        ControlFunctionalityMetadata tunerFunc = this.ontDalFacade.getControlFunctionalityById("http://elite.polito.it/ontologies/dogont.owl#TunerFunctionality");

        if (!((tunerFunc.getId().equals(TUNER_FUNCTIONALITY.getId()) &&
                (tunerFunc.getAvailableCommands().length == TUNER_FUNCTIONALITY.getAvailableCommands().length))))
        {
            fail();
        }

    }


    @Test
    public void testRetrieveNotificationFunctionalityMetadata()
    {

        NotificationFunctionalityMetadata func = this.ontDalFacade.getNotificationFunctionalityById("http://elite.polito.it/ontologies/dogont.owl#FanControlNotificationFunctionality");

        if (!((func.getId().equals(FANCONTROL_NOTIFICATION_FUNCTIONALITY.getId()) &&
                (func.getAvailableNotifications().length == FANCONTROL_NOTIFICATION_FUNCTIONALITY.getAvailableNotifications().length))))
        {
            fail();
        }

    }


    @Test
    public void testRetrieveDeviceClassById1()
    {
            
        DeviceClassMetadata dcm = this.ontDalFacade.getDeviceClassById("http://elite.polito.it/ontologies/dogont.owl#DoorSensor");

        if (!((dcm.getClassId().contains("DoorSensor") &&
                (dcm.getStates().length >= 1) &&
                (dcm.getControlFunctionalities().length >= 1) &&
                dcm.getNotificationFunctionality().length >= 2)))
        {
            fail();
        }

    }
    
    @Test
    public void testRetrieveDeviceClassById2()
    {
            
        DeviceClassMetadata dcm = this.ontDalFacade.getDeviceClassById("http://elite.polito.it/ontologies/dogont.owl#DimmerLamp");

        if (!((dcm.getClassId().contains("DimmerLamp") &&
                (dcm.getStates().length >= 2) &&
                (dcm.getControlFunctionalities().length >= 1) )))
        {
            fail();
        }

    }
    
    @Test
    public void testRetrieveDeviceClassById3()
    {
            
        DeviceClassMetadata dcm = this.ontDalFacade.getDeviceClassById("http://elite.polito.it/ontologies/dogont.owl#OnOffSwitch");     

        if (!((dcm.getClassId().contains("OnOffSwitch") &&
                (dcm.getStates().length >= 1) &&
                (dcm.getControlFunctionalities().length >= 1) )))
        {
            fail();
        }

    }
    
    @Test
    public void testRetrieveDeviceClassById4()
    {
            
        DeviceClassMetadata dcm = this.ontDalFacade.getDeviceClassById("http://elite.polito.it/ontologies/dogont.owl#Lamp");     

        if (!((dcm.getClassId().contains("Lamp") &&
                (dcm.getStates().length >= 1) &&
                (dcm.getControlFunctionalities().length >= 1) )))
        {
            fail();
        }

    }


    @Test
    public void testRetrieveAllDeviceClasses()
    {

        if (!(this.ontDalFacade.getAllDeviceClasses().size() >= 10))
            fail();

    }


    @Test
    public void testRetrieveDeviceDescendants()
    {
        Collection<DeviceClassMetadata> subclases =
                this.ontDalFacade.getDeviceClassDescendants(new DeviceClassMetadata("http://elite.polito.it/ontologies/dogont.owl#AntiIntrusionSystem",
                    new ControlFunctionalityMetadata[0],
                    new DeviceStateMetadata[0],
                    new NotificationFunctionalityMetadata[0]));

        if (!(subclases.size() == 5))
            fail();
    }


    @Test
    public void testRetrieveAllDeviceGatewayClasses()
    {

        GatewayClassMetadata gw = null;
        try {
            gw = this.ontDalFacade.getGatewayClassById("http://elite.polito.it/ontologies/dogont.owl#KonnexGateway");
        }
        catch (ClassNotFoundInOntologyException ex) {
            fail();
        }

        Collection<GatewayClassMetadata> gwClasses = this.ontDalFacade.getAllGatewayClasses();

        if (gwClasses.size() < 1)
            fail();

    }
    
    
    

}
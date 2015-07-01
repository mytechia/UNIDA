/**
 * *****************************************************************************
 *
 * Copyright (C) 2010,2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 * Copyright (C) 2010,2013 Gervasio Varela <gervarela@picandocodigo.com>
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
package com.hi3project.unida.library.device.ontology.dogont;

import com.hi3project.unida.library.device.ontology.dogont.visitor.NParamsDescriptionReader;
import com.hi3project.unida.library.device.ontology.dogont.visitor.ValuesRestrictionReader;
import com.hi3project.unida.library.device.ontology.metadata.ControlCommandMetadata;
import com.hi3project.unida.library.device.ontology.metadata.ControlFunctionalityMetadata;
import com.hi3project.unida.library.device.ontology.metadata.DeviceClassMetadata;
import com.hi3project.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.hi3project.unida.library.device.ontology.state.DeviceStateValue;
import com.hi3project.unida.library.device.ontology.metadata.GatewayClassMetadata;
import com.hi3project.unida.library.device.ontology.IDeviceAccessLayerOntologyFacade;
import com.hi3project.unida.library.device.ontology.metadata.NotificationFunctionalityMetadata;
import com.hi3project.unida.library.device.ontology.metadata.NotificationMetadata;
import com.hi3project.unida.library.device.ontology.dogont.visitor.ClassHierarchyVisitor;
import com.hi3project.unida.library.device.ontology.dogont.visitor.DeviceStateValuesVisitor;
import com.hi3project.unida.library.device.ontology.dogont.visitor.SubClassesVisitor;
import com.hi3project.unida.library.device.ontology.dogont.visitor.SuperClassesVisitor;
import com.hi3project.unida.library.device.ontology.exception.ClassNotFoundInOntologyException;
import com.hi3project.unida.library.device.ontology.exception.OntologyLoadingErrorException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OWLOntologyMerger;

/**
 * <p><b>Description:</b></p>
 * Implementation of the IDeviceAccessLayerOntologyFacade by using the DogOnt
 * ontology (http://elite.polito.it/dogont-tools-80).
 *
 *
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public class DogOntFacade implements IDeviceAccessLayerOntologyFacade
{

    private static final String NOTHING = "Nothing";
    private static final String GATEWAYS_SUPERCLASS_ID = "DomoticNetworkComponent";
    private OWLClass NOTIFICATION_FUNCTIONALITY;
    private OWLClass CONTROL_FUNCTIONALITY;
    private OWLClass FUNCTIONALITY;
    private OWLOntologyManager ontManager;
    private OWLOntology ontology;
    private OWLOntology ontologyWithDOGONT;
    public static final String DOGONT_ONTOLOGY_PROPERTIES_FILE_PATH = "/com/hi3project/unida/library/device/ontology/dogont/generatedDOGOnt.properties";
    private static final String DOGONT_ONTOLOGY_BASE_URI = "http://elite.polito.gl/ontologies/dogont.owl";
    private static final String DOGONT_ONTOLOGY_BASE_PATH = "/com/hi3project/unida/library/device/ontology/dogont/dogont.owl";
    private static final String DOGONT_ONTOLOGY_UNIDA_SPECIFIC_PATH = "/com/hi3project/unida/library/device/ontology/dogont/unidado.owl";
    private static final String PROPERTY_FUNCTIONALITY = "hasFunctionality";
    private static final String PROPERTY_STATE = "hasState";
    private static final String PROPERTY_WRITABLE_STATE = "hasWritableState";
    private static final String PROPERTY_COMMAND = "hasCommand";
    private static final String PROPERTY_NOTIFICATION = "hasNotification";
    private static final String PROPERTY_STATE_VALUE = "hasStateValue";

    public DogOntFacade()
    {
    }

    /**
     * Loads the ontology into memory and initializes the OWL reasoner needed to
     * navigate the ontology.
     */
    public void loadOntology() throws OntologyLoadingErrorException
    {
        this.ontManager = OWLManager.createOWLOntologyManager();
        try
        {
            ontologyWithDOGONT = this.ontManager.loadOntologyFromOntologyDocument(getClass().getResourceAsStream(DOGONT_ONTOLOGY_BASE_PATH));
            this.ontManager.loadOntologyFromOntologyDocument(getClass().getResourceAsStream(DOGONT_ONTOLOGY_UNIDA_SPECIFIC_PATH));
            OWLOntologyMerger owlOntologyMerger = new OWLOntologyMerger(this.ontManager);
            this.ontology = owlOntologyMerger.createMergedOntology(this.ontManager, IRI.create(DOGONT_ONTOLOGY_BASE_URI));

            IRI funcURI = IRI.create(ontologyWithDOGONT.getOntologyID().getOntologyIRI().toString() + "#Functionality");
            FUNCTIONALITY = this.ontManager.getOWLDataFactory().getOWLClass(funcURI);
            IRI notifURI = IRI.create(ontologyWithDOGONT.getOntologyID().getOntologyIRI().toString() + "#NotificationFunctionality");
            NOTIFICATION_FUNCTIONALITY = this.ontManager.getOWLDataFactory().getOWLClass(notifURI);
            IRI controlURI = IRI.create(ontologyWithDOGONT.getOntologyID().getOntologyIRI().toString() + "#ControlFunctionality");
            CONTROL_FUNCTIONALITY = this.ontManager.getOWLDataFactory().getOWLClass(controlURI);
        } catch (OWLOntologyChangeException | OWLOntologyCreationException ex)
        {
            throw new OntologyLoadingErrorException(ex.getLocalizedMessage());
        }
    }

    public Collection<DeviceClassMetadata> getAllDeviceClasses()
    {

        IRI controllableURI = IRI.create(this.ontologyWithDOGONT.getOntologyID().getOntologyIRI().toString() + "#Controllable");
        OWLClass controllableClass = this.ontManager.getOWLDataFactory().getOWLClass(controllableURI);

        SubClassesVisitor descendantsVisitor = new SubClassesVisitor(ontology, controllableClass);
        controllableClass.accept(descendantsVisitor);
        Set<OWLClass> descendants = descendantsVisitor.getClasses();

        ArrayList<DeviceClassMetadata> devices = new ArrayList<>();
        for (OWLClass d : descendants)
        {
            String name = d.getIRI().toString();
            if (!name.equalsIgnoreCase(NOTHING))
            {
                if (d.getSubClasses(ontology).isEmpty())
                {
                    devices.add(this.getDeviceClassById(d.getIRI().toString()));
                }
            }

        }

        return devices;

    }

    public Collection<DeviceClassMetadata> getCompatibleDeviceClasses(Collection<DeviceStateMetadata> states)
    {

        Collection<DeviceClassMetadata> allDeviceClasses = getAllDeviceClasses();

        HashSet<DeviceClassMetadata> compatibleDeviceClasses = new HashSet<DeviceClassMetadata>();

        for (DeviceClassMetadata deviceClass : allDeviceClasses)
        {
            boolean compatible = true;

            for (DeviceStateMetadata state : deviceClass.getStates())
            {
                if (!states.contains(state))
                {
                    compatible = false;
                    break;
                }
            }

            if (compatible)
            {
                compatibleDeviceClasses.add(deviceClass);
            }
        }

        return compatibleDeviceClasses;

    }

    public DeviceClassMetadata getDeviceClassById(String id)
    {

        //TODO -> check that it is really a device class

        IRI deviceURI = IRI.create(id);
        OWLClass deviceClass = this.ontManager.getOWLDataFactory().getOWLClass(deviceURI);

        Set<OWLClassExpression> functionalities = new HashSet<>();
        Set<OWLClassExpression> states = new HashSet<>();
        Set<OWLClassExpression> writableStates = new HashSet<>();

        ValuesRestrictionReader reader = new ValuesRestrictionReader(this.ontology);
        reader.addPropertyToSearch(PROPERTY_FUNCTIONALITY);
        reader.addPropertyToSearch(PROPERTY_STATE);
        reader.addPropertyToSearch(PROPERTY_WRITABLE_STATE);
        deviceClass.accept(reader);
        functionalities.addAll(reader.getPossibleValues(PROPERTY_FUNCTIONALITY));
        states.addAll(reader.getPossibleValues(PROPERTY_STATE));
        writableStates.addAll(reader.getPossibleValues(PROPERTY_WRITABLE_STATE));

        //process states
        DeviceStateMetadata[] statesMetadata = new DeviceStateMetadata[states.size()];
        int i = 0;
        for (OWLClassExpression s : states)
        {
            statesMetadata[i] = this.getDeviceStateById(s.asOWLClass().getIRI().toString());
            for (OWLClassExpression ws : writableStates)
            {
                if (statesMetadata[i].getId().equals(ws.asOWLClass().getIRI().toString()))
                {
                    statesMetadata[i].setIsWritable(true);
                }
            }
            i++;
        }



        //process functionalities
        ArrayList<ControlFunctionalityMetadata> controlFuncsMetadata = new ArrayList<>();
        ArrayList<NotificationFunctionalityMetadata> notifFuncsMetadata = new ArrayList<>();
        for (OWLClassExpression f : functionalities)
        {
            SuperClassesVisitor ancestorsVisitor = new SuperClassesVisitor(ontology, f.asOWLClass());
            f.accept(ancestorsVisitor);
            Set<OWLClass> ancestors = ancestorsVisitor.getClasses();
            if (ancestors.contains(NOTIFICATION_FUNCTIONALITY))
            {
                notifFuncsMetadata.add(this.getNotificationFunctionalityById(f.asOWLClass().getIRI().toString()));
            } else if (ancestors.contains(FUNCTIONALITY))
            {
                /* if (a.contains(CONTROL_FUNCTIONALITY))
                 * Actually, there exists a functionality that isn't a control
                 * nor a notification functionality. QueryFunctionality, but
                 * currently it is mapped as a control one.
                 */
                controlFuncsMetadata.add(this.getControlFunctionalityById(f.asOWLClass().getIRI().toString()));
            }

        }

        return new DeviceClassMetadata(id,
                controlFuncsMetadata.toArray(new ControlFunctionalityMetadata[0]),
                statesMetadata,
                notifFuncsMetadata.toArray(new NotificationFunctionalityMetadata[0]));

    }

    public Collection<DeviceClassMetadata> getDeviceClassByFunctionality(ControlFunctionalityMetadata func)
    {

        LinkedList<DeviceClassMetadata> resultClasses = new LinkedList<DeviceClassMetadata>();
        Collection<DeviceClassMetadata> allClasses = getAllDeviceClasses();

        for (DeviceClassMetadata c : allClasses)
        {
            for (ControlFunctionalityMetadata cfm : c.getControlFunctionalities())
            {
                if (cfm.getId().equals(func.getId()))
                {
                    resultClasses.add(c);
                }
            }
        }

        return resultClasses;

    }

    public ControlFunctionalityMetadata getControlFunctionalityById(String id)
    {

        //TODO -> check that it is really a functionality

        IRI funcURI = IRI.create(id);
        OWLClass stateClass = this.ontManager.getOWLDataFactory().getOWLClass(funcURI);

        //retrieve the possible commands of the functionality
        Set<OWLClassExpression> commands = new HashSet<OWLClassExpression>();
        for (OWLSubClassOfAxiom a : this.ontology.getSubClassAxiomsForSubClass(stateClass))
        {
            ValuesRestrictionReader psvv = new ValuesRestrictionReader(this.ontology);
            psvv.addPropertyToSearch(PROPERTY_COMMAND);
            a.getSuperClass().accept(psvv);
            commands.addAll(psvv.getPossibleValues(PROPERTY_COMMAND));
        }

        //for every command, navigate its clas hierarchy looking for an nParams restriction
        ArrayList<ControlCommandMetadata> cmdMetadataList =
                new ArrayList<ControlCommandMetadata>(commands.size());
        for (OWLClassExpression cmd : commands)
        {
            NParamsDescriptionReader npdr = new NParamsDescriptionReader(this.ontology);
            OWLClass cmdClass = cmd.asOWLClass();
            for (OWLSubClassOfAxiom a : this.ontology.getSubClassAxiomsForSubClass(cmdClass))
            {
                a.getSuperClass().accept(npdr);
            }
            if (npdr.isNParamsDescription())
            {
                String cmdId = cmdClass.getIRI().toString();
                cmdMetadataList.add(
                        new ControlCommandMetadata(cmdId, npdr.getNParams()));
            }
        }

        return new ControlFunctionalityMetadata(id, cmdMetadataList.toArray(new ControlCommandMetadata[0]));

    }

    public ControlCommandMetadata getControlCommandMetadataById(String id)
    {

        IRI funcURI = IRI.create(id);
        OWLClass cmdClass = this.ontManager.getOWLDataFactory().getOWLClass(funcURI);

        String cmdId = id;
        int numParams = 0;
        NParamsDescriptionReader npdr = new NParamsDescriptionReader(this.ontology);

        for (OWLSubClassOfAxiom a : this.ontology.getSubClassAxiomsForSubClass(cmdClass))
        {
            a.getSuperClass().accept(npdr);
        }
        if (npdr.isNParamsDescription())
        {
            cmdId = cmdClass.getIRI().toString();
            numParams = npdr.getNParams();
        }

        return new ControlCommandMetadata(cmdId, numParams);
    }

    public NotificationFunctionalityMetadata getNotificationFunctionalityById(String id)
    {

        //TODO -> check that it is really a functionality

        IRI funcURI = IRI.create(id);
        OWLClass stateClass = this.ontManager.getOWLDataFactory().getOWLClass(funcURI);

        //retrieve the possible commands of the functionality
        Set<OWLClassExpression> notifications = new HashSet<OWLClassExpression>();
        for (OWLSubClassOfAxiom a : this.ontology.getSubClassAxiomsForSubClass(stateClass))
        {
            ValuesRestrictionReader psvv = new ValuesRestrictionReader(this.ontology);
            psvv.addPropertyToSearch(PROPERTY_NOTIFICATION);
            a.getSuperClass().accept(psvv);
            notifications.addAll(psvv.getPossibleValues(PROPERTY_NOTIFICATION));
        }

        //for every command, navigate its clas hierarchy looking for an nParams restriction
        ArrayList<NotificationMetadata> notifMetadataList =
                new ArrayList<>(notifications.size());
        for (OWLClassExpression notif : notifications)
        {
            NParamsDescriptionReader npdr = new NParamsDescriptionReader(this.ontology);
            OWLClass notifClass = notif.asOWLClass();
            for (OWLSubClassOfAxiom a : this.ontology.getSubClassAxiomsForSubClass(notifClass))
            {
                a.getSuperClass().accept(npdr);
            }
            if (npdr.isNParamsDescription())
            {
                String notifId = notifClass.getIRI().toString();
                notifMetadataList.add(
                        new NotificationMetadata(notifId, npdr.getNParams()));
            }
        }

        return new NotificationFunctionalityMetadata(id, notifMetadataList.toArray(new NotificationMetadata[0]));
    }

    @Override
    public DeviceStateMetadata getDeviceStateById(String id)
    {

        //TODO -> check that it is really a state

        IRI stateURI = IRI.create(id);
        OWLClass stateClass = this.ontManager.getOWLDataFactory().getOWLClass(stateURI);

        //retrieve the possible StateValues of the State
        Set<OWLClassExpression> possibleValues = new HashSet<>();
        for (OWLSubClassOfAxiom a : this.ontology.getSubClassAxiomsForSubClass(stateClass))
        {
            ValuesRestrictionReader psvv = new ValuesRestrictionReader(this.ontology);
            psvv.addPropertyToSearch(PROPERTY_STATE_VALUE);
            a.getSuperClass().accept(psvv);
            possibleValues.addAll(psvv.getPossibleValues(PROPERTY_STATE_VALUE));
        }

        //for every possible StateValue, retrieve its value literal
        DeviceStateValuesVisitor svv = new DeviceStateValuesVisitor();
        for (OWLClassExpression d : possibleValues)
        {
            for (OWLSubClassOfAxiom ax : this.ontology.getSubClassAxiomsForSubClass(d.asOWLClass()))
            {
                ax.getSuperClass().accept(svv);
            }
        }

        Collection<DeviceStateValue> values = svv.getDeviceStateValues();

        return new DeviceStateMetadata(id, values.toArray(new DeviceStateValue[0]));

    }

    public Collection<DeviceClassMetadata> getDeviceClassDescendants(DeviceClassMetadata dc)
    {

        IRI deviceURI = IRI.create(dc.getClassId());
        OWLClass deviceClass = this.ontManager.getOWLDataFactory().getOWLClass(deviceURI);

        SubClassesVisitor descendantsVisitor = new SubClassesVisitor(ontology, deviceClass);
        deviceClass.accept(descendantsVisitor);
        Set<OWLClass> subclases = descendantsVisitor.getClasses();

        ArrayList<DeviceClassMetadata> devices = new ArrayList<>();
        for (OWLClass d : subclases)
        {
            String name = d.getIRI().toString();
            if (!name.equalsIgnoreCase(NOTHING))
            {
                devices.add(this.getDeviceClassById(d.getIRI().toString()));
            }
        }

        return devices;

    }

    public Collection<DeviceClassMetadata> getDeviceClassAncestors(DeviceClassMetadata dc)
    {
        //TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GatewayClassMetadata getGatewayClassById(String id) throws ClassNotFoundInOntologyException
    {

        Collection<GatewayClassMetadata> allGw = getAllGatewayClasses();

        GatewayClassMetadata result = new GatewayClassMetadata(id);

        if (allGw.contains(result))
        {
            return result;
        } else
        {
            throw new ClassNotFoundInOntologyException(id);
        }

    }

    public Collection<GatewayClassMetadata> getAllGatewayClasses()
    {

        IRI gatewaysSuperclassURI = IRI.create(this.ontologyWithDOGONT.getOntologyID().getOntologyIRI().toString() + "#" + GATEWAYS_SUPERCLASS_ID);
        OWLClass gwSprClass = this.ontManager.getOWLDataFactory().getOWLClass(gatewaysSuperclassURI);

        ClassHierarchyVisitor descendantsVisitor = new SubClassesVisitor(ontology, gwSprClass);
        gwSprClass.accept(descendantsVisitor);
        Set<OWLClass> subClasses = descendantsVisitor.getClasses();

        ArrayList<GatewayClassMetadata> result = new ArrayList<GatewayClassMetadata>();
        for (OWLClass c : subClasses)
        {
            String name = c.getIRI().toString();
            if (!name.equalsIgnoreCase(NOTHING))
            {
                if (c.getSubClasses(ontology).isEmpty())
                {
                    result.add(new GatewayClassMetadata(c.getIRI().toString()));
                }
            }
        }

        return result;

    }
}

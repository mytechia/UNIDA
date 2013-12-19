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

import com.unida.library.device.ontology.exception.ClassNotFoundInOntologyException;
import com.unida.library.device.ontology.exception.OntologyLoadingErrorException;
import java.util.Collection;


/**
 * <p><b>Description:</b></br>
 * Manages the access to the ontology concepts needed by the DAL.
 *
 * </p>
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></br>
 * <ul>
 * <li>1 - 28-dic-2009<\br> Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public interface IDeviceAccessLayerOntologyFacade
{

        /** Loads the ontology to memory and starts up every needed
         * component of ontology processing.
         *
         * @throws OntologyLoadingErrorException If the ontology cant be loaded.
         */
        void loadOntology() throws OntologyLoadingErrorException;


	/** Retrieves all the device classes supported by the DAL
	 * @return all the device classes supported by the DAL
	 */
	Collection<DeviceClassMetadata> getAllDeviceClasses();


        /** Retrieves all the device classes that contains at least all the
         * specified device states.
         * 
         * @param states The states that the required classes must have
         * @return all the device classes compatibles with the specified states
         */
        Collection<DeviceClassMetadata> getCompatibleDeviceClasses(Collection<DeviceStateMetadata> states);

        
	/** Retrieves the metadata of a device class by its id
	 * @param id The id of the device class
	 * @return the metadata of a device class with the especified id
	 */
	DeviceClassMetadata getDeviceClassById(String id) throws ClassNotFoundInOntologyException;

        
	/** Retrieves all the device classes that support the especified functionality
	 * @param func 
	 * @return 
	 */
	Collection<DeviceClassMetadata> getDeviceClassByFunctionality(ControlFunctionalityMetadata func) throws ClassNotFoundInOntologyException;


        ControlCommandMetadata getControlCommandMetadataById(String id);

        
	/** Retrieves the metadata of a control functionality by its id
	 */
	ControlFunctionalityMetadata getControlFunctionalityById(String id) throws ClassNotFoundInOntologyException;
	
	/** Retrieves the metadata of a notification functionality by its id
	 */
	NotificationFunctionalityMetadata getNotificationFunctionalityById(String id) throws ClassNotFoundInOntologyException;
	
	/** Retrieves the metadata of a device state by its id
	 */
	DeviceStateMetadata getDeviceStateById(String id) throws ClassNotFoundInOntologyException;                


        Collection<DeviceClassMetadata> getDeviceClassDescendants(DeviceClassMetadata dc) throws ClassNotFoundInOntologyException;

        
        Collection<DeviceClassMetadata> getDeviceClassAncestors(DeviceClassMetadata dc) throws ClassNotFoundInOntologyException;


        GatewayClassMetadata getGatewayClassById(String id)  throws ClassNotFoundInOntologyException;


        Collection<GatewayClassMetadata> getAllGatewayClasses();

}

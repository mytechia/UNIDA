/*******************************************************************************
 *   
 *   Copyright (C) 2009-2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2009-2013 Gervasio Varela <gervarela@picandocodigo.com>
 *   Copyright (C) 2012-2013 Victor Sonora <victor@vsonora.com>
 *   Copyright (C) 2009-2013 Alejandro Paz <alejandropl@gmail.com>
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

package com.unida.library.device.ontology.dogont.visitor;


import com.unida.library.device.ontology.state.DeviceStateValue;
import java.util.ArrayList;
import java.util.Collection;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;


/**
 * <p><b>Description:</b>
 * Generates DeviceStateValue objects from anonymous superclass axioms
 * that specify 'realStateValue' restrictions.
 *
 * </p>
 *
 * <p><b>Creation date:</b> 29-dic-2009</p>
 *
 * <p><b>Changelog:</b>
 * <ul>
 * <li>1 - 29-dic-2009 Initial release</li>
 * </ul>
 * </p>
 *
 * @author Gervasio Varela
 * @version 1
 */
public class DeviceStateValuesVisitor extends OWLObjectVisitorAdapter
{

    private static final String REAL_STATE_VALUE = "realStateValue";

    private static final int DEFAULT_VALUES_SIZE = 5;

    private static final int INIT = 0;
    private static final int CLASS_FOUND = 100;
    private static final int REAL_STATE_VALUE_FOUND = 200;

    private int state = INIT;

    private String currentStateValueId = null;
    private ArrayList<DeviceStateValue> stateValues = new ArrayList<DeviceStateValue>(DEFAULT_VALUES_SIZE);

   

    @Override
    public void visit(OWLDataHasValue desc)
    {
        if (this.state == CLASS_FOUND) {
            //check if is a realStateValue property
            String propertyName = desc.getProperty().asOWLDataProperty().getIRI().getFragment();
            if (propertyName.equals(REAL_STATE_VALUE)) {
                this.state = REAL_STATE_VALUE_FOUND;
                this.stateValues.add(new DeviceStateValue(this.currentStateValueId, desc.getValue().getLiteral()));
                this.state = INIT;
            }
        }
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom)
    {
        this.state = CLASS_FOUND;
        this.currentStateValueId = axiom.getSubClass().asOWLClass().getIRI().getFragment();
        axiom.getSuperClass().accept(this);
    }




    public Collection<DeviceStateValue> getDeviceStateValues()
    {
        return this.stateValues;
    }

}

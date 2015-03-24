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

package com.hi3project.unida.library.device.ontology.dogont.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;

/**
 * <p><b>Description:</b></p>
 * It process value restricion expresions (allValuesFrom, someValues) by
 * collection all the values that a property can have. Navigates a hierarchy of
 * clases and properties looking for the especified restrictions (in form of
 * anonymous superclases).
 *
 *
 *
 * <p><b>Creation date:</b> 29-dic-2009</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 29-dic-2009 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
// */
public class ValuesRestrictionReader extends OWLObjectVisitorAdapter {

    private static final int DEFAULT_VALUES_SIZE = 10;
    private static final int INIT = 0;
    private static final int PROPERTY_FOUND = 100;
    private int state = INIT;
    private String currentFoundProperty = null;
    private HashMap<String, List<OWLClassExpression>> propertiesMap;
    private OWLOntology ontology;
    private Set<OWLClass> processedClasses;

    public ValuesRestrictionReader(OWLOntology ontology) {
        this.ontology = ontology;
        this.propertiesMap = new HashMap<>();
        this.processedClasses = new HashSet<>();
    }

    @Override
    public void visit(OWLClass desc) {

//        if (!processedClasses.contains(desc)) {
            // If we are processing inherited restrictions then
            // we recursively visit named supers and also the equivalent classes            
            // Note that we need to keep track of the classes that we have processed
            // so that we don't get caught out by cycles in the taxonomy
//            processedClasses.add(desc);
            if (this.state == PROPERTY_FOUND) { //when the property only have one value, it is found here
                this.state = INIT;
                addFoundValue(desc);
            } else {
                // First we visit recursively the supper classes and then the equivalent classes 
                // for the current OWLClass
                for (OWLSubClassOfAxiom ax : this.ontology.getSubClassAxiomsForSubClass(desc)) {
                    ax.getSuperClass().accept(this);
                }
                for (OWLClassExpression description : desc.getEquivalentClasses(ontology)) {
                    description.accept(this);
                }
            }
//        }

    }

    @Override
    public void visit(OWLEquivalentClassesAxiom eqc) {

        //an OWLEquivalentClassesAxiom has two components:
        // - the class that is equivalent -> so we call visit(OWLClass desc)
        //and follow the set equivalent classes
        // - the restrictions that make it equivalent -> so we go throught visit(OWLObjectUnionOf desc)
        //or visit(OWLObjectIntersectionOf desc)
        for (OWLClassExpression d : eqc.getClassExpressions()) {
            d.accept(this);
        }

    }

    @Override
    public void visit(OWLObjectUnionOf desc) {

        //one of the components of a equivalent class is an union of
        //properties that are equivalent -> we search for value restrictions
        //on that union calling visit(OWLObjectAllRestriction desc)
        //or public void visit(OWLObjectSomeRestriction desc), adding values for the searched restrictions
        if (this.state == PROPERTY_FOUND) { //when the property has multiple values, they are found here
            this.state = INIT;
            for (OWLClassExpression d : desc.getOperands()) {
                addFoundValue(d);
            }
        }

    }

    @Override
    public void visit(OWLObjectIntersectionOf desc) {

        //one of the components of a equivalent class is an intersection of
        //properties that make it equivalent -> we search for value restrictions
        //on that intersection calling visit(OWLObjectAllRestriction desc)
        //or public void visit(OWLObjectSomeRestriction desc), adding values for the searched restrictions      
        for (OWLClassExpression d : desc.getOperands()) {
            d.accept(this);
        }

    }

    @Override
    public void visit(OWLObjectAllValuesFrom desc) {

        //check if is the property we are looking for
        currentFoundProperty = desc.getProperty().asOWLObjectProperty().getIRI().getFragment();
        if (null != propertiesMap.get(currentFoundProperty)) {
            this.state = PROPERTY_FOUND;
            desc.getFiller().accept(this);
        }

    }

    @Override
    public void visit(OWLObjectSomeValuesFrom desc) {

        //check if is the property we are looking for
        currentFoundProperty = desc.getProperty().asOWLObjectProperty().getIRI().getFragment();
        if (null != propertiesMap.get(currentFoundProperty)) {
            this.state = PROPERTY_FOUND;
            desc.getFiller().accept(this);
        }

    }

    public Collection<OWLClassExpression> getPossibleValues(String property) {
        List<OWLClassExpression> result = propertiesMap.get(property);
        if (null == result) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }

    public void addPropertyToSearch(String property) {

        propertiesMap.put(property, new ArrayList<OWLClassExpression>());

    }

    private void addFoundValue(OWLClassExpression owlDescription) {

        //check if is the property we are looking for
        if (null != propertiesMap.get(currentFoundProperty)) {
            propertiesMap.get(currentFoundProperty).add(owlDescription);
        }

    }
}

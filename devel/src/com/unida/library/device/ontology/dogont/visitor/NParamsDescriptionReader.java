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

package com.unida.library.device.ontology.dogont.visitor;

import java.util.HashSet;
import java.util.Set;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;



/**
 * <p><b>Description:</b>
 * Checks if an OWLObject is a restriction about the number of parameters
 * of a command (or other concept) and retrieves the value of the restriction.
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
public class NParamsDescriptionReader extends OWLObjectVisitorAdapter
{

    private static final String PROPERTY_NAME = "nParams";

    private int nParams = 0;
    private boolean isNParams = false;


    private OWLOntology ontology;

    private Set<OWLClass> processedClasses;


    public NParamsDescriptionReader(OWLOntology ontology)
    {
        this.ontology = ontology;
        this.processedClasses = new HashSet<OWLClass>();
    }


    public void visit(OWLClass desc) {
        if(!processedClasses.contains(desc)) {
            // If we are processing inherited restrictions then
            // we recursively visit named supers.  Note that we
            // need to keep track of the classes that we have processed
            // so that we don't get caught out by cycles in the taxonomy
            processedClasses.add(desc);
            for(OWLSubClassOfAxiom ax : this.ontology.getSubClassAxiomsForSubClass(desc)) {
                ax.getSuperClass().accept(this);
                if (this.isNParamsDescription()) return; //we take the first nParams restriction found
            }
        }
    }


    @Override
    public void visit(OWLDataExactCardinality desc)
    {
        String propertyName = desc.getProperty().asOWLDataProperty().getIRI().getFragment();
        if (propertyName.equals(PROPERTY_NAME)) {
            this.isNParams = true;
            this.nParams = desc.getCardinality();
        }
    }


    //Currently, only an exact number of parameters is supported, maybe in future versions
    //of the ontology we will need support for a variable number of parameters
    @Override
    public void visit(OWLDataMaxCardinality desc)
    {
        String propertyName = desc.getProperty().asOWLDataProperty().getIRI().getFragment();
        if (propertyName.equals(PROPERTY_NAME)) {
            this.isNParams = true;
            this.nParams = desc.getCardinality();
        }
    }


    @Override
    public void visit(OWLDataMinCardinality desc)
    {
        String propertyName = desc.getProperty().asOWLDataProperty().getIRI().getFragment();
        if (propertyName.equals(PROPERTY_NAME)) {
            this.isNParams = true;
            this.nParams = desc.getCardinality();
        }
    }


    public boolean isNParamsDescription()
    {
        return this.isNParams;
    }


    public int getNParams()
    {
        return this.nParams;
    }

}

/*******************************************************************************
 *   
 *   Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright (C) 2013 Gervasio Varela <gervarela@picandocodigo.com>
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

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 *
 * @author victor
 */
public class SuperClassesVisitor extends ClassHierarchyVisitor {

    
    public SuperClassesVisitor(OWLOntology ontology, OWLClass ownClass) {
        
        super(ontology, ownClass);
        
    }

    @Override
    public void visit(OWLClass desc) {

        if (!processedClasses.contains(desc)) {            
            processedClasses.add(desc);
            for (OWLSubClassOfAxiom ax : this.ontology.getSubClassAxiomsForSubClass(desc)) {
                ax.getSuperClass().accept(this);
            }

        }

    }
   
    
}

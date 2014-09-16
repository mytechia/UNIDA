/**
 * *****************************************************************************
 *
 * Copyright (C) 2014 Mytech Ingenieria Aplicada
 * <http://www.mytechia.com>
 * Copyright (C) 2014 Victor Sonora <victor@vsonora.com>
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


package com.unida.tools.librarybasicgui.dialog;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.unida.library.manage.im.InMemoryUniDAInstantiationFacade;
import javax.swing.JOptionPane;


/**
 *
 * @author Victor Sonora Pombo
 */
public class AutonomousBehaviourQueryScenariosDialog extends javax.swing.JDialog
{

    private InMemoryUniDAInstantiationFacade instantiationFacade;
    
    
    /**
     * Creates new form AutonomousBehaviourQueryScenariosDialog
     */
    public AutonomousBehaviourQueryScenariosDialog(
            java.awt.Frame parent, 
            boolean modal,
            InMemoryUniDAInstantiationFacade instantiationFacade)
    {
        super(parent, modal);
        this.instantiationFacade = instantiationFacade;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jButtonQueryABScenarios = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListABScenarios = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButtonQueryABScenarios.setText("Query Scenarios");
        jButtonQueryABScenarios.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonQueryABScenariosActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jListABScenarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jButtonQueryABScenarios)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jButtonQueryABScenarios)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonQueryABScenariosActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonQueryABScenariosActionPerformed
    {//GEN-HEADEREND:event_jButtonQueryABScenariosActionPerformed
        try
        {
            this.instantiationFacade.getGatewayOperationFacade().requestABScenarios();
        } catch (InternalErrorException ex)
        {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }//GEN-LAST:event_jButtonQueryABScenariosActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonQueryABScenarios;
    private javax.swing.JList jListABScenarios;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
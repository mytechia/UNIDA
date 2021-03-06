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

package com.hi3project.unida.tools.librarybasicgui.dialog;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.hi3project.unida.library.device.Gateway;
import com.hi3project.unida.library.manage.im.InMemoryUniDAInstantiationFacade;
import com.hi3project.unida.library.operation.OperationFailures;
import com.hi3project.unida.library.operation.OperationTicket;
import com.hi3project.unida.library.operation.gateway.IAutonomousBehaviourCallback;
import com.hi3project.unida.protocol.message.autonomousbehaviour.UniDAABRuleVO;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Sonora Pombo
 */


public class AutonomousBehaviourChangeScenarioDialog extends javax.swing.JDialog
{

    private InMemoryUniDAInstantiationFacade instantiationFacade;
    
    /**
     * Creates new form AutonomousBehaviourChangeScenarioDialog
     */
    public AutonomousBehaviourChangeScenarioDialog(
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

        jRadioButtonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jTextScenarioID = new javax.swing.JTextField();
        jButtonChangeScenario = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextInfoExecution = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jRadioButonActivate = new javax.swing.JRadioButton();
        jRadioButtonDeactivate = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Scenario identifier:");

        jButtonChangeScenario.setText("Send message");
        jButtonChangeScenario.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonChangeScenarioActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Returned Ack:");

        jTextInfoExecution.setEditable(false);
        jTextInfoExecution.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextInfoExecution.setMinimumSize(new java.awt.Dimension(10, 39));

        jRadioButtonGroup.add(jRadioButonActivate);
        jRadioButonActivate.setSelected(true);
        jRadioButonActivate.setText("Activate");

        jRadioButtonGroup.add(jRadioButtonDeactivate);
        jRadioButtonDeactivate.setText("Deactivate");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jTextInfoExecution, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jRadioButonActivate)
                                    .addComponent(jLabel1))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextScenarioID, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jRadioButtonDeactivate)
                                        .addGap(69, 69, 69)))))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jButtonChangeScenario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButonActivate)
                    .addComponent(jRadioButtonDeactivate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextScenarioID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jButtonChangeScenario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextInfoExecution, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonChangeScenarioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonChangeScenarioActionPerformed
    {//GEN-HEADEREND:event_jButtonChangeScenarioActionPerformed
        try
        {
            this.instantiationFacade.getGatewayOperationFacade().changeABScenario(
                    this.jRadioButonActivate.isSelected(),
                    this.jTextScenarioID.getText(),
                    new ABCallback());
        } catch (InternalErrorException ex)
        {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }//GEN-LAST:event_jButtonChangeScenarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonChangeScenario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButonActivate;
    private javax.swing.JRadioButton jRadioButtonDeactivate;
    private javax.swing.ButtonGroup jRadioButtonGroup;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextInfoExecution;
    private javax.swing.JTextField jTextScenarioID;
    // End of variables declaration//GEN-END:variables

    private class ABCallback implements IAutonomousBehaviourCallback
    {

        @Override
        public void notifyGatewayAutonomousBehaviourRules(OperationTicket ticket, Gateway gateway, List<UniDAABRuleVO> rules)
        {
            throw new UnsupportedOperationException("Not supported here.");
        }

        @Override
        public void notifyAutonomousBehaviourScenarios(OperationTicket ticket, List<String> scenarioIDs)
        {
           throw new UnsupportedOperationException("Not supported here.");
        }

        @Override
        public void notifyAutonomousBehaviourACK(OperationTicket ticket)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyExecution(OperationTicket ticket)
        {
            jTextInfoExecution.setText("yippee!");
        }

        @Override
        public void notifyFailure(OperationTicket ticket, OperationFailures failure)
        {
            jTextInfoExecution.setText("oh no!");
        }
        
    }

}

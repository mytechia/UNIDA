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


package com.unida.tools.librarybasicgui.dialog;


import com.unida.tools.librarybasicgui.util.DomoParsing;
import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.ControlCommandMetadata;
import com.unida.library.device.ontology.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.DeviceState;
import com.unida.library.manage.im.InMemoryUniDAInstantiationFacade;
import com.unida.library.operation.device.IDeviceOperationCallback;
import com.unida.library.operation.OperationFailures;
import com.unida.library.operation.OperationTicket;
import java.util.Collection;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *  Dialog class that allows command messages to be sent to an UniDA device
 *  Each UniDA command has an unique ontology identifier and can can have
 * parameters (this GUI allows 1 parameter)
 *  An ACK is sent back if the command is received by its destination device
 * 
 * @author victor
 */
public class CommandsDialog extends javax.swing.JDialog {

    private InMemoryUniDAInstantiationFacade instantiationFacade;
    private String deviceId;

    /**
     *  Constructor: an object of this class must be initilized for a given UniDA device
     */
    public CommandsDialog(java.awt.Frame parent, boolean modal,
            InMemoryUniDAInstantiationFacade instantiationFacade, String deviceId)     
   {
       
        super(parent, modal);
        
        initComponents();
        
        this.instantiationFacade = instantiationFacade;        
        this.deviceId = deviceId;
        
        this.setTitle(this.getTitle() + ": " + deviceId);
        
        this.jTextFunctionalityBaseIRI.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        this.jTextFunctionalityBaseIRI.getDocument().addDocumentListener(new DocumentListener() 
        {
            
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                saveChange();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e)
            {
                saveChange();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e)
            {
                saveChange();
            }
            
            private void saveChange() 
            {
                DomoParsing.instance().changeDefaultOntologyNamespace(jTextFunctionalityBaseIRI.getText());
            }
            
        });
        
        this.jTextCommandBaseIRI.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        this.jTextCommandBaseIRI.getDocument().addDocumentListener(new DocumentListener() 
        {
            
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                saveChange();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e)
            {
                saveChange();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e)
            {
                saveChange();
            }
            
            private void saveChange() 
            {
                DomoParsing.instance().changeDefaultOntologyNamespace(jTextCommandBaseIRI.getText());
            }
            
        });
        this.jTextCommandID.requestFocus();
        
    }

    
    /*
     *  An UniDA command identified by @commandId is sent
     *  The @value1 argument is optional, and represents a parameter for the command
     */
    private void sendExecuteCommandMsg(String funcId, String commandId, String values) {
        try {            
            IDevice device = instantiationFacade.getDeviceManageFacade().findById(deviceId);
            ControlCommandMetadata commandMetadata = 
                    new ControlCommandMetadata(DomoParsing.instance().getDefaultOntologyNamespace() + commandId, 0);
            ControlFunctionalityMetadata functionalityMetadata =
                    new ControlFunctionalityMetadata(DomoParsing.instance().getDefaultOntologyNamespace() + funcId, new ControlCommandMetadata[0]);
            instantiationFacade.getDeviceOperationFacade().asyncSendCommand(
                    device,
                    functionalityMetadata,
                    commandMetadata, 
                    DomoParsing.valuesInStringToArray(values), 
                    new CommandsDialog.OpCback());
        } catch (InternalErrorException | InstanceNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextInfoExecution = new javax.swing.JTextField();
        jButtonSendCommand = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jTextCommandValue = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFunctionalityBaseIRI = new javax.swing.JTextField();
        jTextFunctionalityID = new javax.swing.JTextField();
        jTextCommandBaseIRI = new javax.swing.JTextField();
        jTextCommandID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Device command");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Functionality ID:");

        jTextInfoExecution.setEditable(false);
        jTextInfoExecution.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextInfoExecution.setMinimumSize(new java.awt.Dimension(10, 39));

        jButtonSendCommand.setText("Send Command");
        jButtonSendCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendCommandActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Returned Ack:");

        jLabel3.setText("Value (leave blank if not needed):");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Command ID to send:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jTextCommandValue, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(jTextInfoExecution, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFunctionalityBaseIRI)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFunctionalityID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextCommandBaseIRI)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextCommandID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jButtonSendCommand)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(86, 86, 86))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(116, 116, 116))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFunctionalityID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFunctionalityBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextCommandBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCommandID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextCommandValue, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSendCommand)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextInfoExecution, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSendCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendCommandActionPerformed
        jTextInfoExecution.setText("");
        sendExecuteCommandMsg(jTextFunctionalityID.getText(), jTextCommandID.getText(), jTextCommandValue.getText());
    }//GEN-LAST:event_jButtonSendCommandActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSendCommand;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextCommandBaseIRI;
    private javax.swing.JTextField jTextCommandID;
    private javax.swing.JTextField jTextCommandValue;
    private javax.swing.JTextField jTextFunctionalityBaseIRI;
    private javax.swing.JTextField jTextFunctionalityID;
    private javax.swing.JTextField jTextInfoExecution;
    // End of variables declaration//GEN-END:variables

    
    /*
     *  Implementation for IDeviceOperationCallback
     *  An object of this class will handle the ACK received for a command sent 
     * to an UniDA device
     */
    private class OpCback implements IDeviceOperationCallback {

        @Override
        public void notifyQueryDeviceStateResult(OperationTicket ticket, IDevice dev, DeviceState state) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyQueryDeviceStatesResult(OperationTicket ticket, IDevice dev, Collection<DeviceState> states) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        @Override
        public void notifyWriteDeviceStateResult(OperationTicket ticket, IDevice dev)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifySendCommandQueryStateResult(OperationTicket ticket, IDevice dev, ControlFunctionalityMetadata func,
            ControlCommandMetadata cmd, Collection<String> params, Collection<DeviceState> states) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyCommandExecution(OperationTicket ticket, IDevice dev, 
            ControlFunctionalityMetadata func, ControlCommandMetadata cmd) {
            jTextInfoExecution.setText("¡yuju!");
        }

        @Override
        public void notifyOperationFailure(OperationTicket ticket, IDevice dev, OperationFailures failure, String failureDescription) {
            jTextInfoExecution.setText("¡qué mal!");
        }
    }
    
}

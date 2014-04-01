/**
 * *****************************************************************************
 *
 * Copyright (C) 2013 Mytech Ingenieria Aplicada <http://www.mytechia.com>
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
package com.unida.tools.librarybasicgui.dialog;

import com.unida.tools.librarybasicgui.util.DomoParsing;
import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.unida.library.device.IDevice;
import com.unida.library.device.ontology.ControlCommandMetadata;
import com.unida.library.device.ontology.ControlFunctionalityMetadata;
import com.unida.library.device.ontology.DeviceState;
import com.unida.library.device.ontology.DeviceStateMetadata;
import com.unida.library.device.ontology.DeviceStateValue;
import com.unida.library.manage.im.InMemoryUniDAInstantiationFacade;
import com.unida.library.operation.IDeviceOperationCallback;
import com.unida.library.operation.OperationFailures;
import com.unida.library.operation.OperationTicket;
import java.util.Collection;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *  Dialog class that can send state request messages to an UniDA device and also
 * receive the reply messages and show its info
 *  Each state request message has an unique ontology identifier
 *  A state reply message is sent back if everything goes well. It carries the current
 * value the UniDA device has for the asked state. The value can be raw or an ontology
 * identifier
 *
 * @author victor
 */
public class DeviceStateDialog extends javax.swing.JDialog
{

    private InMemoryUniDAInstantiationFacade instantiationFacade;
    private String deviceId;

    /**
     * Constructor: an object of this class must be initilized for a given UniDA
     * device
     */
    public DeviceStateDialog(java.awt.Frame parent, boolean modal,
            InMemoryUniDAInstantiationFacade instantiationFacade, String deviceId)
    {

        super(parent, modal);

        initComponents();

        this.instantiationFacade = instantiationFacade;
        this.deviceId = deviceId;

        this.setTitle(this.getTitle() + ": " + deviceId);

        this.jTextStateBaseIRI.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        this.jTextStateBaseIRI.getDocument().addDocumentListener(new DocumentListener()
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
                DomoParsing.instance().changeDefaultOntologyNamespace(jTextStateBaseIRI.getText());
            }
        });
        this.jTextStateID.requestFocus();

    }

    
    /*
     *  An state request message with @stateId as identifier is sent to the associated
     * UniDA device
     */
    private void sendQueryDeviceStateMsg(String stateId)
    {
        try
        {
            IDevice device = instantiationFacade.getDeviceManageFacade().findById(deviceId);
            DeviceStateMetadata stateMetada =
                    new DeviceStateMetadata(DomoParsing.instance().getDefaultOntologyNamespace() + stateId, new DeviceStateValue[0]);
            this.instantiationFacade.getDeviceOperationFacade().asyncQueryDeviceState(device, stateMetada, new OpCback());
        } catch (InternalErrorException | InstanceNotFoundException ex)
        {
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
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jTextInfoExecution = new javax.swing.JTextField();
        jButtonSendRequest = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextStateID = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextInfoStateValue = new javax.swing.JTextField();
        jTextInfoStateValueID = new javax.swing.JTextField();
        jTextStateBaseIRI = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Device state");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("State ID to query:");

        jTextInfoExecution.setEditable(false);
        jTextInfoExecution.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextInfoExecution.setMinimumSize(new java.awt.Dimension(10, 39));

        jButtonSendRequest.setText("Send Request");
        jButtonSendRequest.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonSendRequestActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Returned Ack:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("State Value:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("State Value ID:");

        jTextInfoStateValue.setEditable(false);
        jTextInfoStateValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextInfoStateValue.setMinimumSize(new java.awt.Dimension(10, 39));

        jTextInfoStateValueID.setEditable(false);
        jTextInfoStateValueID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextInfoStateValueID.setMinimumSize(new java.awt.Dimension(10, 39));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextInfoExecution, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextInfoStateValue, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextInfoStateValueID, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 28, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(144, Short.MAX_VALUE)
                        .addComponent(jButtonSendRequest)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextStateBaseIRI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jTextStateID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextStateID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextStateBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonSendRequest)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextInfoExecution, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextInfoStateValueID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextInfoStateValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSendRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendRequestActionPerformed
        jTextInfoExecution.setText("");
        jTextInfoStateValue.setText("");
        jTextInfoStateValueID.setText("");


        sendQueryDeviceStateMsg(jTextStateID.getText());
    }//GEN-LAST:event_jButtonSendRequestActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSendRequest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextInfoExecution;
    private javax.swing.JTextField jTextInfoStateValue;
    private javax.swing.JTextField jTextInfoStateValueID;
    private javax.swing.JTextField jTextStateBaseIRI;
    private javax.swing.JTextField jTextStateID;
    // End of variables declaration//GEN-END:variables

    
    /*
     *  Implementation for IDeviceOperationCallback
     *  The object of this class will handle a state reply message, displaying
     * the returned value for that state
     */
    private class OpCback implements IDeviceOperationCallback
    {

        @Override
        public void notifyQueryDeviceStateResult(OperationTicket ticket, IDevice dev, DeviceState state)
        {
            jTextInfoExecution.setText("¡yuju!");
            jTextInfoStateValue.setText(state.getValue().getValueRaw());
            jTextInfoStateValueID.setText(state.getValue().getValueIdShort());
        }

        @Override
        public void notifyQueryDeviceStatesResult(OperationTicket ticket, IDevice dev, Collection<DeviceState> states)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        @Override
        public void notifyWriteDeviceStateResult(OperationTicket ticket, IDevice dev)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifySendCommandQueryStateResult(OperationTicket ticket, IDevice dev, 
            ControlFunctionalityMetadata func, ControlCommandMetadata cmd, Collection<String> params, Collection<DeviceState> states)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyCommandExecution(OperationTicket ticket, IDevice dev, 
            ControlFunctionalityMetadata func, ControlCommandMetadata cmd)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyOperationFailure(OperationTicket ticket, IDevice dev, OperationFailures failure, String failureDescription)
        {
            jTextInfoExecution.setText("¡qué mal!");
        }
    }
}

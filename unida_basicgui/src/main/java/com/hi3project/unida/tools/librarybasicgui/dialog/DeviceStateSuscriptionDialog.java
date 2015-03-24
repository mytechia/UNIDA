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
package com.hi3project.unida.tools.librarybasicgui.dialog;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.hi3project.unida.library.device.IDevice;
import com.hi3project.unida.library.device.ontology.metadata.DeviceStateMetadata;
import com.hi3project.unida.library.device.ontology.state.DeviceState;
import com.hi3project.unida.library.device.ontology.state.DeviceStateValue;
import com.hi3project.unida.library.manage.im.InMemoryUniDAInstantiationFacade;
import com.hi3project.unida.library.notification.IDeviceStateNotificationCallback;
import com.hi3project.unida.library.notification.NotificationTicket;
import com.hi3project.unida.tools.librarybasicgui.util.DomoParsing;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *  Dialog class that handles suscription (and unsuscription) messages to an UniDA
 * device, and the notification messages sent by that device
 *  With a suscription message the sender of the message is asking to receive
 * notification messages for a state of an UniDA device
 *  Each notification message informs of the vale of one state of an UniDA device
 * @author victor
 */
public class DeviceStateSuscriptionDialog extends javax.swing.JDialog
{

    private InMemoryUniDAInstantiationFacade instantiationFacade;
    private String deviceId;
    private NotificationTicket notificationTicket = null;
    private IDeviceStateNotificationCallback notificationCallback = null;

    /**
     *  Constructor: an object of this class must be initilized for a given UniDA
     * device
     */
    public DeviceStateSuscriptionDialog(
            java.awt.Frame parent,
            boolean modal,
            InMemoryUniDAInstantiationFacade instantiationFacade,
            String deviceId)
    {

        super(parent, modal);
        
        initComponents();

        this.instantiationFacade = instantiationFacade;
        this.deviceId = deviceId;

        this.setTitle(this.getTitle() + ": " + deviceId);        
        this.jButtonUnsuscribeTo.setEnabled(false);
        
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
     *  A suscription message is sent to the @stateId of the associated UniDA device
     */
    private void sendSuscribeToDeviceStateMsg(String stateId)
    {
        try
        {
            IDevice device = instantiationFacade.getDeviceManageFacade().findById(deviceId);
            DeviceStateMetadata stateMetada =
                    new DeviceStateMetadata(DomoParsing.instance().getDefaultOntologyNamespace() + stateId, new DeviceStateValue[0]);
            this.notificationCallback = new SuscribeCback();
            this.notificationTicket = this.instantiationFacade.getDeviceOperationFacade().suscribeTo(device, stateMetada, new String[0], this.notificationCallback);
            this.jButtonSuscribeTo.setEnabled(false);
            this.jButtonUnsuscribeTo.setEnabled(true);
            this.jTextStateID.setEditable(false);
        } catch (InternalErrorException | InstanceNotFoundException ex)
        {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }
    

    /*
     *  An unsuscription message is sent for the @stateId of the associated UniDA device
     */
    private void sendUnsuscribeToDeviceStateMsg(String stateId)
    {
        if (null != getNotificationTicket())
        {
            try
            {
                IDevice device = instantiationFacade.getDeviceManageFacade().findById(deviceId);
                DeviceStateMetadata stateMetada =
                        new DeviceStateMetadata(DomoParsing.instance().getDefaultOntologyNamespace() + stateId, new DeviceStateValue[0]);
                this.instantiationFacade.getDeviceOperationFacade().unsuscribeFrom(getNotificationTicket(), device, stateMetada, new String[0], this.notificationCallback);
                this.jButtonSuscribeTo.setEnabled(true);
                this.jButtonUnsuscribeTo.setEnabled(false);
                this.jTextStateID.setEditable(true);
                resetNotificationTicket();
                jTextInfoValueID.setText("");
            } catch (InternalErrorException | InstanceNotFoundException ex)
            {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }
    
    
    
    private NotificationTicket getNotificationTicket()
    {
        return this.notificationTicket;
    }

    
    private void resetNotificationTicket()
    {
        this.notificationTicket = null;
        this.notificationCallback = null;
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
        jTextStateID = new javax.swing.JTextField();
        jButtonSuscribeTo = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jTextInfoValueID = new javax.swing.JTextField();
        jButtonUnsuscribeTo = new javax.swing.JButton();
        jTextInfoValueValue = new javax.swing.JTextField();
        jTextStateBaseIRI = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Device state subscription");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("State ID to query:");

        jButtonSuscribeTo.setText("Subscribe");
        jButtonSuscribeTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuscribeToActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Last reported STATE:");

        jTextInfoValueID.setEditable(false);
        jTextInfoValueID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextInfoValueID.setMinimumSize(new java.awt.Dimension(10, 39));

        jButtonUnsuscribeTo.setText("Unsubscribe");
        jButtonUnsuscribeTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnsuscribeToActionPerformed(evt);
            }
        });

        jTextInfoValueValue.setEditable(false);
        jTextInfoValueValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextInfoValueValue.setMinimumSize(new java.awt.Dimension(10, 39));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextStateBaseIRI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextStateID, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jButtonSuscribeTo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonUnsuscribeTo)
                .addGap(72, 72, 72))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextInfoValueID, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextInfoValueValue, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextStateID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextStateBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSuscribeTo)
                    .addComponent(jButtonUnsuscribeTo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextInfoValueValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTextInfoValueID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSuscribeToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuscribeToActionPerformed
        jTextInfoValueID.setText("");
        sendSuscribeToDeviceStateMsg(jTextStateID.getText());
    }//GEN-LAST:event_jButtonSuscribeToActionPerformed

    private void jButtonUnsuscribeToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUnsuscribeToActionPerformed
        jTextInfoValueID.setText("");
        sendUnsuscribeToDeviceStateMsg(jTextStateID.getText());
    }//GEN-LAST:event_jButtonUnsuscribeToActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSuscribeTo;
    private javax.swing.JButton jButtonUnsuscribeTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextInfoValueID;
    private javax.swing.JTextField jTextInfoValueValue;
    private javax.swing.JTextField jTextStateBaseIRI;
    private javax.swing.JTextField jTextStateID;
    // End of variables declaration//GEN-END:variables

    
    /*
     *  Implementation of IDeviceStateNotificationCallback
     *  Handles notification messages that hold the info of the current value
     * of one state of one UniDA device, displaying that info
     */
    private class SuscribeCback implements IDeviceStateNotificationCallback
    {                

        @Override
        public void notifyState(NotificationTicket nt, IDevice dev, DeviceState state)
        {
            jTextInfoValueID.setText(state.getValue().getValueIdShort());
            jTextInfoValueValue.setText(state.getValue().getValueRaw());
        }
    }
}

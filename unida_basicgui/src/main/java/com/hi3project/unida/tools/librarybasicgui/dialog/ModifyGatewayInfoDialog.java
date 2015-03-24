/**
 * *****************************************************************************
 *
 * Copyright (C) 2014 Copyright 2014 Victor Sonora Pombo
 *
 *****************************************************************************
 */
package com.hi3project.unida.tools.librarybasicgui.dialog;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.hi3project.unida.library.device.Gateway;
import com.hi3project.unida.library.location.StringLiteralLocation;
import com.hi3project.unida.library.manage.im.InMemoryUniDAInstantiationFacade;
import com.hi3project.unida.tools.librarybasicgui.UNIDALibraryBasicGUI;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Sonora Pombo
 */
public class ModifyGatewayInfoDialog extends javax.swing.JDialog
{
    
    private UNIDALibraryBasicGUI basicGUI;
    private InMemoryUniDAInstantiationFacade instantiationFacade;
    private Gateway gateway;

    /**
     * Creates new form ModifyGatewayInfoDialog
     */
    public ModifyGatewayInfoDialog(UNIDALibraryBasicGUI parent, boolean modal,
            InMemoryUniDAInstantiationFacade instantiationFacade, Gateway gateway)
    {
        super(parent, modal);
        initComponents();
        
        this.basicGUI = parent;
        this.jTextName.setText(gateway.getName());
        this.jTextDescription.setText(gateway.getDescription());
        this.jTextLocation.setText(gateway.getLocation().toString());
        
        this.instantiationFacade = instantiationFacade;
        this.gateway = gateway;

        this.setTitle(this.getTitle() + ": " + gateway.getId());
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonSendRequest = new javax.swing.JButton();
        jTextName = new javax.swing.JTextField();
        jTextDescription = new javax.swing.JTextField();
        jTextLocation = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modify Gateway");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Location:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Description:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Name:");

        jButtonSendRequest.setText("Send Request");
        jButtonSendRequest.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonSendRequestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextName)
                    .addComponent(jTextDescription, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextLocation, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(161, Short.MAX_VALUE)
                .addComponent(jButtonSendRequest)
                .addGap(156, 156, 156))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jButtonSendRequest)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSendRequestActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSendRequestActionPerformed
    {//GEN-HEADEREND:event_jButtonSendRequestActionPerformed
        try
        {
            
            gateway.setName(jTextName.getText());
            gateway.setDescription(jTextDescription.getText());
            gateway.setLocation(new StringLiteralLocation(jTextLocation.getText()));
                       
            this.instantiationFacade.getGatewayOperationFacade().modifyGatewayInfo(
                    gateway.getId(), 
                    jTextName.getText(), 
                    jTextDescription.getText(),
                    jTextLocation.getText());
            
            this.basicGUI.notifyGatewayDiscovered(gateway);
            
        } catch (InternalErrorException  ex)
        {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }//GEN-LAST:event_jButtonSendRequestActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSendRequest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextDescription;
    private javax.swing.JTextField jTextLocation;
    private javax.swing.JTextField jTextName;
    // End of variables declaration//GEN-END:variables
}
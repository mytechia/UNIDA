/**
 * *****************************************************************************
 *
 * Copyright (C) 2013 Mytech Ingenieria Aplicada
 * <http://www.mytechia.com>
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
package com.unida.tools.librarybasicgui;

import com.unida.tools.librarybasicgui.dialog.DeviceStateDialog;
import com.unida.tools.librarybasicgui.dialog.DeviceStatesDialog;
import com.unida.tools.librarybasicgui.dialog.OnOffCommandsDialog;
import com.unida.tools.librarybasicgui.dialog.DeviceStateSuscriptionDialog;
import com.unida.tools.librarybasicgui.dialog.CommandsDialog;
import com.unida.tools.librarybasicgui.util.DomoParsing;
import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;

import com.unida.library.device.Gateway;
import com.unida.library.device.GatewayDeviceIO;
import com.unida.library.device.IDevice;
import com.unida.library.device.PhysicalDevice;
import com.unida.library.manage.im.InMemoryUniDAInstantiationFacade;
import com.unida.tools.librarybasicgui.dialog.AutonomousBehaviourDialog;
import com.unida.tools.librarybasicgui.dialog.DeviceWriteStateDialog;
import java.util.Collection;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Main class for this simple yet complete UniDA GUI aplication It can be viewed
 * as an example of how to use the UniDA Java library The main form is handled
 * here, and it contains: - a list of all detected UniDA gateways - a list of
 * UniDA device-IOs for the selected gateway - a list of UniDA devices for the
 * selected gateway - several buttons that launch actions related to the
 * currently selected device The device related actions are handled by specific
 * dialog classes
 *
 * @author victor
 */
public class UNIDALibraryBasicGUI extends javax.swing.JFrame
{

    private InMemoryUniDAInstantiationFacade instantiationFacade;  // only entry point needed for UniDA library
    private int numberOfGateways = 0;

    /*
     *  Constructor: it inits the GUI components, and launches the UniDA system
     */
    public UNIDALibraryBasicGUI()
    {
        
        // GUI
        initComponents();        
        initGUIButtonsState();
        initGUITablesState();

        // UniDA
        launchUniDA();

        // This thread will refresh the table that displays UniDA gateways information
        new Thread(new Refresher()).start();
    }

    /*
     *  Initializes the UniDA library. When that is done, the UniDA system is working
     * in background.
     *  The log level is also set (mainly for the logs generated from UniDA).
     */
    private void launchUniDA()
    {
        System.setProperty("java.util.logging.ConsoleHandler.level", "FINER");
        instantiationFacade = new InMemoryUniDAInstantiationFacade();
        try
        {
            instantiationFacade.initialize();
        } catch (InternalErrorException e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }

    }

    /**
     * ********************************************************************************************************
     */
    /**
     * ***************************************** GUI methods     **********************************************
     */
    /**
     * ********************************************************************************************************
     */
    /*
     *  The gateways table is reloaded.
     *  Accordingly, the device-IOs and devices tables are reset. Same with
     * all the asociated action buttons.
     */
    protected void refreshData()
    {
        cleanJTable(jTableGatewaysInfo);
        cleanJTable(jTableDevicesInfo);
        cleanJTable(jTableDeviceIOsInfo);
        removeSelectionInJTable(jTableGatewaysInfo);
        removeSelectionInJTable(jTableDevicesInfo);
        removeSelectionInJTable(jTableDeviceIOsInfo);
        jButtonOnOffCommands.setEnabled(false);
        jButtonCommands.setEnabled(false);
        jButtonStates.setEnabled(false);
        jButtonState.setEnabled(false);
        jButtonSuscribe.setEnabled(false);
        jButtonAB.setEnabled(false);
        try
        {
            Collection<Gateway> gateways = instantiationFacade.getDeviceManageFacade().findAllDeviceGateways(0, Integer.MAX_VALUE);
            int gatewayRow = 0;
            for (Gateway gateway : gateways)
            {
                jTableGatewaysInfo.getModel().setValueAt(gateway.getId().toString(), gatewayRow, 0);
                jTableGatewaysInfo.getModel().setValueAt(gateway.getOperationalState().getState().toString(), gatewayRow, 1);
                gatewayRow++;
            }
        } catch (InternalErrorException e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }

    protected void refreshGatewaysTable()
    {
        try
        {
            Collection<Gateway> gateways = instantiationFacade.getDeviceManageFacade().findAllDeviceGateways(0, Integer.MAX_VALUE);
            for (Gateway gateway : gateways)
            {
                addOrUpdateGateway(gateway.getId().toString(), gateway.getOperationalState().getState().toString());
            }
        } catch (InternalErrorException e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }

    private void addOrUpdateGateway(String id, String state)
    {
        boolean found = false;
        int rowNumber = 0;
        while (rowNumber < numberOfGateways && !found)
        {
            if (jTableGatewaysInfo.getModel().getValueAt(rowNumber, 0).equals(id))
            {
                found = true;
                jTableGatewaysInfo.getModel().setValueAt(state, rowNumber, 1);
            }
            rowNumber++;
        }
        if (!found)
        {
            jTableGatewaysInfo.getModel().setValueAt(id, rowNumber, 0);
            jTableGatewaysInfo.getModel().setValueAt(state, rowNumber, 1);
            numberOfGateways++;
        }
    }

    /*
     *  The devices table is releaded with relevant data of the devices asociated
     * with the selected gateway.    
     */
    private void loadDevices(String gatewayId)
    {
        try
        {
            Gateway gateway = instantiationFacade.getDeviceManageFacade().findDeviceGatewayById(gatewayId);
            int deviceRow = 0;
            for (IDevice d : gateway.getDevices())
            {
                PhysicalDevice device = (PhysicalDevice) d;
                jTableDevicesInfo.getModel().setValueAt(device.getId().toString(), deviceRow, 0);
                jTableDevicesInfo.getModel().setValueAt(device.getDeviceClass().getShortClassId(), deviceRow, 1);
                jTableDevicesInfo.getModel().setValueAt(DomoParsing.connectedIOsToString(device.getConnectedIOs()), deviceRow, 2);
                deviceRow++;
            }
        } catch (InternalErrorException | InstanceNotFoundException e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }

    /*
     *  The devices table is releaded with relevant data of the device-IOs asociated
     * with the selected gateway.    
     */
    private void loadIOs(String gatewayId)
    {
        try
        {
            Gateway gateway = instantiationFacade.getDeviceManageFacade().findDeviceGatewayById(gatewayId);
            int deviceRow = 0;
            for (GatewayDeviceIO gwDeviceIO : gateway.getIoList())
            {
                jTableDeviceIOsInfo.getModel().setValueAt(gwDeviceIO.getId(), deviceRow, 0);
                jTableDeviceIOsInfo.getModel().setValueAt(DomoParsing.compatibleStatesToString(gwDeviceIO.getCompatibleStates()), deviceRow, 1);                
                deviceRow++;
            }
        } catch (InternalErrorException | InstanceNotFoundException e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }

    final void initGUIButtonsState()
    {
        jButtonOnOffCommands.setEnabled(false);
        jButtonCommands.setEnabled(false);
        jButtonStates.setEnabled(false);
        jButtonState.setEnabled(false);
        jButtonWriteState.setEnabled(false);
        jButtonSuscribe.setEnabled(false);
        jButtonAB.setEnabled(false);
    }

    final void initGUITablesState()
    {
        jTableDeviceIOsInfo.getColumnModel().getColumn(1).setPreferredWidth(250);
        jTableGatewaysInfo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = jTableGatewaysInfo.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting())
                {
                    return;
                }

                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty())
                {
                    System.out.println("No rows are selected.");
                } else
                {
                    int selectedRow = lsm.getMinSelectionIndex();
                    Object valueAt = jTableGatewaysInfo.getModel().getValueAt(selectedRow, 0);
                    cleanJTable(jTableDevicesInfo);
                    cleanJTable(jTableDeviceIOsInfo);
                    jButtonOnOffCommands.setEnabled(false);
                    jButtonCommands.setEnabled(false);
                    jButtonStates.setEnabled(false);
                    jButtonWriteState.setEnabled(false);
                    jButtonState.setEnabled(false);
                    jButtonSuscribe.setEnabled(false);
                    removeSelectionInJTable(jTableDevicesInfo);
                    removeSelectionInJTable(jTableDeviceIOsInfo);
                    if (null != valueAt && valueAt.toString().length() > 0)
                    {
                        jButtonAB.setEnabled(true);
                        loadDevices(valueAt.toString());
                        loadIOs(valueAt.toString());
                    } else
                    {
                        jButtonAB.setEnabled(false);
                    }
                }
            }
        });
        jTableDevicesInfo.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting())
                {
                    return;
                }
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty())
                {
                    System.out.println("No rows are selected.");
                } else
                {
                    jButtonOnOffCommands.setEnabled(true);
                    jButtonCommands.setEnabled(true);
                    jButtonStates.setEnabled(true);
                    jButtonState.setEnabled(true);
                    jButtonSuscribe.setEnabled(true);
                    jButtonWriteState.setEnabled(true);
                }
            }
        });
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

        jPanelGatewaysInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPaneGatewaysInfo = new javax.swing.JScrollPane();
        jTableGatewaysInfo = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPaneDeviceIOsInfo = new javax.swing.JScrollPane();
        jTableDeviceIOsInfo = new javax.swing.JTable();
        jScrollPaneDevicesInfo = new javax.swing.JScrollPane();
        jTableDevicesInfo = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanelGatewaysButtons = new javax.swing.JPanel();
        jPanelDevicesButtons = new javax.swing.JPanel();
        jButtonCommands = new javax.swing.JButton();
        jButtonOnOffCommands = new javax.swing.JButton();
        jButtonStates = new javax.swing.JButton();
        jButtonState = new javax.swing.JButton();
        jButtonWriteState = new javax.swing.JButton();
        jButtonSuscribe = new javax.swing.JButton();
        jButtonAB = new javax.swing.JButton();
        jButtonForceAnnounce = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(973, 760));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jPanelGatewaysInfo.setPreferredSize(new java.awt.Dimension(973, 320));

        jLabel1.setText("Detected Gateways:");

        jScrollPaneGatewaysInfo.setPreferredSize(new java.awt.Dimension(453, 300));

        jTableGatewaysInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String []
            {
                "Gateway ID", "State"
            }
        )
        {
            boolean[] canEdit = new boolean []
            {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jTableGatewaysInfo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPaneGatewaysInfo.setViewportView(jTableGatewaysInfo);

        jLabel2.setText("Selected Gateway IOs:");

        jScrollPaneDeviceIOsInfo.setPreferredSize(new java.awt.Dimension(453, 150));

        jTableDeviceIOsInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String []
            {
                "Device IO", "Supported States"
            }
        )
        {
            boolean[] canEdit = new boolean []
            {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jTableDeviceIOsInfo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableDeviceIOsInfo.setPreferredSize(new java.awt.Dimension(300, 128));
        jScrollPaneDeviceIOsInfo.setViewportView(jTableDeviceIOsInfo);

        jScrollPaneDevicesInfo.setPreferredSize(new java.awt.Dimension(453, 150));

        jTableDevicesInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String []
            {
                "Device ID", "DeviceType", "Conected IOs"
            }
        )
        {
            boolean[] canEdit = new boolean []
            {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jTableDevicesInfo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPaneDevicesInfo.setViewportView(jTableDevicesInfo);

        jLabel3.setText("Selected Gateway Devices:");

        jPanelGatewaysButtons.setLayout(new javax.swing.BoxLayout(jPanelGatewaysButtons, javax.swing.BoxLayout.X_AXIS));

        jPanelDevicesButtons.setMaximumSize(new java.awt.Dimension(221, 25));
        jPanelDevicesButtons.setMinimumSize(new java.awt.Dimension(221, 25));
        jPanelDevicesButtons.setPreferredSize(new java.awt.Dimension(221, 25));
        jPanelDevicesButtons.setLayout(new javax.swing.BoxLayout(jPanelDevicesButtons, javax.swing.BoxLayout.X_AXIS));

        jButtonCommands.setText("Command");
        jButtonCommands.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonCommandsActionPerformed(evt);
            }
        });
        jPanelDevicesButtons.add(jButtonCommands);

        jButtonOnOffCommands.setText("On/Off Commands");
        jButtonOnOffCommands.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonOnOffCommandsActionPerformed(evt);
            }
        });
        jPanelDevicesButtons.add(jButtonOnOffCommands);

        jButtonStates.setText("Read All States");
        jButtonStates.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonStatesActionPerformed(evt);
            }
        });
        jPanelDevicesButtons.add(jButtonStates);

        jButtonState.setText("Read One State");
        jButtonState.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonStateActionPerformed(evt);
            }
        });
        jPanelDevicesButtons.add(jButtonState);

        jButtonWriteState.setText("Write One State");
        jButtonWriteState.setActionCommand("jButtonWriteState");
        jButtonWriteState.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonWriteStateActionPerformed(evt);
            }
        });
        jPanelDevicesButtons.add(jButtonWriteState);

        jButtonSuscribe.setText("Subscribe To State");
        jButtonSuscribe.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonSuscribeActionPerformed(evt);
            }
        });
        jPanelDevicesButtons.add(jButtonSuscribe);

        jButtonAB.setText("Manage Autonomous Behaviours");
        jButtonAB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonABActionPerformed(evt);
            }
        });

        jButtonForceAnnounce.setText("Force Announce");
        jButtonForceAnnounce.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonForceAnnounceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelGatewaysInfoLayout = new javax.swing.GroupLayout(jPanelGatewaysInfo);
        jPanelGatewaysInfo.setLayout(jPanelGatewaysInfoLayout);
        jPanelGatewaysInfoLayout.setHorizontalGroup(
            jPanelGatewaysInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneGatewaysInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneDevicesInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneDeviceIOsInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelGatewaysInfoLayout.createSequentialGroup()
                .addGroup(jPanelGatewaysInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGatewaysInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelGatewaysInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)))
                    .addGroup(jPanelGatewaysInfoLayout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jPanelGatewaysButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(jButtonForceAnnounce)
                        .addGap(0, 0, 0)
                        .addComponent(jButtonAB)))
                .addContainerGap(421, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGatewaysInfoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelDevicesButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 931, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        jPanelGatewaysInfoLayout.setVerticalGroup(
            jPanelGatewaysInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGatewaysInfoLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanelGatewaysInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGatewaysInfoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneGatewaysInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelGatewaysButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGatewaysInfoLayout.createSequentialGroup()
                        .addGroup(jPanelGatewaysInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonForceAnnounce)
                            .addComponent(jButtonAB))
                        .addGap(33, 33, 33)))
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneDeviceIOsInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneDevicesInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDevicesButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        getContentPane().add(jPanelGatewaysInfo);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOnOffCommandsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOnOffCommandsActionPerformed
        Object selectedDevice = jTableDevicesInfo.getModel().getValueAt(jTableDevicesInfo.getSelectionModel().getMinSelectionIndex(), 0);
        if (null != selectedDevice)
        {
            OnOffCommandsDialog commandsDialog = new OnOffCommandsDialog(this, false, instantiationFacade, selectedDevice.toString());
            commandsDialog.setVisible(true);
        }
    }//GEN-LAST:event_jButtonOnOffCommandsActionPerformed

    private void jButtonForceAnnounceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonForceAnnounceActionPerformed
        try
        {
            instantiationFacade.getGatewayOperationFacade().forceAnnounce();
        } catch (InternalErrorException ex)
        {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }//GEN-LAST:event_jButtonForceAnnounceActionPerformed

    private void jButtonStatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStatesActionPerformed
        Object selectedDevice = jTableDevicesInfo.getModel().getValueAt(jTableDevicesInfo.getSelectionModel().getMinSelectionIndex(), 0);
        if (null != selectedDevice)
        {
            DeviceStatesDialog devicesStatesDialog = new DeviceStatesDialog(this, false, instantiationFacade, selectedDevice.toString());
            devicesStatesDialog.setVisible(true);
        }
    }//GEN-LAST:event_jButtonStatesActionPerformed

    private void jButtonStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStateActionPerformed
        Object selectedDevice = jTableDevicesInfo.getModel().getValueAt(jTableDevicesInfo.getSelectionModel().getMinSelectionIndex(), 0);
        if (null != selectedDevice)
        {
            DeviceStateDialog devicesStateDialog = new DeviceStateDialog(this, false, instantiationFacade, selectedDevice.toString());
            devicesStateDialog.setVisible(true);
        }
    }//GEN-LAST:event_jButtonStateActionPerformed

    private void jButtonSuscribeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuscribeActionPerformed
        Object selectedDevice = jTableDevicesInfo.getModel().getValueAt(jTableDevicesInfo.getSelectionModel().getMinSelectionIndex(), 0);
        if (null != selectedDevice)
        {
            DeviceStateSuscriptionDialog deviceStateSuscriptionDialog = new DeviceStateSuscriptionDialog(this, false, instantiationFacade, selectedDevice.toString());
            deviceStateSuscriptionDialog.setVisible(true);
        }
    }//GEN-LAST:event_jButtonSuscribeActionPerformed

    private void jButtonABActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonABActionPerformed
        Object selectedGateway = jTableGatewaysInfo.getModel().getValueAt(jTableGatewaysInfo.getSelectionModel().getMinSelectionIndex(), 0);
        if (null != selectedGateway)
        {
            AutonomousBehaviourDialog dialog = new AutonomousBehaviourDialog(this, false, instantiationFacade, selectedGateway.toString());
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_jButtonABActionPerformed

    private void jButtonCommandsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCommandsActionPerformed
    {//GEN-HEADEREND:event_jButtonCommandsActionPerformed
        Object selectedDevice = jTableDevicesInfo.getModel().getValueAt(jTableDevicesInfo.getSelectionModel().getMinSelectionIndex(), 0);
        if (null != selectedDevice)
        {
            CommandsDialog commandsDialog = new CommandsDialog(this, false, instantiationFacade, selectedDevice.toString());
            commandsDialog.setVisible(true);
        }
    }//GEN-LAST:event_jButtonCommandsActionPerformed

    private void jButtonWriteStateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonWriteStateActionPerformed
    {//GEN-HEADEREND:event_jButtonWriteStateActionPerformed
        Object selectedDevice = jTableDevicesInfo.getModel().getValueAt(jTableDevicesInfo.getSelectionModel().getMinSelectionIndex(), 0);
        if (null != selectedDevice)
        {
            DeviceWriteStateDialog deviceWriteStateDialog = new DeviceWriteStateDialog(this, false, instantiationFacade, selectedDevice.toString());
            deviceWriteStateDialog.setVisible(true);
        }
    }//GEN-LAST:event_jButtonWriteStateActionPerformed

    /**
     * ********************************************************************************************************
     */
    /**
     * ************************************* GUI Utility methods **********************************************
     */
    /**
     * ********************************************************************************************************
     */
    /*
     *  Utility method to clean all data currently shown at a JTable
     */
    public static void cleanJTable(JTable jTable)
    {
        int columnCount = jTable.getModel().getColumnCount();
        for (int i = 0; i < jTable.getModel().getRowCount(); i++)
        {
            for (int j = 0; j < columnCount; j++)
            {
                jTable.getModel().setValueAt("", i, j);
            }
        }
    }

    /*
     *  Utility methid to remove all selected elements in a JTable
     */
    public static void removeSelectionInJTable(JTable jTable)
    {
        jTable.removeRowSelectionInterval(0, jTable.getModel().getRowCount() - 1);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new UNIDALibraryBasicGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAB;
    private javax.swing.JButton jButtonCommands;
    private javax.swing.JButton jButtonForceAnnounce;
    private javax.swing.JButton jButtonOnOffCommands;
    private javax.swing.JButton jButtonState;
    private javax.swing.JButton jButtonStates;
    private javax.swing.JButton jButtonSuscribe;
    private javax.swing.JButton jButtonWriteState;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelDevicesButtons;
    private javax.swing.JPanel jPanelGatewaysButtons;
    private javax.swing.JPanel jPanelGatewaysInfo;
    private javax.swing.JScrollPane jScrollPaneDeviceIOsInfo;
    private javax.swing.JScrollPane jScrollPaneDevicesInfo;
    private javax.swing.JScrollPane jScrollPaneGatewaysInfo;
    private javax.swing.JTable jTableDeviceIOsInfo;
    private javax.swing.JTable jTableDevicesInfo;
    private javax.swing.JTable jTableGatewaysInfo;
    // End of variables declaration//GEN-END:variables

    private class Refresher implements Runnable
    {

        private boolean on = true;

        @Override
        public void run()
        {
            while (on)
            {
                try
                {
                    Thread.sleep(1500);
                } catch (InterruptedException ex)
                {
                    JOptionPane.showMessageDialog(UNIDALibraryBasicGUI.this, ex.toString());
                }
                UNIDALibraryBasicGUI.this.refreshGatewaysTable();
            }
        }

        public void turnOff()
        {
            this.on = false;
        }
    }
}

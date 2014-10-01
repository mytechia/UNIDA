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

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.commons.framework.modelaction.exception.InstanceNotFoundException;
import com.unida.library.device.DeviceID;
import com.unida.library.device.Gateway;
import com.unida.library.device.exception.UniDAIDFormatException;
import com.unida.library.device.ontology.state.DeviceStateValue;
import com.unida.library.manage.im.InMemoryUniDAInstantiationFacade;
import com.unida.library.operation.OperationFailures;
import com.unida.library.operation.OperationTicket;
import com.unida.library.operation.gateway.IAutonomousBehaviourCallback;
import com.unida.protocol.UniDAAddress;
import com.unida.protocol.message.autonomousbehaviour.UniDAABRuleVO;
import com.unida.protocol.message.autonomousbehaviour.action.ChangeScenarioAction;
import com.unida.protocol.message.autonomousbehaviour.action.CommandExecutionAction;
import com.unida.protocol.message.autonomousbehaviour.action.LinkStateAction;
import com.unida.protocol.message.autonomousbehaviour.action.RuleAction;
import com.unida.protocol.message.autonomousbehaviour.action.WriteStateAction;
import com.unida.protocol.message.autonomousbehaviour.trigger.CronoTrigger;
import com.unida.protocol.message.autonomousbehaviour.trigger.RuleTrigger;
import com.unida.protocol.message.autonomousbehaviour.trigger.ScenarioChangeTrigger;
import com.unida.protocol.message.autonomousbehaviour.trigger.StateChangeTrigger;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateCondition;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionBinary;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionEnum;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionNary;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionNull;
import com.unida.protocol.message.autonomousbehaviour.trigger.statechange.StateConditionUnary;
import com.unida.tools.librarybasicgui.UNIDALibraryBasicGUI;
import com.unida.tools.librarybasicgui.util.DomoParsing;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Dialog class that handles everything related to the autonomous behaviour
 * rules associated with an UniDA gateway
 *
 * @author victor
 */
public class AutonomousBehaviourDialog extends javax.swing.JDialog
{

    private InMemoryUniDAInstantiationFacade instantiationFacade;
    private String gatewayAddress;

    /**
     * Creates new form, initialazing the GUI components
     *
     * @param parent
     * @param modal
     * @param instantiationFacade
     * @param gatewayAddress
     */
    public AutonomousBehaviourDialog(java.awt.Frame parent, boolean modal,
            InMemoryUniDAInstantiationFacade instantiationFacade, String gatewayAddress)
    {
        super(parent, modal);
        
        this.instantiationFacade = instantiationFacade;
        this.gatewayAddress = gatewayAddress;

        initComponents();
        initComponentsCustom();

        this.setTitle(this.getTitle() + ": " + gatewayAddress);

    }

    private void hideTriggerPanels()
    {
        this.jPanelCronoTrigger.setVisible(false);
        this.jPanelScenarioChange.setVisible(false);
        this.jPanelStateChangeTrigger.setVisible(false);
    }

    private void hideActionPanels()
    {
        this.jPanelCommandExecutionAction.setVisible(false);
        this.jPanelLinkStatesAction.setVisible(false);
        this.jPanelWriteStateAction.setVisible(false);
        this.jPanelChangeScenarioAction.setVisible(false);
    }

    
    /*
    * Asks the instantiationFacade for the data of the detected UniDA gateways
    * The actual GUI work is done by the callback
    */
    private void loadABRules()
    {
        try
        {

            Gateway gateway = instantiationFacade.getDeviceManageFacade().findDeviceGatewayById(gatewayAddress);

            instantiationFacade.getGatewayOperationFacade().requestABRules(gateway.getId(), new ABCallback());

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

        buttonGroupTriggerType = new javax.swing.ButtonGroup();
        buttonGroupActionType = new javax.swing.ButtonGroup();
        jScrollPaneABRules = new javax.swing.JScrollPane();
        jTableABRules = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButtonRemoveABRule = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonAddABRule = new javax.swing.JButton();
        jPanelTriggerContainer = new javax.swing.JPanel();
        jPanelStateChangeTrigger = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextStateChangeTriggerStateBaseIRI = new javax.swing.JTextField();
        jTextStateChangeTriggerStateID = new javax.swing.JTextField();
        jPanelStateCondition = new javax.swing.JPanel();
        jComboConditionOperator = new javax.swing.JComboBox();
        jTextStateChangeTriggerValue1 = new javax.swing.JTextField();
        jTextStateChangeTriggerValue2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextWriteStateActionStateValueIDBaseIRI1 = new javax.swing.JTextField();
        jTextWriteStateActionStateValueIDID1 = new javax.swing.JTextField();
        jTextWriteStateActionStateValueIDBaseIRI2 = new javax.swing.JTextField();
        jTextWriteStateActionStateValueIDID2 = new javax.swing.JTextField();
        jPanelCronoTrigger = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextCronoTriggerHour = new javax.swing.JTextField();
        jTextCronoTriggerWeekday = new javax.swing.JTextField();
        jTextCronoTriggerMin = new javax.swing.JTextField();
        jPanelScenarioChange = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jTextScenarioChangeTriggerScenarioID = new javax.swing.JTextField();
        jPanelActionContainer = new javax.swing.JPanel();
        jPanelCommandExecutionAction = new javax.swing.JPanel();
        jTextCommandActionFunctionalityBaseIRI = new javax.swing.JTextField();
        jTextCommandActionFunctionalityID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextCommandActionCommandBaseIRI = new javax.swing.JTextField();
        jTextCommandActionCommandID = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextCommandActionValue = new javax.swing.JTextField();
        jPanelLinkStatesAction = new javax.swing.JPanel();
        jTextLinkStateActionStateBaseIRI = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextLinkStateActionStateID = new javax.swing.JTextField();
        jPanelWriteStateAction = new javax.swing.JPanel();
        jTextWriteStateActionStateIDBaseIRI = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextWriteStateActionStateIDID = new javax.swing.JTextField();
        jTextWriteStateActionStateValueIDBaseIRI = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextWriteStateActionStateValueIDID = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextWriteStateActionStateValueRAW = new javax.swing.JTextField();
        jPanelChangeScenarioAction = new javax.swing.JPanel();
        jTextChangeScenarioActionScenarioID = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanelTriggerType = new javax.swing.JPanel();
        jRadioCronoTrigger = new javax.swing.JRadioButton();
        jRadioScenarioChangeTrigger = new javax.swing.JRadioButton();
        jRadioStateChangeTrigger = new javax.swing.JRadioButton();
        jPanelActionType = new javax.swing.JPanel();
        jRadioLinkStateAction = new javax.swing.JRadioButton();
        jRadioWriteStateAction = new javax.swing.JRadioButton();
        jRadioCommandAction = new javax.swing.JRadioButton();
        jRadioChangeScenarioAction = new javax.swing.JRadioButton();
        jButtonRefreshABRules = new javax.swing.JButton();
        jPanelABRule = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextTriggerSourceDeviceNumber = new javax.swing.JTextField();
        jTextActionDestination = new javax.swing.JTextField();
        jTextActionDestinationDeviceNumber = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Autonomous behaviour management");

        jTableABRules.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jTableABRules.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String []
            {
                "Trigger Type", "Trigger", "Action Type", "Action", "Index", "Scenario"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jTableABRules.setColumnSelectionAllowed(true);
        jTableABRules.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneABRules.setViewportView(jTableABRules);
        jTableABRules.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTableABRules.getColumnModel().getColumnCount() > 0)
        {
            jTableABRules.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTableABRules.getColumnModel().getColumn(0).setMaxWidth(100);
            jTableABRules.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTableABRules.getColumnModel().getColumn(2).setMaxWidth(100);
            jTableABRules.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTableABRules.getColumnModel().getColumn(4).setMaxWidth(50);
            jTableABRules.getColumnModel().getColumn(5).setPreferredWidth(150);
            jTableABRules.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        jLabel1.setText("Autonomous Behaviour Rules:");

        jButtonRemoveABRule.setText("Delete Selected Rule");
        jButtonRemoveABRule.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonRemoveABRuleActionPerformed(evt);
            }
        });

        jButtonAddABRule.setText("Add Rule");
        jButtonAddABRule.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonAddABRuleActionPerformed(evt);
            }
        });

        jPanelTriggerContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Trigger data"));
        jPanelTriggerContainer.setLayout(new java.awt.CardLayout());

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("State ID to query:");

        jPanelStateCondition.setBorder(javax.swing.BorderFactory.createTitledBorder("State condition"));

        jComboConditionOperator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "none", "equal to", "different to", "greater than", "lesser than", "between", "some of" }));

        jLabel16.setText("Value 1:");

        jLabel17.setText("Value 2:");

        jLabel18.setText("Type of condition:");

        javax.swing.GroupLayout jPanelStateConditionLayout = new javax.swing.GroupLayout(jPanelStateCondition);
        jPanelStateCondition.setLayout(jPanelStateConditionLayout);
        jPanelStateConditionLayout.setHorizontalGroup(
            jPanelStateConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStateConditionLayout.createSequentialGroup()
                .addGroup(jPanelStateConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStateConditionLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextStateChangeTriggerValue1))
                    .addGroup(jPanelStateConditionLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel18)
                        .addGap(29, 29, 29)
                        .addComponent(jComboConditionOperator, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelStateConditionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelStateConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelStateConditionLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jTextStateChangeTriggerValue2))
                            .addGroup(jPanelStateConditionLayout.createSequentialGroup()
                                .addComponent(jTextWriteStateActionStateValueIDBaseIRI1)
                                .addGap(26, 26, 26)
                                .addComponent(jTextWriteStateActionStateValueIDID1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStateConditionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextWriteStateActionStateValueIDBaseIRI2)
                        .addGap(26, 26, 26)
                        .addComponent(jTextWriteStateActionStateValueIDID2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelStateConditionLayout.setVerticalGroup(
            jPanelStateConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStateConditionLayout.createSequentialGroup()
                .addGroup(jPanelStateConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jComboConditionOperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelStateConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextStateChangeTriggerValue1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStateConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextWriteStateActionStateValueIDID1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextWriteStateActionStateValueIDBaseIRI1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStateConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextStateChangeTriggerValue2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStateConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextWriteStateActionStateValueIDID2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextWriteStateActionStateValueIDBaseIRI2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelStateChangeTriggerLayout = new javax.swing.GroupLayout(jPanelStateChangeTrigger);
        jPanelStateChangeTrigger.setLayout(jPanelStateChangeTriggerLayout);
        jPanelStateChangeTriggerLayout.setHorizontalGroup(
            jPanelStateChangeTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStateChangeTriggerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextStateChangeTriggerStateBaseIRI, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTextStateChangeTriggerStateID, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
            .addGroup(jPanelStateChangeTriggerLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanelStateCondition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelStateChangeTriggerLayout.setVerticalGroup(
            jPanelStateChangeTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStateChangeTriggerLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStateChangeTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextStateChangeTriggerStateBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextStateChangeTriggerStateID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelStateCondition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelTriggerContainer.add(jPanelStateChangeTrigger, "card3");

        jLabel8.setText("Weekday:");

        jLabel9.setText("Hour:");

        jLabel10.setText("Minute:");

        javax.swing.GroupLayout jPanelCronoTriggerLayout = new javax.swing.GroupLayout(jPanelCronoTrigger);
        jPanelCronoTrigger.setLayout(jPanelCronoTriggerLayout);
        jPanelCronoTriggerLayout.setHorizontalGroup(
            jPanelCronoTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCronoTriggerLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanelCronoTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addGroup(jPanelCronoTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextCronoTriggerMin, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCronoTriggerHour, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCronoTriggerWeekday, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );
        jPanelCronoTriggerLayout.setVerticalGroup(
            jPanelCronoTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCronoTriggerLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanelCronoTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextCronoTriggerWeekday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanelCronoTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextCronoTriggerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanelCronoTriggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextCronoTriggerMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jPanelTriggerContainer.add(jPanelCronoTrigger, "card2");

        jLabel20.setText("Scenario Identifier:");

        javax.swing.GroupLayout jPanelScenarioChangeLayout = new javax.swing.GroupLayout(jPanelScenarioChange);
        jPanelScenarioChange.setLayout(jPanelScenarioChangeLayout);
        jPanelScenarioChangeLayout.setHorizontalGroup(
            jPanelScenarioChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScenarioChangeLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel20)
                .addGap(47, 47, 47)
                .addComponent(jTextScenarioChangeTriggerScenarioID, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanelScenarioChangeLayout.setVerticalGroup(
            jPanelScenarioChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScenarioChangeLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanelScenarioChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextScenarioChangeTriggerScenarioID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(220, Short.MAX_VALUE))
        );

        jPanelTriggerContainer.add(jPanelScenarioChange, "card4");

        jPanelActionContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Action data"));
        jPanelActionContainer.setLayout(new java.awt.CardLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Functionality ID:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Command ID to send:");

        jLabel11.setText("Value (leave blank if not needed):");

        javax.swing.GroupLayout jPanelCommandExecutionActionLayout = new javax.swing.GroupLayout(jPanelCommandExecutionAction);
        jPanelCommandExecutionAction.setLayout(jPanelCommandExecutionActionLayout);
        jPanelCommandExecutionActionLayout.setHorizontalGroup(
            jPanelCommandExecutionActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCommandExecutionActionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCommandExecutionActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCommandExecutionActionLayout.createSequentialGroup()
                        .addComponent(jTextCommandActionFunctionalityBaseIRI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextCommandActionFunctionalityID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCommandExecutionActionLayout.createSequentialGroup()
                        .addComponent(jTextCommandActionCommandBaseIRI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextCommandActionCommandID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
            .addGroup(jPanelCommandExecutionActionLayout.createSequentialGroup()
                .addGroup(jPanelCommandExecutionActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCommandExecutionActionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCommandExecutionActionLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCommandExecutionActionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextCommandActionValue, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCommandExecutionActionLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel11)))
                .addContainerGap(181, Short.MAX_VALUE))
        );
        jPanelCommandExecutionActionLayout.setVerticalGroup(
            jPanelCommandExecutionActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCommandExecutionActionLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCommandExecutionActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextCommandActionFunctionalityID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCommandActionFunctionalityBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCommandExecutionActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextCommandActionCommandBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCommandActionCommandID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextCommandActionValue, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );

        jPanelActionContainer.add(jPanelCommandExecutionAction, "card2");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("State ID to link:");

        javax.swing.GroupLayout jPanelLinkStatesActionLayout = new javax.swing.GroupLayout(jPanelLinkStatesAction);
        jPanelLinkStatesAction.setLayout(jPanelLinkStatesActionLayout);
        jPanelLinkStatesActionLayout.setHorizontalGroup(
            jPanelLinkStatesActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLinkStatesActionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLinkStatesActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLinkStatesActionLayout.createSequentialGroup()
                        .addComponent(jTextLinkStateActionStateBaseIRI, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                        .addGap(26, 26, 26)
                        .addComponent(jTextLinkStateActionStateID, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelLinkStatesActionLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelLinkStatesActionLayout.setVerticalGroup(
            jPanelLinkStatesActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLinkStatesActionLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLinkStatesActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextLinkStateActionStateID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextLinkStateActionStateBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(183, Short.MAX_VALUE))
        );

        jPanelActionContainer.add(jPanelLinkStatesAction, "card3");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("State ID:");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("State value ID:");

        jLabel15.setText("State RAW value (leave blank if not needed):");

        javax.swing.GroupLayout jPanelWriteStateActionLayout = new javax.swing.GroupLayout(jPanelWriteStateAction);
        jPanelWriteStateAction.setLayout(jPanelWriteStateActionLayout);
        jPanelWriteStateActionLayout.setHorizontalGroup(
            jPanelWriteStateActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWriteStateActionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelWriteStateActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelWriteStateActionLayout.createSequentialGroup()
                        .addComponent(jTextWriteStateActionStateIDBaseIRI)
                        .addGap(26, 26, 26)
                        .addComponent(jTextWriteStateActionStateIDID, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelWriteStateActionLayout.createSequentialGroup()
                        .addComponent(jTextWriteStateActionStateValueIDBaseIRI)
                        .addGap(26, 26, 26)
                        .addComponent(jTextWriteStateActionStateValueIDID, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelWriteStateActionLayout.createSequentialGroup()
                        .addGroup(jPanelWriteStateActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextWriteStateActionStateValueRAW, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 175, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanelWriteStateActionLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelWriteStateActionLayout.setVerticalGroup(
            jPanelWriteStateActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWriteStateActionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWriteStateActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextWriteStateActionStateIDID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextWriteStateActionStateIDBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWriteStateActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextWriteStateActionStateValueIDID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextWriteStateActionStateValueIDBaseIRI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextWriteStateActionStateValueRAW, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jPanelActionContainer.add(jPanelWriteStateAction, "card4");

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Scenario Identifier:");

        javax.swing.GroupLayout jPanelChangeScenarioActionLayout = new javax.swing.GroupLayout(jPanelChangeScenarioAction);
        jPanelChangeScenarioAction.setLayout(jPanelChangeScenarioActionLayout);
        jPanelChangeScenarioActionLayout.setHorizontalGroup(
            jPanelChangeScenarioActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChangeScenarioActionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextChangeScenarioActionScenarioID, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanelChangeScenarioActionLayout.setVerticalGroup(
            jPanelChangeScenarioActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChangeScenarioActionLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanelChangeScenarioActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextChangeScenarioActionScenarioID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(213, Short.MAX_VALUE))
        );

        jPanelActionContainer.add(jPanelChangeScenarioAction, "card3");

        jPanelTriggerType.setBorder(javax.swing.BorderFactory.createTitledBorder("Trigger type"));

        buttonGroupTriggerType.add(jRadioCronoTrigger);
        jRadioCronoTrigger.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jRadioCronoTrigger.setText("Crono");
        jRadioCronoTrigger.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jRadioCronoTriggerActionPerformed(evt);
            }
        });

        buttonGroupTriggerType.add(jRadioScenarioChangeTrigger);
        jRadioScenarioChangeTrigger.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jRadioScenarioChangeTrigger.setText("Scenario change");
        jRadioScenarioChangeTrigger.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jRadioScenarioChangeTriggerActionPerformed(evt);
            }
        });

        buttonGroupTriggerType.add(jRadioStateChangeTrigger);
        jRadioStateChangeTrigger.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jRadioStateChangeTrigger.setSelected(true);
        jRadioStateChangeTrigger.setText("State change");
        jRadioStateChangeTrigger.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jRadioStateChangeTriggerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTriggerTypeLayout = new javax.swing.GroupLayout(jPanelTriggerType);
        jPanelTriggerType.setLayout(jPanelTriggerTypeLayout);
        jPanelTriggerTypeLayout.setHorizontalGroup(
            jPanelTriggerTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTriggerTypeLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jRadioCronoTrigger)
                .addGap(51, 51, 51)
                .addComponent(jRadioScenarioChangeTrigger)
                .addGap(48, 48, 48)
                .addComponent(jRadioStateChangeTrigger)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTriggerTypeLayout.setVerticalGroup(
            jPanelTriggerTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTriggerTypeLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanelTriggerTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioCronoTrigger)
                    .addComponent(jRadioScenarioChangeTrigger)
                    .addComponent(jRadioStateChangeTrigger))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanelActionType.setBorder(javax.swing.BorderFactory.createTitledBorder("Action Type"));

        buttonGroupActionType.add(jRadioLinkStateAction);
        jRadioLinkStateAction.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jRadioLinkStateAction.setText("Link state");
        jRadioLinkStateAction.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jRadioLinkStateActionActionPerformed(evt);
            }
        });

        buttonGroupActionType.add(jRadioWriteStateAction);
        jRadioWriteStateAction.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jRadioWriteStateAction.setText("Write State");
        jRadioWriteStateAction.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jRadioWriteStateActionActionPerformed(evt);
            }
        });

        buttonGroupActionType.add(jRadioCommandAction);
        jRadioCommandAction.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jRadioCommandAction.setSelected(true);
        jRadioCommandAction.setText("Command");
        jRadioCommandAction.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jRadioCommandActionActionPerformed(evt);
            }
        });

        buttonGroupActionType.add(jRadioChangeScenarioAction);
        jRadioChangeScenarioAction.setText("Change scenario");
        jRadioChangeScenarioAction.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jRadioChangeScenarioActionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelActionTypeLayout = new javax.swing.GroupLayout(jPanelActionType);
        jPanelActionType.setLayout(jPanelActionTypeLayout);
        jPanelActionTypeLayout.setHorizontalGroup(
            jPanelActionTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionTypeLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jRadioLinkStateAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioCommandAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioWriteStateAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioChangeScenarioAction)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelActionTypeLayout.setVerticalGroup(
            jPanelActionTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionTypeLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanelActionTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioLinkStateAction)
                    .addComponent(jRadioWriteStateAction)
                    .addComponent(jRadioCommandAction)
                    .addComponent(jRadioChangeScenarioAction))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jButtonRefreshABRules.setText("Refresh rules");
        jButtonRefreshABRules.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonRefreshABRulesActionPerformed(evt);
            }
        });

        jPanelABRule.setBorder(javax.swing.BorderFactory.createTitledBorder("Rule"));
        jPanelABRule.setName("Rule"); // NOI18N

        jLabel2.setText("Trigger UniDA device number:");

        jLabel7.setText("Action UniDA device:");

        jLabel5.setText("Action UniDA address:");

        javax.swing.GroupLayout jPanelABRuleLayout = new javax.swing.GroupLayout(jPanelABRule);
        jPanelABRule.setLayout(jPanelABRuleLayout);
        jPanelABRuleLayout.setHorizontalGroup(
            jPanelABRuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelABRuleLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jTextTriggerSourceDeviceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(216, 216, 216)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextActionDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextActionDestinationDeviceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanelABRuleLayout.setVerticalGroup(
            jPanelABRuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelABRuleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelABRuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jTextTriggerSourceDeviceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextActionDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextActionDestinationDeviceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneABRules)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonRefreshABRules)
                                .addGap(266, 266, 266)
                                .addComponent(jButtonRemoveABRule)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanelABRule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanelTriggerType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelTriggerContainer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelActionType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelActionContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(444, 444, 444)
                .addComponent(jButtonAddABRule)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneABRules, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRemoveABRule)
                    .addComponent(jButtonRefreshABRules))
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelABRule, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelTriggerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelTriggerContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanelActionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelActionContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jButtonAddABRule)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initComponentsCustom()
    {
        for (int i = 0; i < jTableABRules.getColumnCount(); i++)
        {
            TableColumn col = jTableABRules.getColumnModel().getColumn(i);
            col.setCellRenderer(new CustomCellRenderer());
        }

        jTableABRules.getSelectionModel().addListSelectionListener(new ListSelectionListener()
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
                    Object valueAt = jTableABRules.getModel().getValueAt(selectedRow, 4);
                    if (null != valueAt && valueAt.toString().length() > 0)
                    {
                        jButtonRemoveABRule.setEnabled(true);
                    } else
                    {
                        jButtonRemoveABRule.setEnabled(false);
                    }
                }
            }
        });

        UNIDALibraryBasicGUI.cleanJTable(jTableABRules);

        this.jTextStateChangeTriggerValue1.setEnabled(false);
        this.jTextWriteStateActionStateValueIDBaseIRI1.setEnabled(false);
        this.jTextWriteStateActionStateValueIDID1.setEnabled(false);
        this.jTextStateChangeTriggerValue2.setEnabled(false);
        this.jTextWriteStateActionStateValueIDBaseIRI2.setEnabled(false);
        this.jTextWriteStateActionStateValueIDID2.setEnabled(false);

        this.jComboConditionOperator.addItemListener(new ItemListener()
        {

            @Override
            public void itemStateChanged(ItemEvent e)
            {
                StateConditionEnum conditionType = StateConditionEnum.fromValue(jComboConditionOperator.getSelectedIndex());

                switch (conditionType)
                {
                    case NO_CONDITION:
                        AutonomousBehaviourDialog.this.jTextStateChangeTriggerValue1.setEnabled(false);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDBaseIRI1.setEnabled(false);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDID1.setEnabled(false);
                        AutonomousBehaviourDialog.this.jTextStateChangeTriggerValue2.setEnabled(false);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDBaseIRI2.setEnabled(false);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDID2.setEnabled(false);
                        break;
                    case EQUALS:
                    case DIFFERENT_TO:
                    case GREATER_THAN:
                    case LESSER_THAN:
                        AutonomousBehaviourDialog.this.jTextStateChangeTriggerValue1.setEnabled(true);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDBaseIRI1.setEnabled(true);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDID1.setEnabled(true);
                        AutonomousBehaviourDialog.this.jTextStateChangeTriggerValue2.setEnabled(false);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDBaseIRI2.setEnabled(false);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDID2.setEnabled(false);
                        break;
                    case BETWEEN:
                    case SOME_OF:
                        AutonomousBehaviourDialog.this.jTextStateChangeTriggerValue1.setEnabled(true);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDBaseIRI1.setEnabled(true);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDID1.setEnabled(true);
                        AutonomousBehaviourDialog.this.jTextStateChangeTriggerValue2.setEnabled(true);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDBaseIRI2.setEnabled(true);
                        AutonomousBehaviourDialog.this.jTextWriteStateActionStateValueIDID2.setEnabled(true);
                        break;
                }
            }
        });

        this.jTextActionDestination.setText(gatewayAddress);

        jButtonRemoveABRule.setEnabled(false);

        hideTriggerPanels();
        jPanelStateChangeTrigger.setVisible(true);

        hideActionPanels();
        jPanelCommandExecutionAction.setVisible(true);

        this.jTextStateChangeTriggerStateBaseIRI.setText(DomoParsing.instance().getDefaultOntologyNamespace());

        this.jTextCommandActionCommandBaseIRI.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        this.jTextCommandActionFunctionalityBaseIRI.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        this.jTextLinkStateActionStateBaseIRI.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        this.jTextWriteStateActionStateIDBaseIRI.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        this.jTextWriteStateActionStateValueIDBaseIRI.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        this.jTextWriteStateActionStateValueIDBaseIRI1.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        this.jTextWriteStateActionStateValueIDBaseIRI2.setText(DomoParsing.instance().getDefaultOntologyNamespace());
        
    }


    /*
    * If there is a selected row in the rules table, picks its ID
    *and launches an UniDA gateway operation to delete that rule
    */
    private void jButtonRemoveABRuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveABRuleActionPerformed
        if (jTableABRules.getSelectedRowCount() > 0)
        {
            try
            {
                Integer ruleID = (Integer) jTableABRules.getModel().getValueAt(jTableABRules.getSelectedRow(), 4);

                instantiationFacade.getGatewayOperationFacade().rmABRule(new UniDAAddress(gatewayAddress), ruleID.intValue());
            } catch (InternalErrorException ex)
            {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }//GEN-LAST:event_jButtonRemoveABRuleActionPerformed


    /*
    * From the input data introduced by the user, builds an UniDA autonomous behaviour rule
    *and then it performs and UniDA gateway operation to add that rule to the gateway
    */
    private void jButtonAddABRuleActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddABRuleActionPerformed
    {//GEN-HEADEREND:event_jButtonAddABRuleActionPerformed

        try
        {
            UniDAABRuleVO rule = getRuleFromUserInput();

            instantiationFacade.getGatewayOperationFacade().addABRule(new UniDAAddress(gatewayAddress), rule);

        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }//GEN-LAST:event_jButtonAddABRuleActionPerformed

    private UniDAABRuleVO getRuleFromUserInput() throws UniDAIDFormatException, Exception
    {
        // trigger
        RuleTrigger trigger = null;
        if (jRadioCronoTrigger.isSelected())
        {
            trigger = new CronoTrigger(
                    Short.valueOf(jTextCronoTriggerWeekday.getText()),
                    Short.valueOf(jTextCronoTriggerHour.getText()),
                    Short.valueOf(jTextCronoTriggerMin.getText()));
        } else if (jRadioScenarioChangeTrigger.isSelected())
        {
            trigger = new ScenarioChangeTrigger(jTextScenarioChangeTriggerScenarioID.getText());
        } else if (jRadioStateChangeTrigger.isSelected())
        {
            DeviceID deviceID = new DeviceID(
                    new UniDAAddress(gatewayAddress),
                    Short.valueOf(jTextTriggerSourceDeviceNumber.getText()));

            StateCondition condition = null;

            StateConditionEnum conditionType = StateConditionEnum.fromValue(jComboConditionOperator.getSelectedIndex());

            switch (conditionType)
            {
                case NO_CONDITION:
                    condition = new StateConditionNull();
                    break;
                case EQUALS:
                case DIFFERENT_TO:
                case GREATER_THAN:
                case LESSER_THAN:
                    condition = new StateConditionUnary(
                            conditionType,
                            new DeviceStateValue(
                                    this.jTextWriteStateActionStateValueIDBaseIRI1.getText() 
                                            + this.jTextWriteStateActionStateValueIDID1.getText(),
                                    this.jTextStateChangeTriggerValue1.getText()));
                    break;
                case BETWEEN:
                    condition = new StateConditionBinary(
                            conditionType,
                            new DeviceStateValue(
                                    this.jTextWriteStateActionStateValueIDBaseIRI1.getText() 
                                            + this.jTextWriteStateActionStateValueIDID1.getText(),
                                    this.jTextStateChangeTriggerValue1.getText()),
                            new DeviceStateValue(
                                    this.jTextWriteStateActionStateValueIDBaseIRI2.getText() 
                                            + this.jTextWriteStateActionStateValueIDID2.getText(),
                                    this.jTextStateChangeTriggerValue2.getText()));
                    break;
                case SOME_OF:
                    Collection<DeviceStateValue> stateValues = new ArrayList<>();
                    stateValues.add(new DeviceStateValue(
                            this.jTextWriteStateActionStateValueIDBaseIRI1.getText() 
                                            + this.jTextWriteStateActionStateValueIDID1.getText(),
                            this.jTextStateChangeTriggerValue1.getText()));
                    stateValues.add(new DeviceStateValue(
                            this.jTextWriteStateActionStateValueIDBaseIRI2.getText() 
                                            + this.jTextWriteStateActionStateValueIDID2.getText(),
                            this.jTextStateChangeTriggerValue2.getText()));
                    condition = new StateConditionNary(
                            conditionType,
                            stateValues);
                    break;
            }

            trigger = new StateChangeTrigger(
                    deviceID,
                    jTextStateChangeTriggerStateBaseIRI.getText() + jTextStateChangeTriggerStateID.getText(),
                    condition);
        }

        // action
        RuleAction action = null;
        if (jRadioCommandAction.isSelected())
        {
            action = new CommandExecutionAction(
                    this.jTextCommandActionFunctionalityBaseIRI.getText() + this.jTextCommandActionFunctionalityID.getText(),
                    this.jTextCommandActionCommandBaseIRI.getText() + this.jTextCommandActionCommandID.getText(),
                    new String[0]);
        } else if (jRadioLinkStateAction.isSelected())
        {
            action = new LinkStateAction(
                    this.jTextLinkStateActionStateBaseIRI.getText() + this.jTextLinkStateActionStateID.getText());
        } else if (jRadioWriteStateAction.isSelected())
        {
            action = new WriteStateAction(
                    this.jTextWriteStateActionStateIDBaseIRI.getText() + this.jTextWriteStateActionStateIDID.getText(),
                    new DeviceStateValue(
                            this.jTextWriteStateActionStateValueIDBaseIRI.getText() + this.jTextWriteStateActionStateValueIDID.getText(),
                            this.jTextWriteStateActionStateValueRAW.getText()));
        } else if (jRadioChangeScenarioAction.isSelected())
        {
            action = new ChangeScenarioAction(this.jTextChangeScenarioActionScenarioID.getText());
        }
        action.setActionDestination(new DeviceID(new UniDAAddress(
                jTextActionDestination.getText()),
                Short.valueOf(jTextActionDestinationDeviceNumber.getText())));
                
        return new UniDAABRuleVO(trigger, action);
    }
    
    
    private void setStateChangeInputFields(boolean activate)
    {
        this.jTextTriggerSourceDeviceNumber.setEnabled(activate);
        this.jRadioLinkStateAction.setEnabled(activate);
        if (!activate && this.jRadioLinkStateAction.isSelected()) this.jRadioWriteStateAction.setSelected(true);
    }


    private void jRadioCronoTriggerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioCronoTriggerActionPerformed
    {//GEN-HEADEREND:event_jRadioCronoTriggerActionPerformed
        hideTriggerPanels();
        this.jPanelCronoTrigger.setVisible(true);
        setStateChangeInputFields(false);
    }//GEN-LAST:event_jRadioCronoTriggerActionPerformed


    private void jRadioScenarioChangeTriggerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioScenarioChangeTriggerActionPerformed
    {//GEN-HEADEREND:event_jRadioScenarioChangeTriggerActionPerformed
        hideTriggerPanels();
        this.jPanelScenarioChange.setVisible(true);
        setStateChangeInputFields(false);
    }//GEN-LAST:event_jRadioScenarioChangeTriggerActionPerformed


    private void jRadioStateChangeTriggerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioStateChangeTriggerActionPerformed
    {//GEN-HEADEREND:event_jRadioStateChangeTriggerActionPerformed
        hideTriggerPanels();
        this.jPanelStateChangeTrigger.setVisible(true);
        setStateChangeInputFields(true);
    }//GEN-LAST:event_jRadioStateChangeTriggerActionPerformed


    private void jRadioLinkStateActionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioLinkStateActionActionPerformed
    {//GEN-HEADEREND:event_jRadioLinkStateActionActionPerformed
        hideActionPanels();
        this.jPanelLinkStatesAction.setVisible(true);
    }//GEN-LAST:event_jRadioLinkStateActionActionPerformed


    private void jRadioCommandActionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioCommandActionActionPerformed
    {//GEN-HEADEREND:event_jRadioCommandActionActionPerformed
        hideActionPanels();
        this.jPanelCommandExecutionAction.setVisible(true);
    }//GEN-LAST:event_jRadioCommandActionActionPerformed


    private void jRadioWriteStateActionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioWriteStateActionActionPerformed
    {//GEN-HEADEREND:event_jRadioWriteStateActionActionPerformed
        hideActionPanels();
        this.jPanelWriteStateAction.setVisible(true);
    }//GEN-LAST:event_jRadioWriteStateActionActionPerformed

    private void jButtonRefreshABRulesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRefreshABRulesActionPerformed
    {//GEN-HEADEREND:event_jButtonRefreshABRulesActionPerformed
        loadABRules();
    }//GEN-LAST:event_jButtonRefreshABRulesActionPerformed

    private void jRadioChangeScenarioActionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioChangeScenarioActionActionPerformed
    {//GEN-HEADEREND:event_jRadioChangeScenarioActionActionPerformed
        hideActionPanels();
        this.jPanelChangeScenarioAction.setVisible(true);
    }//GEN-LAST:event_jRadioChangeScenarioActionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupActionType;
    private javax.swing.ButtonGroup buttonGroupTriggerType;
    private javax.swing.JButton jButtonAddABRule;
    private javax.swing.JButton jButtonRefreshABRules;
    private javax.swing.JButton jButtonRemoveABRule;
    private javax.swing.JComboBox jComboConditionOperator;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelABRule;
    private javax.swing.JPanel jPanelActionContainer;
    private javax.swing.JPanel jPanelActionType;
    private javax.swing.JPanel jPanelChangeScenarioAction;
    private javax.swing.JPanel jPanelCommandExecutionAction;
    private javax.swing.JPanel jPanelCronoTrigger;
    private javax.swing.JPanel jPanelLinkStatesAction;
    private javax.swing.JPanel jPanelScenarioChange;
    private javax.swing.JPanel jPanelStateChangeTrigger;
    private javax.swing.JPanel jPanelStateCondition;
    private javax.swing.JPanel jPanelTriggerContainer;
    private javax.swing.JPanel jPanelTriggerType;
    private javax.swing.JPanel jPanelWriteStateAction;
    private javax.swing.JRadioButton jRadioChangeScenarioAction;
    private javax.swing.JRadioButton jRadioCommandAction;
    private javax.swing.JRadioButton jRadioCronoTrigger;
    private javax.swing.JRadioButton jRadioLinkStateAction;
    private javax.swing.JRadioButton jRadioScenarioChangeTrigger;
    private javax.swing.JRadioButton jRadioStateChangeTrigger;
    private javax.swing.JRadioButton jRadioWriteStateAction;
    private javax.swing.JScrollPane jScrollPaneABRules;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableABRules;
    private javax.swing.JTextField jTextActionDestination;
    private javax.swing.JTextField jTextActionDestinationDeviceNumber;
    private javax.swing.JTextField jTextChangeScenarioActionScenarioID;
    private javax.swing.JTextField jTextCommandActionCommandBaseIRI;
    private javax.swing.JTextField jTextCommandActionCommandID;
    private javax.swing.JTextField jTextCommandActionFunctionalityBaseIRI;
    private javax.swing.JTextField jTextCommandActionFunctionalityID;
    private javax.swing.JTextField jTextCommandActionValue;
    private javax.swing.JTextField jTextCronoTriggerHour;
    private javax.swing.JTextField jTextCronoTriggerMin;
    private javax.swing.JTextField jTextCronoTriggerWeekday;
    private javax.swing.JTextField jTextLinkStateActionStateBaseIRI;
    private javax.swing.JTextField jTextLinkStateActionStateID;
    private javax.swing.JTextField jTextScenarioChangeTriggerScenarioID;
    private javax.swing.JTextField jTextStateChangeTriggerStateBaseIRI;
    private javax.swing.JTextField jTextStateChangeTriggerStateID;
    private javax.swing.JTextField jTextStateChangeTriggerValue1;
    private javax.swing.JTextField jTextStateChangeTriggerValue2;
    private javax.swing.JTextField jTextTriggerSourceDeviceNumber;
    private javax.swing.JTextField jTextWriteStateActionStateIDBaseIRI;
    private javax.swing.JTextField jTextWriteStateActionStateIDID;
    private javax.swing.JTextField jTextWriteStateActionStateValueIDBaseIRI;
    private javax.swing.JTextField jTextWriteStateActionStateValueIDBaseIRI1;
    private javax.swing.JTextField jTextWriteStateActionStateValueIDBaseIRI2;
    private javax.swing.JTextField jTextWriteStateActionStateValueIDID;
    private javax.swing.JTextField jTextWriteStateActionStateValueIDID1;
    private javax.swing.JTextField jTextWriteStateActionStateValueIDID2;
    private javax.swing.JTextField jTextWriteStateActionStateValueRAW;
    // End of variables declaration//GEN-END:variables

    
    /*
    * Callback that is invoked when an answer for an UniDA autonomous behaviour rules query
    *is received
    */
    private class ABCallback implements IAutonomousBehaviourCallback
    {

        @Override
        public void notifyGatewayAutonomousBehaviourRules(OperationTicket ticket, Gateway gateway, List<UniDAABRuleVO> rules)
        {

            UNIDALibraryBasicGUI.cleanJTable(jTableABRules);
            int ruleRow = 0;

            for (UniDAABRuleVO rule : rules)
            {
                jTableABRules.getModel().setValueAt(rule.getTrigger().getType().toString(), ruleRow, 0);
                jTableABRules.getModel().setValueAt(rule.getTrigger().toString(), ruleRow, 1);
                jTableABRules.getModel().setValueAt(rule.getAction().getType().toString(), ruleRow, 2);
                jTableABRules.getModel().setValueAt(rule.getAction().toString(), ruleRow, 3);
                jTableABRules.getModel().setValueAt(rule.getRuleId(), ruleRow, 4);
                ruleRow++;
            }
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
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyFailure(OperationTicket ticket, OperationFailures failure)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    
    /*
    * A different cellRenderer is needed to be able to handle a different 
    *tooltip text for each cell
    */
    private class CustomCellRenderer extends JLabel implements TableCellRenderer
    {

        // This method is called each time a cell in a column
        // using this renderer needs to be rendered.
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex)
        {
            // 'value' is value contained in the cell located at
            // (rowIndex, vColIndex)

            if (isSelected)
            {
                // cell (and perhaps other cells) are selected
            }

            if (hasFocus)
            {
                // this cell is the anchor and the table has the focus
            }

            if (null != value)
            {
                // Configure the component with the specified value
                setText(value.toString());
                // Set tool tip if desired          
                setToolTipText(value.toString());
            }

            this.setFont(new java.awt.Font("Lucida Grande", 0, 11));

            // Since the renderer is a component, return itself
            return this;
        }

        // The following methods override the defaults for performance reasons
        @Override
        public void validate()
        {
        }

        @Override
        public void revalidate()
        {
        }

        @Override
        protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
        {
        }

        @Override
        public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue)
        {
        }
    }

}

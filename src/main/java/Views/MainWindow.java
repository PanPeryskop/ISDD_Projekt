/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author stgad
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form Backup
     */
    public MainWindow() {
        initComponents();
        cmbFilterDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" 
        }));
        txtFilterPrice.setText("");
    }
    
    public void addClientMenuListener(ActionListener listener) {
        ClientMenu.setActionCommand("ShowClients");
        ClientMenu.addActionListener(listener);
    }

    public void addTrainerMenuListener(ActionListener listener) {
        TrainerMenu.setActionCommand("ShowTrainers");
        TrainerMenu.addActionListener(listener);
    }

    public void addActivitiesMenuListener(ActionListener listener) {
        ActivitiesMenu.setActionCommand("ShowActivities");
        ActivitiesMenu.addActionListener(listener);
    }

    public void addInitMenuListener(ActionListener listener) {
        jMenuItem5.setActionCommand("ShowInit");
        jMenuItem5.addActionListener(listener);
    }

    public void addNewButtonListener(ActionListener listener) {
        NewButton.setActionCommand("New");
        NewButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.setActionCommand("Delete");
        deleteButton.addActionListener(listener);
    }

    public void addUpdateButtonListener(ActionListener listener) {
        UpdateButton.setActionCommand("Update");
        UpdateButton.addActionListener(listener);
    }
    
    public void addApplyFilterListener(ActionListener listener) {
        btnApplyFilter.setActionCommand("ApplyFilter");
        btnApplyFilter.addActionListener(listener);
    }
    
    public void addJoinButtonListener(ActionListener listener) {
        joinButton.setActionCommand("JoinSquad");
        joinButton.addActionListener(listener);
    }

    public void setViewName(String name) {
        ViewName.setText(name);
    }

    public String getViewName() {
        return ViewName.getText();
    }

    public void setTableData(String[] columns, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DataTable.setModel(model);
    }
    
    public int getSelectedRow() {
        return DataTable.getSelectedRow();
    }

    public Object getValueAt(int row, int column) {
        if (row >= 0 && row < DataTable.getRowCount() && column >= 0 && column < DataTable.getColumnCount()) {
            return DataTable.getValueAt(row, column);
        }
        return null;
    }
    
    public javax.swing.JComboBox<String> getCmbEnrollClient() {
        return cmbEnrollClient;
    }
    
    public javax.swing.JComboBox<String> getCmbEnrollActivity() {
        return cmbEnrollActivity;
    }
    
    public javax.swing.JComboBox<String> getCmbFilterDay() {
        return cmbFilterDay;
    }
    
    public String getFilterPrice() {
        return txtFilterPrice.getText().trim();
    }

    public javax.swing.JTable getDataTable() {
        return DataTable;
    }

    public void addStatsListener(java.awt.event.ActionListener listener) {
    showStats.setActionCommand("ShowStats");
    showStats.addActionListener(listener);
    }
    
    public void setViewMode(String mode) {
        switch (mode) {
            case "Init" -> {
                fLabel.setVisible(false);
                cmbFilterDay.setVisible(false);
                txtFilterPrice.setVisible(false);
                btnApplyFilter.setVisible(false);

                squadLabel.setVisible(false);
                cLabel.setVisible(false);
                aLabel.setVisible(false);
                cmbEnrollClient.setVisible(false);
                cmbEnrollActivity.setVisible(false);
                joinButton.setVisible(false);

                NewButton.setVisible(false);
                deleteButton.setVisible(false);
                UpdateButton.setVisible(false);
                showStats.setVisible(false);
            }
            case "Clients", "Trainers" -> {
                fLabel.setVisible(false);
                cmbFilterDay.setVisible(false);
                txtFilterPrice.setVisible(false);
                btnApplyFilter.setVisible(false);

                squadLabel.setVisible(false);
                cLabel.setVisible(false);
                aLabel.setVisible(false);
                cmbEnrollClient.setVisible(false);
                cmbEnrollActivity.setVisible(false);
                joinButton.setVisible(false);

                NewButton.setVisible(true);
                deleteButton.setVisible(true);
                UpdateButton.setVisible(true);
                showStats.setVisible(false);
            }
            case "Activities" -> {
                fLabel.setVisible(true);
                cmbFilterDay.setVisible(true);
                txtFilterPrice.setVisible(true);
                btnApplyFilter.setVisible(true);

                squadLabel.setVisible(true);
                cLabel.setVisible(true);
                aLabel.setVisible(true);
                cmbEnrollClient.setVisible(true);
                cmbEnrollActivity.setVisible(true);
                joinButton.setVisible(true);

                NewButton.setVisible(true);
                deleteButton.setVisible(true);
                UpdateButton.setVisible(true);
                showStats.setVisible(true);
            }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        ViewName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DataTable = new javax.swing.JTable();
        BottomMenuPanel = new javax.swing.JPanel();
        NewButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        UpdateButton = new javax.swing.JButton();
        showStats = new javax.swing.JButton();
        cmbEnrollClient = new javax.swing.JComboBox<>();
        cmbEnrollActivity = new javax.swing.JComboBox<>();
        squadLabel = new javax.swing.JLabel();
        aLabel = new javax.swing.JLabel();
        cLabel = new javax.swing.JLabel();
        joinButton = new javax.swing.JButton();
        fLabel = new javax.swing.JLabel();
        cmbFilterDay = new javax.swing.JComboBox<>();
        txtFilterPrice = new javax.swing.JTextField();
        btnApplyFilter = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        InitMenu = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        ClientView = new javax.swing.JMenu();
        ClientMenu = new javax.swing.JMenuItem();
        TrainerView = new javax.swing.JMenu();
        TrainerMenu = new javax.swing.JMenuItem();
        ActivitieView = new javax.swing.JMenu();
        ActivitiesMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ViewName.setText("PlaceholderShit");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(ViewName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(ViewName)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        DataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(DataTable);

        NewButton.setText("New One");
        NewButton.setActionCommand("New");
        NewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete One");
        deleteButton.setActionCommand("DeleteOne");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        UpdateButton.setText("Update One");
        UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButtonActionPerformed(evt);
            }
        });

        showStats.setText("Show Stats");

        javax.swing.GroupLayout BottomMenuPanelLayout = new javax.swing.GroupLayout(BottomMenuPanel);
        BottomMenuPanel.setLayout(BottomMenuPanelLayout);
        BottomMenuPanelLayout.setHorizontalGroup(
            BottomMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomMenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NewButton)
                .addGap(18, 18, 18)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(UpdateButton)
                .addGap(56, 56, 56)
                .addComponent(showStats)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BottomMenuPanelLayout.setVerticalGroup(
            BottomMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomMenuPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(BottomMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewButton)
                    .addComponent(deleteButton)
                    .addComponent(UpdateButton)
                    .addComponent(showStats))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        cmbEnrollClient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEnrollClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEnrollClientActionPerformed(evt);
            }
        });

        cmbEnrollActivity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        squadLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        squadLabel.setText("JOIN THE SQUAD");

        aLabel.setText("Activity:");

        cLabel.setText("Candidate:");

        joinButton.setText("Confirm");

        fLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fLabel.setText("Filter");

        cmbFilterDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtFilterPrice.setText("jTextField1");

        btnApplyFilter.setText("Apply");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
            .addComponent(BottomMenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fLabel)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbFilterDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtFilterPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnApplyFilter))
                            .addComponent(squadLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbEnrollClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(aLabel)
                        .addGap(8, 8, 8)
                        .addComponent(cmbEnrollActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(joinButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BottomMenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFilterDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFilterPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnApplyFilter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(squadLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEnrollClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEnrollActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cLabel)
                    .addComponent(aLabel)
                    .addComponent(joinButton))
                .addGap(33, 33, 33))
        );

        InitMenu.setText("Init");
        InitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InitMenuActionPerformed(evt);
            }
        });

        jMenuItem5.setText("Init");
        InitMenu.add(jMenuItem5);

        jMenuBar1.add(InitMenu);

        ClientView.setText("Clients");

        ClientMenu.setText("ClientManager");
        ClientMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientMenuActionPerformed(evt);
            }
        });
        ClientView.add(ClientMenu);

        jMenuBar1.add(ClientView);

        TrainerView.setText("Trainers");

        TrainerMenu.setText("TrainerManager");
        TrainerMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrainerMenuActionPerformed(evt);
            }
        });
        TrainerView.add(TrainerMenu);

        jMenuBar1.add(TrainerView);

        ActivitieView.setText("Activities");

        ActivitiesMenu.setText("ActivitiesManager");
        ActivitiesMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivitiesMenuActionPerformed(evt);
            }
        });
        ActivitieView.add(ActivitiesMenu);

        jMenuBar1.add(ActivitieView);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void InitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InitMenuActionPerformed
        //         // TODO add your handling code here:
    }//GEN-LAST:event_InitMenuActionPerformed

    private void ClientMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientMenuActionPerformed
        //         // TODO add your handling code here:
    }//GEN-LAST:event_ClientMenuActionPerformed

    private void TrainerMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrainerMenuActionPerformed
        //         // TODO add your handling code here:
    }//GEN-LAST:event_TrainerMenuActionPerformed

    private void ActivitiesMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivitiesMenuActionPerformed
        //         // TODO add your handling code here:
    }//GEN-LAST:event_ActivitiesMenuActionPerformed

    private void NewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void UpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UpdateButtonActionPerformed

    private void cmbEnrollClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEnrollClientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEnrollClientActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenu ActivitieView;
    public javax.swing.JMenuItem ActivitiesMenu;
    public javax.swing.JPanel BottomMenuPanel;
    private javax.swing.JMenuItem ClientMenu;
    public javax.swing.JMenu ClientView;
    public javax.swing.JTable DataTable;
    public javax.swing.JMenu InitMenu;
    public javax.swing.JButton NewButton;
    public javax.swing.JMenuItem TrainerMenu;
    public javax.swing.JMenu TrainerView;
    public javax.swing.JButton UpdateButton;
    public javax.swing.JLabel ViewName;
    private javax.swing.JLabel aLabel;
    public javax.swing.JButton btnApplyFilter;
    public javax.swing.JLabel cLabel;
    public javax.swing.JComboBox<String> cmbEnrollActivity;
    public javax.swing.JComboBox<String> cmbEnrollClient;
    public javax.swing.JComboBox<String> cmbFilterDay;
    public javax.swing.JButton deleteButton;
    public javax.swing.JLabel fLabel;
    public javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JButton joinButton;
    public javax.swing.JButton showStats;
    public javax.swing.JLabel squadLabel;
    public javax.swing.JTextField txtFilterPrice;
    // End of variables declaration//GEN-END:variables
}

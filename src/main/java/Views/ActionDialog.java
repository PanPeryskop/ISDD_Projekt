/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Views;
import java.awt.event.ActionListener;

/**
 *
 * @author stgad
 */
public class ActionDialog extends javax.swing.JDialog {
    public enum Mode { ADD, EDIT, DELETE }
    private Mode currentMode;
    private boolean confirmed = false;
    /**
     * Creates new form ActionDialog
     */
    public ActionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
        switch (mode) {
            case ADD -> {
                dialoLabel.setText("Add New Record");
                IdForm.setEditable(false);
                setFieldsEditable(true);
                clearFields();
            }
            case EDIT -> {
                dialoLabel.setText("Edit Record");
                IdForm.setEditable(false);
                setFieldsEditable(true);
            }
            case DELETE -> {
                dialoLabel.setText("Delete Record - Confirm?");
                IdForm.setEditable(false);
                setFieldsEditable(false);
                
                cmbDay.setEnabled(false);
                cmbHour.setEnabled(false);
                cmbGeneric.setEnabled(false);
            }
        }
    }

    public Mode getMode() {
        return currentMode;
    }

    private void setFieldsEditable(boolean editable) {
        NameForm.setEditable(editable);
        idNrForm.setEditable(editable);
        phoneForm.setEditable(editable);
        emailForm.setEditable(editable);
        creationForm.setEditable(editable);
        jTextField7.setEditable(editable);
        
        cmbDay.setEnabled(editable);
        cmbHour.setEnabled(editable);
        cmbGeneric.setEnabled(editable);
    }

    public void clearFields() {
        IdForm.setText("");
        NameForm.setText("");
        idNrForm.setText("");
        phoneForm.setText("");
        emailForm.setText("");
        creationForm.setText("");
        jTextField7.setText("");
        
        if (cmbDay.getItemCount() > 0) cmbDay.setSelectedIndex(0);
        if (cmbHour.getItemCount() > 0) cmbHour.setSelectedIndex(0);
        if (cmbGeneric.getItemCount() > 0) cmbGeneric.setSelectedIndex(0);
    }
    
    public void configureForClient() {
        dialoLabel.setText("Client Record");
        idLabel.setText("Client ID");
        IdLabel.setText("DNI");
        nameLabel.setText("Name");
        phoneLabel.setText("Phone");
        emailLabel.setText("Email");
        creationLabel.setText("Start Date");
        loginLabel.setText("Category");

        phoneForm.setVisible(true);
        cmbDay.setVisible(false);
        
        emailForm.setVisible(true);
        cmbHour.setVisible(false);

        jTextField7.setVisible(false);
        cmbGeneric.setVisible(true);
        cmbGeneric.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E" }));
    }

    public void configureForTrainer() {
        dialoLabel.setText("Trainer Record");
        idLabel.setText("Trainer COD");
        IdLabel.setText("ID Number");
        nameLabel.setText("Name");
        phoneLabel.setText("Phone");
        emailLabel.setText("Email");
        creationLabel.setText("Date");
        loginLabel.setText("Nick");

        phoneForm.setVisible(true);
        cmbDay.setVisible(false);
        
        emailForm.setVisible(true);
        cmbHour.setVisible(false);
        
        jTextField7.setVisible(true);
        cmbGeneric.setVisible(false);
    }
    
    public void configureForActivity() {
        dialoLabel.setText("Activity Record");
        idLabel.setText("Activity ID");
        IdLabel.setText("Price");
        nameLabel.setText("Name");
        
        phoneLabel.setText("Day");
        phoneForm.setVisible(false);
        cmbDay.setVisible(true);
        cmbDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" 
        }));

        emailLabel.setText("Hour");
        emailForm.setVisible(false);
        cmbHour.setVisible(true);
        cmbHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" 
        }));
        
        creationLabel.setText("Description");
        loginLabel.setText("Trainer");
        
        jTextField7.setVisible(false);
        cmbGeneric.setVisible(true);
    }
    
    public String getIdValue() { return IdForm.getText().trim(); }
    public String getNameValue() { return NameForm.getText().trim(); }
    public String getIdNrValue() { return idNrForm.getText().trim(); }
    
    public String getPhoneValue() { 
        if (cmbDay.isVisible()) return (String) cmbDay.getSelectedItem();
        return phoneForm.getText().trim(); 
    }
    
    public String getEmailValue() { 
        if (cmbHour.isVisible()) return (String) cmbHour.getSelectedItem();
        return emailForm.getText().trim(); 
    }
    
    public String getCreationValue() { return creationForm.getText().trim(); }
    
    public String getExtraValue() { 
        if (cmbGeneric.isVisible()) {
            Object item = cmbGeneric.getSelectedItem();
            return item != null ? item.toString() : "";
        }
        return jTextField7.getText().trim(); 
    }

    public void setIdValue(String value) { IdForm.setText(value); }
    public void setNameValue(String value) { NameForm.setText(value); }
    public void setIdNrValue(String value) { idNrForm.setText(value); }
    
    public void setPhoneValue(String value) { 
        if (cmbDay.isVisible()) cmbDay.setSelectedItem(value);
        else phoneForm.setText(value); 
    }
    
    public void setEmailValue(String value) { 
        if (cmbHour.isVisible()) cmbHour.setSelectedItem(value);
        else emailForm.setText(value); 
    }
    
    public void setCreationValue(String value) { creationForm.setText(value); }
    
    public void setExtraValue(String value) { 
        if (cmbGeneric.isVisible()) {
            javax.swing.DefaultComboBoxModel<String> model = (javax.swing.DefaultComboBoxModel<String>) cmbGeneric.getModel();
            for(int i=0; i<model.getSize(); i++) {
                String item = model.getElementAt(i);
                if(item.contains(value)) { 
                    cmbGeneric.setSelectedIndex(i);
                    break;
                }
            }
        }
        else jTextField7.setText(value); 
    }
    
    public void setGenericComboModel(String[] items) {
        cmbGeneric.setModel(new javax.swing.DefaultComboBoxModel<>(items));
    }

    public void addConfirmListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancellButton.addActionListener(listener);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        IdLabel = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        phoneLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        creationLabel = new javax.swing.JLabel();
        loginLabel = new javax.swing.JLabel();
        IdForm = new javax.swing.JTextField();
        NameForm = new javax.swing.JTextField();
        idNrForm = new javax.swing.JTextField();
        phoneForm = new javax.swing.JTextField();
        emailForm = new javax.swing.JTextField();
        creationForm = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();
        cancellButton = new javax.swing.JButton();
        dialoLabel = new javax.swing.JLabel();
        cmbDay = new javax.swing.JComboBox<>();
        cmbHour = new javax.swing.JComboBox<>();
        cmbGeneric = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nameLabel.setText("Name");

        IdLabel.setText("ID NR");

        idLabel.setText("Client ID");

        phoneLabel.setText("Phone");
        phoneLabel.setToolTipText("");

        emailLabel.setText("Email");

        creationLabel.setText("Creation date");

        loginLabel.setText("Login");

        IdForm.setEditable(false);

        phoneForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneFormActionPerformed(evt);
            }
        });

        emailForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFormActionPerformed(evt);
            }
        });

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        cancellButton.setText("Cancel");

        dialoLabel.setText("Dialog Label");

        cmbDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbGeneric.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nameLabel)
                            .addComponent(idLabel)
                            .addComponent(IdLabel)
                            .addComponent(phoneLabel))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(IdForm, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(emailLabel))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(idNrForm, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(loginLabel)))
                                        .addGap(44, 44, 44))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(NameForm, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(creationLabel)))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(emailForm, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(creationForm, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                            .addComponent(jTextField7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbGeneric, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dialoLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(phoneForm, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirmButton)))
                .addGap(18, 18, 18)
                .addComponent(cancellButton)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(dialoLabel)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idLabel)
                    .addComponent(emailLabel)
                    .addComponent(IdForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NameForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel)
                    .addComponent(creationLabel)
                    .addComponent(creationForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdLabel)
                    .addComponent(loginLabel)
                    .addComponent(idNrForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbGeneric, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneLabel)
                    .addComponent(phoneForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmButton)
                    .addComponent(cancellButton))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void emailFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailFormActionPerformed

    private void phoneFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneFormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneFormActionPerformed

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
            java.util.logging.Logger.getLogger(ActionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ActionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ActionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ActionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ActionDialog dialog = new ActionDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField IdForm;
    public javax.swing.JLabel IdLabel;
    public javax.swing.JTextField NameForm;
    public javax.swing.JButton cancellButton;
    public javax.swing.JComboBox<String> cmbDay;
    public javax.swing.JComboBox<String> cmbGeneric;
    public javax.swing.JComboBox<String> cmbHour;
    public javax.swing.JButton confirmButton;
    public javax.swing.JTextField creationForm;
    public javax.swing.JLabel creationLabel;
    public javax.swing.JLabel dialoLabel;
    public javax.swing.JTextField emailForm;
    public javax.swing.JLabel emailLabel;
    public javax.swing.JLabel idLabel;
    public javax.swing.JTextField idNrForm;
    private javax.swing.JTextField jTextField7;
    public javax.swing.JLabel loginLabel;
    public javax.swing.JLabel nameLabel;
    public javax.swing.JTextField phoneForm;
    public javax.swing.JLabel phoneLabel;
    // End of variables declaration//GEN-END:variables
}

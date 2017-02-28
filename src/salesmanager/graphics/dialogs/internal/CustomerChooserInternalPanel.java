/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.dialogs.internal;

import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import salesmanager.beans.Customer;
import salesmanager.beans.dao.DBCustomersManager;

/**
 *
 * @author Lorenzo
 */
public class CustomerChooserInternalPanel extends BeanPanel {

    private Customer customer;
    
    /**
     * Creates new form CustomerChooserInternalPanel
     */
    public CustomerChooserInternalPanel() throws SQLException {
        initComponents();
        lstCustomers.setModel(new DefaultComboBoxModel(DBCustomersManager.getCustomers()));
        this.customer = null;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstCustomers = new javax.swing.JList();

        setLayout(new java.awt.GridBagLayout());

        lstCustomers.setBorder(javax.swing.BorderFactory.createTitledBorder("Clienti"));
        lstCustomers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lstCustomers);

        add(jScrollPane1, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstCustomers;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean updateData() {
        if(lstCustomers.getSelectedValue() != null){
            customer = (Customer) lstCustomers.getSelectedValue();
            return true;
        }
        return false;
    }

    public Customer getCustomer() {
        return customer;
    }
}

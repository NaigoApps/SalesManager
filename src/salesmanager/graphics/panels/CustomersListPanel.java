/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import salesmanager.beans.Customer;
import salesmanager.beans.Product;
import salesmanager.beans.Register;
import salesmanager.beans.dao.DBCustomersManager;
import salesmanager.beans.dao.DBProductsManager;
import salesmanager.graphics.Main;
import salesmanager.graphics.dialogs.CustomerDialog;
import salesmanager.graphics.dialogs.RegisterChooserDialog;
import salesmanager.graphics.tables.CustomersModel;
import salesmanager.printable.CustomerSummaryForm;

/**
 *
 * @author Lorenzo
 */
public class CustomersListPanel extends javax.swing.JPanel implements Loadable, ActionListener {

    /**
     * Creates new form CustomersListPanel
     */
    private Main parent;
    private CustomersModel model;

    private JButton btnSearch;
    private JTextField txtSearch;

    public CustomersListPanel(Main parent) {
        initComponents();
        initSearchBar();
        this.parent = parent;
        model = new CustomersModel();
        tableCustomers.setModel(model);
    }

    private void initSearchBar() {
        JPanel pnNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtSearch = new JTextField();
        txtSearch.setColumns(15);
        btnSearch = new JButton("Cerca");
        btnSearch.addActionListener(this);
        pnNorth.add(txtSearch);
        pnNorth.add(btnSearch);
        this.add(pnNorth, BorderLayout.NORTH);
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
        tableCustomers = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnProducts = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        tableCustomers.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{}
        ));
        jScrollPane1.setViewportView(tableCustomers);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout(5, 0));

        btnAdd.setText("Nuovo");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd);

        btnEdit.setText("Modifica");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit);

        btnDelete.setText("Elimina");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete);

        btnProducts.setText("Prodotti");
        btnProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductsActionPerformed(evt);
            }
        });
        jPanel1.add(btnProducts);

        btnPrint.setText("Stampa riepilogo");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrint);

        add(jPanel1, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        CustomerDialog d = new CustomerDialog(parent);
        d.setVisible(true);
        Customer c = d.getCustomer();
        try {
            DBCustomersManager.addCustomer(c);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore durante la creazione", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int selected = tableCustomers.getSelectedRow();
        if (selected != -1) {
            selected = tableCustomers.convertRowIndexToModel(selected);
            CustomerDialog d = new CustomerDialog(parent);
            d.setCustomer(model.getCustomer(selected));
            d.setVisible(true);
            Customer c = d.getCustomer();
            try {
                DBCustomersManager.editCustomer(c);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore durante la creazione", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selected = tableCustomers.getSelectedRow();
        if (selected != -1) {
            selected = tableCustomers.convertRowIndexToModel(selected);
            int choice = JOptionPane.showConfirmDialog(this, "Sicuro di voler eliminare " + model.getCustomer(selected) + "?", "Attenzione", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    DBCustomersManager.removeCustomer(model.getCustomer(selected));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex, "Errore durante l'eliminazione", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductsActionPerformed
        int selected = tableCustomers.getSelectedRow();
        if (selected != -1) {
            try {
                selected = tableCustomers.convertRowIndexToModel(selected);
                parent.getProductsListPanel().setCustomer(model.getCustomer(selected));
                parent.showPanel(Main.LIST);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex, "Impossibile caricare la lista dei prodotti", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnProductsActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductsActionPerformed
        PrinterJob pj = PrinterJob.getPrinterJob();
        int selected = tableCustomers.getSelectedRow();
        if (selected != -1) {
            try {
                selected = tableCustomers.convertRowIndexToModel(selected);
                Customer selectedCustomer = model.getCustomer(selected);
                Product[] onSale = DBProductsManager.getFromClientProductsOnSale(selectedCustomer.getCode());
                Product[] sold = DBProductsManager.getFromClientProductsSold(selectedCustomer.getCode());
                Product[] back = DBProductsManager.getFromClientProductsBack(selectedCustomer.getCode());
                CustomerSummaryForm form = new CustomerSummaryForm(selectedCustomer, onSale, sold, back);
                form.setHMargin(10);
                form.setVMargin(10);
                pj.setPrintable(form);

                if (pj.printDialog()) {
                    try {
                        PrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
                        attrSet.add(MediaSizeName.ISO_A4);
                        attrSet.add(OrientationRequested.PORTRAIT);
                        pj.print(attrSet);
                    } catch (PrinterException ex) {
                        JOptionPane.showMessageDialog(this, "Impossibile stampare");
                    }
                }
            } catch (SQLException ex) {
            }
        }

    }//GEN-LAST:event_btnPrintActionPerformed

// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnProducts;
    private javax.swing.JButton btnPrint;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableCustomers;
    // End of variables declaration//GEN-END:variables

    @Override
    public void load() throws SQLException {
        model.unload();
        model.reload();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            String surname = txtSearch.getText();
            try {
                model.setLike(surname);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex, "Impossibile ricercare il cliente", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.panels;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import salesmanager.beans.Invoice;
import salesmanager.beans.dao.DBInvoicesManager;
import salesmanager.beans.dao.DBProductsManager;
import salesmanager.graphics.Main;
import salesmanager.graphics.dialogs.InvoiceDetailsDialog;
import salesmanager.graphics.tables.InvoicesTableModel;
import salesmanager.printable.ProductsInvoiceForm;

/**
 *
 * @author Lorenzo
 */
public class InvoicesListPanel extends javax.swing.JPanel implements Loadable {

    /**
     * Creates new form InvoicesListPanel
     */
    InvoicesTableModel model;

    public InvoicesListPanel() {
        initComponents();
        txtDa.setValue(Main.lastMonth());
        txtA.setValue(Main.today());
        model = new InvoicesTableModel(Main.lastMonth(), Main.today());
        tableInvoices.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnCenter = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableInvoices = new javax.swing.JTable();
        pnNorth = new javax.swing.JPanel();
        txtDa = new javax.swing.JFormattedTextField();
        txtA = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnDates = new javax.swing.JButton();
        pnEast = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnInvoiceDetails = new javax.swing.JButton();
        printOnlyInvoiceBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        pnCenter.setLayout(new java.awt.BorderLayout());

        tableInvoices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableInvoices);

        pnCenter.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(pnCenter, java.awt.BorderLayout.CENTER);

        pnNorth.setLayout(new java.awt.GridBagLayout());

        txtDa.setColumns(8);
        txtDa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnNorth.add(txtDa, gridBagConstraints);

        txtA.setColumns(8);
        txtA.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnNorth.add(txtA, gridBagConstraints);

        jLabel1.setText("Da:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnNorth.add(jLabel1, gridBagConstraints);

        jLabel2.setText("A:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnNorth.add(jLabel2, gridBagConstraints);

        btnDates.setText("Ok");
        btnDates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnNorth.add(btnDates, gridBagConstraints);

        add(pnNorth, java.awt.BorderLayout.NORTH);

        pnEast.setLayout(new java.awt.GridBagLayout());

        btnClose.setText("Chiudi fattura");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnEast.add(btnClose, gridBagConstraints);

        btnPrint.setText("Stampa fattura e ricevuta cliente");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnEast.add(btnPrint, gridBagConstraints);

        btnDelete.setText("Elimina fattura");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnEast.add(btnDelete, gridBagConstraints);

        btnInvoiceDetails.setText("Dettagli fattura");
        btnInvoiceDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoiceDetailsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnEast.add(btnInvoiceDetails, gridBagConstraints);

        printOnlyInvoiceBtn.setText("Stampa solo fattura");
        printOnlyInvoiceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printOnlyInvoiceBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnEast.add(printOnlyInvoiceBtn, gridBagConstraints);

        add(pnEast, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        int selected = tableInvoices.getSelectedRow();
        if (selected != -1) {
            selected = tableInvoices.convertRowIndexToModel(selected);
            Invoice invoice = model.getInvoice(selected);
            if (invoice.getProgressive() == -1) {
                try {
                    if (DBProductsManager.countInvoiceProducts(invoice.getCode()) > 0) {
                        if (JOptionPane.showConfirmDialog(this, "Non si potranno aggiungere altri prodotti", "Chiudere la fattura?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            try {
                                invoice.setInvoiceDate(Main.today());
                                DBInvoicesManager.setProgressive(invoice);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(this, "Impossibile chiudere la fattura", "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                            try {
                                model.loadInvoices((Date) txtDa.getValue(), (Date) txtA.getValue());
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(this, "Impossibile caricare le fatture", "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {

                        JOptionPane.showMessageDialog(this, "La fattura è vuota", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(this, "ERRORE", "Attenzione", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "La fattura è già stata chiusa", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnDatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatesActionPerformed
        try {
            load();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Impossibile caricare le fattura", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDatesActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            int selected = tableInvoices.getSelectedRow();
            if (selected != -1) {
                selected = tableInvoices.convertRowIndexToModel(selected);
                Invoice inv = model.getInvoice(tableInvoices.convertRowIndexToModel(selected));
                if (DBInvoicesManager.canRemove(inv)) {
                    int choice = JOptionPane.showConfirmDialog(this, "Sicuro di voler eliminare la fattura?", "Attenzione", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        DBInvoicesManager.removeInvoice(inv);
                        load();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Il documento non può essere eliminato", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione " + ex.getMessage());
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        int[] selected = tableInvoices.getSelectedRows();
        if (selected.length > 0) {
            PrinterJob pj = PrinterJob.getPrinterJob();
            if (pj.printDialog()) {
                for (int i = 0; i < selected.length; i++) {
                    Invoice invoice = model.getInvoice(tableInvoices.convertRowIndexToModel(selected[i]));
                    if (invoice.getProgressive() != -1) {
                        pj.setPrintable(new ProductsInvoiceForm(invoice, false));
                        try {
                            pj.print();
                        } catch (PrinterException ex) {
                            JOptionPane.showMessageDialog(this, "Impossibile stampare");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "La fattura dev'essere chiusa", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnInvoiceDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoiceDetailsActionPerformed
        int selected = tableInvoices.getSelectedRow();
        if (selected != -1) {
            Invoice invoice = model.getInvoice(tableInvoices.convertRowIndexToModel(selected));
            InvoiceDetailsDialog idd = new InvoiceDetailsDialog(null, invoice);
            idd.setVisible(true);
        }
    }//GEN-LAST:event_btnInvoiceDetailsActionPerformed

    private void printOnlyInvoiceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printOnlyInvoiceBtnActionPerformed
        int[] selected = tableInvoices.getSelectedRows();
        if (selected.length > 0) {
            PrinterJob pj = PrinterJob.getPrinterJob();
            if (pj.printDialog()) {
                for (int i = 0; i < selected.length; i++) {
                    Invoice invoice = model.getInvoice(tableInvoices.convertRowIndexToModel(selected[i]));
                    if (invoice.getProgressive() != -1) {
                        pj.setPrintable(new ProductsInvoiceForm(invoice, true));
                        try {
                            pj.print();
                        } catch (PrinterException ex) {
                            JOptionPane.showMessageDialog(this, "Impossibile stampare");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "La fattura dev'essere chiusa", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_printOnlyInvoiceBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDates;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInvoiceDetails;
    private javax.swing.JButton btnPrint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnCenter;
    private javax.swing.JPanel pnEast;
    private javax.swing.JPanel pnNorth;
    private javax.swing.JButton printOnlyInvoiceBtn;
    private javax.swing.JTable tableInvoices;
    private javax.swing.JFormattedTextField txtA;
    private javax.swing.JFormattedTextField txtDa;
    // End of variables declaration//GEN-END:variables

    @Override
    public void load() throws SQLException {
        model.loadInvoices((Date) txtDa.getValue(), (Date) txtA.getValue());
    }
}

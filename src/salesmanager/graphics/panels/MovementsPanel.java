/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.panels;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import salesmanager.beans.DeliveryDocument;
import salesmanager.beans.Movement;
import salesmanager.beans.Product;
import salesmanager.beans.Register;
import salesmanager.beans.RegisterMovement;
import salesmanager.beans.dao.DBDeliveryManager;
import salesmanager.beans.dao.DBMovementsManager;
import salesmanager.beans.dao.DBProductsManager;
import salesmanager.beans.dao.DBRegisterManager;
import salesmanager.graphics.LoadingFrame;
import salesmanager.graphics.Main;
import salesmanager.graphics.dialogs.RegisterChooserDialog;
import salesmanager.graphics.tables.MovementsTableModel;
import salesmanager.printable.MovementsForm;

/**
 *
 * @author Lorenzo
 */
public class MovementsPanel extends javax.swing.JPanel implements Loadable {

    /**
     * Creates new form MovementsPanel
     */
    private Main parent;
    private MovementsTableModel model;

    private Product product;

    public MovementsPanel(Main parent) {
        initComponents();
        this.parent = parent;
        txtDa.setValue(Main.lastMonth());
        txtA.setValue(Main.today());
        product = null;
        model = new MovementsTableModel((Date) txtDa.getValue(), (Date) txtA.getValue());
        tableMovements.setModel(model);
    }

    @Override
    public void load() throws SQLException {
        if (product != null) {
            model.loadProductMovements(product);
        } else {
            model.loadMovements((Date) txtDa.getValue(), (Date) txtA.getValue());
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
        java.awt.GridBagConstraints gridBagConstraints;

        pnEast = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnRegister = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnTestPrint = new javax.swing.JButton();
        pnNorth = new javax.swing.JPanel();
        txtDa = new javax.swing.JFormattedTextField();
        txtA = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnDates = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMovements = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        pnEast.setLayout(new java.awt.GridBagLayout());

        btnDelete.setText("Elimina");
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

        btnRegister.setText("Aggiungi al registro");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnEast.add(btnRegister, gridBagConstraints);

        btnPrint.setText("Stampa");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnEast.add(btnPrint, gridBagConstraints);

        btnTestPrint.setText("Stampa di prova");
        btnTestPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestPrintActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnEast.add(btnTestPrint, gridBagConstraints);

        add(pnEast, java.awt.BorderLayout.EAST);

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

        tableMovements.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableMovements);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public void setProduct(Product product) {
        this.product = product;
    }

    private void btnDatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatesActionPerformed
        try {
            product = null;
            load();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Impossibile caricare i movimenti", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDatesActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        new SwingWorker<Object, Object>() {

            @Override
            protected Object doInBackground() throws Exception {
                blockGUI();
                try {
                    int[] selected = tableMovements.getSelectedRows();
                    if (selected.length > 0) {
                        for (int i = 0; i < selected.length; i++) {
                            selected[i] = tableMovements.convertRowIndexToModel(selected[i]);
                        }
                        Movement[] movements = model.getMovements(selected);
                        ArrayList<Movement> toRegister = new ArrayList<>();
                        LoadingFrame.makeLoadingFrame();
                        for (int i = 0;i < movements.length;i++) {
                            LoadingFrame.setPercent(i*100/movements.length);
                            Movement movement = movements[i];
                            if (movement.getProgressive() == -1 && DBMovementsManager.isGood(movement)) {
                                toRegister.add(movement);
                            }
                        }
                        LoadingFrame.destroyLoadingFrame();

                        if (toRegister.size() > 0) {
                            Movement first = toRegister.get(0);
                            boolean ok = true;
                            int month = 0, year = 0;
                            for (Movement toRegister1 : toRegister) {
                                month = Main.month(toRegister1.getOperationDate());
                                year = Main.year(toRegister1.getOperationDate());
                                if (year != Main.year(first.getOperationDate())) {
                                    ok = false;
                                }
                                if (month != Main.month(first.getOperationDate())) {
                                    ok = false;
                                }
                            }
                            if (ok) {
                                Movement shouldBeFirst = DBMovementsManager.getFirstMovementToRegister();
                                if (toRegister.get(0).compareTo(shouldBeFirst) != 0) {
                                    JOptionPane.showMessageDialog(null, "Esistono movimenti precedenti da registrare", "Attenzione", JOptionPane.WARNING_MESSAGE);
                                } else {
                                    int code;
                                    Register register = DBRegisterManager.getRegister(month, year);
                                    if (register == null) {
                                        DBRegisterManager.addRegister(month, year);
                                        register = DBRegisterManager.getRegister(month, year);
                                    }

                                    if (register != null) {
                                        code = register.getCode();
                                        try {
                                            LoadingFrame.makeLoadingFrame();
                                            for (int i = 0;i < toRegister.size();i++) {
                                                LoadingFrame.setPercent(i*100/toRegister.size());
                                                Movement toRegister1 = toRegister.get(i);
                                                DBMovementsManager.setProgressive(toRegister1);
                                                toRegister1 = DBMovementsManager.getMovement(toRegister1.getCode());
                                                Product prod = DBProductsManager.getProduct(toRegister1.getProduct());
                                                DeliveryDocument doc = DBDeliveryManager.getProductDeliveryDocument(prod);
                                                RegisterMovement rm = new RegisterMovement(
                                                        doc.getCustomer(),
                                                        prod,
                                                        toRegister1
                                                );
                                                rm.setRegisterCode(code);
                                                DBRegisterManager.addRegisterMovement(rm);
                                            }
                                            load();
                                            LoadingFrame.destroyLoadingFrame();
                                        } catch (SQLException ex) {
                                            JOptionPane.showMessageDialog(null, ex, "Errore durante la creazione.. l'operazione sarà annullata", JOptionPane.ERROR_MESSAGE);
                                            for (Movement toRegister1 : toRegister) {
                                                DBMovementsManager.resetProgressive(toRegister1);
                                            }
                                            DBRegisterManager.removeRegisterMovements(code);
                                        }
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "I movimenti devono essere dello stesso mese e dello stesso anno", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "I movimenti sono stati già registrati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex, "Impossibile creare il registro", JOptionPane.ERROR_MESSAGE);
                }
                unlockGUI();
                LoadingFrame.destroyLoadingFrame();
                return null;
            }
        }.execute();
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        PrinterJob pj = PrinterJob.getPrinterJob();
        RegisterChooserDialog rcd = new RegisterChooserDialog(parent, true);
        rcd.setVisible(true);
        Register reg = rcd.getRegister();
        if (reg != null) {
            RegisterMovement[] movements = null;
            try {
                movements = DBRegisterManager.getRegisterMovements(reg);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex, "Errore", JOptionPane.ERROR_MESSAGE);
            }
            /*
             Movement[] movements = model.getMovements();
             Arrays.sort(movements, new Comparator<Movement>() {
             @Override
             public int compare(Movement o1, Movement o2) {
             return Integer.compare(o1.getProgressive(), o2.getProgressive());
             }
             });*/
            if (movements != null && movements.length > 0) {

                boolean ok = true;
                int expectedProgressive = movements[0].getMovementProgressive();
                for (RegisterMovement movement : movements) {
                    if (movement.getMovementProgressive() != expectedProgressive++) {
                        ok = false;
                    }
                }
                if (ok) {
                    MovementsForm mf = new MovementsForm(movements, 1);
                    mf.setHMargin(10);
                    mf.setVMargin(10);
                    pj.setPrintable(mf);

                    if (pj.printDialog()) {
                        try {
                            PrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
                            attrSet.add(MediaSizeName.ISO_A4);
                            attrSet.add(OrientationRequested.LANDSCAPE);
                            pj.print(attrSet);
                        } catch (PrinterException ex) {
                            JOptionPane.showMessageDialog(this, "Impossibile stampare");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Mancano alcuni movimenti progressivi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selected = tableMovements.getSelectedRow();
        if (selected != -1) {
            try {
                selected = tableMovements.convertRowIndexToModel(selected);
                if (model.getMovement(selected).getProgressive() == -1) {
                    Product p = DBProductsManager.getProduct(model.getMovement(selected).getProduct());
                    p.loadMovements();
                    Movement[] pMovements = p.movements();
                    if (pMovements.length > 1) {
                        if (model.getMovement(selected).getCode() == pMovements[pMovements.length - 1].getCode()) {
                            if (JOptionPane.showConfirmDialog(this, "Sicuro di voler eliminare il movimento?") == JOptionPane.YES_OPTION) {
                                DBMovementsManager.removeMovement(model.getMovement(selected));
                                load();
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "E' possibile eliminare solo l'ultimo movimento del prodotto", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Non è possibile eliminare l'ultimo movimento del prodotto", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Non è possibile modificare un movimento registrato", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex, "Impossibile procedere con l'eliminazione", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnTestPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestPrintActionPerformed
        PrinterJob pj = PrinterJob.getPrinterJob();
        RegisterChooserDialog rcd = new RegisterChooserDialog(parent, true);
        rcd.setVisible(true);
        Register reg = rcd.getRegister();
        RegisterMovement[] movements = null;
        try {
            movements = DBRegisterManager.getRegisterMovements(reg);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Errore", JOptionPane.ERROR_MESSAGE);
        }
        /*
         Movement[] movements = model.getMovements();
         Arrays.sort(movements, new Comparator<Movement>() {
         @Override
         public int compare(Movement o1, Movement o2) {
         return Integer.compare(o1.getProgressive(), o2.getProgressive());
         }
         });*/
        if (movements != null && movements.length > 0) {

            boolean ok = true;
            int expectedProgressive = movements[0].getMovementProgressive();
            for (RegisterMovement movement : movements) {
                if (movement.getMovementProgressive() != expectedProgressive++) {
                    ok = false;
                }
            }
            if (ok) {
                MovementsForm mf = new MovementsForm(movements, 1);
                mf.setHMargin(10);
                mf.setVMargin(10);
                pj.setPrintable(mf);

                if (pj.printDialog()) {
                    try {
                        PrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
                        attrSet.add(MediaSizeName.ISO_A4);
                        attrSet.add(OrientationRequested.LANDSCAPE);
                        pj.print(attrSet);
                    } catch (PrinterException ex) {
                        JOptionPane.showMessageDialog(this, "Impossibile stampare");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mancano alcuni movimenti progressivi", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnTestPrintActionPerformed

    public void blockGUI() {
        btnDates.setEnabled(false);
        btnDelete.setEnabled(false);
        btnPrint.setEnabled(false);
        btnRegister.setEnabled(false);
        btnTestPrint.setEnabled(false);
    }

    public void unlockGUI() {
        btnDates.setEnabled(true);
        btnDelete.setEnabled(true);
        btnPrint.setEnabled(true);
        btnRegister.setEnabled(true);
        btnTestPrint.setEnabled(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDates;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton btnTestPrint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnEast;
    private javax.swing.JPanel pnNorth;
    private javax.swing.JTable tableMovements;
    private javax.swing.JFormattedTextField txtA;
    private javax.swing.JFormattedTextField txtDa;
    // End of variables declaration//GEN-END:variables
}

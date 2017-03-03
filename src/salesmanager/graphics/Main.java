/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Toolkit;
import salesmanager.graphics.panels.CustomersListPanel;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import salesmanager.beans.Movement;
import salesmanager.beans.dao.DBProductsManager;
import salesmanager.beans.Product;
import salesmanager.beans.dao.DBMovementsManager;
import salesmanager.components.datepicker.DatePickerDialog;
import salesmanager.graphics.dialogs.ProductDialog;
import salesmanager.graphics.panels.DeliveryDocumentsPanel;
import salesmanager.graphics.panels.HomePanel;
import salesmanager.graphics.panels.InvoicesListPanel;
import salesmanager.graphics.panels.MovementsPanel;
import salesmanager.graphics.panels.ProductsListPanel;
import salesmanager.printable.MovementsSummaryForm;
import salesmanager.printable.MultiDeliveryForm;

/**
 *
 * @author Lorenzo
 */
public class Main extends JFrame {

    public static final String BASE = "base";
    public static final String LIST = "onsale";
    public static final String CUSTOMERS = "customers";
    public static final String MOVEMENTS = "movements";
    public static final String DELIVERY = "delivery";
    public static final String INVOICES = "invoices";

    public static int year(Date d) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return gc.get(Calendar.YEAR);
    }

    public static int month(Date d) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return gc.get(Calendar.MONTH);
    }

    private JPanel basePanel;
    private CustomersListPanel customersPanel;
    private ProductsListPanel productsPanel;
    private MovementsPanel movementsPanel;
    private DeliveryDocumentsPanel deliveryPanel;
    private InvoicesListPanel invoicesPanel;
    //private HistoryProductsListPanel historyProductsPanel;
    private Image logoImage;

    public Main() {
        try {
            LoadingFrame.makeLoadingFrame();
            //setUndecorated(true);
            initComponents();
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setExtendedState(MAXIMIZED_BOTH);
            customersPanel = new CustomersListPanel(this);
            productsPanel = new ProductsListPanel(this);
            movementsPanel = new MovementsPanel(this);
            deliveryPanel = new DeliveryDocumentsPanel(this);
            invoicesPanel = new InvoicesListPanel();
            logoImage = Toolkit.getDefaultToolkit().getImage("./files/logo/logo.png");

            basePanel = new HomePanel(this);

//historyProductsPanel = new HistoryProductsListPanel(this);
            mainPanel.add(basePanel, Main.BASE);
            mainPanel.add(customersPanel, Main.CUSTOMERS);
            mainPanel.add(productsPanel, Main.LIST);
            mainPanel.add(movementsPanel, Main.MOVEMENTS);
            mainPanel.add(deliveryPanel, Main.DELIVERY);
            mainPanel.add(invoicesPanel, Main.INVOICES);
            //mainPanel.add(historyProductsPanel,Main.HISTORY);
            showPanel(BASE);

            Timer t = new Timer();
            TimerTask updateProducts = new TimerTask() {

                @Override
                public void run() {
                    try {
                        LoadingFrame.makeLoadingFrame();
                        ArrayList<String> errors = new ArrayList<>();
                        DBProductsManager.updateProducts(errors);
                        LoadingFrame.destroyLoadingFrame();
                        StringBuilder codes = new StringBuilder();
                        for (String error : errors) {
                            codes.append(error).append("\n");
                        }
                        if (errors.size() > 0) {
                            JOptionPane.showMessageDialog(null, "Prodotti con errore: " + codes.toString(), "Errore durante l'aggiornamento dei prezzi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        LoadingFrame.destroyLoadingFrame();
                        JOptionPane.showMessageDialog(null, ex, "Errore durante l'aggiornamento dei prezzi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            // This task is scheduled to run every 10 seconds

            t.scheduleAtFixedRate(updateProducts, 0, 1000 * 60 * 60);

            LoadingFrame.destroyLoadingFrame();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore durante il caricamento", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
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

        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        mainPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itmExit = new javax.swing.JMenuItem();
        mnCustomers = new javax.swing.JMenu();
        itmCustomersList = new javax.swing.JMenuItem();
        mnProducts = new javax.swing.JMenu();
        itmOnSaleProducts = new javax.swing.JMenuItem();
        btnSearch = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnDeliveryDocuments = new javax.swing.JMenuItem();
        mnMovements = new javax.swing.JMenuItem();
        mnInvoices = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        itmDailySummary = new javax.swing.JMenuItem();
        itmDeliveryDocument = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new java.awt.CardLayout());
        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        itmExit.setIcon(new javax.swing.ImageIcon("D:\\NetBeans\\SalesManager\\files\\images\\close.png")); // NOI18N
        itmExit.setText("Esci");
        itmExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmExitActionPerformed(evt);
            }
        });
        jMenu1.add(itmExit);

        menuBar.add(jMenu1);

        mnCustomers.setText("Clienti");

        itmCustomersList.setText("Elenco");
        itmCustomersList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmCustomersListActionPerformed(evt);
            }
        });
        mnCustomers.add(itmCustomersList);

        menuBar.add(mnCustomers);

        mnProducts.setText("Prodotti");

        itmOnSaleProducts.setText("Elenco");
        itmOnSaleProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmOnSaleProductsActionPerformed(evt);
            }
        });
        mnProducts.add(itmOnSaleProducts);

        btnSearch.setText("Ricerca");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        mnProducts.add(btnSearch);
        mnProducts.add(jSeparator1);

        mnDeliveryDocuments.setText("Documenti di consegna");
        mnDeliveryDocuments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDeliveryDocumentsActionPerformed(evt);
            }
        });
        mnProducts.add(mnDeliveryDocuments);

        mnMovements.setText("Movimenti");
        mnMovements.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMovementsActionPerformed(evt);
            }
        });
        mnProducts.add(mnMovements);

        mnInvoices.setText("Fatture");
        mnInvoices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnInvoicesActionPerformed(evt);
            }
        });
        mnProducts.add(mnInvoices);
        mnProducts.add(jSeparator4);

        itmDailySummary.setText("Stampa riepilogo giornaliero");
        itmDailySummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDailySummaryActionPerformed(evt);
            }
        });
        mnProducts.add(itmDailySummary);

        itmDeliveryDocument.setText("Stampa documento di consegna");
        itmDeliveryDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDeliveryDocumentActionPerformed(evt);
            }
        });
        mnProducts.add(itmDeliveryDocument);
        mnProducts.add(jSeparator2);

        menuBar.add(mnProducts);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itmCustomersListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmCustomersListActionPerformed
        try {
            showPanel(CUSTOMERS);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Impossibile caricare i clienti", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itmCustomersListActionPerformed

    private void itmOnSaleProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmOnSaleProductsActionPerformed
        try {
            productsPanel.setCustomer(null);
            showPanel(LIST);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Impossibile caricare i prodotti", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itmOnSaleProductsActionPerformed

    private void itmDeliveryDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDeliveryDocumentActionPerformed
        PrinterJob pj = PrinterJob.getPrinterJob();
        MultiDeliveryForm mdf = new MultiDeliveryForm();
        mdf.setHMargin(10);
        mdf.setVMargin(10);
        pj.setPrintable(mdf);
        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Impossibile stampare");
            }
        }
    }//GEN-LAST:event_itmDeliveryDocumentActionPerformed

    private void itmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_itmExitActionPerformed

    private void mnMovementsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMovementsActionPerformed
        try {
            movementsPanel.setProduct(null);
            showPanel(MOVEMENTS);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Impossibile caricare i movimenti", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mnMovementsActionPerformed

    private void mnDeliveryDocumentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDeliveryDocumentsActionPerformed
        try {
            showPanel(DELIVERY);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Impossibile caricare i contratti", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mnDeliveryDocumentsActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try {
            int code = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'ID"));
            Product p;
            try {
                p = DBProductsManager.getProduct(code);
                if (p != null) {
                    p.loadMovements();
                    ProductDialog pd = new ProductDialog(this);
                    pd.setProduct(p);
                    pd.setVisible(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex, "Impossibile recuperare il prodotto", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID non corretto", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void mnInvoicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnInvoicesActionPerformed
        try {
            showPanel(INVOICES);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Impossibile caricare le fatture", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mnInvoicesActionPerformed

    private void itmDailySummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDailySummaryActionPerformed
        DatePickerDialog dpd = new DatePickerDialog(this);
        dpd.setVisible(true);
        Date d = dpd.getBean();
        if (d != null) {
            PrinterJob pj = PrinterJob.getPrinterJob();
            try {
                Movement[] movements = DBMovementsManager.getMovements(d, d);

                if (movements != null && movements.length > 0) {
                    boolean ok = true;
                    if (ok) {
                        MovementsSummaryForm mf = new MovementsSummaryForm(movements, 1);
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
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex, "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }


    }//GEN-LAST:event_itmDailySummaryActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnSearch;
    private javax.swing.JMenuItem itmCustomersList;
    private javax.swing.JMenuItem itmDailySummary;
    private javax.swing.JMenuItem itmDeliveryDocument;
    private javax.swing.JMenuItem itmExit;
    private javax.swing.JMenuItem itmOnSaleProducts;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu mnCustomers;
    private javax.swing.JMenuItem mnDeliveryDocuments;
    private javax.swing.JMenuItem mnInvoices;
    private javax.swing.JMenuItem mnMovements;
    private javax.swing.JMenu mnProducts;
    // End of variables declaration//GEN-END:variables

    public CustomersListPanel getCustomersPanel() {
        return customersPanel;
    }

    public DeliveryDocumentsPanel getDeliveryPanel() {
        return deliveryPanel;
    }

    public InvoicesListPanel getInvoicesPanel() {
        return invoicesPanel;
    }

    public void showPanel(String p) throws SQLException {
        LoadingFrame.makeLoadingFrame();
        try {
            ((CardLayout) mainPanel.getLayout()).show(mainPanel, p);
            switch (p) {
                case CUSTOMERS:
                    customersPanel.load();
                    break;
                case DELIVERY:
                    deliveryPanel.load();
                    break;
                case INVOICES:
                    invoicesPanel.load();
                    break;
                case LIST:
                    productsPanel.load();
                    break;
                case MOVEMENTS:
                    movementsPanel.load();
                    break;
            }
        } catch (SQLException ex) {
            LoadingFrame.destroyLoadingFrame();
            throw ex;
        }
        LoadingFrame.destroyLoadingFrame();
    }

    public MovementsPanel getMovementsPanel() {
        return movementsPanel;
    }

    public ProductsListPanel getProductsListPanel() {
        return productsPanel;
    }

    public static Date today() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        return new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH)).getTime();
    }

    public static Date simplify(Date d) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH)).getTime();
    }

    public static Date lastMonth() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(today());
        gc.add(Calendar.MONTH, -1);
        return gc.getTime();
    }
}

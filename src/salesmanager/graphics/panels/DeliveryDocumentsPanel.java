/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import salesmanager.beans.DeliveryDocument;
import salesmanager.beans.Product;
import salesmanager.beans.dao.DBDeliveryManager;
import salesmanager.beans.dao.DBMovementsManager;
import salesmanager.beans.dao.DBProductsManager;
import salesmanager.graphics.Main;
import salesmanager.graphics.dialogs.NewDeliveryDocumentDialog;
import salesmanager.graphics.dialogs.internal.DatesBoundPanel;
import salesmanager.graphics.tables.DeliveryDocumentsTableModel;
import salesmanager.printable.DeliveryDocumentForm;

/**
 *
 * @author Lorenzo
 */
public class DeliveryDocumentsPanel extends OkCancelPanel implements ActionListener, Loadable {

    /**
     * Creates new form CustomersListDialog
     */
    private Main parent;
    private DeliveryDocumentsTableModel model;
    private JTable tableDocuments;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnPrint;

    private DatesBoundPanel datesPanel;
    private JButton btnDates;

    public DeliveryDocumentsPanel(Main parent) throws SQLException {
        this.parent = parent;
        setName("Elenco documenti di consegna");
        model = new DeliveryDocumentsTableModel();
        tableDocuments.setModel(model);
        tableDocuments.setAutoCreateRowSorter(true);
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        Font f = new Font("Arial", Font.PLAIN, 12);
        int max = 0;
        tableDocuments.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        for (int i = 0; i < model.getColumnCount(); i++) {
            String s = model.getColumnName(i);
            max = Math.max(max, (int) f.getStringBounds(s, frc).getWidth());
        }
        for (int i = 0; i < tableDocuments.getColumnModel().getColumnCount(); i++) {
            tableDocuments.getColumnModel().getColumn(i).setPreferredWidth(max + 20);
        }
        setSize(800, 600);
    }

    @Override
    protected JPanel createNorthPanel() {

        JPanel pnNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        datesPanel = new DatesBoundPanel();
        pnNorth.add(datesPanel);
        btnDates = new JButton("Ok");
        btnDates.addActionListener(this);
        pnNorth.add(btnDates);
        pnNorth.setBorder(new TitledBorder("Filtro"));

        return pnNorth;
    }

    @Override
    protected JPanel createCenterPanel() {
        tableDocuments = new JTable();
        JScrollPane pane = new JScrollPane(tableDocuments);
        JPanel ret = new JPanel(new BorderLayout(10, 10));
        ret.add(pane);
        return ret;
    }

    @Override
    protected JPanel createEastPanel() {
        btnAdd = new JButton("Nuovo");
        btnAdd.addActionListener(new DeliveryDocumentsActionListener(this));
        btnEdit = new JButton("Modifica");
        btnEdit.addActionListener(new DeliveryDocumentsActionListener(this));
        btnDelete = new JButton("Elimina");
        btnDelete.addActionListener(new DeliveryDocumentsActionListener(this));
        btnPrint = new JButton("Stampa");
        btnPrint.addActionListener(new DeliveryDocumentsActionListener(this));
        //btnRemove = new JButton("Elimina");
        //btnRemove.addActionListener(new BackProductsListActionListener(this));
        JPanel pn = new JPanel(new GridLayout(4, 1, 10, 10));
        pn.add(btnAdd);
        pn.add(btnEdit);
        pn.add(btnDelete);
        pn.add(btnPrint);
        //pn.add(btnRemove);
        return pn;
    }

    @Override
    protected JPanel createWestPanel() {
        return new JPanel();
    }

    @Override
    protected void onOk() {
        try {
            parent.showPanel(Main.BASE);
            model.unload();
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDocumentsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void onCancel() {
        try {
            parent.showPanel(Main.BASE);
            model.unload();
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDocumentsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDates) {
            model.setFrom(datesPanel.getDa());
            model.setTo(datesPanel.getA());
            try {
                model.reload();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Impossibile caricare i dati");
            }
        }
    }

    @Override
    public void load() throws SQLException {
        model.reload();
    }

    private class DeliveryDocumentsActionListener implements ActionListener {

        private DeliveryDocumentsPanel panel;

        public DeliveryDocumentsActionListener(DeliveryDocumentsPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnAdd) {
                NewDeliveryDocumentDialog deliveryDialog = new NewDeliveryDocumentDialog(parent);
                deliveryDialog.setVisible(true);
                if (deliveryDialog.getDocument() != null) {
                    DeliveryDocument doc = deliveryDialog.getDocument();

                    if (JOptionPane.showConfirmDialog(null, "Aggiungere il contratto " + doc.getProgressive() + "/" + Main.year(doc.getDocumentDate()) + "?", "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        try {
                            DBDeliveryManager.addDeliveryDocument(doc);
                            if (doc.getCode() != -1) {
                                Product[] prods = doc.getProducts();
                                for (Product prod : prods) {
                                    prod.setDeliveryDocument(doc.getCode());
                                    DBProductsManager.addProduct(prod);
                                    if (prod.getCode() != -1) {
                                        prod.movements()[0].setProduct(prod.getCode());
                                        //Cancel insertion if fail
                                        if (!DBMovementsManager.addMovement(prod.movements()[0])) {
                                            DBProductsManager.removeProduct(prod);
                                            JOptionPane.showMessageDialog(parent, "Controllare i prodotti inseriti", "Impossibile aggiungere un prodotto", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }
                                model.reload();
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(parent, ex, "Impossibile aggiungere il documento di consegna", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else if (e.getSource() == btnEdit) {

                int selected = tableDocuments.getSelectedRow();
                if (selected != -1) {
                    try {
                        DeliveryDocument oldDocument = model.getDocument(tableDocuments.convertRowIndexToModel(selected));
                        oldDocument.loadProducts();
                        NewDeliveryDocumentDialog deliveryDialog = new NewDeliveryDocumentDialog(parent);
                        deliveryDialog.setDocument(oldDocument);
                        deliveryDialog.setVisible(true);
                        deliveryDialog.pack();
                        if (deliveryDialog.getDocument() != null) {
                            DeliveryDocument newDocument = deliveryDialog.getDocument();
                            newDocument.setCode(oldDocument.getCode());
                            newDocument.setProgressive(oldDocument.getProgressive());
                            if(DBDeliveryManager.editDeliveryDocument(newDocument)){
                                Product[] prods = newDocument.getProducts();
                                for (Product prod : prods) {
                                    if (prod.getDeliveryDocument() == -1) {
                                        prod.setDeliveryDocument(newDocument.getCode());
                                        DBProductsManager.addProduct(prod);
                                        if (prod.getCode() != -1) {
                                            prod.movements()[0].setProduct(prod.getCode());
                                            DBMovementsManager.addMovement(prod.movements()[0]);
                                        }
                                    }
                                }
                                model.reload();
                            }

                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(parent, ex, "Impossibile modificare il documento di consegna", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } else if (e.getSource() == btnDelete) {
                try {
                    int selected = tableDocuments.getSelectedRow();
                    if (selected != -1) {
                        selected = tableDocuments.convertRowIndexToModel(selected);
                        DeliveryDocument doc = model.getDocument(tableDocuments.convertRowIndexToModel(selected));
                        if (DBDeliveryManager.canRemove(doc)) {
                            int choice = JOptionPane.showConfirmDialog(panel, "Sicuro di voler eliminare il documento e tutti i prodotti?", "Attenzione", JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                Product[] products = doc.getProducts();
                                DBDeliveryManager.removeDeliveryDocument(doc);
                                for (Product product : products) {
                                    DBProductsManager.removeProduct(product);
                                }
                                model.reload();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Il documento non può essere eliminato", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione " + ex.getMessage());
                }
            } else if (e.getSource() == btnPrint) {
                int selected = tableDocuments.getSelectedRow();
                if (selected != -1) {
                    DeliveryDocument document = model.getDocument(tableDocuments.convertRowIndexToModel(selected));
                    try {
                        document.loadProducts();
                        PrinterJob pj = PrinterJob.getPrinterJob();
                        pj.setCopies(2);
                        pj.setPrintable(new DeliveryDocumentForm(document));
                        if (pj.printDialog()) {
                            try {
                                pj.print();
                            } catch (PrinterException ex) {
                                JOptionPane.showMessageDialog(parent, "Impossibile stampare");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(parent, "Impossibile caricare i prodotti", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.tables;

import java.sql.SQLException;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import salesmanager.beans.Invoice;
import salesmanager.beans.dao.DBCustomersManager;
import salesmanager.beans.dao.DBInvoicesManager;

/**
 *
 * @author Lorenzo
 */
public class InvoicesTableModel extends AbstractTableModel {

    private String[] titles = {
        "Codice",
        "Progressivo",
        "Data",
        "Cliente"};

    private Date from;
    private Date to;

    private Invoice[] invoices;

    public InvoicesTableModel(Date da, Date a){
        invoices = new Invoice[0];
        from = da;
        to = a;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public void loadInvoices(Date from, Date to) throws SQLException {
        invoices = DBInvoicesManager.getInvoices(from, to);
        fireTableDataChanged();

    }
    /*
     public void loadCustomerInvoices(Customer c) throws SQLException{
     invoices = DBInvoicesManager.
     fireTableDataChanged();
     }*/

    public void unload() {
        invoices = null;
        fireTableDataChanged();
    }

    @Override
    public final int getRowCount() {
        if (invoices != null) {
            return invoices.length;
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return titles.length;
    }

    @Override
    public String getColumnName(int column) {
        return titles[column];
    }

    @Override
    public final Object getValueAt(int rowIndex, int columnIndex) {
        Invoice i = invoices[rowIndex];
        switch (columnIndex) {
            case 0:
                return i.getCode();
            case 1:
                if (i.getProgressive() == -1) {
                    return "N/A";
                }
                return i.getProgressive();
            case 2:
                return i.getInvoiceDate();
            case 3:
                try {
                    return DBCustomersManager.getCustomer(i.getCustomer());
                } catch (SQLException ex) {
                    return "Cod: " + i.getCustomer();
                }
        }
        return "";
    }

    public final Invoice getInvoice(int i) {
        //return ProductsManager.instance().getReturnedProducts()[i];
        if (invoices != null) {
            return invoices[i];
        }
        return null;
    }

    public final Invoice[] getInvoices() {
        return invoices;
    }

}

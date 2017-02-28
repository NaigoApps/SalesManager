/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.tables;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.table.AbstractTableModel;
import salesmanager.beans.DeliveryDocument;
import salesmanager.beans.dao.DBDeliveryManager;
import salesmanager.graphics.Main;

/**
 *
 * @author Lorenzo
 */
public class DeliveryDocumentsTableModel extends AbstractTableModel {

    private DeliveryDocument [] documents;
    private Date from;
    private Date to;
    
    private String[] titles = {
        "Progressivo",
        "Data",
        "Cliente"};


    public DeliveryDocumentsTableModel() throws SQLException {
        documents = new DeliveryDocument[0];
        to = Main.today();
        from = Main.lastMonth();
    }


    @Override
    public int getColumnCount() {
        return titles.length;
    }
    
    

    @Override
    public String getColumnName(int column) {
        return titles[column];
    }

    protected DeliveryDocument[] loadDocuments() throws SQLException {
        return DBDeliveryManager.getDeliveryDocuments(from, to);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DeliveryDocument d = documents[rowIndex];
        switch (columnIndex) {
            case 0:
                return d.getProgressive();
            case 1:
                return d.getDocumentDate();
            case 2:
                return d.getCustomer();
        }
        return "";
    }
    
    
    public void reload() throws SQLException{
        documents = loadDocuments();
        fireTableDataChanged();
    }
    
    public void unload(){
        documents = null;
        fireTableDataChanged();
    }
    
    @Override
    public final int getRowCount() {
        if(documents != null){
            return documents.length;
        }
        return 0;
    }

    public final DeliveryDocument getDocument(int i){
        //return ProductsManager.instance().getReturnedProducts()[i];
        if(documents != null){
            return documents[i];
        }
        return null;
    }

    public final DeliveryDocument[] getDocuments() {
        return documents;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setTo(Date to) {
        this.to = to;
    }
    
    
}

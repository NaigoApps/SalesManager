/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.tables;

import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
import salesmanager.beans.Customer;
import salesmanager.beans.dao.DBCustomersManager;

/**
 *
 * @author Lorenzo
 */
public class CustomersModel extends AbstractTableModel{

    private String[] titles = {
        "Codice",
        "Nome",
        "Cognome",
        "Codice fiscale",
        "Partita IVA",
        "Telefono",
        "Indirizzo",
        "Documento",
        "Città"};
    
    private Customer[] customers;
    private String like;

    public CustomersModel() {
        customers = new Customer[0];
    }
    
    public void reload() throws SQLException{
        if(like == null){
            customers = DBCustomersManager.getCustomers();
        }else{
            customers = DBCustomersManager.getCustomers(like);
        }
        fireTableDataChanged();
    }
    
    public void unload(){
        customers = null;
        like = null;
        fireTableDataChanged();
    }
    
    
    @Override
    public int getColumnCount() {
        return titles.length;
    }

    private Object getValueAt(Customer c, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return c.getCode();
            case 1:
                return c.getName();
            case 2:
                return c.getSurname();
            case 3:
                return c.getCf();
            case 4:
                return c.getPiva();
            case 5:
                return c.getTelephone();
            case 6:
                return c.getAddress();
            case 7:
                return c.getDocument();
            case 8:
                return c.getCity();
        }
        return "";
    }

    
    @Override
    public final int getRowCount() {
        if(customers != null){
            return customers.length;
        }
        return 0;
    }

    
    @Override
    public String getColumnName(int column) {
        return titles[column];
    }
    
    @Override
    public final Object getValueAt(int rowIndex, int columnIndex) {
        Customer c = customers[rowIndex];
        return getValueAt(c, columnIndex);
    }

    public final Customer getCustomer(int i){
        //return ProductsManager.instance().getReturnedProducts()[i];
        if(customers != null){
            return customers[i];
        }
        return null;
    }

    public final Customer[] getCustomers() {
        return customers;
    }

    public void setLike(String surname) throws SQLException {
        this.like = surname;
        reload();
    }
    
    
}

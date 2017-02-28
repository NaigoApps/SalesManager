/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.tables;

import java.sql.SQLException;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import salesmanager.beans.Customer;
import salesmanager.beans.dao.DBCustomersManager;

/**
 *
 * @author Lorenzo
 */
public class CustomersComboBoxModel extends AbstractListModel<Customer> implements ComboBoxModel<Customer> {

    private Customer[] customers;
    private Object selected;

    public CustomersComboBoxModel() throws SQLException {
        customers = DBCustomersManager.getCustomers();
        if(customers.length > 0){
            setSelectedItem(customers[0]);
        }
    }
    
    
    
    @Override
    public int getSize() {
        return customers.length;
    }

    @Override
    public Customer getElementAt(int index) {
        return customers[index];
    }
    
    public void setSelectedItem(int code){
        for (int i = 0; i < customers.length; i++) {
            if(customers[i].getCode() == code){
                setSelectedItem(customers[i]);
            }
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.selected = anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }

    
}

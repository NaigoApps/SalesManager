/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.dialogs;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import salesmanager.beans.Customer;
import salesmanager.graphics.dialogs.internal.CustomerInternalPanel;

/**
 *
 * @author Lorenzo
 */
public class CustomerDialog extends OkCancelDialog {

    private CustomerInternalPanel pnCustomer;

    private Customer customer;

    public CustomerDialog(JFrame parent) {
        super(parent, true);
        pack();
        setLocationRelativeTo(null);
    }
    
    public void setCustomer(Customer c){
        pnCustomer.setCustomer(c);
    }
    
    public Customer getCustomer(){
        return customer;
    }

    @Override
    protected JPanel createNorthPanel() {
        JPanel pnNorth = new JPanel();
        JLabel l = new JLabel("Dati cliente");
        l.setFont(new Font(null, Font.BOLD, 20));
        pnNorth.add(l);
        return pnNorth;
    }

    @Override
    protected JPanel createCenterPanel() {
        pnCustomer = new CustomerInternalPanel();
        return pnCustomer;
    }

    @Override
    protected JPanel createEastPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createWestPanel() {
        return new JPanel();
    }

    @Override
    protected void onOk() {
        if(pnCustomer.updateData()){
            customer = pnCustomer.getCustomer();
            dispose();
        }else{
            customer = null;
            JOptionPane.showMessageDialog(this, "Dati non validi");
        }
    }

    @Override
    protected void onCancel() {
        customer = null;
        dispose();
    }

}

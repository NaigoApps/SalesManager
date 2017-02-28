/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.dialogs;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import salesmanager.beans.Customer;
import salesmanager.graphics.dialogs.internal.CustomerChooserInternalPanel;

/**
 *
 * @author Lorenzo
 */
public class CustomerChooserDialog extends OkCancelDialog {

    private CustomerChooserInternalPanel ccip;
    private Customer customer;

    public CustomerChooserDialog() {
        super(null, true);
        setTitle("Selezione del cliente");
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    protected JPanel createNorthPanel() {
        JPanel pnNorth = new JPanel();
        return pnNorth;
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
    protected JPanel createCenterPanel() {
        try {
            ccip = new CustomerChooserInternalPanel();
            return ccip;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Impossibile caricare i clienti");
        }
        return new JPanel();
    }

    @Override
    protected void onOk() {
        if(ccip.updateData()){
            this.customer = ccip.getCustomer();
            dispose();
        }else{
            this.customer = null;
        }
    }

    @Override
    protected void onCancel() {
        this.customer = null;
        dispose();
    }

    public Customer getCustomer() {
        return customer;
    }

    
}

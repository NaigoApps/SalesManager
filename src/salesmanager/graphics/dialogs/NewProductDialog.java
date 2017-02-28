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
import salesmanager.beans.Product;
import salesmanager.graphics.dialogs.internal.ProductInternalPanel;

/**
 *
 * @author Lorenzo
 */
public class NewProductDialog extends OkCancelDialog {

    private ProductInternalPanel pnProduct;
    private Product product;

    public NewProductDialog(JFrame parent){
        super(parent, true);
        pack();
        setLocationRelativeTo(null);
        product = null;
    }

    @Override
    protected JPanel createNorthPanel() {
        JPanel pnNorth = new JPanel();
        JLabel l = new JLabel("Dati prodotto");
        l.setFont(new Font(null, Font.BOLD, 20));
        pnNorth.add(l);
        return pnNorth;
    }

    @Override
    protected JPanel createCenterPanel() {
        pnProduct = new ProductInternalPanel();
        return pnProduct;
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
        if (pnProduct.updateData()) {
            product = pnProduct.getProduct();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Dati non validi!");
        }
    }

    @Override
    protected void onCancel() {
        dispose();
    }

    public Product getProduct() {
        return product;
    }

    
    
}

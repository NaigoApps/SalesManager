/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.printable;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import salesmanager.beans.Product;
import salesmanager.beans.dao.DBDeliveryManager;

/**
 *
 * @author Lorenzo
 */
public class ProductLabelForm extends DefaultForm{

    private Product[] products;

    public ProductLabelForm(Product[] products) {
        super(products.length);
        this.products = products;
    }
    
    
    @Override
    public void printPages(Graphics g, PageFormat pf, int pageIndex){
        int i = pageIndex;
        g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        resetUp(g, pf);
        printLine(getLeft(pf),"Codice oggetto: " + String.format("%010d",products[i].getCode()), g, pf);
        printLine(getLeft(pf),"Nome oggetto: " + products[i].getName(), g, pf);
        printLine(getLeft(pf),"Prezzo accordato: " + products[i].getPrice() + "€", g, pf);
        try {
            printLine(getLeft(pf),"Codice cliente: " + String.format("%010d",DBDeliveryManager.getProductDeliveryDocument(products[i]).getCustomer().getCode()), g, pf);
        } catch (SQLException ex) {
            printLine(getLeft(pf),"Codice cliente:", g, pf);
        }
        try {
            products[i].loadMovements();
            printLine(getLeft(pf),"Data di entrata: " + products[i].movements()[0].getOperationDate(), g, pf);
        } catch (SQLException ex) {
            printLine(getLeft(pf),"Data di entrata:", g, pf);
        }

    }
    
}

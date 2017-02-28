/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.printable;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import salesmanager.beans.Customer;
import salesmanager.beans.Invoice;
import salesmanager.beans.Product;
import salesmanager.beans.dao.DBCustomersManager;
import salesmanager.beans.dao.DBProductsManager;
import salesmanager.graphics.Main;
import static salesmanager.printable.DefaultForm.pts;

/**
 *
 * @author Lorenzo
 */
public class ProductsInvoiceForm extends DefaultForm {

    private Image imageLogo;
    private Invoice invoice;

    public ProductsInvoiceForm(Invoice invoice) {
        super(2);
        setHMargin(10);
        setVMargin(10);
        this.invoice = invoice;
        imageLogo = null;
        try {
            imageLogo = ImageIO.read(new File("./files/logo/logo_xxs.png"));
        } catch (IOException ex) {
            Logger.getLogger(ProductsInvoiceForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void printPages(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex == 1) {
            printInvoice(g, pf);
        } else {
            print2ndDocument(g, pf);
        }
    }

    private void printInvoice(Graphics g, PageFormat pf) {
        resetUp(g, pf);
        Customer customer = null;
        try {
            customer = DBCustomersManager.getCustomer(invoice.getCustomer());
        } catch (SQLException ex) {
            customer = null;
        }
        printHeader(customer, g, pf);

        setLine(getTop(pf) + pts(80));
        printCenteredLine("Ricevuta n° " + invoice.getProgressive() + "/" + Main.year(invoice.getInvoiceDate()), g, pf);
        /*g.setFont(new Font("Times New Roman", Font.BOLD, 18));
         GregorianCalendar gc = new GregorianCalendar();
         gc.setTime(invoice.getInvoiceDate());
         int year = gc.get(Calendar.YEAR);
         printCenteredLine("Contratto di mandato di vendita " + document.getProgressive() + "/" + year, g, pf);*/
        g.setFont(new Font("Times New Roman", Font.BOLD, 8));

        printLine("Il sottoscritto " + customer.getSurname() + " " + customer.getName() + " dichiara di avere ricevuto quanto di propria spettanza", g, pf);
        printLine("per la vendita per nostro ordine e conto di beni usati di nostra proprietà come di seguito descritti e ne rilascia la piena quietanza.", g, pf);

        /*
         double[] rows = new double[pages.get(pageIndex).size() + 1];
         for (int i = 0; i < rows.length; i++) {
         rows[i] = 100 / rows.length;
         }*/
        double top = getLine();

        g.setFont(new Font("Arial", Font.BOLD, 10));
        FormTable ft = new FormTable(new double[]{100}, new double[]{10, 15, 35, 25, 15}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), pts(8)));
        ft.drawCenteredString("Q.tà", 0, 0, g);
        ft.drawCenteredString("Documento", 0, 1, g);
        ft.drawCenteredString("Descrizione", 0, 2, g);
        ft.drawCenteredString("Totale pattuito compr. IVA", 0, 3, g);
        ft.drawCenteredString("Provvigione", 0, 4, g);
        g.setFont(new Font("Arial", Font.PLAIN, 9));
        top += pts(8);
        Product[] products = null;
        try {
            products = DBProductsManager.getFromInvoiceProducts(invoice.getCode());
        } catch (SQLException ex) {
            products = new Product[0];
        }
        float tot = 0, realTot = 0;
        ft = new FormTable(Invoice.MAX_PRODUCTS, new double[]{10, 15, 35, 25, 15}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), Invoice.MAX_PRODUCTS * pts(8)));
        for (int i = 0; i < Invoice.MAX_PRODUCTS; i++) {
            if (i < products.length) {
                printProduct(ft, products[i], i, g);
                tot += products[i].getPrice() * products[i].getCommission() / 100;
                realTot += products[i].getPrice();
            }
            top += pts(8);
        }
        ft.paint(g);

        ft = new FormTable(new double[]{100}, new double[]{80, 20}, new Rectangle(getLeft(pf), top + pts(4), getRight(pf) - getLeft(pf), pts(8)));

        if (realTot - tot >= 77.47) {
            ft = new FormTable(new double[]{50, 50}, new double[]{90, 10}, new Rectangle(getLeft(pf), top + pts(4), getRight(pf) - getLeft(pf), 2 * pts(8)));
            ft.drawRightString("Valore rimborsato dedotto dalle provvigioni di vostra spettanza", 0, 0, g);
            ft.drawString(String.format("%5.2f€", realTot - tot), 0, 1, g);
            ft.drawRightString("Fuori campo IVA marca da bollo su imponibile, imposta di bollo assolta", 1, 0, g);
            ft.drawString("2.00€", 1, 1, g);
        } else {
            ft = new FormTable(new double[]{100}, new double[]{90, 10}, new Rectangle(getLeft(pf), top + pts(4), getRight(pf) - getLeft(pf), pts(8)));
            ft.drawRightString("Valore rimborsato dedotto dalle provvigioni di vostra spettanza", 0, 0, g);
            ft.drawString(String.format("%5.2f€", realTot - tot), 0, 1, g);
            ft.paint(g);
        }
        
        resetDown(g, pf);
        backLine(g, pf);
        backLine(g, pf);
        backLine(g, pf);
        printCenteredLine("Firma", g, pf);
        printLine(g, pf);
        printCenteredLine("____________________________", g, pf);
    }

    private void print2ndDocument(Graphics g, PageFormat pf) {
        resetUp(g, pf);
        Customer customer = null;
        try {
            customer = DBCustomersManager.getCustomer(invoice.getCustomer());
        } catch (SQLException ex) {
            customer = null;
        }
        printHeader(customer, g, pf);

        setLine(getTop(pf) + pts(80));
        printCenteredLine("Fattura n° " + invoice.getProgressive() + "/" + Main.year(invoice.getInvoiceDate()), g, pf);
        /*g.setFont(new Font("Times New Roman", Font.BOLD, 18));
         GregorianCalendar gc = new GregorianCalendar();
         gc.setTime(invoice.getInvoiceDate());
         int year = gc.get(Calendar.YEAR);
         printCenteredLine("Contratto di mandato di vendita " + document.getProgressive() + "/" + year, g, pf);*/
        g.setFont(new Font("Times New Roman", Font.BOLD, 8));
        printLine("Provvigione per il servizio di intermediario per la vendita del vostro bene usato come da contratto.", g, pf);
        /*
         double[] rows = new double[pages.get(pageIndex).size() + 1];
         for (int i = 0; i < rows.length; i++) {
         rows[i] = 100 / rows.length;
         }*/
        double top = getLine();

        g.setFont(new Font("Arial", Font.BOLD, 10));
        FormTable ft = new FormTable(new double[]{100}, new double[]{10, 15, 35, 25, 15}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), pts(8)));
        ft.drawCenteredString("Q.tà", 0, 0, g);
        ft.drawCenteredString("Documento", 0, 1, g);
        ft.drawCenteredString("Descrizione", 0, 2, g);
        ft.drawCenteredString("Totale pattuito compr. IVA", 0, 3, g);
        ft.drawCenteredString("Provvigione", 0, 4, g);
        g.setFont(new Font("Arial", Font.PLAIN, 9));
        top += pts(8);
        Product[] products = null;
        try {
            products = DBProductsManager.getFromInvoiceProducts(invoice.getCode());
        } catch (SQLException ex) {
            products = new Product[0];
        }
        float tot = 0;
        ft = new FormTable(Invoice.MAX_PRODUCTS, new double[]{10, 15, 35, 25, 15}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), Invoice.MAX_PRODUCTS * pts(8)));
        for (int i = 0; i < Invoice.MAX_PRODUCTS; i++) {
            if (i < products.length) {
                printProduct(ft, products[i], i, g);
                tot += products[i].getPrice() * products[i].getCommission() / 100;
            }
            top += pts(8);
        }
        ft.paint(g);

        float iva = tot * 22 / 100;

        ft = new FormTable(new double[]{25, 25, 25, 25}, new double[]{90, 10}, new Rectangle(getLeft(pf), top + pts(4), getRight(pf) - getLeft(pf), 4 * pts(7)));

        ft.drawRightString("Imponibile comprensivo IVA", 0, 0, g);
        ft.drawString(String.format("%5.2f€", tot), 0, 1, g);
        ft.drawRightString("Imponibile", 1, 0, g);
        ft.drawString(String.format("%5.2f€", tot - iva), 1, 1, g);
        ft.drawRightString("IVA 22%", 2, 0, g);
        ft.drawString(String.format("%5.2f€", iva), 2, 1, g);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        ft.drawRightString("Totale fattura", 3, 0, g);
        ft.drawString(String.format("%5.2f€", tot), 3, 1, g);
        ft.paint(g);

        resetDown(g, pf);
        backLine(g, pf);
        backLine(g, pf);
        backLine(g, pf);
        printCenteredLine("Firma", g, pf);
        printLine(g, pf);
        printCenteredLine("____________________________", g, pf);
    }

    private void printHeader(Customer customer, Graphics g, PageFormat pf) {
        g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        FormTable ft = new FormTable(new double[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10}, new double[]{62, 5, 33}, new Rectangle(getLeft(pf), getTop(pf), getRight(pf) - getLeft(pf), pts(80)));
        ft.drawString("Data: " + new SimpleDateFormat("dd/MM/yyyy").format(invoice.getInvoiceDate()), 0, 0, g);
        ft.drawString("AFFAREFATTO di Khalfallah Mohamed Lamine", 1, 0, g);
        ft.drawString("Dom.Fisc.: via della Chiesa di Uzzo n°6 - 51100 Pistoia", 2, 0, g);
        ft.drawString("Sede: Viale Europa 154 - 51039 Quarrata - PT", 3, 0, g);
        ft.drawString("CF: KHLMMD70D17Z352A", 4, 0, g);
        ft.drawString("P.IVA: 01884290477", 5, 0, g);
        ft.drawString("Spett.le", 5, 2, g);
        ft.drawString(customer.getSurname() + " " + customer.getName() + " (COD " + String.format("%03d", customer.getCode()) + ")", 6, 2, g);
        ft.drawString(customer.getAddress(), 7, 2, g);
        if (customer.getCAP() != null && customer.getCity() != null && customer.getDistrict() != null) {
            ft.drawString(customer.getCAP() + " " + customer.getCity() + " " + customer.getDistrict(), 8, 2, g);
        } else {
            ft.drawString("Provincia:", 8, 2, g);
        }
        ft.drawString("CF: " + customer.getCf().toUpperCase(), 9, 2, g);
    }

    private void printProduct(FormTable f, Product product, int i, Graphics g) {
        f.drawCenteredString(product.getQta() + "", i, 0, g);
        f.drawCenteredString(String.format("%05d", product.getDeliveryDocument()), i, 1, g);
        f.drawCenteredString(product.getName(), i, 2, g);
        f.drawCenteredString(String.format("%5.2f€", product.getPrice()), i, 3, g);
        f.drawCenteredString(product.getCommission() + "%", i, 4, g);
    }

}

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import salesmanager.beans.Customer;
import salesmanager.beans.DeliveryDocument;
import salesmanager.beans.Movement;
import salesmanager.beans.Product;

/**
 *
 * @author Lorenzo
 */
public class DeliveryDocumentForm extends DefaultForm {

    public static final int LINES_PER_PAGE = 17;

    private DeliveryDocument document;
    private ArrayList<ArrayList<Product>> pages;
    private Image imageLogo;

    public DeliveryDocumentForm(DeliveryDocument document) {
        super(1);
        this.document = document;
        pages = new ArrayList<>();
        int lines;
        Product[] products = document.getProducts();
        for (int i = 0; i < products.length;) {
            lines = 0;
            ArrayList<Product> pageProducts = new ArrayList<>();
            while (i < products.length && lines + lines(products[i]) < LINES_PER_PAGE) {
                lines += lines(products[i]);
                pageProducts.add(products[i]);
                i++;
            }
            if (!pageProducts.isEmpty()) {
                pages.add(pageProducts);
            }
        }
        setNPages(pages.size());
        setHMargin(10);
        setVMargin(10);
        imageLogo = null;
        try {
            imageLogo = ImageIO.read(new File("./files/logo/logo_xxs.png"));
        } catch (IOException ex) {
            Logger.getLogger(MultiDeliveryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int lines(Product p) {
        return 1 + p.movements().length;
    }

    @Override
    public void printPages(Graphics g, PageFormat pf, int pageIndex) {

        resetUp(g, pf);
        //printImage(Toolkit.getDefaultToolkit().getImage("./files/logo/logo_s.png"), 200, 67, g, pf);
        //printLogoImage(imageLogo, g, pf);
        printHeader(document.getCustomer(), g, pf);

        setLine(getTop(pf) + pts(90));
        g.setFont(new Font("Times New Roman", Font.BOLD, 18));
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(document.getDocumentDate());
        int year = gc.get(Calendar.YEAR);
        printCenteredLine("Contratto di mandato di vendita " + document.getProgressive() + "/" + year, g, pf);
        g.setFont(new Font("Times New Roman", Font.BOLD, 8));
        printLine("Il sottoscritto " + document.getCustomer().getSurname() + " " + document.getCustomer().getName() + " autorizza alla vendita dei beni sottodescritti alle seguenti condizioni.", g, pf);
        /*
        double[] rows = new double[pages.get(pageIndex).size() + 1];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = 100 / rows.length;
        }*/
        double top = getLine();
        
        g.setFont(new Font("Arial", Font.BOLD, 10));
        FormTable ft = new FormTable(new double[]{100}, new double[]{10, 15, 45, 15, 15}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), pts(8)));
        ft.drawCenteredString("Q.tà", 0, 0, g);
        ft.drawCenteredString("Nome", 0, 1, g);
        ft.drawCenteredString("Descrizione", 0, 2, g);
        ft.drawCenteredString("Prezzo pattuito", 0, 3, g);
        ft.drawCenteredString("Provvigione", 0, 4, g);
        g.setFont(new Font("Arial", Font.PLAIN, 9));
        top += pts(8);
        for (int i = 0; i < pages.get(pageIndex).size(); i++) {
            Product p = pages.get(pageIndex).get(i);
            printProduct(p, top, g, pf);
            top += lines(p) * pts(8);
        }
        ft.paint(g);
        

        resetDown(g, pf);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        backLine(g, pf);
        backLine(g, pf);
        backLine(g, pf);
        backLine(g, pf);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 10));
        printCenteredLine("Si ricorda che il prezzo pattuito dimezzerà dopo 60 giorni dalla data di consegna", g, pf);
        printLine(g, pf);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        printCenteredLine("Firma del cliente", g, pf);
        printLine(g, pf);
        printCenteredLine("_____________________________", g, pf);
        g.setFont(new Font("Arial", Font.PLAIN, 8));
        printRightLine("Pagina " + (pageIndex+1) + " di " + getNPages(), g, pf);

    }

    private void printProduct(Product p, double top, Graphics g, PageFormat pf) {
        FormTable ft = new FormTable(new double[]{100}, new double[]{10, 15, 45, 15, 15}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), pts(8)));
        ft.drawCenteredString("" + p.getQta(), 0, 0, g);
        ft.drawCenteredString(p.getName(), 0, 1, g);
        ft.drawCenteredString(p.getDescription(), 0, 2, g);
        ft.drawCenteredString(String.format("%5.2f€", p.getPrice()), 0, 3, g);
        ft.drawCenteredString(p.getCommission() + "%", 0, 4, g);
        ft.paint(g);
        top += pts(8);
        Movement[] mvs = p.movements();
        double[] rows = new double[mvs.length];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = 100.0 / rows.length;
        }
        ft = new FormTable(rows, new double[]{100}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), rows.length * pts(8)));

        for (int i = 0; i < mvs.length; i++) {
            ft.drawString(new SimpleDateFormat("dd/MM/yyyy").format(mvs[i].getOperationDate()) + " - " + mvs[i].getDescription(), i, 0, g);
        }
        ft.paint(g);
    }

    private void printHeader(Customer customer, Graphics g, PageFormat pf) {
        g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        FormTable ft = new FormTable(new double[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10}, new double[]{62, 5, 33}, new Rectangle(getLeft(pf), getTop(pf), getRight(pf) - getLeft(pf), pts(80)));
        ft.drawString("AFFAREFATTO di Khalfallah Mohamed Lamine", 0, 0, g);
        ft.drawString("Dom.Fisc.: via della Chiesa di Uzzo n°6 - 51100 Pistoia", 1, 0, g);
        ft.drawString("Sede: Viale Europa 154 - 51039 Quarrata - PT - " + new SimpleDateFormat("dd/MM/yyyy").format(document.getDocumentDate()), 2, 0, g);
        ft.drawString("CF: KHLMMD70D17Z352A", 3, 0, g);
        ft.drawString("P.IVA: 01884290477", 4, 0, g);
        ft.drawString("Spett.le", 5, 2, g);
        ft.drawString(customer.getSurname() + " " + customer.getName(), 6, 2, g);
        ft.drawString(customer.getAddress(), 7, 2, g);
        ft.drawString(customer.getCAP() + " " + customer.getCity() + " " + customer.getDistrict(), 8, 2, g);
        ft.drawString("CF: " + customer.getCf().toUpperCase(), 9, 2, g);
    }
}

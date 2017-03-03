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
import javax.swing.JOptionPane;
import salesmanager.beans.Customer;
import salesmanager.beans.Product;
import salesmanager.beans.dao.DBProductsManager;
import salesmanager.graphics.Main;
import static salesmanager.printable.DefaultForm.pts;

/**
 *
 * @author Lorenzo
 */
public class CustomerSummaryForm extends DefaultForm {

    public static final int LINES_PER_PAGE = 22;

    private Image imageLogo;
    private Customer customer;
    private Product[] onSale;
    private Product[] sold;
    private Product[] back;

    private int onSalePages;
    private int soldPages;
    private int backPages;

    public CustomerSummaryForm(Customer customer, Product[] onSale, Product[] sold, Product[] back) {
        super(1);
        this.customer = customer;
        this.onSale = onSale;
        this.sold = sold;
        this.back = back;
        imageLogo = null;
        try {
            imageLogo = ImageIO.read(new File("./files/logo/logo_xxs.png"));
        } catch (IOException ex) {
            Logger.getLogger(CustomerSummaryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        onSalePages += (onSale.length + LINES_PER_PAGE - 1) / LINES_PER_PAGE;
        soldPages += (sold.length + LINES_PER_PAGE - 1) / LINES_PER_PAGE;
        backPages += (back.length + LINES_PER_PAGE - 1) / LINES_PER_PAGE;
        setNPages(onSalePages + soldPages + backPages);
    }

    @Override
    public void printPages(Graphics g, PageFormat pf, int pageIndex) {
        printHeader(customer, g, pf);
        if (pageIndex < onSalePages) {
            printOnSaleSummary(pageIndex, g, pf);
        } else if (pageIndex < onSalePages + soldPages) {
            printSoldSummary(pageIndex - onSalePages, g, pf);
        } else {
            printBackSummary(pageIndex - onSalePages - soldPages, g, pf);
        }
        printFooter(pageIndex, g, pf);
    }

    private void printOnSaleSummary(int page, Graphics g, PageFormat pf) {
        setLine(getTop(pf) + pts(80));
        printCenteredLine("Riepilogo prodotti in negozio", g, pf);

        double top = getLine();

        g.setFont(new Font("Arial", Font.BOLD, 10));
        FormTable ft = new FormTable(new double[]{100}, new double[]{20, 35, 15, 15, 15}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), pts(8)));
        ft.drawCenteredString("Nome", 0, 0, g);
        ft.drawCenteredString("Descrizione", 0, 1, g);
        ft.drawCenteredString("Data di entrata", 0, 2, g);
        ft.drawCenteredString("Totale pattuito", 0, 3, g);
        ft.drawCenteredString("Provvigione", 0, 4, g);
        g.setFont(new Font("Arial", Font.PLAIN, 9));
        top += pts(8);
        ft = new FormTable(LINES_PER_PAGE, new double[]{20, 35, 15, 15, 15}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), LINES_PER_PAGE * pts(8)));
        for (int i = 0; i < LINES_PER_PAGE; i++) {
            if (page * LINES_PER_PAGE + i < onSale.length) {
                printOnSaleProduct(ft, onSale[page * LINES_PER_PAGE + i], i, g);
            }
            top += pts(8);
        }
        ft.paint(g);

    }

    private void printSoldSummary(int page, Graphics g, PageFormat pf) {
        setLine(getTop(pf) + pts(80));
        printCenteredLine("Riepilogo prodotti venduti", g, pf);

        double top = getLine();

        g.setFont(new Font("Arial", Font.BOLD, 10));
        FormTable ft = new FormTable(new double[]{100}, new double[]{20, 35, 15, 15, 10, 5}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), pts(8)));
        ft.drawCenteredString("Nome", 0, 0, g);
        ft.drawCenteredString("Descrizione", 0, 1, g);
        ft.drawCenteredString("Data di entrata", 0, 2, g);
        ft.drawCenteredString("Data di vendita", 0, 3, g);
        ft.drawCenteredString("Prezzo", 0, 4, g);
        ft.drawCenteredString("Provv.", 0, 5, g);
        g.setFont(new Font("Arial", Font.PLAIN, 9));
        top += pts(8);
        ft = new FormTable(LINES_PER_PAGE, new double[]{20, 35, 15, 15, 10, 5}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), LINES_PER_PAGE * pts(8)));
        for (int i = 0; i < LINES_PER_PAGE; i++) {
            if (page * LINES_PER_PAGE + i < sold.length) {
                printSoldProduct(ft, sold[page * LINES_PER_PAGE + i], i, g);
            }
            top += pts(8);
        }
        ft.paint(g);

    }

    private void printBackSummary(int page, Graphics g, PageFormat pf) {
        setLine(getTop(pf) + pts(80));
        printCenteredLine("Riepilogo prodotti restituiti", g, pf);

        double top = getLine();

        g.setFont(new Font("Arial", Font.BOLD, 10));
        FormTable ft = new FormTable(new double[]{100}, new double[]{20, 35, 15, 15, 10, 5}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), pts(8)));
        ft.drawCenteredString("Nome", 0, 0, g);
        ft.drawCenteredString("Descrizione", 0, 1, g);
        ft.drawCenteredString("Data di entrata", 0, 2, g);
        ft.drawCenteredString("Data di restituzione", 0, 3, g);
        ft.drawCenteredString("Prezzo", 0, 4, g);
        ft.drawCenteredString("Provv.", 0, 5, g);
        g.setFont(new Font("Arial", Font.PLAIN, 9));
        top += pts(8);
        ft = new FormTable(LINES_PER_PAGE, new double[]{20, 35, 15, 15, 10, 5}, new Rectangle(getLeft(pf), top, getRight(pf) - getLeft(pf), LINES_PER_PAGE * pts(8)));
        for (int i = 0; i < LINES_PER_PAGE; i++) {
            if (page * LINES_PER_PAGE + i < back.length) {
                printBackProduct(ft, back[page * LINES_PER_PAGE + i], i, g);
            }
            top += pts(8);
        }
        ft.paint(g);

    }

    private void printHeader(Customer customer, Graphics g, PageFormat pf) {
        resetUp(g, pf);

        g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        FormTable ft = new FormTable(new double[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10}, new double[]{62, 5, 33}, new Rectangle(getLeft(pf), getTop(pf), getRight(pf) - getLeft(pf), pts(80)));
        ft.drawString("Data: " + new SimpleDateFormat("dd/MM/yyyy").format(Main.today()), 0, 0, g);
        ft.drawString("AFFAREFATTO di Khalfallah Mohamed Lamine", 1, 0, g);
        ft.drawString("Dom.Fisc.: via della Chiesa di Uzzo n°6 - 51100 Pistoia", 2, 0, g);
        ft.drawString("Sede: Viale Europa 154 - 51039 Quarrata - PT", 3, 0, g);
        ft.drawString("CF: KHLMMD70D17Z352A", 4, 0, g);
        ft.drawString("P.IVA: 01884290477", 5, 0, g);

        int rowIndex = 5;
        ft.drawString("Spett.le", rowIndex++, 2, g);
        ft.drawString(customer.getSurname() + " " + customer.getName() + " (COD " + String.format("%03d", customer.getCode()) + ")", rowIndex++, 2, g);
        if (customer.getAddress() != null && !customer.getAddress().trim().isEmpty()) {
            ft.drawString(customer.getAddress(), rowIndex++, 2, g);
        }
        if (customer.getCAP() != null && customer.getCity() != null && customer.getDistrict() != null &&
                !customer.getCAP().trim().isEmpty() && !customer.getCity().trim().isEmpty() && !customer.getDistrict().trim().isEmpty()) {
            ft.drawString(customer.getCAP() + " " + customer.getCity() + " " + customer.getDistrict(), rowIndex++, 2, g);
        } 
        if(customer.getCf() != null && !customer.getCf().trim().isEmpty()){
            ft.drawString("CF: " + customer.getCf().toUpperCase(), rowIndex++, 2, g);
        }
    }

    private void printOnSaleProduct(FormTable f, Product product, int i, Graphics g) {
        f.drawCenteredString(product.getName(), i, 0, g);
        f.drawCenteredString(product.getDescription(), i, 1, g);
        try {
            f.drawCenteredString(new SimpleDateFormat("dd/MM/yyyy").format(DBProductsManager.getProductArrivalMovement(product).getOperationDate()), i, 2, g);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        f.drawCenteredString(String.format("%5.2f€", product.getPrice()), i, 3, g);
        f.drawCenteredString(product.getCommission() + "%", i, 4, g);
    }

    private void printSoldProduct(FormTable f, Product product, int i, Graphics g) {
        f.drawCenteredString(product.getName(), i, 0, g);
        f.drawCenteredString(product.getDescription(), i, 1, g);
        try {
            f.drawCenteredString(new SimpleDateFormat("dd/MM/yyyy").format(DBProductsManager.getProductArrivalMovement(product).getOperationDate()), i, 2, g);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        try {
            f.drawCenteredString(new SimpleDateFormat("dd/MM/yyyy").format(DBProductsManager.getProductSoldMovement(product).getOperationDate()), i, 3, g);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        f.drawCenteredString(String.format("%5.2f€", product.getPrice()), i, 4, g);
        f.drawCenteredString(product.getCommission() + "%", i, 5, g);
    }

    private void printBackProduct(FormTable f, Product product, int i, Graphics g) {
        f.drawCenteredString(product.getName(), i, 0, g);
        f.drawCenteredString(product.getDescription(), i, 1, g);
        try {
            f.drawCenteredString(new SimpleDateFormat("dd/MM/yyyy").format(DBProductsManager.getProductArrivalMovement(product).getOperationDate()), i, 2, g);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        try {
            f.drawCenteredString(new SimpleDateFormat("dd/MM/yyyy").format(DBProductsManager.getProductBackMovement(product).getOperationDate()), i, 3, g);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        f.drawCenteredString(String.format("%5.2f€", product.getPrice()), i, 4, g);
        f.drawCenteredString(product.getCommission() + "%", i, 5, g);
    }

    private void printFooter(int pageIndex, Graphics g, PageFormat pf) {
        resetDown(g, pf);
        backLine(g, pf);
        printRightLine("Pag " + (pageIndex + 1) + " di " + (onSalePages + soldPages + backPages), g, pf);
    }

}

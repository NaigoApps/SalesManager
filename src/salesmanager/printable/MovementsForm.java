/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.printable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JOptionPane;
import salesmanager.beans.RegisterMovement;
import salesmanager.beans.dao.DBRegisterManager;

/**
 *
 * @author Lorenzo
 */
public class MovementsForm extends DefaultForm {

    public static final int MOVEMENTS_PER_PAGE = 10;

    private int startPage;

    private RegisterMovement[] movements;

    public MovementsForm(RegisterMovement[] movements, int startPage) {
        super((movements.length / MOVEMENTS_PER_PAGE * MOVEMENTS_PER_PAGE == movements.length)
                ? movements.length / MOVEMENTS_PER_PAGE
                : movements.length / MOVEMENTS_PER_PAGE + 1);
        this.startPage = startPage;
        this.movements = movements;
        Arrays.sort(this.movements, new Comparator<RegisterMovement>() {

            @Override
            public int compare(RegisterMovement o1, RegisterMovement o2) {
                return Integer.compare(o1.getMovementProgressive(), o2.getMovementProgressive());
            }
        });
    }

    @Override
    public void printPages(Graphics g, PageFormat pf, int pageIndex) {
        ((Graphics2D) g).setStroke(new BasicStroke(1));
        int fromIndex = pageIndex * MOVEMENTS_PER_PAGE;
        int toIndex = Math.min((pageIndex + 1) * MOVEMENTS_PER_PAGE, movements.length);

        resetUp(g, pf);
        g.setFont(new Font("Arial", Font.PLAIN, 8));
        printLine("AFFAREFATTO DI KHALFALLAH MOHAMED LAMINE - CF: KHLMMD70D17Z352A - P.IVA: 01884290477 - VIALE EUROPA 154 - 51039 QUARRATA PT", g, pf);

        resetUp(g, pf);
        printRightLine("Pagina " + (startPage + pageIndex) + "/" + new SimpleDateFormat("MMMMMMMMM-yyyy").format(movements[0].getMovementDate()), g, pf);
        printLine(g, pf);
        printLine(g, pf);

        g.setFont(new Font("Arial", Font.BOLD, 12));
        printCenteredLine("Registro degli affari",g,pf);

        g.setFont(new Font("Arial", Font.BOLD, 8));

        double[] rows = new double[]{100};
        double[] cols1 = new double[]{
            10, 10, 10, 20, 5, 10, 10, 25
        };
        double[] cols2 = new double[]{
            5, 7, 7, 5, 10, 5, 10, 31, 6, 6, 8
        };

        double step = (getBot(pf) - getLine()) / (MOVEMENTS_PER_PAGE * 2 + 2) - 4;
        double yPos = getLine();

        FormTable ft1 = new FormTable(rows, cols1, new Rectangle(getLeft(pf), yPos, (getRight(pf) - getLeft(pf)), step));
        yPos += step;
        FormTable ft2 = new FormTable(rows, cols2, new Rectangle(getLeft(pf), yPos, (getRight(pf) - getLeft(pf)), step));

        ft1.paint(g);
        ft2.paint(g);

        g.setFont(new Font("Arial", Font.BOLD, 8));
        ft1.drawCenteredString("Cod. cliente", 0, 0, g);
        ft1.drawCenteredString("Cognome", 0, 1, g);
        ft1.drawCenteredString("Nome", 0, 2, g);
        ft1.drawCenteredString("Indirizzo", 0, 3, g);
        ft1.drawCenteredString("CAP", 0, 4, g);
        ft1.drawCenteredString("Città", 0, 5, g);
        ft1.drawCenteredString("Provincia", 0, 6, g);
        ft1.drawCenteredString("Documento", 0, 7, g);

        ft2.drawCenteredString("Prog.", 0, 0, g);
        ft2.drawCenteredString("Data", 0, 1, g);
        ft2.drawCenteredString("Oggetto", 0, 2, g);
        ft2.drawCenteredString("Q.tà", 0, 3, g);
        ft2.drawCenteredString("Prezzo pattuito", 0, 4, g);
        ft2.drawCenteredString("Provv.", 0, 5, g);
        ft2.drawCenteredString("Prezzo percepito", 0, 6, g);
        ft2.drawCenteredString("Natura dell'operazione", 0, 7, g);
        ft2.drawCenteredString("Uscite", 0, 8, g);
        ft2.drawCenteredString("Entrate", 0, 9, g);
        ft2.drawCenteredString("Totale", 0, 10, g);

        for (int i = fromIndex; i < toIndex; i++) {
            yPos += step + 4;
            ft1 = new FormTable(rows, cols1, new Rectangle(getLeft(pf), yPos, getRight(pf) - getLeft(pf), step));
            ft1.highlightRow(0);
            yPos += step;
            ft2 = new FormTable(rows, cols2, new Rectangle(getLeft(pf), yPos, getRight(pf) - getLeft(pf), step));
            ft1.paint(g);
            ft2.paint(g);
            printMovement(ft1, ft2, movements[i], g);
        }
        yPos += step + 4;

        try {
            ft1 = new FormTable(new double[]{100}, new double[]{30, 20, 30, 20}, new Rectangle(getRight(pf) - pts(100), yPos, pts(100), step));

            g.setFont(new Font("Arial", Font.BOLD, 8));
            ft1.drawCenteredString("Totale iniziale", 0, 0, g);
            ft1.drawCenteredString("Totale finale", 0, 2, g);

            g.setFont(new Font("Arial", Font.PLAIN, 8));
            ft1.drawString(String.format("%5.2f€", DBRegisterManager.getBalanceUntil(movements[fromIndex])), 0, 1, g);
            ft1.drawString(String.format("%5.2f€", movements[toIndex - 1].getTotalGoods()), 0, 3, g);
            ft1.paint(g);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Errore di stampa", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printMovement(FormTable ft1, FormTable ft2, RegisterMovement m, Graphics g) {
        g.setColor(Color.BLACK);

        g.setFont(new Font("Times New Roman", Font.PLAIN, 8));
        if (m != null) {
            ft1.drawCenteredString(String.format("%06d", m.getCustomerCode()), 0, 0, g);
            ft1.drawCenteredString(m.getCustomerSurname(), 0, 1, g);
            ft1.drawCenteredString(m.getCustomerName(), 0, 2, g);
            ft1.drawCenteredString(m.getCustomerAddress(), 0, 3, g);
            if (m.getCustomerCap() != null) {
                ft1.drawCenteredString(m.getCustomerCap(), 0, 4, g);
            }
            if (m.getCustomerCity() != null) {
                ft1.drawCenteredString(m.getCustomerCity(), 0, 5, g);
            }
            if (m.getCustomerDistrict() != null) {
                ft1.drawCenteredString(m.getCustomerDistrict(), 0, 6, g);
            }
            if (m.getCustomerDocument() != null && !m.getCustomerDocument().isEmpty()) {
                if (m.getCustomerDocumentRelease() != null) {
                    ft1.drawCenteredString(m.getCustomerDocument() + " rilasciato in data " + m.getCustomerDocumentRelease(), 0, 7, g);
                } else {
                    ft1.drawCenteredString(m.getCustomerDocument(), 0, 7, g);
                }
            }

            ft2.drawString(m.getMovementDescription(), 0, 7, g);
            ft2.drawCenteredString(String.format("%05d", m.getMovementProgressive()), 0, 0, g);
            ft2.drawCenteredString(new SimpleDateFormat("dd/MM/yyyy").format(m.getMovementDate()), 0, 1, g);
            ft2.drawString(m.getProductName(), 0, 2, g);
            ft2.drawString(m.getProductQta() + "", 0, 3, g);
            ft2.drawString(String.format("%5.2f", m.getProductPrice()) + "€", 0, 4, g);
            ft2.drawCenteredString(m.getProductCommission() + "%", 0, 5, g);
            if (m.getMovementDescription().contains("mandato")) {
                ft2.drawString(String.format("%5.2f", m.getProductPrice() * m.getProductCommission() / 100) + "€", 0, 6, g);
            } else {
                ft2.drawCenteredString("/", 0, 6, g);
            }

            if (m.getMovementLoadVar() < 0) {
                g.setColor(Color.RED);
                ft2.drawString(String.format("%5.2f", m.getMovementLoadVar()) + "€", 0, 8, g);
            } else {
                g.setColor(Color.BLACK);
                ft2.drawString("+" + String.format("%5.2f", m.getMovementLoadVar()) + "€", 0, 9, g);
            }

            ft2.drawString(String.format("%5.2f", m.getTotalGoods()) + "€", 0, 10, g);

        }
    }

}

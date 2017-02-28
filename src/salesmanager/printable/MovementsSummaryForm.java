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
import salesmanager.beans.DeliveryDocument;
import salesmanager.beans.Movement;
import salesmanager.beans.Product;
import salesmanager.beans.dao.DBDeliveryManager;
import salesmanager.beans.dao.DBProductsManager;

/**
 *
 * @author Lorenzo
 */
public class MovementsSummaryForm extends DefaultForm {

    public static final int MOVEMENTS_PER_PAGE = 25;

    private int startPage;

    private Movement[] movements;

    public MovementsSummaryForm(Movement[] movements, int startPage) {
        super((movements.length / MOVEMENTS_PER_PAGE * MOVEMENTS_PER_PAGE == movements.length)
                ? movements.length / MOVEMENTS_PER_PAGE
                : movements.length / MOVEMENTS_PER_PAGE + 1);
        this.startPage = startPage;
        this.movements = movements;
        Arrays.sort(this.movements, new Comparator<Movement>() {

            @Override
            public int compare(Movement o1, Movement o2) {
                return Integer.compare(o1.getCode(), o2.getCode());
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
        printRightLine("Pagina " + (startPage + pageIndex) + "/" + new SimpleDateFormat("dd-MM-yyyy").format(movements[0].getOperationDate()), g, pf);
        printLine(g, pf);
        printLine(g, pf);

        g.setFont(new Font("Arial", Font.BOLD, 12));
        printCenteredLine("Riepilogo giornaliero", g, pf);

        g.setFont(new Font("Arial", Font.BOLD, 8));

        double[] rows = new double[]{100};
        double[] cols1 = new double[]{
            10, 10, 10, 25, 4, 10, 8, 8, 15
        };

        double step = (getBot(pf) - getLine()) / (MOVEMENTS_PER_PAGE + 2);
        double yPos = getLine();

        FormTable ft1 = new FormTable(rows, cols1, new Rectangle(getLeft(pf), yPos, (getRight(pf) - getLeft(pf)), step));

        ft1.paint(g);

        g.setFont(new Font("Arial", Font.BOLD, 8));
        ft1.drawCenteredString("Cognome", 0, 0, g);
        ft1.drawCenteredString("Nome", 0, 1, g);
        ft1.drawCenteredString("Cod. Oggetto", 0, 2, g);
        ft1.drawCenteredString("Nome Oggetto", 0, 3, g);
        ft1.drawCenteredString("Q.tà", 0, 4, g);
        ft1.drawCenteredString("Prezzo pattuito", 0, 5, g);
        ft1.drawCenteredString("Provv.", 0, 6, g);
        ft1.drawCenteredString("Variazione", 0, 7, g);
        ft1.drawCenteredString("Natura dell'operazione", 0, 8, g);

        try {
            for (int i = fromIndex; i < toIndex; i++) {
                yPos += step;
                ft1 = new FormTable(rows, cols1, new Rectangle(getLeft(pf), yPos, getRight(pf) - getLeft(pf), step));
                if (movements[i].getLoadVar() > 0) {
                    ft1.highlightRow(0);
                }
                ft1.paint(g);
                printMovement(ft1, movements[i], g);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Errore di stampa", JOptionPane.ERROR_MESSAGE);
        }
        yPos += step + 4;

        ft1 = new FormTable(new double[]{100}, new double[]{30, 20, 30, 20}, new Rectangle(getRight(pf) - pts(100), yPos, pts(100), step));

        g.setFont(new Font("Arial", Font.BOLD, 8));
        ft1.drawCenteredString("Totale entrate", 0, 0, g);
        ft1.drawCenteredString("Totale uscite", 0, 2, g);

        g.setFont(new Font("Arial", Font.PLAIN, 8));
        float totIn = 0, totOut = 0;
        for (Movement movement : movements) {
            if (movement.getLoadVar() > 0) {
                totIn += movement.getLoadVar();
            } else {
                totOut += -movement.getLoadVar();
            }
        }
        ft1.drawString(String.format("%5.2f€", totIn), 0, 1, g);
        ft1.drawString(String.format("%5.2f€", totOut), 0, 3, g);
        ft1.paint(g);
    }

    private void printMovement(FormTable ft1, Movement m, Graphics g) throws SQLException {
        g.setColor(Color.BLACK);

        g.setFont(new Font("Times New Roman", Font.PLAIN, 8));
        if (m != null) {
            Product p = DBProductsManager.getProduct(m.getProduct());
            DeliveryDocument dd = DBDeliveryManager.getProductDeliveryDocument(p);
            ft1.drawCenteredString(dd.getCustomer().getSurname(), 0, 0, g);
            ft1.drawCenteredString(dd.getCustomer().getName(), 0, 1, g);
            ft1.drawCenteredString("" + p.getCode(), 0, 2, g);
            ft1.drawCenteredString(p.getName(), 0, 3, g);
            ft1.drawCenteredString("" + p.getQta(), 0, 4, g);
            ft1.drawCenteredString(String.format("%5.2f€", p.getPrice()), 0, 5, g);
            ft1.drawCenteredString(p.getCommission() + "%", 0, 6, g);
            ft1.drawCenteredString(String.format("%5.2f€", m.getLoadVar()), 0, 7, g);
            ft1.drawCenteredString(m.getDescription(), 0, 8, g);

        }
    }

}

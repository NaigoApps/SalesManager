/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.printable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Lorenzo
 */
public class FormTable {

    private double rows[];
    private double cols[];
    private boolean highlightedRows[];
    private Rectangle bounds;

    public FormTable(double[] rows, double[] cols, Rectangle bounds) {
        this.rows = rows;
        this.highlightedRows = new boolean[rows.length];
        this.cols = cols;
        this.bounds = bounds;
    }

    public FormTable(int r, double[] cols, Rectangle bounds) {
        double[] newRows = new double[r];
        for (int i = 0; i < newRows.length; i++) {
            newRows[i] = 100.0 / r;
        }
        this.highlightedRows = new boolean[newRows.length];
        this.rows = newRows;
        this.cols = cols;
        this.bounds = bounds;
    }

    public void paint(Graphics g) {
        double sum = 0;
        g.setColor(Color.GRAY);
        //g.drawLine((int) bounds.x,(int)  bounds.y,(int)  (bounds.x + bounds.width),(int)  bounds.y);

        for (int i = 0; i < rows.length; i++) {
            if (highlightedRows[i]) {
                g.setColor(new Color(220, 220, 220));
                g.fillRect((int) bounds.x, (int) (bounds.y + sum),(int)  bounds.width, (int) (rows[i] * bounds.height / 100));
            }
            g.setColor(Color.GRAY);
            g.drawLine((int) bounds.x, (int) (bounds.y + sum),(int) ( bounds.x + bounds.width), (int) (bounds.y + sum));
            sum += rows[i] * bounds.height / 100;
            g.drawLine((int) bounds.x, (int) (bounds.y + sum),(int) ( bounds.x + bounds.width), (int) (bounds.y + sum));

        }
        //g.drawLine((int) bounds.x, (int)(bounds.y + bounds.height), (int)(bounds.x + bounds.width), (int)(bounds.y + bounds.height));

        sum = 0;
        g.drawLine((int)bounds.x,(int) bounds.y,(int) bounds.x,(int) (bounds.y + bounds.height));
        for (int i = 0; i < cols.length - 1; i++) {
            sum += cols[i] * bounds.width / 100;
            g.drawLine((int) (bounds.x + sum), (int)bounds.y, (int) (bounds.x + sum),(int)( bounds.y + bounds.height));
        }
        g.drawLine((int)(bounds.x + bounds.width), (int)bounds.y, (int)(bounds.x + bounds.width),(int) (bounds.y + bounds.height));
    }

    public void drawCenteredString(String s, int i, int j, Graphics g) {
        double x = 0, y = 0;
        g.setColor(Color.BLACK);
        for (int k = 0; k < i; k++) {
            y += rows[k] * bounds.height / 100;
        }

        for (int k = 0; k < j; k++) {
            x += cols[k] * bounds.width / 100;
        }
        double length = ((Graphics2D) g).getFontMetrics().getStringBounds(s, g).getWidth();
        double height = ((Graphics2D) g).getFontMetrics().getStringBounds(s, g).getHeight();
        g.drawString(s,
                (int) (bounds.x + x + (cols[j] * bounds.width / 100 - length) / 2),
                (int) (bounds.y + y + (rows[i] * bounds.height / 100 + height) / 2));
    }
    
    public void drawRightString(String s, int i, int j, Graphics g) {
        double x = 0, y = 0;
        g.setColor(Color.BLACK);
        for (int k = 0; k < i; k++) {
            y += rows[k] * bounds.height / 100;
        }

        for (int k = 0; k < j; k++) {
            x += cols[k] * bounds.width / 100;
        }
        double length = ((Graphics2D) g).getFontMetrics().getStringBounds(s, g).getWidth();
        double height = ((Graphics2D) g).getFontMetrics().getStringBounds(s, g).getHeight();
        g.drawString(s,
                (int) (bounds.x + x + cols[j] * bounds.width / 100 - length - 2),
                (int) (bounds.y + y + (rows[i] * bounds.height / 100 + height) / 2));
    }

    public void drawString(String s, int i, int j, Graphics g) {
        double x = 0, y = 0;
        g.setColor(Color.BLACK);
        for (int k = 0; k < i; k++) {
            y += rows[k] * bounds.height / 100;
        }

        for (int k = 0; k < j; k++) {
            x += cols[k] * bounds.width / 100;
        }
        double height = ((Graphics2D) g).getFontMetrics().getStringBounds(s, g).getHeight();
        double length = ((Graphics2D) g).getFontMetrics().getStringBounds(s, g).getWidth();
        int times = (int) (length / (cols[j] * bounds.width / 100)) + 1;
        for (int k = 0; k < times; k++) {
            
            g.drawString(s.substring(k * s.length() / times, Math.min((k + 1) * s.length() / times, s.length())),
                    (int) (bounds.x + x + 2),
                    (int) (bounds.y + y + (rows[i] * bounds.height / 100 + height) / 2 / times * (k + 1)));
        }
        /*g.drawString(s,
         (int) (bounds.x + x + 2),
         (int) (bounds.y + y + (rows[i] * bounds.height / 100 + height) / 2));*/
    }

    public void highlightRow(int i) {
        highlightedRows[i] = true;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.printable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 *
 * @author Lorenzo
 */
public abstract class DefaultForm implements Printable {

    private int pages;
    private double line;

    private double vMargin;
    private double hMargin;

    public DefaultForm(int nPages) {
        this.pages = nPages;
        this.vMargin = 1;
        this.hMargin = 1;
    }

    public void setNPages(int pages) {
        this.pages = pages;
    }

    public int getNPages() {
        return pages;
    }

    @Override
    public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex < pages) {
            ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            printPages(graphics, pf, pageIndex);
            return PAGE_EXISTS;
        }
        return NO_SUCH_PAGE;
    }

    /**
     * Image won't be resized
     *
     * @param img
     * @param w Indicative width
     * @param h Indicative height
     * @param g
     * @param pf
     */
    public void printCenteredImage(Image img, int w, int h, Graphics g, PageFormat pf) {
        line = Math.max(line, getTop(pf));
        g.drawImage(img, (int) ((pf.getImageableWidth() - w) / 2 + getLeft(pf)), (int) line, null);
        line += h;
    }

    public final void printLine(Graphics g, PageFormat pf) {
        printLine(0, "", g, pf);
    }
    
    public final void printLine(double x, String text, Graphics g, PageFormat pf) {
        g.setColor(Color.BLACK);
        line = Math.max(line, getTop(pf));
        double lineHeight = g.getFontMetrics().getHeight();
        g.drawString(text, (int) x, (int) line);
        line += lineHeight;
    }
    

    public final void printLine(String text, Graphics g, PageFormat pf) {
        printLine(getLeft(pf), text, g, pf);
    }

    public final void printCenteredLine(String text, Graphics g, PageFormat pf) {
        double textSize = g.getFontMetrics().getStringBounds(text, g).getWidth();
        printLine((getRight(pf) - getLeft(pf) - textSize) / 2 + getLeft(pf), text, g, pf);
    }
    
    public final void printRightLine(String text, Graphics g, PageFormat pf) {
        double textSize = g.getFontMetrics().getStringBounds(text, g).getWidth();
        printLine(getRight(pf) - textSize, text, g, pf);
    }

    public final void printFootNote(String text, Graphics g, PageFormat pf) {
        line = pf.getHeight() - getTop(pf);
        printCenteredLine(text, g, pf);
    }

    public final void backLine(Graphics g, PageFormat pf) {
        double lineHeight = g.getFontMetrics().getHeight();
        line -= lineHeight;
    }

    public final static double cm(double pts) {
        return pts * 127 / 360;
    }

    public final static double pts(double mm) {
        return mm / 127 * 360;
    }

    public final double getLine() {
        return line;
    }

    public void setLine(double line) {
        this.line = line;
    }
    
    

    public final double getLeft(PageFormat pf) {
        return (pf.getWidth() - pf.getImageableWidth()) / 2 + pts(hMargin);
    }

    public final double getTop(PageFormat pf) {
        return (pf.getHeight() - pf.getImageableHeight()) / 2 + pts(vMargin);
    }
    
    
    public final double getRight(PageFormat pf){
        return pf.getWidth() - getLeft(pf);
    }
    
    
    public final double getBot(PageFormat pf){
        return pf.getHeight()- getTop(pf);
    }

    public void setHMargin(double mm) {
        this.hMargin = mm;
    }

    public void setVMargin(double mm) {
        this.vMargin = mm;
    }

    public final void resetUp(Graphics g, PageFormat pf) {
        line = getTop(pf) + g.getFontMetrics().getHeight();
    }

    public final void resetDown(Graphics g, PageFormat pf) {
        line = getBot(pf);
    }

    public abstract void printPages(Graphics g, PageFormat pf, int pageIndex);

}

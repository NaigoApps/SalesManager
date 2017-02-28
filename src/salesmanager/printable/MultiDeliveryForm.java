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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Lorenzo
 */
public class MultiDeliveryForm extends DefaultForm{

    private Image imageLogo;
  
    public MultiDeliveryForm() {
        super(1);
        imageLogo = null;
        try {
            imageLogo = ImageIO.read(new File("./files/logo/logo_xxs.png"));
        } catch (IOException ex) {
            Logger.getLogger(MultiDeliveryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void printPages(Graphics g, PageFormat pf, int pageIndex) {
        resetUp(g, pf);
        printCenteredLine("Documento di consegna", g, pf);
        printLine(g, pf);
        g.setFont(new Font("Courier New", Font.PLAIN, 10));

        printLine(getLeft(pf), "Data:",g,pf);
        printLine(getLeft(pf), "Dati cliente:",g,pf);
        printLine(g, pf);
        printLine(g, pf);
        printLine(g, pf);
        printLine(g, pf);
        

        FormTable f = new FormTable(
                new double[]{10,10,10,10,10,10,10,10,10,10},
                new double[]{76, 8, 8, 8},
                new Rectangle(getLeft(pf),getLine(), getRight(pf) - getLeft(pf), (pts(170))));
        
        f.drawCenteredString("Oggetto", 0, 0, g);
        f.drawCenteredString("Q.tà", 0, 1, g);
        f.drawCenteredString("Prezzo", 0, 2, g);
        f.drawCenteredString("%", 0, 3, g);
        f.paint(g);
        
        g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        resetDown(g, pf);
        backLine(g, pf);
        backLine(g, pf);
        backLine(g, pf);
        printCenteredLine("Firma del cliente", g, pf);
        printLine(g, pf);
        printCenteredLine("_____________________________", g, pf);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.dialogs;

import java.awt.Font;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import salesmanager.beans.Movement;
import salesmanager.graphics.dialogs.internal.MovementInternalPanel;

/**
 *
 * @author Lorenzo
 */
public class MovementDialog extends OkCancelDialog{
    
    private MovementInternalPanel pnMovement;
    private Movement movement;
    
    public MovementDialog(JFrame parent, Movement m) {
        super(parent,true);
        pnMovement.setMovement(m);
        pack();
        setLocationRelativeTo(null);
    }
    
    @Override
    protected JPanel createNorthPanel() {
        JPanel pnNorth = new JPanel();
        JLabel l = new JLabel("Dettagli movimento");
        l.setFont(new Font(null, Font.BOLD, 20));
        pnNorth.add(l);
        return pnNorth;
    }

    @Override
    protected JPanel createCenterPanel() {
        try {
            pnMovement = new MovementInternalPanel();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Errore durante la lettura dei movimenti",JOptionPane.ERROR_MESSAGE);
        }
        
        return pnMovement;
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
        if(pnMovement.updateData()){
            movement = pnMovement.getMovement();
            dispose();
        }else{
            movement = null;
        }
    }

    @Override
    protected void onCancel() {
        movement = null;
        dispose();
    }

    public Movement getMovement() {
        return movement;
    }
    
    
}

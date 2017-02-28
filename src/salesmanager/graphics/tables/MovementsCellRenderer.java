/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.tables;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import salesmanager.beans.Movement;

/**
 *
 * @author Lorenzo
 */
public class MovementsCellRenderer extends DefaultListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(value instanceof Movement && ((Movement)value).getProgressive() == -1){
            c.setBackground(new Color(255, 255, 0, 100));
        }else{
            c.setBackground(new Color(0, 255, 0, 100));
        }
        return c;
    }

    
    
}

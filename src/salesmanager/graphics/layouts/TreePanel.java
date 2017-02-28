/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.layouts;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Lorenzo
 */
public class TreePanel extends JPanel{

    private JPanel pnCenter;
    private JPanel pnWest;
    private JPanel pnEast;
    private TreeLayoutManager layout;
    
    private JButton btnBack;
    private JButton[] btnNext;
    
    public TreePanel() {
        setLayout(new BorderLayout(10,10));
        layout = new TreeLayoutManager();
        pnCenter = new JPanel(layout);
        //pnWest = new 
    }

    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        pnCenter.add(comp);
    }
    
    
    
    public void up(){
        layout.upBuild();
    }
    
    public void resetView(){
        layout.resetView();
    }
    
    public void showParent(){
        layout.upView();
        reloadButtons();
    }
    
    public void showChild(int i){
        layout.nextView(i);
        reloadButtons();
    }

    private void reloadButtons() {
        
    }
}

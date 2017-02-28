/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Lorenzo
 */
public class TreeLayoutManager implements LayoutManager {

    private TreeComponent root;

    private TreeComponent currentBuild;

    private TreeComponent currentView;

    public TreeLayoutManager() {
        root = TreeComponent.NULL;
        currentBuild = root;
        currentView = root;
    }

    public void resetView(){
        currentView = root;
    }
    
    public void resetBuild(){
        currentBuild = root;
    }
    
    @Override
    public void addLayoutComponent(String name, Component comp) {
        TreeComponent incoming = new TreeComponent(currentBuild, comp, name);
        if (currentBuild != TreeComponent.NULL) {
            currentBuild.addChild(incoming);
        } else {
            root = incoming;
        }
        currentBuild = incoming;
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        if (currentView.getComponent() != null) {
            return parent.getPreferredSize();
        }
        return new Dimension();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        TreeComponent tc = null;
        for (Iterator<TreeComponent> it = root.iterator(); it.hasNext();) {
            tc = it.next();
            if (tc == currentView) {
                tc.getComponent().setLocation(0, 0);
                tc.getComponent().setSize(parent.getSize());
            }else{
                tc.getComponent().setBounds(0,0,0,0);
            }
        }
    }

    void upBuild() {
        if(currentBuild != root){
            currentBuild = currentBuild.getParent();
        }
    }
    
    void upView() {
        if(currentView != root){
            currentView = currentView.getParent();
        }
    }

    public void nextView(int i) {
        if(i >= 0 && i < currentView.getChildrenNumber()){
            currentView = currentView.getChild(i);
        }
    }
}

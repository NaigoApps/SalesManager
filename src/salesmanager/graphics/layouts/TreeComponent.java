/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.layouts;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Lorenzo
 */
public class TreeComponent implements Iterable<TreeComponent>{
    private Component component;
    private TreeComponent parent;
    private String name;
    private ArrayList<TreeComponent> children;
    
    public static final TreeComponent NULL = new TreeComponent(null);
    
    public TreeComponent(Component data){
        this(null,data,null);
    }
    
    public TreeComponent(TreeComponent parent, Component data, String name){
        this.parent = parent;
        this.component = data;
        this.name = name;
        this.children = new ArrayList<>();
    }
    
    public TreeComponent getParent(){
        return parent;
    }
    
    public void addChild(TreeComponent child){
        this.children.add(child);
    }
    
    public TreeComponent getChild(int i){
        if(i >= 0 && i < children.size()){
            return children.get(i);
        }
        return null;
    }
    
    public int getChildrenNumber(){
        return children.size();
    }

    public Component getComponent() {
        return component;
    }

    public String getName() {
        return name;
    }

    @Override
    public Iterator<TreeComponent> iterator() {
        return new TreeComponentIterator(this);
    }
    
    
}

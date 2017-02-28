/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.layouts;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Lorenzo
 */
class TreeComponentIterator implements Iterator<TreeComponent> {

    private TreeComponent base;
    private ArrayList<TreeComponent> tree;
    private int index;
    
    public TreeComponentIterator(TreeComponent component) {
        this.base = component;
        tree = new ArrayList<>();
        fillTree(base);
        index = 0;
    }
    
    private void fillTree(TreeComponent tc){
        tree.add(tc);
        for (int i = 0; i < tc.getChildrenNumber(); i++) {
            fillTree(tc.getChild(i));
        }
    }

    @Override
    public boolean hasNext() {
        return index < tree.size();
    }

    @Override
    public TreeComponent next() {
        TreeComponent ret = null;
        if(hasNext()){
            ret = tree.get(index);
            index++;
        }
        return ret;
    }

    @Override
    public void remove() {
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.components;

import java.awt.Frame;
import javax.swing.JOptionPane;
import salesmanager.graphics.dialogs.OkCancelDialog;

/**
 *
 * @author Lorenzo
 */
public abstract class OkCancelBeanDialog<T> extends OkCancelDialog {

    private T bean;
    
    public OkCancelBeanDialog(Frame parent) {
        super(parent, true);
        bean = null;
    }

    @Override
    protected final void onOk() {
        T bean = createBean();
        if (isValid(bean)) {
            this.bean = bean;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, getErrorMessage(bean), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public T getBean(){
        return bean;
    }

    protected abstract T createBean();
    public abstract boolean isValid(T bean);
    public abstract String getErrorMessage(T bean);

    @Override
    protected final void onCancel() {
        this.bean = null;
        dispose();
    }

}

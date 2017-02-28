/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.components.datepicker;

import java.awt.Frame;
import java.util.Date;
import javax.swing.JPanel;
import salesmanager.components.OkCancelBeanDialog;

/**
 *
 * @author Lorenzo
 */
public class DatePickerDialog extends OkCancelBeanDialog<Date>{

    private DatePicker datePicker;
    
    public DatePickerDialog(Frame parent) {
        super(parent);
        pack();
        setLocationRelativeTo(null);
    }
    
    @Override
    protected JPanel createNorthPanel() {
        return new JPanel();
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
    protected JPanel createCenterPanel() {
        datePicker = new DatePicker();
        return datePicker;
    }

    @Override
    public boolean isValid(Date bean) {
        return bean != null;
    }

    @Override
    public String getErrorMessage(Date bean) {
        if(bean == null){
            return "Inserire una data valida";
        }
        return "Errore sconosciuto";
    }

    @Override
    public Date createBean() {
        return datePicker.getDate();
    }
    
}

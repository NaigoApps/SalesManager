/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import salesmanager.beans.dao.DBCustomersManager;

/**
 *
 * @author Lorenzo
 */
public class Invoice {

    public static final int MAX_PRODUCTS = 15;
    
    private int code;
    private int progressive;
    private Customer customer;
    private Date invoiceDate;

    public Invoice() {
        this.progressive = -1;
    }

    public Invoice(Customer customer, Date invoiceDate) {
        this.code = -1;
        this.progressive = -1;
        this.customer = customer;
        this.invoiceDate = invoiceDate;
    }

    public Invoice(Invoice i) {
        BeansUtil.copyOf(i, this);
    }

    public int getCode() {
        return code;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }
    
    public void setCustomer(int customer) throws SQLException{
        this.customer = DBCustomersManager.getCustomer(customer);
    }
    
    public int getCustomer(){
        return customer.getCode();
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getProgressive() {
        return progressive;
    }

    public void setProgressive(int progressive) {
        this.progressive = progressive;
    }

    @Override
    public String toString() {
        return customer.getSurname() + " " + customer.getName() + " - " + new SimpleDateFormat("dd/MM/yyyy").format(invoiceDate);
    }
    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import salesmanager.beans.dao.DBCustomersManager;
import salesmanager.beans.dao.DBProductsManager;

/**
 *
 * @author Lorenzo
 */
public class DeliveryDocument {
    private int code;
    private Customer customer;
    private int progressive;
    private Date documentDate;
    private ArrayList<Product> products;

    public DeliveryDocument(Customer customer,int progressive, Date documentDate, Product[] products){
        this.code = -1;
        this.customer = customer;
        this.progressive = progressive;
        this.documentDate = documentDate;
        this.products = new ArrayList<>(Arrays.asList(products));
    }

    public DeliveryDocument() {
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    public void loadProducts() throws SQLException{
        products = new ArrayList<>();
        products.addAll(Arrays.asList(DBProductsManager.getFromDeliveryDocumentProducts(code)));
        for (int i = 0; i < products.size(); i++) {
            products.get(i).loadMovements();
        }
    }

    public int getCode() {
        return code;
    }

    public void setProgressive(int progressive) {
        this.progressive = progressive;
    }

    public int getProgressive() {
        return progressive;
    }
    
    

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }
    
    public void setCustomer(int customer) throws SQLException{
        this.customer = DBCustomersManager.getCustomer(customer);
    }
    
    public void setCustomer(Customer c){
        this.customer = c;
    }

    public Customer getCustomer() {
        return customer;
    }
    
    public Product[] getProducts(){
        return products.toArray(new Product[products.size()]);
    }
    
    public void addProduct(Product p){
        products.add(p);
    }
    
}

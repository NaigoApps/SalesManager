/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import salesmanager.beans.dao.DBProductsManager;

/**
 *
 * @author Lorenzo
 */
public class Product {

    public static String dateToString(Date d) {
        return new SimpleDateFormat("dd/MM/yyyy").format(d);
    }
    public static Date stringToDate(String d) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(d);
    }
    
    private int code;
    private String name;
    private String description;
    private float price;
    private int qta;
    private int commission;
    private int invoice;
    private int deliveryDocument;
    
    private Movement[] movements;

    public Product() {
        invoice = -1;
        movements = null;
    }

    public Product(String name, String description, float price, int qta, int commission, int deliveryDocument, int invoice) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.qta = qta;
        this.commission = commission;
        this.movements = null;
        this.deliveryDocument = deliveryDocument;
        this.invoice = invoice;
    }

    
    public Product(Product p) {
        if (p != null) {
            BeansUtil.copyOf(p, this);
        }
        movements = null;
    }

    public void setDeliveryDocument(int deliveryDocument) {
        this.deliveryDocument = deliveryDocument;
    }

    public int getDeliveryDocument() {
        return deliveryDocument;
    }

    
    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }
    
    

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQta() {
        return qta;
    }

    public void setQta(int qta) {
        this.qta = qta;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            return ((Product) obj).getCode() == code;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%04d", code) + " - " + name + " - " + String.format("%5.2f€", price);
    }
    
    public Movement[] movements() {
        return movements;
    }
    
    public void loadMovements() throws SQLException{
        movements = DBProductsManager.getMovements(this);
        Arrays.sort(movements);
    }
    
    public void clearMovements(){
        movements = null;
    }
    
    public void addMovement(Movement m){
        Movement[] extMovements;
        if(movements != null){
            extMovements = new Movement[movements.length + 1];
            System.arraycopy(movements, 0, extMovements, 0, movements.length);
        }else{
            extMovements = new Movement[1];
        }
        extMovements[extMovements.length-1] = m;
        movements = extMovements;
    }
    
    public boolean isSold(){
        for (Movement movement : movements) {
            if (movement.getCausal().equals(Movement.CPM)) {
                return true;
            }
        }
        return false;
    }
    public boolean isBack(){
        for (Movement movement : movements) {
            if (movement.getCausal().equals(Movement.RES)) {
                return true;
            }
        }
        return false;
    }
}

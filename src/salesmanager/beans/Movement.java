/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans;

import java.util.Date;

/**
 *
 * @author Lorenzo
 */
public class Movement implements Comparable<Movement>{
    public static final String PCV = "PCV";
    public static final String CPM = "CPM";
    public static final String RES = "RES";
    public static final String VAR = "VAR";
    public static final String EXP = "EXP";
    
    private int code;
    private int progressive;
    private int product;
    private String causal;
    private String description;
    private Date operationDate;
    private float loadVar;
    
    private float price;
    private int commission;

    public Movement() {
        progressive = -1;
    }

    
    public Movement(Movement m) {
        if (m != null) {
            BeansUtil.copyOf(m, this);
        }
    }

    public Movement(int product, String causal, String description, Date date,float price,int commission, float loadVar) {
        this.code = -1;
        this.progressive = -1;
        this.product = product;
        this.causal = causal;
        this.description = description;
        this.operationDate = date;
        this.loadVar = loadVar;
        this.commission = commission;
        this.price = price;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int id) {
        this.code = id;
    }

    public void setProgressive(int realProgressive) {
        this.progressive = realProgressive;
    }

    public int getProgressive() {
        return progressive;
    }
    
    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public String getCausal() {
        return causal;
    }

    public void setCausal(String causal) {
        this.causal = causal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date date) {
        this.operationDate = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }
    
    
    
    
    public void makeDescription(){
        switch(causal){
            case PCV:
                description = "Preso in conto vendita";
        }
    }

    @Override
    public int compareTo(Movement o) {
        return Integer.compare(code, o.code);
    }


    @Override
    public String toString() {
        return operationDate.toString() + ":\n" + description;
    }

    public float getLoadVar() {
        return loadVar;
    }

    public void setLoadVar(float loadVar) {
        this.loadVar = loadVar;
    }
    
    
    
}

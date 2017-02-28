/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans;

import java.sql.SQLException;
import java.util.Date;
import salesmanager.beans.dao.DBMovementsManager;

/**
 *
 * @author Lorenzo
 */
public class RegisterMovement {
    private int registerCode;
    
    private int customerCode;
    private String customerSurname;
    private String customerName;
    private String customerAddress;
    private String customerCap;
    private String customerCity;
    private String customerDistrict;
    private String customerDocument;
    private Date customerDocumentRelease;
    
    private int movementProgressive;
    private Date movementDate;
    private String productName;
    private int productQta;
    private float productPrice;
    private int productCommission;
    //commission price
    private String movementDescription;
    private float movementLoadVar;
    private float totalGoods;

    public RegisterMovement() {
    }
    
    
    
    public RegisterMovement(Customer c,Product p,  Movement m) throws SQLException{
        this.registerCode = -1;
        this.customerAddress = c.getAddress();
        this.customerCap = c.getCAP();
        this.customerCity = c.getCity();
        this.customerCode = c.getCode();
        this.customerDistrict = c.getDistrict();
        this.customerDocument = c.getDocument();
        this.customerDocumentRelease = c.getDocumentRelease();
        this.customerName = c.getName();
        this.customerSurname = c.getSurname();
        this.movementDate = m.getOperationDate();
        this.movementDescription = m.getDescription();
        this.movementLoadVar = m.getLoadVar();
        this.movementProgressive = m.getProgressive();
        this.productCommission = m.getCommission();
        this.productName = p.getName();
        this.productPrice = m.getPrice();
        this.productQta = p.getQta();
        this.totalGoods = DBMovementsManager.getBalanceUntilComp(m);
    }

    public int getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(int registerCode) {
        this.registerCode = registerCode;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }
    
    

    public int getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCap() {
        return customerCap;
    }

    public void setCustomerCap(String customerCap) {
        this.customerCap = customerCap;
    }

    public String getCustomerDistrict() {
        return customerDistrict;
    }

    public void setCustomerDistrict(String customerDistrict) {
        this.customerDistrict = customerDistrict;
    }

    public String getCustomerDocument() {
        return customerDocument;
    }

    public void setCustomerDocument(String customerDocument) {
        this.customerDocument = customerDocument;
    }

    public Date getCustomerDocumentRelease() {
        return customerDocumentRelease;
    }

    public void setCustomerDocumentRelease(Date customerDocumentRelease) {
        this.customerDocumentRelease = customerDocumentRelease;
    }

    public int getMovementProgressive() {
        return movementProgressive;
    }

    public void setMovementProgressive(int movementProgressive) {
        this.movementProgressive = movementProgressive;
    }

    public Date getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQta() {
        return productQta;
    }

    public void setProductQta(int productQta) {
        this.productQta = productQta;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductCommission() {
        return productCommission;
    }

    public void setProductCommission(int productCommission) {
        this.productCommission = productCommission;
    }

    public String getMovementDescription() {
        return movementDescription;
    }

    public void setMovementDescription(String movementDescription) {
        this.movementDescription = movementDescription;
    }

    public float getMovementLoadVar() {
        return movementLoadVar;
    }

    public void setMovementLoadVar(float movementLoadVar) {
        this.movementLoadVar = movementLoadVar;
    }

    public float getTotalGoods() {
        return totalGoods;
    }

    public void setTotalGoods(float totalGoods) {
        this.totalGoods = totalGoods;
    }
    
    
}

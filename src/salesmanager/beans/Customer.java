/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans;

import java.util.Date;

/**
 *
 * @author Lorenzo
 */
public class Customer implements Comparable<Customer>{
    
    private int code;
    private String name;
    private String surname;
    private String cf;
    private String piva;
    private String telephone;
    private String address;
    private String document;
    private Date documentRelease;
    private Date documentExpiration;
    private String documentEntity;
    private String CAP;
    private String city;
    private String district;

    public Customer() {
    }

    public Customer(String name, String surname, String cf, String piva, String telephone, String address, String document, Date documentRelease, Date documentExpiration, String documentEntity, String CAP, String city, String district) {
        this.code = -1;
        this.name = name;
        this.surname = surname;
        this.cf = cf;
        this.piva = piva;
        this.telephone = telephone;
        this.address = address;
        this.document = document;
        this.documentRelease = documentRelease;
        this.documentExpiration = documentExpiration;
        this.documentEntity = documentEntity;
        this.CAP = CAP;
        this.city = city;
        this.district = district;
    }

    
    
    public Customer(Customer c) {
        if (c != null) {
            BeansUtil.copyOf(c, this);
        }
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Date getDocumentRelease() {
        return documentRelease;
    }

    public void setDocumentRelease(Date documentRelease) {
        this.documentRelease = documentRelease;
    }

    public Date getDocumentExpiration() {
        return documentExpiration;
    }

    public void setDocumentExpiration(Date documentExpiration) {
        this.documentExpiration = documentExpiration;
    }

    public String getDocumentEntity() {
        return documentEntity;
    }

    public void setDocumentEntity(String documentEntity) {
        this.documentEntity = documentEntity;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        this.CAP = CAP;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Customer) {
            return ((Customer) obj).getCode() == code;
        }
        return false;
    }

    @Override
    public String toString() {
        return surname + " " + name + " - " + cf.toUpperCase() + " - " + address;
    }

    @Override
    public int compareTo(Customer o) {
        return Integer.compare(code, o.code);
    }

}

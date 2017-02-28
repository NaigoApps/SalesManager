/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import salesmanager.beans.BeansUtil;
import salesmanager.beans.Customer;

/**
 *
 * @author Lorenzo
 */
public class DBCustomersManager extends DBManager{
    
    private static Customer resultSetToCustomer(ResultSet rs) throws SQLException {
        return BeansUtil.copyOf(rs, new Customer());
    }
    
    
    private static Customer[] parseCustomersResultSet(ResultSet rs) throws SQLException{
        ArrayList<Customer> customers = new ArrayList<>();
        while(rs.next()){
            customers.add(resultSetToCustomer(rs));
        }
        return customers.toArray(new Customer[customers.size()]);
    }
    
    private DBCustomersManager() {
    }

    public static Customer[] getCustomers() throws SQLException {
        String query = "SELECT * FROM Customers ORDER BY surname";
        dbConnect();
        Customer[] customers = parseCustomersResultSet(dbSelect(query));
        dbDisconnect();
        return customers;
    }
    
    public static Customer[] getCustomers(String like) throws SQLException {
        String query = "SELECT * FROM Customers WHERE LOWER(surname) LIKE LOWER('%" + like + "%') OR LOWER(name) LIKE LOWER('%" + like + "%') ORDER BY surname";
        dbConnect();
        Customer[] customers = parseCustomersResultSet(dbSelect(query));
        dbDisconnect();
        return customers;
    }

    public static boolean addCustomer(Customer c) throws SQLException {
        String query = "INSERT INTO Customers(name,surname,cf,piva,telephone,address,document,documentRelease,documentExpiration,documentEntity,cap,city,district) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setString(1, c.getName());
        ps.setString(2, c.getSurname());
        ps.setString(3, c.getCf());
        ps.setString(4, c.getPiva());
        ps.setString(5, c.getTelephone());
        ps.setString(6, c.getAddress());
        ps.setString(7, c.getDocument());
        ps.setDate(8, (c.getDocumentRelease() != null)?new Date(c.getDocumentRelease().getTime()):null);
        ps.setDate(9, (c.getDocumentExpiration()!= null)?new Date(c.getDocumentExpiration().getTime()):null);
        ps.setString(10, c.getDocumentEntity());
        ps.setString(11, c.getCAP());
        ps.setString(12, c.getCity());
        ps.setString(13, c.getDistrict());
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

    public static boolean editCustomer(Customer c) throws SQLException {
        String query = "UPDATE Customers SET "
                + "name = ?,"
                + "surname = ?,"
                + "cf = ?,"
                + "piva = ?,"
                + "telephone = ?,"
                + "address = ?,"
                + "document = ?,"
                + "documentRelease = ?,"
                + "documentExpiration = ?,"
                + "documentEntity = ?,"
                + "cap = ?,"
                + "city = ?,"
                + "district = ? "
                + "WHERE code = ?";
        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setString(1, c.getName());
        ps.setString(2, c.getSurname());
        ps.setString(3, c.getCf());
        ps.setString(4, c.getPiva());
        ps.setString(5, c.getTelephone());
        ps.setString(6, c.getAddress());
        ps.setString(7, c.getDocument());
        ps.setDate(8, (c.getDocumentRelease() != null)?new Date(c.getDocumentRelease().getTime()):null);
        ps.setDate(9, (c.getDocumentExpiration()!= null)?new Date(c.getDocumentExpiration().getTime()):null);
        ps.setString(10, c.getDocumentEntity());
        ps.setString(11, c.getCAP());
        ps.setString(12, c.getCity());
        ps.setString(13, c.getDistrict());
        ps.setInt(14, c.getCode());
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

    public static boolean removeCustomer(Customer c) throws SQLException {
        String query = "DELETE FROM Customers WHERE code = " + c.getCode();
        dbConnect();
        boolean res = dbUpdate(query);
        dbDisconnect();
        return res;
    }

    public static Customer getCustomer(int code) throws SQLException {
        String query = "SELECT * FROM Customers WHERE code = " + code;
        dbConnect();
        Customer[] customers = parseCustomersResultSet(dbSelect(query));
        dbDisconnect();
        if(customers.length == 1){
            return customers[0];
        }
        return null;
    }
    
}

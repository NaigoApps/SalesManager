/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import salesmanager.beans.BeansUtil;
import salesmanager.beans.Customer;
import salesmanager.beans.Invoice;
import salesmanager.beans.Product;
import static salesmanager.beans.dao.DBManager.dbConnect;
import static salesmanager.beans.dao.DBManager.dbSelect;
import salesmanager.graphics.Main;

/**
 *
 * @author Lorenzo
 */
public class DBInvoicesManager extends DBManager {
    
    private static Invoice resultSetToInvoice(ResultSet rs) throws SQLException {
        return BeansUtil.copyOf(rs, new Invoice());
    }

    private static Invoice[] parseInvoicesResultSet(ResultSet rs) throws SQLException {
        ArrayList<Invoice> diaries = new ArrayList<>();
        while (rs.next()) {
            diaries.add(resultSetToInvoice(rs));
        }
        return diaries.toArray(new Invoice[diaries.size()]);
    }

    public static boolean removeInvoice(Invoice inv) throws SQLException {
        String query = "DELETE FROM invoices WHERE code = " + inv.getCode();
        dbConnect();
        boolean res = dbUpdate(query);
        dbDisconnect();
        return res;
    }
    
    public static Invoice getLastClosedInvoice() throws SQLException{
        String query = "SELECT * FROM invoices i WHERE "
                + "i.progressive <> -1 AND "
                + "i.invoiceDate = (SELECT max(invoiceDate) FROM invoices) AND "
                + "i.progressive = (SELECT max(progressive) FROM invoices WHERE invoiceDate = i.invoiceDate)";
        dbConnect();
        Invoice[] invoices = parseInvoicesResultSet(dbSelect(query));
        dbDisconnect();
        if(invoices.length == 1){
            return invoices[0];
        }
        return null;
    }
    
    public static int getNProducts(Invoice inv) throws SQLException{
        String query = "SELECT count(*) FROM products p WHERE "
                + "p.invoice = " + inv.getCode();
        dbConnect();
        int n = parseValueResultSet(dbSelect(query)).intValue();
        dbDisconnect();
        return n;
    }

    public static boolean canRemove(Invoice inv) throws SQLException {
        if(inv.getProgressive() == -1){
            return true;
        }
        Invoice last = DBInvoicesManager.getLastClosedInvoice();
        if(last.getCode() == inv.getCode()){
            return true;
        }
        return false;
    }

    private DBInvoicesManager() {
    }

    public static Invoice[] getInvoices(Date from, Date to) throws SQLException {
        Date sqlFrom = new java.sql.Date(from.getTime());
        Date sqlTo = new java.sql.Date(to.getTime());
        String query = "SELECT * FROM invoices WHERE "
                + "invoiceDate >= '" + sqlFrom + "' AND "
                + "invoiceDate <= '" + sqlTo + "' "
                + "ORDER BY invoiceDate, progressive;";
        dbConnect();
        Invoice[] invoices = parseInvoicesResultSet(dbSelect(query));
        dbDisconnect();
        return invoices;
    }

    public static Invoice[] getOpenInvoices() throws SQLException {
        String query = "SELECT * FROM invoices WHERE progressive IS NULL;";
        dbConnect();
        Invoice[] invoices = parseInvoicesResultSet(dbSelect(query));
        dbDisconnect();
        return invoices;
    }

    public static Invoice[] getOpenInvoices(Customer c) throws SQLException {
        String query = "SELECT * FROM invoices WHERE "
                + "progressive IS NULL AND "
                + "customer = " + c.getCode();
        dbConnect();
        Invoice[] invoices = parseInvoicesResultSet(dbSelect(query));
        dbDisconnect();
        return invoices;
    }

    public static Invoice getProductInvoice(Product p) throws SQLException {
        String query = "SELECT * FROM invoices WHERE "
                + "code = " + p.getInvoice() + ";";
        dbConnect();
        Invoice[] invoices = parseInvoicesResultSet(dbSelect(query));
        dbDisconnect();
        if (invoices.length == 1) {
            return invoices[0];
        }
        return null;
    }

    public static int getNextInvoice(int year) throws SQLException {
        String query = "SELECT max(progressive)+1 FROM invoices WHERE year(invoiceDate) = " + year;
        dbConnect();
        int val = 1;
        if (DBManager.parseValueResultSet(dbSelect(query)) != null) {
            val = parseValueResultSet(dbSelect(query)).intValue();
        }
        dbDisconnect();
        return val;
    }

    public static boolean addInvoice(Invoice i) throws SQLException {
        String query = "INSERT INTO invoices(customer,invoiceDate) "
                + "VALUES(?,?)";

        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setInt(1, i.getCustomer());
        ps.setDate(2, (i.getInvoiceDate() != null) ? new java.sql.Date(i.getInvoiceDate().getTime()) : null);
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

    public static boolean editInvoice(Invoice i) throws SQLException {
        String query = "UPDATE invoices SET "
                + "customer = ?,"
                + "invoiceDate = ? "
                + "WHERE code = ?";
        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setInt(1, i.getCustomer());
        ps.setDate(2, (i.getInvoiceDate() != null) ? new java.sql.Date(i.getInvoiceDate().getTime()) : null);
        ps.setInt(3, i.getCode());
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

    public static boolean setProgressive(Invoice i) throws SQLException {
        String query = "UPDATE invoices SET "
                + "progressive = ? ,"
                + "invoiceDate = ? "
                + "WHERE code = ?";
        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setInt(1, getNextInvoice(Main.year(i.getInvoiceDate())));
        ps.setDate(2,new java.sql.Date(i.getInvoiceDate().getTime()));
        ps.setInt(3, i.getCode());
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

}

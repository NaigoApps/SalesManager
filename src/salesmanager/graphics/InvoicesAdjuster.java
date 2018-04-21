/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import salesmanager.beans.Invoice;
import salesmanager.beans.dao.DBInvoicesManager;
import salesmanager.beans.dao.DBProductsManager;

/**
 *
 * @author Lorenzo
 */
public class InvoicesAdjuster {

    public List<Integer> getEmpty(int year) throws Exception {

        Invoice[] invoices = DBInvoicesManager.getClosedInvoices(year);

        List<Invoice> invoiceToRemove = new ArrayList<>();
        for (Invoice invoice : invoices) {
            int count = DBProductsManager.countInvoiceProducts(invoice.getCode());
            if (count == 0) {
                invoiceToRemove.add(invoice);
            }
        }

        return invoiceToRemove.stream()
                .map(i -> i.getCode())
                .collect(Collectors.toList());
    }

    public boolean hasHoles(int year) throws Exception {

        Invoice[] invoices = DBInvoicesManager.getClosedInvoices(year);

        int progCounter = 1;
        boolean hasHoles = false;
        for (Invoice invoice : invoices) {
            if (invoice.getProgressive() != progCounter) {
                hasHoles = true;
            }
            progCounter++;
        }
        return hasHoles;
    }

    public void remove(List<Integer> codes) throws Exception{
        List<String> queries = new ArrayList<>();
        for (Integer code : codes) {
            queries.add("DELETE FROM invoices WHERE code = " + code);
        }

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/affarefatto", "root", "root");
        conn.setAutoCommit(false);

        for (String query : queries) {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        }

        conn.commit();
    }

    public void compact(int year) throws SQLException {
        Invoice[] invoices = DBInvoicesManager.getClosedInvoices(year);

        int progCounter = 1;
        boolean hasHoles = false;
        List<String> queries = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (invoice.getProgressive() != progCounter) {
                queries.add("UPDATE invoices SET progressive = " + progCounter + " WHERE code = " + invoice.getCode());
            }
            progCounter++;
        }

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/affarefatto", "root", "root");
        conn.setAutoCommit(false);

        for (String query : queries) {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        }

        conn.commit();
    }
}

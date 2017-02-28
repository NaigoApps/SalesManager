/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import salesmanager.beans.BeansUtil;
import salesmanager.beans.DeliveryDocument;
import salesmanager.beans.Invoice;
import salesmanager.beans.Movement;
import salesmanager.beans.Product;
import salesmanager.graphics.LoadingFrame;
import salesmanager.graphics.Main;

/**
 *
 * @author Lorenzo
 */
public class DBProductsManager extends DBManager {

    private static Product resultSetToProduct(ResultSet rs) throws SQLException {
        return BeansUtil.copyOf(rs, new Product());
    }

    private static Movement resultSetToMovement(ResultSet rs) throws SQLException {
        return BeansUtil.copyOf(rs, new Movement());
    }

    private static Product[] parseProductsResultSet(ResultSet rs) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        while (rs.next()) {
            products.add(resultSetToProduct(rs));
        }
        return products.toArray(new Product[products.size()]);
    }

    private static Movement[] parseMovementsResultSet(ResultSet rs) throws SQLException {
        ArrayList<Movement> movements = new ArrayList<>();
        while (rs.next()) {
            movements.add(resultSetToMovement(rs));
        }
        return movements.toArray(new Movement[movements.size()]);
    }

    public static Movement[] getMovements(Product p) throws SQLException {
        String query = "SELECT * FROM Movements WHERE "
                + "product = " + p.getCode() + ";";
        dbConnect();
        Movement[] movements = parseMovementsResultSet(dbSelect(query));
        dbDisconnect();
        return movements;
    }

    public static void updateProducts() throws SQLException {
        Product[] toUpdate = getExpiredProducts();
        GregorianCalendar gc = new GregorianCalendar();
        for (int i = 0; i < toUpdate.length; i++) {
            LoadingFrame.setPercent(i*100/toUpdate.length);
            gc.setTime(DBDeliveryManager.getProductDeliveryDocument(toUpdate[i]).getDocumentDate());
            gc.add(Calendar.DAY_OF_MONTH, 60);
            toUpdate[i].setPrice(toUpdate[i].getPrice() / 2);
            DBMovementsManager.addMovement(new Movement(
                    toUpdate[i].getCode(),
                    Movement.EXP,
                    "Prezzo dimezzato dopo 60 giorni",
                    gc.getTime(),
                    toUpdate[i].getPrice(),
                    toUpdate[i].getCommission(),
                    -toUpdate[i].getPrice()));
            DBProductsManager.editProduct(toUpdate[i]);
        }
    }

    public static Product[] getExpiredProducts() throws SQLException {
        String query = "SELECT p.* FROM Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "m.causal = \"PCV\" AND "
                + "NOT EXISTS (SELECT * FROM Movements m1 WHERE m1.product = p.code AND m1.causal = \"EXP\") AND "
                + "NOT EXISTS (SELECT * FROM Movements m1 WHERE m1.product = p.code AND m1.causal = \"RES\") AND "
                + "NOT EXISTS (SELECT * FROM Movements m1 WHERE m1.product = p.code AND m1.causal = \"CPM\") AND "
                + "DATE_ADD(m.operationDate,INTERVAL 60 DAY) < CURDATE();";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getFromInvoiceProducts(int code) throws SQLException {
        String query = "SELECT p.* FROM Products p, Invoices i WHERE p.invoice = i.code AND i.code = " + code;
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    private DBProductsManager() {
    }

    public static Product[] getArrivedProducts(Date from, Date to) throws SQLException {
        Date sqlFrom = new java.sql.Date(from.getTime());
        Date sqlTo = new java.sql.Date(to.getTime());
        String query = "SELECT p.* FROM Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "m.causal = \"PCV\" AND "
                + "m.operationDate >= '" + sqlFrom + "' AND "
                + "m.operationDate <= '" + sqlTo + "';";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getSoldProducts(Date from, Date to) throws SQLException {
        Date sqlFrom = new java.sql.Date(from.getTime());
        Date sqlTo = new java.sql.Date(to.getTime());
        String query = "SELECT p.* FROM Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "m.causal = \"CPM\" AND "
                + "m.operationDate >= '" + sqlFrom + "' AND "
                + "m.operationDate <= '" + sqlTo + "';";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getFromDeliveryDocumentProducts(int document) throws SQLException {
        String query = "SELECT p.* FROM Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "p.deliveryDocument = " + document + " AND "
                + "m.causal = \"PCV\";";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getFromClientProducts(int customer, Date from, Date to) throws SQLException {
        Date sqlFrom = new java.sql.Date(from.getTime());
        Date sqlTo = new java.sql.Date(to.getTime());
        String query = "SELECT p.* FROM Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "p.customer = " + customer + " AND "
                + "m.causal = \"PCV\" AND "
                + "m.operationDate >= '" + sqlFrom + "' AND "
                + "m.operationDate <= '" + sqlTo + "';";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getFromClientProductsBack(int customer, Date from, Date to) throws SQLException {
        Date sqlFrom = new java.sql.Date(from.getTime());
        Date sqlTo = new java.sql.Date(to.getTime());
        String query = "SELECT p.* FROM DeliveryDocuments d, Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "p.deliveryDocument = d.code AND "
                + "d.customer = " + customer + " AND "
                + "m.causal = \"PCV\" AND "
                + "m.operationDate >= '" + sqlFrom + "' AND "
                + "m.operationDate <= '" + sqlTo + "' AND "
                + "EXISTS (SELECT * FROM Movements WHERE causal = \"RES\" AND product = p.code);";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getFromClientProductsBack(int customer) throws SQLException {
        String query = "SELECT p.* FROM DeliveryDocuments d, Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "p.deliveryDocument = d.code AND "
                + "d.customer = " + customer + " AND "
                + "m.causal = \"PCV\" AND "
                + "EXISTS (SELECT * FROM Movements WHERE causal = \"RES\" AND product = p.code);";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getFromClientProductsSold(int customer, Date from, Date to) throws SQLException {
        Date sqlFrom = new java.sql.Date(from.getTime());
        Date sqlTo = new java.sql.Date(to.getTime());
        String query = "SELECT p.* FROM DeliveryDocuments d, Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "p.deliveryDocument = d.code AND "
                + "d.customer = " + customer + " AND "
                + "m.causal = \"PCV\" AND "
                + "m.operationDate >= '" + sqlFrom + "' AND "
                + "m.operationDate <= '" + sqlTo + "' AND "
                + "EXISTS (SELECT * FROM Movements WHERE causal = \"CPM\" AND product = p.code);";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getFromClientProductsSold(int customer) throws SQLException {
        String query = "SELECT p.* FROM DeliveryDocuments d, Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "p.deliveryDocument = d.code AND "
                + "d.customer = " + customer + " AND "
                + "m.causal = \"PCV\" AND "
                + "EXISTS (SELECT * FROM Movements WHERE causal = \"CPM\" AND product = p.code);";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getFromClientProductsOnSale(int customer) throws SQLException {
        String query = "SELECT p.* FROM DeliveryDocuments d, Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "p.deliveryDocument = d.code AND "
                + "d.customer = " + customer + " AND "
                + "m.causal = \"PCV\" AND "
                + "NOT EXISTS (SELECT * FROM Movements WHERE causal = \"CPM\" AND product = p.code) AND "
                + "NOT EXISTS (SELECT * FROM Movements WHERE causal = \"RES\" AND product = p.code);";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getOnSaleProducts() throws SQLException {
        String query = "SELECT * FROM Products p WHERE "
                + "NOT EXISTS (SELECT * FROM Movements m WHERE m.product = p.code AND "
                + "(m.causal = \"RES\" OR m.causal = \"CPM\"));";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static Product[] getReturnedProducts(Date from, Date to) throws SQLException {
        Date sqlFrom = new java.sql.Date(from.getTime());
        Date sqlTo = new java.sql.Date(to.getTime());
        String query = "SELECT p.* FROM Products p, Movements m WHERE "
                + "p.code = m.product AND "
                + "m.causal = \"RES\" AND "
                + "m.operationDate >= '" + sqlFrom + "' AND "
                + "m.operationDate <= '" + sqlTo + "';";
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        return products;
    }

    public static boolean addProduct(Product p) throws SQLException {
        String query = "INSERT INTO Products(name,description,qta,price,commission,deliverydocument)"
                + "VALUES(?,?,?,?,?,?)";

        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setString(1, p.getName());
        ps.setString(2, p.getDescription());
        ps.setInt(3, p.getQta());
        ps.setFloat(4, p.getPrice());
        ps.setInt(5, p.getCommission());
        ps.setInt(6, p.getDeliveryDocument());
        int key = dbInsert();
        dbDisconnect();
        p.setCode(key);
        return key != -1;
    }

    public static boolean setInvoice(Product p, Invoice i) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE Products SET ");
        query.append("invoice=? ");
        query.append("WHERE code = ?");
        dbConnect();
        PreparedStatement ps = prepareStatement(query.toString());
        ps.setInt(1, i.getCode());
        ps.setInt(2, p.getCode());
        boolean res = dbUpdate();
        dbDisconnect();
        if (res) {
            p.setInvoice(i.getCode());
        } else {
            p.setInvoice(-1);
        }
        return res;
    }

    public static boolean setDeliveryDocument(Product p, int document) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE Products SET ");
        query.append("deliveryDocument=? ");
        query.append("WHERE code = ?");
        dbConnect();
        PreparedStatement ps = prepareStatement(query.toString());
        ps.setInt(1, document);
        ps.setInt(2, p.getCode());
        boolean res = dbUpdate();
        dbDisconnect();
        if (res) {
            p.setDeliveryDocument(document);
        } else {
            p.setDeliveryDocument(-1);
        }
        return res;
    }

    public static boolean editProduct(Product p) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE Products SET ");
        query.append("name=?,");
        query.append("description=?,");
        query.append("qta=?,");
        query.append("price=?,");
        query.append("commission=? ");
        query.append("WHERE code = ?");
        dbConnect();
        PreparedStatement ps = prepareStatement(query.toString());
        ps.setString(1, p.getName());
        ps.setString(2, p.getDescription());
        ps.setInt(3, p.getQta());
        ps.setFloat(4, p.getPrice());
        ps.setInt(5, p.getCommission());
        ps.setInt(6, p.getCode());
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

    public static boolean removeProduct(Product p) throws SQLException {
        String query = "DELETE FROM Products WHERE code = " + p.getCode();
        dbConnect();
        boolean res = dbUpdate(query);
        dbDisconnect();
        return res;
    }

    public static Product getProduct(int code) throws SQLException {
        String query = "SELECT * FROM Products WHERE code = " + code;
        dbConnect();
        Product[] products = parseProductsResultSet(dbSelect(query));
        dbDisconnect();
        if (products.length == 1) {
            return products[0];
        }
        return null;
    }
}

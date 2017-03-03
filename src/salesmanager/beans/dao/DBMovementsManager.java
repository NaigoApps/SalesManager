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
import salesmanager.beans.DeliveryDocument;
import salesmanager.beans.Movement;
import salesmanager.beans.Product;

/**
 *
 * @author Lorenzo
 */
public class DBMovementsManager extends DBManager {

    private static Movement resultSetToMovement(ResultSet rs) throws SQLException {
        return BeansUtil.copyOf(rs, new Movement());
    }

    private static Movement[] parseMovementsResultSet(ResultSet rs) throws SQLException {
        ArrayList<Movement> movements = new ArrayList<>();
        while (rs.next()) {
            movements.add(resultSetToMovement(rs));
        }
        return movements.toArray(new Movement[movements.size()]);
    }

    private DBMovementsManager() {
    }

    public static Movement[] getMovements(Date from, Date to) throws SQLException {
        Date sqlFrom = new java.sql.Date(from.getTime());
        Date sqlTo = new java.sql.Date(to.getTime());
        String query = "SELECT * FROM movements WHERE "
                + "operationDate >= '" + sqlFrom + "' AND "
                + "operationDate <= '" + sqlTo + "' "
                + "ORDER BY operationDate, code;";
        dbConnect();
        Movement[] movements = parseMovementsResultSet(dbSelect(query));
        dbDisconnect();
        return movements;
    }

    public static Movement getMovement(int code) throws SQLException {
        String query = "SELECT * FROM movements WHERE "
                + "code = " + code + ";";
        dbConnect();
        Movement[] movements = parseMovementsResultSet(dbSelect(query));
        dbDisconnect();
        if (movements.length == 1) {
            return movements[0];
        }
        return null;
    }

    public static Date getMinimumDate() throws SQLException {
        String query = "SELECT max(operationDate) FROM movements m WHERE m.progressive <> -1";
        dbConnect();
        ResultSet rs = dbSelect(query);
        Date d = null;
        if (rs.next()) {
            d = rs.getDate(1);
        }
        dbDisconnect();

        return d;
    }

    public static Date getMinimumDate(Product p) throws SQLException {
        String query = "SELECT max(operationDate) FROM movements m WHERE "
                + "m.product = " + p.getCode();
        dbConnect();
        ResultSet rs = dbSelect(query);
        Date d = null;
        if (rs.next()) {
            d = rs.getDate(1);
        }
        dbDisconnect();

        return d;
    }

    public static Movement getFirstMovementToRegister() throws SQLException {
        String query = "SELECT min(operationDate) FROM movements m WHERE m.progressive = -1 AND m.product NOT IN (SELECT p1.code FROM products p1, movements m1 WHERE p1.code = m1.product AND m1.causal = 'PCV' AND m1.operationDate < '2016-04-22')";
        dbConnect();
        Date minDate = parseDateResultSet(dbSelect(query));
        dbDisconnect();
        query = "SELECT min(code) FROM movements m WHERE m.progressive = -1 AND m.operationDate = '" + minDate + "' AND m.product NOT IN (SELECT p1.code FROM products p1, movements m1 WHERE p1.code = m1.product AND m1.causal = 'PCV' AND m1.operationDate < '2016-04-22')";
        dbConnect();
        int minCode = parseValueResultSet(dbSelect(query)).intValue();
        dbDisconnect();
        
        return getMovement(minCode);
    }

    public static Movement[] getDeliveryDocumentMovements(DeliveryDocument doc) throws SQLException {
        String query = "SELECT * FROM movements m, products p WHERE "
                + "m.product = p.code AND "
                + "p.deliveryDocument = " + doc.getCode() + " "
                + "ORDER BY m.operationDate, m.code;";
        dbConnect();
        Movement[] movements = parseMovementsResultSet(dbSelect(query));
        dbDisconnect();
        return movements;
    }

    public static Movement[] getProductMovements(Product p) throws SQLException {
        String query = "SELECT * FROM movements WHERE "
                + "product = " + p.getCode() + " "
                + "ORDER BY operationDate, code;";
        dbConnect();
        Movement[] movements = parseMovementsResultSet(dbSelect(query));
        dbDisconnect();
        return movements;
    }

    public static boolean addMovement(Movement m) throws SQLException {
        String query = "INSERT INTO movements(product,causal,description,operationDate,loadvar,price,commission) "
                + "VALUES(?,?,?,?,?,?,?)";

        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setInt(1, m.getProduct());
        ps.setString(2, m.getCausal());
        ps.setString(3, m.getDescription());
        ps.setDate(4, (m.getOperationDate() != null) ? new java.sql.Date(m.getOperationDate().getTime()) : null);
        ps.setFloat(5, m.getLoadVar());
        ps.setFloat(6, m.getPrice());
        ps.setInt(7, m.getCommission());
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

    public static float getBalanceUntil(Movement m) throws SQLException {
        String query = "SELECT sum(loadVar) FROM movements m WHERE progressive <> -1 AND "
                + "m.product NOT IN (SELECT p1.code FROM products p1, movements m1 WHERE p1.code = m1.product AND m1.causal = 'PCV' AND m1.operationDate < '2016-04-22') AND "
                + "progressive < " + m.getProgressive();
        dbConnect();
        Number val = parseValueResultSet(dbSelect(query));
        dbDisconnect();
        return (val != null) ? val.floatValue() : 0;
    }

    public static boolean isGood(Movement m) throws SQLException {
        String query = "SELECT * FROM movements m WHERE "
                + "m.product = " + m.getProduct() + " AND "
                + "m.product IN (SELECT p1.code FROM products p1, movements m1 WHERE p1.code = m1.product AND m1.causal = 'PCV' AND m1.operationDate < '2016-04-22')";
        dbConnect();
        Movement[] movements = parseMovementsResultSet(dbSelect(query));
        dbDisconnect();
        return movements.length == 0;
    }

    public static float getBalanceUntilComp(Movement m) throws SQLException {
        String query = "SELECT sum(loadVar) FROM movements m WHERE progressive <> -1 AND "
                + "m.product NOT IN (SELECT p1.code FROM products p1, movements m1 WHERE p1.code = m1.product AND m1.causal = 'PCV' AND m1.operationDate < '2016-04-22') AND "
                + "progressive <= " + m.getProgressive();
        dbConnect();
        float val = parseValueResultSet(dbSelect(query)).floatValue();
        dbDisconnect();
        return val;
    }

    public static boolean setProgressive(Movement m) throws SQLException {
        String query = "UPDATE movements SET "
                + "progressive = ? "
                + "WHERE code = ?";
        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setInt(1, getNextProgressive());
        ps.setInt(2, m.getCode());
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

    public static boolean resetProgressive(Movement m) throws SQLException {
        String query = "UPDATE movements SET "
                + "progressive = -1 "
                + "WHERE code = ?";
        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setInt(1, m.getCode());
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

    public static int getNextProgressive() throws SQLException {
        String query = "SELECT max(progressive)+1 FROM movements WHERE progressive <> -1";
        dbConnect();
        int val = 1;
        if (DBManager.parseValueResultSet(dbSelect(query)) != null) {
            val = DBManager.parseValueResultSet(dbSelect(query)).intValue();
        }
        dbDisconnect();
        return val;
    }

    public static boolean editMovement(Movement m) throws SQLException {
        if (m.getProgressive() == -1) {
            String query = "UPDATE movements SET "
                    + "product = ?,"
                    + "causal = ?,"
                    + "description = ?,"
                    + "operationDate = ?,"
                    + "loadVar = ?,"
                    + "price = ?,"
                    + "commission = ? "
                    + "WHERE code = ?";
            dbConnect();
            PreparedStatement ps = prepareStatement(query);
            ps.setInt(1, m.getProduct());
            ps.setString(2, m.getCausal());
            ps.setString(3, m.getDescription());
            ps.setDate(4, (m.getOperationDate() != null) ? new java.sql.Date(m.getOperationDate().getTime()) : null);
            ps.setFloat(5, m.getLoadVar());
            ps.setFloat(6, m.getPrice());
            ps.setInt(7, m.getCommission());
            ps.setInt(8, m.getCode());
            boolean res = dbUpdate();
            dbDisconnect();
            return res;
        }
        return false;
    }

    public static boolean removeMovement(Movement m) throws SQLException {
        String query = "DELETE FROM movements WHERE code = " + m.getCode();
        dbConnect();
        boolean res = dbUpdate(query);
        dbDisconnect();
        return res;
    }

}

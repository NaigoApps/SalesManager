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
import salesmanager.beans.DeliveryDocument;
import salesmanager.beans.Movement;
import salesmanager.beans.Product;

/**
 *
 * @author Lorenzo
 */
public class DBDeliveryManager extends DBManager{
    
    private static DeliveryDocument resultSetToDeliveryDocument(ResultSet rs) throws SQLException {
        DeliveryDocument dc = new DeliveryDocument();
        dc.setCode(rs.getInt("code"));
        dc.setProgressive(rs.getInt("progressive"));
        dc.setDocumentDate(rs.getDate("documentDate"));
        dc.setCustomer(rs.getInt("customer"));
        return dc;
    }
    
    private static DeliveryDocument[] parseDeliveryDocumentsResultSet(ResultSet rs) throws SQLException{
        ArrayList<DeliveryDocument> documents = new ArrayList<>();
        while(rs.next()){
            documents.add(resultSetToDeliveryDocument(rs));
        }
        return documents.toArray(new DeliveryDocument[documents.size()]);
    }
    
    

    private DBDeliveryManager() {
    }
    
    public static boolean canRemove(DeliveryDocument doc) throws SQLException{
        Movement[] movements = DBMovementsManager.getDeliveryDocumentMovements(doc);
        for (int i = 0; i < movements.length; i++) {
            if(movements[i].getProgressive() != -1){
                return false;
            }
        }
        if(doc.getProgressive() == -1 || doc.getCode() == DBDeliveryManager.getLastClosedDeliveryDocument().getCode()){
            return true;
        }
        return false;
    }

    public static DeliveryDocument getLastClosedDeliveryDocument() throws SQLException{
        String query = "SELECT * FROM DeliveryDocuments d WHERE "
                + "d.progressive <> -1 AND "
                + "d.documentDate = (SELECT max(documentDate) FROM DeliveryDocuments) AND "
                + "d.progressive = (SELECT max(progressive) FROM DeliveryDocuments WHERE documentDate = d.documentDate)";
        dbConnect();
        DeliveryDocument[] documents = parseDeliveryDocumentsResultSet(dbSelect(query));
        dbDisconnect();
        if(documents.length == 1){
            return documents[0];
        }
        return null;
    }
    
    public static DeliveryDocument[] getDeliveryDocuments(Date from, Date to) throws SQLException {
        Date sqlFrom = new java.sql.Date(from.getTime());
        Date sqlTo = new java.sql.Date(to.getTime());
        String query = "SELECT * FROM DeliveryDocuments WHERE "
                + "documentDate >= '" + sqlFrom + "' AND "
                + "documentDate <= '" + sqlTo + "';";
        dbConnect();
        DeliveryDocument[] invoices = parseDeliveryDocumentsResultSet(dbSelect(query));
        dbDisconnect();
        return invoices;
    }
    
    public static DeliveryDocument getProductDeliveryDocument(Product p) throws SQLException {
        String query = "SELECT * FROM DeliveryDocuments WHERE "
                + "code = " + p.getDeliveryDocument() + ";";
        dbConnect();
        DeliveryDocument[] documents = parseDeliveryDocumentsResultSet(dbSelect(query));
        dbDisconnect();
        if(documents.length == 1){
            return documents[0];
        }
        return null;
    }

    public static int getNextDeliveryDocument(int year) throws SQLException{
        String query = "SELECT max(progressive)+1 FROM DeliveryDocuments WHERE year(documentDate) = " + year;
        dbConnect();
        int val = 1;
        if(DBManager.parseValueResultSet(dbSelect(query)) != null){
            val = DBManager.parseValueResultSet(dbSelect(query)).intValue();
        }
        dbDisconnect();
        return val;
    }
    
    public static boolean addDeliveryDocument(DeliveryDocument i) throws SQLException {
        String query = "INSERT INTO DeliveryDocuments(progressive,documentDate,customer) "
                + "VALUES(?,?,?)";
        Calendar c = new GregorianCalendar();
        c.setTime(i.getDocumentDate());
        dbConnect();
        PreparedStatement ps = prepareStatement(query);        
        ps.setInt(1, i.getProgressive());
        ps.setDate(2,(i.getDocumentDate()!= null)? new java.sql.Date(i.getDocumentDate().getTime()):null);
        ps.setInt(3, i.getCustomer().getCode());
        int code = dbInsert();
        dbDisconnect();
        if(code != -1){
            i.setCode(code);
        }else{
            i.setCode(-1);
        }
        return code != -1;
    }
    
    public static boolean editDeliveryDocument(DeliveryDocument i) throws SQLException {
        String query = "UPDATE DeliveryDocuments SET "
                + "customer = ?,"
                + "documentDate = ? "
                + "WHERE code = ?";
        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setInt(1, i.getCustomer().getCode());
        ps.setDate(2,(i.getDocumentDate()!= null)? new java.sql.Date(i.getDocumentDate().getTime()):null);
        ps.setInt(3, i.getCode());
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }
    
    

    public static boolean removeDeliveryDocument(DeliveryDocument d) throws SQLException {
        String query = "DELETE FROM DeliveryDocuments WHERE code = " + d.getCode();
        dbConnect();
        boolean res = dbUpdate(query);
        dbDisconnect();
        return res;
    }
    
}

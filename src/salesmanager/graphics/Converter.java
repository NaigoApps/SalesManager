/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JOptionPane;
import salesmanager.beans.Customer;
import salesmanager.beans.DeliveryDocument;
import salesmanager.beans.Movement;
import salesmanager.beans.Product;
import salesmanager.beans.dao.DBCustomersManager;
import salesmanager.beans.dao.DBDeliveryManager;
import salesmanager.beans.dao.DBProductsManager;

/**
 *
 * @author Lorenzo
 */
public class Converter {

    public static void main(String args[]) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/affarefatto", "root", "");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from products");
        ArrayList<Movement> toInsert = new ArrayList<>();
        ArrayList<DeliveryDocument> docs = new ArrayList<>();
        float i = 0;
        while (rs.next()) {
            i++;
            System.out.println((float)i / 2695 * 100);
            int code = rs.getInt("code");
            Date in = rs.getDate("inDate");
            Date out = rs.getDate("outDate");
            float price = rs.getFloat("price");
            int comm = rs.getInt("commission");
            int cust = rs.getInt("customer");
            Customer customer = null;
            try{
                customer = DBCustomersManager.getCustomer(cust);
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex, "Errore caricamento cliente", JOptionPane.ERROR_MESSAGE);
            }
            if (in != null) {
                in = Main.simplify(in);
                //toInsert.add(new Movement(code, Movement.PCV, "Preso in conto vendita", in, price,comm, price));
                DeliveryDocument dd = new DeliveryDocument(customer, -1, in, new Product[0]);
                DeliveryDocument similar = similarDeliveryDocument(docs, dd);
                if(similar == null){
                    dd.addProduct(DBProductsManager.getProduct(code));
                    docs.add(dd);
                }else{
                    similar.addProduct(DBProductsManager.getProduct(code));
                }
            }/*
            if (out != null) {
                out = Main.simplify(out);
                toInsert.add(new Movement(code, Movement.CPM, "Ceduto per mandato", out, price,comm, -price));
            }*/
        }
        Collections.sort(toInsert, new Comparator<Movement>() {

            @Override
            public int compare(Movement o1, Movement o2) {
                return o1.getOperationDate().compareTo(o2.getOperationDate());
            }
        });
        rs.close();
        s.close();
        PreparedStatement ps;
        /*
        ps = conn.prepareStatement("INSERT INTO Movements(product,causal,description,operationDate,price,commission,loadVar) VALUES (?,?,?,?,?,?,?);");
        i = 0;
        for (Movement m : toInsert) {
            i++;
            System.out.println((float)i / toInsert.size() * 100);
            ps.setInt(1, m.getProduct());
            ps.setString(2, m.getCausal());
            ps.setString(3, m.getDescription());
            ps.setDate(4,(m.getOperationDate() != null)? new java.sql.Date(m.getOperationDate().getTime()):null);
            ps.setFloat(5, m.getPrice());
            ps.setInt(6, m.getCommission());
            ps.setFloat(7, m.getLoadVar());
            ps.executeUpdate();
        }
        ps.close();
        */
        i = 0;
        for (DeliveryDocument doc : docs) {
            i++;
            System.out.println((float)i / docs.size() * 100);
            doc.setProgressive(DBDeliveryManager.getNextDeliveryDocument(Main.year(doc.getDocumentDate())));
            DBDeliveryManager.addDeliveryDocument(doc);
            Product[] docsProducts = doc.getProducts();
            for (Product docsProduct : docsProducts) {
                DBProductsManager.setDeliveryDocument(docsProduct, doc.getCode());
            }
        }
        
        conn.close();
    }
    
    public static DeliveryDocument similarDeliveryDocument(ArrayList<DeliveryDocument> docs, DeliveryDocument doc){
        for (DeliveryDocument get : docs) {
            if(get.getDocumentDate().equals(doc.getDocumentDate()) && get.getCustomer().getCode() == doc.getCustomer().getCode()){
                return get;
            }
        }
        return null;
    }
}

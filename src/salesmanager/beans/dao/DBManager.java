/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lorenzo
 */
public class DBManager {
    
    public static final String DB_NAME = "affarefatto";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "";
    private static Connection conn;
    private static PreparedStatement statement;
    
    protected static final PreparedStatement prepareStatement(String query) throws SQLException{
        if(statement != null){
            statement.close();
        }
        statement = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        return statement;
    }
    
    public static final void dbConnect() throws SQLException{
        conn = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME,DB_USER,DB_PASS);
    }
    
    protected static final ResultSet dbSelect(String query) throws SQLException{
        Statement s = conn.createStatement();
        return s.executeQuery(query);
    }
    
    protected static final ResultSet dbSelect() throws SQLException{
        return statement.executeQuery();
    }
    
    protected static final boolean dbUpdate(String query) throws SQLException{
        Statement s = conn.createStatement();
        return s.executeUpdate(query) > 0;
    }
    
    protected static final boolean dbUpdate() throws SQLException{
        return statement.executeUpdate() > 0;
    }
    
    protected static final int dbInsert() throws SQLException{
        boolean ok = statement.executeUpdate() > 0;
        if(ok){
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
            return 0;
        }
        return -1;
    }
    
    public static final void dbDisconnect() throws SQLException{
        if(conn != null){
            conn.close();
            conn = null;
        }
    }
    
    public static Number parseValueResultSet(ResultSet rs) throws SQLException{
        Number val = 0;
        while(rs.next()){
            val = (Number) rs.getObject(1);
        }
        return val;
    }
    
    public static Date parseDateResultSet(ResultSet rs) throws SQLException{
        Date val = null;
        while(rs.next()){
            val = (Date) rs.getObject(1);
        }
        return val;
    }

}

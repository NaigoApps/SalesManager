/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import salesmanager.beans.BeansUtil;
import salesmanager.beans.Register;
import salesmanager.beans.RegisterMovement;
import static salesmanager.beans.dao.DBManager.dbConnect;
import static salesmanager.beans.dao.DBManager.dbSelect;

/**
 *
 * @author Lorenzo
 */
public class DBRegisterManager extends DBManager {

    private static Register resultSetToRegister(ResultSet rs) throws SQLException {
        Register reg = new Register();
        reg.setCode(rs.getInt("code"));
        reg.setMonth(rs.getInt("month"));
        reg.setYear(rs.getInt("year"));
        return reg;
    }
    
    private static RegisterMovement resultSetToRegisterMovement(ResultSet rs) throws SQLException {
        return BeansUtil.copyOf(rs, new RegisterMovement());
    }

    private static Register[] parseRegistersResultSet(ResultSet rs) throws SQLException {
        ArrayList<Register> registers = new ArrayList<>();
        while (rs.next()) {
            registers.add(resultSetToRegister(rs));
        }
        return registers.toArray(new Register[registers.size()]);
    }
    
    private static RegisterMovement[] parseRegisterMovementsResultSet(ResultSet rs) throws SQLException {
        ArrayList<RegisterMovement> registers = new ArrayList<>();
        while (rs.next()) {
            registers.add(resultSetToRegisterMovement(rs));
        }
        return registers.toArray(new RegisterMovement[registers.size()]);
    }
    
    
    public static float getBalanceUntil(RegisterMovement m) throws SQLException{
        String query = "SELECT sum(movementLoadVar) FROM registermovements WHERE movementProgressive <> -1 AND movementProgressive < " + m.getMovementProgressive();
        dbConnect();
        Number val = parseValueResultSet(dbSelect(query));
        dbDisconnect();
        return (val != null)?val.floatValue():0;
    }

    public static Register[] getRegisters() throws SQLException {
        String query = "SELECT * FROM registers;";
        dbConnect();
        Register[] registers = parseRegistersResultSet(dbSelect(query));
        dbDisconnect();
        return registers;
    }

    public static Register getRegister(int month, int year) throws SQLException {
        String query = "SELECT * FROM registers WHERE "
                + "month = " + month + " AND "
                + "year = " + year;
        dbConnect();
        Register[] registers = parseRegistersResultSet(dbSelect(query));
        dbDisconnect();
        if (registers.length == 1) {
            return registers[0];
        }
        return null;
    }
    
    public static RegisterMovement[] getRegisterMovements(Register reg) throws SQLException{
        String query = "SELECT * FROM registermovements WHERE "
                + "registerCode = " + reg.getCode();
        dbConnect();
        RegisterMovement[] registers = parseRegisterMovementsResultSet(dbSelect(query));
        dbDisconnect();
        return registers;
    }

    public static boolean addRegister(int month, int year) throws SQLException {
        String query = "INSERT INTO registers(month,year) "
                + "VALUES(?,?)";

        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setInt(1, month);
        ps.setInt(2, year);
        boolean res = dbUpdate();
        dbDisconnect();
        return res;
    }

    public static boolean addRegisterMovement(RegisterMovement rm) throws SQLException {
        String query = "INSERT INTO registermovements("
                + "registerCode,"
                + "customerCode,"
                + "customerSurname,"
                + "customerName,"
                + "customerAddress,"
                + "customerCap,"
                + "customerCity,"
                + "customerDistrict,"
                + "customerDocument,"
                + "customerDocumentRelease,"
                + "movementProgressive,"
                + "movementDate,"
                + "productName,"
                + "productQta,"
                + "productPrice,"
                + "productCommission,"
                + "movementDescription,"
                + "movementLoadVar,"
                + "totalGoods) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        dbConnect();
        PreparedStatement ps = prepareStatement(query);
        ps.setInt(1, rm.getRegisterCode());
        ps.setInt(2, rm.getCustomerCode());
        ps.setString(3, rm.getCustomerSurname());
        ps.setString(4, rm.getCustomerName());
        ps.setString(5, rm.getCustomerAddress());
        ps.setString(6, rm.getCustomerCap());
        ps.setString(7, rm.getCustomerCity());
        ps.setString(8, rm.getCustomerDistrict());
        ps.setString(9, rm.getCustomerDocument());
        ps.setDate(10, (rm.getCustomerDocumentRelease()!= null) ? new java.sql.Date(rm.getCustomerDocumentRelease().getTime()) : null);
        ps.setInt(11, rm.getMovementProgressive());
        ps.setDate(12, (rm.getMovementDate() != null) ? new java.sql.Date(rm.getMovementDate().getTime()) : null);
        ps.setString(13, rm.getProductName());
        ps.setInt(14, rm.getProductQta());
        ps.setFloat(15, rm.getProductPrice());
        ps.setInt(16, rm.getProductCommission());
        ps.setString(17, rm.getMovementDescription());
        ps.setFloat(18, rm.getMovementLoadVar());
        ps.setFloat(19, rm.getTotalGoods());
        int res = dbInsert();
        dbDisconnect();
        return res != -1;
    }
    
    
    public static boolean removeRegisterMovements(int register) throws SQLException {
        String query = "DELETE FROM registermovements WHERE registerCode = " + register;
        dbConnect();
        boolean res = dbUpdate(query);
        dbDisconnect();
        return res;
    }
}

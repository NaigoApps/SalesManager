/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.tables;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import salesmanager.beans.Movement;
import salesmanager.beans.Product;
import salesmanager.beans.dao.DBMovementsManager;

/**
 *
 * @author Lorenzo
 */
public class MovementsTableModel extends AbstractTableModel {

    private String[] titles = {
        "Codice",
        "Progressivo",
        "Data",
        "Descrizione",
        "Codice prodotto",
        "Variazione"};

    private Date from;
    private Date to;

    private Product product;

    private Movement[] movements;

    public MovementsTableModel(Date da, Date a){
        movements = new Movement[0];
        from = da;
        to = a;
        product = null;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public void loadMovements(Date from, Date to) throws SQLException {
        movements = DBMovementsManager.getMovements(from, to);
        fireTableDataChanged();

    }

    public void loadProductMovements(Product p) throws SQLException{
        movements = DBMovementsManager.getProductMovements(p);
        fireTableDataChanged();
    }

    public void unload() {
        movements = null;
        fireTableDataChanged();
    }

    @Override
    public final int getRowCount() {
        if (movements != null) {
            return movements.length;
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return titles.length;
    }

    @Override
    public String getColumnName(int column) {
        return titles[column];
    }

    @Override
    public final Object getValueAt(int rowIndex, int columnIndex) {
        Movement m = movements[rowIndex];
        switch (columnIndex) {
            case 0:
                return m.getCode();
            case 1:
                if (m.getProgressive() == -1) {
                    return "N/A";
                }
                return m.getProgressive();
            case 2:
                return m.getOperationDate();
            case 3:
                return m.getDescription();
            case 4:
                return m.getProduct();
            case 5:
                return m.getLoadVar();
        }
        return "";
    }

    public final Movement getMovement(int i) {
        //return ProductsManager.instance().getReturnedProducts()[i];
        if (movements != null) {
            return movements[i];
        }
        return null;
    }

    public final Movement[] getMovements() {
        return movements;
    }
    
    public final Movement[] getMovements(int[] indexes){
        ArrayList<Movement> ms = new ArrayList<>(indexes.length);
        for (int i = 0; i < indexes.length; i++) {
            ms.add(movements[indexes[i]]);
        }
        return ms.toArray(new Movement[indexes.length]);
    }

}

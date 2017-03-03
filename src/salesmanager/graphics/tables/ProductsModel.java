/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.tables;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import salesmanager.beans.dao.DBProductsManager;
import salesmanager.beans.Product;
import salesmanager.beans.dao.DBDeliveryManager;
import salesmanager.graphics.Main;

/**
 *
 * @author Lorenzo
 */
public class ProductsModel extends AbstractTableModel {

    private Product[] products;

    public ProductsModel() {
        products = new Product[0];
    }

    private String[] titles = {
        "Codice",
        "Nome",
        "Descrizione",
        "Prezzo",
        "Quantità",
        "Provvigione",
        "Cliente",
        "Fattura"};

    @Override
    public int getColumnCount() {
        return titles.length;
    }

    @Override
    public String getColumnName(int column) {
        return titles[column];
    }

    public void loadOnSaleProducts(int page) throws SQLException {
        products = DBProductsManager.getOnSaleProducts(page);
        fireTableDataChanged();
    }

    public void loadSoldProducts(Date from, Date to) throws SQLException {
        if (from != null && to != null) {
            products = DBProductsManager.getSoldProducts(from, to);
        }
        fireTableDataChanged();
    }

    public void loadReturnedProducts(Date from, Date to) throws SQLException {
        if (from != null && to != null) {
            products = DBProductsManager.getReturnedProducts(from, to);
        } else {
            products = DBProductsManager.getReturnedProducts(Main.lastMonth(), Main.today());
        }
        fireTableDataChanged();
    }

    public void loadSingleProduct(int code) throws SQLException {
        products = new Product[]{DBProductsManager.getProduct(code)};
        fireTableDataChanged();
    }

    public void loadCustomersOnSaleProducts(int code) throws SQLException {
        products = DBProductsManager.getFromClientProductsOnSale(code);
        fireTableDataChanged();
    }

    public void loadCustomersSoldProducts(int code, Date from, Date to) throws SQLException {
        if (from != null && to != null) {
            products = DBProductsManager.getFromClientProductsSold(code, from, to);
        } else {
            products = DBProductsManager.getFromClientProductsSold(code);
        }
        fireTableDataChanged();
    }

    public void loadCustomersReturnedProducts(int code, Date from, Date to) throws SQLException {
        if (from != null && to != null) {
            products = DBProductsManager.getFromClientProductsBack(code, from, to);
        } else {
            products = DBProductsManager.getFromClientProductsBack(code);
        }
        fireTableDataChanged();
    }

    public void loadCustomersSoldProducts(int code) throws SQLException {
        products = DBProductsManager.getFromClientProductsSold(code);
        fireTableDataChanged();
    }

    public void loadCustomersReturnedProducts(int code) throws SQLException {
        products = DBProductsManager.getFromClientProductsBack(code);
    }

    @Override
    public final int getRowCount() {
        if (products != null) {
            return products.length;
        }
        return 0;
    }

    public Object getValueAt(Product p, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return p.getCode();
            case 1:
                return p.getName();
            case 2:
                return p.getDescription();
            case 3:
                return String.format("%5.2f", p.getPrice()) + "€";
            case 4:
                return p.getQta();
            case 5:
                return p.getCommission() + "%";
            case 6: {
                try {
                    return DBDeliveryManager.getProductDeliveryDocument(p).getCustomer();
                } catch (SQLException | NullPointerException ex) {
                    return "ERRORE";
                }
            }
            case 7:
                return (p.getInvoice() == -1) ? "N/A" : p.getInvoice();

        }
        return "";
    }

    @Override
    public final Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < products.length) {
            Product p = products[rowIndex];
            return getValueAt(p, columnIndex);
        }
        return null;
    }

    public final Product getProduct(int i) {
        return products[i];
    }

    public final Product[] getProducts(int[] indexes) {
        ArrayList<Product> ps = new ArrayList<>(indexes.length);
        for (int i = 0; i < indexes.length; i++) {
            ps.add(products[indexes[i]]);
        }
        return ps.toArray(new Product[indexes.length]);
    }

    public Product[] getProducts() {
        return products;
    }

    public void unload() {
        products = null;
        fireTableDataChanged();
    }
}

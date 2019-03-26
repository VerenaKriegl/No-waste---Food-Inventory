package model;

import beans.Product;
import filter.Filter;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

    private ArrayList<Product> products;
    private Filter filter;
    private ArrayList<Product> currentFilteredProducts;
    private final String[] colName
            = {
                "ProductNr", "Bezeichnung", "Kategorie",
                "Ablaufdatum", "TageBisAblauf"
            };

    public TableModel() {
        filter = new Filter();
        products = new ArrayList();
        currentFilteredProducts = new ArrayList();
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public String getColumnName(int column) {
        return colName[column];
    }

    @Override
    public int getColumnCount() {
        return colName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return product.getProductNr();
            case 1:
                return product.getProductName();
            case 2:
                return product.getCategory();
            case 3:
                return product.getExpirationDate();
            case 4:
                return product.getDaysTillExpiration();
            default:
                return "Error";
        }
    }

    public void modify() {
        this.fireTableDataChanged();
    }

    public void add(Product product) {
        products.add(product);
        this.fireTableRowsInserted(0, products.size() - 1);
    }

    public void removeProductAtIndex(int index) {
        products.remove(index);
        this.fireTableRowsDeleted(index, index);
    }

    public void setFilter(String category, String productName) {
        filter.setCategory(category);
        filter.setProductName(productName);
        filter();
    }

    private void filter() {
        currentFilteredProducts.clear();
        for (Product product : products) {
            if (filter.accept(product)) {
                currentFilteredProducts.add(product);
            }
            this.fireTableDataChanged();
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}

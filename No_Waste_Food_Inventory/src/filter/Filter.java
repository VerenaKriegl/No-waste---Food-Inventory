package filter;

import beans.Product;

public class Filter {

    private String productName, category = null;

    public boolean accept(Product p) {
        boolean isValue = false;
        if (p.getProductName().contains(productName) && productName != null) {
            isValue = true;
        }
        if (p.getCategory().contains(category) && category != null) {
            isValue = true;
        }
        return isValue;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

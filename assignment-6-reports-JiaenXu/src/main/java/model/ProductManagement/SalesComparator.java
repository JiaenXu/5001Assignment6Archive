package model.ProductManagement;

import java.util.Comparator;

public class SalesComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        // descending order
        return Integer.compare(p2.getSalesVolume(), p1.getSalesVolume());
    }
}

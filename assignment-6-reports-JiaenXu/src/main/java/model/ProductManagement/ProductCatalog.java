/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import model.CustomerManagement.CustomerProfile;

/**
 *
 * @author kal bugrara
 */
public class ProductCatalog {

    String type;
    ArrayList<Product> products; //list of products initially empty
    //ArrayList<Product> top5products;

    public ProductCatalog(String n) {
        type = n;
        products = new ArrayList();  ///create the list of elements otherwise it is null
    }
// new ProductCatalog(); or new ProductCatalog("Printers");
    public ProductCatalog() {
        type = "unknown";
        products = new ArrayList();
    }

    public Product newProduct(int fp, int cp, int tp) {
        Product p = new Product(fp, cp, tp);
        products.add(p);
        return p;
    }

    public Product newProduct(String n, int fp, int cp, int tp) {
        Product p = new Product(n, fp, cp, tp);
        products.add(p);
        return p;
    }

    public ProductsReport generatProductPerformanceReport(String sortingRule) {
        ProductsReport productsreport = new ProductsReport(sortingRule);

        for (Product p : products) {

            ProductSummary ps = new ProductSummary(p);
            productsreport.addProductSummary(ps);
        }
        return productsreport;
    }

    public ArrayList<Product> getProductList(){
        return products;
    }

    public Product pickRandomProduct(){
        if (products.size() == 0) return null;
        Random r = new Random();
        int randomIndex = r.nextInt(products.size());
        return products.get(randomIndex);
    }

    public int calculateSales() {
        int sum = 0;
        for (Product p : products) {
            sum = sum + p.getSalesVolume();
        }
        return sum;
    }

    public ArrayList<Product> gettop5Sales(){
        ArrayList<Product> top5products = new ArrayList<>();
        for (Product p: products)
        {
            top5products.add(p);
        }
        return top5products;
    }

    
    public int calculateTop5Sales() {
        int sum = 0;
        int count = 0;
    
        SalesComparator salesComparator = new SalesComparator();
        Collections.sort(gettop5Sales(), salesComparator);
    
        for (Product p : gettop5Sales()) {
            if (count < 5) {
                sum = sum + p.getSalesVolume();
                count++;
            } else {
                break;
            }
        }
        return sum;
    }


    public void printShortInfo(){
        System.out.println("There are " + products.size() +  " products in this catalog");
    }


}

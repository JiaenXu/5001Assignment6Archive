/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Supplier;

import java.util.ArrayList;

import com.github.javafaker.Business;

import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.OrderItem;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.ProductSummary;
import model.ProductManagement.ProductsReport;

/**
 *
 * @author kal bugrara
 */
public class Supplier {
    String name;
    ProductCatalog productcatalog;
    ProductsReport productsreport;
    ArrayList<CustomerProfile> customers;
    CustomerDirectory customerdirectory;

    public Supplier(String n) {
        name = n;
        productcatalog = new ProductCatalog("software");

    }

    public ProductsReport prepareProductsReport() {

        productsreport = productcatalog.generatProductPerformanceReport("");
        return productsreport;
    }

    public ArrayList<ProductSummary> getProductsAlwaysAboveTarget() {

        if (productsreport == null)
            productsreport = prepareProductsReport();
        return productsreport.getProductsAlwaysAboveTarget();

    }

    // Get the number of different customers who have bought from this supplier
    public int getNumberOfDifferentCustomers() {
        ArrayList<CustomerProfile> customers = new ArrayList<>();

        for (Product pd : productcatalog.getProductList()) {
            for (OrderItem oi : pd.getOrderItems()) {

                CustomerProfile cp = oi.getOrder().getCustomer();

                if (!customers.contains(cp)) {
                    customers.add(cp);
                }

            }
        }
        return customers.size();
    }

    // Calculate the total sales of this supplier
    public int calculateSales() {
        return productcatalog.calculateSales();
    }

    // Calculate the loyalty score of this supplier
    public float getLoaltyScore(int totalCustomerNumber) {
        int differentCustomersnum = getNumberOfDifferentCustomers();
        float loyaltyScores = (float) differentCustomersnum / totalCustomerNumber;
        return loyaltyScores;
    }

    // Calculate Average Spending Per Customer
    public float getAverageSpending() {
        int totalSales = calculateSales();
        int differentCustomersnum = getNumberOfDifferentCustomers();
        float averageSpending = 0;
        if (totalSales != 0) {
            averageSpending = (float) totalSales / differentCustomersnum;
        }
        return averageSpending;
    }

    public int getTop5SalesVolume() {
        return productcatalog.calculateTop5Sales();
    }

    public String getName() {
        return name;
    }

    public ProductCatalog getProductCatalog() {
        return productcatalog;
    }

    public void printShortInfo() {
        System.out.println("Checking supplier " + name);
        productcatalog.printShortInfo();
    }

    // add supplier product ..

    // update supplier product ...
    @Override
    public String toString() {
        return name;

    }
}

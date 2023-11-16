/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import model.Business.Business;
import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.MarketingManagement.MarketingPersonDirectory;
import model.MarketingManagement.MarketingPersonProfile;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import model.Personnel.EmployeeDirectory;
import model.Personnel.EmployeeProfile;
import model.Personnel.Person;
import model.Personnel.PersonDirectory;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.SalesManagement.SalesPersonDirectory;
import model.SalesManagement.SalesPersonProfile;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.UserAccountManagement.UserAccount;
import model.UserAccountManagement.UserAccountDirectory;

/**
 *
 * @author kal bugrara
 */
public class ConfigureABusiness {

  static int upperPriceLimit = 50;
  static int lowerPriceLimit = 10;
  static int range = 5;
  static int productMaxQuantity = 5;

  public static Business createABusinessAndLoadALotOfData(String name, int supplierCount, int pickedSpCount,
      int productCount,
      int customerCount, int orderCount, int itemCount){

    Business business = new Business(name);

    // Add Suppliers +
    loadSuppliers(business, supplierCount);

    // Add Products +
    loadProducts(business, productCount, pickedSpCount);

    // Add Customers
    loadCustomers(business, customerCount);

    // Add Order
    loadOrders(business, orderCount, itemCount, customerCount);

    return business;
  }

  public static void loadSuppliers(Business b, int supplierCount) {
    Faker faker = new Faker();

    SupplierDirectory supplierDirectory = b.getSupplierDirectory();
    for (int index = 1; index <= supplierCount; index++) {
      supplierDirectory.newSupplier(faker.company().name());
    }
  }

  static void loadProducts(Business b, int productCount, int pickedSpCount) {
    SupplierDirectory supplierDirectory = b.getSupplierDirectory();

    List<Supplier> suppliers = supplierDirectory.getSuplierList();
    Collections.shuffle(suppliers);

    for (int i = 0; i <= pickedSpCount; i++) {
      Supplier supplier = suppliers.get(i);

      // int randomProductNumber = getRandom(1, productCount);
      int givenProductNumber = productCount; // need to assign 50 products to each supplier instead of random number

      ProductCatalog productCatalog = supplier.getProductCatalog();

      // for (int index = 1; index <= randomProductNumber; index++) {
      for (int index = 1; index <= givenProductNumber; index++) {

        String productName = "Product #" + index + " from " + supplier.getName();
        int randomFloor = getRandom(lowerPriceLimit, lowerPriceLimit + range);
        int randomCeiling = getRandom(upperPriceLimit - range, upperPriceLimit);
        int randomTarget = getRandom(randomFloor, randomCeiling);

        productCatalog.newProduct(productName, randomFloor, randomCeiling, randomTarget);
      }
    }
  }

  static int getRandom(int lower, int upper) {
    Random r = new Random();

    // nextInt(n) will return a number from zero to 'n'. Therefore e.g. if I want
    // numbers from 10 to 15
    // I will have result = 10 + nextInt(5)
    int randomInt = lower + r.nextInt(upper - lower);
    return randomInt;
  }

  static void loadCustomers(Business b, int customerCount) {
    CustomerDirectory customerDirectory = b.getCustomerDirectory();
    PersonDirectory personDirectory = b.getPersonDirectory();

    Faker faker = new Faker();

    for (int index = 1; index <= customerCount; index++) {
      Person newPerson = personDirectory.newPerson(faker.name().fullName());
      customerDirectory.newCustomerProfile(newPerson);
    }

    List<CustomerProfile> customers = customerDirectory.getCustomerList();
    Collections.shuffle(customers);
  }

  static void loadOrders(Business b, int orderCount, int itemCount, int customerCount) {

    // reach out to masterOrderList
    MasterOrderList mol = b.getMasterOrderList();
    // Modify here，reach to limited 25 picked customers
    for (int i = 1; i <= customerCount; i++) {

      // pick a random customer (reach to customer directory)
      CustomerDirectory cd = b.getCustomerDirectory();
      SupplierDirectory sd = b.getSupplierDirectory();
      CustomerProfile randomCustomer = cd.pickRandomCustomer();

      if (randomCustomer == null) {
        System.out.println("Cannot generate orders. No customers in the customer directory.");
        return;
      }

      // create an order for that customer
      int randomOrderCount = getRandom(1, orderCount);
      for (int j = 1; j <= randomOrderCount; j++) {
        Order randomOrder = mol.newOrder(randomCustomer);

        // add order items
        // -- pick a supplier first (randomly)
        // -- pick a product (randomly)
        // -- actual price, quantity

        int randomItemCount = getRandom(1, itemCount);
        for (int itemIndex = 1; itemIndex <= randomItemCount; itemIndex++) { // assign 1-10 items here

          Supplier randomSupplier = sd.pickRandomSupplier();

          // if (randomSupplier == null) {
          // System.out.println("Cannot generate orders. No supplier in the supplier
          // directory.");
          // return;
          // }

          ProductCatalog pc = randomSupplier.getProductCatalog();

          while (pc.getProductList().size() == 0) {
            randomSupplier = sd.pickRandomSupplier();
            pc = randomSupplier.getProductCatalog(); 
            // If the supplier has no product, pick another supplier
          }

          // pick a random product from the supplier's product list
          Product randomProduct = pc.pickRandomProduct();

          if (randomProduct == null) {
            System.out.println("Cannot generate orders. No products in the product catalog.");
            return;
          }

          int randomPrice = getRandom(randomProduct.getFloorPrice(), randomProduct.getCeilingPrice());
          int randomQuantity = getRandom(1, productMaxQuantity);

          OrderItem oi = randomOrder.newOrderItem(randomProduct, randomPrice, randomQuantity);
          
          //Necessary for later calculation, access the orderitem list in product
          randomProduct.addOrderItem(oi); 
        }
      }
    }

  }

}

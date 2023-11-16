package model.Supplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import com.github.javafaker.Business;

import model.Business.ConfigureABusiness;
import model.CustomerManagement.CustomerDirectory;

public class SupplierReport {
    //int numberOfAllCustomers;
    ArrayList<SupplierSummary> supplierSummaryList;
    
    public SupplierReport() {
        //this.numberOfAllCustomers = cb.getTotalCustomerNumber();
        supplierSummaryList = new ArrayList<>();
    }

    public void addSupplierSummary(SupplierSummary ss) {
        supplierSummaryList.add(ss);
    }

    public void printSupplierReport() {
        System.out.println("Supplier Report");
        System.out.println("Supplier | Total Sales");
        System.out.println("---------------------");
        for (SupplierSummary ss : supplierSummaryList) {
            ss.printSupplierSummary();
        }
    }


}

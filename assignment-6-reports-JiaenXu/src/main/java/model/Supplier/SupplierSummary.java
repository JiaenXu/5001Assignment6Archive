package model.Supplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class SupplierSummary {

    Supplier supplier;
    String name;
    float totalSales;

    //int customerNumber;
    float loyaltyScores;
    Set<Supplier> supplierSummaryList;
    float averageSpending;
    float top5SalesVolume;
    float top5SalesScore;

    public SupplierSummary(Supplier s, int cN) {
        supplier = s;
        name = s.getName();
        totalSales = s.calculateSales();
        loyaltyScores = s.getLoaltyScore(cN);
        averageSpending = s.getAverageSpending();
        top5SalesVolume = s.getTop5SalesVolume();
        //top5SalesScore = top5SalesVolume / totalSales;
    }

    public float getTotalSales(){
        return totalSales;
    }

    // public float getTop5SalesScore(){
    //     top5SalesScore =  top5SalesVolume / totalSales;
    //     return top5SalesScore;
    // }

    public void printSupplierSummary(){
        System.out.println("Supplier Name: " + supplier.getName() + " |  Total Sales: " + totalSales + " |  Loyalty Scores: " + loyaltyScores  + " |  Average Spending Per Customer: " + averageSpending);
        System.out.println();
        System.out.println("Top 5 Sales Score: " + top5SalesVolume / totalSales);
        System.out.println("--------------------------------------------------");
    }
}

package com.company;

public class Beverage {

    private String beverageID;
    private String beverageName;
    private double beveragePrice;
    private static int beverageCount;
    private Ingredients[] beverageIngredients;
    private double beverageCostPrice;
    private int beverageSoldQuantity;
    private double beverageProfit;

    public Beverage(){
        this("Pending..", 0.0, 0.0);
    }

    public Beverage(String beverageName, double beveragePrice, double beverageCostPrice){
        this.beverageID = "BV" + (101 + beverageCount);
        this.beverageName = beverageName;
        this.beveragePrice = beveragePrice;
        this.beverageCostPrice = beverageCostPrice;
        this.beverageProfit = beveragePrice - beverageCostPrice;
        beverageCount++;
        Menu.menuCount++;
    }

    public Beverage(String beverageName, double beveragePrice, double beverageCostPrice, int beverageSoldQuantity){
        this.beverageID = "BV" + (101 + beverageCount);
        this.beverageName = beverageName;
        this.beveragePrice = beveragePrice;
        this.beverageCostPrice = beverageCostPrice;
        this.beverageSoldQuantity = beverageSoldQuantity;
        this.beverageProfit = beveragePrice - beverageCostPrice;
        beverageCount++;
        Menu.menuCount++;
    }

    //GETTER
    public String getBeverageID() {
        return beverageID;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public double getBeveragePrice() {
        return beveragePrice;
    }

    public double getBeverageCostPrice() {
        return beverageCostPrice;
    }

    public static int getBeverageCount() {
        return beverageCount;
    }

    public int getBeverageSoldQuantity() {
        return beverageSoldQuantity;
    }

    public double getBeverageProfit() {
        return beverageProfit;
    }

    //SETTER
    public void setBeverageName(String beverageName) {
        this.beverageName = beverageName;
    }

    public void setBeveragePrice(double beveragePrice) {
        this.beveragePrice = beveragePrice;
    }

    public static void setBeverageCount(int beverageCount) {
        Beverage.beverageCount = beverageCount;
    }

    public Ingredients[] getIngredients() {
        return beverageIngredients;
    }

    public void setIngredients(Ingredients[] ingredients) {
        this.beverageIngredients = ingredients;
    }


    //toString
    @Override
    public String toString() {
        return String.format("| %-11s | %-16s | %10.2f |", beverageID, beverageName, beveragePrice);

    }
}


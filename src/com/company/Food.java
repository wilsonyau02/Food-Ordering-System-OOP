package com.company;

public class Food {

    private String foodID;
    private String foodName;
    private double foodPrice;
    private static int foodCount;
    private Ingredients[] foodIngredients;
    private double foodCostPrice;
    private int foodSoldQuantity;
    private double foodProfit;

    public Food(){
        this("Pending..", 0.0, 0.0);
    }

    public Food(String foodName, double foodPrice){
        this.foodID = "FD" + (101 + foodCount);
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        foodCount++;
        Menu.menuCount++;
    }

    public Food(String foodName, double foodPrice, double foodCostPrice){
        this.foodID = "FD" + (101 + foodCount);
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodCostPrice = foodCostPrice;
        this.foodProfit = foodPrice - foodCostPrice;
        foodCount++;
        Menu.menuCount++;
    }

    public Food(String foodName, double foodPrice, double foodCostPrice, int foodSoldQuantity){
        this.foodID = "FD" + (101 + foodCount);
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodCostPrice = foodCostPrice;
        this.foodSoldQuantity = foodSoldQuantity;
        this.foodProfit = foodPrice - foodCostPrice;
        foodCount++;
        Menu.menuCount++;
    }

    //GETTER
    public String getFoodID() {
        return foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public static int getFoodCount() {
        return foodCount;
    }
    public double getFoodCostPrice() {
        return foodCostPrice;
    }

    public int getFoodSoldQuantity() {
        return foodSoldQuantity;
    }

    public double getFoodProfit() {
        return foodProfit;
    }

    //SETTER
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public static void setFoodCount(int foodCount) {
        Food.foodCount = foodCount;
    }

    public Ingredients[] getIngredients() {
        return foodIngredients;
    }

    public void setIngredients(Ingredients[] foodIngredients) {
        this.foodIngredients = foodIngredients;
    }

    //toString
    @Override
    public String toString() {
        return String.format("| %-11s | %-16s | %10.2f |", foodID, foodName, foodPrice);
    }

}

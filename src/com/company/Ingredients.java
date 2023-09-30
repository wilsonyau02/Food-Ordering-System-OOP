package com.company;

public class Ingredients implements Comparable {
    private String ingredientName;
    private String measurementUnit;
    private double ingredientQuantity = 0;


    public Ingredients() {
    }

    public Ingredients(String ingredientName, double ingredientQuantity) {
        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
        this.measurementUnit = "";
    }

    public Ingredients(String ingredientName, String measurementUnit, double ingredientQuantity) {
        this.ingredientName = ingredientName;
        this.measurementUnit = measurementUnit;
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public double getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(double ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }


    @Override
    public String toString() {
        return "Ingredients{" +
                "ingredientName='" + ingredientName + '\'' +
                ", measurementUnit='" + measurementUnit + '\'' +
                ", ingredient Quantity = " + ingredientQuantity + "\'" +
                '}';
    }

    @Override
    public int compareTo(Object i) {
        return toString().compareTo(i.toString());
    }

}

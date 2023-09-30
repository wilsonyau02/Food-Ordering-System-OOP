package com.company;

import java.util.ArrayList;

public class Menu {
    public static int menuCount;

    private static ArrayList<Food> food = new ArrayList<>();
    private static ArrayList<Beverage> beverage = new ArrayList<>();

    //GETTER
    public static ArrayList<Food> getFood() {
        return food;
    }

    public static ArrayList<Beverage> getBeverage() {
        return beverage;
    }

    public void defaultMenu(){
        //String foodID, String foodName, double foodPrice, String foodDescription

        food.add(new Food("Chicken Burger", 12.0, 3.5));
        food.add(new Food("Fish Burger", 14.0,3));
        food.add(new Food("Fried Chicken", 14.0,4));
        food.add(new Food("French Fries", 14.0,2));
        food.add(new Food("Cheese Hot dog", 10.0,2));

        beverage.add(new Beverage("Coca-Cola", 5.0,1));
        beverage.add(new Beverage("7 Up", 5.0,1));
        beverage.add(new Beverage("Pepsi", 4.5,1));
        beverage.add(new Beverage("Ice Lemon Tea", 6.0,1.5));
        beverage.add(new Beverage("Mineral Water", 3.0,0.5));
    }

    public  void displayMenu(int option){

        System.out.println("+" + "-".repeat(45) + "+");

        if (option == 1){

            System.out.printf("| %-11s | %-16s | %-10s \n|%s+%s+%s|\n","Food ID", "Food Name", "Price (RM) |", "-".repeat(13), "-".repeat(18),"-".repeat(12));

            for (Food foodArray: food){
                System.out.println(foodArray);
            }

        }
        else {
            System.out.printf("| %-11s | %-16s | %-10s \n|%s+%s+%s|\n","Beverage ID", "Beverage Name", "Price (RM) |", "-".repeat(13), "-".repeat(18),"-".repeat(12));

            for (Beverage beverageArray: beverage){
                System.out.println(beverageArray);
            }
        }
        System.out.println("+" + "-".repeat(45) + "+");

    }

    public void setIngredients(){

        //Chicken Burger
        food.get(0).setIngredients(new Ingredients[]{
                new Ingredients("Burger Bread","Slice",1),
                new Ingredients("Egg","",1),
                new Ingredients("Ground Chicken","Pound",1),
                new Ingredients("Mayonnaise Sauce","Gram",50),
                new Ingredients("Cabbage","Gram",30),
                new Ingredients("Tomato Slice","Slice",3)
        });

        //Fish Burger
        food.get(1).setIngredients(new Ingredients[]{
                new Ingredients("Fish Fillet","Slice",1),
                new Ingredients("Burger Bread","Slice",1),
                new Ingredients("Cheese slice","Slice",1),
                new Ingredients("Mayonnaise Sauce","Gram",50)
        });

        //Fried Chicken
        food.get(2).setIngredients(new Ingredients[]{
                new Ingredients("Chicken Pieces","Piece",1),
                new Ingredients("Flour", "Gram", 20)
        });

        //French Fries
        food.get(3).setIngredients(new Ingredients[]{
                new Ingredients("Potato","",2)
        });

        //Cheese Hot Dog
        food.get(4).setIngredients(new Ingredients[]{
                new Ingredients("Hod Dog","",1),
                new Ingredients("Cheese Sauce","Gram",50),
                new Ingredients("Flour","Gram",100)
        });

        beverage.get(0).setIngredients(new Ingredients[]{
                new Ingredients("Coca-Cola","Milliliter",400)
        });

        beverage.get(1).setIngredients(new Ingredients[]{
                new Ingredients("7 Up","Milliliter",400)
        });

        beverage.get(2).setIngredients(new Ingredients[]{
                new Ingredients("Pepsi","Milliliter",400)
        });

        beverage.get(3).setIngredients(new Ingredients[]{
                new Ingredients("Black Tea","Milliliter",350),
                new Ingredients("Lemon Juice","Milliliter", 50)

        });

        beverage.get(4).setIngredients(new Ingredients[]{
                new Ingredients("Mineral Water","Milliliter",400)
        });
    }







}

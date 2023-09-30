package com.company;


import java.util.ArrayList;

public class Manager extends Staff{

    public Menu menu = new Menu();



    public Manager(){}

    public Manager(String name, String userID, String password, String contact, String email, String nricNum,
                   String staffType, String dateJoined, double basicSalary){
        super(name, userID, password, contact, email, nricNum, staffType, dateJoined, basicSalary);
    }

    //Setter and getter
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public ArrayList<Account> addStaff(Staff staff, ArrayList<Account> staffAcc){
        staffAcc.add(staff);
        return staffAcc;
    }

    public ArrayList<Account> removeStaff(ArrayList<Account> staffAcc, int index){
        staffAcc.remove(index);
        return staffAcc;
    }
    public ArrayList<Account> editStaffName(ArrayList<Account> staffAcc, int index, String name){
        staffAcc.get(index).setName(name);
        return staffAcc;
    }

    public ArrayList<Account> editStaffContact(ArrayList<Account> staffAcc, int index, String contact){
        staffAcc.get(index).setContact(contact);
        return staffAcc;
    }

    public ArrayList<Account> editStaffEmail(ArrayList<Account> staffAcc, int index, String email){
        staffAcc.get(index).setEmail(email);
        return staffAcc;
    }

    public ArrayList<Account> editStaffBasicSalary(ArrayList<Account> staffAcc, int index, double salary){
        if (staffAcc.get(index) instanceof  Staff){
            ((Staff) staffAcc.get(index)).setBasicSalary(salary);
        }
        return staffAcc;
    }

    public void addNewFood(Food newFood, int index, Ingredients[] ingredients){
        Menu.getFood().add(newFood);
        Menu.getFood().get(index).setIngredients(ingredients);
    }

    public void addNewBeverage(Beverage newBeverage, int index, Ingredients[] ingredients){
        Menu.getBeverage().add(newBeverage);
        Menu.getBeverage().get(index).setIngredients(ingredients);
    }

    public void deleteFood(int index){
        Menu.getFood().remove(index);
    }

    public void deleteBeverage(int index){
        Menu.getBeverage().remove(index);
    }

    public void editFoodItem(String name, double price, int index){
        Menu.getFood().get(index).setFoodName(name);
        Menu.getFood().get(index).setFoodPrice(price);
    }

    public void editBeverageItem(String name, double price, int index){
        Menu.getBeverage().get(index).setBeverageName(name);
        Menu.getBeverage().get(index).setBeveragePrice(price);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

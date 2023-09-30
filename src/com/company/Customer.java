package com.company;

import java.util.ArrayList;

public class Customer extends Account{

    private Review review = new Review();

    private ArrayList<Integer> customerOrder = new ArrayList<>();

    private static int custUserCount;

    public Customer (){
    }

    public Customer (String userName, String password, String contact){
        super(userName, ("CU" + (101 + custUserCount)), password, contact, "-");
        custUserCount++;
    }

    public Customer (String userName, String password, String contact, String email){
        super(userName, ("CU" + (101 + custUserCount)), password, contact, email);
        custUserCount++;
    }

    //GETTER
    public ArrayList<Integer> getCustomerOrder() {
        return customerOrder;
    }

    public Review getReview() {
        return review;
    }

    //METHOD
    public void addCustomerOrder(int orderNum ){
        customerOrder.add(orderNum);
    }

    @Override
    public String toString() {
        return "\n--Registered Information-- "
                + "\nUser ID   : " + super.getUserID()
                + "\nUser Name : " + super.getName()
                + "\nContact No: " + super.getContact()
                + "\nEmail     : " + super.getEmail();
    }
}
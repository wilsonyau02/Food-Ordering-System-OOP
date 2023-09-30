package com.company;

import java.util.ArrayList;

public class Order implements Cloneable{

    private static int orderCount = 0;
    private String orderID;
    private double total;
    private Payment payment;
    private String deliveryMethod;


    public Order(){
        ArrayList<Food> foodList = Menu.getFood();
        System.out.println();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String orderIDGenerator(){
        orderCount++;
        this.orderID = "00" + orderCount;
        return this.orderID;
    }

    public String displayTotal(){
        return String.format("%58s %14.2f", "Total:", getTotal()) + "\n+" + "-".repeat(73) + "+";
    }

    public void copyTo(Order o) {
        o.orderID = this.orderID;
        o.total = this.total;
        o.payment = this.payment;
        o.deliveryMethod = this.deliveryMethod;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

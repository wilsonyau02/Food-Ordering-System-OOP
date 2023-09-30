package com.company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DeliveryOrder extends Order{
    private String deliveryID, deliveryAddress;
    private LocalDateTime deliveryTime;
    private double deliveryFee, distance;
    private static int deliveryNum = 1;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' hh:mm a");
    private boolean assigned = false;


    public DeliveryOrder() {}

    public DeliveryOrder(String deliveryAddress) {
        setDistance();
        this.deliveryAddress = deliveryAddress;
        this.deliveryTime = LocalDateTime.now().plusMinutes((long)(Math.random() * 7 + 8));
        if (this.distance <= 8) {
            this.deliveryFee = 3;
        }else {
            this.deliveryFee = 3 + ((distance-8) * 0.7);
        }
    }

    public DeliveryOrder(String deliveryAddress, int minute) {
        setDistance();
        this.deliveryAddress = deliveryAddress;
        this.deliveryTime = getLocalDateTime(minute);
        if (this.distance <= 8) {
            this.deliveryFee = 3;
        }else {
            this.deliveryFee = 3 + ((distance-8) * 0.7);
        }
    }

    public DeliveryOrder(String deliveryAddress, int hour, int minute) {
        setDistance();
        this.deliveryAddress = deliveryAddress;
        this.deliveryTime = getLocalDateTime(hour, minute);
        if (this.distance <= 8) {
            this.deliveryFee = 3;
        }else {
            this.deliveryFee = 3 + ((distance-8) * 0.7);
        }
    }

    public DeliveryOrder(String deliveryAddress, int numDaysAfterToday, int hour, int minute) {
        setDistance();
        this.deliveryAddress = deliveryAddress;
        this.deliveryTime = getLocalDateTime(numDaysAfterToday, hour, minute);
        if (this.distance <= 8) {
            this.deliveryFee = 3;
        }else {
            this.deliveryFee = 3 + ((distance-8) * 0.7);
        }
    }

    public void setDeliveryTime(int hour, int minute, int afterNumDays) {
        this.deliveryTime = getLocalDateTime(hour, minute, afterNumDays);
    }

    public void setDeliveryTime(int hour, int minute) {
        this.deliveryTime = getLocalDateTime(hour, minute);
    }

    public void setDeliveryTime(int minute) {
        this.deliveryTime = getLocalDateTime(minute);
    }

    public void setDeliveryTime() {
        this.deliveryTime = LocalDateTime.now().plusMinutes(20);
    }

    public double getDistance() {
        return distance;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        setDistance();
        if (this.distance <= 8) {
            this.deliveryFee = 3;
        }else {
            this.deliveryFee = 3 + ((distance-8) * 0.7);
        }
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }



    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public void setDeliveryID() {
        if (deliveryNum < 1000) {this.deliveryID = String.format("D%6s", deliveryNum).replace(' ', '0');}
        else {
            this.deliveryID = "D" + deliveryNum;
        }
        deliveryNum++;
    }

    private void setDistance() {
        double i = Math.random() * 20;
        this.distance = Double.parseDouble(String.format("%.1f", i));
    }

    public String printDeliveryOrder() {
        return "Address: " + this.deliveryAddress +
                "\nDistance: " + this.distance + "km" +
                "\nDelivery Time: " + formatter.format(deliveryTime) +
                "\nDelivery Fee: " + String.format("RM %.2f", this.deliveryFee);
    }

    public String traceDeliveryOrder() {
        if (LocalDateTime.now().isBefore(this.deliveryTime.minusMinutes(16))) {return "Not yet preparing order";}
        else if (LocalDateTime.now().isAfter(this.deliveryTime.minusMinutes(16)) &&
                LocalDateTime.now().isBefore(this.deliveryTime.minusMinutes(8))) {return "Preparing order";}
        else if (LocalDateTime.now().isAfter(this.deliveryTime.minusMinutes(8)) &&
                LocalDateTime.now().isBefore(this.deliveryTime)){return "Delivering order";}
        else {return "Completed";}
    }

    public static LocalDateTime getLocalDateTime(int minute) {
        LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), minute));
        return dateAndTime;
    }

    public static LocalDateTime getLocalDateTime(int hour, int minute) {
        LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute));
        return dateAndTime;
    }

    public static LocalDateTime getLocalDateTime(int hour, int minute, int afterNumDays) {
        LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.now().plusDays(afterNumDays), LocalTime.of(hour, minute));
        return dateAndTime;
    }
}
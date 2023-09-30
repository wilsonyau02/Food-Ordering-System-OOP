package com.company;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class DeliveryMan extends Staff implements Salary{
    private final double DELIVERYCOMISSION = 5;
    private String vehicleType;
    private int monthlyOrdersSent;
    private ArrayList<DeliveryOrder> delivery = new ArrayList<DeliveryOrder>();

    public enum TimePeriod{
        D,
        W,
        M,
        Y,
        A
    }

    public DeliveryMan(){}

    public DeliveryMan(String vehicleType) {
        this.vehicleType = vehicleType;
        this.monthlyOrdersSent = 0;
    }
    public DeliveryMan(String name, String userID, String password, String contact, String email, String nricNum,
                       String staffType, String dateJoined, double basicSalary, int monthlyOrdersSent){
        super(name, userID, password, contact, email, nricNum,  staffType, dateJoined, basicSalary);
        this.monthlyOrdersSent = monthlyOrdersSent;

    }

    public ArrayList<DeliveryOrder> getDelivery() {
        return delivery;
    }

    public int getMonthlyOrdersSent() {
        return monthlyOrdersSent;
    }


    public void addDelivery(DeliveryOrder deliveryOrder) {
        this.delivery.add(deliveryOrder);
    }

    public void delDelivery(int index) {
        delivery.get(index).setAssigned(false);
        delivery.remove(index);
    }

    public String deliveryHistory(TimePeriod timePeriod) {
        StringBuilder history = new StringBuilder();
        try {
            switch (timePeriod) {
                case D:
                    LocalDate today = LocalDate.now();
                    for (int i = delivery.size()-1; i >= 0 &&
                            (delivery.get(i).getDeliveryTime().toLocalDate().equals(today) ||
                                    delivery.get(i).getDeliveryTime().toLocalDate().equals(today.minusDays(-1))); i--) {
                        history.append(delivery.get(i).printDeliveryOrder() + "\n\n");
                    }
                    break;
                case W:
                    LocalDate date = LocalDate.now();
                    int dayOfWeek = date.getDayOfWeek().getValue();
                    LocalDate weekStart = date.minusDays(dayOfWeek + 7);
                    LocalDate weekEnd = date.minusDays(dayOfWeek + 1);
                    for (int i = delivery.size()-1; i >= 0 &&
                            (delivery.get(i).getDeliveryTime().toLocalDate().isAfter(weekStart) &&
                                    delivery.get(i).getDeliveryTime().toLocalDate().isBefore(weekEnd)); i--) {
                        history.append(delivery.get(i).printDeliveryOrder() + "\n\n");
                    }
                    break;
                case M:
                    Month previousMonth = LocalDate.now().minusMonths(1).getMonth();
                    for (int i = delivery.size()-1; i >= 0 && delivery.get(i).getDeliveryTime().getMonth().equals(previousMonth); i--) {
                        history.append(delivery.get(i).printDeliveryOrder() + "\n\n");
                    }
                    break;
                case Y:
                    int previousYear = LocalDate.now().minusYears(1).getYear();
                    for (int i = delivery.size()-1; i >= 0 && delivery.get(i).getDeliveryTime().getYear() == previousYear; i--) {
                        history.append(delivery.get(i).printDeliveryOrder() + "\n\n");
                    }
                    break;
                case A:
                    for (int i = 0; i < delivery.size(); i++){
                        history.append(delivery.get(i).printDeliveryOrder());
                    }

            }
        } catch (IndexOutOfBoundsException e) {
            return "Unable to display history\n";
        }
        return history.toString();
    }


    private int calcMonthlyOrdersSent() {
        int i = 0;
        Month previousMonth = LocalDate.now().minusMonths(1).getMonth();
        for (int j = delivery.size()-1; delivery.get(delivery.size()-1).getDeliveryTime().getMonth().equals(previousMonth); j--) {
            i++;
        }
        return i;
    }

    @Override
    public void calculateSalary() {
        setTotalSalary(super.getBasicSalary() + (monthlyOrdersSent * this.DELIVERYCOMISSION));
    }
}

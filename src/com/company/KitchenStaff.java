package com.company;

public class KitchenStaff extends Staff implements Salary {


    private int overTimeHours;

    public KitchenStaff(){

    }

    public KitchenStaff(String name, String userID, String contact, String email, String nricNum,
                        String staffType, String dateJoined, double basicSalary, int overtimeHours){
        super(name, userID, contact, email, nricNum, staffType, dateJoined, basicSalary);
        this.overTimeHours = overtimeHours;
    }

    public int getOverTimeHours() {
        return overTimeHours;
    }

    @Override
    public void calculateSalary() {
        double salaryPerDay = getBasicSalary() / 26;
        double hourlyRate = salaryPerDay / 8;
        if (overTimeHours == 0){
            setTotalSalary(getBasicSalary());
        }
        else{
            setTotalSalary(getBasicSalary() + (overTimeHours * hourlyRate * 1.5));
        }

    }
}


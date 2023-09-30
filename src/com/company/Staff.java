package com.company;

public class Staff extends Account{
    private String nricNum;
    private String staffType;
    private String dateJoined;
    private double basicSalary;
    private double totalSalary;


    public Staff(){}

    public Staff(String name, String userID, String contact, String email,String nricNum,
                 String staffType, String dateJoined, double salary){
        super(name, userID, contact, email);
        this.nricNum = nricNum;
        this.staffType = staffType;
        this.dateJoined = dateJoined;
        this.basicSalary = salary;
    }

    public Staff(String name, String userID, String password, String contact, String email,String nricNum,
                 String staffType, String dateJoined, double salary){
        super(name, userID, password, contact, email);
        this.nricNum = nricNum;
        this.staffType = staffType;
        this.dateJoined = dateJoined;
        this.basicSalary = salary;
    }

    //Setter and getter


    public String getNricNum() {
        return nricNum;
    }

    public String getStaffType() {
        return staffType;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Staff{" +
                "nricNum='" + nricNum + '\'' +
                ", staffType='" + staffType + '\'' +
                ", dateJoined='" + dateJoined + '\'' +
                ", basicSalary=" + basicSalary +
                ", totalSalary=" + totalSalary +
                '}';
    }
}

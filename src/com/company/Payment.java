package com.company;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {

    private boolean paymentStatus = false;

    private String paymentMethod;
    private LocalDate paymentDate;
    private LocalDateTime paymentTime;

    //wallet
    private double balance;

    //card
    private Long cardNo;
    private String cvv;
    private int expDateMonth;
    private int expDateYear;

    public Payment(){

    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setExpDateMonth(int expDateMonth) {
        this.expDateMonth = expDateMonth;
    }

    public void setExpDateYear(int expDateYear) {
        this.expDateYear = expDateYear;
    }

    public Long getCardNo() {
        return cardNo;
    }

    public String getCvv() {
        return cvv;
    }

    public int getExpDateMonth() {
        return expDateMonth;
    }

    public int getExpDateYear() {
        return expDateYear;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDate getPaymentDate() {
        this.paymentDate = LocalDateTime.now().toLocalDate();
        return this.paymentDate;
    }
}

package com.company;

public class Account {
    private String name, userID, password, contact, email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public Account() {
        this.name = "";
        this.userID = "";
        this.password = "";
        this.contact = "";
        this.email = "";
    }

    public Account(String userID, String password){
        this();
        this.userID = userID;
        this.password = password;
    }

    public Account(String name, String userID, String contact, String email){
        this.name = name;
        this.userID = userID;
        this.email = email;
        this.contact = contact;
        this.password = "-";
    }

    public Account(String name, String userID, String password, String contact, String email){
        this.name = name;
        this.userID = userID;
        this.password = password;
        this.contact = contact;
        this.email = email;
    }

    public boolean checkPassword(String password) {
        if (this.password.equals(password)) {return true;}
        else {return false;}
    }

    @Override
    public String toString() {
        return "--Account--" +
                "\nID: " + name +
                "\nName: " + userID +
                "\nPassword: '" + password +
                "\nContact: " + contact +
                "\nEmail: " + email;
    }

}
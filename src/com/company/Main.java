package com.company;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Account> staffAcc = new ArrayList<>();
    public static ArrayList<Account> custAcc = new ArrayList<>();
    public static ArrayList<Food> foodList = Menu.getFood();
    public static ArrayList<Beverage> beverageList = Menu.getBeverage();
    public static ArrayList<Ingredients> currentIngredients = new ArrayList<>();
    public static String[][] orderedItem;
    public static int option;
    public static int custIndex;
    public static int staffIndex;
    public static ArrayList<Order> orderHistory = new ArrayList<Order>();
    public static Order order = new Order();
    public static int orderNum = 0;
    public static Menu menu = new Menu();



    public static void main(String[] args) {
        //Initialize sample data
        initializeStaff();
        initializeCustomer();
        initializeIngredients();
        menu.defaultMenu();
        menu.setIngredients();
        orderedItem = new String[Menu.menuCount][2];
        int[][][] foodOrder = new int[100][Food.getFoodCount()][2];   //Because without declare a size, it is impossible to store the second order
        int[][][] beverageOrder = new int[100][Beverage.getBeverageCount()][2];
        Payment payment = new Payment();


        boolean continueStatus = true;
        while (continueStatus){
            displayMainMenu();
            option = takeMenuOption(0,2);
            switch (option) {
                case 0 -> continueStatus = false;
                case 1 -> {
                    System.out.println("\n\n=====================");
                    System.out.println("    Login Session");
                    System.out.println("=====================");
                    staffIndex = login(staffAcc);
                    determineStaffType();
                }
                case 2 -> {
                    boolean continueStatus2 = true;
                    while (continueStatus2) {
                        System.out.println("\n[1] Login");
                        System.out.println("[2] Register");
                        System.out.println("[0] Back");
                        option = takeMenuOption(0, 2);
                        switch (option) {
                            case 1 -> {
                                System.out.println("\n\n=====================");
                                System.out.println("    Login Session");
                                System.out.println("=====================");
                                custIndex = login(custAcc);
                                foodOrderProcess(foodOrder, beverageOrder, payment);
                            }
                            case 2 -> {
                                scanner.nextLine();
                                System.out.println("\n\n=======================");
                                System.out.println("    Register Session");
                                System.out.println("=======================");
                                register();
                            }
                            case 0 -> continueStatus2 = false;
                        }

                    }
                }
            }

        }
        System.out.println("Thank you");
        System.exit(0);

    }


    public static void initializeStaff(){
        staffAcc = new ArrayList<>(Arrays.asList(
                new Manager("Jack Ma", "M001", "M001JackMa", "012-0806520", "jackMa@gmail.com", "900806-01-0905",
                        "Manager", "28/02/2022",5000),
                new Manager("Mark Lee", "M002", "M002MarkLee", "010-8888521", "marklee02@gmail.com", "911108-04-1547",
                        "Manager", "28/02/2022",5000),
                new KitchenStaff("Kelly", "K001", "012-5201314", "kelly1221@gmail.com","000720-01-5044",
                        "Kitchen Staff", "01/03/2022", 2000,10),
                new KitchenStaff("Jerry", "K002", "011-4566543", "jerrylim@gmail.com", "010407-01-8569",
                        "Kitchen Staff", "01/03/2022", 2000,20),
                new DeliveryMan("Muhammad", "D001", "D001Muhammad", "011-1144745", "muhammad02@gmail.com", "990405-04-8571",
                        "Delivery Man", "01/03/2022", 2000, 200),
                new DeliveryMan("Nelson", "D002", "D002Nelson", "017-4555332", "nelson02@gmail.com","020218-01-0705",
                        "Delivery Man", "01/03/2022", 2000,180)
        ));
    }

    public static void initializeCustomer(){
        custAcc = new ArrayList<>(Arrays.asList(
                new Customer("Jennie", "aloha123.", "011-7155933", "jennie@gmail.com"),
                new Customer("Jisoo", "jisoo888", "010-7892103", "jisoo@gmail.com"),
                new Customer("Wilson", "Wilson321.", "015-8963118", "wilson@gmail.com"),
                new Customer("Alex", "Alex456.", "017-5962378", "alex@gmail.com"),
                new Customer("Phua Chu Kang", "chukang000.", "012-5639874", "phuack@gmail.com")
        ));
    }

    public static void initializeIngredients(){
        currentIngredients = new ArrayList<>(Arrays.asList(
                new Ingredients("Burger Bread","Slice", 300),
                new Ingredients("Egg", "", 200),
                new Ingredients("Ground Chicken","Pound",200),
                new Ingredients("Fish Fillet","Slice",300),
                new Ingredients("Cheese Slice","Slice",100),
                new Ingredients("Cheese Sauce", "Gram",3000),
                new Ingredients("Chicken Pieces","Piece", 500),
                new Ingredients("Potato","", 50),
                new Ingredients("Hot Dog", "", 30),
                new Ingredients("Mayonnaise Sauce","Gram",2000),
                new Ingredients("Cabbage","Gram",3000),
                new Ingredients("Tomato Slice","Slice",1000),
                new Ingredients("Flour", "Gram", 5000),

                new Ingredients("Coca-Cola","Liter",500),
                new Ingredients("7 Up","Liter",1000),
                new Ingredients("Pepsi","Liter",1000),
                new Ingredients("Black Tea","Liter",1000),
                new Ingredients("Lemon Juice","Liter", 1000),
                new Ingredients("Mineral Water","Liter",1000)
        ));
    }

    public static void displayMainMenu(){
        System.out.println("\n\n+" + "=".repeat(39) + "+");
        System.out.println("|   Welcome to MFC Fast Food Restaurant |");
        System.out.println("+" + "=".repeat(39) + "+");
        System.out.println("Choose the account type: ");
        System.out.println("[1] Staff");
        System.out.println("[2] Customer");
        System.out.println("[0] Exit");
    }

    public static int login(ArrayList<Account> staffAcc){
        scanner.nextLine();
        while (true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            for (int i = 0; i < staffAcc.size(); i++) {
                if (!(staffAcc.get(i) instanceof KitchenStaff)){
                    if (staffAcc.get(i).getName().equals(username)){
                        if (staffAcc.get(i).checkPassword(password)){
                            System.out.println("Logged in successfully\n");
                            System.out.println("Press Enter key to proceed next...");
                            scanner.nextLine();
                            return i;
                        }
                    }
                    if (i == staffAcc.size()-1) {
                        System.out.println("Username or password is incorrect");
                    }
                }

            }
        }
    }

    public static void register(){
        boolean reenterOption;
        Pattern pattern;
        Matcher matcher;

        String name, password, contact, email;

        //Username
        do {
            reenterOption = true;
            System.out.print("\nEnter User Name: ");
            name = scanner.nextLine();
            name = name.trim();
            for (Account customer: custAcc){
                if (customer instanceof Customer){
                    if (name.trim().equals(customer.getName())){
                        System.out.println("The username is taken. Try another.");
                        reenterOption = true;
                        break;
                    }
                    else {
                        reenterOption = false;
                    }
                }
            }

        }while (reenterOption);

        //Password
        do {
            reenterOption = true;

            while (true) {
                int alphabet = 0;
                int number = 0;
                int others = 0;

                System.out.print("\nEnter Password: ");
                password = scanner.nextLine();

                for (int i = 0; i < password.length(); i++){
                    if (Character.isAlphabetic(password.charAt(i))){
                        alphabet++;
                    }
                    else if (Character.isDigit(password.charAt(i))){
                        number++;
                    }
                    else {
                        others++;
                    }
                }

                if (alphabet > 0 && number > 0 && others > 0){
                    break;
                }
                else {
                    System.out.println("Password must include at least 1 alphabet, 1 number & 1 special character.");
                }
            }

            System.out.print("Confirm Password: ");
            String confirmPwd = scanner.nextLine();

            if (confirmPwd.equals(password)){
                reenterOption = false;
            }
            else {
                System.out.println("Passwords must be same.");
            }

        } while (reenterOption);

        //Contact No
        System.out.print("\nEnter Contact Number (Format: 01x-xxxx xxxx): ");
        contact = scanner.nextLine();

        do {
            reenterOption = true;

            pattern = Pattern.compile("^(\\+?6?01)[0-46-9]-*[0-9]{7,8}$");
            matcher = pattern.matcher(contact);
            boolean matchFound = matcher.matches();
            while (!matchFound) {
                System.out.println("Incorrect Contact Number Format. Please follow format (01x-xxxx xxxx)).");
                System.out.print("\nEnter Contact Number again: ");
                contact = scanner.nextLine();
                matcher = pattern.matcher(contact);
                matchFound = matcher.matches();
            }

            for (Account customer: custAcc){
                if (customer instanceof Customer){
                    if (contact.equals(customer.getContact())){
                        System.out.println("The contact number is taken. Try another.");
                        reenterOption = true;
                        break;
                    }
                    else {
                        reenterOption = false;
                    }
                }
            }
            if (reenterOption){
                System.out.print("\nEnter Contact Number again: ");
                contact = scanner.nextLine();
            }
        }while (reenterOption);


        //Email Address
        boolean reenterEmail;


        System.out.println("\nPress [ENTER] to skip.");
        System.out.print("Enter Email (Optional): ");
        email = scanner.nextLine();

        do {
            if (email.isEmpty()){
                reenterEmail= false;
            }
            else {
                pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                matcher = pattern.matcher(email);
                boolean matchFound = matcher.matches();
                if (!matchFound) {
                    System.out.println("\nFormat: def@gmail.com");
                    System.out.println("Incorrect Email Format. Please follow format provided above.");
                    reenterEmail = true;
                }
                else {
                    reenterEmail = false;
                }
            }
            for (Account customer: custAcc){
                if (customer instanceof Customer){
                    if (email.equals(customer.getEmail())){
                        System.out.println("The email is taken. Try another.");
                        reenterEmail = true;
                        break;
                    }
                    else {
                        reenterEmail = false;
                    }
                }
            }
            if (reenterEmail){
                System.out.println("Press [ENTER] to skip.");
                System.out.print("Enter email again (Optional): ");
                email = scanner.nextLine();
            }
        } while (reenterEmail);

        System.out.println("\nPersonal information details");
        System.out.println("=".repeat(30));
        System.out.println("Name: " + name);
        System.out.println("Password: " + password);
        System.out.println("Contact: " + contact);
        System.out.print("Email: ");
        if (email.isEmpty()){
            System.out.println("-");
        }
        else {
            System.out.println(email);
        }

        System.out.print("Confirm the information details? (Y-Yes/N-No) : ");
        String input = scanner.nextLine();
        input = checkInput(input);

        if (input.equals("Y")){
            if (email.isEmpty()){
                custAcc.add(new Customer(name, password, contact));
            }
            else {
                custAcc.add(new Customer(name, password, contact, email));
            }

            System.out.println("\nCongrats. Registered successfully.");
            System.out.println(custAcc.get(custAcc.size() - 1));
        }
        else{
            System.out.println("\nYou will be directed to the previous page.");
        }
        System.out.println("Press Enter key to proceed next...");
        scanner.nextLine();
    }

    public static void foodOrderProcess(int[][][] foodOrder, int[][][] beverageOrder, Payment payment) {

        do {
            System.out.println("\n+" + "-".repeat(23) + "+");
            System.out.println("|       Order Menu      |");
            System.out.println("+" + "-".repeat(23) + "+");
            System.out.println("[1] Order");
            System.out.println("[2] Modify Order Item");
            System.out.println("[3] Clear Order");
            System.out.println("[4] Display Order List");
            System.out.println("[5] Proceed");
            System.out.println("[6] Trace Order");
            System.out.println("[7] Give Review");
            System.out.println("[0] Back");

            option = takeMenuOption(0, 7);

            switch (option){
                case 1:
                    makeOrder(foodOrder, beverageOrder, payment);
                    break;
                case 2:
                    modifyOrder(foodOrder, beverageOrder, payment);
                    break;
                case 3:
                    clearOrder(foodOrder, beverageOrder, payment);
                    break;
                case 4:
                    displayOrderList(foodOrder, beverageOrder, payment);
                    calculateTotalPayment(foodOrder, beverageOrder, payment);
                    if(order.getTotal() == 0){
                        System.out.println("\nPlease order something first...");
                    }
                    break;
                case 5:
                    calculateTotalPayment(foodOrder, beverageOrder, payment);
                    if(order.getTotal() == 0){
                        System.out.println("\nPlease order something first...");
                    }
                    else{
                        orderConfirmation(foodOrder, beverageOrder, payment);
                    }
                    break;
                case 6:
                    if (payment.getPaymentStatus()) {
                        ArrayList<Integer> orderNumArr = (ArrayList<Integer>) ((Customer)custAcc.get(custIndex)).getCustomerOrder().clone();
                        System.out.println("\n" + "-".repeat(24));
                        for (int i = 0; i < orderNumArr.size(); i++) {
                            if((orderHistory.get(i).getDeliveryMethod().equals("Delivery"))){
                                System.out.println("Order ID: " + orderHistory.get(orderNumArr.get(i)).getOrderID());
                                System.out.println(((DeliveryOrder)orderHistory.get(orderNumArr.get(i))).traceDeliveryOrder());
                            }
                            else if((orderHistory.get(i).getDeliveryMethod().equals( "Self Pick Up"))){
                                System.out.println("Order ID: " + orderHistory.get(orderNumArr.get(i)).getOrderID());
                                System.out.println(((SelfPickUp)orderHistory.get(orderNumArr.get(i))).tracePickupOrder());
                            }
                            if (i < orderNumArr.size()-1){
                                System.out.println();
                            }
                        }
                        System.out.println("-".repeat(24));
                    } else {
                        System.out.println("\n***No order to trace.");
                    }
                    break;

                case 7:
                    int rating = 0;
                    String description;
                    //rating
                    do {
                        try {
                            System.out.print("\nRating (0-5): ");
                            rating = scanner.nextInt();

                            if (rating < 0 || rating > 5) {
                                System.out.println("Only rating within 0 and 5 is accepted");
                            }
                        }catch (InputMismatchException e) {
                            System.out.println("Only digits within 0 and 5 is accepted");
                        }
                    }while (rating < 0 || rating > 5);

                    scanner.nextLine();
                    System.out.print("Description: ");
                    description = scanner.nextLine();
                    ((Customer)custAcc.get(custIndex)).getReview().setReview(rating, description);

                    System.out.println("Thank you for giving the review （*＾ワ＾*）");
                    System.out.println("Press Enter key to go back to Order Menu...");
                    scanner.nextLine();
                    break;
                default:
                    return;
            }

        } while(true);
    }

    public static void makeOrder(int[][][] foodOrder, int[][][] beverageOrder, Payment payment){

        String continueOrder;

        do {
            System.out.println("\n[1] Food Menu");
            System.out.println("[2] Beverage Menu");
            System.out.println("[0] Back");

            int menuOption = takeMenuOption(0, 2);

            if (menuOption == 0){
                return;
            }
            else {
                displayMenu(menuOption);

                if (menuOption == 1) {
                    int order = takeMenuOption(1, Food.getFoodCount());
                    foodOrder[orderNum][order - 1][0] = order;
                    foodOrder[orderNum][order - 1][1] += takeQuantityOrder();

                    System.out.println("\nFood: " + foodList.get(order-1).getFoodName());
                    System.out.println("Quantity: " + foodOrder[orderNum][order - 1][1]);

                }
                else {
                    int order = takeMenuOption(1, Beverage.getBeverageCount());
                    beverageOrder[orderNum][order - 1][0] = order;
                    beverageOrder[orderNum][order - 1][1] += takeQuantityOrder();

                    System.out.println("\nBeverage: " + beverageList.get(order-1).getBeverageName());
                    System.out.println("Quantity: " + beverageOrder[orderNum][order - 1][1]);
                }
                System.out.print("Has been added to the order list.\n\nDo you want to continue order (Y/N): ");
                continueOrder = scanner.nextLine();

                continueOrder = checkInput(continueOrder);
            }

        } while (continueOrder.equals("Y"));

    }

    public static void modifyOrder(int[][][] foodOrder, int[][][] beverageOrder, Payment payment){
        String[] itemArray;

        displayOrderList(foodOrder, beverageOrder, payment);

        if (orderedItem[0][0] == null){
            System.out.println("\nPlease order something first...");
        }
        else {
            System.out.println("[1] Remove Ordered Item");
            System.out.println("[2] Increase Quantity");
            System.out.println("[3] Decrease Quantity");
            System.out.println("[0] Back");

            int menuOption = takeMenuOption(0, 3);

            if (menuOption == 0){
                return;
            }
            else {

                itemArray = orderedItemMatch();

                if (menuOption == 1) {
                    removeOrder(itemArray, foodOrder, beverageOrder, payment);
                }
                else if (menuOption == 2) {
                    increaseQuantity(itemArray, foodOrder, beverageOrder, payment);
                }
                else {
                    decreaseQuantity(itemArray, foodOrder, beverageOrder, payment);
                }

                displayOrderList(foodOrder, beverageOrder, payment);

            }
        }
    }

    public static String[] orderedItemMatch() {

        String itemID;
        String itemIndex = "";
        String[] orderArray = new String[2];
        boolean reenterID = true;

        do {
            System.out.print("\nEnter the ID: ");
            scanner.nextLine();
            itemID = scanner.nextLine();

            for (String[] strings : orderedItem) {
                if (itemID.equalsIgnoreCase(strings[0])) {
                    itemIndex = strings[1];
                    reenterID = false;
                    break;
                }
            }

            if (reenterID) {
                System.out.println("\nThere is no such ID found.");
                if (retryOption() == 1) {
                    continue;
                } else {
                    orderArray[0] = null;
                    orderArray[1] = null;
                    break;
                }
            }
            else {
                orderArray[0] = itemID;
                orderArray[1] = itemIndex;
            }

        } while (reenterID);

        return orderArray;
    }

    public static void removeOrder(String[] itemArray, int[][][] foodOrder, int[][][] beverageOrder, Payment payment){

        if (itemArray[0] != null){
            if (Character.toUpperCase(itemArray[0].charAt(0)) == 'F') {
                Arrays.fill(foodOrder[orderNum][Integer.parseInt(itemArray[1])], 0);

            }
            else {
                Arrays.fill(beverageOrder[orderNum][Integer.parseInt(itemArray[1])], 0);
            }

            for (String[] order : orderedItem) {
                Arrays.fill(order, null);
            }
        }

    }

    public static void increaseQuantity(String[] itemArray, int[][][] foodOrder, int[][][] beverageOrder, Payment payment){

        int quantity = takeQuantityOrder();

        if (Character.toUpperCase(itemArray[0].charAt(0))  == 'F') {
            foodOrder[orderNum][Integer.parseInt(itemArray[1])][1] += quantity;
        } else {
            beverageOrder[orderNum][Integer.parseInt(itemArray[1])][1] += quantity;
        }
    }

    public static void decreaseQuantity(String[] itemArray, int foodOrder[][][], int beverageOrder[][][], Payment payment){

        boolean reenterQty = true;

        do {
            int quantity = takeQuantityOrder();

            if (Character.toUpperCase(itemArray[0].charAt(0))  == 'F' && quantity < foodOrder[orderNum][Integer.parseInt(itemArray[1])][1]) {
                foodOrder[orderNum][Integer.parseInt(itemArray[1])][1] -= quantity;
                reenterQty = false;
            } else if (Character.toUpperCase(itemArray[0].charAt(0)) == 'B' && quantity < beverageOrder[orderNum][Integer.parseInt(itemArray[1])][1]) {
                beverageOrder[orderNum][Integer.parseInt(itemArray[1])][1] -= quantity;
                reenterQty = false;
            } else {
                System.out.println("Quantity entered must lesser than previous order quantity");
                if (retryOption() != 1) {
                    break;
                }
            }


        } while (reenterQty);

    }

    public static void clearOrder(int[][][] foodOrder, int[][][] beverageOrder, Payment payment) {

        displayOrderList(foodOrder, beverageOrder, payment);

        if (orderedItem[0][0] == null){
            System.out.println("\nPlease order something first...");
        }
        else {
            System.out.println("Confirm Clear ?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");

            if (takeMenuOption(1, 2) == 1) {

                for (int k = 0; k < Food.getFoodCount(); k++) {
                    for (int m = 0; m < 2; m++) {
                        foodOrder[orderNum][k][m] = 0;
                    }
                }
                for (int k = 0; k < Beverage.getBeverageCount(); k++) {
                    for (int m = 0; m < 2; m++) {
                        beverageOrder[orderNum][k][m] = 0;
                    }
                }

                for (int i = 0; i < Menu.menuCount; i++) {
                    Arrays.fill(orderedItem[orderNum], null);
                }

                System.out.println("\nYour order has been clear...");

            }
        }

    }

    public static void displayMenu(int option) {

        System.out.println("\n+" + "-".repeat(37) + "+");

        if (option == 1) {
            System.out.printf("| %-3s | %-16s | %-10s \n|%s+%s+%s|\n", "No.", "Food Name", "Price (RM) |", "-".repeat(5), "-".repeat(18), "-".repeat(12));
            for (int i = 0; i < foodList.size(); i++) {
                System.out.printf("| %2s  | %-16s | %10.2f |\n", (i + 1), foodList.get(i).getFoodName(), foodList.get(i).getFoodPrice());
            }
        } else {
            System.out.printf("| %-3s | %-16s | %-10s \n|%s+%s+%s|\n", "No.", "Beverage Name", "Price (RM) |", "-".repeat(5), "-".repeat(18), "-".repeat(12));
            for (int i = 0; i < beverageList.size(); i++) {
                System.out.printf("| %2s  | %-16s | %10.2f |\n", (i + 1), beverageList.get(i).getBeverageName(), beverageList.get(i).getBeveragePrice());
            }
        }

        System.out.println("+" + "-".repeat(37) + "+\nPlace Your Order Below.\n");
    }

    public static void displayOrderList(int[][][] foodOrder, int[][][] beverageOrder, Payment payment) {

        int orderCount = 0;
        int displayDash = 0;

        System.out.println();
        System.out.println("+" + "-".repeat(73) + "+");
        System.out.printf("| %-49s |\n|%s+%s+%s+%s+%s|\n", "\t\t\t\t\t\t\t\tOrder List", "-".repeat(13), "-".repeat(18), "-".repeat(12), "-".repeat(10), "-".repeat(16));
        System.out.printf("| %-11s | %-16s | %-10s| %-8s | %14s |\n|%s+%s+%s+%s+%s|\n", "ID", "Name", "Price (RM) ", "Quantity", "Sub Total (RM)", "-".repeat(13), "-".repeat(18), "-".repeat(12), "-".repeat(10), "-".repeat(16));

        for (int k = 0; k < foodOrder[orderNum].length; k++) {
            if (foodOrder[orderNum][k][0] != 0) {
                System.out.print(foodList.get(foodOrder[orderNum][k][0] - 1));
                System.out.printf(" %8d |", foodOrder[orderNum][k][1]);
                System.out.printf(" %14.2f | \n", foodList.get(foodOrder[orderNum][k][0] - 1).getFoodPrice() * foodOrder[orderNum][k][1]);
                orderedItem[orderCount][0] = String.valueOf(foodList.get(foodOrder[orderNum][k][0] - 1).getFoodID());
                orderedItem[orderCount][1] = String.valueOf(k);
                orderCount++;
                displayDash = 1;
            }
            else {
                continue;
            }
        }

        if (displayDash == 1) {
            System.out.println("+" + "-".repeat(73) + "+");
            displayDash = 0;
        }

        for (int k = 0; k < beverageOrder[orderNum].length; k++) {
            if (beverageOrder[orderNum][k][0] != 0) {
                System.out.print(beverageList.get(beverageOrder[orderNum][k][0] - 1));
                System.out.printf(" %8d |", beverageOrder[orderNum][k][1]);
                System.out.printf(" %14.2f | \n", beverageList.get(beverageOrder[orderNum][k][0] - 1).getBeveragePrice() * beverageOrder[orderNum][k][1]);
                orderedItem[orderCount][0] = String.valueOf(beverageList.get(beverageOrder[orderNum][k][0] - 1).getBeverageID());
                orderedItem[orderCount][1] = String.valueOf(k);
                orderCount++;
                displayDash = 1;
            }
            else {
                continue;
            }
        }

        if (displayDash == 1) {
            System.out.println("+" + "-".repeat(73) + "+");
        }
    }

    public static void calculateTotalPayment(int[][][] foodOrder, int[][][] beverageOrder, Payment payment) {
        double totalAmountFood = 0;
        double totalAmountBeverage = 0;


        for (int k = 0; k < foodOrder[orderNum].length; k++) {
            if (foodOrder[orderNum][k][0] != 0) {
                totalAmountFood += foodList.get(foodOrder[orderNum][k][0] - 1).getFoodPrice() * foodOrder[orderNum][k][1];
            }
        }

        for (int k = 0; k < beverageOrder[orderNum].length; k++) {
            if (beverageOrder[orderNum][k][0] != 0) {
                totalAmountBeverage += beverageList.get(beverageOrder[orderNum][k][0] - 1).getBeveragePrice() * beverageOrder[orderNum][k][1];
            }
        }

//        if((totalAmountFood + totalAmountBeverage) == 0){
//            System.out.println("------------------ Please make an complete order first. ---------------------");
//        }
        if( (totalAmountFood+totalAmountBeverage) != 0 ){
            order.setTotal((totalAmountFood + totalAmountBeverage));
        }
    }

    public static void orderConfirmation(int[][][] foodOrder, int[][][] beverageOrder, Payment payment) {
        while(true){
            try{
                displayOrderList(foodOrder, beverageOrder, payment);
                calculateTotalPayment(foodOrder, beverageOrder, payment);
                System.out.println(order.displayTotal());
                System.out.println("\n\n=====================================");
                System.out.println("Confirm Order?");
                System.out.println("[1] Yes");
                System.out.println("[2] No");
                option = scanner.nextInt();
                if (option == 1) {
                    chooseDeliveryMethod(foodOrder, beverageOrder, payment);
                    break;
                }
                else if (option == 2){
                    displayOrderList(foodOrder, beverageOrder, payment);
                    break;
                }
                else{
                    System.out.println("--> Must be 1 or 2 only.");
                    scanner.nextLine();
                    orderConfirmation(foodOrder, beverageOrder, payment);
                }
            }
            catch (InputMismatchException e){
                System.out.println("--> Must be number");
                scanner.nextLine();
                orderConfirmation(foodOrder, beverageOrder, payment);
            }
        }
    }

    public static void chooseDeliveryMethod(int[][][] foodOrder, int[][][] beverageOrder, Payment payment) {
        while(true){
            try{
                System.out.println("\nChoose Delivery Method");
                System.out.println("=====================================");
                System.out.println("[1] Delivery");
                System.out.println("[2] Self-Pickup");
                option = scanner.nextInt();
                if (option == 1) {
                    DeliveryOrder deliveryOrder = new DeliveryOrder();
                    order.setDeliveryMethod("Delivery");
                    order.copyTo(deliveryOrder);
                    delivery(foodOrder, beverageOrder, payment, deliveryOrder);
                    break;
                }
                else if (option == 2){
                    SelfPickUp selfPickUp = new SelfPickUp();
                    order.setDeliveryMethod("Self Pick Up");
                    order.copyTo(selfPickUp);
                    selfPickup(foodOrder, beverageOrder, payment, selfPickUp);
                    break;
                }
                else{
                    System.out.println("--> Must be 1 or 2 only.");
                    scanner.nextLine();
                    chooseDeliveryMethod(foodOrder, beverageOrder, payment);
                }
            }
            catch (InputMismatchException e){
                System.out.println("--> Must be number");
                scanner.nextLine();
                chooseDeliveryMethod(foodOrder, beverageOrder, payment);
            }
        }
    }

    public static void selfPickup(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, Order orderWithMethod) {
        while(true){
            try{
                SelfPickUp selfPickUp = (SelfPickUp) orderWithMethod;
                System.out.println("\n=====================================");
                System.out.println("When do you want to pick up your order?");
                System.out.println("[1] Within 10 minutes.");
                System.out.println("[2] Today.");
                System.out.println("[3] Another day.");
                option = scanner.nextInt();
                if (option == 1) {
                    selfPickUp.setSelfPickUpTime();
                    selfPickUp.printSelfPickupDetails();
                    deliveryMethodConfirmation(foodOrder, beverageOrder, payment, selfPickUp);
                    payment(foodOrder, beverageOrder, payment, selfPickUp);
                    break;
                }
                else if (option == 2){
                    pickUpToday(foodOrder, beverageOrder, payment, selfPickUp);
                    break;
                }
                else if (option == 3){
                    pickUpAnotherDay(foodOrder, beverageOrder, payment, selfPickUp);
                    break;
                }
                else{
                    System.out.println("--> Must be 1,2 or 3 only.");
                    selfPickup(foodOrder, beverageOrder, payment, orderWithMethod);
                }
            }
            catch (InputMismatchException e){
                System.out.println("--> Must be number");
                scanner.nextLine();
                selfPickup(foodOrder, beverageOrder, payment, orderWithMethod);
            }
        }
    }

    public static void pickUpAnotherDay(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, SelfPickUp selfPickUp) {
        int hour;
        int minute;
        int days;
        while (true) {
            days = daysValidation();
            hour = hourValidation();
            minute = minValidation();

            LocalDateTime selectedTime = LocalDateTime.now().plusDays(days).withHour(hour).withMinute(minute);
            if (selectedTime.isAfter(LocalDateTime.now().plusMinutes(10))){
                break;
            }else if (selectedTime.isAfter(LocalDateTime.now())){
                System.out.println("--> Selected time is too close to the current date and time");
                System.out.println("--> We need approximately 10 minute to prepare the order");
            }else {
                System.out.println("--> Selected time cannot be earlier than current time");
            }
        }
        selfPickUp.setSelfPickUpTime(hour, minute, days);
        selfPickUp.printSelfPickupDetails();
        deliveryMethodConfirmation(foodOrder, beverageOrder, payment, selfPickUp);
        payment(foodOrder, beverageOrder, payment, selfPickUp);
    }

    public static void pickUpToday(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, SelfPickUp selfPickUp) {
        int hour;
        int minute;
        while (true) {
            hour = hourValidation();
            minute = minValidation();

            LocalTime selectedTime = LocalTime.now().withHour(hour).withMinute(minute);
            if (selectedTime.isAfter(LocalTime.now().plusMinutes(10))){
                break;
            }else if (selectedTime.isAfter(LocalTime.now())){
                System.out.println("--> Selected time is too close to the current time");
                System.out.println("--> We need approximately 10 minute to prepare the order");
            }else {
                System.out.println("--> Selected time cannot be earlier than current time");
            }
        }
        selfPickUp.setSelfPickUpTime(hour, minute);
        selfPickUp.printSelfPickupDetails();
        deliveryMethodConfirmation(foodOrder, beverageOrder, payment, selfPickUp);
        payment(foodOrder, beverageOrder, payment, selfPickUp);
    }

    public static void delivery(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, Order orderWithMethod) {
        while(true){
            try{
                DeliveryOrder deliveryOrder = (DeliveryOrder)orderWithMethod;
                System.out.println("\n=====================================");
                scanner.nextLine();
                System.out.print("Enter Delivery Address: ");
                String address = scanner.nextLine();
                deliveryOrder.setDeliveryAddress(address);
                System.out.println("\n=====================================");
                System.out.println("When do you want this to be delivered?");
                System.out.println("[1] Within 20 minutes.");
                System.out.println("[2] Today.");
                System.out.println("[3] Another day.");
                option = scanner.nextInt();
                if (option == 1) {
                    deliveryOrder.setDeliveryTime();
                    printDeliveryDetails(deliveryOrder);
                    deliveryMethodConfirmation(foodOrder, beverageOrder, payment, deliveryOrder);
                    payment(foodOrder, beverageOrder, payment, deliveryOrder);
                    break;
                }
                else if (option == 2) {
                    deliverToday(foodOrder, beverageOrder, payment, deliveryOrder);
                    break;
                }
                else if (option == 3) {
                    deliverAnotherDay(foodOrder, beverageOrder, payment, deliveryOrder);
                    break;
                }
                else{
                    System.out.println("--> Must be 1 or 2 only.");
                    scanner.nextLine();

                    delivery(foodOrder, beverageOrder, payment, orderWithMethod);
                }
            }
            catch (InputMismatchException e){
                System.out.println("--> Must be number");
                scanner.nextLine();
                delivery(foodOrder, beverageOrder, payment, orderWithMethod);
            }
        }
    }

    public static void deliverToday(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, DeliveryOrder deliveryOrder) {
        int hour;
        int minute;
        while (true) {
            hour = hourValidation();
            minute = minValidation();

            LocalTime selectedTime = LocalTime.now().withHour(hour).withMinute(minute);
            if (selectedTime.isAfter(LocalTime.now().plusMinutes(20))){
                break;
            }else if (selectedTime.isAfter(LocalTime.now())){
                System.out.println("--> Selected time is too close to the current time");
                System.out.println("--> We need approximately 20 minute to prepare the order");
            }else {
                System.out.println("--> Selected time cannot be earlier than current time");
            }
        }
        deliveryOrder.setDeliveryTime(hour, minute);
        printDeliveryDetails(deliveryOrder);
        deliveryMethodConfirmation(foodOrder, beverageOrder, payment, deliveryOrder);
        payment(foodOrder, beverageOrder, payment, deliveryOrder);
    }

    public static void deliverAnotherDay(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, DeliveryOrder deliveryOrder) {
        int hour;
        int minute;
        int days;
        while (true) {
            days = daysValidation();
            hour = hourValidation();
            minute = minValidation();


            LocalDateTime selectedTime = LocalDateTime.now().plusDays(days).withHour(hour).withMinute(minute);
            if (selectedTime.isAfter(LocalDateTime.now().plusMinutes(20))){
                break;
            }else if (selectedTime.isAfter(LocalDateTime.now())){
                System.out.println("--> Selected time is too close to the current date and time");
                System.out.println("--> We need approximately 20 minute to prepare the order");
            }else {
                System.out.println("--> Selected time cannot be earlier than current time");
            }
        }

        deliveryOrder.setDeliveryTime(hour, minute, days);
        printDeliveryDetails(deliveryOrder);
        deliveryMethodConfirmation(foodOrder, beverageOrder, payment, deliveryOrder);
        payment(foodOrder, beverageOrder, payment, deliveryOrder);
    }

    public static void printDeliveryDetails(DeliveryOrder deliveryOrder) {
        System.out.println("\n                                Delivery Details");
        System.out.println("======================================================================================");
        System.out.print(deliveryOrder.printDeliveryOrder());
        System.out.printf("\nTotal Amount : RM %.2f", (deliveryOrder.getDeliveryFee()+order.getTotal()));
        System.out.println("\n======================================================================================");
    }

    public static void deliveryMethodConfirmation(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, Order orderWithMethod) {
        while (true) {
            try {
                System.out.println("\n\nConfirm Delivery Method?");
                System.out.println("=====================================");
                System.out.println("[1] Yes");
                System.out.println("[2] Choose Other Delivery Method");
                option = scanner.nextInt();
                if (option == 1) {
//                    payment(foodOrder, beverageOrder, payment, orderWithMethod);
                    break;
                } else if (option == 2) {
                    chooseDeliveryMethod(foodOrder, beverageOrder, payment);
                    break;
                } else {
                    System.out.println("--> Must be 1 or 2 only.");
                    chooseDeliveryMethod(foodOrder, beverageOrder, payment);
                }
            } catch (InputMismatchException e) {
                System.out.println("--> Must be number");
                scanner.nextLine();
                deliveryMethodConfirmation(foodOrder, beverageOrder, payment, orderWithMethod);
            }
        }
    }

    public static void payment(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, Order orderWithMethod) {
        while(true){
            try{
                System.out.println("\n\nChoose Payment Method");
                System.out.println("=====================================");
                System.out.println("[1] Card");
                System.out.println("[2] Wallet");
                option = scanner.nextInt();
                if(option == 1){
                    cardPayment(foodOrder, beverageOrder, payment, orderWithMethod);
                    break;
                }
                else if(option == 2){
                    walletPayment(foodOrder, beverageOrder, payment, orderWithMethod);
                    break;
                }
                else{
                    System.out.println("--> Must be 1 or 2 only.");
                    scanner.nextLine();
                    payment(foodOrder, beverageOrder, payment, orderWithMethod);
                }
            }
            catch (InputMismatchException e){
                System.out.println("--> Must be number.");
                scanner.nextLine();
                payment(foodOrder, beverageOrder, payment, orderWithMethod);
            }
        }
    }

    public static void walletPayment(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, Order orderWithMethod) {
        while(true){
            try{
                DeliveryOrder deliveryOrder;
                double deliveryFee = 0;
                System.out.println("\n-------------------------------------");
                System.out.printf("Balance: RM %.2f", payment.getBalance());
                if (orderWithMethod instanceof DeliveryOrder) {
                    deliveryOrder = (DeliveryOrder) orderWithMethod;
                    deliveryFee = deliveryOrder.getDeliveryFee();
                }
                if(payment.getBalance() == 0 || (payment.getBalance() <  (order.getTotal() + deliveryFee))){
                    System.out.printf("\nYou have to pay: RM %.2f", (order.getTotal() + deliveryFee));
                    System.out.println("\n-------------------------------------");
                    System.out.println("--> Hey! Please top up your wallet balance!");
                    System.out.println("--> Unless you want to change payment method.");
                    System.out.println("--> Otherwise you cannot make any order.");
                    System.out.println("=====================================");
                    System.out.println("[1] Top Up");
                    System.out.println("[2] Change to card payment");
                    System.out.println("[3] Cancel order, and go back to menu.");
                    option = scanner.nextInt();
                    if(option == 1){
                        topUp(foodOrder, beverageOrder, payment, orderWithMethod);
                        break;
                    }
                    else if(option == 2){
                        cardPayment(foodOrder, beverageOrder, payment, orderWithMethod);
                        break;
                    }
                    else if(option == 3){
                        makeOrder(foodOrder, beverageOrder, payment);
                        break;
                    }
                    else{
                        System.out.println("--> Must be 1 or 2 or 3 only.");
                        scanner.nextLine();
                        walletPayment(foodOrder, beverageOrder, payment, orderWithMethod);
                    }
                }
                else{
                    System.out.printf("\nYou have to pay: RM %.2f", (order.getTotal() + deliveryFee));
                    System.out.printf("\nWallet Balance Left: RM %.2f", (payment.getBalance() - (order.getTotal() + deliveryFee)));
                    System.out.println("\n-------------------------------------");
                    System.out.println("[1] Confirm payment");
                    System.out.println("[2] Change to card payment");
                    System.out.println("[3] Cancel order, and go back to menu.");
                    option = scanner.nextInt();
                    if(option == 1){
                        payment.setBalance(payment.getBalance() - (order.getTotal() + deliveryFee));
                        payment.setPaymentMethod("Wallet");
                        receipt(foodOrder, beverageOrder, payment, orderWithMethod);

                        break;
                    }
                    else if(option == 2) {
                        cardPayment(foodOrder, beverageOrder, payment, orderWithMethod);
                        break;
                    }
                    else if(option == 3){
                        makeOrder(foodOrder, beverageOrder, payment);
                        break;
                    }
                    else{
                        System.out.println("--> Must be 1 or 2 only.");
                        scanner.nextLine();
                        walletPayment(foodOrder, beverageOrder, payment, orderWithMethod);
                    }
                }
            }
            catch (InputMismatchException e){
                System.out.println("--> Must be number.");
                scanner.nextLine();
                walletPayment(foodOrder, beverageOrder, payment, orderWithMethod);
            }
        }
    }


    public static void topUp(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, Order orderWithMethod) {

        boolean reenter;

        do {
            reenter = true;
            try {
                System.out.println("\n=====================================");
                System.out.print("Enter top up amount: RM ");
                double balance = scanner.nextDouble();
                payment.setBalance(payment.getBalance() + balance);
                walletPayment(foodOrder, beverageOrder, payment, orderWithMethod);
                reenter = false;
//                option = scanner.nextInt();
//                if (option == 1) {
//                    receipt(foodOrder, beverageOrder, payment, orderWithMethod);
//                    break;
//                }
//                else if (option == 2){
//                    makeOrder(foodOrder, beverageOrder, payment);
//                    break;
//                }
//                else{
//                    System.out.println("--> Must be 1 or 2 only.");
//                    scanner.nextLine();
//                    walletPayment(foodOrder, beverageOrder, payment, orderWithMethod);
//                }
            }
            catch (InputMismatchException e) {
                System.out.println("--> Must be number");
                scanner.nextLine();
            }

        }while (reenter);
    }

    public static void receipt(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, Order orderWithMethod) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' hh:mm a");
        DeliveryOrder deliveryOrder = null;
        SelfPickUp selfPickUp;
        payment.setPaymentStatus(true);
        System.out.println("                                Thank You                     ");
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("%-50s %12s", ("Order ID: " + orderWithMethod.orderIDGenerator()), ("Payment Date: " + payment.getPaymentDate()));
        System.out.printf("%-40s %35s", ("\nDelivery Method: " + order.getDeliveryMethod()), ("Paid by: " + payment.getPaymentMethod()));
        try {
            if(orderWithMethod instanceof DeliveryOrder){
                deliveryOrder = (DeliveryOrder) orderWithMethod;
                deliveryOrder.setDeliveryID();
                System.out.println("\nDelivery Address: " + deliveryOrder.getDeliveryAddress());
                orderHistory.add((DeliveryOrder)deliveryOrder.clone());
            }else {
                selfPickUp = (SelfPickUp) orderWithMethod;
                System.out.println("\nSelf pickup time: " + formatter.format(selfPickUp.getSelfPickUpTime()));
                orderHistory.add((SelfPickUp)selfPickUp.clone());
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Could not add the order to history");
        }
        displayOrderList(foodOrder, beverageOrder, payment);

        if (orderWithMethod instanceof DeliveryOrder){
            System.out.printf("%59s %13.2f", "Delivery Fee: ", deliveryOrder.getDeliveryFee());
            System.out.printf("\n%59s %13.2f", "Grand Total: ", (deliveryOrder.getDeliveryFee() + order.getTotal()));
        }else {
            System.out.printf("%59s %13.2f", "Grand Total: ", (order.getTotal()));
        }
        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("                Press Enter key to go back to Order menu...");
        scanner.nextLine();
        scanner.nextLine();
        decreaseInventory(foodOrder,beverageOrder);
        ((Customer) custAcc.get(custIndex)).addCustomerOrder(orderNum);
        orderNum++;
        order = new DeliveryOrder();
        //clear current orderArray
        for (int i = 0; i < orderedItem.length; i++) {
            orderedItem[i][0] = null;
            orderedItem[i][1] = null;
        }

    }

    public static void decreaseInventory(int[][][] foodOrder, int[][][] beverageOrder){

        for (int i = 0; i < foodOrder[orderNum].length; i++) {
            if (foodOrder[orderNum][i][0] != 0){
                Ingredients[] orderIngredients = foodList.get(foodOrder[orderNum][i][0]-1).getIngredients();
                for (Ingredients orderIngredient : orderIngredients) {
                    for (Ingredients currentIngredient : currentIngredients) {
                        if (orderIngredient.getIngredientName().equalsIgnoreCase(currentIngredient.getIngredientName())) {
                            currentIngredient.setIngredientQuantity(currentIngredient.getIngredientQuantity() - (orderIngredient.getIngredientQuantity() * foodOrder[orderNum][i][1]));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < beverageOrder[orderNum].length; i++) {
            if (beverageOrder[orderNum][i][0] != 0){
                Ingredients[] orderIngredients = foodList.get(beverageOrder[orderNum][i][0]-1).getIngredients();
                for (Ingredients orderIngredient : orderIngredients) {
                    for (Ingredients currentIngredient : currentIngredients) {
                        if (orderIngredient.getIngredientName().equalsIgnoreCase(currentIngredient.getIngredientName())) {
                            currentIngredient.setIngredientQuantity(currentIngredient.getIngredientQuantity() - (orderIngredient.getIngredientQuantity() * beverageOrder[orderNum][i][1]));
                        }
                    }
                }
            }
        }
    }


    public static void cardPayment(int[][][] foodOrder, int[][][] beverageOrder, Payment payment, Order orderWithMethod) {
        payment.setCardNo(cardNoValidation());
        payment.setCvv(cvvValidation());
        payment.setExpDateMonth(expMonthValidation());
        payment.setExpDateYear(expYearValidation());
        while(true){
            try{
                System.out.println("\n=====================================");
                System.out.print("Card Number: ");
                for (int i = 0; i < String.valueOf(payment.getCardNo()).length(); i++) {
                    if(i<4){
                        System.out.print(String.valueOf(payment.getCardNo()).charAt(i));
                    }
                }
                System.out.print("-");
                for (int i = 4; i < String.valueOf(payment.getCardNo()).length(); i++) {
                    if(i<8){
                        System.out.print(String.valueOf(payment.getCardNo()).charAt(i));
                    }
                }
                System.out.print("-");
                for (int i = 8; i < String.valueOf(payment.getCardNo()).length(); i++) {
                    if(i<12){
                        System.out.print(String.valueOf(payment.getCardNo()).charAt(i));
                    }
                }
                System.out.print("-");
                for (int i = 12; i < String.valueOf(payment.getCardNo()).length(); i++) {
                    if(i<16){
                        System.out.print(String.valueOf(payment.getCardNo()).charAt(i));
                    }
                }
                System.out.println("\nCVV: " + payment.getCvv());
                if(payment.getExpDateMonth() <= 9 && payment.getExpDateYear() <= 9){
                    System.out.println("Expire Date: 0" + payment.getExpDateMonth() + "/0" + payment.getExpDateYear());
                }
                else if(payment.getExpDateMonth() <= 9 && payment.getExpDateYear() > 9){
                    System.out.println("Expire Date: 0" + payment.getExpDateMonth() + "/" + payment.getExpDateYear());
                }
                else if(payment.getExpDateMonth() >= 9 && payment.getExpDateYear() <= 9){
                    System.out.println("Expire Date: " + payment.getExpDateMonth() + "/0" + payment.getExpDateYear());
                }
                else{
                    System.out.println("Expire Date: " + payment.getExpDateMonth() + "/" + payment.getExpDateYear());
                }
                System.out.println("\nConfirm to Pay? ");
                System.out.println("=====================================");
                System.out.println("[1] Confirm");
                System.out.println("[2] Re-enter card information.");
                System.out.println("[3] Cancel order, and go back to menu.");
                option = scanner.nextInt();
                if (option == 1) {
                    payment.setPaymentMethod("Card");
                    receipt(foodOrder, beverageOrder, payment, orderWithMethod);
                    break;
                }
                else if (option == 2){
                    cardPayment(foodOrder, beverageOrder, payment, orderWithMethod);
                    break;
                }
                else if (option == 3){
                    order = new Order();
                    makeOrder(foodOrder, beverageOrder, payment);
                    break;
                }
                else{
                    System.out.println("--> Must be 1 or 2 or 3 only.");
                    scanner.nextLine();
                }
            }
            catch (InputMismatchException e){
                System.out.println("--> Must be number.");
                scanner.nextLine();
            }
        }
    }

    public static int expYearValidation() {
        int yearFlag = 0;
        int year = 0;

        LocalDate date = LocalDate.now();
        int currentYear = date.getYear();
        try{
            while(yearFlag == 0) {
                System.out.println("\n=====================================");
                System.out.print("Enter expire date year: ");
                year = scanner.nextInt();
                year += 2000;
                if (year >= currentYear && year <= currentYear+3) {
                    yearFlag++;
                } else {
                    System.out.println("Illogical expire date year.");
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("--> Must be number.");
            scanner.nextLine();
            expYearValidation();
        }
        return year-2000;
    }

    public static int expMonthValidation() {
        int monthFlag = 0;
        int month = 0;
        try{
            while(monthFlag == 0){
                System.out.println("\n=====================================");
                System.out.print("Enter expire date month: ");
                month = scanner.nextInt();
                if(month >= 1 && month <= 12){
                    monthFlag++;
                }
                else{
                    System.out.println("Must be within January(1) and December(12).");
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("--> Must be number.");
            scanner.nextLine();
            expMonthValidation();
        }
        return month;
    }

    public static String cvvValidation() {

        String cvv;
        Pattern pattern;
        Matcher matcher;
        boolean matchFound;
        scanner.nextLine();
        System.out.println("\n=====================================");
        System.out.print("Enter CVV: ");
        cvv = scanner.nextLine();
        pattern = Pattern.compile("^[0-9]{3}$");
        matcher = pattern.matcher(cvv);
        matchFound = matcher.matches();
        while (!matchFound) {
            System.out.println("Incorrect CVV Format. Please enter CVV");
            System.out.print("Enter CVV again: ");
            cvv = scanner.nextLine();
            matcher = pattern.matcher(cvv);
            matchFound = matcher.matches();
        }
        return cvv;
    }

    public static Long cardNoValidation() {
        int cardNoFlag = 0;
        Long cardNo = null;
        try{
            while(cardNoFlag == 0){
                System.out.println("\n=====================================");
                System.out.print("Enter card number: ");
                cardNo = scanner.nextLong();
                if(String.valueOf(cardNo).length() == 16){
                    cardNoFlag++;
                }
                else{
                    System.out.println("--> Must be 16 digits.");
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("--> Must be number.");
            scanner.nextLine();
            cardNoValidation();
        }
        return cardNo;
    }

    public static int hourValidation() {
        int hourFlag = 0;
        int hour = 0;
        try{
            while(hourFlag == 0) {
                System.out.println("\n=====================================");
                System.out.print("Enter time in hour (24 hour format): ");
                hour = scanner.nextInt();
                if (hour >= 7 && hour < 23) {
                    hourFlag++;
                } else {
                    System.out.println("Must between 7 (7am) and 23 (11pm).");
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("--> Must be number.");
            scanner.nextLine();
            expYearValidation();
        }
        return hour;
    }

    public static int minValidation() {
        int minFlag = 0;
        int min = 0;
        try{
            while(minFlag == 0) {
                System.out.println("\n=====================================");
                System.out.print("Enter time in minute: ");
                min = scanner.nextInt();
                if (min >= 0 && min <= 59) {
                    minFlag++;
                } else {
                    System.out.println("Must between 0 and 59 minutes.");
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("--> Must be number.");
            scanner.nextLine();
            expYearValidation();
        }
        return min;
    }

    public static int daysValidation() {
        int daysFlag = 0;
        int days = 0;
        try{
            while(daysFlag == 0) {
                System.out.println("\n===================================================");
                System.out.println("--> We only provide after 5 days service delivery");
                System.out.println("Eg: 12/12/2022 --> after 3 days --> 15/12/2022");
                System.out.println("===================================================");
                System.out.print("Enter number of days confirm the order: ");
                days = scanner.nextInt();
                if (days >= 1 && days <= 5) {
                    daysFlag++;
                } else {
                    System.out.println("Must between 1 and 5 days.");
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("--> Must be number.");
            scanner.nextLine();
            expYearValidation();
        }
        return days;
    }

    public static int takeQuantityOrder() {

        int qty = 0;
        boolean reenterOption = true;
        do {
            System.out.print("Enter the quantity: ");

            //Use try, catch and throw to catch the exception
            try {
                qty = scanner.nextInt();
                //Throw the exception if the input is not in the option list (0/1/2/3/4/5)
                if (qty <= 0 || qty > 50) {
                    throw new Exception();
                }
                reenterOption = false;
            }
            //Catch the exception when the user entered others characters or symbols
            catch (InputMismatchException e) {
                System.out.println("Incorrect input. Only digit is allowed.");
                scanner.nextLine();

            }
            //Catch the exception when the user entered the quantity less than 0 or greater than 50
            catch (Exception e) {
                System.out.println("Illogical amount. Only the quantity range between 0 to 50 is acceptable");
                scanner.nextLine();
            }
        } while (reenterOption);

        scanner.nextLine();
        return qty;
    }



    //Staff part
    public static void determineStaffType(){
        String type = ((Staff) staffAcc.get(staffIndex)).getStaffType();
        if (type.equals("Manager")){
            staffJob();
        }
        else if(type.equals("Delivery Man")){
            deliveryManJob();
        }
    }

    public static void staffJob(){
        boolean continueStatus = true;
        int option;
        while (true){
            displayManagerJobMenu();
            option = takeMenuOption(0,6);
            switch (option) {
                case 1 ->
                        //Call to Inventory management
                        inventoryManagement();
                case 2 ->
                        //Call to Menu management
                        menuManagement();
                case 3 ->
                        //Call to Staff management
                        staffManagement();
                case 4 ->
                        //Call to Payroll
                        payroll();
                case 5 ->
                        //Call to Financial Status
                        sales();
                case 6 ->
                        //Call to customer review
                        customerReview();
                default -> {
                    return;
                }
            }

        }
    }


    public static void displayManagerJobMenu(){
        System.out.println("==============================");
        System.out.println("     Manager's Jobs Menu");
        System.out.println("==============================");
        System.out.println("1. Inventory Management");
        System.out.println("2. Menu Management");
        System.out.println("3. Staff Management");
        System.out.println("4. Payroll");
        System.out.println("5. Sales");
        System.out.println("6. Customer Review");
        System.out.println("0. Exit");
        System.out.println("==============================");
    }

    //Inventory Management
    public static void inventoryManagement(){
        System.out.println("\n\nFood and Beverage Inventory List");
        System.out.println("-".repeat(60));
        System.out.printf("%-18s%-21s%18s\n", "Item", "Unit of Measurement", "Quantity In Stock");
        System.out.println("-".repeat(60));
        for (Ingredients ingred: currentIngredients
        ) {
            System.out.printf("%-18s%-21s%18.0f\n",ingred.getIngredientName(), ingred.getMeasurementUnit(), ingred.getIngredientQuantity());
        }
        System.out.println("-".repeat(60));

        boolean orderStatus = false;
        int[] orderIndex = new int[currentIngredients.size()];
        Arrays.fill(orderIndex, -1);
        for (int i = 0; i < currentIngredients.size(); i++) {
            if (currentIngredients.get(i).getIngredientQuantity() <= 50){
                System.out.println("***Please notice that " + currentIngredients.get(i).getIngredientName() + " is almost out of stock. Remember to order stock");
                orderStatus = true;
                orderIndex[i] = i;
            }
        }

        if (orderStatus){
            double quantity;
            System.out.println("\n");
            System.out.println("[1] Restock");
            System.out.println("[2] Sort by item name");
            System.out.println("[3] Sort by quantity in stock");
            System.out.println("[0] Exit");
            System.out.println("You can choose to enter the option to restock, sort or choose to exit this page.");
            int option = takeMenuOption(0,3);
            if (option ==1){
                for (int index : orderIndex) {
                    if (index != -1) {
                        if (!Objects.equals(currentIngredients.get(index).getMeasurementUnit(), "")){
                            System.out.print("How many quantity you want to order in unit of " + currentIngredients.get(index).getMeasurementUnit() + " for " + currentIngredients.get(index).getIngredientName() + " ? ");

                        }
                        else{
                            System.out.print("How many quantity you want to order for " + currentIngredients.get(index).getIngredientName() + " ? ");
                        }
                        while (true) {
                            try {
                                quantity = scanner.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Please only enter integer. Others character is not allowed.");
                                scanner.nextLine();
                                System.out.print("Enter again: ");
                            }
                        }

                        currentIngredients.get(index).setIngredientQuantity(currentIngredients.get(index).getIngredientQuantity() + quantity);

                    }
                }
                scanner.nextLine();
                System.out.println("Order completed. Please remember to check the item quantity again to avoid the problem of out of stock.");
                System.out.println("Press Enter key to exit to job menu...");
                scanner.nextLine();
            }
            else if (option == 2){
                Collections.sort(currentIngredients);
                inventoryManagement();
            }
            else if (option ==3){
                Collections.sort(currentIngredients, new Comparator<Ingredients>() {
                    @Override
                    public int compare(Ingredients o1, Ingredients o2) {
                        if (o1.getIngredientQuantity() < o2.getIngredientQuantity()){
                            return -1;
                        }
                        else if(o1.getIngredientQuantity() > o2.getIngredientQuantity()){
                            return 1;
                        }
                        else {
                            return 0;
                        }
                    }
                });
                inventoryManagement();

            }
        }
        else{
            System.out.println("[1] Sort by item name");
            System.out.println("[2] Sort by quantity in stock");
            System.out.println("[0] Exit");
            int input = takeMenuOption(0,2);
            switch (input){
                case 1:
                    Collections.sort(currentIngredients);
                    inventoryManagement();
                case 2:
                    Collections.sort(currentIngredients, new Comparator<Ingredients>() {
                        @Override
                        public int compare(Ingredients o1, Ingredients o2) {
                            if (o1.getIngredientQuantity() < o2.getIngredientQuantity()){
                                return -1;
                            }
                            else if(o1.getIngredientQuantity() > o2.getIngredientQuantity()){
                                return 1;
                            }
                            else {
                                return 0;
                            }
                        }
                    });
                    inventoryManagement();
            }
        }


        //Minus ingredients when order
//        int[][][] foodOrder = new int [2][5][2];
//        foodOrder[0][0][0] = 1;
//        foodOrder[0][0][1] = 2;
//        foodOrder[0][1][0] = 2;
//        foodOrder[0][1][1] = 2;
//
//        int[][][] beverageOrder = new int [2][5][2];
//        beverageOrder[0][0][0] = 1;
//        beverageOrder[0][0][1] = 2;
//        beverageOrder[0][1][0] = 2;
//        beverageOrder[0][1][1] = 2;
//
//
//        for (int[][] ints : foodOrder) {
//            for (int[] anInt : ints) {
//                if (anInt[0] != 0) {
//                    Ingredients[] orderIngredients = food.get(anInt[0] - 1).getIngredients();
//                    for (Ingredients currentIngredient : currentIngredients) {
//                        for (Ingredients orderIngredient : orderIngredients) {
//                            if (Objects.equals(currentIngredient.getIngredientName(), orderIngredient.getIngredientName())) {
//                                currentIngredient.setIngredientQuantity(currentIngredient.getIngredientQuantity() - orderIngredient.getIngredientQuantity());
//                            }
//                        }
//
//                    }
//
//                }
//            }
//        }
//
//        for (int[][] ints : beverageOrder) {
//            for (int[] anInt : ints) {
//                if (anInt[0] != 0) {
//                    Ingredients[] orderIngredients = beverage.get(anInt[0] - 1).getIngredients();
//                    for (Ingredients currentIngredient : currentIngredients) {
//                        for (Ingredients orderIngredient : orderIngredients) {
//                            if (Objects.equals(currentIngredient.getIngredientName(), orderIngredient.getIngredientName())) {
//                                currentIngredient.setIngredientQuantity(currentIngredient.getIngredientQuantity() - (orderIngredient.getIngredientQuantity() / 1000));
//                            }
//                        }
//
//                    }
//
//                }
//            }
//        }
//
//        for (Ingredients currentIngredient : currentIngredients) {
//            System.out.println(currentIngredient.getIngredientName() + " " + currentIngredient.getIngredientQuantity());
//        }
    }

    //Menu management
    public static void menuManagement() {
        String continueModify;
        do {
            String continueMenuModify;

            System.out.println("\nModify: ");
            System.out.println("[1] Food Menu");
            System.out.println("[2] Beverage Menu");
            System.out.println("[0] Back");

            int modifyMenuOption = takeMenuOption(0, 2);

            if (modifyMenuOption == 0) {
                return;
            }

            else {

                do {
                    displayModifyMenu(modifyMenuOption);
                    System.out.println("\nModify Option: ");
                    System.out.println("[1] Add");
                    System.out.println("[2] Delete");
                    System.out.println("[3] Edit");
                    System.out.println("[0] Back");

                    int editOption = takeMenuOption(0, 3);

                    scanner.nextLine();
                    System.out.println();

                    if (editOption == 1) {
                        addMenu(modifyMenuOption);
                    }

                    else if (editOption == 2) {
                        deleteMenu(modifyMenuOption);
                    }

                    else if (editOption == 3) {
                        modifyMenuItem(modifyMenuOption);
                    }

                    else {
                        break;
                    }

                    System.out.print("\nContinue modify ");

                    if (modifyMenuOption == 1) {
                        System.out.print("FOOD");
                    }

                    else {
                        System.out.print("BEVERAGE");
                    }



                    System.out.print(" menu (Y/N) : ");

                    continueMenuModify = scanner.nextLine();

                    continueMenuModify = checkInput(continueMenuModify);


                } while (continueMenuModify.equalsIgnoreCase("y"));

                System.out.print("Modify others menu (Y/N): ");
                continueModify = scanner.nextLine();

                continueModify = checkInput(continueModify);
            }

        }while (continueModify.equalsIgnoreCase("y"));

    }

    public static void displayModifyMenu(int option) {
        if (option == 1) {
            menu.displayMenu(1);
        } else {
            menu.displayMenu(2);
        }
    }

    public static void modifyMenuItem(int modifyMenuOption) {

        String id;
        int modifyOption;
        boolean reenterID = true;

        displayModifyMenu(modifyMenuOption);

        System.out.println("[1] Name");
        System.out.println("[2] Price");
        System.out.println("[3] All of above");
        System.out.println("[0] Back");

        modifyOption = takeMenuOption(0, 3);

        scanner.nextLine();
        if (modifyOption == 0) {
            return;
        }
        else {
            do {
                System.out.print("\nEnter the ID: ");
                id = scanner.nextLine();

                if (modifyMenuOption == 1) {
                    for (int i = 0; i < foodList.size(); i++) {
                        if (id.equalsIgnoreCase(foodList.get(i).getFoodID())) {
                            modifyFoodItem(i, modifyOption);
                            reenterID = false;
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < beverageList.size(); i++) {
                        if (id.equalsIgnoreCase(beverageList.get(i).getBeverageID())) {
                            modifyBeverageItem(i, modifyOption);
                            reenterID = false;
                            break;
                        }
                    }
                }

                if (reenterID) {
                    System.out.println("No such ID found. Please try again");
                }

            } while (reenterID);
        }
    }

    public static void modifyBeverageItem(int index, int option) {

        String beverageName = beverageList.get(index).getBeverageName();
        double beveragePrice = beverageList.get(index).getBeveragePrice();

        if (option == 1) {
            do{
                System.out.println("Old Name: " + beverageName);
                System.out.print("Enter New Name: ");
                beverageName = scanner.nextLine();
                beverageName = capitalizeWord(beverageName);
                if (beverageList.get(index).getBeverageName().equals(beverageName)){
                    System.out.println("New name cannot same as the old name. Please try again.\n");
                }
            }while(beverageList.get(index).getBeverageName().equals(beverageName));
        }

        else if (option == 2) {
            do{
                System.out.printf("\nOld Price: RM%.2f\n", beveragePrice);
                beveragePrice = takeMenuPrice("Price");
                scanner.nextLine();
                if (beverageList.get(index).getBeveragePrice() == beveragePrice){
                    System.out.println("New price cannot same as the old price. Please try again.\n");
                }
            }while (beverageList.get(index).getBeveragePrice() == beveragePrice);
        }

        else {
            do{
                System.out.println("Old Name: " + beverageName);
                System.out.print("Enter New Name: ");
                beverageName = scanner.nextLine();
                beverageName = capitalizeWord(beverageName);
                if (beverageList.get(index).getBeverageName().equals(beverageName)){
                    System.out.println("New name cannot same as the old name. Please try again.\n");
                }
            }while(beverageList.get(index).getBeverageName().equals(beverageName));

            do{
                System.out.printf("\nOld Price: RM%.2f\n", beveragePrice);
                beveragePrice = takeMenuPrice("Price");
                scanner.nextLine();
                if (beverageList.get(index).getBeveragePrice() == beveragePrice){
                    System.out.println("New price cannot same as the old price. Please try again.\n");
                }
            }while (beverageList.get(index).getBeveragePrice() == beveragePrice);
        }

        ((Manager) staffAcc.get(staffIndex)).editBeverageItem(beverageName, beveragePrice, index);
        beverageList.get(index).setBeverageName(beverageName);
        beverageList.get(index).setBeveragePrice(beveragePrice);
    }

    public static void modifyFoodItem(int index, int option) {

        String foodName = foodList.get(index).getFoodName();
        double foodPrice = foodList.get(index).getFoodPrice();



        if (option == 1) {
            do{
                System.out.println("Old Name: " + foodName);
                System.out.print("Enter New Name: ");
                foodName = scanner.nextLine();
                foodName = capitalizeWord(foodName);
                if (foodList.get(index).getFoodName().equals(foodName)){
                    System.out.println("New name cannot same as the old name. Please try again.\n");
                }
            }while(foodList.get(index).getFoodName().equals(foodName));
        }

        else if (option == 2) {
            do{
                System.out.printf("\nOld Price: RM%.2f\n", foodPrice);
                foodPrice = takeMenuPrice("Price");
                scanner.nextLine();
                if (foodList.get(index).getFoodPrice() == foodPrice){
                    System.out.println("New price cannot same as the old price. Please try again.\n");
                }
            }while (foodList.get(index).getFoodPrice() == foodPrice);
        }

        else {
            do{
                System.out.println("Old Name: " + foodName);
                System.out.print("Enter New Name: ");
                foodName = scanner.nextLine();
                foodName = capitalizeWord(foodName);
                if (foodList.get(index).getFoodName().equals(foodName)){
                    System.out.println("New name cannot same as the old name. Please try again.\n");
                }
            }while(foodList.get(index).getFoodName().equals(foodName));

            do{
                System.out.printf("\nOld Price: RM%.2f\n", foodPrice);
                foodPrice = takeMenuPrice("Price");
                scanner.nextLine();
                if (foodList.get(index).getFoodPrice() == foodPrice){
                    System.out.println("New price cannot same as the old price. Please try again.\n");
                }
            }while (foodList.get(index).getFoodPrice() == foodPrice);
        }
        ((Manager) staffAcc.get(staffIndex)).editFoodItem(foodName, foodPrice, index);
    }

    public static void deleteMenu(int modifyMenuOption) {

        String id;
        boolean reenterID;

        do {
            System.out.print("Enter the ID: ");
            id = scanner.nextLine();

            reenterID = true;

            if (modifyMenuOption == 1) {
                for (int i = 0; i < foodList.size(); i++) {

                    if (id.equalsIgnoreCase(foodList.get(i).getFoodID())) {
                        ((Manager) staffAcc.get(staffIndex)).deleteFood(i);
                        reenterID = false;
                        break;
                    }
                }
            }

            else {

                for (int i = 0; i < beverageList.size(); i++) {

                    if (id.equalsIgnoreCase(beverageList.get(i).getBeverageID())) {
                        Ingredients[] tempIngredient = beverageList.get(i).getIngredients();
                        ((Manager) staffAcc.get(staffIndex)).deleteBeverage(i);
                        reenterID = false;
                        break;
                    }
                }
            }

            if (reenterID) {

                System.out.println("There is no such ID found.");

                if (retryOption() == 1) {
                    continue;
                }

                else {
                    break;
                }
            }

            else {
                System.out.println("\nItem Successfully Deleted.");
            }

        } while (reenterID);

    }

    public static void addMenu(int modifyMenuOption) {

        String name = null;
        double price;
        double costPrice;
        boolean reenterOption = true;

        while (reenterOption){
            if (modifyMenuOption == 1){
                System.out.print("Enter New Name: ");
                name = scanner.nextLine();
                name = capitalizeWord(name);
                for (Food food : foodList) {
                    System.out.println(food.getFoodName());
                    if (food.getFoodName().equalsIgnoreCase(name)) {
                        System.out.println("The food name already existed. Please try again");
                        reenterOption = true;
                        break;
                    } else {
                        reenterOption = false;
                    }

                }
            }
            else{
                System.out.print("Enter New Name: ");
                name = scanner.nextLine();
                name = capitalizeWord(name);
                for (Beverage beverage : beverageList) {
                    if (beverage.getBeverageName().equals(name)) {
                        System.out.println("The beverage name already existed. Please try again");
                        reenterOption = true;
                        break;
                    } else {
                        reenterOption = false;
                    }

                }
            }
        }


        name = capitalizeWord(name);

        price = takeMenuPrice("Price");

        costPrice = takeMenuPrice("Cost Price");
        while (price < costPrice){
            System.out.println("Cost Price cannot greater than selling price. Please try again.");
            price = takeMenuPrice("Price");
            costPrice = takeMenuPrice("Cost Price");
        }

        int num;
        do {
            System.out.print("Enter Number of Ingredients: ");
            num = scanner.nextInt();
            if(num <= 0){
                System.out.println("The number of ingredients must be larger than 0. Please try again.");
            }
        }while (num <= 0);
        
        
        scanner.nextLine();
        String ingredName;
        String ingredUnit;
        double ingredQuantity;

        ArrayList<Ingredients> ingredients = new ArrayList<>();

        for (int i=0; i<num; i++){
            System.out.println("\n Ingredient " + (i+1));

            System.out.println("=".repeat(20));
            System.out.print("Enter ingredient name: ");
            ingredName = scanner.nextLine();
            ingredName = capitalizeWord(ingredName);

            System.out.print("Enter ingredient measurement unit: ");
            ingredUnit = scanner.nextLine();

            while (true){
                try{
                    System.out.print("Enter ingredient quantity: ");
                    ingredQuantity = scanner.nextDouble();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("Please only enter numbers. Other character is not allowed.");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();

            if (ingredUnit.equals("")){
                ingredients.add(new Ingredients(ingredName, ingredQuantity));
            }
            else{
                ingredients.add(new Ingredients(ingredName, ingredUnit ,ingredQuantity));
            }


            boolean foundStatus = false;
            for (Ingredients currentIngred: currentIngredients){
                if (currentIngred.getIngredientName().equals(ingredName)) {
                    foundStatus = true;
                    break;
                }
            }
            if (!foundStatus){
                currentIngredients.add(new Ingredients(ingredName, ingredUnit, 0));
            }
        }

        int length;
        if (modifyMenuOption == 1) {
            length = foodList.size();
            Object[] o = ingredients.toArray();
            Ingredients[] tempIngred = new Ingredients[ingredients.size()];
            for (int i = 0; i <ingredients.size() ; i++){
                tempIngred[i] = (Ingredients) o[i];
            }

            ((Manager) staffAcc.get(staffIndex)).addNewFood(new Food(name, price, costPrice), length, tempIngred);

        } else {
            length = beverageList.size();
            Object[] o = ingredients.toArray();
            Ingredients[] tempIngred = new Ingredients[ingredients.size()];
            for (int i = 0; i <ingredients.size() ; i++){
                tempIngred[i] = (Ingredients) o[i];
            }
            ((Manager) staffAcc.get(staffIndex)).addNewBeverage(new Beverage(name, price, costPrice), length, tempIngred);

        }

        System.out.println("\nItem Successfully Added.");

    }

    public static double takeMenuPrice(String name) {

        double price = 0.0;
        boolean reenterOption = true;
        do {
            System.out.printf("Enter New %s: RM", name);

            //Use try, catch and throw to catch the exception
            try {
                price = scanner.nextDouble();
                if (price <= 0) {
                    throw new Exception();
                }
                reenterOption = false;
            }
            //Catch the exception when the user entered others characters or symbols
            catch (InputMismatchException e) {
                System.out.println("Incorrect input. Only digit is allowed.");
                scanner.nextLine();

            }
            catch (Exception e) {
                System.out.println("Incorrect price. Price must be larger than 0.0");
                scanner.nextLine();
            }
        } while (reenterOption);
        return price;
    }

    //Staff management
    public static void staffManagement(){
        Manager manager = (Manager) staffAcc.get(staffIndex);
        String name, email, contact, nricNum;
        String input;
        int option;
        double basicSalary;
        Pattern pattern;
        Matcher matcher;
        boolean matchFound;
        System.out.printf("%76s\n", "Staff Details");
        System.out.printf("%76s\n\n", "`````````````");
        System.out.println("+" + "-".repeat(148) + "+");
        System.out.printf("|%-2s %-15s %-7s %-18s %-14s %-24s %-15s %-14s %-14s %-16s|\n", "No", "Name", "User ID", "Password", "Contact", "Email","IC Number" , "Staff Type", "Date Joined", "Basic Salary(RM)");
        System.out.println("+" + "-".repeat(148) + "+");
        int index = 1;
        for (Account account:staffAcc
        ) {
            System.out.printf("|" + index+". %-15s %-7s %-18s %-14s %-25s", account.getName(), account.getUserID(), account.getPassword(), account.getContact(), account.getEmail());
            if (account instanceof Staff){
                System.out.printf("%-15s %-14s %-14s %16.2f|\n", ((Staff) account).getNricNum(), ((Staff) account).getStaffType(), ((Staff) account).getStaffType(), ((Staff) account).getBasicSalary());
            }
            index++;
        }
        System.out.println("+" + "-".repeat(148) + "+");
        System.out.println("What operations would you like to do?");
        System.out.println("[1] Add new staff");
        System.out.println("[2] Delete previous staff");
        System.out.println("[3] Edit staff details");
        System.out.println("[0] Exit");
        System.out.println("-".repeat(37));
        option = takeMenuOption(0,3);
        switch (option) {
            case 1 -> {
                System.out.println("\n--Fill in new staff details--");
                String staffType;
                System.out.println("Staff Type Available: \n(1) Kitchen Staff\n(2) Delivery Man\n" +
                        "'1' for Kitchen Staff or '2' for Delivery Man\n"
                        + "-".repeat(45));
                option = takeMenuOption(1,2);
                if (option == 1) {
                    staffType = "Kitchen Staff";
                } else {
                    staffType = "Delivery Man";
                }
                scanner.nextLine();
                //Username
                boolean reenterOption;
                do {
                    reenterOption = true;
                    System.out.print("\nEnter User Name: ");
                    name = scanner.nextLine();
                    for (Account staff: staffAcc){
                        if (name.equals(staff.getName())){
                            System.out.println("The username is taken. Try another.");
                            break;
                        }
                        else {
                            reenterOption = false;
                        }

                    }

                }while (reenterOption);
                name = capitalizeWord(name);



                System.out.print("Enter NRIC number: ");
                nricNum = scanner.nextLine();
                //regex expression
                pattern = Pattern.compile("\\d{6}-\\d{2}-\\d{4}");
                matcher = pattern.matcher(nricNum);
                matchFound = matcher.matches();
                while (!matchFound) {
                    System.out.println("Incorrect NRIC Number Format. Please enter in format (xxxxxx-xx-xxxx)");
                    System.out.print("Enter NRIC Number again: ");
                    nricNum = scanner.nextLine();
                    matcher = pattern.matcher(nricNum);
                    matchFound = matcher.matches();
                }

                System.out.print("Enter Contact Number in format (01x-xxxx xxxx): ");
                contact = scanner.nextLine();
                pattern = Pattern.compile("^(\\+?6?01)[0-46-9]-*[0-9]{7,8}$");
                matcher = pattern.matcher(contact);
                matchFound = matcher.matches();
                while (!matchFound) {
                    System.out.println("Incorrect Contact Number Format. Please enter in format (01x-xxxx xxxx)");
                    System.out.print("Enter Contact Number again: ");
                    contact = scanner.nextLine();
                    matcher = pattern.matcher(contact);
                    matchFound = matcher.matches();
                }
                System.out.print("Enter Email: ");
                email = scanner.nextLine();
                pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                matcher = pattern.matcher(email);
                matchFound = matcher.matches();
                while (!matchFound) {
                    System.out.println("Incorrect Email Format. Please enter in \"abc@gmail.com\" format");
                    System.out.print("Enter email again: ");
                    email = scanner.nextLine();
                    matcher = pattern.matcher(email);
                    matchFound = matcher.matches();
                }
                System.out.print("Enter Date Joined (DD/MM/YYYY): ");
                String dateJoined = scanner.nextLine();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                simpleDateFormat.setLenient(false);
                while (true) {
                    try {
                        Date javaDate = simpleDateFormat.parse(dateJoined);
                        break;
                    }
                    /* Date format is invalid */
                    catch (ParseException e) {
                        System.out.println(dateJoined + " is Invalid Date format, please follow the format (DD/MM/YYYY)");
                        System.out.print("Enter Date Joined again: ");
                        dateJoined = scanner.nextLine();
                    }
                }
                while (true) {
                    try {
                        System.out.print("Enter basic salary: RM");
                        basicSalary = scanner.nextDouble();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Incorrect input. Only digit is allowed.");
                        scanner.nextLine();
                    }
                }
                System.out.println();
                System.out.println("---New Staff Details---");
                System.out.println("Staff Type: " + staffType);
                System.out.println("Name: " + name);
                System.out.println("Contact number: " + contact);
                System.out.println("Email: " + email);
                System.out.println("Date Joined: " + dateJoined);
                System.out.printf("Basic salary: RM%.2f\n", basicSalary);
                scanner.nextLine();
                System.out.print("Are you sure you want to add new staff with above information? (Y-Yes / N-No) : ");
                input = scanner.nextLine();
                input = checkInput(input);
                if (input.equals("Y")) {
                    //Auto generated userID with doing increment to the userID
                    String userID = "";
                    for (Account staff : staffAcc) {
                        if (staff instanceof Staff){
                            if (Objects.equals(((Staff) staff).getStaffType(), staffType)) {
                                userID = staff.getUserID();
                            }
                        }

                    }
                    String password = null;
                    if (staffType.equals("Delivery Man")){
                        if (userID.equals("")){
                            userID = Character.toUpperCase(staffType.charAt(0)) + "000";
                        }
                        int num = Integer.parseInt(userID.substring(1));
                        num++;
                        userID = userID.charAt(0) + String.format("%03d", num);

                        //Auto generated password
                        password = userID + name.trim().replaceAll("\\s+","");

                    }

                    //Add new staff to array list
                    if (staffType.equals("Kitchen Staff")) {
                        staffAcc = manager.addStaff(new KitchenStaff(name, userID, contact, email, nricNum, staffType, dateJoined, basicSalary, 0), staffAcc);
                    } else {
                        staffAcc = manager.addStaff(new DeliveryMan(name, userID, password, contact, email, nricNum, staffType, dateJoined, basicSalary, 0), staffAcc);
                    }
                    System.out.println("Add new staff successfully!!!");
                } else {
                    System.out.println("You have cancelled the add new staff process");
                }
                System.out.println("Press Enter key to go back to Job menu...");
                scanner.nextLine();
            }

            case 2 -> {
                System.out.println("Which staff would you like to delete? (Press '0' to exit)");
                option = takeMenuOption(0, staffAcc.size());
                if (option == 0) {
                    break;
                }
                if (staffAcc.get(option-1) instanceof Staff){
                    while (((Staff) staffAcc.get(option - 1)).getStaffType().equals("Manager")) {
                        System.out.println("You are not qualified to delete other manager.");
                        option = takeMenuOption(0, staffAcc.size());
                        if (option == 0) {
                            break;
                        }
                    }
                }

                if (option == 0) {
                    break;
                }
                System.out.print("Are you sure you want to delete staff with UserID - " + staffAcc.get(option - 1).getUserID() + " ? (Y-Yes / N-No): ");
                input = scanner.nextLine();
                input = checkInput(input);
                if (input.equals("Y")) {
                    staffAcc = manager.removeStaff(staffAcc, option -1);
                    System.out.println("Staff was deleted successfully");
                } else {
                    System.out.println("You have cancelled the delete process.");
                }
                System.out.println("Press Enter key to go back to Job menu...");
                scanner.nextLine();
            }
            case 3 -> {
                String tempName, tempContact, tempEmail;
                double tempSalary;
                System.out.println("Which staff's details would you like to edit?");
                int staffOption = takeMenuOption(1, staffAcc.size());
                System.out.println("\n---Edit staff details---");
                System.out.println("[1] Name");
                System.out.println("[2] Contact number");
                System.out.println("[3] Email");
                System.out.println("[4] Basic salary");
                System.out.println("----------------------------");
                System.out.println("Which information would you like to edit?");
                option = takeMenuOption(1, 4);
                switch (option) {
                    case 1 -> {
                        System.out.print("Enter new name: ");
                        tempName = scanner.nextLine();
                        while (staffAcc.get(staffOption - 1).getName().equals(tempName)) {
                            System.out.print("Cannot enter same name. Please try again: ");
                            tempName = scanner.nextLine();
                        }
                        System.out.print("Are you sure you want to change name from \"" + staffAcc.get(staffOption - 1).getName() + "\" to \"" + tempName + "\" ? ");
                        input = scanner.nextLine();
                        input = checkInput(input);
                        if (input.equals("Y")) {
                            staffAcc = manager.editStaffName(staffAcc, staffOption-1, tempName);
                            System.out.println("Edit staff details successfully!!!");
                        } else {
                            System.out.println("You have cancelled the staff edit.");
                        }
                    }
                    case 2 -> {
                        System.out.print("Enter new Contact Number in format (01x-xxxx xxxx): ");
                        tempContact = scanner.nextLine();
                        while (staffAcc.get(staffOption - 1).getContact().equals(tempContact)) {
                            System.out.println("Cannot enter same contact number. Please try again: ");
                            tempContact = scanner.nextLine();
                        }
                        pattern = Pattern.compile("^(\\+?6?01)[0-46-9]-*[0-9]{7,8}$");
                        matcher = pattern.matcher(tempContact);
                        matchFound = matcher.matches();
                        while (!matchFound) {
                            System.out.println("Incorrect Contact Number Format. Please enter in format (01x-xxxx xxxx)");
                            System.out.print("Enter Contact Number again: ");
                            tempContact = scanner.nextLine();
                            matcher = pattern.matcher(tempContact);
                            matchFound = matcher.matches();
                        }
                        System.out.print("Are you sure you want to change contact number from \"" + staffAcc.get(staffOption - 1).getContact() + "\" to \"" + tempContact + "\" ? ");
                        input = scanner.nextLine();
                        input = checkInput(input);
                        if (input.equals("Y")) {
                            staffAcc = manager.editStaffContact(staffAcc, staffOption-1, tempContact);
                            System.out.println("Edit staff details successfully!!!");
                        } else {
                            System.out.println("You have cancelled the staff edit.");
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter new Email: ");
                        tempEmail = scanner.nextLine();
                        while (staffAcc.get(staffOption - 1).getEmail().equals(tempEmail)) {
                            System.out.println("Cannot enter same email. Please try again: ");
                            tempEmail = scanner.nextLine();
                        }
                        pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                        matcher = pattern.matcher(tempEmail);
                        matchFound = matcher.matches();
                        while (!matchFound) {
                            System.out.println("Incorrect Email Format. Please enter in \"abc@gmail.com\" format");
                            System.out.print("Enter email again: ");
                            tempEmail = scanner.nextLine();
                            matcher = pattern.matcher(tempEmail);
                            matchFound = matcher.matches();
                        }
                        System.out.print("Are you sure you want to change email from \"" + staffAcc.get(staffOption - 1).getEmail() + "\" to \"" + tempEmail + "\" ? ");
                        input = scanner.nextLine();
                        input = checkInput(input);
                        if (input.equals("Y")) {
                            staffAcc = manager.editStaffEmail(staffAcc, staffOption-1, tempEmail);
                            System.out.println("Edit staff details successfully!!!");
                        } else {
                            System.out.println("You have cancelled the staff edit.");
                        }
                    }
                    case 4 -> {
                        while (true) {
                            try {
                                System.out.print("Enter new basic salary: RM");
                                tempSalary = scanner.nextDouble();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Incorrect input. Only digit is allowed.");
                                scanner.nextLine();
                            }
                        }
                        scanner.nextLine();
                        if (staffAcc.get(option-1) instanceof Staff){
                            System.out.printf("Are you sure you want to change basic salary from \"%.2f\" to \"%.2f\" ? ", ((Staff) staffAcc.get(option - 1)).getBasicSalary(), tempSalary);
                        }
                        input = scanner.nextLine();
                        input = checkInput(input);
                        if (input.equals("Y")) {
                            staffAcc = manager.editStaffBasicSalary(staffAcc, staffOption-1, tempSalary);
                            System.out.println("Edit staff details successfully!!!");
                        } else {
                            System.out.println("You have cancelled the staff edit.");
                        }


                    }
                }
                System.out.println("Press Enter key to go back to Job menu...");
                scanner.nextLine();
            }
            default -> {
                //exit
            }
        }
    }


    //Payroll
    public static void payroll(){
        System.out.println("Payroll Menu");
        System.out.println("[1] Check Payroll History");
        System.out.println("[2] Generate Payslip");
        System.out.println("[0] Exit");
        System.out.println("===================================");
        int option = takeMenuOption(0,2);
        switch (option) {
            case 1 -> {
                int month, year;
                String monthName;

                while(true) {
                    try {
                        System.out.print("Enter month(1-12): ");
                        month = scanner.nextInt();
                        if (month < 1 || month > 12){
                            throw new Exception();
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter integer only for year.");
                        scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("Invalid month. Please enter the month between 1-12.");
                        scanner.nextLine();
                    }
                }
                while(true) {
                    try {
                        System.out.print("Enter year: ");
                        year = scanner.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter integer only for year.");
                    }
                }
                monthName = Month.of(month).name();
                String fileName = monthName + year + "SalaryHistory";
                System.out.println("File name: " + fileName);

                File file = new File("./src/" + fileName);
                if (!file.exists()){
                    System.out.println("The salary history for " + monthName + " " + year + " does not exists.\n" +
                            "It may be because the restaurant haven not started yet or the salary for that month has not been given to staff yet.");
                }
                else{
                    try {
                        Scanner myReader = new Scanner(file);
                        while (myReader.hasNextLine()) {
                            String data = myReader.nextLine();
                            System.out.println(data);
                        }
                        myReader.close();
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }


                }
                scanner.nextLine();
                System.out.println("\nPress Enter key to exit to Job Menu page...");
                scanner.nextLine();

            }
            case 2 -> {
                //Get current date and convert into dd/MM/yyyy format
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String text = date.format(formatters);
                LocalDate parsedDate = LocalDate.parse(text, formatters);
                //Get current month
                Month currentMonth = parsedDate.minusMonths(1).getMonth();
                //Get current year
                int currentYear = parsedDate.getYear();

                String fileName = currentMonth + String.valueOf(currentYear) + "SalaryHistory";
                try {
                    File myObj = new File("./src/" + fileName);
                    if (!myObj.createNewFile()) {
                        scanner.nextLine();
                        System.out.println("The payslip for " + currentMonth + " " + currentYear + " already calculated. You can calculate on the next month.");
                        System.out.println("Press Enter key to exit to Job Menu page...");
                        scanner.nextLine();

                        break;
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }


                System.out.println("\nStaff Details for " + currentMonth + " " + currentYear);
                System.out.println("-".repeat(102));
                System.out.printf("%-8s %-15s %-14s %-20s %-23s %-16s\n", "User ID", "Name", "Staff Type", "Monthly Orders Sent", "Monthly Overtime Hours", "Basic Salary(RM)");
                System.out.println("-".repeat(102));
                for (Account staff : staffAcc) {
                    if (staff instanceof Staff){
                        System.out.printf("%-8s %-15s %-14s", staff.getUserID(), staff.getName(), ((Staff) staff).getStaffType());
                    }
                    if (staff instanceof Manager) {
                        System.out.printf("%19s %22s", "-", "-");
                    } else if (staff instanceof DeliveryMan) {
                        System.out.printf("%19s %22s", ((DeliveryMan) staff).getMonthlyOrdersSent(), "-");
                    } else if (staff instanceof KitchenStaff){
                        System.out.printf("%19s %22s", "-", ((KitchenStaff) staff).getOverTimeHours());
                    }
                    if (staff instanceof Staff){
                        System.out.printf("%16.2f\n", ((Staff) staff).getBasicSalary());
                    }
                }
                System.out.println("-".repeat(102));

                scanner.nextLine();
                System.out.print("Do you want to generate payslip ? ('Y'-Yes /'N'-No) ");
                String input = scanner.nextLine();
                input = checkInput(input);

                if (input.equals("Y")){
                    System.out.println("\n\nGenerating payslip for each staff.......");

                    for (Account acc: staffAcc) {
                        if (acc instanceof Staff){
                            System.out.println("\n\n\n+" + "-".repeat(51) + "+");
                            System.out.println("| Payslip for Period Ending " + currentMonth + currentYear + " (Monthly)(RM) |");
                            System.out.println("+" + "-".repeat(51) + "+");
                            System.out.printf("| User ID: %4s%38s\n", acc.getUserID(), "|");
                            System.out.printf("| Name: %-20s%25s\n", acc.getName(),"|");
                            System.out.printf("| NRIC: " + ((Staff) acc).getNricNum() + "%32s", "|\n");
                            System.out.printf("| Staff Type: %-20s%19s\n" , ((Staff) acc).getStaffType(),"|");
                            System.out.println("+" + "-".repeat(51) + "+");
                            System.out.printf("| Basic Salary: %-36.2f|\n", ((Staff) acc).getBasicSalary());
                        }

                        if (acc instanceof KitchenStaff) {
                            System.out.printf("| Monthly Overtime Hours: %-26d|\n" , ((KitchenStaff) acc).getOverTimeHours());
                            ((KitchenStaff) acc).calculateSalary();
                        } else if (acc instanceof DeliveryMan) {
                            System.out.printf("| Monthly Orders Sent: %-29d|\n" , ((DeliveryMan) acc).getMonthlyOrdersSent());
                            ((DeliveryMan) acc).calculateSalary();
                        }
                        else if (acc instanceof Manager){
                            ((Manager) acc).setTotalSalary(((Manager) acc).getBasicSalary());
                        }
                        System.out.println("+" + "-".repeat(51) + "+");
                        if(acc instanceof  Staff){
                            System.out.printf("| Total Salary: %-36.2f|\n", ((Staff) acc).getTotalSalary());

                        }
                        System.out.println("+" + "-".repeat(51) + "+");
                        System.out.println();
                    }
                    String fileContent = String.format("%69s\n", "FAST FOOD RESTAURANT") +
                            String.format("%71s\n", "Employee Salary History") +
                            String .format("%128s\n","Date: " + currentMonth + " " + currentYear) +
                            "+" + "-".repeat(127) + "+\n" +
                            String.format("|%56s%17s%55s\n", "Overtime Hours", "Orders Sent", "|") +
                            String.format("| %-10s%-15s%-15s%-20s%-17s%-19s%-19s%-11s|\n", "User ID", "Name", "Staff Type", "(Kitchen Staff)", "(Delivery Man)", "Basic Salary(RM)", "Total Salary(RM)", "Paid On") +
                            "|" + "-".repeat(127) + "|\n";

                    for (Account acc: staffAcc
                    ) {
                        if (acc instanceof Staff){
                            fileContent= fileContent.concat(
                                    String.format("| %-10s%-15s%-15s", acc.getUserID(), acc.getName(), ((Staff) acc).getStaffType())
                            );
                            if (acc instanceof DeliveryMan){
                                fileContent = fileContent.concat(
                                        String.format("%-20s%-17d", "-", ((DeliveryMan) acc).getMonthlyOrdersSent())
                                );
                            }
                            else if (acc instanceof KitchenStaff){
                                fileContent = fileContent.concat(
                                        String.format("%-20d%-17s", ((KitchenStaff) acc).getOverTimeHours(), "-")
                                );
                            }
                            else{
                                fileContent = fileContent.concat(
                                        String.format("%-20s%-17s", "-", "-" )
                                );
                            }
                            fileContent = fileContent.concat(
                                    String.format("%-19.2f%-19.2f%-11s|\n", ((Staff) acc).getBasicSalary(), ((Staff) acc).getTotalSalary(), text )
                            );
                        }


                    }
                    fileContent = fileContent.concat( "+" + "-".repeat(127) + "+\n");

                    try {
                        FileWriter myWriter = new FileWriter("./src/" + fileName);
                        myWriter.write(fileContent);
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
                else{
                    File myObj = new File("./src/" + fileName);
                    myObj.delete();
                }


                System.out.println("Completed calculate salary!!!");
                System.out.println("Press Enter key to exit to job menu...");
                scanner.nextLine();
            }
        }
    }

    //Sales
    public static void sales(){
        int month;
        String monthName;
        Object[] febFoodSales;
        Object[] marchFoodSales;
        Object[] tempObject = new Object[10];

        while (true) {
            while (true) {
                try {
                    System.out.print("Enter month(1-12) in year 2022 for Monthly Sales Report: ");
                    month = scanner.nextInt();
                    if (month < 1 || month > 12) {
                        throw new Exception();
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter integer only for year.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid month. Please enter the month between 1-12.");
                    scanner.nextLine();
                }
            }
            monthName = Month.of(month).name();
            if (month >=2 && month < 5){
                if (month == 4){
                    //Assume that the current month is April 2020
                    System.out.println("The monthly sales report will be generated at the end of the month. Please wait patiently.");
                }
                else{
                    break;
                }

            }
            else {
                System.out.println("The Monthly Sales Report for " + monthName + " 2022 is invalid.\n" +
                        "There are few reasons for invalid report:\n" +
                        "[1] Month entered exceed the current month\n" +
                        "[2] Month entered is before restaurant open\n");
            }
        }

        switch (month) {
            case 2 -> {
                Food.setFoodCount(0);
                Beverage.setBeverageCount(0);
                //Set the sample sales for February 2022
                febFoodSales = new Object[]{
                        new Food("Chicken Burger", 12.0, 3.5, 120),
                        new Food("Fish Burger", 14.0, 3, 80),
                        new Food("Fried Chicken", 14.0, 4, 150),
                        new Food("French Fries", 14.0, 2, 100),
                        new Food("Cheese Hot dog", 10.0, 2, 45),
                        new Beverage("Coca-Cola", 5.0, 1, 80),
                        new Beverage("7 Up", 5.0, 1, 40),
                        new Beverage("Pepsi", 4.5, 1, 60),
                        new Beverage("Ice Lemon Tea", 6.0, 1.5, 30),
                        new Beverage("Mineral Water", 3.0, 0.5, 5)
                };
                displayMonthlySalesReport(febFoodSales, monthName);
                tempObject = febFoodSales;
            }
            case 3 -> {
                Food.setFoodCount(0);
                Beverage.setBeverageCount(0);
                //Set the sample sales for March 2022
                marchFoodSales = new Object[]{
                        new Food("Chicken Burger", 12.0, 3.5, 200),
                        new Food("Fish Burger", 14.0, 3, 100),
                        new Food("Fried Chicken", 14.0, 4, 200),
                        new Food("French Fries", 14.0, 2, 125),
                        new Food("Cheese Hot dog", 10.0, 2, 58),
                        new Beverage("Coca-Cola", 5.0, 1, 111),
                        new Beverage("7 Up", 5.0, 1, 59),
                        new Beverage("Pepsi", 4.5, 1, 112),
                        new Beverage("Ice Lemon Tea", 6.0, 1.5, 20),
                        new Beverage("Mineral Water", 3.0, 0.5, 8)
                };
                displayMonthlySalesReport(marchFoodSales, monthName);
                tempObject = marchFoodSales;
            }
        }

        Object[] selectedObject = new Object[tempObject.length];
        for (int i = selectedObject.length - 1; i >= 0; --i) {
            Object p = tempObject[i];
            if (p != null) {
                selectedObject[i] = p;
            }
        }

        int option;
        while (true){
            System.out.println();
            System.out.println("Sort the report by: ");
            System.out.println("[1] Quantity Sold");
            System.out.println("[2] Cost Price");
            System.out.println("[3] Selling Price");
            System.out.println("[0] Exit to Job's menu");
            option = takeMenuOption(0,3);
            if (option == 0) break;
            selectedObject = sortMonthlySalesReport(selectedObject, option);
            displayMonthlySalesReport(selectedObject, monthName);
        }
    }

    public static void displayMonthlySalesReport(Object[] o, String monthName){
        double totalProfit = 0;
        //Display monthly sales report
        System.out.println("\n\n\nMonthly Sales Report for " + monthName + " 2022");
        System.out.println("+" + "-".repeat(75) + "+" + "-".repeat(13) + "+");
        System.out.printf("| %-7s  %-14s  %-13s  %-14s  %-17s | %11s |\n","Item ID","Item Name","Quantity Sold","Cost Price(RM)","Selling Price(RM)", "Profit(RM)");
        System.out.println("+" + "-".repeat(75) + "+" + "-".repeat(13) + "+");

        int index = 0;
        for (Object sales: o
        ) {
            if (index == 5){
                System.out.println("+" + "-".repeat(89)  + "+");
            }
            if (sales instanceof Food){
                System.out.printf("| %-7s  %-14s  %13d %14.2f  %17.2f  | %11.2f |\n",
                        ((Food) sales).getFoodID(), ((Food) sales).getFoodName(),
                        ((Food) sales).getFoodSoldQuantity(),((Food) sales).getFoodCostPrice(),
                        ((Food) sales).getFoodPrice(), ((Food) sales).getFoodProfit()* ((Food) sales).getFoodSoldQuantity());
                totalProfit += ((Food) sales).getFoodProfit() * ((Food) sales).getFoodSoldQuantity();
            }
            else if (sales instanceof Beverage){
                System.out.printf("| %-7s  %-14s  %13d %14.2f  %17.2f  | %11.2f |\n",((Beverage) sales).getBeverageID(), ((Beverage) sales).getBeverageName(),
                        ((Beverage) sales).getBeverageSoldQuantity(),((Beverage) sales).getBeverageCostPrice(), ((Beverage) sales).getBeveragePrice(), ((Beverage) sales).getBeverageProfit()*((Beverage) sales).getBeverageSoldQuantity());
                totalProfit += ((Beverage) sales).getBeverageProfit() * ((Beverage) sales).getBeverageSoldQuantity();
            }
            index++;

        }
        System.out.println("+" + "-".repeat(89)  + "+");
        System.out.printf("| %72s : %12.2f |\n", "Total Profit(RM)", totalProfit);
        System.out.println("+" + "-".repeat(89)  + "+");
    }

    public static Object[] sortMonthlySalesReport(Object[] o, int option){
        ArrayList<Object> list = new ArrayList<>(Arrays.asList(o));
        switch (option) {
            case 0 -> {
            }
            case 1 -> {
                Arrays.sort(o, new Comparator<Object>() {
                    @Override
                    public int compare(Object first, Object second) {
                        if (first instanceof Food && second instanceof Food) {
                            if (((Food) first).getFoodSoldQuantity() != ((Food) second).getFoodSoldQuantity()) {
                                return ((Food) first).getFoodSoldQuantity() - ((Food) second).getFoodSoldQuantity();
                            }
                            return ((Food) first).getFoodName().compareTo(((Food) second).getFoodName());
                        } else if (first instanceof Beverage && second instanceof Beverage) {
                            if (((Beverage) first).getBeverageSoldQuantity() != ((Beverage) second).getBeverageSoldQuantity()) {
                                return ((Beverage) first).getBeverageSoldQuantity() - ((Beverage) second).getBeverageSoldQuantity();
                            }
                            return ((Beverage) first).getBeverageName().compareTo(((Beverage) second).getBeverageName());
                        }

                        return 0;
                    }
                });
                return o;
            }
            case 2 -> {
                Collections.sort(list, new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof Food && o2 instanceof Food){
                            if( ((Food) o1).getFoodCostPrice() < ((Food) o2).getFoodCostPrice())
                                return -1;
                            else if(((Food) o2).getFoodCostPrice() < ((Food) o1).getFoodCostPrice())
                                return 1;
                            return 0;
                        }
                        else if (o1 instanceof Beverage && o2 instanceof Beverage){
                            if( ((Beverage) o1).getBeverageCostPrice() < ((Beverage) o2).getBeverageCostPrice())
                                return -1;
                            else if(((Beverage) o2).getBeverageCostPrice() < ((Beverage) o1).getBeverageCostPrice())
                                return 1;
                            return 0;
                        }

                        return 0;
                    }
                });

                return list.toArray();
            }
            case 3 -> {
                Collections.sort(list, new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof Food && o2 instanceof Food){
                            if( ((Food) o1).getFoodPrice() < ((Food) o2).getFoodPrice())
                                return -1;
                            else if(((Food) o2).getFoodPrice() < ((Food) o1).getFoodPrice())
                                return 1;
                            return 0;
                        }
                        else if (o1 instanceof Beverage && o2 instanceof Beverage){
                            if( ((Beverage) o1).getBeveragePrice() < ((Beverage) o2).getBeveragePrice())
                                return -1;
                            else if(((Beverage) o2).getBeveragePrice() < ((Beverage) o1).getBeveragePrice())
                                return 1;
                            return 0;
                        }

                        return 0;
                    }
                });

                return list.toArray();
            }
        }
        return o;
    }

    //View customer review
    public static void customerReview(){

        int num = 0;
        double sum = 0, average;
        System.out.println("Rating Details");
        System.out.println("+" + "-".repeat(112) + "+");
        System.out.printf("| %-20s %-30s %6s  %-49s |\n", "Username", "Review Date", "Rating", "Description");
        System.out.println("+" + "-".repeat(112) + "+");
        for (Account customer: custAcc){
            if (customer instanceof Customer){
                if (((Customer) customer).getReview().getRating()!= -1){
                    System.out.printf("| %-20s %-30s %3s     %-49s |\n", customer.getName(), ((Customer) customer).getReview().getDateTime(), ((Customer) customer).getReview().getRating(), ((Customer) customer).getReview().getDescription());
                    sum+= ((Customer) customer).getReview().getRating();
                    num++;
                }
            }
        }
        if (num == 0){
            System.out.printf("\n%72s\n", "No customer review yet....");
        }
        System.out.println("+" + "-".repeat(112) + "+");
        System.out.println("Total rating: " + sum);
        System.out.println("Average rating: " + (sum/num));
        scanner.nextLine();
        System.out.println("\nPress Enter key to continue...");
        scanner.nextLine();

    }


    //Delivery Man
    public static void deliveryManJob(){
        String cont;
        int option;

        ArrayList<Integer> deliveryManIndex = new ArrayList<Integer>();
        for (int i = 0; i < staffAcc.size(); i++) {
            if (staffAcc.get(i) instanceof DeliveryMan) {
                deliveryManIndex.add(i);
            }
        }

        ArrayList<Integer> deliveryOrdersArrIndex = new ArrayList<Integer>();
        for (int i = 0; i < orderHistory.size(); i++) {
            if (orderHistory.get(i) instanceof DeliveryOrder) {
                deliveryOrdersArrIndex.add(i);
            }
        }

        do {
            //prompt and take input
            System.out.println("[1] Assign delivery\n[2] Check deliveryman history\n[3] Remove delivery history\n[0] Exit");
            option = takeMenuOption(0,3);
            scanner.nextLine();
            switch (option) {
                case 0:
                    return;
                case 1:
                    //add not assigned delivery order index into notAssignedOrderArrIndex
                    ArrayList<Integer> notAssignedOrderArrIndex = new ArrayList<Integer>();
                    for (Integer ordersArrIndex : deliveryOrdersArrIndex) {
                        if (!(((DeliveryOrder) orderHistory.get(ordersArrIndex)).isAssigned())) {
                            notAssignedOrderArrIndex.add(ordersArrIndex);
                        }
                    }
                    //display delivery ID
                    System.out.println("\nDelivery ID List: ");
                    try {
                        if (notAssignedOrderArrIndex.size() == 0){
                            System.out.println("--> No unassigned delivery orders to show");
                            break;
                        } else {
                            for (int i = 0; i < notAssignedOrderArrIndex.size(); i++) {
                                System.out.println("[" + (i+1) + "] " + orderHistory.get(i).getOrderID());
                                System.out.println("[0] Exit");
                            }
                        }
                    }catch (NullPointerException e) {
                        System.out.println("No Record found\nPlease add deliveryman and try again");
                    }
                    //get the delivery order and assign to deliveryman
                    int notAssignedOrderArrIndexIndex = takeMenuOption(0, notAssignedOrderArrIndex.size());
                    scanner.nextLine();
                    if (notAssignedOrderArrIndexIndex == 0) {
                         break;
                    }
                    int orderHistoryIndex = notAssignedOrderArrIndex.get(notAssignedOrderArrIndexIndex-1);

                    //delivery man.addDelivery(delivery order)
                    ((DeliveryMan)staffAcc.get(staffIndex)).addDelivery((DeliveryOrder) orderHistory.get(orderHistoryIndex));
                    ((DeliveryOrder) orderHistory.get(orderHistoryIndex)).setAssigned(true);
                    System.out.println("Assigned " + orderHistory.get(orderHistoryIndex).getOrderID() + " to " + staffAcc.get(staffIndex).getName());
                    break;
                case 2:
                    System.out.println("\nTime period:\n[1] Yesterday and today\n[2] Last week\n[3] Last month\n[4] Last year\n[5] All time\n[0] Exit\n");
                    int duration = takeMenuOption(0, 5);
                    scanner.nextLine();
                    if (duration == 0){break;}
                    else {
                        //deliveryMan.deliveryHistory(timePeriod).equals("")
                        if (((DeliveryMan)staffAcc.get(staffIndex)).deliveryHistory(DeliveryMan.TimePeriod.values()[duration-1]).equals("")){
                            System.out.println("No delivery history to display\n");
                        }else {
                            //deliveryMan.deliveryHistory(timePeriod)
                            System.out.println(((DeliveryMan)staffAcc.get(staffIndex)).deliveryHistory(DeliveryMan.TimePeriod.values()[duration - 1]));
                        }
                    }
                    break;
                case 3:
                    DeliveryMan selectedDeliveryMan = (DeliveryMan)staffAcc.get(staffIndex);
                    System.out.println("\nDelivery ID List:");
                    ArrayList<DeliveryOrder> deliveryOrdersTemp = (ArrayList<DeliveryOrder>)(selectedDeliveryMan).getDelivery().clone();
                    for (int i = 0; i < deliveryOrdersTemp.size(); i++) {
                        System.out.println("[" + (i+1) + "] " + deliveryOrdersTemp.get(i).getOrderID());
                    }
                    System.out.println("[0] Exit");
                    option = takeMenuOption(0, deliveryOrdersTemp.size());
                    scanner.nextLine();
                    if (option == 0){
                        break;
                    }
                    selectedDeliveryMan.delDelivery(option-1);
                    System.out.println("Deleted " + deliveryOrdersTemp.get(option-1).getOrderID() + " from " + selectedDeliveryMan.getName());
                    break;
            }
            System.out.print("Continue (Y/N)? ");
            cont = scanner.nextLine();
            cont = checkInput(cont);
            System.out.println("\n\n\n");
        }while(cont.equals("Y"));
    }

    //Validation
    public static int takeMenuOption(int a, int b){
        //Do the validation to the menu input
        //Use do-while to let the user enter again if the input is not correct
        int menuOption;
        while (true){
            System.out.print("Enter the option: ");
            //Use try, catch and throw to catch the exception
            try {
                menuOption = scanner.nextInt();
                //Throw the exception if the input is not in the option list (1/2/3/4/5)
                if (menuOption < a-1 || menuOption > b){
                    throw new Exception();
                }
                break;
            }
            //Catch the exception when the user entered others characters or symbols
            catch (InputMismatchException e){
                System.out.println("Incorrect input. Only digit is allowed.");
                scanner.nextLine();

            }
            //Catch the exception when the user entered the number other than 1-5
            catch (Exception e){
                System.out.println("Incorrect input. Others option is not allowed.");
                scanner.nextLine();
            }
        }
        return menuOption;
    }

    public static String checkInput(String input){
        Scanner scanner = new Scanner(System.in);
        while (true){
            if (input.length() != 1){
                System.out.println("Incorrect input! Only 1 letter is required.");
            }
            else{
                char convertInput = Character.toUpperCase(input.charAt(0));
                if (Character.isDigit(convertInput)){
                    System.out.println("Incorrect input! Digit is not allowed to enter.");
                }
                else if (Character.isLetter(convertInput)){
                    if (convertInput != 'Y' && convertInput != 'N'){
                        System.out.println("Incorrect input! Only 'Y' or 'N' is accepted.");
                    }
                    else {
                        return Character.toString(convertInput);
                    }
                }
                else {
                    System.out.println("Incorrect input! Others symbol is not allowed.");
                }
            }
            System.out.print("Please enter again. ('Y' for YES or 'N' for No): ");
            input = scanner.nextLine();
        }
    }

    public static int retryOption() {

        System.out.println("Wanna retry?");
        System.out.println("[1] Yes");
        System.out.println("[2] No");

        return takeMenuOption(1, 2);
    }

    public static String capitalizeWord(String input){
        input = input.replaceAll("\\s+"," ");
        String words[] = input.split("\\s");
        String capitalizedWord = "";
        for(String word:words){
            // Capitalize first letter
            String firstLetter=word.substring(0,1);
            // Get remaining letter
            String remainingLetters=word.substring(1);
            capitalizedWord+=firstLetter.toUpperCase()+remainingLetters.toLowerCase()+" ";
        }
        return capitalizedWord.trim();
    }
}

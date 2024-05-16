package com.example.iwardrobefx;

import java.util.ArrayList;

public class Customer {
    FileIO io = new FileIO();
    public String customerID;
    public static String firstName;
    public String phoneNumber;
    public int ticketNumber;
    public String lastSeen;
    public ArrayList<Customer> customers = new ArrayList<Customer>();
    public static String Belongings;
    public Customer(String customerID, String firstName, String phoneNumber, int ticketNumber, String Belongings) {
        this.customerID = customerID;
        Customer.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.ticketNumber = ticketNumber;
        this.Belongings = Belongings;
        customers.add(this);
        io.saveCustomerData(this, customers);
        System.out.println("DEBUG BRUGER: " + Belongings);
    }

    public String getCustomerID() {
        return customerID;
    }
    public static String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public static String getBelongings() {
        return Belongings;
    }
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    @Override
    public String toString() {
        return "Customer ID: " + customerID + ", Name: " + firstName + ", Phone Number: " + phoneNumber + ", Ticket Number: " + ticketNumber;
    }
}
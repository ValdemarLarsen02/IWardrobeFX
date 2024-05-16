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

    public Customer(String customerID, String firstName, String phoneNumber, int ticketNumber) {
        this.customerID = customerID;
        Customer.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.ticketNumber = ticketNumber;
        customers.add(this);
        io.saveCustomerData(this, customers);
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

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    @Override
    public String toString() {
        return "Customer ID: " + customerID + ", Name: " + firstName + ", Phone Number: " + phoneNumber + ", Ticket Number: " + ticketNumber;
    }
}
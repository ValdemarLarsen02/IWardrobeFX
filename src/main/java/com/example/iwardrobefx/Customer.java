package com.example.iwardrobefx;

public class Customer {


    public String customerID;
    public String firstName;
    public String phoneNumber;
    public int ticketNumber;
    public String lastSeen;




    public Customer(String customerID, String firstName, String phoneNumber, int ticketNumber) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.ticketNumber = ticketNumber;
    }

    public String getCustomerID() {
        return customerID;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

}

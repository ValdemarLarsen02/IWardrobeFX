package com.example.iwardrobefx;

public class Customer {


    public int customerID;
    public String firstName;
    public String phoneNumber;
    public int ticketNumber;
    public String lastSeen;




    public Customer(int customerID, String firstName, String phoneNumber, int ticketNumber) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.ticketNumber = ticketNumber;
    }

    public int getCustomerID() {
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

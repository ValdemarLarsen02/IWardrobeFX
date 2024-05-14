package com.example.iwardrobefx;

public class TicketHandler {



    public static void ticketGeneration(Customer customer) {
        System.out.println("Creating ticket for user " + customer.phoneNumber);

        // Opretter vores ticket nummer
        int ticketNumber = generateTicketNumber();
        customer.ticketNumber = ticketNumber;

        System.out.println("Ticket number " + ticketNumber + " has been generated for " + customer.firstName);
    }

    private static int generateTicketNumber() {
        // SKal laves mere kompliceret med checks osv osv
        return (int)(Math.random() * 10000);
    }


}

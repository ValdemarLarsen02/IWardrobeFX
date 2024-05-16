package com.example.iwardrobefx;

public class ErrorHandler {


    public static void saveCustomerDataError() {
        System.out.println("An error occurred while saving customer data to CustomerData.csv");
    }

    public static void getAllCustomerDataError() {
        System.out.println("An error occurred while getting customer data from AllTimeCustomerData.csv");
    }

    public static void getCustomerDataError() {
        System.out.println("An error occurred while getting current customer data");
    }

    public void timesVisistedError() {
        System.out.println("An error occurred while updating a customers times visited count");
    }

    public static void removeCustomerError() {
        System.out.println("An error occurred while removing customers from customerData.csv");
    }

    public void generateAdminCodeError(){
        System.out.println("An error occurred while generating admin code");
    }

    public static void adminLogin(){
        System.out.println("An error occurred while logging in as admin");
    }

}
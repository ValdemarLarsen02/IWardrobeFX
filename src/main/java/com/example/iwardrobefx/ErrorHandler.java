package com.example.iwardrobefx;

public class ErrorHandler {


    public void saveCustomerDataError() {
        System.out.println("An error occurred while saving customer data to CustomerData.csv");
    }

    public void getAllCustomerDataError() {
        System.out.println("An error occurred while getting customer data from AllTimeCustomerData.csv");
    }

    public void getCustomerDataError() {
        System.out.println("An error occurred while getting current customer data");
    }

    public void timesVisistedError() {
        System.out.println("An error occurred while updating a customers times visited count");
    }

    public void removeCustomerError() {
        System.out.println("An error occurred while removing customers from customerData.csv");
    }

    public void generateAdminCodeError(){
        System.out.println("An error occurred while generating admin code");
    }

    public void adminLogin(){
        System.out.println("An error occurred while logging in as admin");
    }

}
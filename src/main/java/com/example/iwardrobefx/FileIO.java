package com.example.iwardrobefx;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


public class FileIO {
    private static String customerDataPath = "data/CustomerData.csv";
    private static String allTimeCustomerData = "data/AllTimeCustomerData.csv";
    private static String adminDataPath = "data/AdminData.csv";
    ErrorHandler er = new ErrorHandler();

    public void saveCustomerData(Customer customer, ArrayList<Customer> customers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(customerDataPath));
             PrintWriter pw2 = new PrintWriter(new FileWriter(allTimeCustomerData, true))) {

            pw.println("ID, Name, Phone Number, Ticket Number");

            if (new File(allTimeCustomerData).length() == 0) {
                pw2.println("ID, Name, Phone Number, Ticket Number, Times Visited");
            }

            for (Customer c : customers) {
                pw.println(c.getCustomerID() + ", " + c.getFirstName() + ", " + c.getPhoneNumber() + ", " + c.getTicketNumber());
                pw2.println(c.getCustomerID() + ", " + c.getFirstName() + ", " + c.getPhoneNumber() + ", " + c.getTicketNumber() + ", 0");
            }

        } catch (IOException e) {
            er.saveCustomerDataError();
        }
    }


    public static void saveUserLastTicketNumber(Customer customer) {
        // Her opretter vi en liste til at holde den data vi henter for senere at kunne opdatere!
        List<String> fileContent = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(customerDataPath))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(String.valueOf(customer.getCustomerID()))) {
                    // Opdaterer ticketnummeret for den givne kunde
                    data[3] = String.valueOf(customer.getTicketNumber());
                    line = String.join(", ", data);
                }
                fileContent.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        // Opdatere selve CSV filen.
        try (PrintWriter pw = new PrintWriter(new FileWriter(customerDataPath))) {
            for (String newLine : fileContent) {
                pw.println(newLine);
            }
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }

    }

    public void getAllCustomerData(ArrayList<Customer> customers) {
        try {
            Scanner scan = new Scanner(new File(allTimeCustomerData));
            while (scan.hasNextLine()) {
                System.out.println(scan.nextLine());
            }
            scan.close();
        } catch (FileNotFoundException e) {
            er.getCustomerDataError();
        }
    }

    public void getCurrentCustomerData(ArrayList<Customer> customers) {
        if (!customers.isEmpty()) {
            System.out.println(customers.get(customers.size() - 1));
        } else {
            er.getCustomerDataError();
        }
    }


    public void timesVisited(Customer customer) {
        try (BufferedReader br = new BufferedReader(new FileReader(allTimeCustomerData));
             PrintWriter pw = new PrintWriter(new FileWriter(allTimeCustomerData + ".temp"))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length >= 5 && data[2].equals(customer.getPhoneNumber())) {
                    int timesVisited = Integer.parseInt(data[4].trim()) + 1;
                    pw.println(data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3] + ", " + timesVisited);
                } else {
                    pw.println(line);
                }
            }

        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            er.timesVisistedError();
        }
        File file = new File(allTimeCustomerData);
        File tempFile = new File(allTimeCustomerData + ".temp");
        if (tempFile.renameTo(file)) {
            System.out.println("Times visited updated successfully.");
        } else {
            System.out.println("Failed to update times visited.");
        }
    }

    public void removeCustomer(Customer customer) {
        try (BufferedReader br = new BufferedReader(new FileReader(customerDataPath));
             PrintWriter pw = new PrintWriter(new FileWriter(customerDataPath + ".temp"))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length >= 3 && data[2].equals(customer)) {
                    continue;
                }
                pw.println(line);
            }

        } catch (IOException e) {
            er.removeCustomerError();
        }

        File file = new File(customerDataPath);
        File tempFile = new File(customerDataPath + ".temp");
        if (tempFile.renameTo(file)) {
            System.out.println("Customer removed successfully.");
        } else {
            System.out.println("Failed to remove customer.");
        }
    }
    /*
    public void generateAdminCode(Company company) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(adminDataPath, true))) {
            int adminCode = (int) (Math.random() * 10000);
            pw.println(company.getName() + ": Admin Code: " + adminCode);
        } catch (IOException e) {
            er.generateAdminCodeError();
        }
    }
    */
    public static Company adminLogin(int adminCode) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(adminDataPath))) {
            br.readLine(); // Spring over header-linjen
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 3) {
                    int code = Integer.parseInt(userData[1].trim());
                    if (code == adminCode) {
                        String firmName = userData[0].trim();
                        int capacity = Integer.parseInt(userData[2].trim());
                        return new Company(capacity, firmName);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Her skal vores errorhandler bruges.
        }
        return null; // vi returner bare null hvis koden ikke findes.. håndteres over i vores controller.
    }



    //Generate Admin code
    //Get Admin Code
}
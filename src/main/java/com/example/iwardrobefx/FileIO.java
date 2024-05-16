package com.example.iwardrobefx;
import javafx.collections.FXCollections;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class FileIO {
    private static String customerDataPath = "data/CustomerData.csv";
    private static String allTimeCustomerData = "data/AllTimeCustomerData.csv";
    private static String adminDataPath = "data/AdminData.csv";
    ErrorHandler er = new ErrorHandler();

    public void saveCustomerData(Customer customer, ArrayList<Customer> customers) {
        Map<String, String[]> customerDataMap = new HashMap<>();

        // starter med at læse eksisterende data fra allTimeCustomerData-filen
        try (BufferedReader br = new BufferedReader(new FileReader(allTimeCustomerData))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals("ID")) {
                    continue; // Springer overskriften over
                }
                customerDataMap.put(data[0], data);
            }
        } catch (IOException e) {
            ErrorHandler.saveCustomerDataError();
        }

        // Finder ud af, om det er en ny kunde eller en der findes i vores data.
        for (Customer c : customers) {
            String id = c.getCustomerID();
            if (customerDataMap.containsKey(id)) {
                // Opdatér eksisterende kunde
                String[] existingData = customerDataMap.get(id);
                if (existingData.length == 5) {

                    existingData = Arrays.copyOf(existingData, 6);
                    existingData[5] = c.getBelongings();
                }
                try {
                    int timesVisited = Integer.parseInt(existingData[4]) + 1;
                    existingData[4] = String.valueOf(timesVisited);
                    existingData[3] = String.valueOf(c.getTicketNumber());
                    existingData[5] = c.getBelongings();
                    customerDataMap.put(id, existingData);
                } catch (NumberFormatException e) {
                    System.out.println("DEBUG FEJL: ved ny bruger data i FILEIO " + id);
                    ErrorHandler.saveCustomerDataError();
                }
            } else {
                // Tilføj ny kunde
                String[] newData = {
                        c.getCustomerID(),
                        c.getFirstName(),
                        c.getPhoneNumber(),
                        String.valueOf(c.getTicketNumber()),
                        "1", // vi starter bare med et besøg
                        c.getBelongings()
                };
                customerDataMap.put(id, newData);
            }
        }

        // Skriver dataen til den csv, der holder vores all time data.
        try (PrintWriter pw = new PrintWriter(new FileWriter(allTimeCustomerData))) {
            pw.println("ID, Name, Phone Number, Ticket Number, Times Visited, TojType");
            for (String[] data : customerDataMap.values()) {
                pw.println(String.join(", ", data));
            }
        } catch (IOException e) {
            ErrorHandler.saveCustomerDataError();
        }

        // Indskriv kunderne til den fil, der holder vores aktive kunder
        Set<String> existingCustomerIds = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(customerDataPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals("ID")) {
                    continue; // Springer overskirften over.
                }
                existingCustomerIds.add(data[0]);
            }
        } catch (IOException e) {
            ErrorHandler.saveCustomerDataError();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(customerDataPath, true))) {
            // Hvis vores fil er tom, skriver vi overskriften (kolonner)
            File currentDataFile = new File(customerDataPath);
            if (currentDataFile.length() == 0) {
                pw.println("ID, Name, Phone Number, Ticket Number, TojType");
            }

            for (Customer c : customers) {
                if (!existingCustomerIds.contains(c.getCustomerID())) {
                    pw.println(c.getCustomerID() + ", " + c.getFirstName() + ", " + c.getPhoneNumber() + ", " + c.getTicketNumber() + ", " + c.getBelongings());
                }
            }
        } catch (IOException e) {
            ErrorHandler.saveCustomerDataError();
        }
    }



    public static void saveUserLastTicketNumber(Customer customer) {
        // Her opretter vi en liste til at holde den data vi henter for senere at kunne opdatere!
        System.out.println("Saving user last ticket number: " + customer.getFirstName() + customer.getBelongings());
        List<String> fileContent = new ArrayList<>();
        String line;
        boolean customerFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(customerDataPath))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(String.valueOf(customer.getCustomerID()))) {
                    // Opdaterer ticketnummeret og tojType for den givne kunde
                    data[3] = String.valueOf(customer.getTicketNumber());
                    data[4] = customer.getBelongings(); // Opdaterer tojType
                    line = String.join(", ", data);
                    customerFound = true;
                }
                fileContent.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        // Hvis kunden ikke blev fundet, tilføj en ny linje for kunden
        if (!customerFound) {
            String newCustomerLine = customer.getCustomerID() + ", " +
                    customer.getFirstName() + ", " +
                    "unknown" + ", " + // Placeholder for Phone Number if not available
                    customer.getTicketNumber() + ", " +
                    customer.getBelongings(); // Tilføjer tojType
            fileContent.add(newCustomerLine);
        }

        // Opdatere selve CSV filen.
        try (PrintWriter pw = new PrintWriter(new FileWriter(customerDataPath))) {
            for (String newLine : fileContent) {
                pw.println(newLine);
            }
        } catch (IOException e) {
            ErrorHandler.saveCustomerDataError();
        }
    }


    public static ObservableList<ObservableList<String>> loadBrugereFromCSV() {
        ObservableList<ObservableList<String>> brugere = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(customerDataPath))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                ObservableList<String> row = FXCollections.observableArrayList(data);
                brugere.add(row);
            }
        } catch (IOException e) {
            ErrorHandler.getAllCustomerDataError();
        }

        return brugere;
    }




    public static boolean removeCustomerByTicketNumber(int ticketNumber) {
        List<String> fileContent = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(customerDataPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length > 3 && data[3].equals(String.valueOf(ticketNumber))) {
                    found = true;
                } else {
                    fileContent.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(customerDataPath))) {
                for (String line : fileContent) {
                    pw.println(line);
                }
            } catch (IOException e) {
                ErrorHandler.removeCustomerError();
            }
        }

        return found;
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
        System.out.println(adminCode);
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
            ErrorHandler.adminLogin();
        }
        return null; // vi returner bare null hvis koden ikke findes.. håndteres over i vores controller.
    }






    //Generate Admin code
    //Get Admin Code
}
package com.example.iwardrobefx;

import javafx.scene.control.Button;

import javafx.scene.control.TextInputDialog;


import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ButtonBar.ButtonData;


public class MainScreenController {


    public Button getTicket;


    public Button createTicket;


    public void getItem() {
        // Laver en dialog til input af brugerens ticket nr.
        int nr = 0;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Angiv dit garderobe nr");
        dialog.setContentText("Angiv dit garderobe nr");

        // Viser dialogen og venter på brugerens input
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(number -> {
            try {
                int inputNumber = Integer.parseInt(number);
                System.out.println("Det indtastede tal er: " + inputNumber);
                // Her skal vi kalde vores metode der gør noget ved dette nummer(Findet det osv osv)

            } catch (NumberFormatException e) {
                System.err.println("Indtast venligst et gyldigt tal.");
            }
        });
    }

    public void createTicket() {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Opret Ticket");
        dialog.setHeaderText("Indtast informationerne for at oprette en ticket");

        // Baggrundsfarve på vores dialog.
        dialog.getDialogPane().setStyle("-fx-background-color: #737373;"); // Sæt en lyseblå baggrundsfarve

        // Opretter vores knapper ok og cancel
        ButtonType createButtonType = new ButtonType("Opret", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Placerer vores inputs i et grid.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstName = new TextField();
        firstName.setPromptText("Fornavn");
        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("Telefonnummer");

        grid.add(new Label("Fornavn:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Telefonnummer:"), 0, 1);
        grid.add(phoneNumber, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Starter med fokus på navne feltet.
        Platform.runLater(firstName::requestFocus);

        // Laver vores data om til en array af data
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new String[] {firstName.getText(), phoneNumber.getText()};
            }
            return null;
        });

        // Venter på det indtastet resultat.
        Optional<String[]> result = dialog.showAndWait();

        result.ifPresent(names -> {
            String fName = names[0];
            String phone = names[1];


            int customerID = generateCustomerNumber();
            if (!fName.isEmpty() && !phone.isEmpty()) {
                Customer customer = new Customer(customerID, fName, phone, 0); // Standard ticket nummer indtil det genereres af ticketHandler
                TicketHandler.ticketGeneration(customer);
            }
        });
    }


    private static int generateCustomerNumber() {
        return (int)(Math.random() * 10000);
    }

}

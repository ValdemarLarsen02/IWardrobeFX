package com.example.iwardrobefx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainScreenController {


    public Button getTicket;


    public Button createTicket;
    public Text passwordText;
    public Text passwordTextWrong;
    public Label cityLabel;
    public String FundetBy;

    @FXML
    private PasswordField passwordField;

    private void alertBoks(String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
    @FXML
    public void initialize() {
        System.out.println("DEBUG: Kører vores start metode?");
        String city = Location.getCity();
        if (city.equals("Elsinore")) {
            cityLabel.setText("Du befinder dig i Helsingør");
            FundetBy = "Helsingør";
        } else {
            cityLabel.setText(city);
            FundetBy = Location.getCity();
        }
    }

    @FXML
    private void togglePasswordVisibility() {
        passwordField.setVisible(!passwordField.isVisible());
        passwordText.setVisible(!passwordText.isVisible());

        //LAver en lytter til når der trykkes enter når bruger har fokus på password feltet.
        passwordField.setOnAction(event -> {
            String password = passwordField.getText();

            System.out.println("DEBUG: Input password: " + password);

            String passCheck = String.valueOf(FileIO.adminLogin(Integer.parseInt(password)));

            System.out.println("DEBUG: passCheck value: " + passCheck);

            if (passCheck == null || passCheck.equals("null")) {
                System.out.println("DEBUG: Password check failed, showing error message.");
                passwordTextWrong.setVisible(true);
            } else {
                System.out.println("DEBUG: Password check succeeded, hiding error message.");
                passwordTextWrong.setVisible(false);
                System.out.println("Sender bruger videre til admin siden her .." + Company.getName());

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adminpanel.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = (Stage) passwordField.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    System.out.println("DEBUG FEJL: " + e);
                }
            }
        });
    }

    public void getItem() {
        // Laver en dialog til input af brugerens ticket nr.
        int nr = 0;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Angiv dit garderobe nr");
        dialog.setHeaderText("Data Mikes | Afhent jakke");  // Fjerner den grimme overskirft
        dialog.setContentText("Angiv dit garderobe nr");

        // Viser dialogen og venter på brugerens input
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(number -> {
            try {
                int inputNumber = Integer.parseInt(number);
                System.out.println("Det indtastede tal er: " + inputNumber);
                // Her skal vi kalde vores metode der gør noget ved dette nummer(Findet det osv osv)

                boolean removed = FileIO.removeCustomerByTicketNumber(inputNumber);
                System.out.println("Customer removed: " + removed);

                if (removed) {
                    alertBoks("Mange tak for besøget du har hentet: " + Customer.getBelongings());
                } else {
                    alertBoks("Vi kunne ikke finde noget på det nummer?");
                }
            } catch (NumberFormatException e) {
                System.err.println("Indtast venligst et gyldigt tal.");
            }
        });
    }

    public void createTicket() {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Opret Ticket");
        dialog.setHeaderText("Indtast informationerne for at oprette en ticket");

        // Baggrundsfarve på vores dialog
        dialog.getDialogPane().setStyle("-fx-background-color: #737373;");

        // Opretter vores knapper ok og cancel
        ButtonType createButtonType = new ButtonType("Opret", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Placerer vores inputs i et grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstName = new TextField();
        firstName.setPromptText("Fornavn");
        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("Telefonnummer");

        // ChoiceBox for hvilken type af tøj man ligger
        ChoiceBox<String> clothingType = new ChoiceBox<>();
        clothingType.getItems().addAll("Jakke", "Accessories");
        clothingType.setValue("Jakke"); // Sæt standardværdien

        grid.add(new Label("Fornavn:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Telefonnummer:"), 0, 1);
        grid.add(phoneNumber, 1, 1);
        grid.add(new Label("Tøjtype:"), 0, 2);
        grid.add(clothingType, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Starter med fokus på navne feltet
        Platform.runLater(firstName::requestFocus);

        // Laver vores data om til en array af data
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new String[] {firstName.getText(), phoneNumber.getText(), clothingType.getValue()};
            }
            return null;
        });

        // Venter på det indtastet resultat
        Optional<String[]> result = dialog.showAndWait();

        result.ifPresent(data -> {
            String fName = data[0];
            String phone = data[1];
            String tojType = data[2];

            // For debugging purposes
            System.out.println("Fornavn: " + fName);
            System.out.println("Telefonnummer: " + phone);
            System.out.println("Tøjtype: " + tojType);

            String customerID = generateCustomerNumber(fName, phone);
            if (!fName.isEmpty() && !phone.isEmpty()) {
                System.out.println("DEBUG: Vi kommer hertil. " + tojType);
                Customer customer = new Customer(customerID, fName, phone, 0, tojType); // Standard ticket nummer indtil det genereres af ticketHandler
                TicketHandler.ticketGeneration(customer);
                FileIO.saveUserLastTicketNumber(customer);

                // Viser dialog med en lille velkommen besked
                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Velkommen!");
                    alert.setHeaderText(null);
                    alert.setContentText("Velkommen " + fName + "! Dit kø nummer er " + customer.getTicketNumber() + ". Du har lagt en " + tojType + " hos os.");
                    alert.showAndWait();
                });
            }
        });
    }

    // Metoder der laver vores customer nummer
    public static String generateCustomerNumber(String fName, String phone) {
        //Tager 2 første bogstaver fra vores navn og sidste 4 cifre/bogstaver fra vores telefonnummer
        String firstNamePart = fName.substring(0, 2).toLowerCase(); // Sikre at alt står med småt.
        String phonePart = phone.substring(phone.length() - 4).toLowerCase();
        return firstNamePart + phonePart;
    }


    @FXML
    private void openFAQTabsWindow() {
        TabPane tabPane = new TabPane();

        Tab generalTab = new Tab("Generelt");
        Tab accountTab = new Tab("Brugerkonto");

        VBox generalContent = new VBox();
        Label generalQuestion1 = new Label("Spørgsmål 1: Var det her en fed opgave?");
        TextArea generalAnswer1 = new TextArea("Svar 1: Meget spændende, uhhhh. 2+2=4");

        generalAnswer1.setWrapText(true);
        generalAnswer1.setEditable(false);

        generalContent.getChildren().addAll(generalQuestion1, generalAnswer1);

        generalTab.setContent(generalContent);

        VBox accountContent = new VBox();
        Label accountQuestion1 = new Label("Spørgsmål 1: Hvordan ændrer jeg min adgangskode?");
        TextArea accountAnswer1 = new TextArea("Svar 1: ved at huske den brormis");

        accountAnswer1.setWrapText(true);
        accountAnswer1.setEditable(false);

        accountContent.getChildren().addAll(accountQuestion1, accountAnswer1);

        accountTab.setContent(accountContent);

        tabPane.getTabs().addAll(generalTab, accountTab);

        Scene scene = new Scene(tabPane, 400, 300);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("FAQ");
        stage.show();
    }
}

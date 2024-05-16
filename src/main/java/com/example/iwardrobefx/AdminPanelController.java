package com.example.iwardrobefx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.property.ReadOnlyStringWrapper;

public class AdminPanelController implements Initializable {

    public Label maxCustomerCount;
    public PieChart pieChart;

    @FXML
    private Label clubLoggedOn;

    @FXML
    private MenuItem logOfCompanyButton;


    @FXML
    private TableView<ObservableList<String>> brugerTable;

    @FXML
    private TableColumn<ObservableList<String>, String> idColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> navnColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> telefonnummerColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> ticketNumberColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> tojtype;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAdminMenu();


        //Opretter vores table med data:
        idColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(0)));
        navnColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(1)));
        telefonnummerColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(2)));
        ticketNumberColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(3)));
        tojtype.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(4)));


        // Load data from CSV
        ObservableList<ObservableList<String>> brugere = FileIO.loadBrugereFromCSV();
        brugerTable.setItems(brugere);

        int maxGuests = Company.getCapacity();
        int checkedInGuests = FileIO.loadBrugereFromCSV().toArray().length;
        System.out.println("DEBUG ANTAL GÆSTER TJEKKET IND: " + checkedInGuests + " Antal gæster der er plads til: " + maxGuests);

        // Calculate remaining guests
        int remainingGuests = maxGuests - checkedInGuests;

        // Add data to PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Antal tøj i garderoben", checkedInGuests),
                new PieChart.Data("Plads tilbage i garderoben", remainingGuests)
        );
        pieChart.setData(pieChartData);
    }

    private void setupAdminMenu() {
        // Tilføj debug-udskrift
        System.out.println("DEBUG: Loader admin siden..");
        // Tjekker først at Comapny name kan findes før vi sætter det!
        String companyName = Company.getName();
        if (companyName == null) {
            System.err.println("Company name is null");
            companyName = "Unknown";
        }
        clubLoggedOn.setText("Logget på som " + companyName);


        // Skift tekst på max customer count
        maxCustomerCount.setText("Maks antal kunder: " + Company.getCapacity());
    }

    @FXML
    public void closeMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) clubLoggedOn.getScene().getWindow(); // Brug en visuel komponent for at få scenen
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

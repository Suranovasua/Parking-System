package com.example.demo18;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;

import static com.example.demo18.ExpiredRecordsTask.checkAndDeleteExpiredRecords;

public class HelloController {
    @FXML
    private TableColumn<UserCar, String> IDColumn;

    @FXML
    private TableColumn<UserCar, String> NameColumn;

    @FXML
    private TableColumn<UserCar, String> CarNameColumn;

    @FXML
    private TableColumn<UserCar, String> CarNumberColumn;

    @FXML
    private TableColumn<UserCar, String> DateFromColumn;

    @FXML
    private TableColumn<UserCar, String> DateToColumn;

    @FXML
    private TableColumn<UserCar, String> ParkingPlaceColumn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<UserCar> table;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCarName;

    @FXML
    private TextField txtCarNumber;

    @FXML
    private DatePicker dateFromPicker;

    @FXML
    private DatePicker dateToPicker;

    @FXML
    private ChoiceBox<String> choiceParkingPlace;

    @FXML
    private ChoiceBox<CarModel> choiceCarModel; // New ChoiceBox for car models
    @FXML
    private Label welcomeLabel;

    @FXML
    private Label balanceLabel;
    @FXML
    private Button addBalanceButton;

    private String username;

    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;

    public void initialize() {
        Connect();
        updateChoiceParkingPlace(); // Update the ChoiceBox
        loadCarModels(); // Load car models into the choiceCarModel ChoiceBox
        table();
    }

    private void updateChoiceParkingPlace() {
        ObservableList<String> availablePlaces = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16");

        try {
            // Fetch busy places from the database
            PreparedStatement busyPlacesStmt = con.prepareStatement("SELECT DISTINCT datefrom, dateto FROM parking");
            ResultSet busyPlacesResultSet = busyPlacesStmt.executeQuery();
            while (busyPlacesResultSet.next()) {
                String busyPlace = busyPlacesResultSet.getString("parkingPlace");
                availablePlaces.remove(busyPlace);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        choiceParkingPlace.setItems(availablePlaces);
    }

    private void loadCarModels() {
        ObservableList<CarModel> carModels = FXCollections.observableArrayList();

        try {
            // Fetch car models from the database
            PreparedStatement carModelsStmt = con.prepareStatement("SELECT * FROM car_models");
            ResultSet carModelsResultSet = carModelsStmt.executeQuery();

            // Populate the carModels list
            while (carModelsResultSet.next()) {
                String modelId = carModelsResultSet.getString("id");
                String modelName = carModelsResultSet.getString("model_name");
                CarModel carModel = new CarModel(modelId, modelName);
                carModels.add(carModel);
            }

            // Set the car models in the ChoiceBox
            choiceCarModel.setItems(carModels);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setUserInfo(String username) {
        this.username = username;
        updateWelcomeMessage();
        updateBalance(); // Call a method to update the initial balance
    }
    private void updateWelcomeMessage() {
        welcomeLabel.setText("Welcome " + username);
    }
    private void updateBalance() {
        // Retrieve initial balance from the database based on the username
        double initialBalance = getInitialBalance(username);

        // Display the initial balance
        balanceLabel.setText("Initial Balance: " + initialBalance);
    }
    private double getInitialBalance(String username) {
        // Query the database to get the initial balance based on the username
        // You need to implement this method based on your database schema
        // Assuming you have a 'users' table with 'initialBalance' column
        String query = "SELECT initialBalance FROM users WHERE user_name = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("initialBalance");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Return a default value if the balance is not found
        return 0.0;
    }
    @FXML
    void addBalanceButtonClicked(ActionEvent event) {
        // Get the current initial balance
        double currentBalance = getInitialBalance(username);

        // Add 100 to the initial balance
        double newBalance = currentBalance + 100;

        // Update the database with the new balance
        updateInitialBalance(username, newBalance);

        // Update the UI to display the new balance
        updateBalance();
    }
    private void updateInitialBalance(String username, double newBalance) {
        // Update the 'initialBalance' in the 'users' table based on the username
        // You need to implement this method based on your database schema
        // Assuming you have a 'users' table with 'initialBalance' column
        String query = "UPDATE users SET initialBalance = ? WHERE user_name = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void subtractFromInitialBalance(String username, double amount) {
        // Get the current initial balance
        double currentBalance = getInitialBalance(username);

        // Check if there's enough money
        if (currentBalance < amount) {
            // Show an alert indicating not enough money
            return; // Stop further processing
        }

        // Subtract the specified amount
        double newBalance = currentBalance - amount;

        // Update the database with the new balance
        updateInitialBalance(username, newBalance);

        // Update the UI to display the new balance
        updateBalance();
    }
    private void showNotEnoughMoneyAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Not enough money!");

        alert.showAndWait();
    }
    @FXML
    void Add(ActionEvent event) {
        String name, carName, carNumber, dateFrom, dateTo, parkingPlace;
        name = txtName.getText();
        carName = choiceCarModel.getValue() != null ? choiceCarModel.getValue().getModelName() : null;
        carNumber = txtCarNumber.getText();
        dateFrom = dateFromPicker.getValue() != null ? dateFromPicker.getValue().toString() : null;
        dateTo = dateToPicker.getValue() != null ? dateToPicker.getValue().toString() : null;
        parkingPlace = choiceParkingPlace.getValue();


        try {
            if (name.isEmpty() || carName == null || carNumber.isEmpty() || dateFrom == null || dateTo == null || parkingPlace == null) {
                // Display an alert indicating that all fields must be filled
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Incomplete Information");
                alert.setContentText("Please fill in all the required information.");

                alert.showAndWait();
                return; // Exit the method if any field is empty
            }
            double currentBalance = getInitialBalance(username);
            if (currentBalance >= 50) {
                subtractFromInitialBalance(username, 50);
                pst = con.prepareStatement("insert into parking (name, carName, carNumber, dateFrom, dateTo, parkingPlace) values (?, ?, ?, ?, ?, ?)");
                pst.setString(1, name);
                pst.setString(2, carName);
                pst.setString(3, carNumber);
                pst.setDate(4, java.sql.Date.valueOf(dateFrom));
                pst.setDate(5, java.sql.Date.valueOf(dateTo));
                pst.setString(6, parkingPlace);
                pst.executeUpdate();
                checkAndDeleteExpiredRecords();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Parking System");
                alert.setHeaderText("Parking Booking");
                alert.setContentText("Booking successful!");

                alert.showAndWait();

                updateChoiceParkingPlace(); // Update the ChoiceBox
                table();
                clearFields();
            } else {
                showNotEnoughMoneyAlert();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }
    private void checkAndDeleteExpiredRecords() {
        try {
            Connect();

            // Delete records with dateTo in the past
            String deleteQuery = "DELETE FROM parking WHERE dateTo < ?";
            pst = con.prepareStatement(deleteQuery);
            pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void closeResources() {
        try {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        myIndex = table.getSelectionModel().getSelectedIndex();
            id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
            try {
                pst = con.prepareStatement("delete from parking where id = ? ");
                pst.setInt(1, id);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Parking System");
                alert.setHeaderText("Parking Booking");
                alert.setContentText("Booking deleted!");

                alert.showAndWait();

                updateChoiceParkingPlace(); // Update the ChoiceBox
                table();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }


    @FXML
    void Update(ActionEvent event) {
        String name, carName, carNumber, dateFrom, dateTo, parkingPlace;
        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
        name = txtName.getText();
        carName = choiceCarModel.getValue().getModelName(); // Use choiceCarModel instead of txtCarName
        carNumber = txtCarNumber.getText();
        dateFrom = dateFromPicker.getValue().toString();
        dateTo = dateToPicker.getValue().toString();
        parkingPlace = choiceParkingPlace.getValue();
            try {
                pst = con.prepareStatement("update parking set name = ?, carName = ?, carNumber = ?, dateFrom = ?, dateTo = ?, parkingPlace = ? where id = ? ");
                pst.setString(1, name);
                pst.setString(2, carName);
                pst.setString(3, carNumber);
                pst.setDate(4, java.sql.Date.valueOf(dateFrom));
                pst.setDate(5, java.sql.Date.valueOf(dateTo));
                pst.setString(6, parkingPlace);
                pst.setInt(7, id);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Parking System");
                alert.setHeaderText("Parking Booking");
                alert.setContentText("Booking updated!");

                alert.showAndWait();

                updateChoiceParkingPlace(); // Update the ChoiceBox
                table();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }

    }

    public void Connect() {
        String url = "jdbc:postgresql:parking-systemdb";
        String username = "postgres";
        String password = "a";
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void clearFields() {
        txtName.clear();
        txtCarNumber.clear();
        dateFromPicker.setValue(null);
        dateToPicker.setValue(null);
        choiceParkingPlace.setValue(null);
        choiceCarModel.setValue(null);
    }

    public void table() {
        Connect();
        ObservableList<UserCar> userCars = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("select id, name, carName, carNumber, dateFrom, dateTo, parkingPlace from parking");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                UserCar userCar = new UserCar();
                userCar.setId(rs.getString("id"));
                userCar.setName(rs.getString("name"));
                userCar.setCarName(rs.getString("carName"));
                userCar.setCarNumber(rs.getString("carNumber"));
                userCar.setDateFrom(rs.getString("dateFrom"));
                userCar.setDateTo(rs.getString("dateTo"));
                userCar.setParkingPlace(rs.getString("parkingPlace"));
                userCars.add(userCar);
            }
            table.setItems(userCars);
            IDColumn.setCellValueFactory(f -> f.getValue().idProperty());
            NameColumn.setCellValueFactory(f -> f.getValue().nameProperty());
            CarNameColumn.setCellValueFactory(f -> f.getValue().carNameProperty());
            CarNumberColumn.setCellValueFactory(f -> f.getValue().carNumberProperty());
            DateFromColumn.setCellValueFactory(f -> f.getValue().dateFromProperty());
            DateToColumn.setCellValueFactory(f -> f.getValue().dateToProperty());
            ParkingPlaceColumn.setCellValueFactory(f -> f.getValue().parkingPlaceProperty());
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }


        table.setRowFactory(tv -> {
            TableRow<UserCar> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtName.setText(table.getItems().get(myIndex).getName());
                    txtCarNumber.setText(table.getItems().get(myIndex).getCarNumber());
                    dateFromPicker.setValue(java.sql.Date.valueOf(table.getItems().get(myIndex).getDateFrom()).toLocalDate());
                    dateToPicker.setValue(java.sql.Date.valueOf(table.getItems().get(myIndex).getDateTo()).toLocalDate());
                    choiceParkingPlace.setValue(table.getItems().get(myIndex).getParkingPlace());
                }
            });
            return myRow;
        });
    }
}

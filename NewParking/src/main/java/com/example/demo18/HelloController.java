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
        ObservableList<String> availablePlaces = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "125", "126", "127", "128", "129", "130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "140", "141", "142", "143", "144", "145", "146", "147", "148", "149", "150", "151", "152", "153", "154", "155", "156", "157", "158", "159", "160", "161", "162", "163", "164", "165", "166", "167", "168", "169", "170", "171", "172", "173", "174", "175", "176", "177", "178", "179", "180", "181", "182", "183", "184", "185", "186", "187", "188", "189", "190", "191", "192", "193", "194", "195", "196", "197", "198", "199", "200", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216", "217", "218", "219", "220", "221", "222", "223", "224", "225", "226", "227", "228", "229", "230", "231", "232", "233", "234", "235", "236", "237", "238", "239", "240", "241", "242", "243", "244", "245", "246", "247", "248", "249", "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279", "280", "281", "282", "283", "284", "285", "286", "287", "288", "289", "290", "291", "292", "293", "294", "295", "296", "297", "298", "299", "300");
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
            if (currentBalance >= 0) {
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
        String password = "123456";
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

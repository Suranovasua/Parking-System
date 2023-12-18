package com.example.demo18;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private Button closeButton;
    @FXML
    private Label signUpMessage;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField paymentCardNumberTextField; // Added TextField for payment card number

    public void signUpButtonOnAction(ActionEvent event) throws IOException {
        if (userNameTextField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {
            signUpUser();
            signUpMessage.setText("Sign Up successfully");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("logIn.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 800, 600));
            newStage.setTitle("Hello, User!");

            // Show the new stage
            newStage.show();

            // Close the current stage
            Stage currentStage = (Stage) closeButton.getScene().getWindow();
            currentStage.close();
        } else {
            signUpMessage.setText("Please enter username or password");
        }
    }

    public void CloseButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void signUpUser() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String username = userNameTextField.getText();
        String password = passwordField.getText();
        String paymentCardNumber = paymentCardNumberTextField.getText();

        String insertField = "INSERT INTO users(user_name, password, paymentCardNumber, initialBalance) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = connectDB.prepareStatement(insertField)) {
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, paymentCardNumber);
            pst.setDouble(4, 0.0); // Assuming initialBalance is a numeric field, adjust the type accordingly
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

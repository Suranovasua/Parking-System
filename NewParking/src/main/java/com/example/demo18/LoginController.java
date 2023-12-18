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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessage;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void loginButtonAction(ActionEvent event){
        loginMessage.setText("You try to log in");

        if (usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false){
            validLogin();
        } else{
            loginMessage.setText("Please enter username or password");
        }
    }
    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void validLogin(){
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String verifyLogin = "Select count(1) from users where user_name = '" + usernameTextField.getText() + "' AND password = '" + enterPasswordField.getText() + "'";

        try{
            Statement stmt = connectDB.createStatement();
            ResultSet queryResult = stmt.executeQuery(verifyLogin);

            while (queryResult.next()){
                if (queryResult.getInt(1) == 1){
                    loginMessage.setText("lets goo");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                    Parent root = loader.load();
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root, 800, 600));
                    newStage.setTitle("Hello, User!");

                    // Show the new stage
                    newStage.show();

                    HelloController helloController = loader.getController();
                    helloController.setUserInfo(usernameTextField.getText());

                    // Close the current stage
                    Stage currentStage = (Stage) cancelButton.getScene().getWindow();
                    currentStage.close();
                } else {
                    loginMessage.setText("Invalid login");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

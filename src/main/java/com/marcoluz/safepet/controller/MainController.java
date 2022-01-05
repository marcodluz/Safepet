package com.marcoluz.safepet.controller;

import com.marcoluz.safepet.DataValidation;
import com.marcoluz.safepet.dao.AccountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    @FXML
    private TextField customer_firstname;
    @FXML
    private TextField customer_lastname;
    @FXML
    public TextField customer_email;
    @FXML
    private PasswordField customer_password;
    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    @FXML
    private Label firstNameError;
    @FXML
    private Label lastNameError;
    @FXML
    private Label errorLogin;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane middleRootPane;
    @FXML
    private Button btnMyPets;
    @FXML
    private Button btnMyAppointments;
    @FXML
    private Button btnHealth;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnAccount;
    @FXML
    private Button btnAddPet;
    @FXML
    private StackPane contentArea;

    // Main Variables
    public static int id;
    public static String firstName;
    public static String lastName;
    public static String email;
    private String mydata[] = new String[3];

    @FXML
    public void createAccount(ActionEvent event) throws IOException, SQLException {
        boolean firstNameValidatorChar = DataValidation.charOnly(customer_firstname, firstNameError, "Only characters");
        boolean firstNameValidatorNull = DataValidation.textFieldNull(customer_firstname, firstNameError, "Enter your first name");
        boolean lastNameValidatorChar = DataValidation.charOnly(customer_lastname, lastNameError, "Only characters");
        boolean lastNameValidatorNull = DataValidation.textFieldNull(customer_lastname, lastNameError, "Enter your last name");
        boolean emailValidatorNull = DataValidation.textFieldNull(customer_email, emailError, "Choose a valid address");
        boolean emailValidatorFormat = DataValidation.emailFormat(customer_email, emailError, "Choose a valid address");
        boolean emailExists = AccountDAO.checkEmailExists(customer_email, emailError, "Email already exists");
        boolean passwordValidatorNull = DataValidation.passwordFieldNull(customer_password, passwordError, "Enter a password");

        // Check if all the data validations are returning TRUE (means that passed the validation)
        if (firstNameValidatorChar && !firstNameValidatorNull && lastNameValidatorChar
                && !lastNameValidatorNull && emailValidatorFormat && !emailExists
                && !emailValidatorNull && !passwordValidatorNull)
        {
            mydata[0] = this.customer_firstname.getText();
            mydata[1] = this.customer_lastname.getText();
            mydata[2] = this.customer_email.getText();

            String insertsql = "INSERT INTO account (id, first_name, last_name, email, password) " +
                    "VALUES (?, ?, ?, ?, crypt('"+ customer_password.getText() +"', gen_salt('bf')));";

            AccountDAO.insertAccountDetails(insertsql, mydata);

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/signup-success.fxml"));
            rootPane.getChildren().setAll(pane);

            System.out.println("Account successfully created!");
        }
        else {
            System.out.println("Account not created!");
        }
    }

    @FXML
    private void login(ActionEvent event) throws IOException, SQLException {

        // Check if the credentials match one account
        if (AccountDAO.checkLoginDetails(customer_email.getText(), customer_password.getText())) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/dashboard.fxml"));
            rootPane.getChildren().setAll(pane);

            // Save the account details to use while logged in
            // (could have been used another approach, but for a simple application this one is more than fine)
            id = AccountDAO.getId(customer_email.getText(), customer_password.getText());
            firstName = AccountDAO.getFirstName(customer_email.getText(), customer_password.getText());
            lastName = AccountDAO.getLastName(customer_email.getText(), customer_password.getText());
            email = AccountDAO.getEmail(customer_email.getText());
        }
        else {
            errorLogin.setText("Wrong email or password");
        }
    }

    @FXML
    public void goToLogin(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void goToSignUp(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/signup.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    // Menu selection system
    @FXML
    private void handleClicks(ActionEvent event) throws IOException {

        // Select the page to show by the button fx:id
        if(event.getSource() == btnMyPets) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/pets-page.fxml"));
            contentArea.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            middleRootPane.getChildren().setAll(pane);
        }
        else if(event.getSource() == btnMyAppointments) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/appointments-page.fxml"));
            contentArea.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            middleRootPane.getChildren().setAll(pane);
        }
        else if(event.getSource() == btnHealth) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/health-page.fxml"));
            middleRootPane.getChildren().setAll(pane);
        }
        else if(event.getSource() == btnAccount) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/account.fxml"));
            middleRootPane.getChildren().setAll(pane);
        }
        else if(event.getSource() == btnLogout) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/login.fxml"));
            rootPane.getChildren().setAll(pane);
            System.out.println("Logout Successful!");
        }
        else if(event.getSource() == btnAddPet) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/add-pet-page.fxml"));
            middleRootPane.getChildren().setAll(pane);
        }
    }
}



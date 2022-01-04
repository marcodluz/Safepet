package com.marcoluz.safepet.controller;

import com.marcoluz.safepet.DataValidation;
import com.marcoluz.safepet.dao.AccountDAO;
import com.marcoluz.safepet.util.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {
    @FXML
    private Label oldPasswordError;
    @FXML
    private TextField accountFirstName;
    @FXML
    private TextField accountLastName;
    @FXML
    private TextField accountEmail;
    @FXML
    private PasswordField accountNewPassword;
    @FXML
    private PasswordField accountPassword;
    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    @FXML
    private Label firstNameError;
    @FXML
    private Label lastNameError;
    @FXML
    private AnchorPane middleRootPane;

    private String mydata[] = new String[4];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAccountInfo();
    }

    public void updateAccount(ActionEvent event) throws IOException {
        boolean firstNameValidatorChar = DataValidation.charOnly(accountFirstName, firstNameError, "Only characters");
        boolean firstNameValidatorNull = DataValidation.textFieldNull(accountFirstName, firstNameError, "Enter your first name");
        boolean lastNameValidatorChar = DataValidation.charOnly(accountLastName, lastNameError, "Only characters");
        boolean lastNameValidatorNull = DataValidation.textFieldNull(accountLastName, lastNameError, "Enter your last name");
        boolean emailValidatorNull = DataValidation.textFieldNull(accountEmail, emailError, "Choose a valid address");
        boolean emailValidatorFormat = DataValidation.emailFormat(accountEmail, emailError, "Choose a valid address");
        boolean emailExists = AccountDAO.checkEmailExists(accountEmail, emailError, "Email already exists");
        boolean passwordValidatorNull = DataValidation.passwordFieldNull(accountNewPassword, passwordError, "");
        boolean oldPasswordValidatorNull = DataValidation.passwordFieldNull(accountPassword, oldPasswordError, "Enter a password");
        boolean oldPasswordCheck = AccountDAO.checkPassword(accountPassword, oldPasswordError, "Wrong Password");

        if (firstNameValidatorChar && !firstNameValidatorNull && lastNameValidatorChar
                && !lastNameValidatorNull && !emailValidatorNull && emailValidatorFormat
                && !emailExists && !oldPasswordValidatorNull && oldPasswordCheck)
        {
            mydata[0] = this.accountFirstName.getText();
            mydata[1] = this.accountLastName.getText();
            mydata[2] = this.accountEmail.getText();

            if(passwordValidatorNull) {
                mydata[3] = this.accountPassword.getText();
            }
            else {
                mydata[3] = this.accountNewPassword.getText();
            }

            String updateSQL = "UPDATE account SET first_name ='"+ mydata[0] +"', last_name = '"+ mydata[1] +"', email = '"+ mydata[2] +"', password = '"+ mydata[3] +"' WHERE (email = '"+ MainController.email +"');";
            DBUtil.sqlUpdate(updateSQL);

            MainController.firstName = this.accountFirstName.getText();
            MainController.lastName = this.accountLastName.getText();
            MainController.email = this.accountEmail.getText();

            System.out.println("Valid Details - Account Updated");

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/account-success.fxml"));
            middleRootPane.getChildren().setAll(pane);
        }
    }

    private void setAccountInfo() {
        accountFirstName.setText(MainController.firstName);
        accountLastName.setText(MainController.lastName);
        accountEmail.setText(MainController.email);
    }
}

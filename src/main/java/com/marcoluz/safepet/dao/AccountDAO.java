package com.marcoluz.safepet.dao;

import com.marcoluz.safepet.controller.MainController;
import com.marcoluz.safepet.util.DBUtil;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
    public static void insertAccountDetails(String pStatement, String[] values){
        try{
            PreparedStatement prepStat = DBUtil.db.prepareStatement(pStatement);
            prepStat.setInt(1, generateAccountId());
            prepStat.setString(2,values[0]);
            prepStat.setString(3,values[1]);
            prepStat.setString(4,values[2]);
            prepStat.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static boolean checkLoginDetails(String email, String password) throws SQLException {
        String SQL = "SELECT * FROM account WHERE (email = '"+ email +"' AND  password = crypt('"+ password +"', password)) LIMIT 1";
        ResultSet rs = DBUtil.sqlExecute(SQL);

        // Check if exists at least 1 row of data in the table
        if(rs.next()) {
            System.out.println("Account login successful!");
            return true;
        }
        else {
            System.out.println("Account login unsuccessful!");
            return false;
        }
    }

    public static boolean checkPassword(PasswordField password, Label errorLabel, String errorText) throws SQLException {
        if ((AccountDAO.getPassword(MainController.email).equals(password.getText()))) {
            // Set the error label with the error text
            errorLabel.setText(errorText);

            return true;
        }

        return false;
    }

    public static boolean checkEmail(TextField email, Label errorLabel, String errorText) throws SQLException {
        // Check if the emails are the same
        if ((AccountDAO.getEmail(MainController.email).equals(email.getText()))) {
            errorLabel.setText(errorText);
            return true;
        }
        return false;
    }

    public static boolean checkEmailExists(TextField email, Label errorLabel, String errorText) throws SQLException {
        String SQL = "SELECT email FROM account WHERE (email = '"+ email.getText() +"')";
        ResultSet rs = DBUtil.sqlExecute(SQL);

        // Check if email exists and is equal to the inserted one
        if(rs.next() && !(rs.getString(1).equals(MainController.email))) {
            errorLabel.setText(errorText);
            return true;
        } else {
            return false;
        }
    }

    public static int generateAccountId() throws SQLException {
        return DBUtil.getNewId("account");
    }

    public static int getId(String email, String password) throws SQLException {
        String SQL = "SELECT id FROM account WHERE (email = '"+ email +"' AND  password = crypt('"+ password +"', password)) LIMIT 1";
        ResultSet rs = DBUtil.sqlExecute(SQL);

        // Check if exists an account
        if(rs.next()) {
            return rs.getInt(1);
        }
        else {
            return 0;
        }
    }

    public static String getFirstName(String email, String password) throws SQLException {
        String SQL = "SELECT first_name FROM account WHERE (email = '"+ email +"' AND  password = crypt('"+ password +"', password)) LIMIT 1";
        ResultSet rs = DBUtil.sqlExecute(SQL);

        // Check if exists an account
        if(rs.next()) {
            return rs.getString(1);
        }
        else {
            return "";
        }
    }

    public static String getLastName(String email, String password) throws SQLException {
        String SQL = "SELECT last_name FROM account WHERE (email = '"+ email +"' AND  password = crypt('"+ password +"', password)) LIMIT 1";
        ResultSet rs = DBUtil.sqlExecute(SQL);

        // Check if exists an account
        if(rs.next()) {
            return rs.getString(1);
        }
        else {
            return "";
        }
    }

    public static String getEmail(String email) throws SQLException {
        String SQL = "SELECT email FROM account WHERE (email = '"+ email +"') LIMIT 1";
        ResultSet rs = DBUtil.sqlExecute(SQL);

        // Check if exists an account
        if(rs.next()) {
            return rs.getString(1);
        }
        else {
            return "";
        }
    }

    public static String getPassword(String email) throws SQLException {
        String SQL = "SELECT email FROM account WHERE (email = '"+ email +"') LIMIT 1";
        ResultSet rs = DBUtil.sqlExecute(SQL);

        // Check if exists an account
        if(rs.next()) {
            return rs.getString(1);
        }
        else {
            return "";
        }
    }
}

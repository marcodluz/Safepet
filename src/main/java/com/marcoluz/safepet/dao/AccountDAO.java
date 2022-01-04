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
            prepStat.setString(5,values[3]);
            prepStat.executeUpdate();

        }catch (Exception e){
            System.out.println("SQL Error!");
            System.out.println(e);
        }
    }

    public static boolean checkLoginDetails(String email, String password) {
        String SQL = "SELECT count(*) AS total FROM account WHERE (email = ? AND  password = ?)";

        try (Connection conn = DBUtil.connect();
             PreparedStatement preStat = conn.prepareStatement(SQL))
        {
            preStat.setString(1, email);
            preStat.setString(2, password);
            ResultSet result = preStat.executeQuery();
            result.next();

            if(result.getInt("total") == 1) {
                System.out.println("Account login successful!");
                return true;
            }
            else {
                System.out.println("Account login unsuccessful!");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean checkPassword(PasswordField password, Label errorLabel, String errorText) {
        if ((AccountDAO.getPassword(MainController.email).equals(password.getText()))) {
            errorLabel.setText(errorText);

            return true;
        }

        return false;
    }

    public static boolean checkEmail(TextField email, Label errorLabel, String errorText) {
        if ((AccountDAO.getEmail(MainController.email).equals(email.getText()))) {
            errorLabel.setText(errorText);
            return true;
        }
        return false;
    }

    public static boolean checkEmailExists(TextField email, Label errorLabel, String errorText) {
        String SQL = "SELECT email FROM account WHERE (email = ?)";

        try (Connection conn = DBUtil.connect();
             PreparedStatement preStat = conn.prepareStatement(SQL))
        {
            preStat.setString(1, email.getText());
            ResultSet result = preStat.executeQuery();
            if(result.next() && !(result.getString(1).equals(MainController.email))) {
                errorLabel.setText(errorText);
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static int generateAccountId() {
        return DBUtil.getNewId("account");
    }

    public static int getId(String email, String password) {
        String SQL = "SELECT id FROM account WHERE (email = ? AND  password = ?)";

        try (Connection conn = DBUtil.connect();
             PreparedStatement preStat = conn.prepareStatement(SQL))
        {
            preStat.setString(1, email);
            preStat.setString(2, password);
            ResultSet result = preStat.executeQuery();
            result.next();

            return result.getInt(1);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0;
    }

    public static String getFirstName(String email, String password) {
        String SQL = "SELECT first_name FROM account WHERE (email = ? AND  password = ?)";

        try (Connection conn = DBUtil.connect();
             PreparedStatement preStat = conn.prepareStatement(SQL))
        {
            preStat.setString(1, email);
            preStat.setString(2, password);
            ResultSet result = preStat.executeQuery();
            result.next();

            return result.getString(1);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return "";
    }

    public static String getLastName(String email, String password) {
        String SQL = "SELECT last_name FROM account WHERE (email = ? AND  password = ?)";

        try (Connection conn = DBUtil.connect();
             PreparedStatement preStat = conn.prepareStatement(SQL))
        {
            preStat.setString(1, email);
            preStat.setString(2, password);
            ResultSet result = preStat.executeQuery();
            result.next();

            return result.getString(1);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return "";
    }

    public static String getEmail(String email) {
        String SQL = "SELECT email FROM account WHERE (email = ?)";

        try (Connection conn = DBUtil.connect();
             PreparedStatement preStat = conn.prepareStatement(SQL))
        {
            preStat.setString(1, email);
            ResultSet result = preStat.executeQuery();
            result.next();

            return result.getString(1);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return "";
    }

    public static String getPassword(String email) {

        String SQL = "SELECT password FROM account WHERE (email = ?)";

        try (Connection conn = DBUtil.connect();
             PreparedStatement preStat = conn.prepareStatement(SQL))
        {
            preStat.setString(1, email);
            ResultSet result = preStat.executeQuery();
            result.next();

            return result.getString(1);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return "";
    }
}

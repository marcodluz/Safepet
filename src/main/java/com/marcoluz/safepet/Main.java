package com.marcoluz.safepet;

import com.marcoluz.safepet.util.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        /////////////////////////////////////////////////////////////////////////////////////
        //
        // GO TO DBUtil.java AND CHANGE THE POSTGRESQL DATABASE CREDENTIALS ON THE TOP!!!!
        //
        /////////////////////////////////////////////////////////////////////////////////////

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize the main window for the app
        Parent root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
        primaryStage.setTitle("SafePet");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Check Database connection
        if(!DBUtil.getConneted()){
            System.exit(0);
        }
        else {
            // Create all the tables and extensions for the app
            String account = "CREATE TABLE account (id INTEGER PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(30), password VARCHAR(100));";
            DBUtil.sqlUpdate(account);

            String pet = "CREATE TABLE pet (id INTEGER PRIMARY KEY, account_id INTEGER, name VARCHAR(50), specie VARCHAR(50), coat_colour VARCHAR(50), dob VARCHAR(20), notes VARCHAR(200));";
            DBUtil.sqlUpdate(pet);

            String medication = "CREATE TABLE medication (id INTEGER PRIMARY KEY, account_id INTEGER, pet_id INTEGER, med_name VARCHAR(50), date VARCHAR(20));";
            DBUtil.sqlUpdate(medication);

            String weight = "CREATE TABLE weight (id INTEGER PRIMARY KEY, account_id INTEGER, pet_id INTEGER, weight VARCHAR(50), date VARCHAR(20));";
            DBUtil.sqlUpdate(weight);

            String appointment = "CREATE TABLE appointment (id INTEGER PRIMARY KEY, account_id INTEGER, pet_id INTEGER, type VARCHAR(50), status VARCHAR(50), date VARCHAR(20));";
            DBUtil.sqlUpdate(appointment);

            String pgcrypto = "CREATE EXTENSION pgcrypto;";
            DBUtil.sqlExecute(pgcrypto);
        }
    }
}
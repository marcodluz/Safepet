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
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
        primaryStage.setTitle("SafePet");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        if(!DBUtil.getConneted()){
            System.out.println("Wrong Password...");
            System.exit(0);
        }
        else {
            String account = "CREATE TABLE account (id INTEGER PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(30), password VARCHAR(30));";
            DBUtil.sqlUpdate(account);

            String pet = "CREATE TABLE pet (id INTEGER PRIMARY KEY, account_id INTEGER, name VARCHAR(50), specie VARCHAR(50), coat_colour VARCHAR(50), dob VARCHAR(20), notes VARCHAR(200));";
            DBUtil.sqlUpdate(pet);

            String medication = "CREATE TABLE medication (id INTEGER PRIMARY KEY, account_id INTEGER, pet_id INTEGER, med_name VARCHAR(50), date VARCHAR(20));";
            DBUtil.sqlUpdate(medication);

            String weight = "CREATE TABLE weight (id INTEGER PRIMARY KEY, account_id INTEGER, pet_id INTEGER, weight VARCHAR(50), date VARCHAR(20));";
            DBUtil.sqlUpdate(weight);

            String appointment = "CREATE TABLE appointment (id INTEGER PRIMARY KEY, account_id INTEGER, pet_id INTEGER, type VARCHAR(50), status VARCHAR(50), date VARCHAR(20));";
            DBUtil.sqlUpdate(appointment);
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}
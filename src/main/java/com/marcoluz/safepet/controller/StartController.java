package com.marcoluz.safepet.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            startProgram();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startProgram() throws IOException {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/login.fxml"));
            rootPane.getChildren().setAll(pane);
    }


}
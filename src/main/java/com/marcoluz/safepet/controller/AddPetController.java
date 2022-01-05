package com.marcoluz.safepet.controller;

import com.marcoluz.safepet.DataValidation;
import com.marcoluz.safepet.dao.PetDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddPetController {
    @FXML
    private TextField petName;
    @FXML
    private TextField petSpecie;
    @FXML
    private TextField petCoatColour;
    @FXML
    private DatePicker petDatePicker;
    @FXML
    private TextField petNotes;
    @FXML
    private AnchorPane middleRootPane;
    @FXML
    private Label petNameError;
    @FXML
    private Label petSpecieError;
    @FXML
    private Label petDobError;
    @FXML
    private Label petCoatColourError;

    // Main Variables
    private String mydata[] = new String[5];

    @FXML
    public void createPet(ActionEvent event) throws IOException {
        boolean petNameValidatorChar = DataValidation.charOnly(petName, petNameError, "Only characters");
        boolean petNameValidatorNull = DataValidation.textFieldNull(petName, petNameError, "Enter your pet's name");
        boolean petSpecieValidatorChar = DataValidation.charOnly(petSpecie, petSpecieError, "Only characters");
        boolean petSpecieValidatorNull = DataValidation.textFieldNull(petSpecie, petSpecieError, "Enter your pet's specie");
        boolean petDOBValidatorNull = DataValidation.datePickerNull(petDatePicker, petDobError, "Select your pet's date of birth");
        boolean petCoatColourValidatorChar = DataValidation.charOnly(petCoatColour, petCoatColourError, "Only characters");
        boolean petCoatColourValidatorNull = DataValidation.textFieldNull(petCoatColour, petCoatColourError, "Enter your pet's coat colour");

        if (petNameValidatorChar && !petNameValidatorNull && petSpecieValidatorChar
                && !petSpecieValidatorNull && petCoatColourValidatorChar && !petDOBValidatorNull && !petCoatColourValidatorNull)
        {
                mydata[0] = this.petName.getText();
                mydata[1] = this.petSpecie.getText();
                mydata[2] = this.petCoatColour.getText();
                mydata[3] = String.valueOf(this.petDatePicker.getValue());
                mydata[4] = this.petNotes.getText();

                String SQL = "INSERT INTO pet (id, account_id, name, specie, coat_colour, dob, notes) values (?, ?, ?, ?, ?, ?, ?);";
                PetDAO.insertPetDetails(SQL, mydata);

                AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/pets-page.fxml"));
                middleRootPane.getChildren().setAll(pane);

                System.out.println("Pet successfully created!");
        }
        else {
            System.out.println("Pet not created!");
        }
    }
}

package com.marcoluz.safepet.controller;

import com.marcoluz.safepet.DataValidation;
import com.marcoluz.safepet.dao.PetDAO;
import com.marcoluz.safepet.util.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPetController implements Initializable {
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

    private String mydata[] = new String[5];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPetInfo();
    }

    public void updatePet(ActionEvent event) throws IOException {
        boolean petNameValidatorChar = DataValidation.charOnly(petName, petNameError, "Only characters");
        boolean petNameValidatorNull = DataValidation.textFieldNull(petName, petNameError, "Enter your pet's name");
        boolean petSpecieValidatorChar = DataValidation.charOnly(petSpecie, petSpecieError, "Only characters");
        boolean petSpecieValidatorNull = DataValidation.textFieldNull(petSpecie, petSpecieError, "Enter your pet's specie");
        boolean petDOBValidatorNull = DataValidation.datePickerNull(petDatePicker, petDobError, "Select your pet's date of birth");
        boolean petCoatColourValidatorChar = DataValidation.charOnly(petCoatColour, petCoatColourError, "Only characters");
        boolean petCoatColourValidatorNull = DataValidation.textFieldNull(petCoatColour, petCoatColourError, "Enter your pet's coat colour");


        // Check date if is less than today
        ///REMEMBER


        if (petNameValidatorChar && !petNameValidatorNull && petSpecieValidatorChar
                && !petSpecieValidatorNull && petCoatColourValidatorChar && !petDOBValidatorNull && !petCoatColourValidatorNull)
        {
            System.out.println("Pet successfully updated!");

            mydata[0] = this.petName.getText();
            mydata[1] = this.petSpecie.getText();
            mydata[2] = this.petCoatColour.getText();
            mydata[3] = String.valueOf(this.petDatePicker.getValue());
            mydata[4] = this.petNotes.getText();

            String updateSQL = "UPDATE pet SET name ='"+ mydata[0] +"', specie = '"+ mydata[1] +"', coat_colour = '"+ mydata[2] +"', dob = '"+ mydata[3] +"', notes = '"+ mydata[4] +"' WHERE (id = '"+ MyPetsController.selectedPetId +"');";
            DBUtil.sqlUpdate(updateSQL);

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/pets-page.fxml"));
            middleRootPane.getChildren().setAll(pane);

        }
        else {
            System.out.println("Pet not updated!");
        }
    }

    private void setPetInfo() {
        petName.setText(MyPetsController.selectedPetName);
        petSpecie.setText(MyPetsController.selectedPetSpecie);
        petCoatColour.setText(MyPetsController.selectedPetCoatColour);
        petDatePicker.setValue(MyPetsController.selectedPetDob);
        petNotes.setText(MyPetsController.selectedPetNotes);
    }
}

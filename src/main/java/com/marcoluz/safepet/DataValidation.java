package com.marcoluz.safepet;

import javafx.scene.control.*;

import java.util.Date;

public class DataValidation {

    public static boolean textFieldNull(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNull = false;
        String validationString = null;

        if(inputTextField.getText().isEmpty()) {
            isNull = true;
            validationString = validationText;
        }

        inputLabel.setText(validationString);
        return isNull;

    }

    public static boolean datePickerNull(DatePicker inputDatePicker, Label inputLabel, String validationText) {
        boolean isNull = false;
        String validationString = null;

        if(inputDatePicker.getValue() == null) {
            isNull = true;
            validationString = validationText;
        }

        inputLabel.setText(validationString);
        return isNull;

    }

    public static boolean comboBoxNull(ComboBox inputComboBox, Label inputLabel, String validationText) {
        boolean isNull = false;
        String validationString = null;

        if(inputComboBox.getValue() == null) {
            isNull = true;
            validationString = validationText;
        }

        inputLabel.setText(validationString);
        return isNull;

    }

    public static boolean passwordFieldNull(PasswordField inputTextField, Label inputLabel, String validationText) {
        boolean isNull = false;
        String validationString = null;

        if(inputTextField.getText().isEmpty()) {
            isNull = true;
            validationString = validationText;
        }

        inputLabel.setText(validationString);
        return isNull;

    }

    public static boolean emailFormat(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isEmail = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com")) {
            isEmail = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isEmail;
    }

    public static boolean charOnly(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isChar = true;
        String validationString = null;

        if (!inputTextField.getText().matches("^[a-zA-Z]*$")) {
            isChar = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isChar;
    }
}

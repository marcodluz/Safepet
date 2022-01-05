package com.marcoluz.safepet;

import javafx.scene.control.*;

public class DataValidation {

    public static boolean textFieldNull(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNull = false;
        String validationString = null;

        // Check if the the text field is empty
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

        // Check if the date picker has no date selected
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

        // Check if the combo box is empty
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

        // Check if the password is empty
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

        // Check if the email is in the correct format
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

        // Check if it is only characters
        if (!inputTextField.getText().matches("^[a-zA-Z]*$")) {
            isChar = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isChar;
    }

    public static boolean numberOnly(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNumber = true;
        String validationString = null;

        // Check if is only numbers
        if (!inputTextField.getText().matches("[0-9]+")) {
            isNumber = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isNumber;
    }
}

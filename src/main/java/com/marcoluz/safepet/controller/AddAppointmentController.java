package com.marcoluz.safepet.controller;

import com.marcoluz.safepet.DataValidation;
import com.marcoluz.safepet.dao.AppointmentsDAO;
import com.marcoluz.safepet.model.Pet;
import com.marcoluz.safepet.util.DBUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    @FXML
    private ComboBox appointmentPet;
    @FXML
    private ComboBox appointmentType;
    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private Label appointmentPetError;
    @FXML
    private Label appointmentTypeError;
    @FXML
    private Label appointmentDateError;
    @FXML
    private AnchorPane middleRootPane;

    private String mydata[] = new String[2];

    public static int petId;

    final ObservableList<Pet> petOptions = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillPetComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        appointmentPet.valueProperty().addListener(new ChangeListener<Pet>() {
            @Override
            public void changed(ObservableValue<? extends Pet> observableValue, Pet pet1, Pet pet2) {
                petId = pet2.getId();
            }
        });
    }

    public void createAppointment(ActionEvent event) throws IOException {
        boolean appointmentPetValidatorNull = DataValidation.comboBoxNull(appointmentPet, appointmentPetError, "You need to select one pet");
        boolean appointmentTypeValidatorNull = DataValidation.comboBoxNull(appointmentPet, appointmentTypeError, "You need to select one type");

        // Check date if is less than today
        ///REMEMBER

        if (!appointmentPetValidatorNull && !appointmentTypeValidatorNull
                && !isDatePast(String.valueOf(appointmentDatePicker.getValue()),  "yyyy-MM-dd"))
        {
            System.out.println("Appointment successfully created!");

            mydata[0] = (String) this.appointmentType.getValue();
            mydata[1] = String.valueOf(this.appointmentDatePicker.getValue());

            String SQL = "INSERT INTO appointment (id, account_id, pet_id, type, status, date) values (?, ?, ?, ?, ?, ?);";
            AppointmentsDAO.insertAppointmentDetails(SQL, mydata);

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/appointments-page.fxml"));
            middleRootPane.getChildren().setAll(pane);

        }
        else {
            System.out.println("Appointment not created!");
        }
    }

    public void fillPetComboBox() throws SQLException {
        String SQL = "SELECT * FROM pet WHERE account_id =" + MainController.id + " ORDER BY name ASC";

        ResultSet rs = DBUtil.sqlExecute(SQL);
        Pet pet;
        while (rs.next()){
            pet = new Pet(rs.getInt("id"), rs.getInt("account_id"),
                    rs.getString("name"),rs.getString("specie"), rs.getString("coat_colour"), rs.getString("dob"),
                    rs.getString("notes"));
            petOptions.add(pet);
        }

        appointmentPet.setItems(null);
        appointmentPet.setItems(petOptions);
    }

    public boolean isDatePast(final String date, final String dateFormat) {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate inputDate = LocalDate.parse(date, dtf);

        appointmentDateError.setText("You cannot choose a past date");

        return inputDate.isBefore(localDate);
    }


}

package com.marcoluz.safepet.controller;

import com.marcoluz.safepet.model.Appointment;
import com.marcoluz.safepet.util.DBUtil;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    @FXML
    private TableView<Appointment> tblview;
    @FXML
    private TableColumn<Appointment, Integer> clm_pet_id;
    @FXML
    private TableColumn<Appointment,String> clm_type;
    @FXML
    private TableColumn<Appointment,String> clm_date;
    @FXML
    private TableColumn<Appointment,String> clm_status;
    @FXML
    private TableView<Appointment> tblview2;
    @FXML
    private TableColumn<Appointment, Integer> clm_past_pet_id;
    @FXML
    private TableColumn<Appointment,String> clm_past_type;
    @FXML
    private TableColumn<Appointment,String> clm_past_date;
    @FXML
    private TableColumn<Appointment,String> clm_past_status;
    @FXML
    private AnchorPane middleRootPane;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listAppointments();
            listPastAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnCancel.disableProperty().bind(Bindings.isEmpty(tblview.getSelectionModel().getSelectedItems()));
        btnDelete.disableProperty().bind(Bindings.isEmpty(tblview.getSelectionModel().getSelectedItems()));
    }

    public ObservableList<Appointment> getAppointmentList() throws SQLException {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

        ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();
        String myQuery =
                "SELECT * FROM appointment WHERE (account_id = "+ MainController.id +" AND date >= '"+ localDate +"') ORDER BY date ASC";

        ResultSet rs = DBUtil.sqlExecute(myQuery);
        Appointment appointment;

        while (rs.next()){
            appointment = new Appointment(rs.getInt("id"), rs.getInt("account_id"), rs.getInt("pet_id"), rs.getString("type"), rs.getString("status"), rs.getString("date"));
            appointmentsObservableList.add(appointment);
        }
        return appointmentsObservableList;
    }

    public void listAppointments() throws SQLException {
        ObservableList<Appointment> list = getAppointmentList();
        clm_pet_id.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("pet_id"));
        clm_type.setCellValueFactory(new PropertyValueFactory<Appointment,String>("type"));
        clm_date.setCellValueFactory(new PropertyValueFactory<Appointment,String>("date"));
        clm_status.setCellValueFactory(new PropertyValueFactory<Appointment,String>("status"));
        tblview.setItems(list);

        // Allow the selection of multiple rows in the table
        //tblview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public ObservableList<Appointment> getPastAppointmentList() throws SQLException {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

        ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();
        String myQuery =
                "SELECT * FROM appointment WHERE (account_id = "+ MainController.id +" AND date < '"+ localDate +"') ORDER BY id ASC";

        ResultSet rs = DBUtil.sqlExecute(myQuery);
        Appointment appointment;

        while (rs.next()){
            appointment = new Appointment(rs.getInt("id"), rs.getInt("account_id"), rs.getInt("pet_id"), rs.getString("type"), rs.getString("status"), rs.getString("date"));
            appointmentsObservableList.add(appointment);
        }
        return appointmentsObservableList;
    }

    public void listPastAppointments() throws SQLException {
        ObservableList<Appointment> list = getPastAppointmentList();
        clm_past_pet_id.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("pet_id"));
        clm_past_type.setCellValueFactory(new PropertyValueFactory<Appointment,String>("type"));
        clm_past_date.setCellValueFactory(new PropertyValueFactory<Appointment,String>("date"));
        clm_past_status.setCellValueFactory(new PropertyValueFactory<Appointment,String>("status"));
        tblview2.setItems(list);

        // Allow the selection of multiple rows in the table
        tblview2.setSelectionModel(null);
    }

    public void deleteSelectedAppointment(ActionEvent event) {
        ObservableList<Appointment> selectedRow, allAppointments;
        allAppointments = tblview.getItems();

        // Get the rows that are selected
        selectedRow = tblview.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setContentText("This action is permanent!");
        alert.setTitle("Delete Appointment");
        alert.setHeaderText("Are you sure you want to delete?");

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Loop over selected rows and remove the objects
            for(Appointment appointment: selectedRow) {
                // Variables
                String SQL = "DELETE FROM appointment WHERE id = ?";
                int id = appointment.getId();

                try (Connection conn = DBUtil.connect();
                     PreparedStatement prepareStatement = conn.prepareStatement(SQL)){

                    // Define the PET id to the respective row
                    prepareStatement.setInt(1, id);

                    // Execute the delete statement
                    prepareStatement.executeUpdate();

                    // Remove row from the table view
                    Platform.runLater(() -> allAppointments.remove(appointment));

                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    @FXML
    private void goToAddAppointment(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/add-appointment-page.fxml"));
        middleRootPane.getChildren().setAll(pane);
    }

    @FXML
    private void cancelAppointment(ActionEvent event) throws IOException {
        ObservableList<Appointment> selectedRow, allAppointments;
        allAppointments = tblview.getItems();

        // Get the rows that are selected
        selectedRow = tblview.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setContentText("This action is permanent!");
        alert.setTitle("Cancel Appointment");
        alert.setHeaderText("Are you sure you want to cancel it?");

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Loop over selected rows and remove the objects
            for(Appointment appointment: selectedRow) {
                // Variables
                String SQL = "UPDATE appointment SET status = 'Canceled' WHERE id = ?";
                int id = appointment.getId();

                try (Connection conn = DBUtil.connect();
                     PreparedStatement prepareStatement = conn.prepareStatement(SQL)){

                    // Define the PET id to the respective row
                    prepareStatement.setInt(1, id);

                    // Execute the delete statement
                    prepareStatement.executeUpdate();

                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/marcoluz/safepet/appointments-page.fxml"));
        middleRootPane.getChildren().setAll(pane);
    }
}



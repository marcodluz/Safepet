package com.marcoluz.safepet.controller;

import com.marcoluz.safepet.model.Pet;
import com.marcoluz.safepet.util.DBUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyPetsController implements Initializable {
    @FXML
    private TableView<Pet> tblview;
    @FXML
    private TableColumn<Pet,String> clm_name;
    @FXML
    private TableColumn<Pet,String> clm_specie;
    @FXML
    private TableColumn<Pet,String> clm_coat_colour;
    @FXML
    private TableColumn<Pet,String> clm_dob;
    @FXML
    private TableColumn<Pet,String> clm_notes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listPets();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Pet> getPetList() throws SQLException {
        ObservableList<Pet> petObservableList = FXCollections.observableArrayList();
        String myQuery = "SELECT * FROM pet WHERE account_id =" + MainController.id;

        ResultSet rs= DBUtil.sqlExecute(myQuery);
        Pet pet;
        while (rs.next()){
            pet = new Pet(rs.getInt("id"),rs.getInt("account_id"),
                    rs.getString("name"),rs.getString("specie"), rs.getString("coat_colour"), rs.getString("dob"), rs.getString("notes"));
            petObservableList.add(pet);
        }
        return petObservableList;
    }

    public void listPets() throws SQLException {
        ObservableList<Pet> list = getPetList();
        clm_name.setCellValueFactory(new PropertyValueFactory<Pet,String>("name"));
        clm_specie.setCellValueFactory(new PropertyValueFactory<Pet,String>("specie"));
        clm_coat_colour.setCellValueFactory(new PropertyValueFactory<Pet,String>("coat_colour"));
        clm_dob.setCellValueFactory(new PropertyValueFactory<Pet,String>("dob"));
        clm_notes.setCellValueFactory(new PropertyValueFactory<Pet,String>("notes"));
        tblview.setItems(list);

        // Allow the selection of multiple rows in the table
        tblview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void deleteSelectedPet(ActionEvent event) {
        ObservableList<Pet> selectedRow, allPeople;
        allPeople = tblview.getItems();

        // Get the rows that are selected
        selectedRow = tblview.getSelectionModel().getSelectedItems();

        // Loop over selected rows and remove the objects
        for(Pet pet: selectedRow) {
            // Variables
            String SQL = "DELETE FROM pet WHERE id = ?";
            int id = pet.getId();

            try (Connection conn = DBUtil.connect();
                 PreparedStatement prepareStatement = conn.prepareStatement(SQL)){

                // Define the PET id to the respective row
                prepareStatement.setInt(1, id);

                // Execute the delete statement
                prepareStatement.executeUpdate();

                // Remove row from the table view
                Platform.runLater(() -> allPeople.remove(pet));

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}



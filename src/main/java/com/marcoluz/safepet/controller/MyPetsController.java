package com.marcoluz.safepet.controller;

import com.marcoluz.safepet.model.Pet;
import com.marcoluz.safepet.util.DBUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
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

public class MyPetsController implements Initializable {

  @FXML
  private TableView<Pet> tblview;

  @FXML
  private TableColumn<Pet, Integer> clm_id;

  @FXML
  private TableColumn<Pet, String> clm_name;

  @FXML
  private TableColumn<Pet, String> clm_specie;

  @FXML
  private TableColumn<Pet, String> clm_coat_colour;

  @FXML
  private TableColumn<Pet, String> clm_dob;

  @FXML
  private TableColumn<Pet, String> clm_notes;

  @FXML
  private AnchorPane middleRootPane;

  @FXML
  private Button btnDelPet;

  @FXML
  private Button btnEditPet;

  // Main Variables
  public static int selectedPetId;
  public static String selectedPetName;
  public static String selectedPetSpecie;
  public static String selectedPetCoatColour;
  public static LocalDate selectedPetDob;
  public static String selectedPetNotes;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      listPets(); // Show pets info in the table
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Disable the buttons until a row is selected
    btnEditPet
      .disableProperty()
      .bind(Bindings.isEmpty(tblview.getSelectionModel().getSelectedItems()));
    btnDelPet
      .disableProperty()
      .bind(Bindings.isEmpty(tblview.getSelectionModel().getSelectedItems()));
  }

  // Obtain the pets from the database
  public ObservableList<Pet> getPetList() throws SQLException {
    ObservableList<Pet> petObservableList = FXCollections.observableArrayList();
    String SQL =
      "SELECT * FROM pet WHERE account_id =" +
      MainController.id +
      " ORDER BY id ASC";
    ResultSet rs = DBUtil.sqlExecute(SQL);
    Pet pet;

    while (rs.next()) {
      pet =
        new Pet(
          rs.getInt("id"),
          rs.getInt("account_id"),
          rs.getString("name"),
          rs.getString("specie"),
          rs.getString("coat_colour"),
          rs.getString("dob"),
          rs.getString("notes")
        );
      petObservableList.add(pet);
    }
    return petObservableList;
  }

  // Show the pets in the table
  public void listPets() throws SQLException {
    ObservableList<Pet> list = getPetList();

    clm_id.setCellValueFactory(new PropertyValueFactory<Pet, Integer>("id"));
    clm_name.setCellValueFactory(new PropertyValueFactory<Pet, String>("name"));
    clm_specie.setCellValueFactory(
      new PropertyValueFactory<Pet, String>("specie")
    );
    clm_coat_colour.setCellValueFactory(
      new PropertyValueFactory<Pet, String>("coat_colour")
    );
    clm_dob.setCellValueFactory(new PropertyValueFactory<Pet, String>("dob"));
    clm_notes.setCellValueFactory(
      new PropertyValueFactory<Pet, String>("notes")
    );

    tblview.setItems(list);
    // Allow the selection of multiple rows in the table
    // DISABLED. But could be implemented if wanted to delete multiple rows at the same time
    //tblview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }

  // Delete the selected pet from the database and the table view
  public void deleteSelectedPet(ActionEvent event) {
    ObservableList<Pet> selectedRow, allPets;
    allPets = tblview.getItems();

    // Get the rows that are selected
    selectedRow = tblview.getSelectionModel().getSelectedItems();

    // Show an alert screen to double-check if the user wants to execute the action
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setContentText("This action is permanent!");
    alert.setTitle("Delete Pet");
    alert.setHeaderText("Are you sure you want to delete?");
    ButtonType cancelButtonType = new ButtonType(
      "Cancel",
      ButtonBar.ButtonData.CANCEL_CLOSE
    );
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      // Loop over selected rows and remove the objects
      for (Pet pet : selectedRow) {
        // Variables
        int id = pet.getId();

        try (Connection conn = DBUtil.connect()) {
          //Delete the selected pet from the DB table
          DBUtil.sqlExecute("DELETE FROM pet WHERE id =" + id);

          //Delete the appointments from the DB table of the selected pet
          DBUtil.sqlExecute("DELETE FROM appointment WHERE pet_id =" + id);

          //Delete the medications from the DB table of the selected pet
          DBUtil.sqlExecute("DELETE FROM medication WHERE pet_id =" + id);

          //Delete the weights from the DB table of the selected pet
          DBUtil.sqlExecute("DELETE FROM weight WHERE pet_id =" + id);

          // Remove row from the table view
          Platform.runLater(() -> allPets.remove(pet));
        } catch (SQLException ex) {
          System.err.println(ex.getMessage());
        }
      }
    }
  }

  @FXML
  private void goToAddPet(ActionEvent event) throws IOException {
    AnchorPane pane = FXMLLoader.load(
      getClass().getResource("/com/marcoluz/safepet/add-pet-page.fxml")
    );
    middleRootPane.getChildren().setAll(pane);
  }

  @FXML
  private void goToEditPet(ActionEvent event) throws IOException {
    ObservableList<Pet> selectedRow, allPets;
    allPets = tblview.getItems();

    // Get the rows that are selected
    selectedRow = tblview.getSelectionModel().getSelectedItems();

    for (Pet pet : selectedRow) {
      selectedPetId = pet.getId();
      selectedPetName = pet.getName();
      selectedPetSpecie = pet.getSpecie();
      selectedPetCoatColour = pet.getCoat_colour();
      selectedPetDob = LocalDate.parse(pet.getDob());
      selectedPetNotes = pet.getNotes();
    }

    AnchorPane pane = FXMLLoader.load(
      getClass().getResource("/com/marcoluz/safepet/edit-pet-page.fxml")
    );
    middleRootPane.getChildren().setAll(pane);
  }
}

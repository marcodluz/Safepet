package com.marcoluz.safepet.controller;

import com.marcoluz.safepet.DataValidation;
import com.marcoluz.safepet.dao.MedWeiDAO;
import com.marcoluz.safepet.model.Medication;
import com.marcoluz.safepet.model.Pet;
import com.marcoluz.safepet.model.Weight;
import com.marcoluz.safepet.util.DBUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class MedWeiController implements Initializable {

  @FXML
  private TableView tblMedication;

  @FXML
  private TableView tblWeight;

  @FXML
  private TextField medicationName;

  @FXML
  private TextField weightValue;

  @FXML
  private ComboBox medPet;

  @FXML
  private ComboBox weightPet;

  @FXML
  private Label medNameError;

  @FXML
  private Label medPetError;

  @FXML
  private Label weightError;

  @FXML
  private Label weightPetError;

  @FXML
  private AnchorPane middleRootPane;

  @FXML
  private TableColumn clm_med_pet_id;

  @FXML
  private TableColumn clm_medication;

  @FXML
  private TableColumn clm_med_date;

  @FXML
  private TableColumn clm_weight_pet_id;

  @FXML
  private TableColumn clm_weight;

  @FXML
  private TableColumn clm_weight_date;

  // Main Variables
  private String mydata[] = new String[1];
  final ObservableList<Pet> petOptions = FXCollections.observableArrayList();
  public static int petId1;
  public static int petId2;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      fillPetComboBox(); // Fill the combo boxes with the user pets
      listMedication(); // Show medication info in the table
      listWeight(); // Show weight info in the table
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Get the selected pet from the combo box of the medication
    medPet
      .valueProperty()
      .addListener(
        new ChangeListener<Pet>() {
          @Override
          public void changed(
            ObservableValue<? extends Pet> observableValue,
            Pet pet1,
            Pet pet2
          ) {
            petId1 = pet2.getId();
          }
        }
      );

    // Get the selected pet from the combo box of the weight
    weightPet
      .valueProperty()
      .addListener(
        new ChangeListener<Pet>() {
          @Override
          public void changed(
            ObservableValue<? extends Pet> observableValue,
            Pet pet1,
            Pet pet2
          ) {
            petId2 = pet2.getId();
          }
        }
      );
  }

  // Add a new entry to the medication table
  public void addMedication(ActionEvent event) throws IOException {
    boolean medicationNameNull = DataValidation.textFieldNull(
      medicationName,
      medNameError,
      "Insert a medication name"
    );
    boolean medicationPetNull = DataValidation.comboBoxNull(
      medPet,
      medPetError,
      "You need to select your pet"
    );

    if (!medicationNameNull && !medicationPetNull) {
      mydata[0] = this.medicationName.getText();

      String SQL =
        "INSERT INTO medication (id, account_id, pet_id, med_name, date) values (?, ?, ?, ?, ?);";
      MedWeiDAO.insertMedicationDetails(SQL, mydata);

      AnchorPane pane = FXMLLoader.load(
        getClass().getResource("/com/marcoluz/safepet/health-page.fxml")
      );
      middleRootPane.getChildren().setAll(pane);

      System.out.println("Medication successfully added!");
    } else {
      System.out.println("Medication not added!");
    }
  }

  // Add a new entry to the weight table
  public void addWeight(ActionEvent event) throws IOException {
    boolean weightNull = DataValidation.textFieldNull(
      weightValue,
      weightError,
      "Insert a valid weight"
    );
    boolean weightNumber = DataValidation.numberOnly(
      weightValue,
      weightError,
      "Insert a valid weight"
    );
    boolean weightPetNull = DataValidation.comboBoxNull(
      weightPet,
      weightPetError,
      "You need to select your pet"
    );

    if (!weightNull && !weightPetNull && weightNumber) {
      mydata[0] = this.weightValue.getText();

      String SQL =
        "INSERT INTO weight (id, account_id, pet_id, weight, date) values (?, ?, ?, ?, ?);";
      MedWeiDAO.insertWeightDetails(SQL, mydata);

      AnchorPane pane = FXMLLoader.load(
        getClass().getResource("/com/marcoluz/safepet/health-page.fxml")
      );
      middleRootPane.getChildren().setAll(pane);

      System.out.println("Weight successfully added!");
    } else {
      System.out.println("Weight not added!");
    }
  }

  // Fill the combo box with the pets
  public void fillPetComboBox() throws SQLException {
    String SQL =
      "SELECT * FROM pet WHERE account_id =" +
      MainController.id +
      " ORDER BY name ASC";
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
      petOptions.add(pet);
    }

    medPet.setItems(null);
    medPet.setItems(petOptions);

    weightPet.setItems(null);
    weightPet.setItems(petOptions);
  }

  // Obtain the medication from the database
  public ObservableList<Medication> getMedicationList() throws SQLException {
    ObservableList<Medication> medicationsObservableList = FXCollections.observableArrayList();
    String myQuery =
      "SELECT * FROM medication WHERE (account_id = " +
      MainController.id +
      ") ORDER BY id DESC";
    ResultSet rs = DBUtil.sqlExecute(myQuery);
    Medication medication;

    while (rs.next()) {
      medication =
        new Medication(
          rs.getInt("id"),
          rs.getInt("account_id"),
          rs.getInt("pet_id"),
          rs.getString("med_name"),
          rs.getString("date")
        );
      medicationsObservableList.add(medication);
    }
    return medicationsObservableList;
  }

  // Show the medications in the table
  public void listMedication() throws SQLException {
    ObservableList<Medication> list = getMedicationList();

    clm_med_pet_id.setCellValueFactory(
      new PropertyValueFactory<Medication, Integer>("pet_id")
    );
    clm_medication.setCellValueFactory(
      new PropertyValueFactory<Medication, String>("med_name")
    );
    clm_med_date.setCellValueFactory(
      new PropertyValueFactory<Medication, String>("date")
    );

    // Insert the values into the table
    tblMedication.setItems(list);
  }

  // Obtain the weight from the database
  public ObservableList<Weight> getWeightList() throws SQLException {
    ObservableList<Weight> weightsObservableList = FXCollections.observableArrayList();
    String myQuery =
      "SELECT * FROM weight WHERE (account_id = " +
      MainController.id +
      ") ORDER BY id DESC";
    ResultSet rs = DBUtil.sqlExecute(myQuery);
    Weight weight;

    while (rs.next()) {
      weight =
        new Weight(
          rs.getInt("id"),
          rs.getInt("account_id"),
          rs.getInt("pet_id"),
          rs.getString("weight"),
          rs.getString("date")
        );
      weightsObservableList.add(weight);
    }
    return weightsObservableList;
  }

  // Show the weights in the table
  public void listWeight() throws SQLException {
    ObservableList<Weight> list = getWeightList();

    clm_weight_pet_id.setCellValueFactory(
      new PropertyValueFactory<Weight, Integer>("pet_id")
    );
    clm_weight.setCellValueFactory(
      new PropertyValueFactory<Weight, String>("weight")
    );
    clm_weight_date.setCellValueFactory(
      new PropertyValueFactory<Weight, String>("date")
    );

    // Insert the values into the table
    tblWeight.setItems(list);
  }
}

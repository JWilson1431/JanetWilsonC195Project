package controller;

import DAO.DBConnection;
import DAO.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    Stage stage;
    Parent scene;

    //public static ObservableList<String> allCountries = FXCollections.observableArrayList();

    //buttons
    @FXML
    private Button savebtn;
    @FXML
    private Button cancelbtn;

    //text fields
    @FXML
    private TextField customeridtxt;
    @FXML
    private TextField customernametxt;
    @FXML
    private TextField phonetxt;
    @FXML
    private TextField postaltxt;
    @FXML
    private TextField addresstxt;

    //combo boxes
    @FXML
    private ComboBox<Country> countrycombo;
    @FXML
    private ComboBox<FirstLevelDivision> firstLevelCombo;

    //ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();

    //when the user clicks save, the new customer is added to the database and the table
    @FXML
    void clickSavebtn(ActionEvent event) throws SQLException, IOException {
        String customerName = customernametxt.getText();
        String address = addresstxt.getText();
        String postalCode = postaltxt.getText();
        String phoneNum = phonetxt.getText();
        int divisionId = firstLevelCombo.getValue().getDivisionId();

        int rowsAffected = Helper.addCustomer(customerName, address, postalCode, phoneNum, divisionId);

        if(rowsAffected > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Added");
            alert.setContentText("A customer was successfully added");
            alert.showAndWait();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was a problem with adding the customer, please try again");
            alert.showAndWait();
        }

    }

    //takes the user to the main screen if they click the cancel button
    @FXML
    void clickcancelbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    //sets the list of first level divisions once a country is chosen
    @FXML
    void chooseCountry(ActionEvent event) throws SQLException {
        firstLevelCombo.getItems().clear();
      int countryId= countrycombo.getSelectionModel().getSelectedItem().getCountryId();
      Helper.getDivision(countryId);
      firstLevelCombo.setItems(Helper.getDivision(countryId));
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            countrycombo.setItems(Helper.getAllCountries());

        }
    catch(SQLException e){
            System.out.println("error");
    }
    }
}



package controller;

import DAO.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.FirstLevelDivision;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateCustomerController {
    Stage stage;
    Parent scene;


    @FXML
    private TextField addresstxt;

    @FXML
    private Button cancelbtn;

    @FXML
    private ComboBox<Country> countrycombo;

    @FXML
    private TextField custnametxt;

    @FXML
    private TextField customeridtxt;

    @FXML
    private ComboBox<FirstLevelDivision> firstlevelcombo;

    @FXML
    private TextField phonetxt;

    @FXML
    private TextField postaltxt;

    @FXML
    private Button savebtn;

    ObservableList<Country> allCountries = FXCollections.observableArrayList();

    @FXML
    void ClickCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

        }

    @FXML
    void clickSave(ActionEvent event) throws SQLException, IOException {
        String customerName = custnametxt.getText();
        String address = addresstxt.getText();
        String postalCode = postaltxt.getText();
        String phoneNum = phonetxt.getText();
        int customerId = Integer.parseInt(customeridtxt.getText());
        int divId = firstlevelcombo.getValue().getDivisionId();

        int rowsAffected = Helper.update(customerName,address,postalCode,phoneNum,divId,customerId);

        if(rowsAffected > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Updated");
            alert.setContentText("The customer was successfully updated");
            alert.showAndWait();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was a problem with updating the customer, please try again");
            alert.showAndWait();
        }

    }



    public void sendCustomer(Customer customer1) {
        customeridtxt.setText(String.valueOf(customer1.getCustomerId()));
        custnametxt.setText(customer1.getCustomerName());
        addresstxt.setText(String.valueOf(customer1.getAddress()));
        postaltxt.setText(String.valueOf(customer1.getPostalCode()));
        phonetxt.setText(String.valueOf(customer1.getPhoneNumber()));
        //for(FirstLevelDivision division:)


    }

    public ObservableList<Country> getAllCountries() {
        return allCountries;
    }
}
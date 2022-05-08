package controller;

import DAO.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.AlertInterface;
import model.Country;
import model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This class is the controller for the update customer page. This page is populated with the selected customer's information and the user can change the fields as needed*/
public class UpdateCustomerController implements Initializable {
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

    /**This is the cancel button. Upon clicking, the user is taken back to the main screen.
     * @param event */
    @FXML
   public void ClickCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

        }
   /**This is the save button. Upon clicking the customer is saved if it is a valid customer.
    * @param event */
    @FXML
    public void clickSave(ActionEvent event) throws SQLException, IOException {
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


    /**This is the send customer method. This method populates the update customer page with the information of the selected customer.
     * @param customer1*/
    public void sendCustomer(Customer customer1) throws SQLException {
        customeridtxt.setText(String.valueOf(customer1.getCustomerId()));
        custnametxt.setText(customer1.getCustomerName());
        addresstxt.setText(String.valueOf(customer1.getAddress()));
        postaltxt.setText(String.valueOf(customer1.getPostalCode()));
        phonetxt.setText(String.valueOf(customer1.getPhoneNumber()));
        countrycombo.setItems(Helper.getAllCountries());
        FirstLevelDivision division = Helper.getDivisionById(customer1.getDivisionId());
        firstlevelcombo.setItems(Helper.getDivision(division.getCountryId()));
        firstlevelcombo.setValue(division);
        countrycombo.setValue(Helper.getCountryById(division.getCountryId()));


    }

    /**This is the choose country combo box. This populates the first level division box with appropriate choices when the country is chosen.
     * @param event */
    @FXML
    public void chooseCountry(ActionEvent event) throws SQLException {
        firstlevelcombo.getItems().clear();
        int countryId= countrycombo.getSelectionModel().getSelectedItem().getCountryId();
        Helper.getDivision(countryId);
        firstlevelcombo.setItems(Helper.getDivision(countryId));
    }

/**This is the initialize method. It initializes the country combo with all of the country choices when the page is loaded.
 * @param rb
 * @param url */
@Override
public void initialize(URL url, ResourceBundle rb) {
    try{
        countrycombo.setItems(Helper.getAllCountries());
        customeridtxt.setDisable(true);

    }
    catch(SQLException e){
        System.out.println("error");
    }
}
}
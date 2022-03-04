package controller;

import model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class UpdateCustomerController {


    @FXML
    private TextField addresstxt;

    @FXML
    private Button cancelbtn;

    @FXML
    private ComboBox<?> countrycombo;

    @FXML
    private TextField custnametxt;

    @FXML
    private TextField customeridtxt;

    @FXML
    private ComboBox<?> firstlevelcombo;

    @FXML
    private TextField phonetxt;

    @FXML
    private TextField postaltxt;

    @FXML
    private Button savebtn;


    public void sendCustomer(Customer customer1) {
        customeridtxt.setText(String.valueOf(customer1.getCustomerId()));
        custnametxt.setText(customer1.getCustomerName());
        addresstxt.setText(String.valueOf(customer1.getAddress()));
        postaltxt.setText(String.valueOf(customer1.getPostalCode()));
        phonetxt.setText(String.valueOf(customer1.getPhoneNumber()));


    }
}
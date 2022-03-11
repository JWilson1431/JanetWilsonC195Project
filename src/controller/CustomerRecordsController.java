package controller;

import DAO.DBConnection;
import DAO.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AlertInterface;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerRecordsController implements Initializable {
    Stage stage;
    Parent scene;

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    //lambda for alerts if a customer is not selected
    AlertInterface alert1 = s -> "A " + s + " was not chosen, please select a " + s + " and try again";

    //buttons
    @FXML
    private Button addcustomerbtn;
    @FXML
    private Button cancelbtn;
    @FXML
    private Button updatecustomerbtn;


    //customer table view
    @FXML
    private TableView<Customer> customertableview;

    //customer table columns
    @FXML
    private TableColumn<Customer, String> addresscol;
    @FXML
    private TableColumn<Customer, Integer> customeridcol;
    @FXML
    private TableColumn<Customer, String> namecol;
    @FXML
    private TableColumn<Customer, String> phonenumcol;
    @FXML
    private TableColumn<Customer, String> postalcol;
    @FXML
    private TableColumn<Customer, String> custcountrycol;
    @FXML
    private TableColumn<Customer, Integer> firstleveldivcol;

    //method to populate the customer table with a list of customers
    public void setAllCustomers(ObservableList<Customer> listOfCustomers){
        customeridcol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalcol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phonenumcol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        firstleveldivcol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        customertableview.setItems(listOfCustomers);
    }

    //takes the user to the add customer page if they click the add customer button
    @FXML
    void clickaddcustomerbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //takes the user back to the main screen if they click the cancel button
    @FXML
    void clickcancelbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //takes user to the update customer page if they click the update customer button
    @FXML
    void clickupdatecustbtn(ActionEvent event) throws IOException, SQLException {

        if(customertableview.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer not selected");
            alert.setContentText(alert1.getAlert("customer"));
            alert.showAndWait();

        }
        else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateCustomer.fxml"));
            loader.load();
            UpdateCustomerController UCC = loader.getController();
            UCC.sendCustomer(customertableview.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    //deletes a selected customer upon confirming the user wants to delete it
    @FXML
    void clickDeleteBtn(ActionEvent event) throws SQLException, IOException {
        //checks to see if a customer is selected
        Customer customerToDelete = customertableview.getSelectionModel().getSelectedItem();
        int customerToDeleteId = customerToDelete.getCustomerId();
        if(!Helper.filterByCustomerId(customerToDeleteId).isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot delete customer");
            alert.setContentText("This customer currently has appointments and cannot be deleted");
            alert.showAndWait();
        }

        else if(customertableview.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer not selected");
            alert.setContentText(alert1.getAlert("customer"));
            alert.showAndWait();
        }

        else{
            //confirms the user wants to delete the customer
            Customer customerToDelete1 = customertableview.getSelectionModel().getSelectedItem();
            int customerToDeleteId1 = customerToDelete.getCustomerId();
            int rowsAffected = Helper.deleteCustomer(customerToDeleteId1);
            if(rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete this customer, do you want to proceed?");
                    Optional<ButtonType> result = alert.showAndWait();

                 //upon confirmation that the user wants to delete the customer, deletes them
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    allCustomers.remove(customerToDelete1);
                        Alert alert2 = new Alert(Alert.AlertType.WARNING);
                        alert2.setTitle("Customer deleted");
                        alert2.setContentText("Customer was successfully deleted");
                        alert2.showAndWait();
                    }

                //refreshes the page once customer is deleted
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/customerRecords.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else{
                System.out.println("Delete Failed");
            }
        }

    }





    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setAllCustomers(Helper.getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
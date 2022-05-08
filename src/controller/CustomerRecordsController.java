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

/**This class is the controller for the customer records page. This page contains a tableview of all customer and gives the user options to go to an add or update customer page.*/
public class CustomerRecordsController implements Initializable {
    Stage stage;
    Parent scene;

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**This is a lambda which helps the code be more efficient. It provides text for an alert that can be used throughout the page. This is useful because it decreases the amount of redundant code on the page.*/
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

    /**This is the set all customers method. This method creates an observable list of all customers and populates the customer table view with it.
     * @param listOfCustomers*/
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

    /**This is the add customer button. When this button is clicked, the user is taken to the add customer page.
     * @param event */
    //takes the user to the add customer page if they click the add customer button
    @FXML
    public void clickaddcustomerbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the cancel button. When cancel is clicked, the user is taken back to the main screen
     * @param event */
    //takes the user back to the main screen if they click the cancel button
    @FXML
    public void clickcancelbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the update customer button. When this button is clicked, the user is taken to the update customer button if a customer is selected.
     * @param event */
    //takes user to the update customer page if they click the update customer button
    @FXML
    public void clickupdatecustbtn(ActionEvent event) throws IOException, SQLException {

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

    /**This is the delete customer button. Upon confirmation that the user wants to delete the customer, the customer is deleted.
     * @param event*/
    //deletes a selected customer upon confirming the user wants to delete it
    @FXML
    public void clickDeleteBtn(ActionEvent event) throws SQLException, IOException {
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




    /**This is the initialize method. When the page is loaded all customers is initialized to the set all customers method.
     * @param url
     * @param rb */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setAllCustomers(Helper.getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
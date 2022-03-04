package controller;

import DAO.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerRecordsController implements Initializable {
    Stage stage;
    Parent scene;

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    @FXML
    private Button addcustomerbtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private Button updatecustomerbtn;

    @FXML
    private TableColumn<Customer, String> addresscol;

    @FXML
    private TableColumn<Customer, Integer> customeridcol;

    @FXML
    private TableView<Customer> customertableview;

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


    @FXML
    void clickaddcustomerbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void clickcancelbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void clickupdatecustbtn(ActionEvent event) throws IOException {
        if(customertableview.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer not selected");
            alert.setContentText("A customer was not selected, please select a customer and try again");
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




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //use database to populate customer data in table
        try {
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division_ID, countries.Country FROM customers, first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";


            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt(1);
                String customerName = rs.getString(2);
                String address = rs.getString(3);
                String postalCode = rs.getString(4);
                String phone = rs.getString(5);
                int divisionId = rs.getInt(7);
                String country = rs.getString(8);

                allCustomers.add(new Customer(customerID, customerName, address, postalCode, phone, divisionId, country));

            }

            customeridcol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            namecol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalcol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phonenumcol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            firstleveldivcol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
            custcountrycol.setCellValueFactory(new PropertyValueFactory<>("country"));


            customertableview.setItems(allCustomers);
        }
        catch(SQLException e){
            System.out.println("wtf");
        }
    }
}
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
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    //customer table view
    @FXML
    private TableView<Customer> customertableview;

    //customer table columns
    @FXML
    private TableColumn<Customer, String> customeraddresscol;
    @FXML
    private TableColumn<Customer, String> customercountrycol;
    @FXML
    private TableColumn<Customer, Integer> customeridcolumn;
    @FXML
    private TableColumn<Customer, String> customernamecolumn;
    @FXML
    private TableColumn<Customer, String> customerphonecol;
    @FXML
    private TableColumn<Customer, String> customerpostalcol;
    @FXML
    private TableColumn<Customer, Integer> firstLevelDivisionCol;

    //buttons
    @FXML
    private Button exitbtn;
    @FXML
    private Button addupdatecustomerbtn;
    @FXML
    private Button addUpdateApptBtn;


    @FXML
    private TableView<?> scheduletableview;

    //takes the user to the customer records page where they can chooose to add, update, or delete a customer
    @FXML
    void clickaddupdatecustbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customerRecords.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //populates the customer table view with a list of customers
    public void setAllCustomers(ObservableList<Customer> listOfCustomers){
        customeridcolumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customernamecolumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customeraddresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerpostalcol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerphonecol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        firstLevelDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        customertableview.setItems(listOfCustomers);
    }

    //takes the user to the main schedule page when the button is clicked

    @FXML
    void clickAddUpdateAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //exits the application when the exit button is clicked
    @FXML
    void onClickExit(ActionEvent event) {
        //confirms that the user wants to exit the application
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the application?");
        Optional<ButtonType> result = alert.showAndWait();

        //upon confirmation, exits the system
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
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


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
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class is the controller for the main screen. This screen is the first screen to be displayed upon signing in. The user can go to the main scheduling page, the main customer records page, or the reports page from here.*/
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

    //schedule tableview
    @FXML
    private TableView<Appointment> scheduletableview;

    //schedule table columns
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;
    @FXML
    private TableColumn<Appointment, Integer> contactCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, Date> endCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, Integer> scheduleCustIdCol;
    @FXML
    private TableColumn<Appointment, Date> startCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;


    //buttons
    @FXML
    private Button exitbtn;
    @FXML
    private Button addupdatecustomerbtn;
    @FXML
    private Button addUpdateApptBtn;
    @FXML
    private Button reportsBtn;



    /**This is the update customer button. Upon clicking, the user is taken to the customer records page.
     * @param event */
    //takes the user to the customer records page where they can chooose to add, update, or delete a customer
    @FXML
    public void clickaddupdatecustbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customerRecords.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**This is the set all customers method. This method takes in a list of customers and populates the customer table with the list.
     * @param listOfCustomers */
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

    /**This is the set all appointments method. This method takes in a list of appointments and populates the schedule table with it.
     * @param listOfAppointments */
    //populates the schedule table view with a list of all appointments
    public void setAllAppointments(ObservableList<Appointment> listOfAppointments){
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        scheduleCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        scheduletableview.setItems(listOfAppointments);


    }

    /**This is the add or update appointment button. Upon clicking this button the user is taken to the main scheduling page.
     * @param event */
    //takes the user to the main schedule page when the button is clicked
    @FXML
    public void clickAddUpdateAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the reports button. Upon clicking this button the user is taken to the reports page.
     * @param event*/
    //takes the user to the reports page when reports button is clicked
    @FXML
    public void clickReportsBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reportsPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /**This is the exit button. Upon clicking and confirming the application is exited.
     * @param event*/
    //exits the application when the exit button is clicked
    @FXML
    public void onClickExit(ActionEvent event) {
        //confirms that the user wants to exit the application
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the application?");
        Optional<ButtonType> result = alert.showAndWait();

        //upon confirmation, exits the system
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**This is the initialize method. It loads the set all customers method and the set all appointments method.
     * @param rb
     * @param url */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            setAllCustomers(Helper.getAllCustomers());
            setAllAppointments(Helper.getAllAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    }


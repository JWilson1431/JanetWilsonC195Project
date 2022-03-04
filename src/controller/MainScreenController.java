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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    @FXML
    private Button addupdatecustomerbtn;


    @FXML
    private TableColumn<Customer, Integer> customeridcolumn;

    @FXML
    private TableColumn<Customer, Integer> customernamecolumn;

    @FXML
    private TableView<Customer> customertableview;

    @FXML
    private Button exitbtn;

    @FXML
    private TableView<Appointment> scheduletableview;

    @FXML
    private TableColumn<Customer, String> customeraddresscol;

    @FXML
    private TableColumn<Customer, Integer> customerdividcol;

    @FXML
    private TableColumn<Customer, String> customerphonecol;

    @FXML
    private TableColumn<Customer, String> customerpostalcol;

    @FXML
    void clickaddupdatecustbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customerRecords.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }



    @FXML
    void onClickExit(ActionEvent event) {
        System.exit(0);

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
     //use database to populate customer data in table
try {
    String sql = "SELECT * FROM customers";

    PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
        int customerID = rs.getInt(1);
        String customerName = rs.getString(2);
        String address = rs.getString(3);
        String postalCode = rs.getString(4);
        String phone = rs.getString(5);
        int divisionId = rs.getInt(10);

        allCustomers.add(new Customer(customerID, customerName, address, postalCode, phone, divisionId));

    }

    customeridcolumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    customernamecolumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    customeraddresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
    customerpostalcol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
    customerphonecol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    customerdividcol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));


    customertableview.setItems(allCustomers);
}
catch(SQLException e){
    System.out.println("wtf");
}
        }
    }


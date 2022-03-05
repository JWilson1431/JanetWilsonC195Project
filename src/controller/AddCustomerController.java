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


    @FXML
    private Button cancelbtn;

    @FXML
    private TextField customeridtxt;

    @FXML
    private TextField customernametxt;

    @FXML
    private TextField phonetxt;

    @FXML
    private TextField postaltxt;

    @FXML
    private Button savebtn;


    @FXML
    private TextField addresstxt;

    @FXML
    private ComboBox<Country> countrycombo;

    @FXML
    private ComboBox<FirstLevelDivision> firstLevelCombo;

    ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();

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

    @FXML
    void clickcancelbtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    public  ObservableList<Country> getAllCountries() throws SQLException {
         ObservableList<Country> countries = FXCollections.observableArrayList();

        String sql = "SELECT Country, Country_ID FROM countries";


        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String countryName = rs.getString(1);
            int countryId = rs.getInt(2);
            Country country1 = new Country(countryId, countryName);
            countries.add(country1);
        }

        return countries;
    }
    public ObservableList<FirstLevelDivision> getDivision(int countryID) throws SQLException {


        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String division = rs.getString(2);
            int divisionId = rs.getInt(1);
            int countryId = rs.getInt(7);
            FirstLevelDivision division1 = new FirstLevelDivision(divisionId,division,countryID);
            divisions.add(division1);
        }
        return divisions;
    }




    @FXML
    void chooseCountry(ActionEvent event) throws SQLException {
        firstLevelCombo.getItems().clear();
      int countryId= countrycombo.getSelectionModel().getSelectedItem().getCountryId();
      getDivision(countryId);
      firstLevelCombo.setItems(getDivision(countryId));
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            countrycombo.setItems(getAllCountries());

        }
    catch(SQLException e){
            System.out.println("error");
    }
    }
}



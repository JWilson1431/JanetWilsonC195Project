package controller;

import DAO.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ReportsPageController implements Initializable {
    Stage stage;
    Parent scene;

    //combo box for types
    @FXML
    private ComboBox<String> typeCombo;

    //table columns for table filtered by contact
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;
    @FXML
    private TableColumn<Appointment, Integer> custIdcol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, LocalTime> endCol;
    @FXML
    private TableColumn<Appointment, LocalTime> startCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;

    //table columns for table filtered by user ID
    @FXML
    private TableColumn<Appointment, Integer> apptIdUserCol;
    @FXML
    private TableColumn<Appointment, Integer> custIdUserCol;
    @FXML
    private TableColumn<Appointment, String> descriptionUserCol;
    @FXML
    private TableColumn<Appointment, LocalTime> endUserCol;
    @FXML
    private TableColumn<Appointment, LocalTime> startUserCol;
    @FXML
    private TableColumn<Appointment, String> titleUserCol;
    @FXML
    private TableColumn<Appointment, String> typeUserCol;

    //buttons
    @FXML
    private Button backToMainBtn;
    @FXML
    private Button searchContactBtn;
    @FXML
    private Button searchTypeBtn;
    @FXML
    private Button searchUserBtn;

    //labels
    @FXML
    private Label typeLbl;

    //table view for table filtered by contacts
    @FXML
    private TableView<Appointment> contactTableview;


    //text fields
    @FXML
    private TextField searchContactTxt;
    @FXML
    private Button monthSearchBtn;
    @FXML
    private TextField searchUserTxt;

    //table view for table filtered by users
    @FXML
    private TableView<Appointment> userTableview;

    //takes the user back to the main screen when button is clicked
    @FXML
    void clickBackToMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    //When the search button is clicked, the application searches for all appointments with the entered contactID
    @FXML
    void clickSearchContact(ActionEvent event) throws SQLException {
        ObservableList<Appointment> apptByContact = FXCollections.observableArrayList();
        int contactId = Integer.parseInt(searchContactTxt.getText());

        apptByContact = Helper.filterByContactId(contactId);
        setAllContactAppts(apptByContact);
        contactTableview.setItems(apptByContact);

        if(contactId > Helper.getAllContacts().size()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("This contact doesn't exist");
            alert.setContentText("This is not a correct contact ID. Please try a valid contact ID");
            alert.showAndWait();

        }

        else if(apptByContact.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No matching appointments");
            alert.setContentText("This contact does not have any appointments");
            alert.showAndWait();
        }
        }



    //When the search button is clicked, the application searches for all appointments with a matching user ID
    @FXML
    void clickSearchUser(ActionEvent event) throws SQLException {
        ObservableList<Appointment> apptByUser = FXCollections.observableArrayList();
        int userId = Integer.parseInt(searchUserTxt.getText());

        apptByUser = Helper.filterByUserId(userId);
        setAllUserAppts(apptByUser);
        userTableview.setItems(apptByUser);

        if(userId > Helper.getAllUsers().size()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("This user doesn't exist");
            alert.setContentText("This is not a correct user ID. Please try a valid user ID");
            alert.showAndWait();
        }
        else if(apptByUser.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No matching appointments");
            alert.setContentText("This user does not have any appointments");
            alert.showAndWait();
        }

    }
    //Populate the table that filters by contactID
    public void setAllContactAppts(ObservableList<Appointment> allContactAppts) {

        //populate the table that contains appointments based on contact
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdcol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        contactTableview.setItems(allContactAppts);

    }

    //populate the table that filters by userID
    public void setAllUserAppts(ObservableList<Appointment> allUserAppts) {
        //populate the table that contains appointments based on user
        apptIdUserCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleUserCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionUserCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeUserCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startUserCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endUserCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdUserCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        userTableview.setItems(allUserAppts);

    }

    @FXML
    void clickMonthSearch(ActionEvent event) {

    }

    public void clickSearchTypeBtn(ActionEvent actionEvent) throws SQLException {
      String chosenType = typeCombo.getSelectionModel().getSelectedItem();

      ObservableList<Appointment> getTypes = FXCollections.observableArrayList();
      getTypes = Helper.getAllApptsWithType(chosenType);

      typeLbl.setText(String.valueOf(getTypes.size()));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setAllContactAppts(Helper.getAllAppointments());
            setAllUserAppts(Helper.getAllAppointments());
            typeCombo.setItems(Helper.getAllTypes());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
package controller;

import DAO.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

/**This class is the update appointment controller. It is populated with the information from the selected appointment and details of the appointment can be changed.*/
public class UpdateAppointmentController implements Initializable {
    Stage stage;
    Parent scene;

    //combo boxes for type and contact
    @FXML
    private ComboBox<String> TypeCombo;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private ComboBox<LocalTime> startTimeCombo;
    @FXML
    private ComboBox<Customer> custIdCombo;
    @FXML
    private ComboBox<User> userIdCombo;

    //text fields for input
    @FXML
    private TextField apptIdTxt;
    @FXML
    private TextField custIdTxt;
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField userIdTxt;
    @FXML
    private TextField descriptionTxt;
    @FXML
    private TextField locationTxt;

    //buttons
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    //datepicker
    @FXML
    private DatePicker datePicker;


    //takes user back to the main schedule page when cancel is clicked
    @FXML
    void clickCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    void clickSave(ActionEvent event) throws SQLException, IOException {
        String title =titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = TypeCombo.getValue();
        LocalDate date = datePicker.getValue();
        LocalTime start = startTimeCombo.getValue();
        LocalTime end = endTimeCombo.getValue();
        int customerId = custIdCombo.getValue().getCustomerId();
        int userId = userIdCombo.getValue().getUserId();
        int contactId = contactCombo.getValue().getContactId();
        int apptId = Integer.parseInt(apptIdTxt.getText());



        LocalDateTime start1 = LocalDateTime.of(date,start);
        LocalDateTime end1 = LocalDateTime.of(date,end);

        //convert start and end time to zoneddatetime
        ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(start1, Helper.getCurrentUserZoneId());
        ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(end1, Helper.getCurrentUserZoneId());

        //convert zoneddate time start and end to UTC to put it in the database
        zonedDateTimeStart = zonedDateTimeStart.withZoneSameInstant(ZoneOffset.UTC);
        zonedDateTimeEnd = zonedDateTimeEnd.withZoneSameInstant(ZoneOffset.UTC);
        int rowsAffected = Helper.updateAppt(title,description,location,type,zonedDateTimeStart, zonedDateTimeEnd, customerId, userId,contactId,apptId);

        if(rowsAffected >0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment updated");
            alert.setContentText("The appointment was successfully updated");
            alert.showAndWait();

            stage = (Stage)((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was a problem with updating the appointment, please try again");
            alert.showAndWait();
        }
    }

    public void sendAppointment(Appointment appt1) throws SQLException {
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(8, 30);

        while (start.isBefore(LocalTime.of(22, 0))) {
            startTimeCombo.getItems().add(start);
            start = start.plusMinutes(30);
        }
        while (end.isBefore(LocalTime.of(22, 30))) {
            endTimeCombo.getItems().add(end);
            end = end.plusMinutes(30);
        }

        apptIdTxt.setText(String.valueOf(appt1.getAppointmentId()));
        titleTxt.setText(appt1.getTitle());
        descriptionTxt.setText(appt1.getDescription());
        locationTxt.setText(appt1.getLocation());
        TypeCombo.setItems(Helper.getAllTypes());
        TypeCombo.getSelectionModel().select(appt1.getType());
        custIdCombo.setItems(Helper.getAllCustomers());
        //custIdCombo.getSelectionModel().select(appt1.getCustomerName());
        userIdCombo.setItems(Helper.getAllUsers());
        userIdCombo.getSelectionModel().select(appt1.getUserId());
        contactCombo.setItems(Helper.getAllContacts());
        contactCombo.getSelectionModel().select(appt1.getContactId());

       // startTimeCombo.getSelectionModel().select(appt1.getStart());

    }
@Override
    public void initialize(URL url, ResourceBundle rb){

}
}


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
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    Stage stage;
    Parent scene;

    //buttons
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;

    //combo boxes
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private ComboBox<LocalTime> startTimeCombo;
    @FXML
    private ComboBox<Customer> customerIdCombo;
    @FXML
    private ComboBox<User> userIdCombo;

    //text fields
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField descriptionTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private TextField apptIdTxt;

    //datepicker for the date
    @FXML
    private DatePicker startDatePicker;




    //if the user clicks cancel, they are taken back to the main schedule page
    @FXML
    void clickCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void clickSave(ActionEvent event) throws SQLException, IOException {
        //int apptId = Integer.parseInt(apptIdTxt.getText());
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        int contactId = contactCombo.getValue().getContactId();
        String type = typeCombo.getValue();
        LocalDate date = startDatePicker.getValue();
        LocalTime start = startTimeCombo.getValue();
        LocalTime end = endTimeCombo.getValue();
        int customerId = customerIdCombo.getValue().getCustomerId();
        int userId = userIdCombo.getValue().getUserId();



        LocalDateTime start1 = LocalDateTime.of(date,start);
        LocalDateTime end1 = LocalDateTime.of(date,end);

        //convert start and end time to zoneddatetime
        ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(start1, Helper.getCurrentUserZoneId());
        ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(end1, Helper.getCurrentUserZoneId());

        //convert zoneddate time start and end to UTC to put it in the database
        zonedDateTimeStart = zonedDateTimeStart.withZoneSameInstant(ZoneOffset.UTC);
        zonedDateTimeEnd = zonedDateTimeEnd.withZoneSameInstant(ZoneOffset.UTC);
        int rowsAffected = Helper.addAppointment(title,description,location,type,zonedDateTimeStart, zonedDateTimeEnd, customerId, userId,contactId);

        if(start == end){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Appointment start and end time cannot be the same. Please select a different time");
            alert.showAndWait();
        }

        else if (start.isAfter(end)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Start time cannot come after end time. Please select a different time");
            alert.showAndWait();
        }

        else if(rowsAffected >0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment added");
            alert.setContentText("The appointment was successfully added");
            alert.showAndWait();

           stage = (Stage)((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There was a problem with adding the appointment, please try again");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){
        try{
            contactCombo.setItems(Helper.getAllContacts());
            typeCombo.setItems(Helper.getAllTypes());
            customerIdCombo.setItems(Helper.getAllCustomers());
            userIdCombo.setItems(Helper.getAllUsers());

            //populate times in combo boxes for start and end times
            LocalTime start = LocalTime.of(8,0);
            LocalTime end = LocalTime.of(8,30);

            while(start.isBefore(LocalTime.of(22,0))){
                startTimeCombo.getItems().add(start);
                start = start.plusMinutes(30);
            }
            while(end.isBefore(LocalTime.of(22,30))){
                endTimeCombo.getItems().add(end);
                end = end.plusMinutes(30);
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
    }
}

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
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    /**This is the cancel button. Upon clicking it the user is taken back to the main scheduling page.
     * @param event */
    //takes user back to the main schedule page when cancel is clicked
    @FXML
    public void clickCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the save button. Upon clicking it, the appointment is verified to be valid and is saved if it is valid.
     * @param event */
    @FXML
    public void clickSave(ActionEvent event) throws SQLException, IOException {

        ObservableList<Appointment> apptByCustomer = FXCollections.observableArrayList();

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = TypeCombo.getValue();
        LocalDate date = datePicker.getValue();
        LocalTime start = startTimeCombo.getValue();
        LocalTime end = endTimeCombo.getValue();
        int customerId = custIdCombo.getValue().getCustomerId();
        String userName = userIdCombo.getValue().getUserName();
        int contactId = contactCombo.getValue().getContactId();
        int apptId = Integer.parseInt(apptIdTxt.getText());

        apptByCustomer = Helper.filterByCustomerId(customerId);


        LocalDateTime start1 = LocalDateTime.of(date, start);
        LocalDateTime end1 = LocalDateTime.of(date, end);

        if (titleTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty() || locationTxt.getText().isEmpty() || TypeCombo.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty field");
            alert.setContentText("Fields cannot be empty, please enter a value");
            alert.showAndWait();
        }

        boolean checkOverlap = false;
        for (Appointment appointment : apptByCustomer) {

            if (appointment.getAppointmentId() != apptId && appointment.getCustomerId() == customerId) {
                System.out.println("reached here");
                System.out.println(apptByCustomer);


                //LocalDateTime of appointment in the system
                Timestamp tsStart = appointment.getStart();
                LocalDateTime start2 = tsStart.toLocalDateTime();
                Timestamp tsEnd = appointment.getEnd();
                LocalDateTime end2 = tsEnd.toLocalDateTime();


                if ((start1.isAfter(start2) || start1.isEqual(start2)) && start1.isBefore(end2)) {
                    System.out.println("number 1 issue");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlap Error");
                    alert.setContentText("This appointment overlaps another appointment for this customer. Please choose a new time");
                    alert.showAndWait();
                    checkOverlap = true;
                    break;
                }
                if (end1.isAfter(start2) && end1.isBefore(end2)) {
                    System.out.println("number 2 issue");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlap Error");
                    alert.setContentText("This appointment overlaps another appointment for this customer. Please choose a new time");
                    alert.showAndWait();
                    checkOverlap = true;
                    break;
                }
                if ((start1.isBefore(start2) || start2.equals(start1)) && (end1.isAfter(end2) || end1.isEqual(end2))) {
                    System.out.println("number 3 issue");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlap Error");
                    alert.setContentText("This appointment overlaps another appointment for this customer. Please choose a new time");
                    alert.showAndWait();
                    checkOverlap = true;
                    break;
                }
                if (start == end) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment start and end time cannot be the same. Please select a different time");
                    alert.showAndWait();
                    checkOverlap = true;
                    break;
                }
                if (start.isAfter(end)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Start time cannot come after end time. Please select a different time");
                    alert.showAndWait();
                    checkOverlap = true;
                    break;
                }

            }
        }
        if(!Helper.validateBusinessHours(start1,end1,date)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Outside Business Hours");
            alert.setContentText("Please select an appointment within the business hours of 8 am and 10 pm EST");
            alert.showAndWait();

        }
        else if (checkOverlap == false) {
            int userId = Helper.getUserId(userName);
            int rowsAffected = Helper.updateAppt(title, description, location, type, start1, end1, customerId, userId, contactId, apptId);

            if (rowsAffected > 0) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Appointment updated");
                alert1.setContentText("The appointment was successfully updated");
                alert1.showAndWait();


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment not added");
                alert.setContentText("An error occurred and this appointment could not be added");
                alert.showAndWait();
            }
        }

    }


    /**This is the send appointment method. This method is used to populate the update page with the appointment chosen to update.
     * @param appt1 */
    public void sendAppointment(Appointment appt1) throws SQLException {
        LocalTime start = LocalTime.of(1, 0);
        LocalTime end = LocalTime.of(1, 30);

        while (start.isBefore(LocalTime.of(23, 0))) {
            startTimeCombo.getItems().add(start);
            start = start.plusMinutes(30);
        }
        while (end.isBefore(LocalTime.of(23, 30))) {
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
        Customer customer = Helper.getCustomerByID(appt1.getCustomerId());
        custIdCombo.setValue(customer);
        userIdCombo.setItems(Helper.getAllUsers());
        User user = Helper.getUserById(appt1.getUserId());
        userIdCombo.setValue(user);

        //populate the start time combo box with the start time from the selected appt
        LocalDateTime start1 = appt1.getStart().toLocalDateTime();
        LocalTime startTime = start1.toLocalTime();
        startTimeCombo.getSelectionModel().select(startTime);

        //populate the endtime combo box with the end time from the selected appt
        LocalDateTime end1 = appt1.getEnd().toLocalDateTime();
        LocalTime endTime = end1.toLocalTime();
        endTimeCombo.getSelectionModel().select(endTime);

        //populate the datepicker with the date of the selected appt
        LocalDate date = appt1.getStart().toLocalDateTime().toLocalDate();


    contactCombo.setItems(Helper.getAllContacts());
        Contact contact = Helper.getContactById(appt1.getContactId());
        contactCombo.setValue(contact);

       // startTimeCombo.getSelectionModel().select(appt1.getStart());

    }

    /**This is the initialize method. It is used to initialize the page upon loading.
     * @param url
     * @param rb */
@Override
    public void initialize(URL url, ResourceBundle rb){
    apptIdTxt.setDisable(true);
}
}


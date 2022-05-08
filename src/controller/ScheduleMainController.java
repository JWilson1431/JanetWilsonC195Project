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
import model.AlertInterface;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the controller for the main scheduling page. The user can view all appointments, appointments filtered by month, and appointments filtered by week. They can also click buttons to go to the add or update appointment pages.*/
public class ScheduleMainController implements Initializable {
    Stage stage;
    Parent scene;

    //lambda for alerts if a customer is not selected
    AlertInterface alert1 = s -> "A " + s + " was not chosen, please select a " + s + " and try again";

    //buttons
    @FXML
    private Button addApptBtn;
    @FXML
    private Button backToMainBtn;
    @FXML
    private Button deleteApptBtn;
    @FXML
    private Button updateApptBtn;

    //schedule table view
    @FXML
    private TableView<Appointment> scheduleTableView;

    //schedule table columns
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;
    @FXML
    private TableColumn<Appointment, String> contactCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> endDateTimeCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> startDateTimeCol;
    @FXML
    private TableColumn<Appointment, String> titleIdCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;

    //radiobuttons to switch between monthly, weekly, and all appointments views
    @FXML
    private RadioButton viewAllApptRbtn;
    @FXML
    private RadioButton viewMonthRbtn;
    @FXML
    private RadioButton viewWeekRbtn;

    //datepicker for filtering appointments
    @FXML
    private DatePicker chooseDatePicker;

    private static ObservableList<Customer> allAppts = FXCollections.observableArrayList();

    /**This is the add appointment button. When clicked, it takes the user to the add appointment page.
     * @param event */
    //when add appointment button is clicked, the user is taken to the add appointment page
    @FXML
    public void clickAddAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**This is the delete button. Upon clicking and confirmation it deletes an appointment.
     * @param event */
    //when delete appointment is clicked, the appointment is deleted after confirmation the user wants to delete
    @FXML
    public void clickDeleteBtn(ActionEvent event) throws SQLException, IOException {
        //checks to see if an appointment is selected
        if(scheduleTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment not selected");
            alert.setContentText(alert1.getAlert("appointment"));
            alert.showAndWait();
        }
        else{
            //confirms the user wants to delete the appointment
            Appointment apptToDelete = scheduleTableView.getSelectionModel().getSelectedItem();
            int customerToDeleteId = apptToDelete.getAppointmentId();
            int rowsAffected = Helper.deleteAppt(customerToDeleteId);
            if(rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete this appointment, do you want to proceed?");
                Optional<ButtonType> result = alert.showAndWait();

                //upon confirmation that the user wants to delete the customer, deletes them
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    int apptId = apptToDelete.getAppointmentId();
                    String type = apptToDelete.getType();
                    allAppts.remove(apptToDelete);
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Appointment deleted");
                    alert2.setContentText("Appointment with ID: " + apptId + " and type: " + type + " was deleted.");
                    alert2.showAndWait();
                }

                //refreshes the page once customer is deleted
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else{
                System.out.println("Delete Failed");
            }
        }

    }

    /**This is the update appointment button. Upon clicking, the user is taken to the update appointment page if an appointment is selected.
     * @param event */
    //when update appointment is clicked, the user is taken to the update appointment page
    @FXML
    public void clickUpdateAppt(ActionEvent event) throws IOException, SQLException {
        if(scheduleTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment not selected");
            alert.setContentText(alert1.getAlert("appointment"));
            alert.showAndWait();
        }
        else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateAppointment.fxml"));
            loader.load();
            UpdateAppointmentController UCC = loader.getController();
            UCC.sendAppointment(scheduleTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**This is the view all appointments radio button. When it is chosen, the table displays all appointments.
     * @param event */
    //when the view all radiobutton is clicked, all appointments are shown
    @FXML
    public void clickViewAllApptsRbtn(ActionEvent event) throws SQLException {
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();

        allAppts = Helper.getAllAppointments();
        scheduleTableView.setItems(allAppts);
    }

    /**This is the view month radio button. When it is chosen, it filters the appointment view by month.
     * @param event */
    //when the view by month radiobutton is chosen, appointments are shown for a specified month
    @FXML
    public void clickViewMonthRbtn(ActionEvent event) throws SQLException {
        ObservableList<Appointment> monthAppts = FXCollections.observableArrayList();

        //sets the date picker date as the start and adds a month for the end
        LocalDate startDate = chooseDatePicker.getValue();
        LocalDate endDate = startDate.plusMonths(1);


        monthAppts = Helper.getApptsFilteredMonthWeek(startDate, endDate);
        scheduleTableView.setItems(monthAppts);


    }
    /**This is the week view radio button. When it is chosen, appointments for a specified week are shown.
     * @param event */
    //when the view by week radiobutton is chosen, appointments are shown for a certain week
    public void clickViewWeekRbtn(ActionEvent event) throws SQLException {
        ObservableList<Appointment> weekAppts = FXCollections.observableArrayList();

        //sets the date picker date as the start and adds a week for the end
        LocalDate startDate = chooseDatePicker.getValue();
        LocalDate endDate = startDate.plusWeeks(1);


        //populate the filtered appointments into the observable list week appointments
        weekAppts = Helper.getApptsFilteredMonthWeek(startDate, endDate);

        scheduleTableView.setItems(weekAppts);

    }

    /**This is the back to main button. When clicked it takes the user back to the main screen.
     * @param event */
    @FXML
    public void clickBackToMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This is the set all appointments method. It takes in a list of appointments and sets it to the schedule table view.
     * @param listOfAppointments */
    public void setAllAppointments(ObservableList<Appointment> listOfAppointments) {
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleIdCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        scheduleTableView.setItems(listOfAppointments);

    }

    /**This is the initialize method. It sets all appointments and the date picker.
     * @param rb
     * @param url */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            setAllAppointments(Helper.getAllAppointments());
            LocalDate now = LocalDate.now();
            chooseDatePicker.setValue(now);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}


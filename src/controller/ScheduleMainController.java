package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.util.Date;

public class ScheduleMainController {
    Stage stage;
    Parent scene;

    //buttons
    @FXML
    private Button addApptBtn;
    @FXML
    private Button backToMainBtn;
    @FXML
    private Button deleteApptBtn;
    @FXML
    private Button updateApptBtn;

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
    private TableColumn<Appointment, Date> endDateTimeCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, Date> startDateTimeCol;
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


    //when add appointment button is clicked, the user is taken to the add appointment page
    @FXML
    void clickAddAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //when delete appointment is clicked, the appointment is deleted after confirmation the user wants to delete
    @FXML
    void clickDeleteBtn(ActionEvent event) {

    }

    //when update appointment is clicked, the user is taken to the update appointment page
    @FXML
    void clickUpdateAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/updateAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //when the view by month radiobutton is chosen, the user is taken to the view by month page
    @FXML
    void clickViewMonthRbtn(ActionEvent event) {

    }

    //when the view by week radiobutton is chosen, the user is taken to the view by week page
    @FXML
    void clickViewWeekRbtn(ActionEvent event) {

    }

    @FXML
    void clickBackToMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


}


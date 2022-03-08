package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;

public class UpdateAppointmentController {
    Stage stage;
    Parent scene;

    //combo boxes for type and contact
    @FXML
    private ComboBox<Appointment> TypeCombo;
    @FXML
    private ComboBox<Appointment> contactCombo;

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


    //takes user back to the main schedule page when cancel is clicked
    @FXML
    void clickCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/scheduleMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    void clickSave(ActionEvent event) {

    }

}


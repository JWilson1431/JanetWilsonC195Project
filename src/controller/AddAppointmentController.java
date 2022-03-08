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

import java.io.IOException;

public class AddAppointmentController {
    Stage stage;
    Parent scene;

    //buttons
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;

    //combo boxes
    @FXML
    private ComboBox<?> contactComboBox;
    @FXML
    private ComboBox<?> typeComboBox;

    //text fields
    @FXML
    private TextField customerIdTxt;
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField descriptionTxt;
    @FXML
    private TextField userIdTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private TextField apptIdTxt;



    //if the user clicks cancel, they are taken back to the main schedule page
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

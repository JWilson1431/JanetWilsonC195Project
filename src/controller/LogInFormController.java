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
import resources.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static DAO.Helper.checkCredentials;

    public class LogInFormController implements Initializable {
        Stage stage;
        Parent scene;

        //labels
        @FXML
        private Label enterPasswordlbl;
        @FXML
        private Label enterUserIdlbl;
        @FXML
        private Label zonelbl;
        @FXML
        private Label pleaseloginlbl;
        @FXML
        private Label schedulingsoftwarelbl;

        //buttons
        @FXML
        private Button loginbtn;
        @FXML
        private Button exitbtn;

        //password field
        @FXML
        private PasswordField passwordtxt;
        //username textfield
        @FXML
        private TextField usernametxt;





        //attempts to log in to the software when user clicks login
        @FXML
     public void clickLogIn(ActionEvent event) throws SQLException, IOException {
            String userName = usernametxt.getText();
            String password = passwordtxt.getText();

            boolean correctLogIn = checkCredentials(userName, password);
            //record log in attempt in log in file
            Logger.recordLogin(userName,correctLogIn);

            if (userName.isEmpty() || password.isEmpty()) {
                Locale userLocale = Locale.getDefault();
                ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/language");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("emptyfield1"));
                alert.setContentText(resourceBundle.getString("emptyField"));
                alert.showAndWait();
            }

            else if (correctLogIn) {
                int userId = Helper.getCurrentUser().getUserId();
                System.out.println("made it");
                System.out.println(Helper.getApptsIn15(userId));
                if (Helper.getApptsIn15(userId).isEmpty()) {
                    Locale userLocale = Locale.getDefault();
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/language");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(resourceBundle.getString("noUpcoming"));
                    alert.setContentText(resourceBundle.getString("successNoAppt"));
                    alert.showAndWait();

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else {
                    ObservableList<Appointment> appointmentsSoon = FXCollections.observableArrayList();
                    appointmentsSoon = Helper.getApptsIn15(userId);
                    for(Appointment upcomingAppointment : appointmentsSoon ) {
                        int userApptID = upcomingAppointment.getAppointmentId();
                        Timestamp userTimestamp = upcomingAppointment.getStart();

                        Locale userLocale = Locale.getDefault();
                        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/language");

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(resourceBundle.getString("successTitle"));
                        alert.setContentText(resourceBundle.getString("successlogin")  + userApptID + " " + resourceBundle.getString("time") + " " + userTimestamp);
                        alert.showAndWait();

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }
            }

            else{
                Locale userLocale = Locale.getDefault();
                ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/language");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("emptyfield1"));
                alert.setContentText(resourceBundle.getString("invalidfield"));
                alert.showAndWait();
            }
        }
  //exits application if exit is clicked
  @FXML
  void clickExit(ActionEvent event) {
      System.exit(0);

  }
  @Override
        public void initialize(URL location, ResourceBundle rb){
            rb = ResourceBundle.getBundle("resources/Language", Locale.getDefault());
            enterUserIdlbl.setText(rb.getString("enterUserIdlbl"));
            enterPasswordlbl.setText(rb.getString("enterPasswordlbl"));
            pleaseloginlbl.setText(rb.getString("pleaseloginlbl"));
            schedulingsoftwarelbl.setText(rb.getString("schedulingsoftwarelbl"));
            loginbtn.setText(rb.getString("loginbtn"));
            exitbtn.setText(rb.getString("exitbtn"));

            zonelbl.setText(ZoneId.systemDefault().toString());


  }

}

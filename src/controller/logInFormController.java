package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static DAO.helper.checkCredentials;

    public class logInFormController implements Initializable {
        Stage stage;
        Parent scene;

        @FXML
        private Label enterPasswordlbl;

        @FXML
        private Label enterUserIdlbl;

        @FXML
        private Button loginbtn;

        @FXML
        private PasswordField passwordtxt;

        @FXML
        private Label pleaseloginlbl;

        @FXML
        private Label schedulingsoftwarelbl;

        @FXML
        private TextField usernametxt;

        @FXML
        private Button exitbtn;



        //attempts to log in to the software when user clicks login
        @FXML
     public void clickLogIn(ActionEvent event) throws SQLException, IOException {
            String userName = usernametxt.getText();
            String password = passwordtxt.getText();

            boolean correctLogIn = checkCredentials(userName, password);

            if (correctLogIn) {

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
            }
            else if(userName.isEmpty() || password.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login not entered");
                alert.setContentText("A username or password was not entered");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login information invalid");
                alert.setContentText("Username or password invalid. Please try again");
                alert.showAndWait();
            }
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


  }

}

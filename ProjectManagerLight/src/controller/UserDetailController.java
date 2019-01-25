package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

//The Controller for userDetailPopUp.fxml
public class UserDetailController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField userDetailFirstNameField;

    @FXML
    private PasswordField userDetailPasswordField;

    @FXML
    private TextField userDetailLastNameField;

    @FXML
    private TextField userDetailEmailField;

    @FXML
    private TextField userDetailRoleField;

    @FXML
    private Button userDetailAssumeButton;
    
    @FXML
    private TextField userDetailShortcutField;

    @FXML
    void userDetailAssumeButtonPressed(ActionEvent event) {
    	//TODO Insert check if right values are in fields
		DatabaseController databaseController = new DatabaseController();
		
		databaseController.createUser(userDetailShortcutField.getText(),
				userDetailFirstNameField.getText(), userDetailLastNameField.getText(),
				userDetailEmailField.getText(), userDetailRoleField.getText(), userDetailPasswordField.getText());	 
    }

    @FXML
    void initialize() {

    }
	
}

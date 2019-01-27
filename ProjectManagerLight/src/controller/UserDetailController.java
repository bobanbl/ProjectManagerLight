package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
    	
    	String shortcut = userDetailShortcutField.getText().trim().toLowerCase();
    	String eMail = userDetailEmailField.getText().trim().toLowerCase();
    	if(evaluateuserDetailShortcutField(shortcut)) {
    		errorWindow("User-Shortcut already exists");
    	} else if(!validEMail(eMail)) {
    		errorWindow("eMail has false syntax!");
    	} 
//    	else if()
    	
    	
    	
    	DatabaseController databaseController = new DatabaseController();
		databaseController.createUser(shortcut, userDetailFirstNameField.getText().trim(), userDetailLastNameField.getText().trim(),
				eMail, userDetailRoleField.getText().trim(), userDetailPasswordField.getText().trim());
    	}

    @FXML
    void initialize() {
    	userDetailShortcutField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
    		if(event.getCode() == KeyCode.ENTER) {
    			evaluateuserDetailShortcutField(userDetailShortcutField.getText().trim());
    		}
    	}); 
    	
    	userDetailShortcutField.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
    		if(userDetailShortcutField.getText().trim().indexOf("") != 0) {
    			evaluateuserDetailShortcutField(userDetailShortcutField.getText().trim());
    		}
    	}); 
    	
    	userDetailEmailField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
    		if(event.getCode() == KeyCode.ENTER) {
    			validEMail(userDetailEmailField.getText().trim());
    		}
    	}); 
    	
    	userDetailEmailField.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
    		if(userDetailEmailField.getText().trim().indexOf("") != 0) {
    			validEMail(userDetailEmailField.getText().trim());
    		}
    	});    	
    	
    }
//return true if Shortcut already exists in database, otherwise false     
    private boolean evaluateuserDetailShortcutField(String shortcut) {
    	DatabaseController databaseController = new DatabaseController();
    	if(databaseController.userShortcutExistis(shortcut)) {
    		System.out.println("User-Shortcut already exists");
    		userDetailShortcutField.setStyle("-fx-control-inner-background: #FF0000");
    		return true;
    	} else {
    		System.out.println("User-Shortcut is free");
    		userDetailShortcutField.setStyle("-fx-control-inner-background: #FFFFFF");
    		return false;
    	}
    }
//returns true if eMail has right syntax, otherwise false
    private boolean validEMail(String eMail) {
    	  String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                  "[a-zA-Z0-9_+&*-]+)*@" + 
                  "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                  "A-Z]{2,7}$"; 
                    
    	  Pattern pat = Pattern.compile(emailRegex); 
    	  boolean result = pat.matcher(eMail).matches();
    	  if (eMail == null | result == false) {
    	  		System.out.println("eMail has false syntax!");
    	  		userDetailEmailField.setStyle("-fx-control-inner-background: #FF0000");
        		return false;
    	  } else {
      		System.out.println("User-Shortcut is free");
      		userDetailEmailField.setStyle("-fx-control-inner-background: #FFFFFF");
      		return false;
    	  }
    }
    
    private void errorWindow(String message) {
    	System.out.println("Print User Error-Message");
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Create User Failed!");
    	alert.setHeaderText(message);
    	alert.showAndWait();
    }
	
}

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

//The Controller for userManView.fxml
public class UserManController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ImageView addUserButton;
    
    @FXML
    void initialize() {
//Method loadUserDetailPopUp() is called, when addUserButton is pressed
    	addUserButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Add-User-Button pressed");
    		laodUserDetailPopUp();		
    	});
    }
//opens User Detail Pop Up Window    
    private void laodUserDetailPopUp() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/userDetailPopUp.fxml"));  	
			Parent root = loader.load();	
			
	    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));	
	    	Stage popUpWindow = new Stage();
	    	
	    	popUpWindow.setTitle("Create User");
	    	popUpWindow.setScene(scene);
	    	popUpWindow.showAndWait();	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
}

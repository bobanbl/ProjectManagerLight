package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

//The Controller for taskView.fxml
public class TaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addStoryButton;
    @FXML
    void initialize() {
    	addStoryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Add-Story-Button pressed");
    		laodCreateStoryPopUp();		
    	});
    }
    
    private void laodCreateStoryPopUp() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/taskDetailPopUp.fxml"));  	
			Parent root = loader.load();	
			
	    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));
	    	
	    	Stage popUpWindow = new Stage();
	    	
	    	popUpWindow.setTitle("Create Story");
	    	popUpWindow.setScene(scene);
	    	popUpWindow.showAndWait();
	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }    
    
}

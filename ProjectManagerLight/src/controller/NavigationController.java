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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//  The Controller for main.fxml
public class NavigationController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView navigationButtonUserManView;

    @FXML
    private Label mainSelectedProject;

    @FXML
    private ImageView navigationButtonTaskView;

    @FXML
    private VBox mainNavigationVBox;

    @FXML
    private ImageView navigationButtonDashboardView;
    
    @FXML
    private AnchorPane anchorPaneViews;
   
    @FXML
    private Label labelSelectedView;

    @FXML
    void initialize() {
    	System.out.println("NavigationController loaded");
    	labelSelectedView.setText("Project Dashboard");
    	
    	navigationButtonDashboardView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Project Dashboard Navigation Button pressed");
    		laodProjectView();
    	});
    	
    	navigationButtonTaskView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Task Navigation Button pressed");
    		laodTaskView();
    		
    	});
    	
    	navigationButtonUserManView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("User Man. Navigation Button pressed");
    		laodUserManView();
    	});
    	
    }
 
/* Load in AnchorPane Task View
	Set label(selected View) on "Tasks"
*/
    private void laodTaskView() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/taskView.fxml"));  	
			Parent root = loader.load();	
			anchorPaneViews.getChildren().setAll(root);
			
			labelSelectedView.setText("Tasks");
			
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
// Load in AnchorPane User Management View
    private void laodUserManView() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/userManView.fxml"));  	
			Parent root = loader.load();
			anchorPaneViews.getChildren().setAll(root);
			
			labelSelectedView.setText("User Management");
			
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }    

// Load in AnchorPane Project View    
    private void laodProjectView() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/projectView.fxml"));  	
			Parent root = loader.load();
			anchorPaneViews.getChildren().setAll(root);
			
			labelSelectedView.setText("Project Dashboard");
			
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }    
}


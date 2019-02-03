package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import model.DataModel;

//Controller for main.fxml
public class NavigationController {
	
	private DataModel model;
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
    private Label labelLoggedUser;

    @FXML
    void initialize() {
    	labelSelectedView.setText("Project Dashboard");
//click on Navigation Button Dashboard View calls method loadProjectView()    	
    	navigationButtonDashboardView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Project Dashboard Navigation Button pressed");
    		laodProjectView();
    	});
//click on Navigation Button Task View calls method loadTaskView()        	
    	navigationButtonTaskView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Task Navigation Button pressed");
    		laodTaskView();		
    	});
//click on Navigation Button User Management calls method loadUserManView()        	
    	navigationButtonUserManView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("User Man. Navigation Button pressed");
    		laodUserManView();
    	});   	
    }
    
    public void setDataModel(DataModel model) {
    	this.model = model;
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
 
//load in AnchorPane User Management View
    private void laodUserManView() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
			   	
	    	
	    	loader.setLocation(getClass().getResource("../view/userManView.fxml"));  
	    		 
			Parent root = loader.load();
			
			UserManController userManController =  loader.getController();
			userManController.setDataModel(model);
			System.out.println("trying to open: " + userManController);
			
			anchorPaneViews.getChildren().setAll(root);			
			labelSelectedView.setText("User Management");			
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }    

//load in AnchorPane Project View    
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
    
    public void setLabelLoggedUser(String loggedUser) {
    	labelLoggedUser.setText("Logged User: " + loggedUser);
    }
}


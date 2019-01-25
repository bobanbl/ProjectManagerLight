package controller;

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

//Navigation Button for loading projectView.fxml in AnchorPane
    @FXML
    void navigationButtonDashboardViewPressed(ActionEvent event) {
    	System.out.println("Dashboard Navigation Button pressed");
    }

//Navigation Button for loading taskView.fxml in AnchorPane    
    @FXML
    void navigationButtonTaskViewPressed(ActionEvent event) {
    	System.out.println("Task Navigation Button pressed");
    }
    
//Navigation Button for loading userManView.fxml in AnchorPane
    @FXML
    void navigationButtonUserManViewPressed(ActionEvent event) {
    	System.out.println("User Man. Navigation Button pressed");
    	//new fxml loader
    	//PC.getView erzeugen in jedem COntroller
    	//view.parent aufrufen
    	

    }

    @FXML
    void initialize() {

    }

}


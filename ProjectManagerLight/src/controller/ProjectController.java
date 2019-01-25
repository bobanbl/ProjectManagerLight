package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

//The Controller for projectView.fxml
public class ProjectController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addProjectButton;

    @FXML
    private AnchorPane anchorPaneDetailView;
 
    @FXML
    void initialize() {
    	System.out.println("Project Controller loaded");
    	addProjectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Project-Add-Button pressed");
    		laodDetailMainView();
    	});
    }
    
 // Load in AnchorPane Project Detail View
    private void laodDetailMainView() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/projectDetail.fxml"));  	
			Parent root = loader.load();
			anchorPaneDetailView.getChildren().setAll(root);
						
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }    
    
    private void closeDetailWindow() {
    	anchorPaneDetailView.getChildren().clear();
    }
       
    
}

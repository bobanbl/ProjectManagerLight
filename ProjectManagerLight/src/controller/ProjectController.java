package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

//Controller for projectView.fxml
public class ProjectController {
	
	private ProjectDetailController projectDetailController;	
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
//When the Project-Add-Button, the Button gets invisible and the method laodDetailMainView is called
    	addProjectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Project-Add-Button pressed");
    		addProjectButton.setVisible(false);
    		laodDetailMainView();
    	});
    }
    
 //load in AnchorPane Project Detail View
    private void laodDetailMainView() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/projectDetail.fxml"));
			Parent root = loader.load();
			this.projectDetailController = loader.getController();
			this.projectDetailController.setProjectController(this);
			anchorPaneDetailView.getChildren().setAll(root);	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
//closes the Detail Window    
    public void closeDetailWindow() {
    	anchorPaneDetailView.getChildren().clear();
    	addProjectButton.setVisible(true);
    }     
}

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//Controller for projectDetail.fxml
public class ProjectDetailController {
	
	private ProjectController projectController;
	private ProjectDetailTeamController projectDetailTeamController;
	@FXML
	ProjectDetailMainController projectDetailMainController;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane anchorPaneDetailViews;
    @FXML
    private ImageView closeDetailViewButton;
    
    @FXML
    void initialize() {
//when initializing, the instance is hand over to the object ProjectDetailMainController
		projectDetailMainController.setProjectDetailController(this);
//clicking on the Close-Detail-Window-Button calls the method closeDetailWindow in ProjectController   	
    	closeDetailViewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Close-Detail-Window-Button pressed");
    		projectController.closeDetailWindow();	 		
    	});
    }
//loads the Detail-Main-Window in the AnchorPane of the Detail-Window
    public void loadDetailMainWindow() {
    	try {
    		anchorPaneDetailViews.getChildren().clear();
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/projectDetailMain.fxml"));  	
			Parent root = loader.load();
			this.projectDetailMainController = loader.getController();
			this.projectDetailMainController.setProjectDetailController(this);
			anchorPaneDetailViews.getChildren().setAll(root);  	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
//loads the Detail-Team-Window in the AnchorPane of the Detail-Window    
    public void loadDetailTeamWindow() {
    	try {
    		anchorPaneDetailViews.getChildren().clear();
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/projectDetailTeam.fxml"));  	
			Parent root = loader.load();
			this.projectDetailTeamController = loader.getController();
			this.projectDetailTeamController.setProjectDetailController(this);
			anchorPaneDetailViews.getChildren().setAll(root);
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }  
//sets the ProjectController projectController    
    public void setProjectController(ProjectController controller) {
    	this.projectController = controller;
    	System.out.println(projectController);
    }
}

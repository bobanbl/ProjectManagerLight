package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.DataModelProject;
import model.Project;

//Controller for projectDetail.fxml
public class ProjectDetailController {
	
	private ProjectController projectController;
	private ProjectDetailTeamController projectDetailTeamController;
	private DataModelProject projectModel;
	private boolean newProject;
	private Project selectedProject;
	
	@FXML
	ProjectDetailMainController projectDetailMainController;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button createProjectButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane anchorPaneDetailViews;
    @FXML
    private ImageView closeDetailViewButton;
    @FXML
    void cancelButtonPressed(ActionEvent event) {
    	confirmClosingCreateProjectWindow();
    }

    @FXML
    void createProjectButtonPressed(ActionEvent event) {
    	System.out.println("[controller.ProjectDetailController] createProjectButtonPressed");
    	String ProjectName = projectDetailMainController.getprojectNameTextField();
    	String description = projectDetailMainController.getprojectDescriptionTextField();
    	projectModel.createProject(ProjectName, description);
    	projectController.closeDetailWindow();	
    }
    
    
    @FXML
    void initialize() {
/*when initializing, the instance is hand over to the object ProjectDetailMainController
 * in the FXML was the "fx:id="projectDetailMain"" was included
 * and in this Controller "@FXML	ProjectDetailMainController projectDetailMainController;" was included
 */
		projectDetailMainController.setProjectDetailController(this);
    	

//clicking on the Close-Detail-Window-Button calls the method closeDetailWindow in ProjectController   	
    	closeDetailViewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Close-Detail-Window-Button pressed");
    		if(checkIfChangesExists()) {
    			confirmClosingProjectDetailWindowChanges();
    		} else {
    			projectController.closeDetailWindow();
    		}
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
			projectDetailTeamController = loader.getController();
			projectDetailTeamController.setProjectDetailController(this);
			projectDetailTeamController.setSelectedProject(selectedProject);
			anchorPaneDetailViews.getChildren().setAll(root);
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }  
//sets the ProjectController projectController    
    public void setProjectController(ProjectController controller) {
    	this.projectController = controller;
		newProject = projectController.getIfNewProject();
		System.out.println("[controller.ProjectDetailController] New Project: " + newProject);
			
		if(newProject) {
			cancelButton.setVisible(true);
			createProjectButton.setVisible(true);
			closeDetailViewButton.setVisible(false);
		} 
//		else if(!newProject) {
//			cancelButton.setVisible(true);
//			createProjectButton.setVisible(true);
//			closeDetailViewButton.setVisible(false);
//		}
    }
    
    public void setDataModelProject(DataModelProject projectModel) {
    	this.projectModel = projectModel;
    }
    
    private void errorWindow(String message) {
    	System.out.println("[controller.PojectDetailController] Print User Error-Message");
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Cancel Creating Project?");
    	alert.setHeaderText(message);
    	alert.showAndWait();
    }
    
    private void confirmClosingCreateProjectWindow() {
    	System.out.println("[controller.PojectDetailController] Open Confirm Canceling Creating User Window");
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Cancel Creating Project");
    	alert.setHeaderText("Canceling: Are you sure?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		System.out.println("[controller.PojectDetailController] Creating Project canceled!");
    		projectController.closeDetailWindow();	
    	}
    	else if(result.get() == ButtonType.CANCEL) {
    	    alert.close();
    	}
    }
    
    public void setSelectedProject(Project selectedProject) {
    	this.selectedProject = selectedProject;
    	projectDetailMainController.setSelectedProject(selectedProject);	
    }
    
    public boolean checkIfChangesExists() {
    	System.out.println("[controller.ProjectDetailController] checkIfChangesExists");
    	String ProjectNameNEW = projectDetailMainController.getprojectNameTextField();
    	String descriptionNEW = projectDetailMainController.getprojectDescriptionTextField();
    	String ProjectNameOLD = selectedProject.getProjectName().trim();
    	String descriptionOLD = selectedProject.getDescription().trim();
//    	System.out.println(ProjectNameNEW + ProjectNameOLD);
    	
    	if(ProjectNameNEW.equals(ProjectNameOLD) && descriptionNEW.equals(descriptionOLD)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    private void confirmClosingProjectDetailWindowChanges() {
    	System.out.println("[controller.PojectDetailController] Open Confirm Update Project Window");
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Update Project");
    	alert.setHeaderText("Update Project: Save changes?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		System.out.println("[controller.PojectDetailController] Update Project Confirm!");
    		projectModel.updateProject(selectedProject,  projectDetailMainController.getprojectNameTextField(), projectDetailMainController.getprojectDescriptionTextField());
    		projectController.closeDetailWindow();	
    	}
    	else if(result.get() == ButtonType.CANCEL) {
    	    alert.close();
    	}
    }
}

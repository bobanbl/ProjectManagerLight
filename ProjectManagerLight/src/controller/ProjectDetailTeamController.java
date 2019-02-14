package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import model.Project;

//Controller for projectDetailTeam.fxml
public class ProjectDetailTeamController {
	
	private Project selectedProject;
	private ProjectDetailController projectDetailController;	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ImageView addProjectTeamMemberButton;
    @FXML
    private ComboBox<?> projectStatusComboBox1;
//click on Project-Detail-View-Button calls method loadDetailMainWindow in ProjectDetailController    
    @FXML
    void projectDetailView(ActionEvent event) {
    	System.out.println("Project-Detail-View-Button Pressed");
    	projectDetailController.loadDetailMainWindow();
    }
    
    @FXML
    void initialize() {

    }
//sets the ProjectDetailController projectDetailController        
    public void setProjectDetailController(ProjectDetailController controller) {
    	this.projectDetailController = controller;
    }
    
    public void setSelectedProject(Project selectedProject) {
    	this.selectedProject = selectedProject;	
    }
}

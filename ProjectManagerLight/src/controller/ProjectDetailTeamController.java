package controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Project;
import model.ProjectUser;

//Controller for projectDetailTeam.fxml
public class ProjectDetailTeamController {
	
	private Project selectedProject;
	private ProjectDetailController projectDetailController;	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField textFieldProjectSponsor;
    @FXML
    private ComboBox<ProjectUser> projectManagerComboBox;
    @FXML
    private ImageView addProjectTeamMemberButton;
    @FXML
    private TableView<ProjectUser> projectMemberTable;
   
//click on Project-Detail-View-Button calls method loadDetailMainWindow in ProjectDetailController    
    @FXML
    void projectDetailViewButtonPressed(ActionEvent event) {
    	System.out.println("[controller.ProjectDetailTeamController] Project-Detail-View-Button Pressed");
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
    	textFieldProjectSponsor.setText(selectedProject.getProjectSponsor());
    	projectManagerComboBox.setValue(selectedProject.getProjectManager()); 
    }
    
    public String getProjectSponsor() {
    	return textFieldProjectSponsor.getText().trim();
    }
    
    public ProjectUser getProjectManager() {
    	return projectManagerComboBox.getValue();
    }
}

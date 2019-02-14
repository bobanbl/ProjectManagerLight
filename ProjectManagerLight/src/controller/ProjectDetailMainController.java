package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Project;

//Controller for projectDetailMain.fxml
public class ProjectDetailMainController {
	
	private ProjectDetailController projectDetailController;
	private Project selectedProject;
	@FXML
    private TextArea projectDescriptionTextField;

    @FXML
    private Button projectTeamButton;

    @FXML
    private DatePicker projectStartDate;

    @FXML
    private ComboBox<?> projectStatusComboBox;

    @FXML
    private DatePicker projectFinishDate;

    @FXML
    private TextField projectNameTextField;

    @FXML
    private Label projectID;
//click in projecktTeamButton calls method loadDetailTeamWindow in ProjectDetailController
    @FXML
    void projectTeamButton(ActionEvent event) {
    	System.out.println("Team Button pressed");
    	projectDetailController.loadDetailTeamWindow();
    }

    @FXML
    void initialize() {
    	
    }
//sets the ProjectDetailController projectDetailController    
    public void setProjectDetailController(ProjectDetailController controller) {
    	this.projectDetailController = controller;
    }
    
    public String getprojectNameTextField() {
    	return projectNameTextField.getText().trim();
    }
    
    public String getprojectDescriptionTextField() {
    	return projectDescriptionTextField.getText().trim();
    }
    
    public void setSelectedProject(Project selectedProject) {
    	this.selectedProject = selectedProject; 	
    	projectNameTextField.setText(selectedProject.getProjectName());
    	projectDescriptionTextField.setText(selectedProject.getDescription());
    }
    
    
    

}

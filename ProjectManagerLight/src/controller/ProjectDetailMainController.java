package controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import model.Project.ProjectStatus;
import model.Task.TaskStatus;

//Controller for projectDetailMain.fxml
public class ProjectDetailMainController {

	private ProjectDetailController projectDetailController;
	private Project selectedProject;
	private boolean newProject;
	@FXML
	private TextArea projectDescriptionTextField;
	@FXML
	private Button projectTeamButton;
	@FXML
	private DatePicker projectStartDate;
	@FXML
	private ComboBox<ProjectStatus> projectStatusComboBox;
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
		projectStatusComboBox.getItems().addAll(ProjectStatus.values());

	}
	//sets the ProjectDetailController projectDetailController  
	public void setIfNewProject(boolean newProject) {
		this.newProject = newProject;
		if(newProject) {
			projectStatusComboBox.setValue(ProjectStatus.NOT_STARTED);
			projectStartDate.setValue(LocalDate.now(ZoneId.systemDefault()));
			projectFinishDate.setValue(LocalDate.now(ZoneId.systemDefault()));
		}
	}

	public void setProjectDetailController(ProjectDetailController controller) {
		this.projectDetailController = controller;
	}

	public String getProjectNameTextField() {
		return projectNameTextField.getText().trim();
	}

	public String getProjectDescriptionTextField() {
		return projectDescriptionTextField.getText().trim();
	}

	public ProjectStatus getProjectStatus() {
		return projectStatusComboBox.getValue();
	}

	public Date getprojectStartDate() {
		//converting from datatype "LocalDate" to "Date"
		LocalDate localDate = projectStartDate.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date startDate = Date.from(instant);
		return startDate;
	}

	public Date getprojectFinishDate() {
		//converting from datatype "LocalDate" to "Date"
		LocalDate localDate = projectFinishDate.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date finishDate = Date.from(instant); 	
		return finishDate;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject; 	
		projectNameTextField.setText(selectedProject.getProjectName());
		projectDescriptionTextField.setText(selectedProject.getDescription());
		projectStatusComboBox.setValue(selectedProject.getProjectStatus());
		projectID.setText(String.valueOf(selectedProject.getProjectID()));
		try { 	
			//convert from datatype "Date" to "LocalDate" for DataPicker
			Date startDate = selectedProject.getStartDate();
			Instant instantStart = startDate.toInstant();
			LocalDate localStartDate = instantStart.atZone(ZoneId.systemDefault()).toLocalDate();
			projectStartDate.setValue(localStartDate);

			Date finishDate = selectedProject.getPlanedFinishDate();

			Instant instantFinish = finishDate.toInstant();
			LocalDate localFinishDate = instantFinish.atZone(ZoneId.systemDefault()).toLocalDate();
			projectFinishDate.setValue(localFinishDate);
		} catch(NullPointerException e) {

		}
	}

	public void setValuesFromTemp(Project selectedProject, String projectNameTemp, String descriptionTemp, 
			ProjectStatus projectStatusTemp, Date projectStartDateTEMP, Date projectFinishDateTEMP) {
		this.selectedProject = selectedProject; 	
		projectNameTextField.setText(projectNameTemp);
		projectDescriptionTextField.setText(descriptionTemp);
		projectStatusComboBox.setValue(projectStatusTemp);
		if (selectedProject != null){
			projectID.setText(String.valueOf(selectedProject.getProjectID()));
		}

		try {
			//convert from datatype "Date" to "LocalDate" for DataPicker
			Date startDate = projectStartDateTEMP;
			Instant instantStart = startDate.toInstant();
			LocalDate localStartDate = instantStart.atZone(ZoneId.systemDefault()).toLocalDate();
			projectStartDate.setValue(localStartDate);

			Date finishDate = projectFinishDateTEMP;
			Instant instantFinish = finishDate.toInstant();
			LocalDate localFinishDate = instantFinish.atZone(ZoneId.systemDefault()).toLocalDate();
			projectFinishDate.setValue(localFinishDate);
		} catch(NullPointerException e) {

		}

	}
}

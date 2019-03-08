package controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Project;
import model.Project.ProjectStatus;

//Controller for projectDetailMain.fxml
public class ProjectDetailMainController {

	private ProjectDetailController projectDetailController;
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
		System.out.println("[controller.ProjectDetailMainController] Team Button pressed");
		if(getprojectFinishDate() != null && getprojectStartDate() != null && getProjectNameTextField() != null) {
			projectDetailController.loadDetailTeamWindow();
		}
	}

	//filling projectStatusComboBox with ProjectStatus with String-Values from ProjectStatus-enum
	@FXML
	void initialize() {
		projectDescriptionTextField.setWrapText(true);
		projectStatusComboBox.getItems().addAll(ProjectStatus.values());
		projectStatusComboBox.setConverter(new StringConverter<ProjectStatus>() {

			@Override
			public ProjectStatus fromString(String arg0) {
				ProjectStatus returnVar = null;
				switch(arg0){
				case "Not started": returnVar = ProjectStatus.NOT_STARTED; break;
				case "Closed": returnVar = ProjectStatus.CLOSED; break;
				case "In development": returnVar = ProjectStatus.IN_DEVELOPMENT; break;
				}
				return returnVar;
			}

			@Override
			public String toString(ProjectStatus arg0) {
				return arg0.getName();
			}			
		});
	}

	//sets the ProjectDetailController projectDetailController  
	public void setIfNewProject(boolean newProject) {
		if(newProject) {
			projectStatusComboBox.setValue(ProjectStatus.NOT_STARTED);
			projectStartDate.setValue(LocalDate.now(ZoneId.systemDefault()));
			projectFinishDate.setValue(LocalDate.now(ZoneId.systemDefault()));
		}
	}

	//sets the attribute projectDetailController
	public void setProjectDetailController(ProjectDetailController controller) {
		this.projectDetailController = controller;
	}

	/*returns the String from the projectNameTextField
	 * if this field is empty --> field-background turns red
	 */
	public String getProjectNameTextField() {
		if(projectNameTextField.getText().equals("")) {
			projectNameTextField.setStyle("-fx-control-inner-background: #FF0000");
		} else {
		return projectNameTextField.getText().trim();
		}
		return null;
	}

	//returns String from projectDescriptionTextField
	public String getProjectDescriptionTextField() {
		return projectDescriptionTextField.getText().trim();
	}

	//return selected ProjectStatus from projectStatusComboBox
	public ProjectStatus getProjectStatus() {
		return projectStatusComboBox.getValue();
	}

	//returns startDate
	public Date getprojectStartDate() {
		//converting from datatype "LocalDate" to "Date"
		//projectStartDate must have a value, if not --> field background turns red
		if(projectStartDate.getValue() == null) {
			projectStartDate.setStyle("-fx-control-inner-background: #FF0000");
		} else {
			LocalDate localDate = projectStartDate.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date startDate = Date.from(instant);
			return startDate;
		}
		return null;
		
	}

	//returns finishDate
	public Date getprojectFinishDate() {
		//converting from datatype "LocalDate" to "Date"
		//projectFinishDate must have a value, if not --> field background turns red
		if(projectFinishDate.getValue() == null) {
			projectFinishDate.setStyle("-fx-control-inner-background: #FF0000");
		} else {
			LocalDate localDate = projectFinishDate.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date finishDate = Date.from(instant); 	
			return finishDate;
		}
		return null;
	}
	
	//sets the values from the selected project into the fields 
	public void setSelectedProject(Project selectedProject) {
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
}

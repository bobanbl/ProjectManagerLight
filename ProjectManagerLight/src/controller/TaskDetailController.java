package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import model.DataModelStory;
import model.Project;
import model.ProjectUser;
import model.Story;
import model.Task;
import model.Task.TaskStatus;

public class TaskDetailController {

	private DataModelStory storyModel;
	private TaskController taskController;
	private Project selectedProject;
	private Story selectedStory;
	private Task selectedTask;

	//OLD values
	private String nameOLD;
	private String descriptionOLD;
	private String durationOLD;
	private ProjectUser responsibleUserOLD;

	//NEW values
	private String nameNEW;
	private String descriptionNEW;
	private String durationNEW;
	private ProjectUser responsibleUserNEW;

	ObservableList<ProjectUser> userList = null;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private Button assumeButton;
	@FXML
	private TextField durationTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextArea descriptionTextField;
	@FXML
	private ComboBox<ProjectUser> responsibilityComboBox;

	/* pressing Assume-Button sets the values from the text fields in the NEW-Attributes
	 *  Checks if attributes are not empty and valid and
	 *  if newTask --> method createTask then updateAndClose
	 *  if existing task --> method checkIfChangesExists --> if changes available --> confirmClosingTaskDetailWindowChanges
	 */
	@FXML
	void assumeButtonPressed(ActionEvent event) {
		System.out.println("[controller.StoryDetailController] assumeButtonPressed");

		nameNEW = nameTextField.getText().trim();
		descriptionNEW = descriptionTextField.getText().trim();
		responsibleUserNEW = responsibilityComboBox.getSelectionModel().getSelectedItem();

		if(nameNEW.equals("")) { 
			errorWindow("Name field empty!");
		} else if(descriptionNEW.equals("")) {
			errorWindow("Description missing!");
		} else if(!checkCorrectData()) {
			errorWindow("Duration has to be a Number");
		} else if (selectedTask == null) {
			createTask();
			updateAndClose();
		} else if(checkIfChangesExists()) {
			confirmClosingTaskDetailWindowChanges();
		} else {
			updateAndClose();
		}
	}

	//updates the tasks in the taskController and closes the Pop-Up Window
	private void updateAndClose() {
		taskController.updateTasks();
		taskController.closePopUpWindow();
	}

	//creates a new Object Task with the new values
	private void createTask() {
		Task newTask = new Task();
		newTask.setDescription(descriptionNEW);
		newTask.setDuration(Integer.parseInt(durationNEW));
		newTask.setTaskName(nameNEW);
		newTask.setStory(selectedStory);
		newTask.setStatus(TaskStatus.NEW);
		newTask.setResponsibility(responsibleUserNEW);
		storyModel.createTask(newTask);
	}

	@FXML
	void initialize() {
		descriptionTextField.setWrapText(true);
	}

	//sets the storyModel attribute from another object 
	public void setDataModelStory(DataModelStory storyModel) {
		this.storyModel = storyModel;
	}

	//sets the taskController attribute from another object 
	public void setTaskController(TaskController taskController) {
		this.taskController = taskController;
	}

	//sets the selectedProject attribute from another object 
	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
		setUserListFromModel();
	}

	//sets the selectedStory attribute from another object 
	public void setSelectedStory(Story selectedStory) {
		if(selectedStory != null) {
			this.selectedStory = selectedStory;
		}
	}

	/*sets the selectedTask attribute and if not null 
	 * --> the values from the selectedTask are set into the selectedStory and the ..OLD attributes
	 * and the fillSelectedValuesInFields method is called
	 */
	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;

		if(selectedTask != null) {	
			this.selectedStory = selectedTask.getStory();
			nameOLD = selectedTask.getTaskName();
			descriptionOLD = selectedTask.getDescription();
			durationOLD = String.valueOf(selectedTask.getDuration());
			fillSelectedValuesInFields();
		}
	}

	//the text fields are set from the ..OLD attributes
	private void fillSelectedValuesInFields() {
		nameTextField.setText(nameOLD);
		descriptionTextField.setText(descriptionOLD);
		durationTextField.setText(durationOLD);
	}

	/* checks if the ..OLD and ..NEW values are equal
	 * if NOT, so if changes exist --> returns TRUE
	 */
	private boolean checkIfChangesExists() {
		System.out.println("[controller.StoryDetailController] checkIfChangesExists");

		boolean changesOnProjectResponsibility = false;
		if(responsibleUserNEW == responsibleUserOLD ||  (responsibleUserNEW != null && responsibleUserNEW.equals(responsibleUserOLD))) {
			changesOnProjectResponsibility = true;			
		}

		if(nameOLD.equals(nameNEW) && descriptionOLD.equals(descriptionNEW) && durationOLD.equals(durationNEW)
				&& changesOnProjectResponsibility) {
			return false;
		} else {
			return true;
		}
	}

	/*if the value in the durationTextField is not a number, the durationTextField turns red
	 * and the method returns FALSE
	 */
	private boolean checkCorrectData() {
		durationNEW = durationTextField.getText().trim();
		if(!isNumeric(durationNEW)) {		 		
			durationTextField.setStyle("-fx-control-inner-background: #FF0000");
			return false;
		}
		return true;
	}

	//returns TRUE if the given String str is a number
	public boolean isNumeric(String str) {  
		try {  
			Integer.parseInt(str);			
		} catch(NumberFormatException e) {  
			return false;  
		}  
		return true;  
	}

	//Confirmation-Pop-Up-Window for confirming "SAVE CHANGES"
	private void confirmClosingTaskDetailWindowChanges() {
		System.out.println("[controller.TaskDetailController] Open Confirm Update Story Window");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Update Task");
		alert.setHeaderText("Update: Save changes?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			System.out.println("[controller.TaskDetailController] Update Story Confirm!");

			updateTask();

			taskController.updateTasks();
			taskController.closePopUpWindow();	
		}
		else if(result.get() == ButtonType.CANCEL) {
			alert.close();
		}
	}

	//sets the ...NEW attributes in the selectedTask-Object and updates this Object in the storyModel
	private void updateTask() {
		System.err.println("updateTask");
		selectedTask.setTaskName(nameNEW);
		selectedTask.setDescription(descriptionNEW);
		selectedTask.setDuration(Integer.parseInt(durationNEW));
		selectedTask.setResponsibility(responsibleUserNEW);
		storyModel.updateTask(selectedTask);
	}

	/*adds the ProjectMember form the selectedProject into the ObservableList<ProjectUser> userProjectList.
	 * Makes then a reference to the global ObservableList userlist
	 * and calls the method updateResponsibilityComboBox
	 */
	public void setUserListFromModel() {
		System.out.println("[controller.TaskDetailController] Project: " + selectedProject);
		ObservableList<ProjectUser> userProjectList = FXCollections.observableArrayList();
		if(selectedProject.getProjectMember() != null) {
			userProjectList.addAll(selectedProject.getProjectMember());
		}
		userList = userProjectList;
		System.out.println("[controller.TaskDetailController] userList: " + userList);   	
		updateResponsibilityComboBox();
	}

	/*sets the values from the userList in the responsibilityComboBox 
	 * and selects the user from the selctedTask if not null
	 */
	private void updateResponsibilityComboBox() {
		responsibilityComboBox.setItems(userList);
		if(selectedTask != null) {
			responsibleUserOLD = selectedTask.getResponsibility();
			responsibilityComboBox.setValue(responsibleUserOLD);
			System.out.println("[controller.TaskDetailController] responsibleUserOLD: " + responsibleUserOLD);  
		}
		responsibilityComboBox.setConverter( new StringConverter<ProjectUser>() {

			@Override
			public ProjectUser fromString(String useShortcut) {
				return responsibilityComboBox.getItems().stream().filter(u ->
				u.getUserShortcut().equals(useShortcut)).findFirst().orElse(null);
			}

			@Override
			public String toString(ProjectUser u) {
				return u.getFirstName() + " " + u.getLastName() + " (" + u.getUserShortcut() + ")";
			}	
		}); 	
	}

	//opens a alert window with the title "ERROR"
	private void errorWindow(String message) {
		System.out.println("Print User Error-Message");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}




package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.derby.impl.store.replication.net.SlaveAddress;

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
import model.DataModelUser;
import model.DataModelStory;
import model.Project;
import model.ProjectUser;
import model.Story;
import model.Task;
import model.Task.TaskStatus;

public class StoryTaskDetailController {

	private DataModelStory storyModel;
	private TaskController taskController;
	private Project selectedProject;
	private Story selectedStory;
	private Task selectedTask;
	private boolean addTask;

	private String nameOLD;
	private String descriptionOLD;
	private String durationOLD;
	private String nameNEW;
	private String descriptionNEW;
	private String durationNEW;
	private ProjectUser responsibleUserOLD;
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
		} else if (selectedTask == null && selectedStory == null) {
			createStory();
			updateAndClose();
		} else if (addTask == true) {
			createTask();
			updateAndClose();
		} else if(checkIfChangesExists()) {
			confirmClosingStoryDetailWindowChanges();
		} else {
			updateAndClose();
		}
	}
	
	private void updateAndClose() {
		taskController.updateTasks();
		taskController.closePopUpWindow();
	}

	private void createStory() {
		Story newStory = new Story();
		newStory.setDescription(descriptionNEW);
		newStory.setDuration(Integer.parseInt(durationNEW));
		newStory.setStoryName(nameNEW);
		newStory.setPositionGridPane(taskController.getRowCount());
		newStory.setProject(selectedProject);
		newStory.setResponsibility(responsibleUserNEW);
		storyModel.createStory(newStory);		 
	}
	
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
	
	public void setAddTaskTag(boolean addTask) {
		this.addTask = addTask;
	}

	public void setDataModelStory(DataModelStory storyModel) {
		this.storyModel = storyModel;
	}

	public void setTaskController(TaskController taskController) {
		this.taskController = taskController;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
		setUserListFromModel();
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
		if(selectedTask != null) {	
			nameOLD = selectedTask.getTaskName();
			descriptionOLD = selectedTask.getDescription();
			durationOLD = String.valueOf(selectedTask.getDuration());
			fillSelectedValuesInFields();
		}
	}

	public void setSelectedStory(Story selectedStory) {
		this.selectedStory = selectedStory;
    	if(selectedStory != null && addTask == false) {
    		nameOLD = selectedStory.getStoryName();
    		descriptionOLD = selectedStory.getDescription();
    		durationOLD = String.valueOf(selectedStory.getDuration());
    		fillSelectedValuesInFields();
    	}
    }
	
	private void fillSelectedValuesInFields() {
		nameTextField.setText(nameOLD);
		descriptionTextField.setText(descriptionOLD);
		durationTextField.setText(durationOLD);
	}
	
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

	private boolean checkCorrectData() {
		durationNEW = durationTextField.getText().trim();
		if(!isNumeric(durationNEW)) {		 		
			durationTextField.setStyle("-fx-control-inner-background: #FF0000");
			return false;
		}
		return true;
	}

	public boolean isNumeric(String str) {  
		try {  
			int i = Integer.parseInt(str);  
		} catch(NumberFormatException e) {  
			return false;  
		}  
		return true;  
	}

	private void confirmClosingStoryDetailWindowChanges() {
		System.out.println("[controller.StoryDetailController] Open Confirm Update Story Window");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Update");
		alert.setHeaderText("Update: Save changes?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			System.out.println("[controller.StoryDetailController] Update Story Confirm!");
			if(selectedTask == null) {
				updateStory();
			} else {
				updateTask();
			}

			taskController.updateTasks();
			taskController.closePopUpWindow();	
		}
		else if(result.get() == ButtonType.CANCEL) {
			alert.close();
		}
	}
	
	private void updateStory() {
		System.err.println("updateStory");
		selectedStory.setStoryName(nameNEW);
		selectedStory.setDescription(descriptionNEW);
		selectedStory.setDuration(Integer.parseInt(durationNEW));
		selectedStory.setResponsibility(responsibleUserNEW);
		storyModel.updateStory(selectedStory);
	}
	
	private void updateTask() {
		System.err.println("updateTask");
		selectedTask.setTaskName(nameNEW);
		selectedTask.setDescription(descriptionNEW);
		selectedTask.setDuration(Integer.parseInt(durationNEW));
		selectedTask.setResponsibility(responsibleUserNEW);
		storyModel.updateTask(selectedTask);
	}

	public void setUserListFromModel() {
		System.out.println("[controller.StroyDetailController] Project: " + selectedProject);
		ObservableList<ProjectUser> userProjectList = FXCollections.observableArrayList(selectedProject.getProjectMember());
		userList = userProjectList;
		System.out.println("[controller.StrorySetailController] userList: " + userList);   	
		updateResponsibilityComboBox();
	}

	private void updateResponsibilityComboBox() {
		responsibilityComboBox.setItems(userList);
		if(selectedStory != null && addTask == false) {
			responsibleUserOLD = selectedStory.getResponsibility();
			responsibilityComboBox.setValue(responsibleUserOLD);
			System.out.println("[controller.StrorySetailController] responsibleUserOLD: " + responsibleUserOLD);  
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

	private void errorWindow(String message) {
		System.out.println("Print User Error-Message");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}




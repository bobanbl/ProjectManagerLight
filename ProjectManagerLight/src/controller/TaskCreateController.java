package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class TaskCreateController {
	
	private DataModelStory storyModel;
	private TaskController taskController;
	private Story selectedStory;
	private Project selectedProject;
	private int duration;
	
	ObservableList<ProjectUser> userList = null;
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField durationTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private Button createButton;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private ComboBox<ProjectUser> responsibilityComboBox;


    @FXML
    void createButtonPressed(ActionEvent event) {
    	//prove if task name not longer than 10 chars
    	String taskName = nameTextField.getText().trim();
    	
    	String description = descriptionTextField.getText().trim();
    	
    	if(taskName.equals("") || description.equals("")) {
    		errorWindow("Empty field!");
    	} else if(!checkCorrectData()) {
    		errorWindow("Duration has to be a Number!");
    	} else {
        	Task newTask = new Task();
        	newTask.setDescription(description);
    		newTask.setDuration(duration);
    		newTask.setTaskName(taskName);
    		newTask.setStory(selectedStory);
    		newTask.setStatus(TaskStatus.NEW);
    		newTask.setResponsibility(getResponsibleUser());
    		storyModel.createTask(newTask);
    		taskController.updateTasks();
    		taskController.closePopUpWindow();
    	}
    }

    @FXML
    void initialize() {
    	descriptionTextField.setWrapText(true);
    }
    
    private boolean checkCorrectData() {
    	if(!isNumeric(durationTextField.getText().trim())) {		 		
    		durationTextField.setStyle("-fx-control-inner-background: #FF0000");
    		return false;
    	}
    	return true;
    }
    
    //return TRUE if str is numeric and between 0 and 999
    public boolean isNumeric(String str)  
    {  
    	try {  
    		duration = Integer.parseInt(durationTextField.getText().trim()); 
    	} catch(NumberFormatException nfe) {  
    		return false;  
    	}
    	return true;  

    }

    public void setDataModelStory(DataModelStory storyModel) {
    	this.storyModel = storyModel;
    }
    
    public void setTaskController(TaskController taskController) {
    	this.taskController = taskController;
    }
    
    public void setSelectedStory(Story selectedStory) {
    	this.selectedStory = selectedStory;
    	selectedProject = selectedStory.getProject();
    	setUserListFromModel();
    }
    
    private void errorWindow(String message) {
    	System.out.println("[controller.TaskCreateController] Print Error-Message");
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Creating Task Failed!");
    	alert.setHeaderText(message);
    	alert.showAndWait();
    }
        
    public void setUserListFromModel() {
    	ObservableList<ProjectUser> userProjectList = FXCollections.observableArrayList(selectedProject.getProjectMember());
    	userList = userProjectList;
    	
    	System.out.println("[controller.TaskCreateController] userList: " + userList);
    	updateResponsibilityComboBox();
    }
    
    private void updateResponsibilityComboBox() {
    	responsibilityComboBox.setItems(userList);
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
    
    public ProjectUser getResponsibleUser() {
    	return responsibilityComboBox.getSelectionModel().getSelectedItem();
    }
}

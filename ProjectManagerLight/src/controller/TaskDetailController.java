package controller;

import java.awt.Color;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.derby.iapi.util.StringUtil;

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
import model.Task;
import model.Task.TaskStatus;

//The Controller for taskDetailPopUp.fxml
public class TaskDetailController {
	
	private DataModelStory storyModel;
	private TaskController taskController;
	private Task selectedTask;
	private Project selectedProject;
	
	private String taskNameOLD;
	private String descriptionOLD;
	private String durationOLD;
	private String taskNameNEW;
	private String descriptionNEW;
	private String durationNEW;
	private TaskStatus taskStatusOLD;
	private TaskStatus taskStatusNEW;
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
    private ComboBox<TaskStatus> taskStatusComboBox;
    
    @FXML
    void assumeButtonPressed(ActionEvent event) {
    	System.out.println("[controller.TaskDetailController] assumeButtonPressed");
    	if(!checkCorrectData()) {
    		errorWindow("Duration has to be a Number");
    	} else if(checkIfChangesExists()) {
    		confirmClosingTaskDetailWindowChanges();
    	} else {
    		taskController.closePopUpWindow();
    	}
    }

    @FXML
    void initialize() {
    	taskStatusComboBox.getItems().addAll(TaskStatus.values());
    	descriptionTextField.setWrapText(true);
    }
    
    public void setDataModelStory(DataModelStory storyModel) {
    	this.storyModel = storyModel;	
    }
    
    public void setTaskController(TaskController taskController) {
    	this.taskController = taskController;
    }
    
    public void setSelectedTask(Task selectedTask) {
    	this.selectedTask = selectedTask;
    	selectedProject = selectedTask.getStory().getProject();
    	taskNameOLD = selectedTask.getTaskName();
    	descriptionOLD = selectedTask.getDescription();
    	durationOLD = String.valueOf(selectedTask.getDuration());
    	taskStatusOLD = selectedTask.getStatus();
    	
    	nameTextField.setText(taskNameOLD);
    	descriptionTextField.setText(descriptionOLD);
    	durationTextField.setText(durationOLD);
    	taskStatusComboBox.setValue(taskStatusOLD);
    	
    	setUserListFromModel();    	
    }
        
    private boolean checkIfChangesExists() {
    	System.out.println("[controller.StoryDetailController] checkIfChangesExists");
    	taskNameNEW = nameTextField.getText().trim();
    	descriptionNEW = descriptionTextField.getText().trim();
    	taskStatusNEW = taskStatusComboBox.getValue();
    	
    	responsibleUserNEW = responsibilityComboBox.getSelectionModel().getSelectedItem();
		boolean changesOnProjectResponsibility = false;
		if(responsibleUserNEW == responsibleUserOLD ||  (responsibleUserNEW != null && responsibleUserNEW.equals(responsibleUserOLD))) {
			changesOnProjectResponsibility = true;			
		}
	    	
    	if(taskNameOLD.equals(taskNameNEW) && descriptionOLD.equals(descriptionNEW) && durationOLD.equals(durationNEW) 
    			&& taskStatusOLD.equals(taskStatusNEW) && changesOnProjectResponsibility) {
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
        
    public static boolean isNumeric(String str)  
    {  
    	try {  
    		int i = Integer.parseInt(str);  
    	} catch(NumberFormatException nfe) {  
    		return false;  
    	}  
    	return true;  
    }
    
    private void confirmClosingTaskDetailWindowChanges() {
    	System.out.println("[controller.StoryDetailController] Open Confirm Update Story Window");
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Update Task");
    	alert.setHeaderText("Update Task: Save changes?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		System.out.println("[controller.TaskDetailController] Update Task Confirm!");
    		selectedTask.setTaskName(taskNameNEW);
    		selectedTask.setDescription(descriptionNEW);
    		selectedTask.setDuration(Integer.parseInt(durationNEW));
    		selectedTask.setStatus(taskStatusNEW);
    		selectedTask.setResponsibility(responsibleUserNEW);
    		storyModel.updateTask(selectedTask);
    		taskController.updateTasks();
    		taskController.closePopUpWindow();	
    	}
    	else if(result.get() == ButtonType.CANCEL) {
    	    alert.close();
    	}
    }
        
    public void setUserListFromModel() {
    	ObservableList<ProjectUser> userProjectList = FXCollections.observableArrayList(selectedProject.getProjectMember());
    	userList = userProjectList;
    	System.out.println("[controller.TaskDetailController] userList: " + userList);   	
    	updateResponsibilityComboBox();
    }
       
    private void updateResponsibilityComboBox() {
    	responsibleUserOLD = selectedTask.getResponsibility();
    	responsibilityComboBox.setItems(userList);
    	responsibilityComboBox.setValue(responsibleUserOLD);
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

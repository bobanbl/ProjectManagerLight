package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.DataModelStory;
import model.Task;
import model.Task.TaskStatus;

//The Controller for taskDetailPopUp.fxml
public class TaskDetailController {
	
	private DataModelStory storyModel;
	private TaskController taskController;
	private Task selectedTask;
	
	private String taskNameOLD;
	private String descriptionOLD;
	private String durationOLD;
	private String taskNameNEW;
	private String descriptionNEW;
	private String durationNEW;
	private TaskStatus taskStatusOLD;
	private TaskStatus taskStatusNEW;

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
    private ComboBox<?> responsibilityComboBox;
    @FXML
    private ComboBox<TaskStatus> taskStatusComboBox;
    
    @FXML
    void assumeButtonPressed(ActionEvent event) {
    	System.out.println("[controller.TaskDetailController] assumeButtonPressed");
    	if(checkIfChangesExists()) {
    		confirmClosingTaskDetailWindowChanges();
    	} else {
    		taskController.closePopUpWindow();
    	}
    }

    @FXML
    void initialize() {
    	taskStatusComboBox.getItems().addAll(TaskStatus.values());
 
    }
    public void setDataModelStory(DataModelStory storyModel) {
    	this.storyModel = storyModel;
    }
    
    public void setTaskController(TaskController taskController) {
    	this.taskController = taskController;
    }
    
    public void setSelectedTask(Task selectedTask) {
    	this.selectedTask = selectedTask;
    	taskNameOLD = selectedTask.getTaskName();
    	descriptionOLD = selectedTask.getDescription();
    	durationOLD = String.valueOf(selectedTask.getDuration());
    	taskStatusOLD = selectedTask.getStatus();
    	
    	nameTextField.setText(taskNameOLD);
    	descriptionTextField.setText(descriptionOLD);
    	durationTextField.setText(durationOLD);
    	taskStatusComboBox.setValue(taskStatusOLD);
    	
    }
    
    private boolean checkIfChangesExists() {
    	System.out.println("[controller.StoryDetailController] checkIfChangesExists");
    	taskNameNEW = nameTextField.getText().trim();
    	descriptionNEW = descriptionTextField.getText().trim();
    	durationNEW = durationTextField.getText().trim();
    	taskStatusNEW = taskStatusComboBox.getValue();
    	
    	if(taskNameOLD.equals(taskNameNEW) && descriptionOLD.equals(descriptionNEW) && durationOLD.equals(durationNEW) && taskStatusOLD.equals(taskStatusNEW)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    private void confirmClosingTaskDetailWindowChanges() {
    	System.out.println("[controller.StoryDetailController] Open Confirm Update Story Window");
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Update Task");
    	alert.setHeaderText("Update Task: Save changes?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		System.out.println("[controller.TaskDetailController] Update Task Confirm!");
    		storyModel.updateTask(selectedTask, taskNameNEW, descriptionNEW, Integer.parseInt(durationNEW), taskStatusNEW);
    		taskController.updateTasks();
    		taskController.closePopUpWindow();	
    	}
    	else if(result.get() == ButtonType.CANCEL) {
    	    alert.close();
    	}
    }   
    
    
    
}

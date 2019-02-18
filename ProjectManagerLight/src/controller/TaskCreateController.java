package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.DataModelStory;
import model.Project;
import model.Story;

public class TaskCreateController {
	
	private DataModelStory storyModel;
	private TaskController taskController;
	private Story selectedStory;

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
    private ComboBox<?> responsibilityComboBox;


    @FXML
    void createButtonPressed(ActionEvent event) {
    	String taskName = nameTextField.getText().trim();
    	int duration = Integer.parseInt(durationTextField.getText().trim());
    	String description = descriptionTextField.getText().trim();
    	
    	if(taskName.equals("") || description.equals("")) {
    		errorWindow("Empty field!");
    	} else {
    		storyModel.createTask(description, duration, taskName, selectedStory);
    		taskController.updateTasks();
    		taskController.closePopUpWindow();
    	}
    }

    @FXML
    void initialize() {

    }
    
    public void setDataModelStory(DataModelStory storyModel) {
    	this.storyModel = storyModel;
    }
    
    public void setTaskController(TaskController taskController) {
    	this.taskController = taskController;
    }
    
    public void setSelectedStory(Story selectedStory) {
    	this.selectedStory = selectedStory;
    }
    
    private void errorWindow(String message) {
    	System.out.println("[controller.TaskCreateController] Print Error-Message");
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Creating Task Failed!");
    	alert.setHeaderText(message);
    	alert.showAndWait();
    }
    
    
}

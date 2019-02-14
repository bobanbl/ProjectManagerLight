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
import model.DataModel;
import model.DataModelStory;
import model.ProjectUser;

public class StoryCreateController {
	
	private DataModelStory storyModel;
	private TaskController taskController;
	
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
    void assumeButtonPressed(ActionEvent event) {
    	String storyName = nameTextField.getText().trim();
    	int duration = Integer.parseInt(durationTextField.getText().trim());
    	String description = descriptionTextField.getText().trim();
    	
    	if(storyName.equals("") || description.equals("")) {
    		errorWindow("Empty field!");
    	} else {
    		storyModel.createStory(description, duration, storyName);
    		taskController.createNewStory(storyName, duration);
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
    
    private void errorWindow(String message) {
    	System.out.println("Print User Error-Message");
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Creating Story Failed!");
    	alert.setHeaderText(message);
    	alert.showAndWait();
    }
}


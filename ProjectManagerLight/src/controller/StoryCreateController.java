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

public class StoryCreateController {
	
	private DataModelStory storyModel;
	private TaskController taskController;
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
    void assumeButtonPressed(ActionEvent event) {
    	String storyName = nameTextField.getText().trim();
    	
    	String description = descriptionTextField.getText().trim();
    	
    	if(storyName.equals("") || description.equals("")) {
    		errorWindow("Empty field!");
    	} else if(!checkCorrectData()) {
    		errorWindow("Duration has to be a Number");
    	} else {
        	Story newStory = new Story();
        	newStory.setDescription(description);
    		newStory.setDuration(duration);
    		newStory.setStoryName(storyName);
    		newStory.setPositionGridPane(taskController.getRowCount());
    		newStory.setProject(selectedProject);
    		newStory.setResponsibility(getResponsibleUser());
    		storyModel.createStory(newStory);
    		System.out.println("[controller.StoryCreateController] Responsible User: " + getResponsibleUser());
    		 		
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
    
    public void setSelectedProject(Project selectedProject) {
    	this.selectedProject = selectedProject;
    	setUserListFromModel();
    }
    
    private void errorWindow(String message) {
    	System.out.println("[controller.StroyCreateController] Print Error-Message");
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Creating Story Failed!");
    	alert.setHeaderText(message);
    	alert.showAndWait();
    }
        
    public void setUserListFromModel() {
    	System.out.println("[controller.StroyCreateController] Project: " + selectedProject);
    	ObservableList<ProjectUser> userProjectList = FXCollections.observableArrayList(selectedProject.getProjectMember());
    	userList = userProjectList;
    	
    	System.out.println("[controller.StroryCreateController] userList: " + userList);
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


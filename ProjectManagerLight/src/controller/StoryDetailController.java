package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.derby.impl.store.replication.net.SlaveAddress;

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
import model.DataModel;
import model.DataModelStory;
import model.Project;
import model.ProjectUser;
import model.Story;

public class StoryDetailController {
	
	private DataModelStory storyModel;
	private DataModel userModel;
	private TaskController taskController;
	private Story selectedStory;
	
	private String storyNameOLD;
	private String descriptionOLD;
	private String durationOLD;
	private String storyNameNEW;
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
    	if(checkIfChangesExists()) {
    		confirmClosingStoryDetailWindowChanges();
    	} else {
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
    	storyNameOLD = selectedStory.getStoryName();
    	descriptionOLD = selectedStory.getDescription();
    	durationOLD = String.valueOf(selectedStory.getDuration());
    	
    	nameTextField.setText(storyNameOLD);
    	descriptionTextField.setText(descriptionOLD);
    	durationTextField.setText(durationOLD);
    	
    	setResponsibilityComboBox();
    }
    
    private boolean checkIfChangesExists() {
    	System.out.println("[controller.StoryDetailController] checkIfChangesExists");
    	storyNameNEW = nameTextField.getText().trim();
    	descriptionNEW = descriptionTextField.getText().trim();
    	durationNEW = durationTextField.getText().trim();
    	responsibleUserNEW = responsibilityComboBox.getSelectionModel().getSelectedItem();
    	
    	if(storyNameOLD.equals(storyNameNEW) && descriptionOLD.equals(descriptionNEW) && durationOLD.equals(durationNEW)
    			&& responsibleUserNEW.equals(responsibleUserOLD)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    private void confirmClosingStoryDetailWindowChanges() {
    	System.out.println("[controller.StoryDetailController] Open Confirm Update Story Window");
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Update Story");
    	alert.setHeaderText("Update Story: Save changes?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		System.out.println("[controller.StoryDetailController] Update Story Confirm!");
    		storyModel.updateStory(selectedStory, storyNameNEW, descriptionNEW, Integer.parseInt(durationNEW), responsibleUserNEW);
    		taskController.updateTasks();
    		taskController.closePopUpWindow();	
    	}
    	else if(result.get() == ButtonType.CANCEL) {
    	    alert.close();
    	}
    }
    
    public void setDataModelUser(DataModel userModel) {
    	this.userModel = userModel;
    	setUserListFromModel();
    }
    
    public void setUserListFromModel() {
    	userList = userModel.getUserBelongingToProject(selectedStory.getProject());   	
    	System.out.println("[controller.StrorySetailController] userList: " + userList);   	
    	updateResponsibilityComboBox();
    }
    
    
    private void updateResponsibilityComboBox() {
    	responsibleUserOLD = selectedStory.getResponsibility();
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
    
    private void setResponsibilityComboBox() {
    	ProjectUser selectedUser = selectedStory.getResponsibility();
    }

}




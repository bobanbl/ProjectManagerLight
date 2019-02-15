package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataModel;
import model.DataModelStory;
import model.Project;
import model.Story;

//Controller for taskView.fxml
public class TaskController {
	
	private Stage popUpWindow;
	private DataModelStory storyModel;
	private Project selectedProject;

    @FXML
    private GridPane gridPane;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ImageView addStoryButton;
    @FXML
    void initialize() {
    	mouseActivities();
    	addStoryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("[controller.TaskController] Add-Story-Button pressed");
    		laodCreateStoryPopUp();	
    	});	
    }
    //opens taskDetailPopUp.fxml in new window
    private void laodCreateStoryPopUp() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/storyCreatePopUp.fxml"));  	
			Parent root = loader.load();	
			
			StoryCreateController storyCreateController =  loader.getController();
			storyCreateController.setDataModelStory(storyModel);
			storyCreateController.setTaskController(this);
			storyCreateController.setSelectedProject(selectedProject);
			
			
	    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));    	
	    	popUpWindow = new Stage();
	    	
	    	popUpWindow.setTitle("Create Story");
	    	popUpWindow.setScene(scene);
	    	popUpWindow.showAndWait();
	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }  
    
    public void setDataModelStory(DataModelStory storyModel) {
    	this.storyModel = storyModel;
    }
    
    public void closePopUpWindow() {
    	popUpWindow.close();
    }
            
    private void createVBox(List<Story> storyList) {
    	gridPane.clearConstraints(gridPane);
    	for(Story s : storyList) {
        	Label storyNameLabel = new Label();
        	storyNameLabel.setText(s.getStoryName());
        	storyNameLabel.setTextFill(Color.WHITE);
        	storyNameLabel.setFont(new Font("Arial", 20));

        	Label storyDurationLabel = new Label();
        	storyDurationLabel.setText(String.valueOf(s.getDuration()));
        	storyDurationLabel.setTextFill(Color.WHITE);
        	storyDurationLabel.setFont(new Font("Arial", 20));
        	storyDurationLabel.setAlignment(Pos.CENTER_RIGHT);
        	
        	VBox vbox = new VBox(storyNameLabel, storyDurationLabel);
        	vbox.getStyleClass().add("vbox");
        	gridPane.addRow(s.getPositionGridPane(), vbox);
    	}
    	System.out.println("[controller.TaskController] Row Count: " + getRowCount());
    }
    
    public void updateTasks() {
    	System.out.println("[controller.TaskController] Selected Project: " + selectedProject.getProjectName());
    	List<Story> storyList = storyModel.getStoriesFromSelectedProject(selectedProject);
    	for(Story s : storyList) {
    		System.out.println("[controller.TaskController] stories: " + s);
    	}
    	createVBox(storyList);	
    }
    
    /* Returns the number of rows in the GridPane
     * 
     */
    public int getRowCount() {
    	int numRows = gridPane.getRowConstraints().size();
    	for(int i = 0; i < gridPane.getChildren().size(); i++) {
    		Node child = gridPane.getChildren().get(i);
    		if(child.isManaged()) {
    			Integer rowIndex = GridPane.getRowIndex(child);
    			if(rowIndex != null){
    				numRows = Math.max(numRows, rowIndex+1);
    			}	
    		}
    	}
    	return numRows;
    }
    
    public void setSelectedProject(Project selectedProject) {
    	this.selectedProject = selectedProject;
    	updateTasks();
    }
    
    //Not Finished -- ON WORK -- 
    public void mouseActivities() {

		
    	gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		if (event.getClickCount() == 1) {
    			
    			Node source = (Node)event.getSource();
    			System.out.println("[controller.TaskController] getChildren; " + gridPane.getChildren());
    			System.out.println("[controller.TaskController] getRow; " + event.getX());
    			System.out.println("[controller.TaskController] getCol; " + event.getY());
//    			System.out.println("[controller.TaskController] source; " + source);

    			
    		}
    		//    		selectedUserList = userTable.getSelectionModel().getSelectedItems();
    		//    		System.out.println(selectedUserList.get(0).getLastName());
    		//    		if (event.getClickCount() == 2) {
    		//    			laodUserDetailPopUp();
    		//    		}
    		//    		if (event.getButton() == MouseButton.SECONDARY && selectedUserList.get(0) != null) {
    		//    			System.out.println("Right Mouse Button clicked");
    		//    			openContextMenu();
    		//    		}
    	});
    }
    
}

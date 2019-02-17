package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
	private NavigationController navigationController;
	
	private Story selectedStory;

    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane anchorPane;
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
        	gridPane.add(vbox, 0, s.getPositionGridPane());
        	vbox.setUserData(s);
        	        	
        	Image addImage = new Image(getClass().getResourceAsStream("../assets/AddProject.png"),20,20,false,true);
        	ImageView addTaskButton = new ImageView();
        	addTaskButton.setImage(addImage);
        	
        	gridPane.add(addTaskButton, 1, s.getPositionGridPane());
        	
    	}
    	System.out.println("[controller.TaskController] Row Count: " + getRowCount());
    }
    
    public void updateTasks() {
    	System.out.println("[controller.TaskController] Selected Project: " + selectedProject.getProjectName());
    	List<Story> storyList = storyModel.getStoriesFromSelectedProject(selectedProject);
    	for(Story s : storyList) {
    		System.out.println("[controller.TaskController] Stories: " + s);
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
    		Node clickedNode = event.getPickResult().getIntersectedNode();
    		if (clickedNode != gridPane) {
    			// click on descendant node
    			Node parent = clickedNode.getParent();
    			
    			while (parent != gridPane) {
    				clickedNode = parent;
    				parent = clickedNode.getParent();
    				if(parent.getUserData() != null) {
    					selectedStory = (Story) parent.getUserData();
    					System.out.println("[controller.TaskController] StoryName: " + selectedStory.getStoryName());
    				}	
    			}
    			Integer colIndex = GridPane.getColumnIndex(clickedNode);
    			Integer rowIndex = GridPane.getRowIndex(clickedNode);
    			System.out.println("[controller.TaskController] Mouse clicked cell: " + colIndex + " and: " + rowIndex);
    		}
    		
    		if (event.getClickCount() == 2 && selectedStory != null) {
    			loadStoryDetailPopUp();
    		} else if (event.getButton() == MouseButton.SECONDARY && selectedStory != null && selectedStory != null) {
    			System.out.println("[controller.TaskController] Right Mouse Button clicked");
    			openContextMenu();
    		}
    	});
    }
    
    private void loadStoryDetailPopUp() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/storyDetailPopUp.fxml"));  	
			Parent root = loader.load();	
			
			StoryDetailController storyDetailController =  loader.getController();
			storyDetailController.setDataModelStory(storyModel);
			storyDetailController.setTaskController(this);
			storyDetailController.setSelectedStory(selectedStory);
			
	    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));    	
	    	popUpWindow = new Stage();
	    	
	    	popUpWindow.setTitle("Story Details");
	    	popUpWindow.setScene(scene);
	    	popUpWindow.showAndWait();
	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}	
    }
    
    private void openContextMenu() {
    	Label label = new Label();
    	ContextMenu contextMenu = new ContextMenu();

    	MenuItem item1 = new MenuItem("Delete Story: " + selectedStory.getStoryName());
    	item1.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent event) {
    			label.setText("Select Menu Item 1");
    			confirmDeletingStoryWindow();
    		}
    		
    	});	
    	contextMenu.getItems().addAll(item1);
    	gridPane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(gridPane, event.getScreenX(), event.getScreenY());
            }
        });

    	gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Any button pressed hide context");
    		contextMenu.hide();
    	}); 
    }
    
    public void setNavigationController(NavigationController navigationController) {
    	this.navigationController = navigationController;
    }
    
    private void confirmDeletingStoryWindow() {
    	System.out.println("[controller.TasklController] Print Story Error-Message");
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Delete Story");
    	alert.setHeaderText("Deleting story: " + selectedStory.getStoryName() + " Are you sure?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		System.out.println("[controller.TaskController] Story deleted!");
    		storyModel.deleteStory(selectedStory);	
    		navigationController.laodTaskView();
//    		updateTasks();
    		
    	}
    	else if(result.get() == ButtonType.CANCEL) {
    	    alert.close();
    	}
    }
    	
    
}

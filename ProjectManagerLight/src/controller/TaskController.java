package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.w3c.dom.events.EventException;

import javafx.collections.ObservableList;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataModelUser;
import model.DataModelStory;
import model.Project;
import model.Story;
import model.Task;
import model.Task.TaskStatus;

//Controller for taskView.fxml
public class TaskController {
	
	private Stage popUpWindow;
	private DataModelStory storyModel;
	private DataModelUser userModel;
	private Project selectedProject;
	private NavigationController navigationController;
	private List<Story> storyList;
	
	private Story selectedStory;
	private Task selectedTask;
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
    	addStoryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("[controller.TaskController] Add-Story-Button pressed");
    		laodCreateStoryPopUp();	
    	});	
    }
    
    public void updateTasks() {
    	System.out.println("[controller.TaskController] Selected Project: " + selectedProject.getProjectName());
//    	this.storyList = storyModel.getStoriesFromSelectedProject(selectedProject);
    	this.storyList = selectedProject.getStory();
    	for(Story s : storyList) {
    		System.out.println("[controller.TaskController] Stories: " + s);
    	}
    	createVBox();	
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
    
    public void setDataModelUser(DataModelUser userModel) {
    	this.userModel = userModel;
    }
    
    public void closePopUpWindow() {
    	popUpWindow.close();
    	navigationController.laodTaskView();
    }
    
    //creates the VBoxes for the GridPane from storyList and taskList (DataModelStory.class)
    private void createVBox() {
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
    		vbox.setId("StoryBox");
    		
    		vbox.setOnMouseClicked(event ->{
			System.out.println("[controller.TaskController] StoryName: " + vbox.getId());
			selectedStory = s;
			selectedTask = null;
			if (event.getClickCount() == 2) {
    				loadStoryDetailPopUp();
    		} else if (event.getButton() == MouseButton.SECONDARY) {
    			System.out.println("[controller.TaskController] Right Mouse Button clicked");
    			openContextMenu();
    		}	
    		});

    		Image addImage = new Image(getClass().getResourceAsStream("../assets/AddProject.png"),20,20,false,true);
    		ImageView addTaskButton = new ImageView();
    		addTaskButton.setImage(addImage);
    		addTaskButton.setUserData(s);
    		addTaskButton.setId("AddTaskButton");
    		addTaskButton.setOnMouseClicked(event ->{
    			System.out.println("[controller.TaskController] AddTaskButton was pressed " + addTaskButton.getUserData());
				selectedStory = (Story) addTaskButton.getUserData();
				addTaskToStory();  
    		});

    		gridPane.add(addTaskButton, 1, s.getPositionGridPane());

    		List<Task> taskListStory = s.getTasks();

    		//Creating VBoxes for every Status and every Story
    		VBox vboxNEW = new VBox();
    		VBox vboxINPROGRESS = new VBox();
    		VBox vboxONHOLD = new VBox();
    		VBox vboxREJECTED = new VBox();
    		VBox vboxCLOSED = new VBox();
    		
    		//adding porperties to Vboxes
    		vboxNEW.getProperties().put("TaskStatus", TaskStatus.NEW);
    		vboxNEW.getProperties().put("Story", s);
    		vboxINPROGRESS.getProperties().put("TaskStatus", TaskStatus.IN_PROGRESS);
    		vboxINPROGRESS.getProperties().put("Story", s);
    		vboxONHOLD.getProperties().put("TaskStatus", TaskStatus.ON_HOLD);
    		vboxONHOLD.getProperties().put("Story", s);
    		vboxREJECTED.getProperties().put("TaskStatus", TaskStatus.REJECTED);
    		vboxREJECTED.getProperties().put("Story", s);
    		vboxCLOSED.getProperties().put("TaskStatus", TaskStatus.CLOSED);
    		vboxCLOSED.getProperties().put("Story", s);
    		
    		//DragEntered Event on VBoxes
    		vboxNEW.setOnDragEntered(this::onDragEntered);
			vboxINPROGRESS.setOnDragEntered(this::onDragEntered);
			vboxONHOLD.setOnDragEntered(this::onDragEntered);
			vboxREJECTED.setOnDragEntered(this::onDragEntered);
			vboxCLOSED.setOnDragEntered(this::onDragEntered);
    		
			//DragDropped Event on VBoxes
			vboxNEW.setOnDragDropped(this::onDragDropped);
			vboxINPROGRESS.setOnDragDropped(this::onDragDropped);
			vboxONHOLD.setOnDragDropped(this::onDragDropped);
			vboxREJECTED.setOnDragDropped(this::onDragDropped);
			vboxCLOSED.setOnDragDropped(this::onDragDropped);
    		    		
    		//creates the Vboxes for the Tasks
    		for(Task t : taskListStory) {
    			System.out.println("[controller.TaskController] Tasks for VBox: " + t);
    			    			
    			Label taskNameLabel = new Label();
    			taskNameLabel.setText(t.getTaskName());
    			taskNameLabel.setTextFill(Color.WHITE);
    			taskNameLabel.setFont(new Font("Arial", 10));

    			Label taskDurationLabel = new Label();
    			taskDurationLabel.setText(String.valueOf(t.getDuration()));
    			taskDurationLabel.setTextFill(Color.WHITE);
    			taskDurationLabel.setFont(new Font("Arial", 10));
    			taskDurationLabel.setAlignment(Pos.CENTER_RIGHT);

    			VBox vbox1 = new VBox(taskNameLabel, taskDurationLabel);
    			vbox1.getStyleClass().add("vboxTasks");
    			vbox1.setUserData(t);
    			vbox1.setId("TaskBox");
    			
    			vbox1.setOnMouseClicked(event ->{
    				System.out.println("[controller.TaskController] StoryName: " + vbox.getId());
    				selectedStory = null;
    				selectedTask = (Task) vbox1.getUserData();
    				if (event.getClickCount() == 2) {
    					loadTaskDetailPopUp();
    				} else if (event.getButton() == MouseButton.SECONDARY) {
    					System.out.println("[controller.TaskController] Right Mouse Button clicked");
    					openContextMenu();
    				}	
    			});
    			
    			//Drag & Drop function from the tasks
    			vbox1.setOnDragDetected((MouseEvent event) -> {
    				Dragboard db = vbox1.startDragAndDrop(TransferMode.MOVE);
    				ClipboardContent content = new ClipboardContent();
    				selectedTask = (Task) vbox1.getUserData();
    				Image dragDropImage = vbox1.snapshot(null, null);
    				content.put(DataFormat.IMAGE, dragDropImage);
    				db.setContent(content);   		
    				event.consume();
    			});
    			
    			gridPane.setOnDragDone((DragEvent event) -> {
    				System.out.println("[controller.TaskController] 5--setOnDragDone");
    				event.consume();
    			});

    			gridPane.setOnDragOver((DragEvent event)-> {
    				if (event.getDragboard().hasImage()) {
    					event.acceptTransferModes(TransferMode.ANY);
    				}
    				event.consume();
    			});    	

    			gridPane.setOnDragExited((DragEvent event) -> {
    				event.acceptTransferModes(TransferMode.ANY);
    				System.out.println("[controller.TaskController] 3--setOnDragExited");
    				event.consume();
    			});
    			
    			switch(t.getStatus()) {
    			case NEW: vboxNEW.getChildren().add(vbox1); break;
    			case IN_PROGRESS: vboxINPROGRESS.getChildren().add(vbox1); break;
    			case ON_HOLD: vboxONHOLD.getChildren().add(vbox1); break;
    			case REJECTED: vboxREJECTED.getChildren().add(vbox1); break;
    			case CLOSED: vboxCLOSED.getChildren().add(vbox1); break;
    			}

    		}    
    		
    		//adds the Vboxes in the GridPane for the tasks, that's nessecary 
    		gridPane.add(vboxNEW, 2, s.getPositionGridPane());
    		gridPane.add(vboxINPROGRESS, 3, s.getPositionGridPane());
    		gridPane.add(vboxONHOLD, 4, s.getPositionGridPane());
    		gridPane.add(vboxREJECTED, 5, s.getPositionGridPane());
    		gridPane.add(vboxCLOSED, 6, s.getPositionGridPane());	
    	}
    }
    
    private void onDragEntered(DragEvent event) {
    	event.acceptTransferModes(TransferMode.ANY);
		System.out.println("[controller.TaskController] 2--setOnDragEntered");
		Node clickedNode = event.getPickResult().getIntersectedNode();		
		System.err.println("vBoxNEW Story: " + clickedNode.getProperties().get("Story"));
		System.err.println("vBoxNEW Status: " + clickedNode.getProperties().get("TaskStatus"));
		event.consume();
    }
    
    private void onDragDropped(DragEvent event) {
		System.out.println("[controller.TaskController] 4--setOnDragDropped"); 
		Dragboard db = event.getDragboard();
		boolean success = false;
		Node clickedNode = event.getPickResult().getIntersectedNode();
		success = executeDragDrop(clickedNode);
		event.setDropCompleted(success);
		event.consume();
    }
        
    // returns the number of rows in the GridPane
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
    
    //executes the Drag & Drop from a task
    private boolean executeDragDrop(Node clickedNode) {
    	/*proving if the task is dragged on an other task
    	 * if YES: the parent of the TaskBox is set on the given clickedNode
    	 */
    	if(clickedNode.getId() != null) {
    		if(clickedNode.getId().equals("TaskBox")) {
    			clickedNode = clickedNode.getParent();
    		}
    	}
    	Story targetStory = (Story) clickedNode.getProperties().get("Story");
    	TaskStatus targetTaskStatus = (TaskStatus) clickedNode.getProperties().get("TaskStatus");

		if(targetTaskStatus != null && targetStory != null) {	
			System.out.println("[controller.TaskController] executeDragDrop: " + targetStory + " Status: " + targetTaskStatus);
	    	Story srcStory = selectedTask.getStory();
	    	if(srcStory != targetStory){
	    		System.out.println("[model.DataModelStory] UpdateTask Drag&Drop: " + selectedTask.getStory().getStoryID() + " " + targetStory.getStoryID());
	    		selectedTask.setStory(targetStory);
	    		srcStory.getTasks().remove(selectedTask);
	    	}
	    	selectedTask.setStatus(targetTaskStatus);
			
			storyModel.updateTask(selectedTask);
			navigationController.laodTaskView();
			return true;
		}
		return false;
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
    
    private void loadTaskDetailPopUp() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/taskDetailPopUp.fxml"));  	
			Parent root = loader.load();	
			
			TaskDetailController taskDetailController =  loader.getController();
			taskDetailController.setDataModelStory(storyModel);
			taskDetailController.setTaskController(this);
			taskDetailController.setSelectedTask(selectedTask);
			
	    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));    	
	    	popUpWindow = new Stage();
	    	
	    	popUpWindow.setTitle("Task Details");
	    	popUpWindow.setScene(scene);
	    	popUpWindow.showAndWait();
	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}	
    }
    
    private void openContextMenu() {
    	Label label = new Label();
    	ContextMenu contextMenu = new ContextMenu();
    	if(selectedStory != null) {
    		MenuItem item1 = new MenuItem("Delete Story: " + selectedStory.getStoryName());
    		item1.setOnAction(new EventHandler<ActionEvent>() {

    			@Override
    			public void handle(ActionEvent event) {
    				label.setText("Select Menu Item 1");
    				confirmDeletingStoryWindow();
    			}
    		});	
    		contextMenu.getItems().addAll(item1);
    	} else if(selectedTask != null) {
    		MenuItem item1 = new MenuItem("Delete Task: " + selectedTask.getTaskName());
    		item1.setOnAction(new EventHandler<ActionEvent>() {

    			@Override
    			public void handle(ActionEvent event) {
    				label.setText("Select Menu Item 1");
    				confirmDeletingTaskWindow();
    			}
    		});	
    		contextMenu.getItems().addAll(item1);
    	}
    	
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
    	}
    	else if(result.get() == ButtonType.CANCEL) {
    	    alert.close();
    	}
    }
    
    private void confirmDeletingTaskWindow() {
    	System.out.println("[controller.TasklController] Print Task Error-Message");
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Delete Task");
    	alert.setHeaderText("Deleting task: " + selectedTask.getTaskName() + " Are you sure?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		System.out.println("[controller.TaskController] Task deleted!");
    		storyModel.deleteTask(selectedTask);	
    		navigationController.laodTaskView();    		
    	}
    	else if(result.get() == ButtonType.CANCEL) {
    	    alert.close();
    	}
    }
        
    private void addTaskToStory() {
    	System.out.println("[controller.TaskController] addTaskToStory()");
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/taskCreatePopUp.fxml"));  	
			Parent root = loader.load();	
			
			TaskCreateController taskCreateController =  loader.getController();
			taskCreateController.setSelectedStory(selectedStory);
			taskCreateController.setDataModelStory(storyModel);
			taskCreateController.setTaskController(this);
			
	    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));    	
	    	popUpWindow = new Stage();
	    	
	    	popUpWindow.setTitle("Create Task");
	    	popUpWindow.setScene(scene);
	    	popUpWindow.showAndWait();
	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }	
}



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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	private Project selectedProject;
	private NavigationController navigationController;
	private List<Story> storyList;

	//for Drag&Drop
	Story srcStory;
	TaskStatus srcTask;
	Node target;

	private Story selectedStory;
	private Task selectedTask;
	@FXML
	private GridPane gridPane;
	@FXML
	private GridPane gridPaneHEADER;
	@FXML
	private ScrollPane scrollPane;
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

		scrollPane.setContent(gridPane);

		addStoryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("[controller.TaskController] Add-Story-Button pressed");
			selectedStory = null;
			loadDetailPopUp(false);	
		});	
	}

	public void updateTasks() {
		System.out.println("[controller.TaskController] Selected Project: " + selectedProject.getProjectName());
		this.storyList = selectedProject.getStory();
		for(Story s : storyList) {
			System.out.println("[controller.TaskController] Stories: " + s);
		}
		createVBox();	
	}

	private void loadDetailPopUp(boolean addTask) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/storyTaskDetailPopUp.fxml"));  	
			Parent root = loader.load();	

			StoryTaskDetailController storyDetailController =  loader.getController();
			storyDetailController.setDataModelStory(storyModel);
			storyDetailController.setTaskController(this);
			storyDetailController.setAddTaskTag(addTask);
			storyDetailController.setSelectedTask(selectedTask);
			storyDetailController.setSelectedStory(selectedStory);
			storyDetailController.setSelectedProject(selectedProject);

			Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));    	
			popUpWindow = new Stage();

			String title = null;

			if(addTask == true){
				title = "Create Task";
			} else if(selectedStory != null || selectedTask != null) {
				title = "Details";
			} else {
				title = "Create Story";
			}

			popUpWindow.setTitle(title);
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

		navigationController.laodTaskView();
		popUpWindow = null;
	}



	//creates the VBoxes for the GridPane from storyList and taskList (DataModelStory.class)
	private void createVBox() {
		for(Story s : storyList) {
			Label storyNameLabel = new Label();
			storyNameLabel.setText(s.getStoryName());
			storyNameLabel.getStyleClass().add("storyNameLabel");


			Label storyDurationLabel = new Label();
			storyDurationLabel.setText(String.valueOf(s.getDuration()));
			storyDurationLabel.getStyleClass().add("storyAnyLabel");

			Label storyResponsibilityLabel = new Label();
			String responsibleUserShortcut;
			if(s.getResponsibility() != null) {
				responsibleUserShortcut = s.getResponsibility().getUserShortcut();
			} else {
				responsibleUserShortcut = "";
			}
			storyResponsibilityLabel.setText(responsibleUserShortcut);
			storyResponsibilityLabel.getStyleClass().add("storyAnyLabel");

			HBox hbox = new HBox(storyDurationLabel, storyResponsibilityLabel);
			hbox.setAlignment(Pos.CENTER);
			hbox.setSpacing(20);
			hbox.setPadding(new Insets(10, 0, 0, 4));

			VBox vbox = new VBox(storyNameLabel, hbox);
			vbox.getStyleClass().add("vbox");
			gridPane.add(vbox, 0, s.getPositionGridPane());
			vbox.setUserData(s);
			vbox.setId("StoryBox");

			vbox.setOnMouseClicked(event ->{
				System.out.println("[controller.TaskController] StoryName: " + vbox.getId());
				selectedStory = s;
				selectedTask = null;
				if (event.getClickCount() == 2) {
					loadDetailPopUp(false);
				} else if (event.getButton() == MouseButton.SECONDARY) {
					System.out.println("[controller.TaskController] Right Mouse Button clicked");
					openContextMenu();

				}	
			});

			Image addImage = new Image(getClass().getResourceAsStream("../assets/AddProject.png"),20,20,false,true);
			ImageView addTaskButton = new ImageView();
			addTaskButton.setImage(addImage);
			VBox vBoxAddTask = new VBox(addTaskButton);
			vBoxAddTask.setAlignment(Pos.CENTER);

			vBoxAddTask.setUserData(s);
			vBoxAddTask.setId("AddTaskButton");
			vBoxAddTask.setOnMouseClicked(event ->{
				System.out.println("[controller.TaskController] AddTaskButton was pressed " + addTaskButton.getUserData());
				selectedStory = (Story) vBoxAddTask.getUserData();
				selectedTask = null;
				loadDetailPopUp(true);
			});		

			gridPane.add(vBoxAddTask, 1, s.getPositionGridPane());

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
			
			vboxNEW.setOnDragExited(this:: onDragExited);
			vboxINPROGRESS.setOnDragExited(this:: onDragExited);
			vboxONHOLD.setOnDragExited(this:: onDragExited);
			vboxREJECTED.setOnDragExited(this:: onDragExited);
			vboxCLOSED.setOnDragExited(this:: onDragExited);

			//creates the Vboxes for the Tasks
			for(Task t : taskListStory) {

				System.out.println("[controller.TaskController] Tasks for VBox from Stories: " + t);

				Label taskNameLabel = new Label();
				taskNameLabel.setText(t.getTaskName());
				taskNameLabel.getStyleClass().add("taskNameLabel");
				taskNameLabel.setId("TaskBoxChildren");

				Label taskDurationLabel = new Label();
				taskDurationLabel.setText(String.valueOf(t.getDuration()));
				taskDurationLabel.getStyleClass().add("storyAnyLabel");	
				taskDurationLabel.setId("TaskBoxChildren");

				Label taskResponsibilityLabel = new Label();
				taskResponsibilityLabel.setId("TaskBoxChildren");
				String taskResponsibleUserShortcut;
				if(t.getResponsibility() != null) {
					taskResponsibleUserShortcut = t.getResponsibility().getUserShortcut();
				} else {
					taskResponsibleUserShortcut = "";
				}
				taskResponsibilityLabel.setText(taskResponsibleUserShortcut);
				taskResponsibilityLabel.getStyleClass().add("storyAnyLabel");	

				HBox hbox1 = new HBox(taskDurationLabel, taskResponsibilityLabel);
				hbox1.setId("TaskBoxChildren");
				hbox1.setAlignment(Pos.CENTER);
				hbox1.setSpacing(40);
				hbox1.setPadding(new Insets(10, 0, 0, 4));

				VBox vbox1 = new VBox(taskNameLabel, hbox1);
				vbox1.setAlignment(Pos.TOP_CENTER);
				vbox1.getStyleClass().add("vboxTasks");
				vbox1.setUserData(t);
				vbox1.setId("TaskBox");

				vbox1.setOnMouseClicked(event ->{
					System.out.println("[controller.TaskController] StoryName: " + vbox.getId());
					selectedStory = null;
					selectedTask = (Task) vbox1.getUserData();
					if (event.getClickCount() == 2) {
						loadDetailPopUp(false);
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
			gridPane.getStyleClass().add("gridPane1");

		}
	}
	
	private void onDragEntered(DragEvent event) {
		event.acceptTransferModes(TransferMode.ANY);
		System.out.println("[controller.TaskController] 2--setOnDragEntered");
		Node clickedNode = event.getPickResult().getIntersectedNode();	
		target = getClickedNode(clickedNode);
		target.setStyle("-fx-background-color:  #86C232;");

		event.consume();
	}

	private void onDragExited(DragEvent event) {
		event.acceptTransferModes(TransferMode.ANY);
		System.out.println("[controller.TaskController] 3--setOnDragExited");
		target.setStyle("");
		event.consume();
	}
	
	private void onDragDropped(DragEvent event) {
		System.out.println("[controller.TaskController] 4--setOnDragDropped"); 
		Dragboard db = event.getDragboard();
		boolean success = false;
		Node clickedNode = event.getPickResult().getIntersectedNode();
		success = executeDragDrop(getClickedNode(clickedNode));
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

	/*proving if the task is dragged on an other task
	 * if YES: the parent of the TaskBox is set on the given clickedNode
	 */
	private Node getClickedNode(Node clickedNode) {		
	
		if(clickedNode.getId() != null) {
			if(clickedNode.getId().equals("TaskBox")) {
				clickedNode = clickedNode.getParent();
			} 
		}

		//if dragged on parent TASK BOX
		if(clickedNode.getParent().getId() != null) {
			if(clickedNode.getParent().getId().equals("TaskBox")) {
				clickedNode =  clickedNode.getParent().getParent();
			}
		}

		//if dragged on LABEL
		if(clickedNode.getParent().getParent().getId() != null) {
			if(clickedNode.getParent().getParent().getId().equals("TaskBox")) {
				clickedNode =  clickedNode.getParent().getParent().getParent();
			}
		}
		return clickedNode;	
	}

	//executes the Drag & Drop from a task
	private boolean executeDragDrop(Node clickedNode) {
		Story targetStory = (Story) clickedNode.getProperties().get("Story");
		TaskStatus targetTaskStatus = (TaskStatus) clickedNode.getProperties().get("TaskStatus");

		if(targetTaskStatus != null && targetStory != null) {	
			System.out.println("[controller.TaskController] executeDragDrop: " + targetStory + " Status: " + targetTaskStatus);
			srcStory = selectedTask.getStory();
			srcTask = selectedTask.getStatus();
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

	//back from target to src
	private void removeDragDropAction() {
		Story targetStory = selectedTask.getStory();
		TaskStatus targetTaskStatus  = selectedTask.getStatus();

		if(srcStory != null && srcTask != null) {
			if(srcStory != targetStory) {
				selectedTask.setStory(srcStory);
				targetStory.getTasks().remove(selectedTask);
			}
			selectedTask.setStatus(srcTask);
		}
		storyModel.updateTask(selectedTask);
		navigationController.laodTaskView();		
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
			System.out.println("[controller.TaskController] Mouse Clicked to hide context menu");
			contextMenu.hide();
		}); 
	}

	public void setNavigationController(NavigationController navigationController) {
		this.navigationController = navigationController;
	}

	private void confirmDeletingStoryWindow() {
		System.out.println("[controller.TaskController] Print Story Error-Message");
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
}



package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.javafx.property.PropertyReference;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DataModelUser;
import model.DataModelProject;
import model.Project;
import model.ProjectUser;

//Controller for projectView.fxml
public class ProjectController {

	private ProjectDetailController projectDetailController;
	private NavigationController navigationController;
	private DataModelUser userModel;
	private boolean newProject;
	private DataModelProject projectModel;
	ObservableList<Project> selectedProjectList;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private ImageView addProjectButton;
	@FXML
	private AnchorPane anchorPaneDetailView;
	@FXML
	private TableView<Project> projectTable;
	@FXML
	private TableColumn<Project, Integer> colProjectID;
	@FXML
	private TableColumn<Project, String> colProjectName;
	@FXML
	private TableColumn<Project, String> colProjectStatus;


	@FXML
	void initialize() {
		//When the Project-Add-Button, the Button gets invisible and the method laodDetailMainView is called
		addProjectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("[controller.ProjectController] Project-Add-Button pressed");
			addProjectButton.setVisible(false);
			this.newProject = true;
			laodDetailMainView();
		});  	
		tablesChanges();
	}

	//load in AnchorPane Project Detail View
	private void laodDetailMainView() {
		try {
			addProjectButton.setVisible(false);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/projectDetail.fxml"));
			Parent root = loader.load();
			projectDetailController = loader.getController();
			projectDetailController.setDataModelUser(userModel);
			projectDetailController.setDataModelProject(projectModel);
			projectDetailController.setProjectController(this);

			anchorPaneDetailView.getChildren().setAll(root);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//closes the Detail Window    
	public void closeDetailWindow() {
		anchorPaneDetailView.getChildren().clear();
		addProjectButton.setVisible(true);
		initializeTable();
	} 

	public boolean getIfNewProject() {
		return this.newProject;
	}

	public void setDataModelProject(DataModelProject projectModel) {
		this.projectModel = projectModel;
		initializeTable();
	}

	public void setNavigationController(NavigationController navigationController) {
		this.navigationController = navigationController;
	}

	public void setDataModelUser(DataModelUser userModel) {
		this.userModel = userModel;
	}

	public void initializeTable() {
		System.out.println("[controller.ProjectController] Initializing table view"); 
		System.out.println("[controller.ProjectController]: " + projectModel.getProjectList());
		if(!projectModel.getProjectList().isEmpty()) {
			projectTable.setItems(projectModel.getProjectList());
			colProjectID.setCellValueFactory(new PropertyValueFactory<Project, Integer>("projectID"));
			colProjectName.setCellValueFactory(new PropertyValueFactory<Project, String>("projectName"));
			colProjectName.setStyle("-fx-alignment:CENTER-LEFT;");
			colProjectStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProjectStatus().getName()));
			
			//Select first project in table at the first opening of the application
			if(navigationController.getSelectedProject() == null) {
					navigationController.setSelectedProject(projectTable.getItems().get(0));
				}
		}
		
		projectTable.getSelectionModel().select(navigationController.getSelectedProject());
	}

	public void tablesChanges() { 	
		projectTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			selectedProjectList = projectTable.getSelectionModel().getSelectedItems();
			System.out.println("[controller.ProjectController] Mouse Click on Project: " + selectedProjectList.get(0).getProjectName());
			if (event.getClickCount() == 2) {
				System.out.println("[controller.ProjectController] Double Mouse Click on Project: " + selectedProjectList.get(0).getProjectName());
				this.newProject = false;
				laodProjectDetailWindow();
			} else if (event.getButton() == MouseButton.SECONDARY && selectedProjectList.get(0) != null) {
				System.out.println("[controller.ProjectController] Right Mouse Button clicked");
				closeDetailWindow();
				openContextMenu();
			} else if (event.getClickCount() == 1) {
				System.out.println("[controller.ProjectController] One Mouse Click on Project: " + selectedProjectList.get(0).getProjectName());
				System.out.println("[controller.ProjectController] navigationController: " + navigationController);
				navigationController.setSelectedProject(selectedProjectList.get(0));
			}
		});            
	}

	private void laodProjectDetailWindow() {
		try {
			addProjectButton.setVisible(false);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/projectDetail.fxml"));
			Parent root = loader.load();
			projectDetailController = loader.getController();
			projectDetailController.setProjectController(this);
			projectDetailController.setDataModelProject(projectModel);
			projectDetailController.setSelectedProject(selectedProjectList.get(0));
			projectDetailController.setDataModelUser(userModel);

			anchorPaneDetailView.getChildren().setAll(root);	
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private void openContextMenu() {
		Label label = new Label();
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("Delete Project: " + selectedProjectList.get(0).getProjectName());
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				label.setText("Select Menu Item 1");
				confirmDeletingProjectWindow();
			}

		});	
		contextMenu.getItems().addAll(item1);
		projectTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				contextMenu.show(projectTable, event.getScreenX(), event.getScreenY());
			}
		});

		projectTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("Any button pressed hide context");
			contextMenu.hide();
		}); 
	}

	private void confirmDeletingProjectWindow() {
		System.out.println("[controller.ProjectController] Print Project Error-Message");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete User");
		alert.setHeaderText("Deleting Project: " + selectedProjectList.get(0).getProjectName() + " Are you sure?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			System.out.println("[UserManController] Project deleted!");
			//select another Project when deleting the selected Project
			if(navigationController.getSelectedProject() == selectedProjectList.get(0)) {
				System.out.println("[controller.ProjectController] selectedProject = deletedProject");
				if(projectTable.getItems().get(0) != selectedProjectList.get(0)) {
					System.out.println("[controller.ProjectController] selectedProject = POSITION NOT 0");
					navigationController.setSelectedProject(projectTable.getItems().get(0));
				} else if(selectedProjectList.size() == 1){
					System.out.println("[controller.ProjectController] selectedProjectList: " + selectedProjectList.size());
					navigationController.setSelectedProject(null);
				} else {
					System.out.println("[controller.ProjectController] selectedProject = POSITION 0" + projectTable.getItems().get(1)) ;
					navigationController.setSelectedProject(projectTable.getItems().get(1));
				}
			}
			projectModel.deleteProject(selectedProjectList.get(0));	

		}
		else if(result.get() == ButtonType.CANCEL) {
			alert.close();
		}
	}
	

}

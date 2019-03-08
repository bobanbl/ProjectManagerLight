package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Project;
import model.Project.ProjectStatus;

//Controller for invovedProjectsPopUp.fxml
public class InvolvedProjectsController {

	private UserManController userManController;
	ObservableList<Project> projectList = null;

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private Button closeButton;
	@FXML
	private TableView<Project> projectTable;
	@FXML
	private TableColumn<Project, String> colProjectName;
	@FXML
	private TableColumn<Project, ProjectStatus> colProjectStatus;
	@FXML
	private TableColumn<Project, Integer> colProjectID;

	//pressing Close-Button calls closePopUpWindow-method in userManController
	@FXML
	void closeButtonPressed(ActionEvent event) {
		userManController.closePopUpWindow();
	}


	@FXML
	void initialize() {
	}

	//sets the attribute userManController
	public void setUserManController(UserManController userManController){
		this.userManController = userManController;
	}

	//sets the ObervableList "projectList" and calls the method initializeTableInvolvedProjects
	public void setInvolvedProjectsFromUser(List<Project> list) {
		projectList = FXCollections.observableArrayList(list);
		initializeTableInvolvedProjects();
	}

	//sets items from projectList in projectTable
	private void initializeTableInvolvedProjects() {
		System.out.println("[controller.ProjectUserAddController] initialize involved projects table view");
		projectTable.setItems(projectList);
		colProjectID.setCellValueFactory(new PropertyValueFactory<Project, Integer>("projectID"));
		colProjectName.setCellValueFactory(new PropertyValueFactory<Project, String>("projectName"));
		colProjectStatus.setCellValueFactory(new PropertyValueFactory<Project, ProjectStatus>("projectStatus"));
	}
}

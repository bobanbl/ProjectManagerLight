package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.DataModelUser;
import model.Project;
import model.Project.ProjectStatus;
import model.ProjectUser;

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

	@FXML
	void closeButtonPressed(ActionEvent event) {
		userManController.closePopUpWindow();
	}


	@FXML
	void initialize() {
	}

	public void setUserManController(UserManController userManController){
		this.userManController = userManController;
	}

	public void setInvolvedProjectsFromUser(List<Project> list) {
		projectList = FXCollections.observableArrayList(list);
		initializeTableInvolvedProjects();
	}

	private void initializeTableInvolvedProjects() {
		System.out.println("[controller.ProjectUserAddController] initialize involved projects table view");

		projectTable.setItems(projectList);
		colProjectID.setCellValueFactory(new PropertyValueFactory<Project, Integer>("projectID"));
		colProjectName.setCellValueFactory(new PropertyValueFactory<Project, String>("projectName"));
		colProjectStatus.setCellValueFactory(new PropertyValueFactory<Project, ProjectStatus>("projectStatus"));
	}


}

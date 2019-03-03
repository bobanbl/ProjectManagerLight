package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import model.ProjectUser;

public class ProjectUserAddController {

	private DataModelUser userModel;
	private ProjectDetailTeamController projectDetailTeamController;
	ObservableList<ProjectUser> userList = null;
	ObservableList<ProjectUser> selectedUserList = null;
	ObservableList<ProjectUser> alreadyAddedUserList = null;

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private Button AddButton;
	@FXML
	private TableView<ProjectUser> userTable;
	@FXML
	private TableColumn<ProjectUser, String> colShortcut;
	@FXML
	private TableColumn<ProjectUser, String> colFirstName;
	@FXML
	private TableColumn<ProjectUser, String> colLastName;
	@FXML
	private TableColumn<ProjectUser, String> colRole;

	@FXML
	void addButtonPressed(ActionEvent event) {
		projectDetailTeamController.setUserListFromProjectUserAddController(userTable.getSelectionModel().getSelectedItems());
		projectDetailTeamController.closePopUpWindow();
	}

	@FXML
	void initialize() {
	}

	public void setProjectDetailTeamController(ProjectDetailTeamController projectDetailTeamController) {
		this.projectDetailTeamController = projectDetailTeamController;
		alreadyAddedUserList = projectDetailTeamController.getProjectMembers();
	}

	public void setUserModel(DataModelUser userModel) {
		this.userModel = userModel;
		initializeTableUser();
	}

	private void initializeTableUser() {
		System.out.println("[controller.ProjectUserAddController] initialize user table view");
		/*FXCollections.observableArrayList is necessary to create a new ObservableList
		 *  with userList = userModel.getUserList() the reference would be set and the user would be 
		 *  deleted at userList.remove()
		 */ 
		userList = FXCollections.observableArrayList(userModel.getUserList()); 

		// the already added user have not to be shown in the ADD-Window
		for(ProjectUser alreadyAddedUser : alreadyAddedUserList) {
			userList.remove(alreadyAddedUser);
		}
		
		
		userTable.setItems(userList);
		userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		userTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		colShortcut.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("userShortcut"));
		colFirstName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("firstName"));
		colLastName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("lastName"));
		colRole.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("role"));
	}




}

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DataModelProject;
import model.DataModelUser;
import model.Project;
import model.ProjectUser;

//The Controller for userManView.fxml
public class UserManController {

	private DataModelUser model;
	DataModelProject projectModel;
	private ProjectUser selectedUser;
	ObservableList<ProjectUser> selectedUserList;
	private boolean isPopUpOpen = false;

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TableColumn<ProjectUser, String> colProjects;
	@FXML
	private TableColumn<ProjectUser, String> colLastName;
	@FXML
	private ImageView addUserButton;
	@FXML
	private TableView<ProjectUser> userTable;
	@FXML
	private TableColumn<ProjectUser, String> colRole;
	@FXML
	private TableColumn<ProjectUser, String> colShortcut;
	@FXML
	private TableColumn<ProjectUser, String> colFirstName;
	@FXML
	private TableColumn<ProjectUser, String> colEmail;

	private Stage popUpWindow;

	//opens at the initializing the method "tablesChanges"
	@FXML
	void initialize() {
		//Method loadUserDetailPopUp() is called, when addUserButton is pressed
		addUserButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("[controller.UserManController] Add-User-Button pressed");
			loadUserDetailPopUp(true);
		});
		tablesChanges();
	}

	//sets the attribute "model" and calls the method "initializeTable"
	public void setDataModel(DataModelUser userModel) {
		this.model = userModel;
		initializeTable();
	}
	
	//sets the attribute projectModel
	public void setProjectModel(DataModelProject projectModel) {
		this.projectModel = projectModel;
	}

	//sets the items from the userList in the userModel into the columns from the userTable
	private void initializeTable() {
		System.out.println("[controller.UserManController] initialize table view");
		userTable.setItems(model.getUserList());
		colShortcut.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("userShortcut"));
		colFirstName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("firstName"));
		colLastName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("lastName"));
		colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().geteMail()));
		colRole.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("role"));
		colProjects.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
	}

	//closes the Pop-up-Window    
	public void closePopUpWindow() {
		popUpWindow.close();
	}

	/*recognizes mouse-clicks on the userTable and sets the Clicked-User on the attribute selectedUser
	 * at Double-Mouse-Click on ProjectUser in table --> calling method: loadUserDetailPopUp
	 * at Right-Mouse-Click on ProjectUser in table --> calling method: openContextMenu
	 */
	public void tablesChanges() {
		
		userTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				selectedUserList = userTable.getSelectionModel().getSelectedItems();
				selectedUser = selectedUserList.get(0);				
				getContextMenu().show(userTable, event.getScreenX(), event.getScreenY());
			}
		});
		
		userTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			selectedUserList = userTable.getSelectionModel().getSelectedItems();
			selectedUser = selectedUserList.get(0);
			System.out.println("[controller.UserManController] selectedUser: " + selectedUser.getLastName());
			if (event.getClickCount() == 2) {
				loadUserDetailPopUp(false);
			}
		}); 
	}

	/*opens User Detail Pop-Up-Window  
	 * if given attribute "addUser" TRUE --> Create User 
	 * if given attribute "addUser" FALSE --> Detail User 
	 */
	private void loadUserDetailPopUp(boolean addUser) {
		if(!isPopUpOpen) {
			try {
				isPopUpOpen = true;
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../view/userDetailPopUp.fxml"));  	
				Parent root = loader.load();

				UserDetailController userDetailController =  loader.getController();
				userDetailController.setDataModel(model);
				userDetailController.setUserManController(this);
				if(addUser == true) {
					selectedUser = null;
				}

				userDetailController.setSelectedUser(selectedUser);

				Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));	
				popUpWindow = new Stage();

				String title;
				if(addUser == false) {
					title = "User Detail";
				} else {
					title = "Create User";
				}

				popUpWindow.setTitle(title);
				popUpWindow.setScene(scene);
				popUpWindow.setResizable(false);
				popUpWindow.showAndWait();	  
				isPopUpOpen = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} 

	// opens context menu with the items: "DELETE USER" and "SHOW INVOLVED PROJECTS"
	private ContextMenu getContextMenu() {
		Label label = new Label();
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("Delete User: " + selectedUser.getUserShortcut());
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				label.setText("[controller.UserManController] Select Menu Item 1");
				confirmDeletingUserWindow();
			}

		});		

		MenuItem item2 = new MenuItem("Show involved projects from user");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				label.setText("[controller.UserManController] Select Menu Item 2");
				openInvolvedProjectsPopUpWindow();
			}

		});		
		contextMenu.getItems().addAll(item1, item2);

		userTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("[controller.UserManController] Any button pressed hide context");
			contextMenu.hide();
		}); 
		
		return contextMenu;
	}

	//opens Alert-Pop-Up-Window with confirmation "Delete User"
	private void confirmDeletingUserWindow() {
		System.out.println("[controller.UserManController] Print User Error-Message");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete User");
		alert.setHeaderText("Deleting user: " + selectedUser.getUserShortcut() + ". Are you sure?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			System.out.println("[controller.UserManController] User deleted!");
			if(selectedUser.getInvolvedProjects() != null) {
				for(Project p : selectedUser.getInvolvedProjects()) {
					p.removeMemberFromProject(selectedUser);
					projectModel.updateProject(p);
				}
			}

			model.deleteUser(selectedUser);	
		}
		else if(result.get() == ButtonType.CANCEL) {
			alert.close();
		}
	}   

	/*opens Involved-Project-Pop-Up-Window 
	 * the title of the Window involves the first and last name from the selected ProjectUser
	 */
	private void openInvolvedProjectsPopUpWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/involvedProjectsPopUp.fxml"));  	
			Parent root = loader.load();

			InvolvedProjectsController involvedProjectsController =  loader.getController();
			involvedProjectsController.setUserManController(this);
			involvedProjectsController.setInvolvedProjectsFromUser(selectedUser.getInvolvedProjects());

			Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));	
			popUpWindow = new Stage();
			String title = "Involved Project from User " + selectedUser.getFirstName() + 
					" " + selectedUser.getLastName();
			popUpWindow.setTitle(title);
			popUpWindow.setScene(scene);
			popUpWindow.showAndWait();	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

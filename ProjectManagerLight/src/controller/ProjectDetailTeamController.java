package controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Project;
import model.ProjectUser;
import model.DataModelUser;

//Controller for projectDetailTeam.fxml
public class ProjectDetailTeamController {
	
	private Project selectedProject;
	private DataModelUser userModel;
	private ProjectDetailController projectDetailController;	
	private Stage popUpWindow;
	ObservableList<ProjectUser> userList = null;
	ObservableList<ProjectUser> selectedUserList = null;
	
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    /*Project Sponsor is a TextField bc it could be also an external Person 
     * and so its not necessary to add the sponsor in the user list
     */
    private TextField textFieldProjectSponsor;
    @FXML
    private ComboBox<ProjectUser> projectManagerComboBox;
    @FXML
    private ImageView addProjectTeamMemberButton;
    @FXML
    private TableView<ProjectUser> projectMemberTable;
    @FXML
    private TableColumn<ProjectUser, String> colShortcut;
    @FXML
    private TableColumn<ProjectUser, String> colFirstName;
    @FXML
    private TableColumn<ProjectUser, String> colLastName;
    @FXML
    private TableColumn<ProjectUser, String> colRole;
    
   
    //click on Project-Detail-View-Button calls method loadDetailMainWindow in ProjectDetailController    
    @FXML
    void projectDetailViewButtonPressed(ActionEvent event) {
    	System.out.println("[controller.ProjectDetailTeamController] Project-Detail-View-Button Pressed");
    	projectDetailController.loadDetailMainWindow();
    }
    
    @FXML
    void initialize() {
    	//Method loadUserDetailPopUp() is called, when addUserButton is pressed
    	addProjectTeamMemberButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("[controller.ProjectDetailTeamController] Add-Team-Member-Button pressed");
    		laodUserSelectPopUp();
    	});
    	clickOnTable();
    }
    
    //opens User Select Pop Up Window - Create User    
    private void laodUserSelectPopUp() {
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("../view/userAddToProjectPopUp.fxml"));  	
    		Parent root = loader.load();

    		ProjectUserAddController projectUserAddController =  loader.getController();
    		
    		projectUserAddController.setProjectDetailTeamController(this);
    		projectUserAddController.setUserModel(userModel);

    		Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));	
    		popUpWindow = new Stage();
	    	
	    	popUpWindow.setTitle("Select User");
	    	popUpWindow.setScene(scene);
	    	popUpWindow.showAndWait();	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //close Pop-up window - Select User   
    public void closePopUpWindow() {
    	popUpWindow.close();
    }
    
    //sets the ProjectDetailController projectDetailController        
    public void setProjectDetailController(ProjectDetailController controller) {
    	this.projectDetailController = controller;
    }
    
    public void setSelectedProject(Project selectedProject) {
    	this.selectedProject = selectedProject; 
    	textFieldProjectSponsor.setText(selectedProject.getProjectSponsor());
    	projectManagerComboBox.setValue(selectedProject.getProjectManager()); 
    }
    
    public String getProjectSponsor() {
    	String projectSponsor = "";
    	if (textFieldProjectSponsor.getText() != null) {
    		projectSponsor = textFieldProjectSponsor.getText().trim();
    	}
    	return projectSponsor;
    }
    
    public ProjectUser getProjectManager() {
    	return projectManagerComboBox.getSelectionModel().getSelectedItem();
    }
    
    public void setDataModelUser(DataModelUser userModel) {
    	this.userModel = userModel;
    	ObservableList<ProjectUser> userList = FXCollections.observableArrayList();
    	userList = userModel.getUserList();
    	
    	projectManagerComboBox.setItems(userList);
    	projectManagerComboBox.setConverter( new StringConverter<ProjectUser>() {

			@Override
			public ProjectUser fromString(String useShortcut) {
				 return projectManagerComboBox.getItems().stream().filter(u ->
				 u.getUserShortcut().equals(useShortcut)).findFirst().orElse(null);
			}

			@Override
			public String toString(ProjectUser u) {
				return u.getFirstName() + " " + u.getLastName() + " (" + u.getUserShortcut() + ")";
			}	
		}); 	
    }
    
    public void setValuesFromTemp(String projectSponsor, ProjectUser projectManager, ObservableList<ProjectUser> projectMembersListTemp) {
    	textFieldProjectSponsor.setText(projectSponsor);
    	projectManagerComboBox.setValue(projectManager);
    	userList = projectMembersListTemp;
    	updateProjectMemberTable();
    }
    
    public void setUserListFromModel() {
    	ObservableList<ProjectUser> userProjectList = FXCollections.observableArrayList();
    	if(selectedProject.getProjectMember() != null) {
    		userProjectList.addAll(selectedProject.getProjectMember());
    	}
    	userList = userProjectList;
    	
    	System.out.println("[controller.ProjectDetailTeamCotnroller] userList: " + userList);
    	updateProjectMemberTable();
    }
        
    public void setUserListFromProjectUserAddController(ObservableList<ProjectUser> list) {
    	userList.addAll(list);
    	updateProjectMemberTable();
    }
    
    public ObservableList<ProjectUser> getProjectMembers(){
    	
    	return userList;
    }
    
    public void updateProjectMemberTable() {
    	System.out.println("[controller.ProjectUserAddController] initialize table view for project: " + selectedProject);
    	projectMemberTable.setItems(userList);
    	colShortcut.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("userShortcut"));
    	colFirstName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("firstName"));
    	colLastName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("lastName"));
    	colRole.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("role"));
    }

    public void clickOnTable() {
    	projectMemberTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("[controller.ProjectUserAddController] click on table");
    		if(projectMemberTable.getSelectionModel().getSelectedItems() != null) {
    			selectedUserList = projectMemberTable.getSelectionModel().getSelectedItems();
    		}

    		if (event.getButton() == MouseButton.SECONDARY && selectedUserList.get(0) != null) {
    			System.out.println("Right Mouse Button clicked");
    			openContextMenu();
    		}
    	});
    }
    
    private void openContextMenu() {
    	Label label = new Label();
    	ContextMenu contextMenu = new ContextMenu();

    	MenuItem item1 = new MenuItem("Delete User from Project: " + selectedUserList.get(0).getUserShortcut());
    	item1.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent event) {
    			label.setText("[controller.ProjectDetailTeamController] Select Menu Item 1");
    			userList.remove(selectedUserList.get(0));	
    		}
    	});	
    	contextMenu.getItems().addAll(item1);
    	projectMemberTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(projectMemberTable, event.getScreenX(), event.getScreenY());
            }
        });

    	projectMemberTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("(controller.ProjectDetailTeamController] Any button pressed hide context");
    		contextMenu.hide();
    	}); 
    }
}



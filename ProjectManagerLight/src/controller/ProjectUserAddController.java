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
import model.DataModel;
import model.Project;
import model.ProjectUser;

public class ProjectUserAddController {
	
	private DataModel userModel;
	private Project selectedProject;
	private ProjectDetailTeamController projectDetailTeamController;
	ObservableList<ProjectUser> userList = null;
	ObservableList<ProjectUser> selectedUserList = null;

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
    	
//    	userTable.getSelectionModel().getSelectedItems()
//    	for(ProjectUser p : userTable.getSelectionModel().getSelectedItems()) {
//        	System.err.println(p);
//        	userModel.setProjectToUser(p, selectedProject);
//        	}
	
    	projectDetailTeamController.setUserListFromProjectUserAddController(userTable.getSelectionModel().getSelectedItems());
    	projectDetailTeamController.closePopUpWindow();
    }

    @FXML
    void initialize() {
//    	multibleTableselection();
    }
    
    public void setProjectDetailTeamController(ProjectDetailTeamController projectDetailTeamController) {
    	this.projectDetailTeamController = projectDetailTeamController;
    }
    
    public void setUserModel(DataModel userModel) {
    	this.userModel = userModel;
    	initializeTable();
    }
    
    public void setSelectedProject(Project selectedProject) {
    	this.selectedProject = selectedProject; 
    }
    
    private void initializeTable() {
    	System.out.println("[controller.ProjectUserAddController] initialize table view");
    	userList = userModel.getUserList();
//    	System.out.println("-------------" + projectDetailTeamController.getProjectSponsor());
//    	for(ProjectUser u : userList) {
//    		try {
//    			
//    			if(u.equals(projectDetailTeamController.getProjectSponsor())) {
//    				userList.remove(u);
//    			}
//    		} catch(NullPointerException e){
//
//    		}
//    	}

    		
    	userTable.setItems(userList);
    	userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	userTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    	colShortcut.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("userShortcut"));
    	colFirstName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("firstName"));
    	colLastName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("lastName"));
    	colRole.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("role"));
    }
    
    //this method should select multiple user by mouse click and will be developed later
    private void multibleTableselection(){
    	selectedUserList  = FXCollections.observableArrayList();
    	userTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("[controller.ProjectUserAddController] CLICK on Table");
    		
    		for(ProjectUser u : userTable.getSelectionModel().getSelectedItems()) {
//    			try {
//    				for(ProjectUser v : selectedUserList) {
//    					if(!u.equals(v)) {
    			System.out.println("[controller.ProjectUserAddController] CLICK on Table" + u);
    						selectedUserList.add(u);
//    					}
//    				}
//    			} catch(NullPointerException e) {
//
//    			}

    		}    
    	});
    	
    	
   
    }
}

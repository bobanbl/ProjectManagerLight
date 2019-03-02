package controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.DataModelUser;
import model.DataModelProject;
import model.Project;
import model.Project.ProjectStatus;
import model.ProjectUser;

//Controller for projectDetail.fxml
public class ProjectDetailController {

	private ProjectController projectController;
	private ProjectDetailTeamController projectDetailTeamController;
	private DataModelProject projectModel;
	private DataModelUser userModel;
	private boolean newProject;
	private Project selectedProject;
	private Date projectStartDateOLD = null;
	private Date projectFinishDateOLD = null;
	private ProjectUser projectManagerOLD = null;
	private ObservableList<ProjectUser> projectMembersListOLD = null;
	
	private Date projectStartDateNEW = null;
	private Date projectFinishDateNEW  = null;
	private String projectNameNEW;
	private String descriptionNEW;
	private ProjectStatus projectStatusNEW;
	private String projectSponsorNEW;
	private ProjectUser projectManagerNEW = null;
	private ObservableList<ProjectUser> projectMembersListNEW = null;
	
	private String projectNameTemp;
	private String descriptionTemp;
	private ProjectStatus projectStatusTemp;
	private Date projectStartDateTEMP = null;
	private Date projectFinishDateTEMP = null;
	private String projectSponsorTemp;
	private ProjectUser projectManagerTemp = null;
	private boolean firstOpeningTeamWindow = true;
	private ObservableList<ProjectUser> projectMembersListTemp = null;

	@FXML
	ProjectDetailMainController projectDetailMainController;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private Button createProjectButton;
	@FXML
	private Button cancelButton;
	@FXML
	private AnchorPane anchorPaneDetailViews;
	@FXML
	private ImageView closeDetailViewButton;
	@FXML
	void cancelButtonPressed(ActionEvent event) {
		confirmClosingCreateProjectWindow();
	}

	@FXML
	void createProjectButtonPressed(ActionEvent event) {
		Date projectStartDate = null;
		Date projectFinishDate = null;
				
		//values from Project Detail Main Windows
		System.out.println("[controller.ProjectDetailController] createProjectButtonPressed");
		projectNameNEW = projectDetailMainController.getProjectNameTextField();
		descriptionNEW = projectDetailMainController.getProjectDescriptionTextField();
		projectStatusNEW = projectDetailMainController.getProjectStatus();

		projectStartDateNEW = projectDetailMainController.getprojectStartDate();
		projectFinishDateNEW = projectDetailMainController.getprojectFinishDate();
				
		//values from Project Team Main Windows
		if(projectDetailTeamController != null) {
			projectSponsorNEW = projectDetailTeamController.getProjectSponsor();
			projectManagerNEW = projectDetailTeamController.getProjectManager();
			projectMembersListNEW = projectDetailTeamController.getProjectMembers();	
		} else {
			projectSponsorNEW = "";
			projectManagerNEW = null;
			projectMembersListNEW = null;
		}
		
		updateProject();

		projectController.closeDetailWindow();	
	}

	@FXML
	void initialize() {
		/*when initializing, the instance is hand over to the object ProjectDetailMainController
		 * in the FXML was the "fx:id="projectDetailMain"" was included
		 * and in this Controller "@FXML	ProjectDetailMainController projectDetailMainController;" was included
		 */
		projectDetailMainController.setProjectDetailController(this);

		//clicking on the Close-Detail-Window-Button calls the method closeDetailWindow in ProjectController   	
		closeDetailViewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("[controller.ProjectDetailCOntroller] Close-Detail-Window-Button pressed");
			if(checkIfValuesValid()) {
				if(checkIfChangesExists()) {
					confirmClosingProjectDetailWindowChanges();
				} else {
					projectController.closeDetailWindow();
				}
			}
		});
	}
	//loads the Detail-Main-Window in the AnchorPane of the Detail-Window
	public void loadDetailMainWindow() {
		saveValuesTemporaryTeamWindow();
		try {
			anchorPaneDetailViews.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/projectDetailMain.fxml"));  	
			Parent root = loader.load();
			
			this.projectDetailMainController = loader.getController();
			this.projectDetailMainController.setProjectDetailController(this);
			projectDetailMainController.setValuesFromTemp(selectedProject, projectNameTemp, descriptionTemp, projectStatusTemp, projectStartDateTEMP, projectFinishDateTEMP);
	
			anchorPaneDetailViews.getChildren().setAll(root);  	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//loads the Detail-Team-Window in the AnchorPane of the Detail-Window    
	public void loadDetailTeamWindow() {
		saveValuesTemporaryMainWindow();
		try {
			anchorPaneDetailViews.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/projectDetailTeam.fxml"));  	
			Parent root = loader.load();
			projectDetailTeamController = loader.getController();
			projectDetailTeamController.setProjectDetailController(this);
			projectDetailTeamController.setDataModelUser(userModel);

			projectDetailTeamController.setSelectedProject(selectedProject);
			if(projectMembersListTemp == null) {
				projectDetailTeamController.setUserListFromModel();
			}

			if(!firstOpeningTeamWindow) {
				projectDetailTeamController.setValuesFromTemp(projectSponsorTemp, projectManagerTemp, projectMembersListTemp);
			}
			
			firstOpeningTeamWindow = false;
			anchorPaneDetailViews.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  

	private void saveValuesTemporaryMainWindow() {
		projectNameTemp = projectDetailMainController.getProjectNameTextField();
		descriptionTemp = projectDetailMainController.getProjectDescriptionTextField();
		projectStatusTemp = projectDetailMainController.getProjectStatus();
		projectStartDateTEMP = projectDetailMainController.getprojectStartDate();
		projectFinishDateTEMP = projectDetailMainController.getprojectFinishDate();
	}

	private void saveValuesTemporaryTeamWindow() {
			projectSponsorTemp = projectDetailTeamController.getProjectSponsor();
			projectManagerTemp = projectDetailTeamController.getProjectManager();
			projectMembersListTemp = projectDetailTeamController.getProjectMembers();
	}
	
	//sets the ProjectController projectController    
	public void setProjectController(ProjectController controller) {
		this.projectController = controller;
		newProject = projectController.getIfNewProject();
		System.out.println("[controller.ProjectDetailController] New Project: " + newProject);

		if(newProject) {
			cancelButton.setVisible(true);
			createProjectButton.setVisible(true);
			closeDetailViewButton.setVisible(false);

			//setting local Date now
			LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date date = Date.from(instant);	
						
			Project newProject = new Project();
	    	newProject.setDescription("");
			newProject.setProjectName("");
			newProject.setProjectStatus(ProjectStatus.NOT_STARTED);
			newProject.setStartDate(date);
			newProject.setPlanedFinishDate(date);
			newProject.setProjectSponsor("");
			newProject.setProjectManager(null);
			
			selectedProject = projectModel.createProject(newProject);
			projectDetailMainController.setSelectedProject(selectedProject);
		} 
	}

	public void setDataModelProject(DataModelProject projectModel) {
		this.projectModel = projectModel;
	}
	
    public void setDataModelUser(DataModelUser userModel) {
    	this.userModel = userModel;
    }

	private void errorWindow(String message) {
		System.out.println("[controller.PojectDetailController] Print User Error-Message");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Cancel Creating Project?");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	private void confirmClosingCreateProjectWindow() {
		System.out.println("[controller.PojectDetailController] Open Confirm Canceling Creating User Window");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Cancel Creating Project");
		alert.setHeaderText("Canceling: Are you sure?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			System.out.println("[controller.PojectDetailController] Creating Project canceled!");
			projectModel.deleteProject(selectedProject);
			projectController.closeDetailWindow();	
		}
		else if(result.get() == ButtonType.CANCEL) {
			alert.close();
		}
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
		projectDetailMainController.setSelectedProject(selectedProject);	
	}

	private boolean checkIfValuesValid() {
		//if value null, field gets red in ProjectDetailMainController
		if(projectDetailMainController.getprojectStartDate() != null && projectDetailMainController.getprojectFinishDate() != null
				&& projectDetailMainController.getProjectNameTextField() != null) {
			projectStartDateNEW = projectDetailMainController.getprojectStartDate();
			projectFinishDateNEW = projectDetailMainController.getprojectFinishDate();
			projectNameNEW = projectDetailMainController.getProjectNameTextField();
			return true;
		} else {
			return false;
		}
	}

	public boolean checkIfChangesExists() {
		System.out.println("[controller.ProjectDetailController] checkIfChangesExists");
		
		//Values from Main Detail Window
		String projectNameOLD = selectedProject.getProjectName().trim();
		String descriptionOLD = selectedProject.getDescription().trim();
		ProjectStatus projectStatusOLD = selectedProject.getProjectStatus();
		projectStartDateOLD = selectedProject.getStartDate();
		projectFinishDateOLD = selectedProject.getPlanedFinishDate();
		projectMembersListOLD = FXCollections.observableArrayList(selectedProject.getProjectMember());
		
		descriptionNEW = projectDetailMainController.getProjectDescriptionTextField();
		projectStatusNEW = projectDetailMainController.getProjectStatus();
		
		//Values from Team Detail Window
		String projectSponsorOLD = selectedProject.getProjectSponsor();
		projectManagerOLD = selectedProject.getProjectManager();

		//if Team Button was not pressed --> projectDetailTeamController doesn`t exists
		if(projectDetailTeamController != null) {
			projectSponsorNEW = projectDetailTeamController.getProjectSponsor();
			projectManagerNEW = projectDetailTeamController.getProjectManager();
			if(projectDetailTeamController.getProjectMembers() != null) {
				projectMembersListNEW = projectDetailTeamController.getProjectMembers();
			} else {
				projectMembersListNEW = null;
			}
		} else {
			projectSponsorNEW = projectSponsorOLD;
			projectManagerNEW = projectManagerOLD;
			projectMembersListNEW = projectMembersListOLD;
		}
		
		boolean changesOnPRojectManager = false;
		if(projectManagerNEW == projectManagerOLD ||  (projectManagerNEW != null && projectManagerNEW.equals(projectManagerOLD))) {
			changesOnPRojectManager = true;			
		}

		if(projectNameNEW.equals(projectNameOLD) && descriptionNEW.equals(descriptionOLD) 
				&& projectStatusNEW.equals(projectStatusOLD) && projectStartDateNEW.equals(projectStartDateOLD)
				&& projectFinishDateNEW.equals(projectFinishDateOLD) && projectSponsorNEW.equals(projectSponsorOLD)
				&& changesOnPRojectManager && projectMembersListNEW.equals(projectMembersListOLD) ) {
			return false;
		} else {
			return true;
		}
	}
	
	private void updateProject() {
		//set new parameters in selected project
		selectedProject.setDescription(descriptionNEW);
		selectedProject.setProjectName(projectNameNEW);
		selectedProject.setProjectStatus(projectStatusNEW);
		selectedProject.setStartDate(projectStartDateNEW);
		selectedProject.setProjectSponsor(projectSponsorNEW);
		selectedProject.setProjectManager(projectManagerNEW);
		selectedProject.setProjectMembers(projectMembersListNEW);
		selectedProject.setPlanedFinishDate(projectFinishDateNEW);
		//update project in projectModel projectList
		projectModel.updateProject(selectedProject);
	}

	private void confirmClosingProjectDetailWindowChanges() {
		System.out.println("[controller.PojectDetailController] Open Confirm Update Project Window");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Update Project");
		alert.setHeaderText("Update Project: Save changes?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			System.out.println("[controller.PojectDetailController] Update Project Confirm!");
			
			updateProject();
			addProjectToUser();	

			projectController.closeDetailWindow();	
		}
		else if(result.get() == ButtonType.CANCEL) {
			alert.close();
			projectController.closeDetailWindow();	
		}
	}
	
	private void addProjectToUser() {
		for(ProjectUser u : projectMembersListNEW) {
			System.out.println("[controller.PojectDetailController] Update User: " + u.getFirstName() + ", adding Project: " + selectedProject);
			if(!(u.getInvolvedProjects().contains(selectedProject))) {
				u.addProjectToUser(selectedProject);
				userModel.updateUser(u);
			}
		}
	}
}

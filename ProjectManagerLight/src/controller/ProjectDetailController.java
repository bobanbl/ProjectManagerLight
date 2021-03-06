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
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
//import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
	private boolean isNewProject;
	private Project selectedProject;

	//OLD values
	private String projectNameOLD;
	private String descriptionOLD;
	private ProjectStatus projectStatusOLD;
	private String projectSponsorOLD;
	private Date projectStartDateOLD = null;
	private Date projectFinishDateOLD = null;
	private ProjectUser projectManagerOLD = null;
	private ObservableList<ProjectUser> projectMembersListOLD = null;

	//NEW values
	private Date projectStartDateNEW = null;
	private Date projectFinishDateNEW  = null;
	private String projectNameNEW;
	private String descriptionNEW;
	private ProjectStatus projectStatusNEW;
	private String projectSponsorNEW;
	private ProjectUser projectManagerNEW = null;
	private ObservableList<ProjectUser> projectMembersListNEW = null;


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

	/*pressing Create-Button --> set ...NEW attributes from fields,
	 * check if Project-Detail-Team-View was already open, when not these ..NEW attributes are set to "EMPTY"
	 * and calling methods: updateProject and closeDetailWindow in projectController
	 */
	@FXML
	void createProjectButtonPressed(ActionEvent event) {
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
			checkBeforeClosing();
		});
	}

	/*check before closing Detail-View if changes exists
	 *  if YES --> method confirmClosingProjectDetailWindowChanges is called
	 *  if NO --> method closeDetailWindow in projectController is called
	 */
	public void checkBeforeClosing() {
		if(checkIfValuesValid()) {
			if(checkIfChangesExists()) {
				confirmClosingProjectDetailWindowChanges();
			} else {
				projectController.closeDetailWindow();
			}
		}
	}

	//loads the Detail-Main-Window in the AnchorPane of the Detail-Window
	public void loadDetailMainWindow() {
		saveValuesTemporaryTeamWindow();

		try {
			anchorPaneDetailViews.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/projectDetailMain.fxml"));  	
			Parent root = loader.load();

			projectDetailMainController = loader.getController();
			projectDetailMainController.setProjectDetailController(this);
			projectDetailMainController.setSelectedProject(selectedProject);

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
			loader.setLocation(getClass().getResource("/view/projectDetailTeam.fxml"));  	
			Parent root = loader.load();
			projectDetailTeamController = loader.getController();

			projectDetailTeamController.setSelectedProject(selectedProject);
			projectDetailTeamController.setProjectDetailController(this);
			projectDetailTeamController.setDataModelUser(userModel);

			projectDetailTeamController.setUserListFromModel();

			anchorPaneDetailViews.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  

	//sets the values from the Detail-Main-View in the object selectedProject
	private void saveValuesTemporaryMainWindow() {
		projectNameNEW = projectDetailMainController.getProjectNameTextField();
		descriptionNEW= projectDetailMainController.getProjectDescriptionTextField();
		projectStatusNEW = projectDetailMainController.getProjectStatus();
		projectStartDateNEW = projectDetailMainController.getprojectStartDate();
		projectFinishDateNEW = projectDetailMainController.getprojectFinishDate();
		selectedProject.setProjectName(projectNameNEW);
		selectedProject.setDescription(descriptionNEW);
		selectedProject.setProjectStatus(projectStatusNEW);
		selectedProject.setStartDate(projectStartDateNEW);
		selectedProject.setPlanedFinishDate(projectFinishDateNEW);
	}

	//sets the values from the Detail-Team-View in the object selectedProject
	private void saveValuesTemporaryTeamWindow() {
		projectSponsorNEW = projectDetailTeamController.getProjectSponsor();
		projectManagerNEW = projectDetailTeamController.getProjectManager();
		projectMembersListNEW = projectDetailTeamController.getProjectMembers();
		selectedProject.setProjectSponsor(projectSponsorNEW);
		selectedProject.setProjectManager(projectManagerNEW);
		selectedProject.setProjectMembers(projectMembersListNEW);
	}

	//sets the ProjectController projectController    
	public void setProjectController(ProjectController controller) {
		this.projectController = controller;
		isNewProject = projectController.getIfNewProject();
		System.out.println("[controller.ProjectDetailController] New Project: " + isNewProject);

		if(isNewProject) {
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

	//sets the attribute projectModel
	public void setDataModelProject(DataModelProject projectModel) {
		this.projectModel = projectModel;
	}

	//sets the attribute userModel
	public void setDataModelUser(DataModelUser userModel) {
		this.userModel = userModel;
	}
	
	//opens Confirm-Window to confirm or cancel changes
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

	//sets the attribute selectedProject and the ...OLD values from the selected project
	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
		projectDetailMainController.setSelectedProject(selectedProject);
		//Values from Main Detail Window
		projectNameOLD = selectedProject.getProjectName().trim();
		descriptionOLD = selectedProject.getDescription().trim();
		projectStatusOLD = selectedProject.getProjectStatus();
		projectStartDateOLD = selectedProject.getStartDate();
		projectFinishDateOLD = selectedProject.getPlanedFinishDate();
		if(selectedProject.getProjectMember() != null) {
			projectMembersListOLD = FXCollections.observableArrayList(selectedProject.getProjectMember());
		} else {
			projectMembersListOLD = null;
		}
		//Values from Team Detail Window
		projectSponsorOLD = selectedProject.getProjectSponsor();
		projectManagerOLD = selectedProject.getProjectManager();
	}

	//returns TRUE when values from fields are valid
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

	//returns TRUE when changes exist
	public boolean checkIfChangesExists() {
		System.out.println("[controller.ProjectDetailController] checkIfChangesExists");

		descriptionNEW = projectDetailMainController.getProjectDescriptionTextField();
		projectStatusNEW = projectDetailMainController.getProjectStatus();

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

		boolean changesOnProjectManager = false;
		if(projectManagerNEW == projectManagerOLD ||  (projectManagerNEW != null && projectManagerNEW.equals(projectManagerOLD))) {
			changesOnProjectManager = true;			
		}

		if(projectNameNEW.equals(projectNameOLD) && descriptionNEW.equals(descriptionOLD) 
				&& projectStatusNEW.equals(projectStatusOLD) && projectStartDateNEW.equals(projectStartDateOLD)
				&& projectFinishDateNEW.equals(projectFinishDateOLD) && projectSponsorNEW.equals(projectSponsorOLD)
				&& changesOnProjectManager && projectMembersListNEW.equals(projectMembersListOLD) ) {
			return false;
		} else {
			return true;
		}
	}

	//updates the selected project with the ...NEW values and calls the update method in the projectModel
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

	//opens the Confirm-Window for confirming if changes should be saved 
	private void confirmClosingProjectDetailWindowChanges() {
		System.out.println("[controller.PojectDetailController] Open Confirm Update Project Window");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Update Project");
		alert.setHeaderText("Update Project: Save changes?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			System.out.println("[controller.PojectDetailController] Update Project Confirm!");

			updateProject();
			addProjectToUsers();	
			removeProjectFromUsers();

			projectController.closeDetailWindow();	
		}
		else if(result.get() == ButtonType.CANCEL) {
			alert.close();
			projectController.closeDetailWindow();	
		}
	}

	//adding OrijectUser to ProjectMemberList
	private void addProjectToUsers() {
		for(ProjectUser u : projectMembersListNEW) {
			System.out.println("[controller.PojectDetailController] Update User: " + u.getFirstName() + ", adding Project: " + selectedProject);
			if(!(u.getInvolvedProjects().contains(selectedProject))) {
				u.addProjectToUser(selectedProject);
				userModel.updateUser(u);
			}
		}
	}

	//remove project from ProjectUser
	private void removeProjectFromUsers() {
		if(projectMembersListOLD != null) {
			for(ProjectUser userOLD: projectMembersListOLD) {
				if(!projectMembersListNEW.contains(userOLD)) {
					userOLD.removeProjectFromUser(selectedProject);
					userModel.updateUser(userOLD);
				}
			}
		}
	}
}

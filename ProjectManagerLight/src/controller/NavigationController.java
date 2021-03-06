package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DataModelUser;
import model.DataModelProject;
import model.DataModelStory;
import model.Project;
import model.ProjectUser;

//Controller for main.fxml
public class NavigationController {

	private DataModelUser userModel;
	private DataModelStory storyModel;
	private DataModelProject projectModel;
	private Project selectedProject;

	@FXML
	ProjectController projectController;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private ImageView navigationButtonUserManView;
	@FXML
	private Label mainSelectedProject;
	@FXML
	private ImageView navigationButtonTaskView;
	@FXML
	private VBox mainNavigationVBox;
	@FXML
	private ImageView navigationButtonDashboardView;  
	@FXML
	private AnchorPane anchorPaneViews; 
	@FXML
	private Label labelSelectedView;
	@FXML
	private Label labelLoggedUser;

	@FXML
	void initialize() {
		/*when initializing, the instance is hand over to the object ProjectDetailMainController
		 * in the FXML was the "fx:id="projectController"" was included
		 * and in this Controller "@FXML	ProjectController projectController;" was included
		 */
		projectController.setNavigationController(this);

		labelSelectedView.setText("Project Dashboard");
		//click on Navigation Button Dashboard View calls method loadProjectView()    	
		navigationButtonDashboardView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("Project Dashboard Navigation Button pressed");
			if(projectController != null) {
				projectController.checkForChangesInProjectDetail();
			}
			loadProjectView();
		});
		//click on Navigation Button Task View calls method loadTaskView()        	
		navigationButtonTaskView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("Task Navigation Button pressed");
			if(projectController != null) {
				projectController.checkForChangesInProjectDetail();
			}
			if(selectedProject != null) {
				loadTaskView();	
			} else {
				errorWindow("No Project selected!");
			}
		});
		//click on Navigation Button User Management calls method loadUserManView()        	
		navigationButtonUserManView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("User Man. Navigation Button pressed");
			if(projectController != null) {
				projectController.checkForChangesInProjectDetail();
			}
			loadUserManView();
		});   	
	}

	/*sets attributes for models and calling the 
	 * calling the methods setDataModelProject and setDataModelUser for the projectController, 
	 * bc of implemented projectController
	 */
	public void setModels(DataModelUser userModel, DataModelStory storyModel, DataModelProject projectModel) {
		this.userModel = userModel;
		this.storyModel = storyModel;
		this.projectModel = projectModel;
		projectController.setDataModelProject(projectModel);
		projectController.setDataModelUser(userModel);
	}

	/*sets the selected project in the attribute and in the text-field: mainSelectedProject
	 * if selected project is null --> "---NO PROJECT SELECTED--" in text field
	 */
	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
		if(selectedProject == null) {
			mainSelectedProject.setText("---NO PROJECT SELECTED--");
		} else {
			mainSelectedProject.setText(selectedProject.getProjectName());
		}
	}

	//returns the selectedProject
	public Project getSelectedProject() {
		return selectedProject;
	}

	//return the attribute projectModel
	public DataModelProject getDataModelProject() {
		return this.projectModel;
	}

	/* Load in AnchorPane Task View
	Set label(selected View) on "Tasks"
	 */
	public void loadTaskView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/taskView.fxml"));  	
			Parent root = loader.load();
			root.getStylesheets().add("application/application.css");

			TaskController taskController =  loader.getController();
			taskController.setDataModelStory(storyModel);
			taskController.setNavigationController(this);
			taskController.setSelectedProject(selectedProject);

			anchorPaneViews.getChildren().setAll(root);
			labelSelectedView.setText("Tasks");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//load in AnchorPane User Management View
	private void loadUserManView() {
		try {
			FXMLLoader loader = new FXMLLoader();    	
			loader.setLocation(getClass().getResource("/view/userManView.fxml"));  

			Parent root = loader.load();

			UserManController userManController =  loader.getController();
			userManController.setDataModel(userModel);
			userManController.setProjectModel(projectModel);
			userManController.setStoryModel(storyModel);

			anchorPaneViews.getChildren().setAll(root);			
			labelSelectedView.setText("User Management");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}    

	//load in AnchorPane Project View    
	public void loadProjectView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/projectView.fxml"));  	
			Parent root = loader.load();

			ProjectController projectController =  loader.getController();
			projectController.setNavigationController(this);
			projectController.setDataModelProject(projectModel);
			projectController.setDataModelUser(userModel);

			anchorPaneViews.getChildren().setAll(root);	
			labelSelectedView.setText("Project Dashboard");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}   

	//sets the label "labelLoggedUser" with "Logged User: " and the given String
	public void setLabelLoggedUser(String loggedUser) {
		for(ProjectUser u : userModel.getUserList()) {
			if(u.getUserShortcut().equals(loggedUser)) {
				labelLoggedUser.setText("Logged User: " + u.getFirstName() + " " + u.getLastName());
			}
		}
	}

	//Alert-Pop-Up-Window with the given String as message
	private void errorWindow(String message) {
		System.out.println("Print User Error-Message");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Opening Task View Failed!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}


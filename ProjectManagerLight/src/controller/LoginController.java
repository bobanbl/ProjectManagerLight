package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.DataModelUser;
import model.DataModelProject;
import model.DataModelStory;
import javafx.event.ActionEvent;
//LoginController for login.fxml
public class LoginController {

	private NavigationController navigationController;
	private String loggedUser;
	private String loggedPassword;
	private DataModelUser userModel;
	private DataModelStory storyModel;
	private DataModelProject projectModel;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextField loginUsername;
	@FXML
	private Button loginButton;
	@FXML
	private PasswordField loginPassword;
	@FXML
	private Label loginLabel;

	//pressing "Assume"-Button --> method from the DatabaseController will be called
	@FXML
	void buttonPressed(ActionEvent event) {
		System.out.println("Button was pressed!");
		loginUser();
	}
	
	@FXML
	void initialize() {
		//if in text field loginUsername Enter is pressed -> cursor jumps in password field    	
		loginUsername.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if(event.getCode() == KeyCode.ENTER) {
				System.out.println("[controller.LoginController] LoginUsename Enter pressed");
				loginPassword.requestFocus();
			}
		});
		//if ENTER is pressed while in loginPassword-Field 
		loginPassword.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if(event.getCode() == KeyCode.ENTER) {
				loginUser();
			}
		});    		
	}

	//attributes for models are set
	public void setModels(DataModelUser userModel, DataModelStory storyModel, DataModelProject projectModel) {
		this.userModel = userModel;
		this.storyModel = storyModel;
		this.projectModel = projectModel;
	}

	//closes the Login-Window and opens the Main-Window (main.fxml)        
	private void loginUser() {
		loggedUser = loginUsername.getText().trim();
		loggedPassword = loginPassword.getText().trim();
		int loginResult = userModel.verifyLoginData(loggedUser, loggedPassword);
		switch(loginResult) {
		case 1:		openMainWindow();
		break;
		case 2: 	System.out.println("Login failed");
		errorWindow("Wrong password!");
		break;
		default: 	System.out.println("User does not exist");
		errorWindow("User " + loggedUser + " does not exist");
		break;
		} 
	}
	
	//closing Login Window and opening the Main Window 
	private void openMainWindow(){
		try {
			loginButton.getScene().getWindow().hide();	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/main.fxml"));  	
			Parent root = loader.load();

			this.navigationController =  loader.getController();
			this.navigationController.setModels(userModel, storyModel, projectModel);
			this.navigationController.setLabelLoggedUser(loggedUser.toLowerCase());

			Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));
			Stage mainStage = new Stage();

			mainStage.setTitle("ProjectManagerLight");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	/** Error Window 
	 * 
	 * @param message: Text which appears in message
	 */
	private void errorWindow(String message) {
		System.out.println("Print Message");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Login Failed");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}


package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.DataModelUser;
import model.ProjectUser;

//The Controller for userDetailPopUp.fxml
public class UserDetailController {

	private DataModelUser model;
	private UserManController userManController;
	private ProjectUser selectedUser;

	//attributes for text-fields
	private String shortcut;
	private String eMail;
	private String firstName;
	private String lastName;
	private String role;
	private String password;

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextField userDetailFirstNameField;
	@FXML
	private PasswordField userDetailPasswordField;
	@FXML
	private TextField userDetailLastNameField;
	@FXML
	private TextField userDetailEmailField;
	@FXML
	private TextField userDetailRoleField;
	@FXML
	private Button userDetailAssumeButton;   
	@FXML
	private TextField userDetailShortcutField;

	/* By pressing the "Assume"-Button the text fields where evaluating
	 *  and if ok --> calling the createUser method in DataModel and closing the windows
	 */
	@FXML
	void userDetailAssumeButtonPressed(ActionEvent event) {	
		shortcut = userDetailShortcutField.getText().trim().toLowerCase();
		eMail = userDetailEmailField.getText().trim().toLowerCase();
		firstName = userDetailFirstNameField.getText().trim();
		lastName = userDetailLastNameField.getText().trim();
		role = userDetailRoleField.getText().trim();
		password = userDetailPasswordField.getText().trim();

		if(evaluateuserDetailShortcutField(shortcut)) {
			errorWindow("User-Shortcut already exists");
		} else if(!validEMail(eMail)) {
			errorWindow("eMail has false syntax!");
		} else if(shortcut.equals("") || eMail.equals("") || firstName.equals("") || lastName.equals("") || role.equals("") || password.equals("")) {
			errorWindow("Empty field!");
		} else if(selectedUser == null){
			createNewUser();
		} else if(selectedUser != null){
			updateSelectedUser();
		}
		userManController.closePopUpWindow();
	}

	/*update selected ProjectUser with values from attributes (text-fields)
	 * and updating the selected ProjectUser in the userModel
	 */
	private void updateSelectedUser() {
		selectedUser.setFirstName(firstName);
		selectedUser.setLastName(lastName);
		selectedUser.seteMail(eMail);
		selectedUser.setUserShortcut(shortcut);
		selectedUser.setPassword(password);
		selectedUser.setRole(role);
		model.updateUser(selectedUser);
	}

	//create new ProjectUser with attributes and call createUser-method in userModel
	private void createNewUser() {
		ProjectUser newUser = new ProjectUser();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.seteMail(eMail);
		newUser.setUserShortcut(shortcut);
		newUser.setPassword(password);
		newUser.setRole(role);
		model.createUser(newUser);
	}

	@FXML
	void initialize() {
		//pressing Enter-Button in the userDetailShortcutField calls method evaluateuserDetailShortcutField
		userDetailShortcutField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if(event.getCode() == KeyCode.ENTER) {
				evaluateuserDetailShortcutField(userDetailShortcutField.getText().trim());
			}
		}); 

		//when Mouse exits userDetailShortcutField --> calling method evaluateuserDetailShortcutField
		userDetailShortcutField.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
			evaluateuserDetailShortcutField(userDetailShortcutField.getText().trim());
		}); 

		//pressing Enter-Button in the userDetailEmailField calls method validEMail
		userDetailEmailField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if(event.getCode() == KeyCode.ENTER) {
				validEMail(userDetailEmailField.getText().trim());
			}
		}); 

		//when Mouse exits userDetailEmailField --> calling method validEMail
		userDetailEmailField.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
			if(userDetailEmailField.getText().trim().indexOf("") != 0) {
				validEMail(userDetailEmailField.getText().trim());
			}
		});    		   		
	}

	//setting userModel method
	public void setDataModel(DataModelUser model) {
		this.model = model;
	}

	//setting userManController method
	public void setUserManController(UserManController controller) {
		this.userManController = controller;
	}

	/*setting selectedUser method
	 * if selectedUser not null, means that details from selected ProjectUser are set in the text-fields
	 */
	public void setSelectedUser(ProjectUser selectedUser) {
		this.selectedUser = selectedUser;
		if(selectedUser != null) {
			userDetailShortcutField.setText(selectedUser.getUserShortcut());
			userDetailEmailField.setText(selectedUser.geteMail());
			userDetailFirstNameField.setText(selectedUser.getFirstName());
			userDetailLastNameField.setText(selectedUser.getLastName());
			userDetailRoleField.setText(selectedUser.getRole());
			userDetailPasswordField.setText(selectedUser.getPassword());    
		}
	}

	//return true is shortcut does NOT exist!! because this the 'everything is fine' case  
	private boolean evaluateuserDetailShortcutField(String shortcut) {
		for(ProjectUser u : model.getUserList()) {
			//existingUserShortcutCheck is TRUE if the shortcut equals the shortcut of the selected user
			boolean existingUserShortcutCheck = true;
			if(selectedUser != null) {
				if(u.getUserShortcut().equals(selectedUser.getUserShortcut())){
					existingUserShortcutCheck = false;
				}
			}
			if(u.getUserShortcut().toLowerCase().contains(shortcut.toLowerCase()) && existingUserShortcutCheck && !userDetailShortcutField.getText().equals("")){
				System.out.println("User-Shortcut already exists");
				userDetailShortcutField.setStyle("-fx-control-inner-background: #FF0000");
				return true;    
			}	
		}

		System.out.println("User-Shortcut is free");
		userDetailShortcutField.setStyle("-fx-control-inner-background: #FFFFFF");
		return false;


	}

	/*returns true if eMail has right syntax, otherwise false
	 * background of userDetailEmailField turns RED if false if false syntax
	 * otherwise white
	 */
	private boolean validEMail(String eMail) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
				"[a-zA-Z0-9_+&*-]+)*@" + 
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
				"A-Z]{2,7}$"; 

		Pattern pat = Pattern.compile(emailRegex); 
		boolean result = pat.matcher(eMail).matches();
		if (eMail == null | result == false) {
			System.out.println("eMail has false syntax!");
			userDetailEmailField.setStyle("-fx-control-inner-background: #FF0000");
			return false;
		} else {
			System.out.println("eMail has right syntax");
			userDetailEmailField.setStyle("-fx-control-inner-background: #FFFFFF");
			return true;
		}
	}

	//Error-Pop-Up Window 
	private void errorWindow(String message) {
		System.out.println("Print User Error-Message");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}

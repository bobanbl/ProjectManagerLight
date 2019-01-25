package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class LoginController {
	
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

    @FXML
    void buttonPressed(ActionEvent event) {
    	System.out.println("Button was pressed!");
    	loginUser();
//    	String loginName = loginUsername.getText().trim();
//    	String loginPswd = loginPassword.getText().trim();
//    	if(!loginName.equals("") || !loginPswd.equals("")) {
//    		DatabaseController dbController = new DatabaseController();
//    		if (dbController.userLoginQuery(loginName, loginPswd)) {
//    			loginUser();
//    		}
//    	} else {
//    		System.out.println("Empty field!");
//    	}
    }

    @FXML
    void initialize() {

    }
    
    private void loginUser() {
    	System.out.println("LOGIN successfull!");
    	
		try {
    	loginButton.getScene().getWindow().hide();
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../view/main.fxml"));
		Parent root = loader.load();
		NavigationController controller = loader.getController();
		controller.getClass();
    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));
    	Stage mainStage = new Stage();
    	mainStage.setTitle("Main");
    	mainStage.setScene(scene);
    	mainStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}	
    }

}


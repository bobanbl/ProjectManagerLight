package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

//The Controller for projectView.fxml
public class ProjectController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addProjectButton;
 
/** Adds a project in TableView
	TODO coding
*/
    @FXML
    void addProjectImageButtonPressed(ActionEvent event) {
    	System.out.println("Add Project Button pressed!");
    }

    @FXML
    void initialize() {

    }
}

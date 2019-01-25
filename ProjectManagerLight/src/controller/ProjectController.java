package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ProjectController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addProjectButton;

    @FXML
    private VBox projectVBox;

    @FXML
    void addProjectButtonPressed(ActionEvent event) {
    	System.out.println("Add Project Button pressed!");
    }
//    @FXML
//    void 00ff5e(ActionEvent event) {
//
//    }

    @FXML
    void initialize() {

    }
}

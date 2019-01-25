package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavigationController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button navigationButtonUserManView;

    @FXML
    private Label mainSelectedProject;

    @FXML
    private Button navigationButtonTaskView;

    @FXML
    private VBox mainNavigationVBox;

    @FXML
    private Button navigationButtonDashboardView;

    @FXML
    void navigationButtonDashboardViewPressed(ActionEvent event) {

    }

    @FXML
    void navigationButtonTaskViewPressed(ActionEvent event) {

    }

    @FXML
    void navigationButtonUserManViewPressed(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

	
//    private void loadProjectDashboardView() {
//    	System.out.println("Loading Project-DashboardView");
//    	
//		try {
//   	
//    	FXMLLoader loader = new FXMLLoader();
//    	loader.setLocation(getClass().getResource("../view/projectView.fxml"));
//		Parent root = loader.load();
//		NavigationController controller = loader.getController();
//		controller.getClass();
//    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));
//
////    	mainStage.setScene(scene);
////    	mainStage.show();
//
//		} catch(Exception e) {
//			e.printStackTrace();
//		}	
//    }

}


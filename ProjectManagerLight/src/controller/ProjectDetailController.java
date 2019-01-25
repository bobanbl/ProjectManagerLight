package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//The Controller for projectDetail.fxml
public class ProjectDetailController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPaneDetailViews;

    @FXML
    private ImageView closeDetailViewButton;

    @FXML
    void initialize() {
    	loadDetailMainWindow();    	
    	
    	closeDetailViewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Close-Detail-Window-Button pressed");
    		//TODO get instance from ProjcetController and close Detail-Window with clsoeDetailWindow method
//    		closeDetailWindow();		
    	});
    }
    
    private void loadDetailMainWindow() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/projectDetailMain.fxml"));  	
			Parent root = loader.load();
			anchorPaneDetailViews.getChildren().setAll(root);
			
	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

}

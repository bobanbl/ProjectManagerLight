package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataModel;
import model.DataModelStory;

//Controller for taskView.fxml
public class TaskController {
	
	private Stage popUpWindow;
	private DataModelStory storyModel;

    @FXML
    private GridPane gridPane;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ImageView addStoryButton;
    @FXML
    void initialize() {
    	addStoryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("[controller.TaskController] Add-Story-Button pressed");
    		laodCreateStoryPopUp();		
    	});
    }
//opens taskDetailPopUp.fxml in new window
    private void laodCreateStoryPopUp() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/storyCreatePopUp.fxml"));  	
			Parent root = loader.load();	
			
			StoryCreateController storyCreateController =  loader.getController();
			storyCreateController.setDataModelStory(storyModel);
			storyCreateController.setTaskController(this);
			
			
	    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));    	
	    	popUpWindow = new Stage();
	    	
	    	popUpWindow.setTitle("Create Story");
	    	popUpWindow.setScene(scene);
	    	popUpWindow.showAndWait();
	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }  
    
    public void setDataModelStory(DataModelStory storyModel) {
    	this.storyModel = storyModel;
    }
    
    public void closePopUpWindow() {
    	popUpWindow.close();
    }
    
    public void loadStoryDataForProject() {
    	
    }
    
    public void createNewStory(String storyName, int duration) {
    	Label storyNameLabel = new Label();
    	storyNameLabel.setText(storyName);
    	storyNameLabel.setTextFill(Color.WHITE);
    	storyNameLabel.setFont(new Font("Arial", 30));

    	Label storyDurationLabel = new Label();
    	storyDurationLabel.setText(String.valueOf(duration));
    	storyDurationLabel.setTextFill(Color.WHITE);
    	storyDurationLabel.setFont(new Font("Arial", 20));
    	storyDurationLabel.setAlignment(Pos.CENTER_RIGHT);
//    	storyDurationLabel.
    	
    	VBox vbox = new VBox(storyNameLabel, storyDurationLabel);
    	vbox.getStyleClass().add("vbox");
    	gridPane.addRow(1, vbox);
//    	gridPane.add(vbox, 0, 1);
    	
    }
    
    
}

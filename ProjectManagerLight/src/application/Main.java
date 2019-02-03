package application;
	
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DataModel;
/** This is the main Class from the tool "ProjectManagerLight" which opens the Login Window
@author Boblaz
@version 0.3 <I>Java Project 2019</I>
*/

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/login.fxml"));
			Parent root = loader.load();
			
			LoginController loginController = loader.getController();
			DataModel model = new DataModel();
			loginController.setDataModel(model);
			
			Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Login");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
//    public static void delay() {
//    	try        
//    	{	
//    		Thread.sleep(20000);
//    	} 
//    	catch(InterruptedException ex) 
//    	{
//    		Thread.currentThread().interrupt();
//    	}	
//    }
}

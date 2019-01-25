package application;
	
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/login.fxml"));
			Parent root = loader.load();
			LoginController controller = loader.getController();
			controller.getClass();
			
//			Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
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
}

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.derby.iapi.sql.dictionary.UserDescriptor;

import database.ProjectUser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import model.DataModel;

//The Controller for userManView.fxml
public class UserManController {
	
	private DataModel model;
	ObservableList<ProjectUser> selectedUserList;
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<ProjectUser, String> colProjects;
    @FXML
    private TableColumn<ProjectUser, String> colLastName;
    @FXML
    private ImageView addUserButton;
    @FXML
    private TableView<ProjectUser> userTable;
    @FXML
    private TableColumn<ProjectUser, String> colRole;
    @FXML
    private TableColumn<ProjectUser, String> colShortcut;
    @FXML
    private TableColumn<ProjectUser, String> colFirstName;
    @FXML
    private TableColumn<ProjectUser, String> colEmail;
    
    private Stage popUpWindow;
    
    
    
    @FXML
    void initialize() {
//Method loadUserDetailPopUp() is called, when addUserButton is pressed
    	addUserButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Add-User-Button pressed");
    		laodUserCreatePopUp();
    	});
    	
    	tablesChanges();
    	
    }
//opens User Detail Pop Up Window - Create User    
    private void laodUserCreatePopUp() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("../view/userCreatePopUp.fxml"));  	
			Parent root = loader.load();
			
			UserCreateController userCreateController =  loader.getController();
			userCreateController.setDataModel(model);
			userCreateController.setUserManController(this);
			
	    	Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));	
	    	popUpWindow = new Stage();
	    	
	    	popUpWindow.setTitle("Create User");
	    	popUpWindow.setScene(scene);
	    	popUpWindow.showAndWait();	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void setDataModel(DataModel model) {
    	this.model = model;
    	System.out.println("opened: " + this);
    	initializeTable();
    }
    
    private void initializeTable() {
    	System.out.println("initialize table view");
    	System.out.println(model);
    	
    	userTable.setItems(model.getUserList());
//    	userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	userTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    	colShortcut.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("userShortcut"));
    	colFirstName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("firstName"));
    	colLastName.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("lastName"));
    	colEmail.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("eMail"));
    	colRole.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("role"));
//    	colProjects.setCellValueFactory(new PropertyValueFactory<ProjectUser, String>("projects"));
    }
//Close Pop-up window - Create User   
    public void closePopUpWindow() {
    	popUpWindow.close();
    }
    
    public void tablesChanges() {
//    	userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProjectUser>() {
//
//    		@Override
//    		public void changed(ObservableValue<? extends ProjectUser> observable, ProjectUser oldValue, ProjectUser newValue) {
//    			laodUserDetailPopUp1(newValue);
//    		}
//    	});
//    	
    	userTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		selectedUserList = userTable.getSelectionModel().getSelectedItems();
    		System.out.println(selectedUserList.get(0).getLastName());
    		if (event.getClickCount() == 2) {
    			laodUserDetailPopUp();
    		}
    		if (event.getButton() == MouseButton.SECONDARY && selectedUserList.get(0) != null) {
    			System.out.println("Right Mouse Button clicked");
    			openContextMenu();
    		}
    	});
    	
//    	userTable.getSelectionModel().selectedItemProperty().
//    	setRowFactory(tv -> {
//            TableRow<ProjectUser> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
//                	ProjectUser rowData = row.getItem();
//                    System.out.println("Double click on: ");
//                }
//            });
//    	}
            
    }
    
    //opens User Detail Pop Up Window - User Details  
    private void laodUserDetailPopUp() {
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("../view/userDetailPopUp.fxml"));  	
    		Parent root = loader.load();

    		UserDetailController userDetailController =  loader.getController();
    		userDetailController.setDataModel(model);
    		userDetailController.setUserManController(this);
    		userDetailController.setSelectedUser(selectedUserList.get(0));

    		Scene scene = new Scene(root, root.minWidth(0), root.minHeight(0));	
    		popUpWindow = new Stage();

    		popUpWindow.setTitle("User Detail");
    		popUpWindow.setScene(scene);
    		popUpWindow.showAndWait();	    	
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    } 
     
    
    private void openContextMenu() {
    	Label label = new Label();
    	ContextMenu contextMenu = new ContextMenu();

    	MenuItem item1 = new MenuItem("Delete User: " + selectedUserList.get(0).getUserShortcut());
    	item1.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent event) {
    			label.setText("Select Menu Item 1");
    			confirmDeletingUserWindow();
    		}
    		
    	});	
    	contextMenu.getItems().addAll(item1);
    	userTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(userTable, event.getScreenX(), event.getScreenY());
            }
        });

    	userTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		System.out.println("Any button pressed hide context");
    		contextMenu.hide();
    	}); 
    }
    
    private void confirmDeletingUserWindow() {
    	System.out.println("Print User Error-Message");
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Delete User");
    	alert.setHeaderText("Deleting user: " + selectedUserList.get(0).getUserShortcut() + " Are you sure?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		System.out.println("[UserManController] User deleted!");
    		model.deleteUser(selectedUserList.get(0));	
    	}
    	else if(result.get() == ButtonType.CANCEL) {
    	    alert.close();
    	}
    }   

}

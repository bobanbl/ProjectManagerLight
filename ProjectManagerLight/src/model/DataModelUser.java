package model;

import java.util.List;
import database.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
/**loads the data from the Entity ProjectUser into a ObservableList from the Database
 * over the DatabaseController
 * Methods: CRUD
 * @author blazebo
 *
 */
public class DataModelUser {

	private final DatabaseController database = DatabaseController.getInstance();
	
    private ObservableList<ProjectUser> userList;
	
    public DataModelUser() {
		loadUserData();
		printUserData();
		
		userList.addListener(new ListChangeListener<ProjectUser>() {

			@Override
			public void onChanged(Change<? extends ProjectUser> c) {
				c.next();

				if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                         System.err.println("[model.DataModelUser] Permutation not implemented");
                    }
                } else if (c.wasReplaced()) {
                	for (int i = c.getFrom(); i < c.getTo(); ++i) {
                		database.updateUser(userList.get(i));
                   }
                } else if (c.wasRemoved()){
                	for (ProjectUser u : c.getRemoved()) {
                		System.out.println("Test");
                		database.deleteUser(u);
                	}
                } else {
                    for (ProjectUser u : c.getAddedSubList()) {	
                        database.createUser(u);
                    }
                }
			}
		});

    }
  
    public void loadUserData() {
    	List<ProjectUser> list = database.readAllUser();
		userList = FXCollections.observableArrayList(list);	
    }   
    
    public void printUserData() {
    	for(ProjectUser u : userList) {
    		System.out.println("[model.DataModelUser] printUserData: " + u.toString());
    	}
    }
    
    public ObservableList<ProjectUser> getUserList(){
    	return userList;
    }
    
    /** proves if loginShortcut exists with associated password in userList 
     * 
     * @param loginShortcut is not case sensitive
     * @param password
     * @return
     * returns 1 when loginShortcut and password are correct in database
     * returns 2 if password does not match to user
     * returns 3 if user does not exist in database
     */    
    public int getLoginData(String loginShortcut, String password) {
    	System.out.println("[model.DataModelUser] Start Query Shortcut and Password in DataModel");
    	for(ProjectUser u : userList) {
    		if(u.getUserShortcut().equals(loginShortcut)) {
    			if(u.getPassword().equals(password)) {
    				System.out.println("[model.DataModelUser] Password ok");
    				return 1;
    			} else {
    				System.out.println("[model.DataModelUser] Password not ok");
    				return 2;       				
    			}
    		}
    	}
    	System.out.println("[model.DataModelUser] User: " + loginShortcut + " does not exist");
    	return 3;
    }

    //creates new user and calls the method printUserData
    public void createUser(ProjectUser newUser) {
    	System.out.println("[model.DataModelUser] Adding new User to List");
    	userList.add(newUser);
    	printUserData();    	
    }
    
    /** Deleting existing ProjectUser in userList
     * 
     * @param deleteUser 
     */
    public void deleteUser(ProjectUser deleteUser) {
    	System.out.println("[model.DataModelUser] Deleting user");
    	userList.remove(deleteUser);
    	printUserData();
    }

    //Update existing projectUser in userList
    public void updateUser(ProjectUser updateUser) {
    	System.out.println("[model.DataModelUser] Update user: " + updateUser.getUserShortcut() + " " + updateUser);
    	int userIndex = userList.indexOf(updateUser);
		userList.set(userIndex, updateUser);
    	printUserData();
    }  
}

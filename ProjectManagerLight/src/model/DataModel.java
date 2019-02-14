package model;

import java.util.List;
import java.util.Optional;

import database.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class DataModel {

	private final DatabaseController database = DatabaseController.getInstance();
	
    private ObservableList<ProjectUser> userList;
//    private ObservableList<Object> projectList;
	
    public DataModel() {
		loadUserData();
		printUserData();
		
		userList.addListener(new ListChangeListener<ProjectUser>() {

			@Override
			public void onChanged(Change<? extends ProjectUser> c) {
				c.next();

				if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                         System.err.println("Permutation not implemented");
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
		
//		loadAllData();
//		printAllData();
    }
//------------------ProjectUser--------------------------------------    
    public void loadUserData() {
    	List<ProjectUser> list = database.readAllUser();
		userList = FXCollections.observableArrayList(list);	
    }   
    
    public void printUserData() {
    	for(ProjectUser u : userList) {
    		System.out.println("[model.DataModel]" + u.toString());
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
    	System.out.println("Start Query Shortcut and Password in DataModel");
    	for(ProjectUser u : userList) {
    		if(u.getUserShortcut().equals(loginShortcut)) {
    			if(u.getPassword().equals(password)) {
    				System.out.println("[DataModel] Password ok");
    				return 1;
    			} else {
    				System.out.println("[DataModel] Password not ok");
    				return 2;       				
    			}
    		}
    	}
    	System.out.println("[model.DataModel] User: " + loginShortcut + " does not exist");
    	return 3;
    }
    
    /** proves is userShortcut already exists in userList
     * 	
     * @param shortcut
     * @return true...userShortcut exists in userList false...userShortcut does not exist in userList
     */
    public boolean userShortcutExistis(String shortcut) {
    	for(ProjectUser u : userList) {
    		if(u.getUserShortcut().toLowerCase().equals(shortcut.toLowerCase())) {
    			return true;
    		}
    	}
    	return false;
    }
    /* Creating new ProjectUser and adding to userList
     *     
    */
    public void createUser(String userShortcut, String firstName, String lastName, String eMail, String role, String password) {
    	System.out.println("[model.DataModel] Adding new User to List");
    	ProjectUser newUser = new ProjectUser();
    	newUser.setFirstName(firstName);
    	newUser.setLastName(lastName);
    	newUser.seteMail(eMail);
    	newUser.setUserShortcut(userShortcut);
		newUser.setPassword(password);
		newUser.setRole(role);
    	userList.add(newUser);
    	printUserData();    	
    }
    /** Deleting existing ProjectUser in userList
     * 
     * @param deleteUser 
     */
    public void deleteUser(ProjectUser deleteUser) {
    	System.out.println("[DataModel] Deleting user");
    	userList.remove(deleteUser);
    	printUserData();
    }

    /** Update existing projectUser in userList
     * 
     * @param updateUser	Object: existing User
     * @param userShortcut	
     * @param firstName
     * @param lastName
     * @param eMail
     * @param role
     * @param password
     */
    public void updateUser(ProjectUser updateUser, String userShortcut, String firstName, String lastName, String eMail, String role, String password) {
    	System.out.println("[DataModel] Update user" + updateUser.getUserShortcut());
    	int userIndex = userList.indexOf(updateUser);
    	updateUser.setFirstName(firstName);
    	updateUser.setLastName(lastName);
    	updateUser.seteMail(eMail);
    	updateUser.setUserShortcut(userShortcut);
		updateUser.setPassword(password);
		updateUser.setRole(role);
		userList.set(userIndex, updateUser);
    	printUserData();
    }
    
    
    


    
    

//    public void loadAllData() {
//    	List<Object> projectList = database.readAllData();
//		projectList = FXCollections.observableArrayList(projectList);	
//    }   
//    
//    public void printAllData() {
//    	for(Object u : projectList) {
//    		if(u != null) {
//    			System.out.println(u.getClass());
//    		}
//    	}
//    }
    
    
	
}

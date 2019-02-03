package model;

import java.util.List;
import java.util.Optional;
import controller.DatabaseController;
import database.Project;
import database.ProjectUser;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class DataModel {

	private final DatabaseController database = DatabaseController.getInstance();
	
    private ObservableList<ProjectUser> userList;
    private ObservableList<Object> projectList;
	
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
    		System.out.println(u.toString());
    	}
    }
    
    public ObservableList<ProjectUser> getUserList(){
    	return userList;
    }
    
    /**returns 1 when loginShortcut and password are correct in database
     * returns 2 if password does not match to user
     * returns 3 if user does not exist in database 
     * threw the try-catch-NoResultExeption the the error is catched if no result is in the database
     * 
     * @param loginShortcut is not case sensitive
     * @param password
     */    
    public int getLoginData(String loginShortcut, String password) {
    	System.out.println("Start Query Shortcut and Password in DataModel");
    	for(ProjectUser u : userList) {
    		if(u.getUserShortcut().equals(loginShortcut)) {
    			if(u.getPassword().equals(password)) {
    				System.out.println("Password ok");
    				return 1;
    			} else {
    				System.out.println("Password not ok");
    				return 2;       				
    			}
    		}
    	}
    	System.out.println("User: " + loginShortcut + " does not exist");
    	return 3;
    }
    
    /** returns TRUE if Shortcut exists in database, FALSE if not
     * 	
     * @param shortcut
     * @return
     */
    public boolean userShortcutExistis(String shortcut) {
    	for(ProjectUser u : userList) {
    		if(u.getUserShortcut().toLowerCase().equals(shortcut.toLowerCase())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void createUser(String userShortcut, String firstName, String lastName, String eMail, String role, String password) {
		System.out.println("[DataModel] Adding new User to List");
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
    
    


    
    

    public void loadAllData() {
    	List<Object> projectList = database.readAllData();
		projectList = FXCollections.observableArrayList(projectList);	
    }   
    
    public void printAllData() {
    	for(Object u : projectList) {
    		if(u != null) {
    			System.out.println(u.getClass());
    		}
    	}
    }
    
    
	
}

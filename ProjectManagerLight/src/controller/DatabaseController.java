package controller;

import database.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/** The Controller for interactions with the database classes in the package database
 	Class is still in test
 */
public class DatabaseController {
	
	private static DatabaseController onlyInstance;
	
//Test method for instance	
	public static DatabaseController getInstance() {
		
		if (onlyInstance != null) return onlyInstance;
		else {
			onlyInstance = new DatabaseController();
			return onlyInstance;
		}
	}

	public DatabaseController() {}

	/* implement CRUD functions
	 * C... create
	 * R... read
	 * U... update
	 * D... delete
	 */
	
//-------------Project User--------------------------	
	public void createUser(String userShortcut, String firstName, String lastName, String eMail, String role, String password) {
		System.out.println("Start creating user");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("Create User: " + userShortcut);
		transaction.begin();
		
		ProjectUser newUser = new ProjectUser();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.seteMail(eMail);
		newUser.setUserShortcut(userShortcut);
//TODO Generate password to HashCode
		newUser.setPassword(password);
		newUser.setRole(role);
		
		em.persist(newUser);
		transaction.commit();
		em.close();
		emf.close();
	}
	
	public ProjectUser readUser(String userShortcut) throws NoResultException{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("Read User: " + userShortcut);
		transaction.begin();		
		
		ProjectUser user = (ProjectUser)em.createQuery("select userShortcut from ProjectUser user where user.userShortcut like '" + userShortcut + "'");

		transaction.commit();
		em.close();
		
		return user;
	}
/**returns 1 when loginShortcut and password are correct in database
 * returns 2 if password does not match to user
 * returns 3 if user does not exist in database 
 * threw the try-catch-NoResultExeption the the error is catched if no result is in the database
 * 
 * @param loginShortcut is not case sensitive
 * @param password
 */
	public int userLoginQuery(String loginShortcut, String password) {
		System.out.println("Start Query Shortcut and Password");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("Aks Login data from: " + loginShortcut);
		transaction.begin();
		
		try {
			ProjectUser loginUser = (ProjectUser)em.createQuery("SELECT u FROM ProjectUser u WHERE LOWER(u.userShortcut) LIKE '" + loginShortcut.toLowerCase() + "'").getSingleResult();

			transaction.commit();
			em.close();

			System.out.println(loginUser.getPassword());
			if (loginUser.getPassword().equals(password)) {
				System.out.println("Password ok");
				return 1;
			} else {
				System.out.println("Password not ok");
				return 2;
			}
		} catch (NoResultException e) {
			System.out.println("User: " + loginShortcut + " does not exist");
			return 3;
		}
	}
/** returns TRUE if Shortcut exists in database, FALSE if not
 * 	
 * @param shortcut
 * @return
 */
	public boolean userShortcutExistis(String shortcut) {
		System.out.println("Start Query if shortcut exists");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		try {
			ProjectUser loginUser = (ProjectUser)em.createQuery("SELECT u FROM ProjectUser u WHERE LOWER(u.userShortcut) LIKE '" + shortcut.toLowerCase() + "'").getSingleResult();

			transaction.commit();
			em.close();
			return true;
		} catch (NoResultException e) {
			System.out.println("User: " + shortcut + " does not exist");
			return false;
		}
	}
	public ProjectUser updateUser(ProjectUser updateUser) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("Update User: " + updateUser.getUserShortcut());
		transaction.begin();		
		
		ProjectUser updatedUser = em.merge(updateUser);
		
		transaction.commit();
		em.close();
		
		return updatedUser;
	}
	
	public void deleteUser(ProjectUser deleteUser) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("Delete User: " + deleteUser.getUserShortcut());
		transaction.begin();		
		
		if (!em.contains(deleteUser)) {
			deleteUser = em.merge(deleteUser);
		}
		
		em.remove(deleteUser);
		
		transaction.commit();
		em.close();
	}
//------------Project--------------------------------
	public void createProject(Project newProject) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("Create Project: " + newProject.getProjectID());
		transaction.begin();
		
		em.persist(newProject);
		
		transaction.commit();
		em.close();		
	}
	
	public Project readProject(String projectID) {
		return null;
	}
	
	public Project updateProject(Project updatedProject) {
		return null;
	}
	
	public void deleteProject(Project deletedProject) {
		
	}
	//-------------Story--------------------------------
	public void createStory(Story newStory) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("Create Story: " + newStory.getStoryID());
		transaction.begin();
		
		em.persist(newStory);
		
		transaction.commit();
		em.close();			
	}
	
	public Story readStory(String storyID) {
		return null;
	}
	
	public Story updateStory(Story updatedStory) {
		return null;
	}
	
	public void deleteStory(Project deletedStory) {
		
	}	
	//-------------Task--------------------------------
	public void createTask(Task newTask) {
		
	}
	
	public Task readTask(String TaskID) {
		return null;
	}
	
	public Task updateTask(Task updatedTask) {
		return null;
	}
	
	public void deleteTask(Project deletedTask) {
		
	}
	
	
	
	
	

}

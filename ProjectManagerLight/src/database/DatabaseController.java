package database;

import database.*;
import model.Project;
import model.ProjectUser;
import model.Story;
import model.Task;

import java.net.NetworkInterface;
import java.util.List;

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
	public void createUserDatabase(String userShortcut, String firstName, String lastName, String eMail, String role, String password) {
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
		newUser.setPassword(password);
		newUser.setRole(role);
		
		em.persist(newUser);
		transaction.commit();
		em.close();
		emf.close();
	}
	
	public void createUser(ProjectUser newUser) {
		System.out.println("Start creating user");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Create User: " + newUser.getUserShortcut());
		transaction.begin();
				
		em.persist(newUser);
		transaction.commit();
		em.close();
		emf.close();
	}

	public ProjectUser readUser(String userShortcut) throws NoResultException{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Read User: " + userShortcut);
		transaction.begin();		
		
		ProjectUser user = (ProjectUser)em.createQuery("select userShortcut from ProjectUser user where user.userShortcut like '" + userShortcut + "'");

		transaction.commit();
		em.close();
		
		return user;
	}
	
	public List<ProjectUser> readAllUser(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Read all User");
		transaction.begin();	
		
		@SuppressWarnings("unchecked")
		List<ProjectUser> user = (List<ProjectUser>)em.createQuery("select u from ProjectUser u").getResultList();
		
		transaction.commit();
		em.close();
		
		return user;		
	}
	
	public void deleteUser(ProjectUser deleteUser) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Delete User: " + deleteUser.getUserShortcut());
		transaction.begin();		
		
		if (!em.contains(deleteUser)) {
			deleteUser = em.merge(deleteUser);
		}
		
		em.remove(deleteUser);
		
		transaction.commit();
		em.close();
	}	
	
	public ProjectUser updateUser(ProjectUser updateUser) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Update User: " + updateUser.getUserShortcut());
		transaction.begin();		
		
		ProjectUser updatedUser = em.merge(updateUser);
		
		transaction.commit();
		em.close();
		
		return updatedUser;
	}
	//-------------Story--------------------------------
	public void createStory(Story newStory) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Create Story: " + newStory.getStoryID());
		transaction.begin();
		
		em.persist(newStory);
		
		transaction.commit();
		em.close();			
	}
	public void createStoryDirectInDatabase(String description, int duration, String storyName, Project toProject, int positionGridPane) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Create Story: " + storyName);
		transaction.begin();
		
		Story newStory = new Story();
		newStory.setDescription(description);
		newStory.setDuration(duration);
		newStory.setStoryName(storyName);
		newStory.setProject(toProject);
		newStory.setPositionGridPane(positionGridPane);
				
		em.persist(newStory);
		
		transaction.commit();
		em.close();			
	}	
	
	public Story readStory(String storyName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[database.DatabaseController] Read Story: " + storyName);
		transaction.begin();
		
		Story story = (Story)em.createQuery("select s from Story s where s.storyName like '" + storyName + "'").getSingleResult();
				
		em.persist(story);
		transaction.commit();
		em.close();	
		
		return story;
	}
	
	public List<Story> readAllStories(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[database.DatabaseController] Read all Stories");
		transaction.begin();	
		
		@SuppressWarnings("unchecked")
		List<Story> story = (List<Story>)em.createQuery("select u from Story u").getResultList();
		
		transaction.commit();
		em.close();
		
		return story;		
	}
	
	public Story updateStory(Story updateStory) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();	
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Update Story: " + updateStory.getStoryName());
		transaction.begin();		
		
		Story updatedStory = em.merge(updateStory);
		
		transaction.commit();
		em.close();
		
		return updatedStory;
	}
	
	public void deleteStory(Story deletedStory) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();	
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[database.DatabaseController] Delete Story: " + deletedStory.getStoryName());
		transaction.begin();		
		
		if (!em.contains(deletedStory)) {
			deletedStory = em.merge(deletedStory);
		}
		
		em.remove(deletedStory);
		
		transaction.commit();
		em.close();
	}	
	//-------------Task--------------------------------
	public void createTaskDirectInDatabase(String taskName, String description, int duration, Story toStory) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Create Story: " + taskName);
		transaction.begin();
		
		Task newTask= new Task();
		newTask.setTaskName(taskName);
		newTask.setDescription(description);
		newTask.setDuration(duration);
		newTask.setStory(toStory);
						
		em.persist(newTask);

		transaction.commit();
		em.close();			
	}	
	
	public List<Task> readAllTasks(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[database.DatabaseController] Read all Tasks");
		transaction.begin();	
		
		@SuppressWarnings("unchecked")
		List<Task> task = (List<Task>)em.createQuery("select u from Task u").getResultList();
		
		transaction.commit();
		em.close();
		
		return task;		
	}
	
	
	public void createTask(Task newTask) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Create Task: " + newTask.getTaskID());
		transaction.begin();
		
		em.persist(newTask);
		
		transaction.commit();
		em.close();	
	}
	
	public Task readTask(String TaskName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[database.DatabaseController] Read Story: " + TaskName);
		transaction.begin();
		
		Task task = (Task)em.createQuery("select s from Story s where s.storyName like '" + TaskName + "'").getSingleResult();
				
		em.persist(task);
		transaction.commit();
		em.close();	
		
		return task;
	}
	
	public Task updateTask(Task updateTask) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();	
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Update Story: " + updateTask.getTaskName());
		transaction.begin();		
		
		Task updatedTask = em.merge(updateTask);
		
		transaction.commit();
		em.close();
		
		return updatedTask;
	}
	
	public void deleteTask(Task deletedTask) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();	
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[database.DatabaseController] Delete Task: " + deletedTask.getTaskName());
		transaction.begin();		
		
		if (!em.contains(deletedTask)) {
			deletedTask = em.merge(deletedTask);
		}
		em.remove(deletedTask);	
		transaction.commit();
		em.close();		
	}	

//------------Project--------------------------------
	public void createProjectDirectInDatabase(String projectName, String description) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Create Project: " + projectName);
		transaction.begin();
		
		Project newProject= new Project();
		newProject.setProjectName(projectName);
		newProject.setDescription(description);
			
		em.persist(newProject);

		transaction.commit();
		em.close();			
	}	
		
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
	
	public List<Project> readAllProjects(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[database.DatabaseController] Read all Projects");
		transaction.begin();	
		
		@SuppressWarnings("unchecked")
		List<Project> project = (List<Project>)em.createQuery("select u from Project u").getResultList();
		
		transaction.commit();
		em.close();
		
		return project;		
	}
	
	public Project readProject(String projectName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[database.DatabaseController] Read Project: " + projectName);
		transaction.begin();
		
		Project project = (Project)em.createQuery("select s from Project s where s.projectName like '" + projectName + "'").getSingleResult();
				
		em.persist(project);		
		transaction.commit();
		em.close();	
		
		return project;
	}
	
	public Project updateProject(Project updateProject) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();	
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[DatabaseController] Update User: " + updateProject.getProjectName());
		transaction.begin();		
		
		Project updatedProject = em.merge(updateProject);
		
		transaction.commit();
		em.close();
		
		return updatedProject;
	}
	
	public void deleteProject(Project deletedProject) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();	
		EntityTransaction transaction = em.getTransaction();
		
		System.out.println("[database.DatabaseController] Delete Project: " + deletedProject.getProjectName());
		transaction.begin();		
		
		if (!em.contains(deletedProject)) {
			deletedProject = em.merge(deletedProject);
		}
		
		em.remove(deletedProject);	
		transaction.commit();
		em.close();
	}

	
	
	
	
	

}

package database;

import model.Project;
import model.ProjectUser;
import model.Story;
import model.Task;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/** The Controller for interactions with the database classes in the package database
 	Class is still in test
 */
public class DatabaseController {

	private static DatabaseController onlyInstance;
	//Test-method for instance	
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
	/*this method is for testing
	 * adds a ProjectUser in the database
	 */
	public void createUserDatabase(String userShortcut, String firstName, String lastName, String eMail, String role, String password) {
		System.out.println("Start creating user");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		System.out.println("[database.DatabaseController] Create User in Database: " + userShortcut);
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

	//creates a new ProjectUser in the database
	public void createUser(ProjectUser newUser) {
		System.out.println("Start creating user");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		System.out.println("[database.DatabaseController] Create User: " + newUser.getUserShortcut());
		transaction.begin();

		em.persist(newUser);
		transaction.commit();
		em.close();
		emf.close();
	}

	/* for testing
	 * returns the ProjectUser by giving the user-shortcut
	 */
	public ProjectUser readUser(String userShortcut) throws NoResultException{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		System.out.println("[database.DatabaseController] Read User: " + userShortcut);
		transaction.begin();		

		ProjectUser user = (ProjectUser)em.createQuery("select userShortcut from ProjectUser user where user.userShortcut like '" + userShortcut + "'");

		transaction.commit();
		em.close();

		return user;
	}

	//returns a list with all ProjectUser from the database
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

	//deleting ProjectUser in the database
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

	//update ProjectUser in the database
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
	//crates a new Story in the database
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

	/*this method is for testing
	 * adds a Story in the database
	 */
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

	//returns a list with all Stories from the database
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

	//updates the given Story in the database
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

	//deletes the given Story in the database
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
	/*this method is for testing
	 * adds a Task in the database
	 */
	public void createTaskDirectInDatabase(String taskName, String description, int duration, Story toStory) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();

		EntityTransaction transaction = em.getTransaction();

		System.out.println("[DatabaseController] Create Task: " + taskName);
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

	//returns a list with all Tasks from the database
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

	//crates a new Task in the database
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

	/*this method is for testing
	 * reads a Task by the given Task-Name from the database
	 */
	public Task readTask(String TaskName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		System.out.println("[database.DatabaseController] Read Task: " + TaskName);
		transaction.begin();

		Task task = (Task)em.createQuery("select s from Story s where s.storyName like '" + TaskName + "'").getSingleResult();

		em.persist(task);
		transaction.commit();
		em.close();	

		return task;
	}

	//updates the given Task in the database
	public Task updateTask(Task updateTask) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();	
		EntityTransaction transaction = em.getTransaction();

		System.out.println("[database.DatabaseController] Update Task: " + updateTask.getTaskName());
		transaction.begin();		

		Task updatedTask = em.merge(updateTask);

		transaction.commit();
		em.close();
		return updatedTask;
	}

	//deletes the given Task in the database
	public void deleteTask(Task deletedTask) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();	
		EntityTransaction transaction = em.getTransaction();

		System.out.println("[database.DatabaseController] Delete Task: " + deletedTask);
		transaction.begin();		

		if (!em.contains(deletedTask)) {
			deletedTask = em.merge(deletedTask);
		}
		em.remove(deletedTask);	
		transaction.commit();
		em.close();		
	}	

	//------------Project--------------------------------
	/*this method is for testing
	 * adds a Project in the database
	 */
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

	//crates a new Project in the database
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

	//returns a list with all Projects from the database
	public List<Project> readAllProjects(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerLight");
		EntityManager em = emf.createEntityManager();

		EntityTransaction transaction = em.getTransaction();

		System.out.println("[database.DatabaseController] Read all Projects");
		transaction.begin();	

		@SuppressWarnings("unchecked")
		List<Project> project = (List<Project>)em.createQuery("select u from Project u").getResultList();

		System.out.println("[database.DatabaseController] Read all Projects " + project);

		transaction.commit();
		em.close();

		return project;		
	}

	//updates the given Project in the database
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

	//deletes the given Project in the database
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
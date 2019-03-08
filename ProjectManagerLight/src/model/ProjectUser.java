package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class ProjectUser implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userID;
	private String userShortcut;
	private String firstName;
	private String lastName;
	private String eMail;
	private String role;
	private String password;


	@OneToMany(mappedBy = "projectManager", orphanRemoval=true)
	private List<Project> projectManger = new ArrayList<Project>();

	@OneToMany(mappedBy = "responsibility", orphanRemoval=true)
	private List<Story> involvedStories = new ArrayList<>();

	@OneToMany(mappedBy = "responsibility", orphanRemoval=true)
	private List<Task> involvedTasks = new ArrayList<>();

	@ManyToMany(mappedBy = "projectMembers", fetch=FetchType.EAGER)
	private List<Project> involvedProjects = new ArrayList<>();

	public ProjectUser() {
		super();
	}

	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getUserShortcut() {
		return userShortcut;
	}
	public void setUserShortcut(String userShortcut) {
		this.userShortcut = userShortcut;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Project> getInvolvedProjects() {
		return involvedProjects;
	}
	public void setInvolvedProjects(List<Project> involvedProjects) {
		this.involvedProjects = involvedProjects;
	}

	//removes the given Project from the involvedProject-List
	public void removeProjectFromUser(Project project) {
		this.involvedProjects.remove(project);
	}

	//adds the given Project to the involvedProject-List, if it has not already been added
	public void addProjectToUser(Project project) {	
		if(!(this.getInvolvedProjects().contains(project))) {
			System.out.println("[model.ProjectUser] addProjectToUser, Project: " + project);
			this.involvedProjects.add(project);
		}
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Story> getInvolvedStories() {
		return involvedStories;
	}
	public void setInvolvedStories(List<Story> involvedStories) {
		this.involvedStories = involvedStories;
	}
	public List<Task> getInvolvedTasks() {
		return involvedTasks;
	}
	public void setInvolvedTasks(List<Task> involvedTasks) {
		this.involvedTasks = involvedTasks;
	}

	public void addInvolvedStory(Story newStory) {
		this.involvedStories.add(newStory);
	}

	public void addInvolvedTask(Task newTask) {
		this.involvedTasks.add(newTask);
	}


	/* returns all ProjectIDs from involvedProject list as String with the 
	 * letter "P"
	 * Is used for the UserTable in the UserManagement
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@SuppressWarnings("unused")
	@Override
	public String toString() {
		StringBuffer returnString = new StringBuffer();
		for(Project p : this.getInvolvedProjects()) {
			returnString.append("P-" + p.getProjectID() + " ");
		}
		String newString = returnString.toString();
		return newString;
	}
	
	/*compares the given Object to this Object 
	 * returns TRUE if References equal
	 * if References not equal and given Object instanceof ProjectUser --> assigned by userID
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override public boolean equals(Object o) {

		if (this == o) return true;

		if ((o instanceof ProjectUser) == true) {
			ProjectUser u = (ProjectUser)o;
			return this.userID == u.userID;
		} else {
			return false;
		}
	}
}
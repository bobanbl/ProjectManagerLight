package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class ProjectUser implements Serializable{

	/**
	 * 
	 */
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
	@OneToMany
	private List<Project> involvedProjects = new ArrayList<>();

	private List<Story> involvedStories = new ArrayList<>();

	private List<Task> involvedTasks = new ArrayList<>();
	
	public ProjectUser() {
		super();
	}
	
	public ProjectUser(String userShortcut, String firstName, String lastName, String eMail, String role, String password) {
		super();
		this.setUserShortcut(userShortcut);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.seteMail(eMail);
		this.setRole(role);
		this.setPassword(password);
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


}

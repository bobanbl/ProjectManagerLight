package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
	@ManyToMany(mappedBy = "projectMembers", fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
	private List<Project> involvedProjects = new ArrayList<>();
	
	@OneToMany
	private List<Story> involvedStories = new ArrayList<>();
	
	@OneToMany
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
	
	public void addProjectToUser(Project project) {	
		if(!project.getProjectMember().contains(this)) {
			System.out.println("[model.ProjectUser] addProjectToUser, Project: " + project);
			this.involvedProjects.add(project);
			project.addProjectMembers(this);
		}
	}
	
	public void deleteProjectFromUser(Project project) {
		for(Project p : involvedProjects) {
			if(p.getProjectID() == project.getProjectID()) {
				involvedProjects.remove(p);
				p.deleteMemberFromProject(this);
			}
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

	@Override
	public String toString() {
		return "ProjectUser [userID=" + userID + ", userShortcut=" + userShortcut + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", eMail=" + eMail + ", role=" + role + ", password=" + password
				+ ", involvedProjects=" + involvedProjects + ", involvedStories=" + involvedStories + ", involvedTasks="
				+ involvedTasks + "]";
	}
	
	


}

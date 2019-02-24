package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: Story
 *
 */
@Entity
public class Story implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int storyID;
	private String storyName;
	private String description;
	@ManyToOne
	@JoinColumn(name = "FK_USERID", referencedColumnName = "USERID")
	private ProjectUser responsibility;
	private int duration;
	private int positionGridPane;
	
	@OneToMany(mappedBy = "story", orphanRemoval=true, cascade = CascadeType.ALL)
	private List<Task> tasks = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name = "FKProjectID", referencedColumnName = "PROJECTID")
	private Project project;
	
//	private List<Task> tasks = new ArrayList<>();
	
	public int getStoryID() {
		return storyID;
	}
	public void setStoryID(int storyID) {
		this.storyID = storyID;
	}
	public String getStoryName() {
		return storyName;
	}
	public void setStoryName(String storyName) {
		this.storyName = storyName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ProjectUser getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(ProjectUser responsibility) {
		this.responsibility = responsibility;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	//For testing
	public void removeTaskFromStory(Task removeTask) {
		System.out.println("[model.Story] Remove Task 1 from Story: " + removeTask);
		
		for(Task t : tasks) {
			if(t.getTaskID() == removeTask.getTaskID()) {
				System.out.println("[model.Story] Remove Task from Story: " + t);
				tasks.remove(t);
			}
		}	
	}
	
	public void addTasktoStory(Task newTask) {
		this.tasks.add(newTask);
		System.out.println("[model.Story] Task List: " + this.getTasks());
	}
	public int getPositionGridPane() {
		return positionGridPane;
	}
	public void setPositionGridPane(int positionGridPane) {
		this.positionGridPane = positionGridPane;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
		System.out.println("[model.Story] Story: " + project.getProjectName());
		if(!project.getStory().contains(this)) {
			project.addStorytoProject(this);
		}
	}
	
	
		
	
}

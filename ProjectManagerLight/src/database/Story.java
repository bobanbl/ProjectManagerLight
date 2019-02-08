package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	private ProjectUser responsibility;
	private int duration;
	@OneToMany(mappedBy = "story")
	private List<Task> tasks = new ArrayList<>();
	
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
	
	public void addTasktoStory(Task newTask) {
		this.tasks.add(newTask);
		System.out.println("[database.Story] Task List: " + this.getTasks());
	}
		
	
}

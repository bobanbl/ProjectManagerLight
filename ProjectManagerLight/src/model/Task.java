package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity implementation class for Entity: Task
 *
 */
@Entity
public class Task implements Serializable{

	//TaskStatus as ENUM
	public enum TaskStatus{
		NEW, IN_PROGRESS, ON_HOLD, CLOSED, REJECTED;
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int taskID;
	private String taskName;
	private String description;
	@ManyToOne
	@JoinColumn(name = "FK_USERID", referencedColumnName = "USERID")
	private ProjectUser responsibility;
	private int duration;
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	@ManyToOne
	@JoinColumn(name = "FKStoryID", referencedColumnName = "STORYID")
	private Story story;

	public final Task getTask() {
		return this;
	}

	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
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
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Story getStory() {
		return story;
	}

	/*adds the given Story to the attribute "story" 
	 * and adds this Task to the TaskList: tasks in the object Story
	 */
	public void setStory(Story story) {
		this.story = story;
		if(!story.getTasks().contains(this)) {
			System.out.println("[model.Task] !story.getTasks().contains(this) " + story);
			story.addTasktoStory(this);
		}
	}

	/*compares the given Object to this Object 
	 * returns TRUE if References equal
	 * if References not equal and given Object instanceof Task --> assigned by taskID
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override 
	public boolean equals(Object o) {
		if (this == o) return true;
		if ((o instanceof Task) == true) {
			Task u = (Task)o;
			return this.taskID == u.taskID;
		} else {
			return false;
		}
	}
}
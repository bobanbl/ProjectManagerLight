package database;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Task
 *
 */
@Entity
public class Task implements Serializable{
	
	enum TaskStatus{
		NEW, IN_PROGRESS, ON_HOLD, CLOSED;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int taskID;
	private String taskName;
	private String description;
	private ProjectUser responsibility;
	private int duration;
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	
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
	
}

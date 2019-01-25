package database;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: Project
 *
 */
@Entity

public class Project implements Serializable{

	public enum ProjectStatus {
		IN_DEVELOPMENT, CLOSED, NOT_STARTET
	}
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int projectID;
	private String projectName;
	@Enumerated(EnumType.STRING)
	private ProjectStatus projectStatus;
	private String description;
	private String startDate;
	private String planedFinishDate;
	private String projectSponsor;
	private ProjectUser projectManager;
	@OneToMany
	private List<ProjectUser> projectMember = new ArrayList<>();
	
	public Project() {
		super();
	}
	
	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPlanedFinishDate() {
		return planedFinishDate;
	}

	public void setPlanedFinishDate(String planedFinishDate) {
		this.planedFinishDate = planedFinishDate;
	}

	public String getProjectSponsor() {
		return projectSponsor;
	}

	public void setProjectSponsor(String projectSponsor) {
		this.projectSponsor = projectSponsor;
	}

	public ProjectUser getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectUser projectManager) {
		this.projectManager = projectManager;
	}

	public List<ProjectUser> getProjectMember() {
		return projectMember;
	}

	public void setProjectMember(List<ProjectUser> projectMember) {
		this.projectMember = projectMember;
	}
	
	public void addProjectMember(ProjectUser user) {
		user.getInvolvedProjects().add(this);
		this.projectMember.add(user);
	}
	
}

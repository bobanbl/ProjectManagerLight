package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: Project
 *
 */
@Entity

public class Project implements Serializable{

	public enum ProjectStatus {
		IN_DEVELOPMENT("In development"), CLOSED("Closed"), NOT_STARTED("Not started");
		
		String name;
		
		private ProjectStatus(String name) {
			this.name = name;
		}
		
		public String getName() {			
			return this.name;
		}
	}
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int projectID;
	private String projectName;
	@Enumerated(EnumType.STRING)
	private ProjectStatus projectStatus;
	private String description;
	private Date startDate;
	private Date planedFinishDate;
	private String projectSponsor;
	private ProjectUser projectManager;
	@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
	private List<ProjectUser> projectMembers = new ArrayList<>();
//	@JoinColumn(name = "FKUSERID", JoinColumns = { @JoinColumns"USERID")
	
	
	@OneToMany(mappedBy = "project", orphanRemoval=true, cascade = CascadeType.ALL)
	private List<Story> story = new ArrayList<>();
	
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getPlanedFinishDate() {
		return planedFinishDate;
	}

	public void setPlanedFinishDate(Date planedFinishDate) {
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
		return projectMembers;
	}

	public void setProjectMembers(List<ProjectUser> projectMember) {
		this.projectMembers = projectMember;
	}
	
	public void addProjectMembers(ProjectUser user) {
		//		if(!this.getProjectMember().contains(user)) {
		this.projectMembers.add(user);
		System.out.println("[model.Project] addProjectMembers: " + user);

		//			user.addProjectToUser(this);
		//		}
	}
	
	public void deleteMemberFromProject(ProjectUser user) {
		for(ProjectUser u : projectMembers) {
			if(u.getUserID() == user.getUserID()) {
				projectMembers.remove(u);
			}
		}
	}

	public List<Story> getStory() {
		return story;
	}

	public void setStory(List<Story> story) {
		this.story = story;
	}
	
	public void addStorytoProject(Story story) {
		this.story.add(story);
		System.out.println("[model.Project] Story List: " + this.getStory());
	}
	
	
	
}

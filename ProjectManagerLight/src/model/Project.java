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

	//ProjectStatus as Enum with String for ComboBox in ProjetDetailView
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
	@OneToOne
	@JoinColumn(name = "FK_USERID", referencedColumnName = "USERID")
	private ProjectUser projectManager;
	//(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
	//
	@ManyToMany(fetch=FetchType.EAGER)
	private List<ProjectUser> projectMembers = new ArrayList<>();
	//	@JoinColumn(name = "FKUSERID", JoinColumns = { @JoinColumns"USERID")


	@OneToMany(mappedBy = "project", orphanRemoval=true)
	private List<Story> stories = new ArrayList<>();

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

	//returns the  List "projectMember" if not null otherwise null
	public List<ProjectUser> getProjectMember() {
		if(projectMembers != null) {
			return projectMembers;
		}
		return null;	
	}

	public void setProjectMembers(List<ProjectUser> projectMember) {
		this.projectMembers = projectMember;
	}

	public List<Story> getStory() {
		return stories;
	}

	public void setStory(List<Story> story) {
		this.stories = story;
	}

	//adds the given Story to the list "stories"
	public void addStorytoProject(Story story) {
		this.stories.add(story);
	}

	//removes the given Story from the list "stories"
	public void removeStoryFromProject(Story removeStory) {
		stories.remove(removeStory);
	}

	/*compares the given Object to this Object 
	 * returns TRUE if References equal
	 * if References not equal and given Object instanceof Project --> assigned by projectID
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override 
	public boolean equals(Object o) {
		if (this == o) return true;
		if ((o instanceof Project) == true) {
			Project u = (Project)o;
			return this.projectID == u.projectID;
		} else {
			return false;
		}
	}
}
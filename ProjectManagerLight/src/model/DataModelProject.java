package model;

import java.util.Date;
import java.util.List;

import database.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import model.Project.ProjectStatus;
import javafx.collections.ListChangeListener.Change;

public class DataModelProject {
	private final DatabaseController database = DatabaseController.getInstance();
	private ObservableList<Project> projectList;
	private DataModelStory storyModel;
	
	public DataModelProject(){
		loadProjectData();
		printProjectData();
		
		projectList.addListener(new ListChangeListener<Project>() {

			@Override
			public void onChanged(Change<? extends Project> c) {
				c.next();

				if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                         System.err.println("Permutation not implemented");
                    }
                } else if (c.wasReplaced()) {
                	for (int i = c.getFrom(); i < c.getTo(); ++i) {
                		database.updateProject(projectList.get(i));
                   }
                } else if (c.wasRemoved()){
                	for (Project u : c.getRemoved()) {
                		database.deleteProject(u);
                	}
                } else {
                    for (Project u : c.getAddedSubList()) {
                        database.createProject(u);
                    }
                }
			}
		});
	}	
	public void setDataModelStory(DataModelStory storyModel) {
		this.storyModel = storyModel;
	}
	
	//----------------------------Project------------------------------------------
    public void loadProjectData() {
    	List<Project> list = database.readAllProjects();
		projectList = FXCollections.observableArrayList(list);
    }
    
    public void printProjectData() {
    	for(Project u : projectList) {
    		System.out.println("[model.DataModelProject]" + u.toString());
    	}
    }
    
    public Project createProject( String ProjectName, String description, ProjectStatus projectStatus, 
    		Date projectStartDate, Date projectFinishDate, String projectSponsor, ProjectUser projectManager) {
    	System.out.println("[model.DataModelProject] Adding new Project to List");
    	Project newProject = new Project();
    	newProject.setDescription(description);
		newProject.setProjectName(ProjectName);
		newProject.setProjectStatus(projectStatus);
		newProject.setStartDate(projectStartDate);
		newProject.setPlanedFinishDate(projectFinishDate);
		newProject.setProjectSponsor(projectSponsor);
		newProject.setProjectManager(projectManager);
		projectList.add(newProject);
		printProjectData();
		return newProject;
    }
    
    public ObservableList<Project> getProjectList(){
    	return projectList;
    }
    
    /** Deleting existing Project in projectList
     * 
     * @param deleteProject
     */
    public void deleteProject(Project deleteProject) {
    	System.out.println("[model.DataModelProject] Deleting project");
    	projectList.remove(deleteProject);
    	storyModel.loadStoryData();
    	printProjectData();
    }
    
    public void updateProject(Project updateProject, String ProjectName, String description, 
    		ProjectStatus projectStatus, Date startDate, Date planedFinishDate, String projectSponsor, ProjectUser projectManager) {
    	System.out.println("[mdoel.DataModelProject] Update project" + updateProject.getProjectName());
    	int userIndex = projectList.indexOf(updateProject);
    	updateProject.setDescription(description);
    	updateProject.setProjectName(ProjectName);
    	updateProject.setProjectStatus(projectStatus);
    	updateProject.setStartDate(startDate);
    	updateProject.setProjectSponsor(projectSponsor);
    	updateProject.setProjectManager(projectManager);
    	System.out.println("[model.DataModelProject] UpdateProject FinishDate: " + planedFinishDate);
    	updateProject.setPlanedFinishDate(planedFinishDate);
		projectList.set(userIndex, updateProject);
    	printProjectData();
    }
    
    public void removeAllUserFromProject(Project project) {
    	int projectIndex = projectList.indexOf(project);
    	project.getProjectMember().clear();
    	projectList.set(projectIndex, project);
    	printProjectData();
    }

}

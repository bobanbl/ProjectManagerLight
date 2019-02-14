package model;

import java.util.List;

import database.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class DataModelProject {
	private final DatabaseController database = DatabaseController.getInstance();
	private ObservableList<Project> projectList;
	
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
    
    public void createProject( String ProjectName, String description) {
    	System.out.println("[model.DataModelProject] Adding new Project to List");
    	Project newProject = new Project();
    	newProject.setDescription(description);
		newProject.setProjectName(ProjectName);
		projectList.add(newProject);
		printProjectData();    	
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
    	printProjectData();
    }
    
    public void updateProject(Project updateProject, String ProjectName, String description) {
    	System.out.println("[mdoel.DataModelProject] Update project" + updateProject.getProjectName());
    	int userIndex = projectList.indexOf(updateProject);
    	updateProject.setDescription(description);
    	updateProject.setProjectName(ProjectName);
		projectList.set(userIndex, updateProject);
    	printProjectData();
    }

}

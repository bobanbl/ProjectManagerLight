package model;


import java.util.List;
import database.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**loads the data from the Entity Project  into an ObservableList from the Database 
 * over the DatabaseController
 * @author blazebo
 *
 */
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

	//load Projects from Database into a list and adds this list to an ObservableList projectList
	public void loadProjectData() {
		List<Project> list = database.readAllProjects();
		projectList = FXCollections.observableArrayList(list);
	}

	//print projectList in Console
	public void printProjectData() {
		for(Project u : projectList) {
			System.out.println("[model.DataModelProject]" + u.toString());
		}
	}

	/**creating a new project in projectList
	 * @return newProject
	 */
	public Project createProject(Project newProject) {
		System.out.println("[model.DataModelProject] Adding new Project to List");
		projectList.add(newProject);
		printProjectData();
		return newProject;
	}

	//return projectList
	public ObservableList<Project> getProjectList(){
		return projectList;
	}

	/** deleting existing Project in projectList
	 * @param deleteProject
	 */
	public void deleteProject(Project deleteProject) {
		System.out.println("[model.DataModelProject] Deleting project");

		projectList.remove(deleteProject);
		printProjectData();
	}

	//update existing project
	public void updateProject(Project updateProject) {
		System.out.println("[model.DataModelProject] Update project: " + updateProject.getProjectName()); 	
		int userIndex = projectList.indexOf(updateProject);
		projectList.set(userIndex, updateProject);
		printProjectData();
	}

}

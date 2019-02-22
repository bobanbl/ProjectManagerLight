package test;

import java.util.List;

import model.DataModel;
import model.DataModelProject;
import model.DataModelStory;
import model.Project;
import model.ProjectUser;
import model.Task;

public class DataModelTest {

	public static void main(String[] args) {
		DataModel model = new DataModel();
		DataModelStory modelStory = new DataModelStory();
		DataModelProject modelProject = new DataModelProject();
		
//		List<ProjectUser> list = model.getUserList();
//		for(ProjectUser u : list) {
//			System.out.println("User----------" + u);
//			System.out.println("ProjectUser---------" + u.getInvolvedProjects());
//			List<Project> projectList = u.getInvolvedProjects();
//			for(Project p : projectList) {
//				System.out.println("-----" + p.getProjectName());
//			}
//		}
		
		List<Project> list = modelProject.getProjectList();
		for(Project u : list) {
			System.out.println("ProjectUser---------" + u.getProjectMember());	
		}
		
		
		
//		for(Task t : modelStory.getStoriesFromSelectedProject(modelProject.getProjectList().get(0)).get(0).getTasks()) {
//			System.out.println(t.getTaskName());
//			modelStory.deleteTask(t);
//		}
		
		
	}
}

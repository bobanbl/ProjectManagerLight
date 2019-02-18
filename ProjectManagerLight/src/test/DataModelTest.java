package test;

import model.DataModel;
import model.DataModelProject;
import model.DataModelStory;
import model.Task;

public class DataModelTest {

	public static void main(String[] args) {
//		DataModel model = new DataModel();
		DataModelStory modelStory = new DataModelStory();
		DataModelProject modelProject = new DataModelProject();
		
		for(Task t : modelStory.getStoriesFromSelectedProject(modelProject.getProjectList().get(0)).get(0).getTasks()) {
			System.out.println(t.getTaskName());
			modelStory.deleteTask(t);
		}
		
		
	}
}

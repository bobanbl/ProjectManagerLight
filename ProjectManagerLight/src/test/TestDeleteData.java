package test;



import model.DataModelUser;
import model.DataModelProject;
import model.DataModelStory;
import model.Project;

public class TestDeleteData {

	static DataModelUser userModel;
	static DataModelStory storyModel;
	static DataModelProject projectModel;

	public static void main(String[] args) {
		userModel = new DataModelUser();
		storyModel = new DataModelStory();
		projectModel = new DataModelProject();

		deleteAllData();
	}

	//deletes all data from the database
	private static void deleteAllData() {
		
//		for(ProjectUser u : userModel.getUserList()) {
//			System.err.println(u);
//			userModel.deleteUser(u);
//		}

		for(Project p : projectModel.getProjectList()) {
			projectModel.deleteProject(p);
		}
	}
}

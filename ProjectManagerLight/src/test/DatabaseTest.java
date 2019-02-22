package test;

import database.DatabaseController;
import model.Project;
import model.Story;

// Test class for database
public class DatabaseTest {

	public static void main(String[] args) {
		
		DatabaseController databaseTestController = new DatabaseController();
		databaseTestController.createUserDatabase("a", "Franz", "Mayer", "franz@company.com", "Developer", "a");
//		databaseTestController.createProjectDirectInDatabase("FirstProject", "test Project");
//		Project project = databaseTestController.readProject("FirstProject#1");
//		databaseTestController.createStoryDirectInDatabase("TestStory", 99, "Story99", project, 1);
//		Story story = databaseTestController.readStory("Story99");
//		databaseTestController.createTaskDirectInDatabase("Task#1", "TestTask", 77, story);
		
//		databaseTestController.createStoryDirectInDatabase("TestStory22", 77, "Story90", project, 2);
//		databaseTestController.createTaskDirectInDatabase("Task#2", "TestTask2", 66, story);
//		databaseTestController.createTaskDirectInDatabase("Task#3", "TestTask2", 66, story);
//		System.out.println("[tets.DatabaseTest]" + story.getTasks().get(0).getTaskName());
//		System.out.println("[tets.DatabaseTest]" + story.getTasks().get(1).getTaskName());
//		System.out.println("[tets.DatabaseTest]" + story.getTasks().get(0));
		
//		Project project = databaseTestController.
		
//		databaseTestController.createUserDatabase("mayfr", "Franz", "Mayer", "franz@company.com", "Developer", "test");

//		databaseTestController.userLoginQuery("mafr", "test321");
//		databaseTestController.userLoginQuery("mafr", "test321");
		

	}

}

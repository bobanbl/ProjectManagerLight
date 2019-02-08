package test;

import controller.DatabaseController;
import database.Story;

// Test class for database
public class DatabaseTest {

	public static void main(String[] args) {
		
		DatabaseController databaseTestController = new DatabaseController();

		Story story = databaseTestController.readStory("Story99");
//		databaseTestController.createTaskDirectInDatabase("Task#1", "TestTask", 77, story);
//		databaseTestController.createTaskDirectInDatabase("Task#2", "TestTask2", 66, story);
		System.out.println(story.getTasks().get(0).getTaskName());
		System.out.println(story.getTasks().get(1).getTaskName());
		
//		databaseTestController.createUser("mayfr", "Franz", "Mayer", "franz@company.com", "Developer", "test");
//		databaseTestController.userLoginQuery("mafr", "test321");
//		databaseTestController.createStoryDirectInDatabase("TestStory", 99, "Story99");
		

	}

}

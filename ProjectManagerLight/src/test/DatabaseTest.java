package test;

import controller.DatabaseController;

// Test class for database
public class DatabaseTest {

	public static void main(String[] args) {
		
		DatabaseController databaseTestController = new DatabaseController();
//		databaseTestController.createUser("MayFr", "Franz", "Mayer", "franz@company.com", "Developer", "test321");
		databaseTestController.userLoginQuery("mafr", "test321");

	}

}

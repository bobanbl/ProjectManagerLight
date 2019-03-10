package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.DataModelProject;
import model.DataModelStory;
import model.DataModelUser;
import model.Project;
import model.Project.ProjectStatus;
import model.ProjectUser;
import model.Story;
import model.Task;
import model.Task.TaskStatus;

// Creating Test-Data
public class TestData {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		//DatabaseController databaseTestController = new DatabaseController();
		//Creating ProjectUser direct in database
		//databaseTestController.createUserDatabase("a", "Franz", "Mayer", "franz@company.com", "Developer", "a");

		DataModelProject projectModel = new DataModelProject();
		DataModelStory storyModel = new DataModelStory();
		DataModelUser userModel = new DataModelUser();

		//-------------------Creating ProjectUser-------------------
		//User Franz
		ProjectUser userFranz = new ProjectUser();
		userFranz.setUserShortcut("a");
		userFranz.setFirstName("Franz");
		userFranz.setLastName("Mueller");
		userFranz.seteMail("franz@company.com");
		userFranz.setRole("Developer");
		userFranz.setPassword("a");
		userModel.createUser(userFranz);

		//User Peter
		ProjectUser userPeter = new ProjectUser();
		userPeter.setUserShortcut("pema");
		userPeter.setFirstName("Peter");
		userPeter.setLastName("Mayer");
		userPeter.seteMail("p.mayer@xy.com");
		userPeter.setRole("Manager");
		userPeter.setPassword("pm");
		userModel.createUser(userPeter);

		//User Peter
		ProjectUser userSebastian = new ProjectUser();
		userSebastian.setUserShortcut("sefr");
		userSebastian.setFirstName("Sebastian");
		userSebastian.setLastName("Franck");
		userSebastian.seteMail("sebastian@franck.at");
		userSebastian.setRole("Developer");
		userSebastian.setPassword("s1978");
		userModel.createUser(userSebastian);

		//-------------------Creating Projects-------------------
		//Project 1
		Project project1 = new Project();
		project1.setProjectName("Hompage Motiva Frankfurt");
		project1.setDescription("Erstellung der Shop-Hompage für den Reifehersteller Motiva.");
		project1.setProjectSponsor("Frank Leitner");

		/*Data: YEAR-1900 (1900+119=2019)
		 * MONTH-1 (2 = March)
		 */
		project1.setStartDate(new Date(119,2,11));
		project1.setPlanedFinishDate(new Date(119,4,31));
		project1.setProjectManager(userSebastian);
		project1.setProjectStatus(ProjectStatus.IN_DEVELOPMENT);

		List<ProjectUser> projectMember1 = new ArrayList<ProjectUser>();
		projectMember1.add(userPeter);
		projectMember1.add(userFranz);
		project1.setProjectMembers(projectMember1);
		projectModel.createProject(project1);

		//Project 2
		Project project2 = new Project();
		project2.setProjectName("WebAPP Steiner GmbH&Co KG");
		project2.setDescription("Web-Applikation zur virtuellen Darstellung der Küchenelemente der Fa. Steiner");
		project2.setProjectSponsor("Manfred Steiner");

		/*Data: YEAR-1900 (1900+119=2019)
		 * MONTH-1 (2 = March)
		 */
		project2.setStartDate(new Date(119,4,15));
		project2.setPlanedFinishDate(new Date(120,1,29));
		project2.setProjectManager(userPeter);
		project2.setProjectStatus(ProjectStatus.NOT_STARTED);

		List<ProjectUser> projectMember2 = new ArrayList<ProjectUser>();
		projectMember2.add(userSebastian);
		projectMember2.add(userFranz);
		project2.setProjectMembers(projectMember2);
		projectModel.createProject(project2);

		//-------------------Creating Stories-------------------
		//Stories for Project1
		//Story 1
		Story story1 = new Story();
		story1.setStoryName("Domain");
		story1.setDuration(2);
		story1.setPositionGridPane(0);
		story1.setDescription("Domainname finden und Domain reservieren");
		story1.setProject(project1);
		story1.setResponsibility(userPeter);
		storyModel.createStory(story1);	

		//Story 2
		Story story2 = new Story();
		story2.setStoryName("GUI");
		story2.setDuration(3);
		story2.setPositionGridPane(1);
		story2.setDescription("GUI entwerfen");
		story2.setProject(project1);
		story2.setResponsibility(userFranz);
		storyModel.createStory(story2);	

		//Story 3
		Story story3 = new Story();
		story3.setStoryName("Hompage");
		story3.setDuration(13);
		story3.setPositionGridPane(2);
		story3.setDescription("Hompage erstellen");
		story3.setProject(project1);
		story3.setResponsibility(userFranz);
		storyModel.createStory(story3);	

		//Stories for Project2
		//Story 4
		Story story4 = new Story();
		story4.setStoryName("Integr.");
		story4.setDuration(10);
		story4.setPositionGridPane(3);
		story4.setDescription("Integration der bestehenden Appliaktion");
		story4.setProject(project2);
		story4.setResponsibility(userSebastian);
		storyModel.createStory(story4);	

		//-------------------Creating Tasks-------------------
		//Tasks for PROJECT 1 STORY 1
		//Task1
		Task task1 = new Task();
		task1.setTaskName("Domainname finden");
		task1.setDescription("Workshop mit Sponsor zu Findung eines passenden Domainnamens organisieren");
		task1.setDuration(1);
		task1.setResponsibility(userPeter);
		task1.setStatus(TaskStatus.CLOSED);
		task1.setStory(story1);
		storyModel.createTask(task1);

		//Task2
		Task task2 = new Task();
		task2.setTaskName("Domain reservieren");
		task2.setDescription("Domain reserviren und Angebot einholen");
		task2.setDuration(1);
		task2.setResponsibility(userPeter);
		task2.setStatus(TaskStatus.IN_PROGRESS);
		task2.setStory(story1);

		//Tasks for PROJECT 1 STORY 2
		//Task3
		Task task3 = new Task();
		task3.setTaskName("Menü");
		task3.setDescription("Struktur für Menüführung erstellen");
		task3.setDuration(1);
		task3.setResponsibility(userFranz);
		task3.setStatus(TaskStatus.ON_HOLD);
		task3.setStory(story2);
		storyModel.createTask(task3);

		//Task4
		Task task4 = new Task();
		task4.setTaskName("GUI");
		task4.setDescription("GUI in Form eines Paper-Prototype erstellen");
		task4.setDuration(2);
		task4.setResponsibility(userPeter);
		task4.setStatus(TaskStatus.IN_PROGRESS);
		task4.setStory(story2);
		storyModel.createTask(task4);

		//Tasks for PROJECT 1 STORY 3
		//Task5
		Task task5 = new Task();
		task5.setTaskName("Front-End");
		task5.setDescription("Gesamtes Frontend programmieren");
		task5.setDuration(3);
		task5.setResponsibility(userPeter);
		task5.setStatus(TaskStatus.NEW);
		task5.setStory(story3);
		storyModel.createTask(task5);

		//Task6
		Task task6 = new Task();
		task6.setTaskName("Back-End");
		task6.setDescription("Gesamtes Backend programmieren");
		task6.setDuration(10);
		task6.setResponsibility(userFranz);
		task6.setStatus(TaskStatus.NEW);
		task6.setStory(story3);
		storyModel.createTask(task6);

		//Tasks for PROJECT 2 STORY 4
		//Task7
		Task task7 = new Task();
		task7.setTaskName("Integration");
		task7.setDescription("Integration der Applikation");
		task7.setDuration(6);
		task7.setResponsibility(userSebastian);
		task7.setStatus(TaskStatus.NEW);
		task7.setStory(story4);
		storyModel.createTask(task7);

		//Task8
		Task task8 = new Task();
		task8.setTaskName("Schnittstelle");
		task8.setDescription("Schnittstelle zum Server");
		task8.setDuration(4);
		task8.setResponsibility(userFranz);
		task8.setStatus(TaskStatus.NEW);
		task8.setStory(story4);
		storyModel.createTask(task8);
	}

}

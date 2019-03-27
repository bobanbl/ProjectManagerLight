package model;

import java.util.List;
import database.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**loads the data from the Entity Story and Task into two ObservableLists from the Database
 * over the DatabaseController
 * Methods: CRUD
 * @author blazebo
 *
 */
public class DataModelStory {

	private final DatabaseController database = DatabaseController.getInstance();
	private ObservableList<Story> storyList;
	private ObservableList<Task> taskList;

	public DataModelStory(){
		loadStoryData();
		printStoryData();

		storyList.addListener(new ListChangeListener<Story>() {

			@Override
			public void onChanged(Change<? extends Story> c) {
				c.next();

				if (c.wasPermutated()) {
					for (int i = c.getFrom(); i < c.getTo(); ++i) {
						System.err.println("Permutation not implemented");
					}
				} else if (c.wasReplaced()) {
					for (int i = c.getFrom(); i < c.getTo(); ++i) {
						database.updateStory(storyList.get(i));
					}
				} else if (c.wasRemoved()){
					for (Story u : c.getRemoved()) {
						database.deleteStory(u);
					}
				} else {
					for (Story u : c.getAddedSubList()) {
						database.createStory(u);
					}
				}
			}
		});

		taskList.addListener(new ListChangeListener<Task>() {		
			@Override
			public void onChanged(Change<? extends Task> c) {
				c.next();

				if (c.wasPermutated()) {
					for (int i = c.getFrom(); i < c.getTo(); ++i) {
						System.err.println("Permutation not implemented");
					}
				} else if (c.wasReplaced()) {
					for (int i = c.getFrom(); i < c.getTo(); ++i) {
						database.updateTask(taskList.get(i));
					}
				} else if (c.wasRemoved()){
					for (Task u : c.getRemoved()) {
						database.deleteTask(u);
					}
				} else {
					for (Task u : c.getAddedSubList()) {
						database.createTask(u);
					}
				}				
			}
		});
	}
	//----------------------------STORY------------------------------------------
	/*load Stories from Database into a list and adds this list to an ObservableList storyList
	 * and calls the method: loadTaskData
	 */
	public void loadStoryData() {
		List<Story> list = database.readAllStories();
		storyList = FXCollections.observableArrayList(list);
		loadTaskData();
	}

	//print printStoryData in Console
	public void printStoryData() {
		for(Story u : storyList) {
			System.out.println("[model.DataModelStory] Print stories: " + u.toString());
		}
	}

	//create a new Story in storyList and call method printStoryData
	public void createStory(Story newStory) {
		System.out.println("[model.DataModelStory] Adding new Story to List");
		storyList.add(newStory);
		printStoryData();    	
	}

	/** Deleting existing Story in storyList
	 * 
	 * @param deleteStory
	 */
	public void deleteStory(Story deleteStory) {
		System.out.println("[model.DataModelStory] Deleting story: " + deleteStory);
		Project project = deleteStory.getProject();
		project.removeStoryFromProject(deleteStory);
		storyList.remove(deleteStory);
		printStoryData();
	}    

	//updateStory in storyList and call method printStoryData
	public void updateStory(Story updateStory) {
		System.out.println("[model.DataModelStory] UpdateStory: " + updateStory.getStoryName());
		int storyIndex = storyList.indexOf(updateStory);
		storyList.set(storyIndex, updateStory);
		printStoryData();
	}
	
	//removes the given ProjectUser from all Stories
	public void removeUserFromStories(ProjectUser projectUser) {
		System.out.println("[model.DataModelStory] removeUserFromStories: " + projectUser);
		for(Story s : storyList) {
			if(projectUser == s.getResponsibility()) {
				s.setResponsibility(null);
				int storyIndex = storyList.indexOf(s);
				storyList.set(storyIndex, s);
			}
		}
		printStoryData();
	}
	
	//----------------------------Task------------------------------------------
	/*load Tasks from Database into a list and adds this list to an ObservableList taskList
	 * and calls the method: loadTaskData
	 */
	public void loadTaskData() {
		List<Task> list = database.readAllTasks();
		taskList = FXCollections.observableArrayList(list);	
		printTaskData();
	}

	//print printStoryData in Console
	public void printTaskData() {
		for(Task u : taskList) {
			System.out.println("[model.DataModelStory] Print tasks: " + u.toString());
		}
	}

	//create a new Task in storyList and print Tasks and call method printTaskData
	public void createTask(Task newTask) {
		System.out.println("[model.DataModelStory] Adding new Task to List");
		taskList.add(newTask);
		printTaskData();    	
	}

	//deleting existing Task in TaskList and call method printTaskData
	public void deleteTask(Task deleteTask) {
		System.out.println("[model.DataModelStory] 1 Deleting Task: " + deleteTask);
		Story story = deleteTask.getStory();
		story.removeTaskFromStory(deleteTask);
		taskList.remove(deleteTask);
		printTaskData();
	}    

	//updating existing Task in TaskList call method printTaskData
	public void updateTask(Task updateTask) {
		System.out.println("[model.DataModelStory] UpdateTask: " + updateTask);
		int taskIndex = taskList.indexOf(updateTask);
		taskList.set(taskIndex, updateTask);
		printTaskData();
	} 
	
	//removes the given ProjectUser from all Tasks
	public void removeUserFromTasks(ProjectUser projectUser) {
		System.out.println("[model.DataModelStory] removeUserFromTasks: " + projectUser);
		for(Task t : taskList) {
			if(projectUser == t.getResponsibility()) {
				t.setResponsibility(null);
				int taskIndex = taskList.indexOf(t);
				taskList.set(taskIndex, t);
			}
		}
		printStoryData();
	}

}




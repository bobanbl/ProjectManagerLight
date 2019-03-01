package model;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import model.Task.TaskStatus;
import javafx.collections.ListChangeListener.Change;

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
    public void loadStoryData() {
    	List<Story> list = database.readAllStories();
		storyList = FXCollections.observableArrayList(list);
		printStoryData();
		loadTaskData();
    }
    
    public void printStoryData() {
    	for(Story u : storyList) {
    		System.out.println("[model.DataModelStory] Print stories: " + u.toString());
    		System.out.println("[model.DataModelStory] Print story tasks: " +  u.getTasks());
    	}
    }
        
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
    	System.out.println("[model.DataModelStory] Deleting story");
    	storyList.remove(deleteStory);
    	printStoryData();
    }    

    public void updateStory(Story updateStory) {
    	System.out.println("[model.DataModelStory] UpdateStory: " + updateStory.getStoryName());
    	int storyIndex = storyList.indexOf(updateStory);
    	storyList.set(storyIndex, updateStory);
    	printStoryData();
    }
  //----------------------------Task------------------------------------------
    public void loadTaskData() {
    	List<Task> list = new ArrayList<Task>();
    	for(Story s : storyList) {
    		for(Task t : s.getTasks()) {
    			list.add(t);
    		}
    	}
		taskList = FXCollections.observableArrayList(list);	
		printTaskData();
    }
        
    public void printTaskData() {
    	for(Task u : taskList) {
    		System.out.println("[model.DataModelStory] Print tasks: " + u.toString());
    	}
    }
    
    public void createTask(Task newTask) {
    	System.out.println("[model.DataModelStory] Adding new Task to List");
		taskList.add(newTask);
		printTaskData();    	
    }
        
    //deleting existing Task in TaskList
    public void deleteTask(Task deleteTask) {
    	System.out.println("[model.DataModelStory] Deleting Task: " + deleteTask);
    	
    	taskList.remove(deleteTask);
    	loadStoryData();
    	printStoryData();
    	printTaskData();
    }    
    
  //updating existing Task in TaskList
    public void updateTask(Task updateTask) {
    	System.out.println("[model.DataModelStory] UpdateTask: " + updateTask);
    	int taskIndex = taskList.indexOf(updateTask);
    	taskList.set(taskIndex, updateTask);

    	printStoryData();
    	printTaskData();
    } 

}



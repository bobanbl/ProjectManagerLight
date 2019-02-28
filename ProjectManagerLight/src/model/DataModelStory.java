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
    
    private Story getStoryWithID(int storyID) {
    	for(Story s : storyList) {
    		if(s.getStoryID() == storyID) {
    			return s;
    		}
    	}
    	return null;
    }
        
    public void createStory(String description, int duration, String storyName, int positionGridPane, Project project, ProjectUser responsibility) {
    	System.out.println("[model.DataModelStory] Adding new Story to List");
    	Story newStory = new Story();
    	newStory.setDescription(description);
		newStory.setDuration(duration);
		newStory.setStoryName(storyName);
		newStory.setPositionGridPane(positionGridPane);
		newStory.setProject(project);
		newStory.setResponsibility(responsibility);
		storyList.add(newStory);
		printStoryData();    	
    }
            
    public List<Story> getStoriesFromSelectedProject(Project selectedProject){
    	List<Story> selectedStoryList = new ArrayList<Story>();    	
    	for(Story s : storyList) {
    		if(s.getProject().getProjectID() == selectedProject.getProjectID()) { 
    			System.out.println("[model.DataModelStory] SelectedStoryList: " + s.getStoryName());
    			selectedStoryList.add(s);
    		}
    	}
    	return selectedStoryList;
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
    
    public void updateStory(Story updateStory, String storyName, String description, int duration, ProjectUser responsibility) {
    	System.out.println("[model.DataModelStory] UpdateStory: " + updateStory.getStoryName());
    	int storyIndex = storyList.indexOf(updateStory);
    	updateStory.setStoryName(storyName);
    	updateStory.setDescription(description);
    	updateStory.setDuration(duration);
    	updateStory.setResponsibility(responsibility);
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
//    	List<Task> list = database.readAllTasks();
		taskList = FXCollections.observableArrayList(list);	
		printTaskData();
    }
        
    public void printTaskData() {
    	for(Task u : taskList) {
    		System.out.println("[model.DataModelStory] Print tasks: " + u.toString());
    	}
    }
    
    public void createTask(String description, int duration, String TaskName, Story story, ProjectUser responsibility) {
    	System.out.println("[model.DataModelStory] Adding new Task to List");
    	Task newTask = new Task();
    	newTask.setDescription(description);
		newTask.setDuration(duration);
		newTask.setTaskName(TaskName);
		newTask.setStory(story);
		newTask.setStatus(TaskStatus.NEW);
		newTask.setResponsibility(responsibility);
		taskList.add(newTask);
//		loadStoryData();
		printTaskData();    	
    }
    
    public Task getTaskWithID(int taskID) {
    	for(Task t : taskList) {
    		if(t.getTaskID() == taskID) {
    			return t;
    		}
    	}
    	return null;
    }
            
    public List<Task> getTasksFromSelectedStory(List<Story> selectedStoryList){
    	List<Task> selectedTaskList = new ArrayList<Task>();    	
    	for(Task t : taskList) {
    		for(Story s : selectedStoryList) {
    			if(t.getStory().getStoryID() == s.getStoryID()) { 
    				System.out.println("[model.DataModelStory] SelectedTaskList: " + t.getTaskName());
    				selectedTaskList.add(t);
    			}
    		}
    	}
    	return selectedTaskList;
    }
    
    /** Deleting existing Task in TaskList
     * 
     * @param deleteTask
     */
    public void deleteTask(Task deleteTask) {
    	System.out.println("[model.DataModelStory] Deleting Task: " + deleteTask);
    	
    	taskList.remove(deleteTask);
    	loadStoryData();
    	printStoryData();
    	printTaskData();
    }    
      
    public void updateTask(Task updateTask, String TaskName, String description, int duration, TaskStatus taskStatus, ProjectUser responsibility) {
    	System.out.println("[model.DataModelStory] UpdateTask: " + updateTask);
    	int taskIndex = taskList.indexOf(updateTask);
    	updateTask.setTaskName(TaskName);
    	updateTask.setDescription(description);
    	updateTask.setDuration(duration);
//		updateTask.setStory(story);
		updateTask.setStatus(taskStatus);
		updateTask.setResponsibility(responsibility);
    	taskList.set(taskIndex, updateTask);
//    	loadStoryData();
    	printStoryData();
    	printTaskData();
    } 
    
    public void updateTaskStatusStory(Task selectedTask, Story targetStory, TaskStatus targetTaskStatus) {

    	System.out.println("[model.DataModelStory] UpdateTask Drag&Drop: " + selectedTask);
    	int taskIndex = taskList.indexOf(selectedTask);
    	if(selectedTask.getStory().getStoryID() != targetStory.getStoryID()){
    		System.out.println("[model.DataModelStory] UpdateTask Drag&Drop: " + selectedTask.getStory().getStoryID() + " " + targetStory.getStoryID());
    		selectedTask.setStory(targetStory);
    		
    	}
    	selectedTask.setStatus(targetTaskStatus);
    	taskList.set(taskIndex, selectedTask);
//    	loadStoryData();
    	printTaskData();	
    	printStoryData();
    }
    
//    public void updateTaskStatusStory(Task selectedTask, Story targetStory, TaskStatus targetTaskStatus) {
//
//    	System.out.println("[model.DataModelStory] UpdateTask Drag&Drop: " + selectedTask);
//    	int storyIndex = storyList.indexOf(targetStory);
//    	Story storyOLD = selectedTask.getStory();
//    	int storyINdexOLD = storyList.indexOf(storyOLD);
//    	int taskIndex = taskList.indexOf(selectedTask);
//    	if(selectedTask.getStory().getStoryID() != targetStory.getStoryID()){
//    		System.out.println("[model.DataModelStory] UpdateTask Drag&Drop: " + selectedTask.getStory().getStoryID() + " " + targetStory.getStoryID());
////    		int taskID = selectedTask.getTaskID();
//    		targetStory.addTasktoStory(selectedTask);
//    		storyOLD.removeTaskFromStory(selectedTask);
//
//    		
//    	}
//    	selectedTask.setStatus(targetTaskStatus);
//    	storyList.set(storyIndex, targetStory);
//    	storyList.set(storyINdexOLD, storyOLD);
//    	taskList.set(taskIndex, selectedTask);
////    	loadStoryData();
//    	printTaskData();	
//    	printStoryData();
//    }
	
}



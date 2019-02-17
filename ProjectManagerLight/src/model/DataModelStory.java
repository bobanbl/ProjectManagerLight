package model;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class DataModelStory {

	private final DatabaseController database = DatabaseController.getInstance();
	private ObservableList<Story> storyList;
	private ObservableList<Task> taskList;
	
	
	public DataModelStory(){
		loadStoryData();
		printStoryData();
		loadTaskData();
		printTaskData();
		
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
				
		
		
	}
//----------------------------STORY------------------------------------------
    public void loadStoryData() {
    	List<Story> list = database.readAllStories();
		storyList = FXCollections.observableArrayList(list);	
    }
    
    public void printStoryData() {
    	for(Story u : storyList) {
    		System.out.println("[model.DataModelStory]" + u.toString());
    	}
    }
    
    public void createStory(String description, int duration, String storyName, int positionGridPane, Project project) {
    	System.out.println("[model.DataModelStory] Adding new Story to List");
    	Story newStory = new Story();
    	newStory.setDescription(description);
		newStory.setDuration(duration);
		newStory.setStoryName(storyName);
		newStory.setPositionGridPane(positionGridPane);
		newStory.setProject(project);
		storyList.add(newStory);
		printStoryData();    	
    }
        
    public void loadTaskData() {
    	List<Task> list = database.readAllTasks();
		taskList = FXCollections.observableArrayList(list);	
    } 
    
    public void printTaskData() {
    	for(Task u : taskList) {
    		System.out.println("[model.DataModelStory]" + u.toString());
    	}
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
    
    
    public void updateStory(Story updateStory, String storyName, String description, int duration) {
    	System.out.println("[model.DataModelStory] UpdateStory: " + updateStory.getStoryName());
    	int storyIndex = storyList.indexOf(updateStory);
    	updateStory.setStoryName(storyName);
    	updateStory.setDescription(description);
    	updateStory.setDuration(duration);
    	storyList.set(storyIndex, updateStory);
    	printStoryData();
    }
    
    
    
    
	
}

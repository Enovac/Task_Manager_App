package engine;
import java.io.Serializable;
import java.util.*;
public class TaskFolder implements Serializable{
	private ArrayList<Task> taskList;
	private String name;
	private int id;
	private int totalHours;
	private int totalMins;
//===========Constructor==================	
	public TaskFolder(String name,int id) {
		this.name=name;
		this.id=id+1;
		taskList=new ArrayList<Task>();
	}
//======================G&S==========================
	public String getName() {
		return name;
	}
	public int getID() {
		return id;
	}
	public void toggleIsDone(int x) {
		taskList.get(x).toggleIsDone();
	}
	public String getTaskName(int x) {
		return taskList.get(x).getName();
	}
	public int getTaskNum() {
		return taskList.size();
	}
	public boolean isDone(int x) {
		return taskList.get(x).isDone();
	}
	public int getHours(int i) {
		 return taskList.get(i).getHours();
	 }
	 public int getMinutes(int i) {
		 return taskList.get(i).getMinutes();
	 }
	 public int getDay(int i) {
		 return taskList.get(i).getDay();
	 }
	 public int getMonth(int i) {
		 return taskList.get(i).getMonth();
	 }
	 public int getYear(int i) {
		 return taskList.get(i).getYear();
	 }
//===========Methods=======================
	public void addTask(Task task) {
		taskList.add(task);
		Collections.sort(taskList);
	}
	public Task getTask(int i) {
		return taskList.get(i);
	}
	public void deleteTask(int x) {
		if(totalMins<taskList.get(x).getMinutes()) {
			totalMins+=60;
			totalHours-=1;
		}
		totalMins-=taskList.get(x).getMinutes();
		totalHours-=taskList.get(x).getHours();
		taskList.remove(x);
		Collections.sort(taskList);
	}
	public void setTotalTime() {
		Task task=taskList.get(taskList.size()-1);
		totalHours+=task.getHours()+(task.getMinutes()+totalMins)/60;
		totalMins=(task.getMinutes()+totalMins)%60;
	}
	public int getTotalHours() {
		return totalHours;
	}
	public int getTotalMins() {
		return totalMins;
	}
	
}

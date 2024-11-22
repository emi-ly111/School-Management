package src.models;

import java.util.List;


public class Course {
	private String id;
	private String name;
	private int courseLoad;
	private Professor professor;
	private List<String> subscribedUsersId; 
	
	public Course(String id, String name, int courseLoad, Professor professor) {
		this.id = id;
		this.name = name;
		this.courseLoad = courseLoad;
		this.professor = professor;
	}
	
	public Course(String name, int courseLoad, Professor professor) {
		this.name = name;
		this.courseLoad = courseLoad;
		this.professor = professor;
	}
	
	
	public void subscribeUserToCourse(String userId) {
		boolean userAlreadyInList = this.subscribedUsersId.contains(userId);
		
		if(userAlreadyInList) {
			return;
		}
		
		this.subscribedUsersId.add(userId);
	}
	
	public void unsubscribeUserFromCourse(String userId) {
		boolean isUserAlreadyOutOfCourse = !this.subscribedUsersId.contains(userId);
		
		if(isUserAlreadyOutOfCourse) {
			return;
		}
		
		this.subscribedUsersId.remove(userId);
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCourseLoad() {
		return this.courseLoad;
	}
	
	public Professor getProfessor() {
		return this.professor;
	}
	
	public List<String> getSubscribedUsersIds() {
		return this.subscribedUsersId;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCourseLoad(int courseLoad) {
		this.courseLoad = courseLoad;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	
}

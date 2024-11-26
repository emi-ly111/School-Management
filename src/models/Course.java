package src.models;

import java.util.List;

import src.controllers.CourseController;


public class Course {
	private int id;
	private String name;
	private int courseLoad;
	private Professor professor;
	private CourseController courseController = new CourseController();
	private List<String> subscribedStudentsId; 
	
	public Course(int id, String name, int courseLoad, Professor professor) {
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
		boolean userAlreadyInList = this.subscribedStudentsId.contains(userId);
		
		if(userAlreadyInList) {
			return;
		}
		
		this.subscribedStudentsId.add(userId);
		
	}
	
	public void unsubscribeUserFromCourse(String userId) {
		boolean isUserAlreadyOutOfCourse = !this.subscribedStudentsId.contains(userId);
		
		if(isUserAlreadyOutOfCourse) {
			return;
		}
		
		this.subscribedStudentsId.remove(userId);
	}
	
	public int getId() {
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
	
	public List<String> getSubscribedStudentsIds() {
		return this.subscribedStudentsId;
	}
	
	public void setId(int id) {
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

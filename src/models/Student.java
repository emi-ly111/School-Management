package src.models;

public class Student extends Person {
	private String registration;
	
	public Student(String id, String name, int age, String registration) {
		super(id, name, age);
		this.registration = registration;
	}
	
	public Student(String name, int age, String registration) {
		super(name, age);
		this.registration = registration;
	}
	
	public String getRegistration() {
		return this.registration;
	}
	
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	
	public void showData() {
		System.out.printf("ID: %s;\nNome: %s;\nIdade: %d;\nMatrícula: %s.\n", this.getId(), this.getName(), this.getAge(), this.registration);
	}
}

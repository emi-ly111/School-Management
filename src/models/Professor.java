package src.models;

public class Professor extends Person{
	private String specialty;
	private String registration;
	
	public Professor(int id, String name, int age, String specialty, String registration) {
		super (id, name, age);
		this.specialty = specialty;
		this.registration = registration;
	}
	
	public Professor(String name, int age, String specialty, String registration) {
		super (name, age);
		this.specialty = specialty;
		this.registration = registration;
	}
	
	public String getSpecialty() {
		return this.specialty;
	}

	public String getRegistration() {
		return this.registration;
	}
	
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}
	
	public void showData() {
		System.out.printf("ID: %s;\nNome: %s;\nIdade: %d;\nEspecialidade: %s;\nMatrícula: %s.\n", this.getId(), this.getName(), this.getAge(), this.specialty, this.registration);
	}
}

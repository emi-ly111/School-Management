package src.models;

public class Professor extends Person{
	private String specialty;
	
	public Professor(String id, String name, int age, String specialty) {
		super (id, name, age);
		this.specialty = specialty;
	}
	
	public Professor(String name, int age, String specialty) {
		super (name, age);
		this.specialty = specialty;
	}
	
	public String getSpecialty() {
		return this.specialty;
	}
	
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public void showData() {
		System.out.printf("ID: %s;\nNome: %s;\nIdade: %d;\nEspecialidade: %s.\n", this.getId(), this.getName(), this.getAge(), this.specialty);
	}
}

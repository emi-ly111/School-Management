package src.models;

public class Person {
	private int id;
	private String name;
	private int age;
	
	public Person(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public Person(String name, int age) {		
		this.name = name;
		this.age = age;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void showData() {
		System.out.printf("ID: %s;\nNome: %s;\nIdade: %d.\n", this.id, this.name, this.age);
	}
}

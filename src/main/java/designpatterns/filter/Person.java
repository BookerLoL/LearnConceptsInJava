package designpatterns.filter;

public class Person {
	public enum Sex {
		MALE, FEMALE;
	}

	public String name;
	public Sex gender;
	public int age;

	public Person(String name, int age, Sex gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
}

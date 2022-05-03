package designpatterns.dataaccessobject;

public class Student {
	private static int studentCount = 0;
	
	public String name;
	public int grade;
	public int id;
	
	public Student(String name, int grade) {
		this.name = name;
		this.grade = grade;
		studentCount++;
		id = studentCount;
	}
}

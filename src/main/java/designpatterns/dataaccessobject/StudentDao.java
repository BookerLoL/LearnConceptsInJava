package designpatterns.dataaccessobject;

import java.util.Collection;

public interface StudentDao {
	public Collection<Student> getAllStudents();
	public Student getStudent(int id);
}

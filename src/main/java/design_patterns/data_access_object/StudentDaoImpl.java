package designpatterns.dataaccessobject;

import java.util.Collection;
import java.util.Collections;

public class StudentDaoImpl implements StudentDao {

	@Override
	public Collection<Student> getAllStudents() {
		return Collections.emptyList();
	}

	@Override
	public Student getStudent(int id) {
		return null;
	}

}

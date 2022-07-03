package designpatterns.bridge;

public class Tester {

	public static void main(String[] args) {
		StorageRepository<Student> fileRepo = new FileRepository<>();
		StorageRepository<Student> dbRepo = new DatabaseRepository<>();

		StudentRepository<Student> studentRepo = new StudentRepository<>(fileRepo);
		Student student = new Student("Test Student");
		studentRepo.store(student);

		studentRepo = new StudentRepository<>(dbRepo);
		studentRepo.store(student);
	}

}

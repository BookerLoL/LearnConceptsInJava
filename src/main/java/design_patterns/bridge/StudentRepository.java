package designpatterns.bridge;

public class StudentRepository<T extends Student> implements BaseRepository<T> {
	private StorageRepository<T> storageRepository;

	public StudentRepository(StorageRepository<T> repository) {
		storageRepository = repository;
	}

	@Override
	public T store(T student) {
		if (student == null || student.getName() == null) {
			return null;
		}

		storageRepository.store(student);
		return student;
	}

	@Override
	public StorageRepository<T> getRepository() {
		return storageRepository;
	}

}

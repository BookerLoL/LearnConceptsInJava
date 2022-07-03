package designpatterns.bridge;

public class FileRepository<T extends Entity> implements StorageRepository<T> {
	@Override
	public T store(T entity) {
		System.out.println("Storing in file");
		return entity;
	}

}

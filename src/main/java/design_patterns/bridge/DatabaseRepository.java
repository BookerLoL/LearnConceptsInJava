package designpatterns.bridge;

public class DatabaseRepository<T extends Entity> implements StorageRepository<T> {
	@Override
	public T store(T entity) {
		System.out.println("Storing in db");
		return entity;
	}
}

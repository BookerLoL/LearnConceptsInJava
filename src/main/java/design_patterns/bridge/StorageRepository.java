package designpatterns.bridge;

public interface StorageRepository<T extends Entity> {
	T store(T entity);
}

package designpatterns.bridge;

public interface BaseRepository<T extends Entity> extends StorageRepository<T> {
	StorageRepository<T> getRepository();
}

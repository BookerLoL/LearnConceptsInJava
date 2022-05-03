package designpatterns.observer;

public interface Observable<T> {
	void notify(T data);

	void addObserver(Observer<T> observer);

	void removeObserver(Observer<T> observer);
}

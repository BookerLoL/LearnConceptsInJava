package designpatterns.facade;

public interface Filter<T> {
	T filter(T items);
}

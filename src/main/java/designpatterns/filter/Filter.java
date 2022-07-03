package designpatterns.filter;

import java.util.Objects;

@FunctionalInterface
public interface Filter<T> {
	boolean test(T data);

	public default Filter<T> and(Filter<T> other) {
		Objects.requireNonNull(other);
		return data -> test(data) && other.test(data);
	}

	public default Filter<T> or(Filter<T> other) {
		Objects.requireNonNull(other);
		return data -> test(data) || other.test(data);
	}

	public default Filter<T> not() {
		return data -> !test(data);
	}
}

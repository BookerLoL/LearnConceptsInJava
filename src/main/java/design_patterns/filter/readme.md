# Filter Design Pattern

- Also knwown as **Criteria**
- Filter set of objects and chain them

```java
public interface Filter<T, U> {
    public U criteria(T t);
}

public class AndCriteria<T> implements Criteria<T> {
    private Filter<T> leftCondition;
    private Filter<T> rightCondition;

    public AndFilter(Filter<T> left, Filter<T> right) {
        leftCondition = left;
        rightCondition = right;
    }

    @Override
    public boolean matches(T candidate) {
        return leftCondition.matches(candidate) &&
               rightCondition.matches(candidate);
    }
}

//Have a bunch of classes that implement Filter
//Can implement AND filter and OR filter, etc
```

Functional Approach

```java
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
```

# Decorator Design Pattern

**Structural** Design Pattern

Add additional functionality to an existing object without changing the structure.

```java
import java.util.Objects;
import java.util.Collection;

public interface Collector<T> {
    Collection<T> getCollection();
}

public class DefaultCollector<T> implements Collector<T> {
    private Collection<T> collection;

    public DefaultCollector(Collection<T> collection) {
        this.collection = collection;
    }

    @Override
    public Collection<T> getCollection() {
        return collection;
    }
}

public class NonNullCollectorDecorator<T> implements Collector<T> {
    private Collector<T> collector;

    public NonNullCollectorDecorator(Collector<T> collector) {
        this.collector = collector;
    }

    @Override
    public Collection<T> getCollection() {
        return collector.getCollection().stream().filter(Objects::nonNull).toList();
    }
}

public class Example {
    public static void main(String[] args) {
        Collection<Integer> numbers = List.of(1, 2, 3, null);
        Collector<Integer> nonNullCollector = new NonNullCollectorDecorator<>(new DefaultCollector<>(numbers));
    }
}
```

Functional Approach

```java
//Decorator pattern
public void setFilters(final Function<Color, Color>... filters) {
        Function<Color, Color> f =
                Stream.of(filters)
                        .reduce(Function::compose)
                        .orElse(color -> color);
}


setFilters(Color::brighter, Color::darker);
```

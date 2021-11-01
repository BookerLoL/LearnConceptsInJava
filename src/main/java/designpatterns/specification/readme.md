# Specification Design Pattern

**Architectural** Design Pattern

**Behavioral** Design Pattern

Reusable expression specifications and combine them for validation

```java
import java.util.List;

public abstract class AbstractSelector<T> implements Predicate<T> {
    public AbstractSelect<T> and(AbstractSelector<T> other) {
        return new AndSelector(this, other);
    }
}

public class AndSelector<T> extends AbstractSelector<T> {
    private final Collection<AbstractSelector<T>> selectors;

    @SafeVarargs
    AndSelector(AbstractSelector<T>... selectors) {
        this.selectors = List.of(selectors);
    }
    
    @Override
    public boolean test(T t) {
        return selectors.stream().allMatch(selector -> selector.test(t));
    }
}

public class GreaterThan extends AbstractSelector<Integer> {
    private Integer number;
    public GreaterThan(Integer i) {
        number = i;
    }
    
    @Override
    public boolean test(Integer i) {
        return i.compareTo(number) > 0;
    }
} 
```
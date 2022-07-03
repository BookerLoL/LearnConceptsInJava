# Visitor Design Pattern

- Need to impelment new functionality but not change hiearchry
- Essentially a double dispatch

Functional Appraoch

```java
public class Visitor<R> {
    private final HashMap<Class<?>, Function<Object, R>> map = new HashMap<>();

    public <T> visitor<R> when(
        Class<T> type, Function<T, R> fun) {
            map.put(type, f.compose(type::cast));
        }
    )

    public R accept(Object receiver) {
        return map.getOrDefault(receiver.getClass(), r -> {throw ...}).apply(receiver);
    }
}
```

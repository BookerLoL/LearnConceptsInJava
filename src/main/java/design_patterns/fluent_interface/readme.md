# Fluent Interface Design Pattern

Provides an easy, readable, and flowing interface read like human language

```java
public interface Fluent<T> {
    Fluent<T> filter(Predicate<? super T> predicate);
    Optional<T> first();
    Fluent<T> first(int count);
    List<T> asList();
}
```

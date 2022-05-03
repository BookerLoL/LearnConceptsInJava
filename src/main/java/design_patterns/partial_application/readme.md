# Partial Application Design Pattern

- A functional design pattern to get rid of extra parameters of redundant similar redundant code

```java
public interface Logger {
    void log(String message);
}

public interface LeveledLogger {
    void log(Level level, String message);

    default Logger level(Level level) {
        return msg -> System.out.println("Level: " + level + " " + msg);
    }
}
```

- Java also has partial application built in with lambda reference

```java
ToIntFunction<String> lengthCalculator = String::length;
IntSupplier supplier = "String"::length;

public static <T, R> Supplier<R> partial(Function<T, R> function, T parameterValue) {
    return () -> function.apply(value);
}

//Just an example of how someone could use this
Supplier<Object> partialCreation = partial(Object::new, paramValue);
```

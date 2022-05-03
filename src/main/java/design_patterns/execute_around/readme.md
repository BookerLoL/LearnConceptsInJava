# Execute Around Design Pattern

- Helps elminate boilerplate code
- Write method that does something every code must do
- Functional design pattern

```java
public interface Timeable {
    public void execute();
}

public class Timer {
    public class meaure(Timeable timeableAction) {
        long startTime = System.nanoTime();
        timeableAction.execute();
        long elapsedTime = System.nanoTime() - startTime;
    }
}
```

```java
public interface Consumer<T extends Closeable, X extends Throwable> {
    void consume(T instance) throws X;
}

//Similar to what JUnit 5 does
public static <X extends Throwable> Throwable assertThrows(Class<X> expectedExceptionClass, Runnable block) {
    try {
        block.run();
    } catch (Throwable ex) {
        if (expectedExceptionClass.isInstance(ex)) {
            return ex;
        }
    }
    fail("Didn't throw expected exception");
    return null;
}
```

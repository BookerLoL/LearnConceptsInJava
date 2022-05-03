# Callback Design Pattern

- Allow executable code to be passed in as an argument and called later on

- [Source](https://java-design-patterns.com/patterns/callback/)

```java
import java.util.Optional;

public interface Callback {
    void call();
}

public abstract class Task {
    final void executeAfter(Callback callback) {
        execute();
        Optional.ofNullable(callback).ifPresent(Callback::call);
    }

    public abstract void execute();
}
```

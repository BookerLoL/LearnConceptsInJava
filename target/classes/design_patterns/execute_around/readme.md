# Execute Around Design Pattern

- Helps elminate boilerplate code
- Write method that does something every code must do

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

# Pipeline

- Break down operations into sepearte pieces that could be tested and executed independently

```java
public interface Step<I, O> {
    O execute(I input);

    default <R> Step<I, R> pipe(Step<O, R> source) {
        return value -> source.execute(execute(value));
    }

    static <I, O> Step<I, O> of(Step<I, O> source) {
        return source;
    }
}
```

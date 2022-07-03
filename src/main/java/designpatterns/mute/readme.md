# Mute Design Pattern

Runs code and ignores all exceptions but continues running

- Useful for functional code

```java
public interface Muter {
    default mute(Callable<?> consumer) {
        try {
            consumer.call();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    default mute(Runnable action) {
        try {
             action.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    default loggedMute(Callable<?> consumer, Logger logger) {
         try {
             action.run();
        } catch (Exception ex) {
            logger.log(ex);
        }
    }
}
```

Unchecked exceptions handling

```java
import java.util.function.Function;

@FunctionalInterface
public interface Func<T, U, V extends Exception> {
    U apply(T t) throws V;

    public static <T, U, V extends Exception> Function<T, U> run(Func<T, U, V> f) {
        return arg -> {
            try {
                return f.apply(arg);
            } catch (Exception e) {
                throwAsUnchecked(e);
            }
            return null;
        };
    }

    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E {
        throw (E) exception;
    }
}
```

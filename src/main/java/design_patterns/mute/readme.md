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

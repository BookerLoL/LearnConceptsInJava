# State Design Pattern

- Alter behavior when internal state changes
  - Different objects to represent state of the object
  - Often pass the object to other instances
- [Source](https://www.youtube.com/watch?v=-k2X7guaArU)

Functional Approach

```java
public class Logger {
    private final Consumer<String> error, warning;
    private final Logger noisy, quiet;

    private Logger(Consumer<String> error, Consumer<String> warning, Function<Logger, Logger> quietFactory, Function<Logger, Logger> noisyFactory) {
        this.error = error;
        this.warning = warning;
        this.quiet = quietFactory.apply(this);
        this.noisy = noisyFactory.apply(this);
    }

    public Logger quiet() {
        return quiet;
    }
    public Logger noisy() {
        return noisy;
    }

    public static Logger logger(Consumer<String> consumer) {
        return new Logger(consumer, consumer, noisy -> new Logger(consumer, msg -> {}. identity(), it -> noisy), identity());
    }
}
```

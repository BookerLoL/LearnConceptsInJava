# Feature Flag Design Pattern

- Also known as **Feature Toggle**
- Great for quickly turning off or on different features

```java
public interface Service {
    public boolean isEnhanced();
    public boolean isOn();
}

public class FeatureToggler implements Service {
    private boolean isEnhanced;
    private boolean isOn;

    //Implement methods
}

public class TieredServie implements Service {
    public enum Level {
        DRAFT, TEST, LIVE;
    }

    public TieredService(Level level) {
        //omit
    }
}
```

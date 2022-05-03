# Throttling Design Pattern

- Rate-limit access to resource
- [Source](https://java-design-patterns.com/patterns/throttling/)

```java
public class User {
    private final String name;
    private final int allowedCallsPerSecond;

    public User(String name, int allowedCallsPerSecond, CallsCount callsCount) {
        this.name = name;
        this.allowedCallsPerSecond = allowedCallsPerSecond;
        callsCount.addUser(name);
    }
}

public final class CallsCount {
    private final Map<String, AtomicLong> userCallsCount = new ConcurrentHashMap<>();

    public void addUser(String username) {
        userCallsCount.putIfAbsent(username, new AtomicLong(0));
    }

    //increment/getCount/reset based on username
}

public interface Throttler {
    void state();
}

public class ThrottleResetter implements Throttler {
  private final int throttlePeriod;
  private final CallsCount callsCount;

  public ThrottleTimerImpl(int throttlePeriod, CallsCount callsCount) {
      this.throttlePeriod = throttlePeriod;
      this.callsCount = callsCount;
  }

  @Override
  public void start() {
    new Timer(true).schedule(new TimerTask() {
      @Override
      public void run() {
        callsCount.reset();
      }
    }, 0, throttlePeriod);
  }
}

public class Service {
    private CallsCount counter;

    public Service(Throttler timer, CallsCount callsCount) {
        //...
    }

    //methods that use callscount
}
```

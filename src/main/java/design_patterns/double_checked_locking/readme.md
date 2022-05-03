# Double Checked Locking

Attempts to reduce the number of acquired locks by checking conditional before locking

```java
public class SharedObject {};

public class Singleton {
    private static SharedObject instance;

    public static SharedObject getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new SharedObject();
                }
            }
        }
        return instance;
    }
}
```

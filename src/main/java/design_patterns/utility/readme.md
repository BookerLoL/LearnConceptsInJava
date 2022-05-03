# Utility Classes

- Typically an anti-pattern where a class provides static methods and a private constructor
- Typically called "utility" or "helper" class

```java
//Sometimes people prefer public constructors to avoid mocking static methods
public class MyClassHelper {
    private MyClassHelper() {}

    public static void doSomething() {}
}
```

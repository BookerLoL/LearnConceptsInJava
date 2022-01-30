# Virtual Proxy

- Creates expensive objects on demand when needed
  - Often times, a lighter (virtual proxy) version of the object will be used

```java
public interface SomeInterface {
    //omit methods
}

public class ExpensiveObject implements SomeInterface {}
public class CheapObject implements SomeInterface {
    //Uses less memory to display the information needed for usage
}
```

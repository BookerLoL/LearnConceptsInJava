# Two-Way Adapter / N-way adapter

- Essentially an adapter class that implements all the Target and Adaptee interfaces
  - Ensure they are interfaces

```java
// Better to implement interfaces rather than extending
public class NWayAdapter extends TargetService, AdapteeService, OtherServices {
  //implement methods, take a constructor that accepts those types and call delegate
}
```

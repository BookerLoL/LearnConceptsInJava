# Bridge Design Pattern

- Structural design pattern
- Breaks up a class into two separate hierarchies
  - Allows for abstraction and implementation
- Essentially uses composition of interfaces and implements that interface

```java
public abstract class Shape {}
public class Circle extends Shape {}


public interface Colorable {
    void color();
}

public class RedCircleColorable extends Circle implements Colorable {
      Colorable colorer;
}
```

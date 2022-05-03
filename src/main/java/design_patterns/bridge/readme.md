# Bridge Design Pattern

- Structural design pattern
- Breaks up a class into two separate hierarchies
  - Allows for abstraction and implementation

```java
public abstract class Shape {
    Colorable colorer;
}
public class Circle extends Shape {}


public interface Colorable {
    void color();
}

public class RedCircleColorable implements Colorable {}
```

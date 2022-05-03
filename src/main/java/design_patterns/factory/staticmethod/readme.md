# Static Factory Method

- Static method to create objects

```java
import java.util.Optional;

public interface Shape {}
public class Circle implements Shape {}
public class Square implements Shape {}

public class ShapeUtility {
    public static Optional<Shape> create(String name) {
        name = name == null ? "" : name.toLowerCase();

        Shape shape = null;
        if (name.equals("circle")) {
            shape = new Circle();
        } else if (name.equals("square")) {
            shape = new Square();
        }

        return Optional.ofNullable(shape);
    }
}
```

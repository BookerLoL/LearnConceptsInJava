# Factory Method

- An interface that helps define what a conrete factory class should create

```java
import java.util.Optional;

public interface Shape {}
public interface ShapeFactory {
    Shape getShape();
}
public class Circle implements Shape {}
public class Square implements Shape {}


public class SquareShapeFactory implements ShapeFactory {
    public Shape getShape() {
        return new Square();
    }
}

public class CircleShapeFactory implements ShapeFactory {
    public Shape getShape() {
        return new Circle();
    }
}
```

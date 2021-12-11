# Simple Factory Method

- A class that has a method that creates objects and is overridable

```java
import java.util.Optional;

public interface Shape {}
public class Circle implements Shape {}
public class Square implements Shape {}

public class ShapeFactory {
    private String desiredShape;

    public ShapeFactory(String shape) {
        this.desiredShape = shape == null ? "" : shape.toLowerCase();
    }

    public Optional<Shape> getShape() {
        Shape shape = null;
        if (desiredShape.equals("circle")) {
            shape = new Circle();
        } else if (desiredShape.equals("square")) {
            shape = new Square();
        }

        return Optional.ofNullable(shape);
    }
}

```

Functional Approach

```java

public interface Shape {}
public class Circle implements Shape {}
public class Square implements Shape {}

public class ShapeFactory {
    private static final  Map<String, Supplier<Shape>> availableShapes = createAvailableShapes();

    private static HashMap<String, Supplier<Shape>> createAvailableShapes() {
        HashMap<String, Supplier<Shape>> shapes = new HashMap<>();
        shapes.put("circle", Circle::new);
        shapes.put("square", Square::new);
        return shapes;
    }

    public Optional<Shape> getShape(String shape) {
        Supplier<Shape> shapeSupplier = availableShapes.get(shape);
        return shapeSuppler != null ? Optional.of(shapeSupplier.get()) : Optional.empty();
    }
}
```

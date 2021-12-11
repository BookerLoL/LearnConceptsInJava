# Abstract Factory

- Essential Factory method but with other related creation methods in the interface

```java
public interface Material {}
public interface Model {}

public interface PartsFactory {
    public Material getMaterial();
    public Model getModel();
}

//factories that implement PartsFactory
//Omitted classes that implement Material and Model
```

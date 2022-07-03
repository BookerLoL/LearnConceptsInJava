# Object Adapater

Composition of the incompatible type, and implements the interface of other type

```java
public interface Displayable {
    void display();
}

public class ObjectAdapter implements Dispayable {
    IncompatibleService service;

    public void display() {
        service.show();
    }
}

```

# Class adapter

Extends one type and implements the interface of other type

```java
public interface Displayable {
    void display();
}

public class IncompatibleService {
    void show();
}

public ClassAdapter extends IncompatibleService implements Dispayable {
    public void display() {
        show();
    }
}
```

# Type Object Design Pattern

Behavioral Design Pattern

- Can add/modify types without recompile or change code
  - Only the data of types is different, not behavior
- [Source](https://gameprogrammingpatterns.com/type-object.html)

```java
public class Type {
    String name;
    //other fields

    public Instance make() {
        return new Instance(this);
    }
}

public class Instance {
    private Type type;
}
```

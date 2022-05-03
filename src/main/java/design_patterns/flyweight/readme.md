# Flyweight

- Reuse of a set of already initialized objects to avoid creating additional objects

```java
public class FlyweightFactor {
    private Map<Class<SomeObject>, SomeObject> mappings;
    public SomeObject get(Class<? extends SomeObject> type) {
        mappings.ifAbsent(type -> createNewObject(type));
        return mappings.get(type);
    }

    private SomeObject createNewObject(Class<SomeObject> type) {
        //some logic to create new class
    }
}
```

- Often times, instances will have already been created and just stored somewhere that can be used.

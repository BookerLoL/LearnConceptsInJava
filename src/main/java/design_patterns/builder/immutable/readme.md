# Immutable Builder

- Great pattern if want to create immutable builders
- Essentially creates a new instance for all methods

```java
public class ImmutableBuilder() {
    //fields omitted
    private ImmutableBuilder(ImmutableBuilder builder) {
        //copy constructor
    }

    public ImmutableBuilder withStreetName(String streetName) {
        this.streetName = streetName;
        return new ImmutableBuilder(this);
    }

    //etc
}
```

- Functional appraoch

```java
public class ImmutableBuilder() {
    //fields omitted
    private ImmutableBuilder(ImmutableBuilder builder) {
        //copy constructor
    }


    public ImmutableBuilder with(Consumer<ImmutableBuilder> builder) {
        builder.apply(this);
        return new ImmutableBuilder(this);
    }
}
```

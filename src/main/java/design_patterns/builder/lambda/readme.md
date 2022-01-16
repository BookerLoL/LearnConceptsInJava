# Lambda Builder Design Pattern

- Prevents users from passing around a Builder and creating more objects.
  - Useful if you want to limit builder to one per object creation
- [Source](https://www.youtube.com/watch?v=-k2X7guaArU)

```java
public interface Builder<T> {
    void register(String name, Supplier<T> supplier);
}

interface VehicleFactory {
    Vehicle create(String name);

    static VehicleFactory factory(Consumer<Builder<Vehicle>> consumer) {
        HashMap<String, Supplier<Vehicle>> map = new HashMap<>();
        consumer.accept(map::put);
        return name -> map.getOrDefault(name, () -> {
            throw new NoSuchElementException();
        }).get();
    }
}

interface Vehicle {}
```

- Can do better be providing a method that does all you need

```java
public static <U, V> Function<U, V> factoryKit(Consumer<BiConsumer<U, V>> consumer, Function<U, V> ifAbsent) {
        HashMap<U, V> map = new HashMap<>();
        consumer.accept(map::put);
        return key -> map.computeIfAbsent(key, ifAbsent);
}


Function<String, Supplier<Vehicle>> factory = factoryKit(builder -> {
    builder.accept("car", Car::new);
    builder.accept("bike", Bike::new);
}, name -> {throw new NoSuchElementException("Unknown vehicle name: " + name); });
```

- Other functional example

```java
public class PersonBuilder {
    public String name;
    public int age;
    //more fields
    public PersonBuilder with(Consumer<PersonBuilder> builder) {
        builder.accept(this);
        return this;
    }

    public PersonBuilder() {
        return new PersonBuilder(name, age);
    }
}
```

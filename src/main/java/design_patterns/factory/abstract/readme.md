# Abstract Factory

- Essential Factory method but with other related creation methods in the interface

```java
public interface Material {}
public interface Model {}

public interface PartsFactory {
    public Material getMaterial();
    public Model getModel();
}

//factories that implement PartsFactory ex: ExpensivePartsFactory, CheapPartsFactory, etc
//Omitted classes that implement Material and Model
```

Functional Appraoch

```java
public interface Animal {}
public interface Domestic {}
public interface Wild {}

public class Dog implements Animal, Domestic {
    public Dog(String name) {
        //just an example
    }
}


public interface AnimalFactory {
    public Animal create();
}

public List<Animal> create(AnimalFactory animalFactory, int numAnimals) {
    return IntStream.range(0, numAnimals).mapToObj(i -> factory::create).collect(Collectors.toList());
}

public static <T, R> Supplier<R> partial(Function<T, R> function, T parameterValue) {
    return () -> function.apply(value);
}

List<Animal> animals = create(partial(Dog::new, "Dog name"), 10);
```

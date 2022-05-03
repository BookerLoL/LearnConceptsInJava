# Repository

- Creates an abstraction between data layer and handling domain objects

```java
public interface Repository<T> {
    List<T> getAll();
    T create(T entity);
    T update(T entity);
    T delete(T entity);
}

public interface FlexibleRepository<T, U> extends Repository<T> {
    T getById(U id);
}

public interface SomeDaoRepsitory implements FlexibleRepository<SomeObject, Long> {
//add other methods
}
```

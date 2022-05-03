# Unit of Work

- Meant to reduce the round trips to DB and run all updates as one unit of work

```java
public interface UnitOfWork<T> {
    public enum Operation {
        INSERT, DELETE, MODIFY;
    }

    public void create(T entity);
    public void modify(T entity);
    public void delete(T entity);
    public void commit();
}

public class Repository<T> implements UnitOfWork<T> {
    private final Map<Operation, List<T>> context;
    private final Database<T> database;

    public Repository(Database<T> database) {
        context = new EnumHashMap<>();
        this.database = database;
    }

    @Override
    public void create(T entity) {
        //add to create list
    }

    @Override
    public void modify(T entity) {
        //add to modify list
    }

    @Override
    public void delete(T entity) {
        //add to delete list
    }

    @Override
    public void commit() {
        //do commit work for all the operations
    }
}
```

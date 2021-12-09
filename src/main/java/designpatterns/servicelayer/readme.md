# Service Layer

- Adds abstraction over domain logic
- Exposes set of available operations

```java
public class Entity extends BaseEntity {
    //have id, and other fields
}


public interface EntityDao extends Dao<Entity> {
    Entity findByName(String name);
}

public class EntityDaoImpl extends DaoBase<Entity> {
    public Entity findByName(String name) {
        //transaction work
        return entity;
    }
}

// This will allow many different implementations
public interface SomeService {
    List<Entity> getAllData();
    List<Integer> getAllActiveIds();
    List<OtherData> getRareData(String name);
}

//These implementation classes often require DAOs
public class NullSafeSomeServiceImpl implements SomeService {
    //implement methods
}

public class DefaultSomeServiceImpl implements SomeService {

}
```

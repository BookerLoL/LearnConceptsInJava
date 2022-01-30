# Object Pool

- Having a set number of resources that can be in use to avoid overhead of allocating / removing

  - Such examples may be DB Connections

- [Source](https://sourcemaking.com/design_patterns/object_pool/java)

```java
public abstract class ObjectPool<T> {
    private long expirationTime;
    private HashMap<T, Long> locked, unlocked;

    public abstract T create();

    public synchronized T checkout() {
        //omit logic to retrieve object
        //maybe validate, expire, etc
        //If none, may need to create
    }

    public synchronized void remove(T t) {
        locked.remove(t);
        unlocked.put(t, System.nanoTime());
    }
}
```

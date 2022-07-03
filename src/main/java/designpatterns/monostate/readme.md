# Monostate Design Pattern

Maintains a shared state across all instances.

- Can create as many instances, but data is all the same

- Often confused with Singleton which cares only about maintaining one instance

```java
import java.util.Collections;
import java.util.TreeSet;

//Monostate object
public class GlobalCounter {
    //All global counters will share same object counter
    protected static final HashMap<Object, Integer> OBJ_COUNTER = new HashMap<>();

    public final increment(Object o) {
        OBJ_COUNTER.compute(o, (k, v) -> v == null ? 1 : v + 1);
    }

    public Integer getCount(Object o) {
        return OBJ_COUNTER.getOrDefault(o, 0);
    }
}

//Extensions of Monostate class
public class SortedGlobalTable extends GlobalCounter {
    public Iterator<Entry<Object, Integer>> highestCount() {
        datastructures.list<Entry<K, V>> list = new ArrayList<>(OBJ_COUNTER.entrySet());
        list.sort(Entry.comparingByValue());
        return list;
    }
}
```

- Other examples

```java
public class Monostate {
    private static int someData = 0;

    public int getData() {
        return someData;
    }

    public void setData(int data) {
        this.someData = data;
    }
}
```

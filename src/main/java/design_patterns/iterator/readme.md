# Iterator Design Pattern

**Behavioral** Design Pattern

Allows you to traverse contents of a container object without exposing underlying representation

```java
interface Iterable<T> {
    Iterator<T> getIterator();
}

interface Iterator<T> {
    T next();

    boolean hasNext();
}

public class FakeCollection implements Iterable<Object> {
    private List<Object> list = new ArrayList<>();

    public Iterator<Object> getIterator() {
        return new FakeCollectionIterator();
    }

    protected class FakeCollectionIterator implements Iterator<Object> {
        int index = 0;

        Object next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more items to iterate");
            }
            Object nextObject = list.get(index);
            index++;
            return nextObject;
        }

        boolean hasNext() {
            return index < list.size();
        }
    }
}

public class Example {
    public static void main(String[] args) {
        FakeCollection c = new FakeCollection();
        Iterator<Object> iter = c.getIterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
```

Functional Approach

```java
//No need to implement iterator, but can't be used for for-enhanced loops anymore
public class Container {
    private int size;
    private Object[] elements;
    //...

    public void forEach(Consumer<Object> action) {
        for (int i = 0; i < size; i++) {
            action.accept(elements[i]);
        }
    }
}
```

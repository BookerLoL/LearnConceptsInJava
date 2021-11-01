# Lazy Evaluation Design Pattern

Evaluate results until needed.

There are many different ways of designing

```java
//DecoratingIterator omitted

public class LazyEvaluation<T> extends Iterable<T> {
    private final Iterable<T> iterable;
    
    protected LazyEvaluation() {
        iterable = this;
    }
    
    public LazyEvaluation<T> filter(Predicate<? super E> predicate) {
        return new LazyEvaluation<>() {
            @Override 
            public Iterator<T> iterator() {
                return new DecoratingIterator<>(iterable.iterator()) {
                    @Override 
                    public E next() {
                        while (decoratingIterator.hasNext()) {
                            E item = decoratingIterator.next();
                            if (predicate.test(item)) {
                                return candidate;
                            }
                        }
                        return null;
                    }
                }
            }
        }
    }
    
    public Iterator<T> iterator() {
        return new DecoratingIterator<>(iterable.iterator());
    }
}
```
# Trampoline

- **Behavioral design pattern**
- Other names: **thunk**, **tail call optimization**
- [Source](https://java-design-patterns.com/patterns/trampoline/)
- Helps to implement recursive functions without stack issues

```java
    @FunctionalInterface
    public interface Trampoline<T> {
        Trampoline<T> apply();

        default boolean isComplete() {
            return false;
        }

        default T result() {
            throw new Error("Not Implemented");
        }

        default T invoke() {
            return Stream.iterate(this, Trampoline::apply).filter(Trampoline::isComplete).findFirst().get().result();
        }

        public static <T> Trampoline<T> call(Trampoline<T> nextCall) {
            return nextCall;
        }

        public static <T> Trampoline<T> done(T value) {
            return new Trampoline<T>() {
                public boolean isComplete() { return true; }
                public T result() { return value; }
                public Trampoline<T> apply() {
                    throw new Error("Not Implemented");
                }
            };
        }
    }
```

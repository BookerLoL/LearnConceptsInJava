# Thread Local Storage

- Helps secure access to updatable variables from other threads
- [Source](https://java-design-patterns.com/patterns/tls/)

```java
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.concurrent.Callable;

public class Result<T> {
    public final List<T> dataList = new ArrayList<>();
    public final List<String> exceptions = new ArrayList<>();
}

public class Data<T> implements Callable<Result<T>> {
    private final ThreadLocal<V> lockedData;
    private final T data;
    private final Function<T, T> f;

    public Data(T data, Supplier<T> supplier, Function<T, T> f) {
        this.lockedData = ThreadLocal.withInitial(supplier);
        this.data = data;
        this.f = f;
    }

    @Override
    public Result<T> call() {
        Result<T> result = new Result<>();
        System.out.println(Thread.currenthThread());
        try {
            result.dataList.add(f.apply(this.lockedData.get()));
        } catch (Exception e) {
            result.execptions.add(e.getMessage());
        }
        return result;
    }
}
```

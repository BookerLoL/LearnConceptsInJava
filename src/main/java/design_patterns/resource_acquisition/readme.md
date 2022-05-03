# Resource Aquisition

- Whenever need resourece to be closed

```java
import java.io.Closeable;
public class DataReader implements Closeable {
    @Override
    public void close() {
        System.out.println("Closing");
    }
}

public static void main(String[] args) {
    try (DataReader reader = new DataReader()) {
        //do sth
    }
}
```

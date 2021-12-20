# Loan Design Pattern

- Functional Design Pattern
- Lender Lendee Pattern

- Ensures resource has been disposed of once out of scope
  - Caller can pass their objects method to manage state

```java
public interface InputStreamConsumer {
    void consume(InputStream in) throws Exception;
}

public class InputStreamUtils {
    public static InputStreamProducer fromClassPath(final String path) {
        return new InputStreamProducer() {
            @Override
            public InputStream produce() throws Exception {
                return InputStreamUtils.class.getClassLoader().getResourceAsStream(path);
            }
        };
    }
}


public class InputStreamLender {
        public static void lend(InputStreamProducer producer, InputStreamConsumer consumer)
            throws Exception {
            InputStream in = null;
            try {
                in = producer.produce();
                consumer.consume(in);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }
}
```

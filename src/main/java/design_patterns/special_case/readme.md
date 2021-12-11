# Special Case

Special case behaviors as subclasses

```java
public interface DisplayViewModel {
    void show();
}


public class ServerDown implements DisplayViewModel {}
public class InvalidUser implements DisplayViewModel {}
public class ErrorValidation implements DisplayViewModel {}
public class SuccessMessage implements DisplayViewModel {}
```

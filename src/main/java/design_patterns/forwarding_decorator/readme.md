# Forwarding Decorator Design Pattern

- This is used to avoid duplication of multiple decorators

```java
public abstract class ForwardingDecorator implements SomeInterface {
    private SomeInterface delegate;

    //delegate methods
    public void show() {
        return delegate.show();
    }

    public SomeInterface set(SomeInterface t) {
        return super.set(t);
    }
}
```

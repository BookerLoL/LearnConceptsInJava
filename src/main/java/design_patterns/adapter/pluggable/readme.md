# Pluggable Adaptable

- An adapter that isn't tied to a specific adaptee, allows different implementations to work

```java
public interface DoSomething() {
    void action();
}

public class PluggableAdapter implements DoSomething {
    private DoSomething adapter;

    public PluggableAdapter(DoSomething adapter) {
        this.adapter = adapter;
    }

    public void action() {
        adapter.action();
    }
}

public class Library1Adapter implements DoSomething {
    //omitted implementation using some Library method
}

public class LibraryNAdapter implements DoSomething {
    //omitted implementation using a different library method
}
```

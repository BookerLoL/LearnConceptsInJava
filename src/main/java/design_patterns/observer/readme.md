# Observer design Pattern

**Behavioral** Design Pattern

- Lets multiple objects be informed of new events that happen to an object

Functional Approach

```java
public interface Observer {
    void action(String value);
}

public class Observerable {
    private List<Observer> observer;

    public void notify(String value) {
        observer.forEach(o -> o.action(value));
    }

    public void add(Observer obs) {
        observer.add(obs);
    }
}


Observable o = new Observable();
o.add( value -> {
    if (value.eqauls("value")) {
        System.out.println("Found the value, printing out value: " +  value);
    }
});

o.notify("value");
```

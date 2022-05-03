# Observer design Pattern

**Behavioral** Design Pattern

- Lets multiple dependents be informed of new events that happen to the object

```java
public interface Observer {
    void update();
}

public class Observerable {
    private List<Observer> observables;

    public void add(Observer o) { observables.add(o); }
    public void remove(Observer o) { observables.remove(o); }
    public void notify() { observables.forEach(Observer::update); }
}

public class ExampleObserver implements Observer {
    public void update() {
        System.out.println("Updated at: " + System.nanoTime());
    }
}
```

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

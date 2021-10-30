# Memento Design Pattern
Design pattern to restore an object to it's previous states
- Components
  - Originator
  - Caretaker
  - Memento

```java
public class Memento {
    int number;
    String text;
}

public class Caretaker {
    datastructures.list<Memento> states;
    
    public void add(Memento memento) {
        states.add(memento);
    }
    
    public Memento get(int index) {
        return states.get(index);
    }
}

public class Originator {
    int id;
    String text;
    
    public void getStateFrom(Memento state) {
        id = state.number;
        text = state.text;
    }
    
    public Memento saveState() {
        return new Memento(id, text);
    }
}
```

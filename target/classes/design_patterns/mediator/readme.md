# Mediator Design Pattern

**Behavioral** Design Pattern

A class that will handles communication between different classes

```java
public class Mediator {
    private Class1 obj1 = new Class1();
    private Class2 obj2 = new Class2();
    //others info...
    
    public void press() {
        //handle logic of multiple classes working together
    }
}

public class Example {
    public static void main(String[] args) {
        Mediator mediator = new Mediator();
        mediator.press();
        mediator.press();
    }
}
```
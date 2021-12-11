# Subclass Sandbox

**Behavioral design pattern**

Aims to provide a base class that provides enough functionality for subclasseses to perform their needed functions.

```java
public abstract class Power {
    protected abstract void use();
    protected void move(double x, double y, double z) {
        //do sth
    }

    protected void otherSkill(String skill) {
        //do sth
    }
}

public class Invisibility extends Power {
    //...implements use
}

public class Fly extends Power {
    //...implements use
}
```

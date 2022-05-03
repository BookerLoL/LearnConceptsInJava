# Proxy Design Pattern

**Structural** Design Pattern

- Placeholder for another object
- Proxy has access to original object

```java
interface Showable() {
    void show();
}


public class OriginalShower implements Showable() {
    public void show() {
        System.out.println("Real show");
    }
}

public class ProxyShower implements Showable() {
    private OriginalShower shower;

    public ProxyShower(OriginalShower shower) {
        this.shower = shower;
    }

    public void show() {
        if (show == null) {
            System.out.println("Fake show");
        } else {
            shower.show();
        }
    }
}

public static void main(String[] args) {
    Showable shower = new ProxyShower(new OriginalShower());
}
```

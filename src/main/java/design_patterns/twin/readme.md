# Twin Design Pattern

Simulates multiple inheritance for languages without multiple inheritance

```java
public abstract class Item {
    public abstract void draw();
}

public class RandomItem extends Item {
    private Runner runner; //should reference RandomItemRunner
}

public abstract class Runner {
    public abstract void run();
}

public class RandomItemRunner extends Runner {
    private Item item; //should reference RandomItem
}
```
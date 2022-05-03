# Update Method

```java
public class World {
    List<Entity> entities;

    //gameLoop, processInput, render, update
    public void run() {}
    public void stop() {}
}

public class Position {
    int x;
    int y;
}

abstract class Entity {
    int id;
    Position position;

    //getPosition, setPosition
    public abstract void update();
}

public class SlowEntity extends Entity {
    int delay;
    int frames;
}

public class NonMovingEntity extends Entity {
    //etc
}


public class Zombie extends Entity {
    //etc
}
```

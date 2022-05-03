# Double Dispatch

- Often a code smell idiom
  - **Visitor design pattern is often a better appraoch for double dispatching**
- Creates dynamic behavior based on receiving objects type and methods

```java
public class GameSquare() {
    //methods for interesecting
}

public abstract class Entity {
    collision(Entity entity);
    collisionResolve(Car);
    collisionResolve(FastCar);
    collisionResolve(Truck);
}

// Have classes override the methods from Entity
```

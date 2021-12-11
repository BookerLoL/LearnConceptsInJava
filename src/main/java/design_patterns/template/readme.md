# Template Design Pattern

**Behavioral** Design Pattern

Defines an example of an algorithm that subclasses can override

```java
public abstract class Game {
	abstract void setup();

	abstract void start();

	abstract void end();

	public void play() {
		setup();
		start();
		end();
	}
}
```
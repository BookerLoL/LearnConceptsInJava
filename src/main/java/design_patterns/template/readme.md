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

Functional Appraoch (Avoids inheritance)

```java
interface GameAction {
	void play();
}

public class GameActionUtil {
	public static void playUtil(GameAction gameAction) {
		//Time before
		gameAction.play();
		//Time after
	}
}

public class Tester {
	void test() {
		Game game = new Game();
		GameActionUtil.playUtil(
			() -> {
				game.setup();
				game.start();
				game.end();
			}
		)
	}
}
```

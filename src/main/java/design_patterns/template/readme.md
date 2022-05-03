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

- Consider default methods as well

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

Other examples

```java
public class Application {
	GameAction setup;
	GameAction start;
	GameAction end;

	public Application(GameAction setup, GameAction start, GameAction end) {
		this.setup = setup;
		this.start = start;
		this.end = end;
	}

	public void template() {
		setup.play();
		start.play();
		end.play();
	}
}

Game game = new Game();
new Application(game::setup, game:;start, game::end);
```

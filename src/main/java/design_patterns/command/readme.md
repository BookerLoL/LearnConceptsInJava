# Command Design Pattern

**Behavioral** Design Pattern

- Turns a request into an object to be executed when needed

```java
public interface Game {
    void start();
    void quit();
}

public interface Command {
    public void doSomething();
}

public class QuitCommand implements Command {
    Game game;
    public QuitCommand(Game game) {
        this.game = game;
    }

    public void doSomething() {
        game.quit();
    }
}

public class StartCommand implements Command {
    Game game;
    public StartCommand(Game game) {
        this.game = game;
    }

    public void doSomething() {
        game.start();
    }
}
```

Functional Approach

```java
public interface Game {
    void start();
    void quit();
}

public interface Command {
    public void doSomething();
}

public static void main(String[] args) {
    Game game = null; //replace with concrete game implementation
    Command startCommand = () -> game.start();
    Command quitCommand = () -> game.quit();

    startCommand.doSomething();
    quitCommand.doSomething();
}
```

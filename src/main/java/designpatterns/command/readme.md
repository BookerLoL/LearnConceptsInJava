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

Other examples

```java
public interface Action {
    public void do();
}

public class ActionRecorder {
    private List<Action> actions = new ArrayList<>();

    public void record(Action action) {
        actions.add(action);
    }

    public void run() {
        actions.forEach(Action::do);
    }
}

ActionRecorder actions = new ActionRecorder();
actions.record(x::doSomething1);
actions.record((x::doSomething2);
actions.run();
```

```java
public interface Logger {
  void log(String message);

  default Logger filter(Predicate<String> filter) {
    return message -> {
      if (filter.test(message)) {
        log(message);
      }
    };
  }
}

Logger logger = msg -> System.out::println;
Logger filteredLogger = logger.filter(msg -> !msg.isEmpty());
```
# LineMemento Design Pattern

**Behavioral** Design Pattern

Capture an object's state for later to restore back to a previous state

```java
import java.util.ArrayList;
import java.util.List;

public class LineMemento {
    int number;
    String text;
}

public class TextLineEditorStates {
    List<LineMemento> states = new ArrayList<>();

    public void add(LineMemento LineMemento) {
        states.add(LineMemento);
    }

    public LineMemento get(int index) {
        return states.get(index);
    }
}

public class TextLineEditor {
    List<String> lines = new ArrayList<>();
    int currentLine = 0;

    public void getStateFrom(LineMemento state) {
        lines.set(currentLine, state.text);
    }

    public LineMemento saveState() {
        return new LineMemento(currentLine, lines.get(currentLine));
    }

    public void jumpToLine(int line) {
        currentLine = line;
    }

    public void printLine() {
        System.out.println(lines.get(currentLine));
    }

    public void clearLine() {
        lines.set(currentLine, "");
    }

    public void write(String text) {
        lines.set(currentLine, text);
    }
}

public class Example {
    public static void main(String[] args) {
        TextLineEditor textEditor = new TextLineEditor();
        textEditor.jumpToLine(2);
        textEditor.write("On Line 2");

        TextLineEditorStates savedStates = new TextLineEditorStates();
        savedStates.add(textEditor.saveState());

        textEditor.clearLine();
        textEditor.printLine();
        textEditor.getStateFrom(savedStates.get(0));
        textEditor.printLine();
    }
}
```

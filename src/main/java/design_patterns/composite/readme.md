# Composite Design Pattern

**Structural** Design Pattern

Compose objects into tree structures and have same object type

```java
import java.util.ArrayList;

class SystemManager {
    Collection<Component> components = new ArrayList<>();

    public void printAll() {
        for (Component component : components) {
            printAllHelper(component);
        }
    }

    private void printAllHelper(Component component) {
        if (component == null) {
            return;
        }

        System.out.println(component.name);

        if (component instanceof Directory) {
            for (Component subcomponents : component) {
                printAllHelper(subcomponents);
            }
        }
    }
}

public abstract class Component {
    String name;
}

//Leaf class
class File extends Component {
}

//Composite class
class Directory extends Component {
    Collection<Component> subcomponents = new ArrayList<>();
}

public class Example {
    public static void main(String[] args) {
        File f0 = new File();

        Directory d1 = new Directory();
        File f1 = new File();
        d1.subcomponents.add(f1);

        Directory d2 = new Directory();
        File f2 = new File();
        File f3 = new File();
        d2.subcomponents.add(f2);
        d2.subcomponents.add(f3);

        SystemManager manager = new SystemManager();
        manager.components.add(f0);
        manager.components.add(d1);
        manager.components.add(d2);
        manager.printAll();
    }
}
```

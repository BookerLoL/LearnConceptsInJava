# Null Object Design Pattern

**Behavioral** Design Pattern

Is a special case of the Special Case Pattern

Replaces the need for null checking by providing a class that does nothing

- While not having to check for null is nice, `Optional` is a good alternative

```java
interface Function {
    public void apply();
}

public class PrintFunction implements Function {
    private Object[] objects;

    public PrintFunction(Object... objs) {
        objects = objs;
    }

    @Override
    public void apply() {
        System.out.println(Arrays.toString(objects));
    }
}

public class NullFunction implements Function {
    @Override
    public void apply() {
        //do nothing
    }
}

public class Example {
    public static void main(String[] args) {
        List<Function> functions = List.of(new PrintFunction("1"), new NullFunction());
        for (Function f : functions) {
            f.apply();
        }
    }
}
```

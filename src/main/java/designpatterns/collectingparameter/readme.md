# Collecting Parameter

- Passing a mutable pattern through multiple methods
  - Such as passing around StringBuilder, Lists, etc
- Often useful when breaking up a big method that manipulates an object multiple times

```java
public class Accumlator {
    public static void addRandomText(StringBuilder sb) {
        sb.append("random text");
    }

    public static void addHelloText(StringBuilder sb) {
        sb.append("hello");
    }
    //etc
}
```

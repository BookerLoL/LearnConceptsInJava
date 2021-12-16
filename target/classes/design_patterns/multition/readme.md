# Multition Design Pattern

**Creational** design pattern

- Also known as **Registry**
- Great when some instances are needed added by clients from a specific place

```java
public final class NameResource {
    public enum FakeName {
        BOB, MARY, BILLY, SAMMY;
    }

    private final Map<FakeName, String> allowedNames;

    static {
        allowedNames = new ConcurrentHashMap<>();
        allowedNames.put(BOB, "Bobby");
        allowedNames.put(MARY, "Marilyn");
        allowedNames.put(BILLY, "Bill");
        //omitting SAMMY
        return allowedNames;
    }

    public static String getName(FakeName allowedName) {
        return allowedNames.getOrDefault(allowedName, "");
    }
}
```

# Default Values Design Pattern

- How to provide default values in Java
- [Source](https://dzone.com/articles/functional-default-arguments-part-two)

Providing default values by checking values

```java

public void doSomething(int hours, String activity) {
    activity = activity == null ? "None" : activity;
    hours = hours < 0 ? 0 : hours;

    //do stuff
}
```

Providing default values by overloading methods

```java
public void doSomething(int hours) {
    doSomething(hours, null, true);
}

public void doSomething(int hours, String activity) {
    doSomething(hours, activity, true);
}

public void doSomething(int hours, String acivitity, boolean inMorning) {
    //does stuff
}
```

Functional Approach

```java
public interface DefaultValues<T, U, V> extends Function<T, Function<U, V>> {
    default V apply(T t, U u) {
        return this.apply(t).apply(u);
    }

    default DefaultValues<T, U, V> defaultFirst(T defaultFirst) {
        return t -> u -> this.apply(Optional.ofNullable(t).orElse(defaultFirst), u);
    }

    default DefaultValues<T, U, V> defaultSecond(U defaultSecond) {
        return a -> b -> this.apply(a, Optional.ofNullable(b).orElse(defaultSecond));
    }
}


public static Boolean isActive(int hours, String activity) {
    return hours > 0 && activity.length() > 3;
}


DefaultValues<Integer, String, Boolean> test = hours -> activity -> isActive(hours, activity);
System.out.println(test.defaultFirst(10).defaultSecond("a").apply(null, null));
System.out.println(test.defaultFirst(10).defaultSecond("abcdc").apply(null, null);
System.out.println(test.defaultFirst(10).defaultFirst(0).defaultSecond("abcdc").apply(null, null));
```

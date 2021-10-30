# Generic Builder Pattern

This allows you extend existing builder patterns to add additional functionalities and keep existing builder features

```java
public static class PersonBuilder<T extends PersonBuilder<T>> {
    //builder methods
}

public static class StudentBuilder extends PersonBuilder<StudentBuilder> {
    //additional student specific methods
}
```
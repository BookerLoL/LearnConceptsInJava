# Generic Builder Pattern

- This allows you extend existing builder patterns to add additional functionalities and keep existing builder features

```java
public class Person {
    String name;
    int age;
    String occupation;
    String schoolName;
    int graduationYear;
}

public static class PersonBuilder<T extends PersonBuilder<T>> {
    protected Person p = new Person();

    public Person build() {
        return p;
    }

    public T name(String name) {
        p.name = name;
        return self();
    }

    public T age(int age) {
        p.age = age;
        return self();
    }

    protected T self() {
        return (T) this;
    }
}

public static class StudentBuilder extends PersonBuilder<StudentBuilder> {
    public StudentBuilder graduatedIn(int year) {
        p.graduationYear = year;
        return self();
    }

    public StudentBuilder attended(String schoolName) {
        p.schoolName = schoolName;
        return self();
    }
}

public class Example {
    public static void main(String[] args) {
        Person p = new StudentBuilder().name("Bob").age(24)
                .attended("University").graduatedIn(2021).build();
    }
}
```
# Builder Pattern

Great for designing classes with lots of parameters in constructor or static factories with lots of parameters.

- Allows you to make chain calls

```java
public class Person {
    //Lots of fields...
    private Person() {
    }

    public Person(PersonBuilder builder) {
        //..set fields
    }

    public static class PersonBuilder() {
        //Person fields that users can modify...
        public PersonBuilder name(String name) {
            this.name = name;
        }

        public PersonBuilder age(int age) {
            this.age = age;
        }

        // ... more fields
        public Person build() {
            return new Person(this);
        }
    }
}

public class Example {
    public static void main(String[] args) {
        Person person = new Person.PersonBuilder().name("Bob").age(21).build();
    }
}
```

Functional Approach

```java
import java.util.function.Consumer;

public class Person {
    public String firstName;
    public String lastName;
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

public class PersonBuilder {
    public String firstName;
    public String lastName;
    //other fields

    public PersonBuilder with(Consumer<PersonBuilder> c) {
        c.accept(this);
        return this;
    }

    public Person build() {
        return new Person(firstName, lastName);
    }
}
```

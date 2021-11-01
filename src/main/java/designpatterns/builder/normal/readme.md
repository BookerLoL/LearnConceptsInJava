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
# Faceted Builder Pattern

Creating a class might be very complex, so need to have multiple builders to jump from one builder to antoher builder

- Utilizes facade design pattern approach

```java
public class Person {
    public class PersonBuilder {
        protected Person person = new Person();

        //A builder that handles address info, omitted details of builder
        public PersonAddressBuilder lives() {
            return new PersonAddressBuilder(person);
        }

        //A builder that handles job info, omitted details of builder
        public PersonJobBuilder works() {
            return new PersonJobBuilder(person);
        }

        public Person build() {
            return person;
        }
    }
}


public class Example {
    public static void main(String[] args) {
        Person p = Person.builder()
                .lives().at("place").in("city").withPostalCode("zipcode")
                .works().at("place").forCompany("company").years(6)
                .name("Person")
                .age(28)
                .build();
    }
}

```
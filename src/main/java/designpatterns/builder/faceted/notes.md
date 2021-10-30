# Faceted Builder Pattern

Creating a class might be very complex, so need to have multiple builders to jump from one builder to antoher builder
- Utilizes facade design pattern approach

```java
// lives() and works() are both different builders
Person p = Person.builder()
        .lives().at("place").in("city").withPostalCode("zipcode")
        .works().at("place").forCompany("company").years(6)
        .name("Person")
        .age(28)
        .build();
```
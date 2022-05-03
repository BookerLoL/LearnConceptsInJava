# Collection Pipeline Design Pattern

A functional preference in writing code with using collections as input then output over and over until result

```java
//Reduced the need for large functions
public static List<Car> getSedanCarsOwnedSortedByDate(List<Person> persons) {
    return persons.stream().map(Person::getCars).flatMap(List::stream)
        .filter(car -> Category.SEDAN.equals(car.getCategory()))
        .sorted(Comparator.comparing(Car::getYear)).collect(Collectors.toList());
  }
```
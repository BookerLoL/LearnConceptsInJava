package designpatterns.builder.generic;

/**
 * Recursive generic builders
 */
public class Generic {
    class Person {
        public String name;
        public String position;
    }

    class PersonBuilder<T extends PersonBuilder<T>> {
        protected Person person = new Person();

        protected T withName(String name) {
            person.name = name;
            return self();
        }

        public Person build() {
            return person;
        }

        protected T self() {
            return (T) this;
        }
    }

    class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
        public EmployeeBuilder worksAt(String position) {
            person.position = position;
            return self();
        }

        @Override
        protected EmployeeBuilder self() {
            return this;
        }
    }
}

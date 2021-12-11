package designpatterns.builder.faceted;

public class Person {
    private int years;
    private String company;
    private String jobAddress;
    private String postalCode;
    private String city;
    private String address;

    private Person() {
    }

    public class PersonBuilder {
        protected Person person = new Person();

        public PersonAddressBuilder lives() {
            return new PersonAddressBuilder(person);
        }

        public PersonJobBuilder works() {
            return new PersonJobBuilder(person);
        }

        public Person build() {
            return person;
        }
    }

    public class PersonAddressBuilder extends PersonBuilder {
        public PersonAddressBuilder(Person person) {
            this.person = person;
        }

        public PersonAddressBuilder at(String address) {
            this.person.address = address;
            return this;
        }

        public PersonAddressBuilder in(String city) {
            this.person.city = city;
            return this;
        }

        public PersonAddressBuilder withPostalCode(String postalCode) {
            this.person.postalCode = postalCode;
            return this;
        }
    }

    public class PersonJobBuilder extends PersonBuilder {
        public PersonJobBuilder(Person person) {
            this.person = person;
        }

        public PersonJobBuilder at(String address) {
            this.person.jobAddress = address;
            return this;
        }

        public PersonJobBuilder forCompany(String company) {
            this.person.company = company;
            return this;
        }

        public PersonJobBuilder years(int years) {
            this.person.years = years;
            return this;
        }
    }
}

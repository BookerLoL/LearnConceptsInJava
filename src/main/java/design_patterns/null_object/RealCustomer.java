package designpatterns.nullobject;

public class RealCustomer extends Customer {
	public RealCustomer(String name) {
		super(name);
	}

	@Override
	public String getName() {
		return name;
	}

}

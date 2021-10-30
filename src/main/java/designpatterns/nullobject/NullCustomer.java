package designpatterns.nullobject;

public class NullCustomer extends Customer {
	public NullCustomer(String name) {
		super(name);
	}

	@Override
	public String getName() {
		return "Not Available in DB";
	}

}

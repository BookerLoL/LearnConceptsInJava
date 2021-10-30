package designpatterns.adapter.classadapter;

public class Adapter implements Identifiable, Nameable {

	@Override
	public String getName() {
		return "Class Adapter";
	}

	@Override
	public int getId() {
		return -1;
	}

}

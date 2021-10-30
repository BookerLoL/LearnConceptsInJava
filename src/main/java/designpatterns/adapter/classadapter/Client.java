package designpatterns.adapter.classadapter;

public class Client {
	Adapter adapter;

	public Client(Adapter adapter) {
		this.adapter = adapter;
	}

	public String getObjectId() {
		return this.adapter.getId() + this.adapter.getName();
	}
}

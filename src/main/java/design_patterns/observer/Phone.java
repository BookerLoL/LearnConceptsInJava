package designpatterns.observer;

public class Phone implements Observer<String> {
	@Override
	public void update(String data) {
		System.out.println(data);
	}

}

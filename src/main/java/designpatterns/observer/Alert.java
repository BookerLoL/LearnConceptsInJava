package designpatterns.observer;

import java.util.ArrayList;
import java.util.Collection;

public class Alert implements Observable<String> {
	Collection<Observer<String>> observers;

	public Alert() {
		observers = new ArrayList<>();
	}

	@Override
	public void notify(String data) {
		observers.forEach(observer -> observer.update(data));
	}

	@Override
	public void addObserver(Observer<String> observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer<String> observer) {
		observers.remove(observer);
	}

}

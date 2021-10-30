package designpatterns.adapter.objectadapter;

public class RudeHuman implements Human {

	@Override
	public void talk() {
		System.out.print("Who the hell are you?");
	}
}

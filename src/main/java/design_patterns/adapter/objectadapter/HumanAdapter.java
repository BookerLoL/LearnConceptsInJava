package design_patterns.adapter.objectadapter;

public class HumanAdapter implements Robot {
	Human human;

	public HumanAdapter(Human human) {
		this.human = human;
	}

	@Override
	public void speak() {
		System.out.print("I am a robot, ");
		human.talk();
	}
}

package designpatterns.template;

public abstract class Game {
	abstract void setup();

	abstract void start();

	abstract void end();

	public void play() {
		setup();
		start();
		end();
	}
}

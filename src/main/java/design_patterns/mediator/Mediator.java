package designpatterns.mediator;

public class Mediator {
	private BooleanValue alwaysFalse;
	private BooleanValue normalBool;

	public Mediator() {
		alwaysFalse = new BooleanValue(false);
		normalBool = new BooleanValue(false);
	}

	public Boolean getBooleanOne() {
		return alwaysFalse.get();
	}

	public Boolean getBooleanTwo() {
		return normalBool.get();
	}

	public void setBooleans(Boolean bool) {
		if (bool == null) {
			return;
		}

		normalBool.set(bool);
	}
}

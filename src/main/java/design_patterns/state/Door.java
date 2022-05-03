package designpatterns.state;

public class Door {
	private DoorState state;

	public Door() {
		state = new ClosedDoorState(this);
	}

	protected void setState(DoorState state) {
		this.state = state;
	}

	protected DoorState getState() {
		return state;
	}
}

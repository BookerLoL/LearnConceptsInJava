package designpatterns.state;

public abstract class AbstractDoorState implements DoorState {
	private Door door;

	public AbstractDoorState(Door door) {
		this.door = door;
	}

	protected Door getDoor() {
		return door;
	}
}

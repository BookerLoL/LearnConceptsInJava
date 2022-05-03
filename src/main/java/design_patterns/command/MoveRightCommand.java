package designpatterns.command;

public class MoveRightCommand implements Command {
	private Player player;

	public MoveRightCommand(Player obj) {
		player = obj;
	}

	@Override
	public void execute() {
		player.moveRight();
	}
}

package designpatterns.memento;

public class Tester {

	public static void main(String[] args) {
		TextEditor editor = new TextEditor(new TextWindow());
		editor.write("This is a test to see if we saved our state");
		editor.save();
		editor.clear();
		editor.write("We just cleared the text");
		editor.save();
		editor.clear();
		editor.write("Testing if this works");
		editor.save();
		editor.clear();
		editor.clear();

		while (editor.restore()) {
			System.out.println(editor.print());
			editor.clear();

		}
	}

}

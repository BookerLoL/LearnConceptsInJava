package designpatterns.visitor;

public abstract class Visitor {
	abstract void visit(If statemment);

	abstract void visit(Assign statement);

	abstract void visit(Read statement);

	abstract void visit(Write statement);
}

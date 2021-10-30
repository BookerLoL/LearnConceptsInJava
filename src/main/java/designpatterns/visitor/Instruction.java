package designpatterns.visitor;

public abstract class Instruction {
	abstract void accept(Visitor v);
}

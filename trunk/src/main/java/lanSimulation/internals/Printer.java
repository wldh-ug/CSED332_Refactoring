
package lanSimulation.internals;

public class Printer extends Node {
	
	public Printer(byte _type, String _name) {
		super(_type, _name);
	}
	
	public Printer(byte _type, String _name, Node _nextNode) {
		super(_type, _name, _nextNode);
	}
	
}

package lanSimulation.internals;

public class Workstation extends Node {

	public Workstation(byte _type, String _name) {
		super(_type, _name);
	}
	
	public Workstation(byte _type, String _name, Node _nextNode) {
		super(_type, _name, _nextNode);
	}
	
}
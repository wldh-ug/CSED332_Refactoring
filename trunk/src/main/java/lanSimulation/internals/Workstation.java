package lanSimulation.internals;

public class Workstation extends Node {

	public Workstation(String _name) {
		super(_name);
	}

	public Workstation(String _name, Node _nextNode) {
		super(_name, _nextNode);
	}

	@Override
	public void bufferAppend(StringBuffer buf, Boolean isXML) {
		if (isXML) {
			buf.append("<workstation>");
			buf.append(this.name);
			buf.append("</workstation>");
		} else {
			buf.append("Workstation ");
			buf.append(this.name);
			buf.append(" [Workstation]");
		}
	}

}

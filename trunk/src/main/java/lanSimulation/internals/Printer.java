package lanSimulation.internals;

public class Printer extends Node {

	public Printer(String _name) {
		super(_name);
	}

	public Printer(String _name, Node _nextNode) {
		super(_name, _nextNode);
	}

	@Override
	public void bufferAppend(StringBuffer buf, Boolean isXML) {
		if (isXML) {
			buf.append("<printer>");
			buf.append(this.name);
			buf.append("</printer>");
		} else {
			buf.append("Printer ");
			buf.append(this.name);
			buf.append(" [Printer]");
		}
	}

}

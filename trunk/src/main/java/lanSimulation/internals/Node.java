/*
 * This file is part of lanSimulation.
 *
 * lanSimulation is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * lanSimulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with lanSimulation; if
 * not, write to the Free Software Foundation, Inc. 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 *
 * Copyright original Java version: 2004 Bart Du Bois, Serge Demeyer Copyright C++ version: 2006
 * Matthias Rieger, Bart Van Rompaey
 */
package lanSimulation.internals;

import java.io.IOException;
import java.io.Writer;

/**
 * A <em>Node</em> represents a single Node in a Local Area Network (LAN). Several types of Nodes
 * exist.
 */
public class Node {
	// enumeration constants specifying all legal node types
	/**
	 * Holds the name of the Node.
	 */
	public String name;
	/**
	 * Holds the next Node in the token ring architecture.
	 * 
	 * @see lanSimulation.internals.Node
	 */
	public Node nextNode;

	/**
	 * Construct a <em>Node</em> with given #type and #name.
	 */
	public Node(String _name) {
		name = _name;
		nextNode = null;
	}

	/**
	 * Construct a <em>Node</em> with given #type and #name, and which is linked to #nextNode.
	 */
	public Node(String _name, Node _nextNode) {
		name = _name;
		nextNode = _nextNode;
	}

	/**
	 * Write a log report with given description and Writer.
	 * 
	 * @param report      Stream that will hold a report about what happened when handling the
	 *                    request.
	 * @param description Brief description about report
	 */
	public void writeReport(Writer report, String description) {
		try {
			report.write("\tNode '" + name + "' " + description + ".\n");
			report.flush();
		} catch (IOException exc) {
			// just ignore
		}
	}

	public void bufferAppend(StringBuffer buf, Boolean isXML) {
		if (isXML) {
			buf.append("<node>");
			buf.append(this.name);
			buf.append("</node>");
		} else {
			buf.append("Node ");
			buf.append(this.name);
			buf.append(" [Node]");
		}
	}

}

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
 * not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 *
 * Copyright original Java version: 2004 Bart Du Bois, Serge Demeyer Copyright C++ version: 2006
 * Matthias Rieger, Bart Van Rompaey
 */
package lanSimulation.internals;

import java.io.IOException;
import java.io.Writer;

/**
 * A <em>Packet</em> represents a unit of information to be sent over the Local Area Network (LAN).
 */
public class Packet {
	/**
	 * Holds the actual message to be send over the network.
	 */
	public String message;
	/**
	 * Holds the name of the Node which initiated the request.
	 */
	public String origin;
	/**
	 * Holds the name of the Node which should receive the information.
	 */
	public String destination;

	/**
	 * Construct a <em>Packet</em> with given #message and #destination.
	 */
	public Packet(String _message, String _destination) {
		message = _message;
		origin = "";
		destination = _destination;
	}

	/**
	 * Construct a <em>Packet</em> with given #message, #origin and #receiver.
	 */
	public Packet(String _message, String _origin, String _destination) {
		message = _message;
		origin = _origin;
		destination = _destination;
	}

	/**
	 * Check whether a node is the destination node
	 * 
	 * @param node A node that has to be checked
	 * @return Truth value that whether a node is the destination node
	 */
	public Boolean isDestination(Node node) {
		return destination.equals(node.name);
	}

	/**
	 * Check whether a node is the origin node
	 * 
	 * @param node A node that has to be checked
	 * @return Truth value that whether a node is the origin node
	 */
	public Boolean isOrigin(Node node) {
		return origin.equals(node.name);
	}

	/**
	 * Write a report of the given accounting with description.
	 * 
	 * @param report Stream that will hold a report about what happened when handling the request.
	 */
	public void writeReport(Writer report) {
		String author, title, docType;

		if (message.startsWith("!PS")) {
			author = getPSInfo("author", "Unknown");
			title = getPSInfo("title", "Untitled");
			docType = "Postscript";
		} else {
			title = "ASCII DOCUMENT";
			if (message.length() >= 16) {
				author = message.substring(8, 16);
			} else {
				author = "Unknown";
			}
			docType = "ASCII Print";
		}

		try {
			report.write("\tAccounting -- author = '" + author + "' -- title = '" + title + "'\n");
			report.write(">>> " + docType + " job delivered.\n\n");
			report.flush();
		} catch (IOException exc) {
			// just ignore
		}
	}

	/**
	 * Get information with given key when type is !PS.
	 * 
	 * @param data         Key of data
	 * @param defaultValue Default value that is used when no value found
	 * @return Acquired value data
	 */
	public String getPSInfo(String data, String defaultValue) {
		int startPos = 0, endPos = 0;

		startPos = message.indexOf(data + ":");
		if (startPos >= 0) {
			endPos = message.indexOf(".", startPos + data.length() + 1);
			if (endPos < 0) {
				endPos = message.length();
			}
			return message.substring(startPos + data.length() + 1, endPos);
		}
		return defaultValue;
	}

}

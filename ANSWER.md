Pair Information
================
 - Changhoon Kang ([chkang](mailto:chkang@postech.edu))
 - Jio Gim ([iknowme](mailto:iknowme@postech.edu))

Instruction
============
 - List at least 1 code smell for each question.
 - List at least 1 code example for each code smell.
 - Explain the code smell in each example with 1 or 2 sentences. (e.g. the process of ... appears 2 times in ... method.)

Answer to Q1
============
We found the following code smell(s): **Duplicated Node Logging**, **Duplicated Accounting Logging**, **Duplicated Parameter Parsing**  

Here are some example(s) of each code smell:

- Name: **Duplicated Node Logging**

    * Example 1: Four logging lines are duplicated two times in `requestWorkstationPrintsDocument` method.

        ```java
        try {
            report.write("\tNode '");
            report.write(startNode.name);
            report.write("' passes packet on.\n");
            report.flush();
        } catch (IOException exc) {
            // just ignore
        }

        currentNode = startNode.nextNode;
        while ((!packet.destination.equals(currentNode.name))
            & (!packet.origin.equals(currentNode.name))) {
            try {
                report.write("\tNode '");
                report.write(currentNode.name);
                report.write("' passes packet on.\n");
                report.flush();
            } catch (IOException exc) {
                // just ignore
            }

            currentNode = currentNode.nextNode;
        }
        ```

    * Example 2: Similar logging line with above occurs two times in `requestBroadcast` method.

        ```java
        report.write("\tNode '");
        report.write(currentNode.name);
        report.write("' accepts broadcast packet.\n");
        report.write("\tNode '");
        report.write(currentNode.name);
        report.write("' passes packet on.\n");
        report.flush();
        ```

- Name: **Duplicated Accounting Logging**

    * Example: 7 logging lines are duplicated two times in `printDocument` method.

        ```java
        if (document.message.startsWith("!PS")) {

            ...

            report.write("\tAccounting -- author = '");
            report.write(author);
            report.write("' -- title = '");
            report.write(title);
            report.write("'\n");
            report.write(">>> Postscript job delivered.\n\n");
            report.flush();
        } else {
            title = "ASCII DOCUMENT";
            if (document.message.length() >= 16) {
                author = document.message.substring(8, 16);
            }

            report.write("\tAccounting -- author = '");
            report.write(author);
            report.write("' -- title = '");
            report.write(title);
            report.write("'\n");
            report.write(">>> ASCII Print job delivered.\n\n");
            report.flush();
        }
        ```

- Name: **Duplicated Parameter Parsing**

    * Example: In `printDocument` method, the code part of parsing `author` and `title` from `document.message` is duplicated. In addition, this code is hard to know its function by just looking, so we extracted this as a method.

        ```java
        if (document.message.startsWith("!PS")) {
            startPos = document.message.indexOf("author:");
            if (startPos >= 0) {
                endPos = document.message.indexOf(".", startPos + 7);
                if (endPos < 0) {
                    endPos = document.message.length();
                }

                author = document.message.substring(startPos + 7, endPos);
            }

            startPos = document.message.indexOf("title:");
            if (startPos >= 0) {
                endPos = document.message.indexOf(".", startPos + 6);
                if (endPos < 0) {
                    endPos = document.message.length();
                }
                title = document.message.substring(startPos + 6, endPos);
            }

            accountingReport(report, author, title, ">>> Postscript job delivered.\n\n");
        } else {

            ...
        ```

Several duplications on `printOn`, `printHTMLOn`, `printXMLOn` method will be fixed later (Q4).  

Answer to Q2
============
We found the following code smell(s): **Far Away Reporting Code**

Here are some example(s) of each code smell:

- Name: **Far Away Reporting Code**

    * Example 1: A method `nodeReport` that made in Q1 uses data which is belonging to class Node.

        ```java
        /**
          * Write a log report of the given node with description.
          *
          * @param report      Stream that will hold a report about what happened when
          *                    handling the request.
          * @param node        Node that has to be reported
          * @param description Brief description about report
          */
        public void nodeReport(Writer report, Node node, String description) {
            try {
                report.write("\tNode '");
                report.write(node.name + "' ");
                report.write(description + ".\n");
                report.flush();
            } catch (IOException exc) {
                // just ignore
            }
        }
        ```

    * Example 2: A method `accountingReport` that made in Q1 uses data which is belonging to class Packet.

        ```java
        /**
           * Write a report of the given accounting with description.
           *
           * @param report      Stream that will hold a report about what happened when
           *                    handling the request.
           * @param author      A string of author
           * @param title       A string of title
           * @param description Brief description about report
           */
        public void accountingReport(Writer report, String author, String title, String description) {
            try {
                report.write("\tAccounting -- author = '");
                report.write(author);
                report.write("' -- title = '");
                report.write(title);
                report.write("'\n");
                report.write(">>> " + description + " job delivered.\n\n");
                report.flush();
            } catch (IOException exc) {
                // just ignore
            }
        }
        ```

    * Example 3: A method `getInfo` that made in Q1 uses data which is belonging to class Packet.

        ```java
        /**
          * Get info. of either author or title.
          * @param message Document body
          * @param data Key of data
          * @param defaultValue Default value that is used when no value found
          * @return Acquired value data
          */
        public String getInfo(String message, String data, String defaultValue) {
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
        ```

Also, a method `printDocument` was only called at once in method `requestWorkstationPrintsDocument`, so that we inlined method `printDocument` in it.

Answer to Q3
============
We found the following code smell(s): _**NOPE**_

**We were in agony for a long time**, but we concluded that there is no meaningful refactorable factors. So we didn't eliminate navigation code.

The only good point which can be attained by extracting boolean conditions of `while` to a method we thought is that *extracting it to a method makes converting `origin` and `destination` public variables to private ones able, so it is good for object capsulation*. But test codes from [LANTest.java](./src/test/java/lanSimulation/test/LANTest.java) uses those variables as public, so we couldn't do anything.  

Finally, rather, we decided to consider just making long code short meaningful.  

Answer to Q4
============
We found the following code smell(s): **Smells on Node Type**

Here are some example(s) of each code smell:

- Name: **Smells on Node Type**

    * Example 1: In `printOn` method, there were a lot of duplicated code.

        ```java
        ...

        switch (currentNode.type) {
        case Node.NODE:
            buf.append("Node ");
            buf.append(currentNode.name);
            buf.append(" [Node]");
            break;
        case Node.WORKSTATION:
            buf.append("Workstation ");
            buf.append(currentNode.name);
            buf.append(" [Workstation]");
            break;
        case Node.PRINTER:
            buf.append("Printer ");
            buf.append(currentNode.name);
            buf.append(" [Printer]");
            break;
        default:
            buf.append("(Unexpected)");
            break;
        }
        
        ...
        ```
    
    * Example 2: In `printHTMLOn` method, there were a lot of duplicated code which are almost same with above.

        ```java
        ...

        switch (currentNode.type) {
        case Node.NODE:
            buf.append("Node ");
            buf.append(currentNode.name);
            buf.append(" [Node]");
            break;
        case Node.WORKSTATION:
            buf.append("Workstation ");
            buf.append(currentNode.name);
            buf.append(" [Workstation]");
            break;
        case Node.PRINTER:
            buf.append("Printer ");
            buf.append(currentNode.name);
            buf.append(" [Printer]");
            break;
        default:
            buf.append("(Unexpected)");
            break;
        }

        ...
        ```

    * Example 3: In `printXMLOn` method, there were a lot of duplicated code which are almost same with above.

        ```java
        ...

        switch (currentNode.type) {
        case Node.NODE:
            buf.append("<node>");
            buf.append(currentNode.name);
            buf.append("</node>");
            break;
        case Node.WORKSTATION:
            buf.append("<workstation>");
            buf.append(currentNode.name);
            buf.append("</workstation>");
            break;
        case Node.PRINTER:
            buf.append("<printer>");
            buf.append(currentNode.name);
            buf.append("</printer>");
            break;
        default:
            buf.append("<unknown></unknown>");
            break;
        }

        ...
        ```

    * Example 4: There was a lot of type checking of Node existed. Below example is from `consistentNetwork` method.

        ```java
        ...

        while (!encountered.containsKey(currentNode.name)) {
            encountered.put(currentNode.name, currentNode);
            if (currentNode.type == Node.WORKSTATION) {
                workstationsFound++;
            }
            if (currentNode.type == Node.PRINTER) {
                printersFound++;
            }
            currentNode = currentNode.nextNode;
        }

        ...
        ```

In this assignment, we didn't want to modify test code, but final Q4 required us to remove `type` attribute from `Node` class, so we were forced to change test code. In other words, we didn't do refactoring in previous questions (Q1 to Q3) if it requires changing of test code.  

<br>
<br>

---  

*This code is evil... We want to rewrite whole the code rather than refactoring...*  
*S..T...A...Y....*  

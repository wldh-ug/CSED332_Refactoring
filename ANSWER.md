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

Answer to Q2
============
We found the following code smell(s): <CODE_SMELL_NAME_1>, <CODE_SMELL_NAME_2>...

Here are some example(s) of each code smell: 
(same as Q1)


Answer to Q3
============
We found the following code smell(s): <CODE_SMELL_NAME_1>, <CODE_SMELL_NAME_2>...

Here are some example(s) of each code smell: 
(same as Q1)


Answer to Q4
============
We found the following code smell(s): <CODE_SMELL_NAME_1>, <CODE_SMELL_NAME_2>...

Here are some example(s) of each code smell: 
(same as Q1)

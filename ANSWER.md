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
We found the following code smell(s): `Duplicated Node Logging`

Here are some example(s) of each code smell: 

- Name: **`Duplicated Node Logging`**

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

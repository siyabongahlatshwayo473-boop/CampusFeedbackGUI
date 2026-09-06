# Campus Feedback Triage Pipeline

Campus Feedback Triage Pipeline is a Java Swing GUI application designed to process and analyse campus feedback using different Java data structures. The project demonstrates how a Queue, Set, Stack, and HashMap can work together as part of an organised feedback-processing pipeline.

The application starts with a paragraph containing several campus feedback statements. The user can click the **Prepare Queue** button to clean the input, separate it into individual feedback items, and add them to the Queue. The Queue follows the **FIFO (First-In, First-Out)** principle, meaning feedback is processed in the same order in which it was added.

The **Process Feedback** button removes items from the Queue and checks whether each feedback item has already been encountered. A **Set** is used to identify duplicate feedback because it only stores unique values. If an item already exists in the Set, it is identified as a duplicate and counted. This prevents repeated feedback from being treated as separate unique entries.

The program also identifies urgent feedback by searching for keywords such as **"urgent", "immediately", and "unsafe"**. When urgent feedback is detected, it is pushed onto a **Stack**. Since a Stack follows the **LIFO (Last-In, First-Out)** principle, the most recently detected urgent feedback is placed at the top and can be reviewed first using the **Review Latest Urgent** button.

After processing is completed, the **Calculate Metrics** button stores important statistics in a **HashMap**. These statistics include the number of feedback items processed, accepted, duplicated, and identified as urgent. The **Generate Report** button retrieves these values from the HashMap and displays them in the Analyst Report section.

The application also includes a **Reset** button that clears the data structures, counters, and output areas, allowing the pipeline to be used again.

Overall, this project demonstrates my understanding of **Java Swing, event-driven programming, text processing, and Java data structures**. Each button has an `ActionListener`, allowing the application to respond to user interactions and perform the appropriate processing operation efficiently.

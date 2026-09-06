package za.ac.aop.mini4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Starter graphical user interface for Mini-Assignment 4.
 *
 * The application processes a paragraph of campus feedback through a Queue,
 * Set, Stack and HashMap.
 *
 * @author LANGARM
 */
public class CampusFeedbackGUI extends JFrame {

    /*
     * Source paragraph component
     */
    private final JTextArea taFeedback;

    /*
     * Processing output component
     */
    private final JTextArea taProcessingOutput;

    /*
     * Final analyst report component
     */
    private final JTextArea taAnalystReport;

    /*
     * Status component
     */
    private final JLabel lblStatus;

    /*
     * Processing buttons
     */
    private final JButton btnPrepareQueue;
    private final JButton btnProcessFeedback;
    private final JButton btnReviewUrgent;
    private final JButton btnCalculateMetrics;
    private final JButton btnGenerateReport;
    private final JButton btnReset;

    /*
     * Required data structures
     */
    private Queue<String> feedbackQueue;
    private Set<String> acceptedFeedback;
    private Stack<String> urgentFeedback;
    private HashMap<String, Integer> metrics;

    /*
     * Counters
     */
    private int processedCount;
    private int acceptedCount;
    private int duplicateCount;
    private int urgentDetectedCount;

    /**
     * Constructs and displays the Campus Feedback Triage Pipeline.
     */
    public CampusFeedbackGUI() {

        /*
         * =====================================================
         * STEP 1: CONFIGURE THE FRAME
         * =====================================================
         */
        setTitle("Campus Feedback Triage Pipeline");
        setSize(900, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        /*
         * =====================================================
         * STEP 2: CREATE THE MAIN PANEL
         * =====================================================
         */
        JPanel mainPnl = new JPanel(new BorderLayout(8, 8));
        mainPnl.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        /*
         * =====================================================
         * STEP 3: CREATE THE HEADING PANEL
         * =====================================================
         */
        JPanel headingPnl =
                new JPanel(new FlowLayout(FlowLayout.CENTER));

        headingPnl.setBackground(new Color(253, 184, 19));

        JLabel lblHeading =
                new JLabel("Campus Feedback Triage Pipeline");

        lblHeading.setFont(
                new Font("SansSerif", Font.BOLD, 22)
        );

        headingPnl.add(lblHeading);

        /*
         * =====================================================
         * STEP 4: CREATE THE SOURCE-PARAGRAPH AREA
         * =====================================================
         */
        JPanel feedbackPnl =
                new JPanel(new BorderLayout(5, 5));

        feedbackPnl.setBorder(
                BorderFactory.createTitledBorder(
                        "Campus Feedback Paragraph"
                )
        );

        taFeedback = new JTextArea(8, 60);
        taFeedback.setLineWrap(true);
        taFeedback.setWrapStyleWord(true);
        taFeedback.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        taFeedback.setText(
                "The computer laboratory needs more charging points. "
                + "The library computers are working well. "
                + "The broken projector must be repaired immediately! "
                + "The computer laboratory needs more charging points. "
                + "The walkway near the residence is unsafe at night. "
                + "Online registration was easy to complete. "
                + "The cafeteria service requires urgent attention."
        );

        JScrollPane feedbackScrollPane =
                new JScrollPane(taFeedback);

        feedbackScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        feedbackScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        feedbackPnl.add(
                feedbackScrollPane,
                BorderLayout.CENTER
        );

        /*
         * =====================================================
         * STEP 5: FIRST BUTTON PANEL
         * =====================================================
         */
        JPanel firstButtonPnl =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
                );

        btnPrepareQueue =
                new JButton("Prepare Queue");

        btnProcessFeedback =
                new JButton("Process Feedback");

        btnReviewUrgent =
                new JButton("Review Latest Urgent");

        firstButtonPnl.add(btnPrepareQueue);
        firstButtonPnl.add(btnProcessFeedback);
        firstButtonPnl.add(btnReviewUrgent);

        btnProcessFeedback.setEnabled(false);
        btnReviewUrgent.setEnabled(false);

        JPanel sourcePnl =
                new JPanel(new BorderLayout(5, 5));

        sourcePnl.add(
                feedbackPnl,
                BorderLayout.CENTER
        );

        sourcePnl.add(
                firstButtonPnl,
                BorderLayout.SOUTH
        );

        /*
         * =====================================================
         * STEP 6: PROCESSING OUTPUT
         * =====================================================
         */
        JPanel processingPnl =
                new JPanel(new BorderLayout(5, 5));

        processingPnl.setBorder(
                BorderFactory.createTitledBorder(
                        "Processing Output"
                )
        );

        taProcessingOutput =
                new JTextArea(10, 60);

        taProcessingOutput.setEditable(false);
        taProcessingOutput.setLineWrap(true);
        taProcessingOutput.setWrapStyleWord(true);
        taProcessingOutput.setFont(
                new Font("Monospaced", Font.PLAIN, 13)
        );

        JScrollPane processingScrollPane =
                new JScrollPane(taProcessingOutput);

        processingPnl.add(
                processingScrollPane,
                BorderLayout.CENTER
        );

        /*
         * =====================================================
         * STEP 7: SECOND BUTTON PANEL
         * =====================================================
         */
        JPanel secondButtonPnl =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
                );

        btnCalculateMetrics =
                new JButton("Calculate Metrics");

        btnGenerateReport =
                new JButton("Generate Report");

        btnReset =
                new JButton("Reset");

        btnCalculateMetrics.setEnabled(false);
        btnGenerateReport.setEnabled(false);

        secondButtonPnl.add(btnCalculateMetrics);
        secondButtonPnl.add(btnGenerateReport);
        secondButtonPnl.add(btnReset);

        /*
         * =====================================================
         * STEP 8: ANALYST REPORT
         * =====================================================
         */
        JPanel reportPnl =
                new JPanel(new BorderLayout(5, 5));

        reportPnl.setBorder(
                BorderFactory.createTitledBorder(
                        "Analyst Report"
                )
        );

        taAnalystReport =
                new JTextArea(9, 60);

        taAnalystReport.setEditable(false);
        taAnalystReport.setLineWrap(true);
        taAnalystReport.setWrapStyleWord(true);
        taAnalystReport.setFont(
                new Font("Monospaced", Font.PLAIN, 13)
        );

        JScrollPane reportScrollPane =
                new JScrollPane(taAnalystReport);

        reportPnl.add(
                reportScrollPane,
                BorderLayout.CENTER
        );

        /*
         * =====================================================
         * STEP 9: STATUS PANEL
         * =====================================================
         */
        JPanel statusPnl =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        statusPnl.setBorder(
                BorderFactory.createTitledBorder(
                        "Pipeline Status"
                )
        );

        lblStatus =
                new JLabel(
                        "Enter or review the paragraph, "
                        + "then prepare the Queue."
                );

        lblStatus.setForeground(
                new Color(126, 87, 0)
        );

        lblStatus.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        statusPnl.add(lblStatus);

        /*
         * =====================================================
         * STEP 10: ORGANISE MAIN CONTENT
         * =====================================================
         */
        JPanel centrePnl =
                new JPanel(
                        new GridLayout(2, 1, 8, 8)
                );

        centrePnl.add(processingPnl);
        centrePnl.add(reportPnl);

        JPanel lowerPnl =
                new JPanel(new BorderLayout(5, 5));

        lowerPnl.add(
                secondButtonPnl,
                BorderLayout.NORTH
        );

        lowerPnl.add(
                statusPnl,
                BorderLayout.SOUTH
        );

        mainPnl.add(
                headingPnl,
                BorderLayout.NORTH
        );

        mainPnl.add(
                sourcePnl,
                BorderLayout.WEST
        );

        mainPnl.add(
                centrePnl,
                BorderLayout.CENTER
        );

        mainPnl.add(
                lowerPnl,
                BorderLayout.SOUTH
        );

        add(mainPnl, BorderLayout.CENTER);

        /*
         * =====================================================
         * STEP 11: CREATE DATA STRUCTURES
         * =====================================================
         */

        feedbackQueue =
                new LinkedList<>();

        acceptedFeedback =
                new LinkedHashSet<>();

        urgentFeedback =
                new Stack<>();

        metrics =
                new HashMap<>();

        /*
         * Initialise counters.
         */
        processedCount = 0;
        acceptedCount = 0;
        duplicateCount = 0;
        urgentDetectedCount = 0;

        /*
         * =====================================================
         * STEP 12: REGISTER LISTENERS
         * =====================================================
         */
        btnPrepareQueue.addActionListener(
                new BtnPrepareQueueClickListener()
        );

        btnProcessFeedback.addActionListener(
                new BtnProcessFeedbackClickListener()
        );

        btnReviewUrgent.addActionListener(
                new BtnReviewUrgentClickListener()
        );

        btnCalculateMetrics.addActionListener(
                new BtnCalculateMetricsClickListener()
        );

        btnGenerateReport.addActionListener(
                new BtnGenerateReportClickListener()
        );

        btnReset.addActionListener(
                new BtnResetClickListener()
        );

        setVisible(true);
    }

    /**
     * Question 1:
     *
     * Cleans the paragraph, separates the feedback statements and offers the
     * statements to the Queue in paragraph order.
     */
    private class BtnPrepareQueueClickListener
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Clear the previous pipeline data.
             */
            feedbackQueue.clear();
            acceptedFeedback.clear();
            urgentFeedback.clear();
            metrics.clear();

            processedCount = 0;
            acceptedCount = 0;
            duplicateCount = 0;
            urgentDetectedCount = 0;

            taProcessingOutput.setText("");
            taAnalystReport.setText("");

            /*
             * Get the paragraph.
             */
            String paragraph =
                    taFeedback.getText().trim();

            if (paragraph.isEmpty()) {

                lblStatus.setText(
                        "Please enter a feedback paragraph."
                );

                return;
            }

            /*
             * Clean unnecessary whitespace.
             */
            paragraph =
                    paragraph.replaceAll("\\s+", " ").trim();

            /*
             * Separate paragraph into sentences.
             *
             * The positive lookbehind keeps the punctuation
             * while splitting after ., ! or ?
             */
            String[] statements =
                    paragraph.split("(?<=[.!?])\\s+");

            /*
             * Add each statement to the Queue.
             */
            for (String statement : statements) {

                String cleaned =
                        statement.trim();

                if (!cleaned.isEmpty()) {
                    feedbackQueue.offer(cleaned);
                }
            }

            /*
             * Display the prepared Queue.
             */
            taProcessingOutput.append(
                    "QUEUE PREPARED\n"
            );

            taProcessingOutput.append(
                    "------------------------------\n"
            );

            for (String statement : feedbackQueue) {

                taProcessingOutput.append(
                        statement + "\n"
                );
            }

            taProcessingOutput.append(
                    "\nQueue size: "
                    + feedbackQueue.size()
            );

            /*
             * Enable next stage.
             */
            btnProcessFeedback.setEnabled(true);

            lblStatus.setText(
                    "Queue prepared with "
                    + feedbackQueue.size()
                    + " feedback statements."
            );
        }
    }

    /**
     * Question 2:
     *
     * Processes the Queue, accepts unique statements through the Set and pushes
     * urgent statements onto the Stack.
     */
    private class BtnProcessFeedbackClickListener
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Make sure there is something to process.
             */
            if (feedbackQueue.isEmpty()) {

                lblStatus.setText(
                        "The Queue is empty. "
                        + "Prepare the Queue first."
                );

                return;
            }

            taProcessingOutput.append(
                    "\n\nFEEDBACK PROCESSING\n"
            );

            taProcessingOutput.append(
                    "------------------------------\n"
            );

            /*
             * Process the Queue until it is empty.
             */
            while (!feedbackQueue.isEmpty()) {

                String feedback =
                        feedbackQueue.poll();

                processedCount++;

                /*
                 * Try to add the statement to the Set.
                 *
                 * Set.add() returns false if the item
                 * already exists.
                 */
                if (acceptedFeedback.add(feedback)) {

                    acceptedCount++;

                    taProcessingOutput.append(
                            "ACCEPTED: "
                            + feedback
                            + "\n"
                    );

                    /*
                     * Check for urgent feedback.
                     */
                    String lower =
                            feedback.toLowerCase();

                    if (lower.contains("urgent")
                            || lower.contains("immediately")
                            || lower.contains("unsafe")) {

                        urgentFeedback.push(feedback);

                        urgentDetectedCount++;

                        taProcessingOutput.append(
                                "URGENT -> pushed to Stack\n"
                        );
                    }

                } else {

                    duplicateCount++;

                    taProcessingOutput.append(
                            "DUPLICATE: "
                            + feedback
                            + "\n"
                    );
                }
            }

            taProcessingOutput.append(
                    "\nProcessed: "
                    + processedCount
                    + "\n"
            );

            taProcessingOutput.append(
                    "Accepted: "
                    + acceptedCount
                    + "\n"
            );

            taProcessingOutput.append(
                    "Duplicates: "
                    + duplicateCount
                    + "\n"
            );

            taProcessingOutput.append(
                    "Urgent detected: "
                    + urgentDetectedCount
                    + "\n"
            );

            /*
             * Enable the urgent review button.
             */
            btnReviewUrgent.setEnabled(
                    !urgentFeedback.isEmpty()
            );

            /*
             * Enable metrics because processing
             * has been completed.
             */
            btnCalculateMetrics.setEnabled(true);

            lblStatus.setText(
                    "Feedback processed. "
                    + acceptedCount
                    + " unique statements accepted."
            );

            /*
             * Prevent processing the same Queue twice.
             */
            btnProcessFeedback.setEnabled(false);
        }
    }

    /**
     * Question 3:
     *
     * Displays and optionally removes the most recently detected urgent
     * feedback statement.
     */
    private class BtnReviewUrgentClickListener
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (urgentFeedback.isEmpty()) {

                lblStatus.setText(
                        "No urgent feedback is currently available."
                );

                return;
            }

            /*
             * Peek first so that the statement can be
             * reviewed without immediately removing it.
             */
            String latestUrgent =
                    urgentFeedback.peek();

            taProcessingOutput.append(
                    "\nLATEST URGENT FEEDBACK\n"
            );

            taProcessingOutput.append(
                    "------------------------------\n"
            );

            taProcessingOutput.append(
                    latestUrgent + "\n"
            );

            /*
             * Remove the latest urgent item after review.
             */
            urgentFeedback.pop();

            lblStatus.setText(
                    "Reviewed urgent feedback: "
                    + latestUrgent
            );

            /*
             * Disable the button when the Stack
             * becomes empty.
             */
            if (urgentFeedback.isEmpty()) {

                btnReviewUrgent.setEnabled(false);
            }
        }
    }

    /**
     * Question 4:
     *
     * Calculates the required batch metrics and stores the named results in the
     * HashMap.
     */
    private class BtnCalculateMetricsClickListener
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Store metrics in the HashMap.
             */
            metrics.put(
                    "Processed Statements",
                    processedCount
            );

            metrics.put(
                    "Accepted Statements",
                    acceptedCount
            );

            metrics.put(
                    "Duplicate Statements",
                    duplicateCount
            );

            metrics.put(
                    "Urgent Statements",
                    urgentDetectedCount
            );

            /*
             * Display metrics.
             */
            taProcessingOutput.append(
                    "\n\nCALCULATED METRICS\n"
            );

            taProcessingOutput.append(
                    "------------------------------\n"
            );

            taProcessingOutput.append(
                    "Processed Statements: "
                    + metrics.get("Processed Statements")
                    + "\n"
            );

            taProcessingOutput.append(
                    "Accepted Statements: "
                    + metrics.get("Accepted Statements")
                    + "\n"
            );

            taProcessingOutput.append(
                    "Duplicate Statements: "
                    + metrics.get("Duplicate Statements")
                    + "\n"
            );

            taProcessingOutput.append(
                    "Urgent Statements: "
                    + metrics.get("Urgent Statements")
                    + "\n"
            );

            /*
             * Enable report generation.
             */
            btnGenerateReport.setEnabled(true);

            lblStatus.setText(
                    "Metrics calculated successfully."
            );
        }
    }

    /**
     * Question 5:
     *
     * Retrieves the calculated metrics and generates the final analyst report.
     */
    private class BtnGenerateReportClickListener
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Check that metrics have been calculated.
             */
            if (metrics.isEmpty()) {

                lblStatus.setText(
                        "Calculate metrics before generating the report."
                );

                return;
            }

            taAnalystReport.setText("");

            taAnalystReport.append(
                    "CAMPUS FEEDBACK ANALYST REPORT\n"
            );

            taAnalystReport.append(
                    "================================\n\n"
            );

            /*
             * Retrieve values from the HashMap.
             */
            int processed =
                    metrics.get("Processed Statements");

            int accepted =
                    metrics.get("Accepted Statements");

            int duplicates =
                    metrics.get("Duplicate Statements");

            int urgent =
                    metrics.get("Urgent Statements");

            taAnalystReport.append(
                    "Processed statements : "
                    + processed
                    + "\n"
            );

            taAnalystReport.append(
                    "Accepted statements  : "
                    + accepted
                    + "\n"
            );

            taAnalystReport.append(
                    "Duplicate statements : "
                    + duplicates
                    + "\n"
            );

            taAnalystReport.append(
                    "Urgent statements    : "
                    + urgent
                    + "\n\n"
            );

            taAnalystReport.append(
                    "Unique feedback items: "
                    + acceptedFeedback.size()
                    + "\n"
            );

            taAnalystReport.append(
                    "Urgent items remaining in Stack: "
                    + urgentFeedback.size()
                    + "\n\n"
            );

            /*
             * Analyst conclusion.
             */
            taAnalystReport.append(
                    "ANALYST SUMMARY\n"
            );

            taAnalystReport.append(
                    "--------------------------------\n"
            );

            if (duplicates > 0) {

                taAnalystReport.append(
                        "Duplicate feedback was identified "
                        + "and excluded from the accepted Set.\n"
                );

            } else {

                taAnalystReport.append(
                        "No duplicate feedback was identified.\n"
                );
            }

            if (urgent > 0) {

                taAnalystReport.append(
                        urgent
                        + " urgent feedback statement(s) "
                        + "were detected.\n"
                );

            } else {

                taAnalystReport.append(
                        "No urgent feedback was detected.\n"
                );
            }

            taAnalystReport.append(
                    "\nPipeline completed successfully."
            );

            lblStatus.setText(
                    "Final analyst report generated."
            );
        }
    }

    /**
     * Question 6:
     *
     * Clears the pipeline structures, counters and output.
     */
    private class BtnResetClickListener
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Clear all data structures.
             */
            feedbackQueue.clear();
            acceptedFeedback.clear();
            urgentFeedback.clear();
            metrics.clear();

            /*
             * Reset counters.
             */
            processedCount = 0;
            acceptedCount = 0;
            duplicateCount = 0;
            urgentDetectedCount = 0;

            /*
             * Clear output.
             */
            taProcessingOutput.setText("");
            taAnalystReport.setText("");

            /*
             * Restore button states.
             */
            btnProcessFeedback.setEnabled(false);
            btnReviewUrgent.setEnabled(false);
            btnCalculateMetrics.setEnabled(false);
            btnGenerateReport.setEnabled(false);

            /*
             * Restore status.
             */
            lblStatus.setText(
                    "Pipeline reset. "
                    + "Prepare the Queue to begin again."
            );
        }
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                CampusFeedbackGUI::new
        );
    }
}


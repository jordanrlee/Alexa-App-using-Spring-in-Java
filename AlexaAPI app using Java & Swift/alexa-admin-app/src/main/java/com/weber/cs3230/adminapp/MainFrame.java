package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.api.ApiClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.ArrayList;


/*
google "jtable selection listener" and dynamically change the answers as they select different intents
this would be when the user selects an intentName, the intentAnswers will automatically update to show only the selected
answers from the selected intentName.
 */

import static javax.swing.JOptionPane.showMessageDialog;

public class MainFrame extends JPanel {
    boolean isAccessingAPI = false;
    boolean isDeletingFromAPI = false;
    IntentDetailList intentList = new IntentDetailList();
    IntentAnswerList answerList = new IntentAnswerList();
    ApiClient apiClient = new ApiClient();

    public java.util.List<Object> tableIntentObjects = new ArrayList<>();
    
    public final java.util.List<Object> tableAnswerObjects = new ArrayList<>();
    private DefaultTableModel intentModel;
    protected JTable intentTable;
    protected DefaultTableModel answerModel;
    protected JTable answerTable;
    // get the selected row and place it into the intentToEdit variable
    
    protected String intentToEdit;
    protected String answerToEdit;
    private final String[] intentColumnNames = {"intentID", "intentName", "dateAdded"};
    private final String[] answerColumnNames = {"answerID", "IntentAnswer", "dateAdded"};
    
    public MainFrame() throws InterruptedException {
            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //ApiClient apiClient = new ApiClient();
                    intentList = apiClient.getIntents();
                    answerList = apiClient.getAnswers(intentList.getIntents().get(0).getIntentID());
                    return isAccessingAPI = true;
                }
                @Override
                protected void done() {
                    if (isAccessingAPI) {
                        setCursor(Cursor.getDefaultCursor());
                        JFrame mainFrame = new JFrame();
                        mainFrame.setTitle("Alexa Intent Manager");
                        mainFrame.setPreferredSize(new Dimension(1100, 300));
                        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        mainFrame.add(createBorderPanelLayout());
                        mainFrame.pack();
                        mainFrame.setVisible(true);
                    }
                }
            };
            worker.execute();
    }

    public long getSelectedIntentID() {
        int selectedRow = intentTable.getSelectedRow();
        if (selectedRow < 0)
        {
            showMessageDialog(this, "No valid item selected. Please select an item to edit.");
        }
        else {
            long intentID = intentList.getIntents().get(0).getIntentID();
        }
        return 0;
    }

    static class jTextFieldLimit extends PlainDocument {
        private final int limit;
        jTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
    
    private JComponent createBorderPanelLayout() {
        //this.intentObjects = intentObjects;
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(this.createTablePanel(), "West");
        mainPanel.add(this.createTablePanelAnswers(), "East");
        mainPanel.add(this.createButtonPanel(), "South");
        mainPanel.add(this.createLabelPanel(), "North");
        return mainPanel;
    }
    
    private JComponent createLabelPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.add(new JLabel("Alexa Admin Utility"));
        // center the label in the middle of the panel, above the table
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        return labelPanel;
    }
    
    private JComponent createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton buttonMetrics = new JButton("Metrics Table");
        buttonMetrics.addActionListener(e -> {
            // testing without the SwingWorker (didnt make a difference)
//            apiClient.getMetrics();
//            new LockoutWatcher().watcher();
//            new MetricsDialog();
            boolean gotMetrics = false;
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            SwingWorker<MetricDetailList, Void> worker = new SwingWorker<>() {
                @Override
                protected MetricDetailList doInBackground() throws Exception {
                    MetricDetailList metricDetail = new MetricDetailList();
                    //apiClient.getMetrics();
                    //boolean gotMetrics = false;
                    metricDetail = new ApiClient().getMetrics();
                    if (metricDetail == null) {
                        showMessageDialog(null, "No metrics found");
                        
                    }
//                    else {
//                        gotMetrics = true;
//                    }
                    return metricDetail;
                }

                protected void done() {
                    setCursor(Cursor.getDefaultCursor());
                    try {
                        //gotMetrics = get();
                        
                            new LockoutWatcher().watcher();
                            new MetricsDialog(get().getMetrics());
                       // }
//                        else {
//                            new JOptionPane();
//                            showMessageDialog(null, "Error obtaining metrics in worker thread.");
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        new JOptionPane();
                        showMessageDialog(null, "Error obtaining metrics from API.");

                    }
                }
            };
            worker.execute(); // execute the worker
        });
        buttonPanel.add(buttonMetrics);
        // add an answer to the answers table
        JButton buttonAddAnswers = new JButton("Add Answers");
        buttonAddAnswers.addActionListener(e -> {

        new AddAnswerDialog(this);
        });
        // edit answers for intent selected
        JButton buttonEditAnswers = new JButton("Edit Answers");
        buttonEditAnswers.addActionListener(e -> {
            LockoutWatcher.mSecondsSinceButtonClicked = System.currentTimeMillis();
            // get the selected row and place it into the intentToEdit variable
            int selectedRow = answerTable.getSelectedRow();
            if (selectedRow < 0)
            {
                showMessageDialog(this, "No valid Answer selected. Please select an answer to edit.");
            }
            else {
                // get the intentID from the selected row
                // get the answerID from the selected row
                long answerID = (long) answerTable.getValueAt(selectedRow, 0);
                //answerToEdit = answerList.get(selectedRow).toString();
                new EditAnswerDialog(answerID, this);
            }
        });
        // delete intent selected
        JButton buttonDeleteAnswer = new JButton("Delete Answer");
        buttonDeleteAnswer.addActionListener(e -> {
            LockoutWatcher.mSecondsSinceButtonClicked = System.currentTimeMillis();
            int row = answerTable.getSelectedRow();
            if (row < 0) {
                showMessageDialog(this, "No valid Answer selected. Please select an answer to delete.");
            }
            else {
                deleteAnswer();
            }
        });
        // add
        JButton buttonAdd = new JButton("Add Intent");
        buttonAdd.addActionListener(e -> {
            LockoutWatcher.mSecondsSinceButtonClicked = System.currentTimeMillis();
            new AddIntentDialog(this);
        });
        // delete
        JButton buttonDelete = new JButton("Delete Intent");
        buttonDelete.addActionListener(e -> {
            LockoutWatcher.mSecondsSinceButtonClicked = System.currentTimeMillis();
            int selectedRow = intentTable.getSelectedRow();
            if (selectedRow < 0) {
                showMessageDialog(this, "No valid intent selected. Please select an intent to delete.");
            }
            else {
                deleteIntent();
            }
        });
        // edit
        JButton buttonEdit = new JButton("Edit Intent");
        buttonEdit.addActionListener(e -> {
            LockoutWatcher.mSecondsSinceButtonClicked = System.currentTimeMillis();
            int selectedRow = intentTable.getSelectedRow();
            if (selectedRow < 0) {
                showMessageDialog(this, "No intent selected. Please select an intent to edit.");
            }
            else {
                //intentToEdit = tableIntentObjects.get(selectedRow).toString();
                //long intentID = (long) intentTable.getValueAt(selectedRow, 0);
                new EditIntentDialog(this);
            }
        });
        buttonPanel.add(buttonAdd);
        buttonPanel.add(buttonDelete);
        buttonPanel.add(buttonEdit);
        buttonPanel.add(buttonAddAnswers);
        buttonPanel.add(buttonEditAnswers);
        buttonPanel.add(buttonDeleteAnswer);
        return buttonPanel;
    }

    private void deleteIntent() {
//        int row = intentTable.getSelectedRow();
//        tableIntentObjects.remove(row);
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                apiClient.deleteIntent(intentList.getIntents().get(0).getIntentID());
                return isDeletingFromAPI = true;
            }
            @Override
            protected void done() {
                if (isDeletingFromAPI) {
                    try {
                        // reset the shared boolean
                        isDeletingFromAPI = false;
                        setCursor(Cursor.getDefaultCursor());
                        updateTableData();
                    }  catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error deleting intent");
                }
            }
        };
        worker.execute();
    }
    
    private void deleteAnswer() {
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                apiClient.deleteAnswer(answerList.getAnswers().get(0).getIntentID(), answerList.getAnswers().get(0).getAnswerID());
                return isDeletingFromAPI = true;
            }
            @Override
            protected void done() {
                if (isDeletingFromAPI) {
                    try {
                        // reset the shared boolean
                        isDeletingFromAPI = false;
                        setCursor(Cursor.getDefaultCursor());
                        updateTableData();
                    }  catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error deleting answer");
                }
            }
        };
        worker.execute();
    }

    private JComponent createTablePanel() {
        intentModel = new DefaultTableModel(getIntentTableData(), intentColumnNames);
        intentTable = new JTable(intentModel);
        intentTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPaneIntents = new JScrollPane(intentTable);
        intentTable.setFillsViewportHeight(true);
        scrollPaneIntents.setVisible(true);
        return scrollPaneIntents;
    }
    
    private JComponent createTablePanelAnswers() {
        answerModel = new DefaultTableModel(getAnswerTableData(), answerColumnNames);
        answerTable = new JTable(answerModel);
        answerTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPaneAnswers = new JScrollPane(answerTable);
        answerTable.setFillsViewportHeight(true);
        scrollPaneAnswers.setVisible(true);
        return scrollPaneAnswers;
    }
    
    protected void updateTableData() {
        intentModel.setDataVector(getIntentTableData(), intentColumnNames);
        answerModel.setDataVector(getAnswerTableData(), answerColumnNames);
    }
    
    private Object[][] getIntentTableData() {
        java.util.List<Object[]> rows = new ArrayList<>();
        for(IntentDetail intents : intentList.getIntents()) {
            rows.add(new Object[]{intents.getIntentID(), intents.getName(), intents.getDateAdded()});
        }
        return rows.toArray(new Object[0][0]);
    }
    
    private Object[][] getAnswerTableData() {
        java.util.List<Object[]> rows = new ArrayList<>();
        for(IntentAnswer answers :  answerList.getAnswers()) {
            rows.add(new Object[]{answers.getAnswerID(), answers.getText(), answers.getDateAdded()});
        }
        return rows.toArray(new Object[0][0]);
    }


}
package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.api.ApiClient;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class EditAnswerDialog extends JDialog {
    boolean isAdding = false;
    MainFrame.jTextFieldLimit limit = new MainFrame.jTextFieldLimit(300);
    protected Long dialogAnswerFromAnswerIDToEdit;
    protected String dialogAnswerFromAnswerIDToEditString;

    private final MainFrame mainFrame;
    ApiClient apiClient = new ApiClient();
    
    public EditAnswerDialog(long answerFromAnswerIDToEdit, MainFrame mainFrame) {
        this.dialogAnswerFromAnswerIDToEdit = answerFromAnswerIDToEdit;
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(400, 150));
        pack();
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setTitle("Edit Answer");
        add(createEditAnswerButtonPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }
    
    private JComponent createEditAnswerButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JTextField answerTextField = new JTextField(10);
        answerTextField.setDocument(limit);
        // im pretty sure this will just give me the ID but not the answer from the database to edit

        answerTextField.setText(dialogAnswerFromAnswerIDToEditString);
        JButton buttonEditAnswer = new JButton("Confirm");
        buttonEditAnswer.addActionListener(e -> {
            if (answerTextField.getText().equals("")) {
                showMessageDialog(this, "No valid answer entered. Please enter a valid answer.");
            }
            else if (mainFrame.tableAnswerObjects.contains(answerTextField.getText())) {
                showMessageDialog(this, "Duplicate answer entered. Please enter a unique answer.");
            }
            else {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        apiClient.updateAnswer(apiClient.getIntents().getIntents().get(0).getIntentID(), apiClient.getAnswers(apiClient.getIntents().getIntents().get(0).getIntentID()).getAnswers().get(0).getAnswerID() , answerTextField.getText());
                        return isAdding = true;
                    }

                    @Override
                    protected void done() {
                        if (isAdding) {
                            try {
                                mainFrame.setCursor(Cursor.getDefaultCursor());
                                //int selectedRow = mainFrame.table2.getSelectedRow();
                                Object answer = answerTextField.getText();
                                mainFrame.tableAnswerObjects.set(mainFrame.answerTable.getSelectedRow(), answer);
                                mainFrame.updateTableData();
                                answerTextField.setText("");
                                setVisible(false);
                            }  catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Error Adding answer");
                        }
                    }
                };

            }
        });
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> setVisible(false));
        buttonPanel.add(answerTextField);
        buttonPanel.add(buttonEditAnswer);
        buttonPanel.add(buttonCancel);
        return buttonPanel;
    }
}
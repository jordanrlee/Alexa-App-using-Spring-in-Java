package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.api.ApiClient;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddAnswerDialog extends JDialog {
    boolean isAddingAnswer = false;
    private final MainFrame mainFrame;
    ApiClient apiClient = new ApiClient();

    MainFrame.jTextFieldLimit limit = new MainFrame.jTextFieldLimit(300);
    protected AddAnswerDialog(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(400, 150));
        pack();
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setTitle("Add Answer");
        add(createAddAnswerButtonPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }
    
    private Component createAddAnswerButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JTextField answerTextField = new JTextField(10);
        answerTextField.setDocument(limit);
        JButton buttonAddAnswer = new JButton("Confirm");
        // this intent is funky
        //long intentID = mainFrame.getSelectedIntentID();


        buttonAddAnswer.addActionListener(e -> {
            if (answerTextField.getText().equals("")) {
                showMessageDialog(this, "No valid answer entered. Please enter a valid answer.");
            }
            //mainFrame.tableAnswerObjects.contains(answerTextField.getText())
//            else if (apiClient.checkDuplicateAnswer(intentID ,answerTextField.getText()) ) {
//                showMessageDialog(this, "Duplicate answer entered. Please enter a unique answer.");
//            }
            else {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                // use another swing worker
                SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        // loading cursor
                        //mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        apiClient.saveNewAnswer(apiClient.getIntents().getIntents().get(0).getIntentID(), answerTextField.getText());
                        return isAddingAnswer = true;
                    }

                    @Override
                    protected void done() {
                        if (isAddingAnswer) {
                            try {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                mainFrame.updateTableData();
                                answerTextField.setText("");
                                setVisible(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            showMessageDialog(null, "Error adding answer. Please cancel and try again.");
                        }
                        //Object answer = answerTextField.getText();
                        //mainFrame.tableAnswerObjects.add(answer);
                    }
                };
                worker.execute();
            }
        });
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> setVisible(false));
        buttonPanel.add(answerTextField);
        buttonPanel.add(buttonAddAnswer);
        buttonPanel.add(buttonCancel);
        return buttonPanel;
    }
}
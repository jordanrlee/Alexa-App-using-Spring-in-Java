package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.api.ApiClient;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddIntentDialog extends JDialog {
    boolean isAddingIntent = false;

    ApiClient apiClient = new ApiClient();
    private final MainFrame mainFrame;

    MainFrame.jTextFieldLimit limit = new MainFrame.jTextFieldLimit(85);
    
    protected AddIntentDialog(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(400, 150));
        pack();
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setTitle("Add Intent");
        add(createAddAnswerButtonPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }
    
    private Component createAddAnswerButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JTextField intentTextField = new JTextField(10);
        intentTextField.setDocument(limit);
        JButton buttonAddIntent = new JButton("Confirm");
        buttonAddIntent.addActionListener(e -> {
            if (intentTextField.getText().equals("")) {
                showMessageDialog(this, "No valid answer entered. Please enter a valid answer.");
            }
//            else if (apiClient.checkDuplicateAnswer(mainFrame.getSelectedIntentID(),intentTextField.getText())) {
//                showMessageDialog(this, "Duplicate answer entered. Please enter a unique answer.");
//            }
            else {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        // loading cursor
                        //setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        apiClient.saveNewIntent(intentTextField.getText());
                        return isAddingIntent = true;
                    }
                    @Override
                    protected void done() {
                        if (isAddingIntent) {
                            try {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                mainFrame.updateTableData();
                                intentTextField.setText("");
                                setVisible(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                worker.execute();
            }
        });
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> setVisible(false));
        buttonPanel.add(intentTextField);
        buttonPanel.add(buttonAddIntent);
        buttonPanel.add(buttonCancel);
        return buttonPanel;
    }
}
package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.api.ApiClient;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class EditIntentDialog extends JDialog{
    boolean isEditing = false;
    ApiClient apiClient = new ApiClient();

    MainFrame.jTextFieldLimit limit = new MainFrame.jTextFieldLimit(85);
    //protected String dialogIntentToEdit = "";
    private final MainFrame mainFrame;
    
    public EditIntentDialog(MainFrame mainFrame) {
        //this.dialogIntentToEdit = intentToEdit;
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(400, 150));
        pack();
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setTitle("Edit Intent");
        add(createEditAnswerButtonPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }
    
    private JComponent createEditAnswerButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JTextField intentTextField = new JTextField(10);
        intentTextField.setDocument(limit);
        //intentTextField.setText(dialogIntentToEdit);
        JButton buttonEditAnswer = new JButton("Confirm");
        buttonEditAnswer.addActionListener(e -> {

            if (intentTextField.getText().equals("")) {
                showMessageDialog(this, "No valid answer entered. Please enter a valid answer.");
            }
            else if (mainFrame.tableIntentObjects.contains(intentTextField.getText())) {
                showMessageDialog(this, "Duplicate answer entered. Please enter a unique answer.");
            }
            else {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        apiClient.updateIntent(apiClient.getIntents().getIntents().get(0).getIntentID() ,intentTextField.getText());
                        return isEditing = true;
                    }
                    @Override
                    protected void done() {
                        if (isEditing) {
                            try {
                                setCursor(Cursor.getDefaultCursor());
                                mainFrame.updateTableData();
                                intentTextField.setText("");
                                setVisible(false);
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

                // selected row is changed
                //int selectedRow = mainFrame.table2.getSelectedRow();
                //Object answer = intentTextField.getText();
                //mainFrame.tableIntentObjects.set(mainFrame.intentTable.getSelectedRow(), answer);

            }
        });
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> setVisible(false));
        buttonPanel.add(intentTextField);
        buttonPanel.add(buttonEditAnswer);
        buttonPanel.add(buttonCancel);
        return buttonPanel;
    }

}

package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.api.ApiClient;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginDialog extends JDialog {
    MainFrame.jTextFieldLimit limit = new MainFrame.jTextFieldLimit(10);
    public String username = "";
    public String password = "";
    //private final String adminUsername = "admin";
    //private final String adminPassword = "admin";
    public boolean loginSuccessful = false;
    ApiClient apiClient = new ApiClient();
    
    public LoginDialog() {
        setPreferredSize(new Dimension(400, 150));
        pack();
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setTitle("Login");
        add(createLoginFieldsPanel(), BorderLayout.CENTER);
        setVisible(true);
    }
    
    private void jOptionPane() {
        showMessageDialog(this, "Invalid Login Information", "Login Error", JOptionPane.WARNING_MESSAGE);
    }
    
    private JComponent createLoginFieldsPanel() {
        JPanel loginFieldsPanel = new JPanel();
        loginFieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelUsername = new JLabel("Username: ");
        JLabel labelPassword = new JLabel("Password: ");
        JTextField usernameField = new JTextField(10);
        // set a limit to the amount of characters in usernameField
        usernameField.setDocument(limit);
        JPasswordField passwordField = new JPasswordField(10);
        // set a limit to the amount of characters in passwordField
        passwordField.setDocument(new MainFrame.jTextFieldLimit(10));
    
        JButton buttonLogin = new JButton("Login");
        buttonLogin.addActionListener(e -> {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            SwingWorker<Boolean, Object> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    username = usernameField.getText();
                    password = new String(passwordField.getPassword());
                    boolean validated = apiClient.validateCreds(username, password);
                    // use a done method
                    return validated;
                }
                @Override
                protected void done() {
                    setCursor(Cursor.getDefaultCursor());
                    try {
                        boolean validated = get();
                        if (validated) {
                            loginSuccessful = true;
                            new LockoutWatcher().watcher();
                            closeDialog();
                        }
                        else {
                            jOptionPane();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        jOptionPane();
                    }
                }
            }; // end worker code
            worker.execute(); // execute the worker
        });
        JButton buttonExit = new JButton("Exit");
        buttonExit.addActionListener(e -> closeDialog());
        loginFieldsPanel.add(labelUsername);
        loginFieldsPanel.add(usernameField);
        loginFieldsPanel.add(labelPassword);
        loginFieldsPanel.add(passwordField);
        loginFieldsPanel.add(buttonLogin);
        loginFieldsPanel.add(buttonExit);
        return loginFieldsPanel;
    }
    
    private void closeDialog() {
        setVisible(false);
        dispose();
    }
}


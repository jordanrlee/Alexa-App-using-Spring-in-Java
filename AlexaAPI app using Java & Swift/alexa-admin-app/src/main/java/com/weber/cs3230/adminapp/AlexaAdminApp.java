package com.weber.cs3230.adminapp;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class AlexaAdminApp {
    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {

                LoginDialog loginDialog = new LoginDialog();
                //loginDialog.loginSuccessful = true;
                if (loginDialog.loginSuccessful) {
                    SwingWorker<Boolean, Object> worker = new SwingWorker<>() {
                        @Override
                        protected Boolean doInBackground() throws Exception {
                            // get all the intents, and display them in mainFrame table

                            return true;
                        }
                        @Override
                        protected void done() {
                            MainFrame mainFrame;
    
                            // set the cursor to the default cursor
                            //setCursor(Cursor.getDefaultCursor());
                            try {
                                mainFrame = new MainFrame();
                                mainFrame.setVisible(true);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
                    // done
                    worker.execute();
                    // use the get() to update table
                    }
                
                });
    }

    public String setDateAdded() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

}
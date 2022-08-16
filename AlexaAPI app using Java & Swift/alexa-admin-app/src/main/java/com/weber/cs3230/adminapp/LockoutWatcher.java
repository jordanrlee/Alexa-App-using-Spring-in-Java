package com.weber.cs3230.adminapp;

import javax.swing.*;

public class LockoutWatcher {
    
    public static volatile long mSecondsSinceButtonClicked = 0L;
    //private final long lockoutThreshold = 6000L;
    public void watcher() {
        new Thread(this::beginWatchLoop).start();
    }
    
    public void beginWatchLoop() {
        mSecondsSinceButtonClicked = System.currentTimeMillis();
        boolean continueLooping = true;
        while (continueLooping) {
            try {
                Thread.sleep(30_000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long currentTime = System.currentTimeMillis();
            long buttonClickTime = mSecondsSinceButtonClicked;
            if (theDifferenceBetween(currentTime, buttonClickTime)) {
                continueLooping = false;
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Locked out due to inactivity. Please sign in again.");
                    LoginDialog loginDialog = new LoginDialog();
                });
            }
        }
    }
    
    private boolean theDifferenceBetween(long currentTime, long buttonClickTime) {
        
        long difference = currentTime - buttonClickTime;
        return difference > 600000;
    }
}

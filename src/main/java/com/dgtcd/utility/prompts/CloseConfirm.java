package com.dgtcd.utility.prompts;

import java.awt.event.*;
import javax.swing.*;

public class CloseConfirm extends JFrame {
    public CloseConfirm(WindowEvent e) { //confirmation when trying to close the app
        JFrame frame = (JFrame)e.getSource();
        int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the application?", "Exit Application", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}

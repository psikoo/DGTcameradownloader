package com.dgtcd.utility.prompts;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class OpenFolder {
    public static void openFolder(String path) throws IOException { //open folder to given path
        Desktop desktop = Desktop.getDesktop();
        File dirToOpen = null;
        try {
            dirToOpen = new File(path);
            desktop.open(dirToOpen);
        } catch (IllegalArgumentException e) {
            Runnable sound2 = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
            if(sound2 != null) { sound2.run(); }
        }
    }
}

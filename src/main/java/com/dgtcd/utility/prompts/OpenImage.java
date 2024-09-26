package com.dgtcd.utility.prompts;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.dgtcd.utility.FileData;

public class OpenImage extends JFrame {
    public OpenImage(String label) { //opens img in a new window
        //Calculate location of the image
        String cameraID = label.substring(label.lastIndexOf("photos\\")+7, label.lastIndexOf("\\"));
        String photoID = label.substring(label.lastIndexOf("\\")+1, label.lastIndexOf("."));
        //Setup basic JFrame
        JFrame frame = new JFrame ();
        frame.setTitle(label);
        frame.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/ico.png")).getImage());
        frame.setSize(FileData.getWidth("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+photoID+".jpg"), FileData.getHeight("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+photoID+".jpg"));
        frame.setResizable(false);
        frame.setVisible(true);
        JLabel image = new JLabel(new ImageIcon(getClass().getResource("/assets/img/noPhotos.png")));
        //Display image
        try { image.setIcon(new ImageIcon(ImageIO.read(new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+photoID+".jpg"))));
        } catch (IOException ignore) {};
        frame.add(image);
    }
}

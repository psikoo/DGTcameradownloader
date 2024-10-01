package com.dgtcd.utility.prompts;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.dgtcd.utility.DirEncode;
import com.dgtcd.utility.FileData;

public class OpenImage extends JFrame {
    private int counter;
    private JFrame frame;
    private JLabel image;
    public OpenImage(String label) { //opens img in a new window
        //Calculate location of the image
        String cameraID = label.substring(label.lastIndexOf("photos\\")+7, label.lastIndexOf("\\"));
        String photoID = label.substring(label.lastIndexOf("\\")+1, label.lastIndexOf("."));
        counter = 0;
        //Setup basic JFrame
        frame = new JFrame ();
        frame.setTitle(label);
        frame.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/ico.png")).getImage());
        frame.setSize(FileData.getWidth("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+photoID+".jpg"), FileData.getHeight("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+photoID+".jpg"));
        frame.setResizable(false);
        frame.setVisible(true);
        image = new JLabel(new ImageIcon(getClass().getResource("/assets/img/noPhotos.png")));
        //Display image
        try { image.setIcon(new ImageIcon(ImageIO.read(new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+photoID+".jpg"))));
        } catch (IOException ignore) {};
        frame.add(image);
        //Key listener
        frame.addKeyListener(new KeyListener(){
            @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                        counter++;
                        System.out.println("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(cameraID)+"\\"+(Integer.parseInt(photoID)+counter)+".jpg");
                        frame.setTitle("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(cameraID)+"\\"+(Integer.parseInt(photoID)+counter)+".jpg");
                        try { image.setIcon(new ImageIcon(ImageIO.read(new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+(Integer.parseInt(photoID)+counter)+".jpg"))));
                        } catch (IOException ignore) {};
                    } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                        counter--;
                        System.out.println("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(cameraID)+"\\"+(Integer.parseInt(photoID)+counter)+".jpg");
                        frame.setTitle("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(cameraID)+"\\"+(Integer.parseInt(photoID)+counter)+".jpg");
                        try { image.setIcon(new ImageIcon(ImageIO.read(new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+(Integer.parseInt(photoID)+counter)+".jpg"))));
                        } catch (IOException ignore) {};
                    }
                }
                @Override
                public void keyTyped(KeyEvent ignore) {}
                @Override
                public void keyReleased(KeyEvent ignore) {}
       });
    }
}

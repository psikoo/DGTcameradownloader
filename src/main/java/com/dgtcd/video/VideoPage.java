package com.dgtcd.video;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.dgtcd.photo.PhotoDeleteDuplicates;
import com.dgtcd.photo.PhotoTable;
import com.dgtcd.utility.DirEncode;
import com.dgtcd.utility.prompts.OpenFolder;

import net.miginfocom.swing.MigLayout;

public class VideoPage {
    public static String cameraURL;

    private static JLabel lblURLstart;
    private static JTextField tfURL;
    private static JButton btnURLVideo;
    private static JButton btnURLDuplicate;
    private static JButton btnURLReload;
    private static JButton btnURLFolder;

    private static JPanel pnlConnected;
    private static JScrollPane scTblConnected;

    private static ExecutorService videoMaker; 

    public VideoPage(JPanel pageVideo) {
        GridBagConstraints c = new GridBagConstraints();
        //
        //  Top menu
        //
        //URL section
        JPanel pnlURL = new JPanel(new GridBagLayout());
        //URL left
        JPanel pnlURLLeft = new JPanel(new GridBagLayout());
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        pnlURL.add(pnlURLLeft, c);
        //URL left top
        JPanel pnlURLLeftTop = new JPanel(new MigLayout());
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        pnlURLLeft.add(pnlURLLeftTop, c);
        //URL text
        lblURLstart = new JLabel("Camera URL: ");
        lblURLstart.setBorder(new EmptyBorder(0, 0, 50, 0));
        pnlURLLeftTop.add(lblURLstart);
        tfURL = new JTextField("");
        pnlURLLeftTop.add(tfURL);
        //URL right
        JPanel pnlURLRight = new JPanel(new GridLayout(2,2));
        pnlURLRight.setBorder(new EmptyBorder(5, 5, 0, 5));
        c.anchor = GridBagConstraints.NORTHEAST;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 1;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 0;
        pnlURL.add(pnlURLRight, c);
        //Video button
        btnURLVideo = new JButton("Video", new ImageIcon(getClass().getResource("/assets/icons/video.png")));
        btnURLVideo.addActionListener(new BtnVideoListener());
        btnURLVideo.setPreferredSize(new Dimension(110, 40));
        pnlURLRight.add(btnURLVideo);
        //Reload button
        btnURLReload = new JButton("Reload", new ImageIcon(getClass().getResource("/assets/icons/reload.png")));
        btnURLReload.addActionListener(new BtnReloadListener());
        btnURLReload.setPreferredSize(new Dimension(110, 40));
        pnlURLRight.add(btnURLReload);
        //Duplicate button
        btnURLDuplicate = new JButton("Duplicate", new ImageIcon(getClass().getResource("/assets/icons/x.png")));
        btnURLDuplicate.addActionListener(new BtnDuplicateListener());
        btnURLDuplicate.setPreferredSize(new Dimension(110, 40));
        pnlURLRight.add(btnURLDuplicate);
        //Folder button
        btnURLFolder = new JButton("Folder", new ImageIcon(getClass().getResource("/assets/icons/folder.png")));
        btnURLFolder.addActionListener(new BtnFolderListener());
        btnURLFolder.setPreferredSize(new Dimension(110, 40));
        pnlURLRight.add(btnURLFolder);
        //URL section settings
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        pageVideo.add(pnlURL, c);
        //
        //  Page content
        //
        //Page content
        pnlConnected = new JPanel(new GridLayout());
        pnlConnected.setBorder(new EmptyBorder(5, 5, 5, 5));
        //Page content settings
        c.anchor = GridBagConstraints.SOUTH;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 100;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        pageVideo.add(pnlConnected, c);
    }

    private class BtnVideoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            cameraURL = tfURL.getText();
            //Check if url is not set
            if(tfURL.getText().isEmpty() == true) {
                Runnable sound2 = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                if(sound2 != null) { sound2.run(); }
            } else {
                videoMaker = Executors.newSingleThreadExecutor();
                Runnable videoMakerRunnable = new VideoMaker();
                videoMaker.execute(videoMakerRunnable);
            }
        }
    }

    private class BtnReloadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            if(tfURL.getText().isEmpty() == true) {
                Runnable sound2 = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                if(sound2 != null) { sound2.run(); }
            } else {
                if(scTblConnected == null) { //Create Table
                    scTblConnected = new JScrollPane(PhotoTable.createTable(DirEncode.dirEncode(tfURL.getText())));
                    pnlConnected.add(scTblConnected);
                } else{
                    //Removes and recalculates the table
                    reloadTable();
                }
            }
        }
    }

    public static void reloadTable() {
        //Reload Table
        pnlConnected.remove(scTblConnected);
        scTblConnected = new JScrollPane(PhotoTable.createTable(DirEncode.dirEncode(tfURL.getText())));
        pnlConnected.add(scTblConnected);
    }

    private class BtnDuplicateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            //Check if url is not set
            if(tfURL.getText().isEmpty() == true) {
                Runnable sound2 = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                if(sound2 != null) { sound2.run(); }
            } else {
                System.out.println(" <-- Deleting Duplicates: "+tfURL.getText()+" -->");
                PhotoDeleteDuplicates.deleteAndRenamePhotos(DirEncode.dirEncode(tfURL.getText()));
            }       
        }
    }
    
    private class BtnFolderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            //Open the photos folder
            try { OpenFolder.openFolder("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(tfURL.getText())); } 
            catch (IOException e) {
                Runnable sound2 = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                if(sound2 != null) { sound2.run(); }
            }
        }
    }
}

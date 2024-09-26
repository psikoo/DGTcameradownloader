package com.dgtcd.photo;
import com.dgtcd.App;
import com.dgtcd.utility.DirEncode;
import com.dgtcd.utility.prompts.OpenFolder;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class PhotoPage extends JPanel {
    private static JLabel lblURLstart;
    private static JTextField tfURL;
    private static JButton btnURLStart;
    private static JButton btnURLStop;
    private static JButton btnURLReload;
    private static JButton btnURLFolder;
    private static JLabel lblConnect;

    private static JPanel pnlConnected;
    private static JScrollPane scTblConnected;

    private static ScheduledExecutorService downloader; 

    public PhotoPage(JPanel pagePhoto) {
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
        c.weightx = 100;
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
        pnlURLLeftTop.add(lblURLstart);
        tfURL = new JTextField("");
        pnlURLLeftTop.add(tfURL);
        //URL left bottom
        JPanel pnlURLLeftBot = new JPanel(new MigLayout());
        pnlURLLeftBot.setBorder(new EmptyBorder(0, 20, 17, 0));
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        pnlURLLeft.add(pnlURLLeftBot, c);
        lblConnect = new JLabel("> Not connected to any camera");
        pnlURLLeftBot.add(lblConnect);
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
        //Start button
        btnURLStart = new JButton("Start", new ImageIcon(getClass().getResource("/assets/icons/magnifyingGlass.png")));
        btnURLStart.addActionListener(new BtnStartListener());
        btnURLStart.setPreferredSize(new Dimension(80, 40));
        pnlURLRight.add(btnURLStart);
        //Reload button
        btnURLReload = new JButton("Reload", new ImageIcon(getClass().getResource("/assets/icons/reload.png")));
        btnURLReload.addActionListener(new BtnReloadListener());
        btnURLReload.setPreferredSize(new Dimension(90, 40));
        btnURLReload.setEnabled(false);
        pnlURLRight.add(btnURLReload);
        //Stop button
        btnURLStop = new JButton("Stop", new ImageIcon(getClass().getResource("/assets/icons/x.png")));
        btnURLStop.addActionListener(new BtnStopListener());
        btnURLStop.setPreferredSize(new Dimension(80, 40));
        btnURLStop.setEnabled(false);
        pnlURLRight.add(btnURLStop);
        //Folder button
        btnURLFolder = new JButton("Folder", new ImageIcon(getClass().getResource("/assets/icons/folder.png")));
        btnURLFolder.addActionListener(new BtnFolderListener());
        btnURLFolder.setPreferredSize(new Dimension(90, 40));
        btnURLFolder.setEnabled(false);
        pnlURLRight.add(btnURLFolder);
        //URL section settings
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        pagePhoto.add(pnlURL, c);
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
        pagePhoto.add(pnlConnected, c);

        //
        //  Camera mode
        //
        tfURL.setText(App.cameraURL);
        if(App.cameraURL != "") {
            startSequence();
        }
    }

    private class BtnStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            //Check if url is not set
            if(tfURL.getText().isEmpty() == true) {
                Runnable sound2 = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                if(sound2 != null) { sound2.run(); }
            } else {
                startSequence();
            }
        }
    }

    public static void startSequence() {
        //Ui setup
        App.tabPanel.setEnabledAt(1, false);
        btnURLStart.setEnabled(false);
        btnURLStop.setEnabled(true);
        btnURLReload.setEnabled(true);
        btnURLFolder.setEnabled(true);
        tfURL.setEnabled(false);
        //Set cameraID
        App.cameraID = DirEncode.dirEncode(tfURL.getText());
        App.cameraURL = tfURL.getText();
        lblConnect.setText("> Connected to camera " + App.cameraURL);
        //Create Table
        scTblConnected = new JScrollPane(PhotoTable.createTable(App.cameraID));
        pnlConnected.add(scTblConnected);
        //Create a folder for the photos
        File cameraFolder = new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+App.cameraID);
        if (!cameraFolder.exists()){
            cameraFolder.mkdirs();
        }
        //Start downloader
        downloader = Executors.newScheduledThreadPool(1);
        Runnable photoDownload = new PhotoDownloader();
        long timeDelay = App.downloadDelay;
        downloader.scheduleAtFixedRate(photoDownload, 1,timeDelay, TimeUnit.SECONDS);
        System.out.println(" <-- Downloader Started: "+App.cameraURL+" -->");
    }

    private class BtnReloadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            //Removes and recalculates the table
            reloadTable();
        }
    }

    public static void reloadTable() {
        pnlConnected.remove(scTblConnected);
        scTblConnected = new JScrollPane(PhotoTable.createTable(App.cameraID));
        pnlConnected.add(scTblConnected);
    }

    private class BtnStopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            //Reset ui
            App.tabPanel.setEnabledAt(1, true);
            btnURLStart.setEnabled(true);
            btnURLStop.setEnabled(true);
            btnURLReload.setEnabled(false);
            btnURLFolder.setEnabled(false);
            tfURL.setEnabled(true);
            lblConnect.setText("> Not connected to any camera");
            pnlConnected.remove(scTblConnected);
            //Stop downloader
            downloader.shutdown();
            System.out.println(" <-- Downloader Stopped: "+App.cameraURL+" -->");
        }
    }

    
    private class BtnFolderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            //Open the photos folder
            try { OpenFolder.openFolder("C:\\ProgramData\\dgtcd\\assets\\photos\\"+App.cameraID); } 
            catch (IOException e) {
                Runnable sound2 = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                if(sound2 != null) { sound2.run(); }
            }
        }
    }
} 

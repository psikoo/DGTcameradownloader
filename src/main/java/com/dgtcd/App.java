package com.dgtcd;
import com.dgtcd.photo.PhotoPage;
import com.dgtcd.utility.DirEncode;
import com.dgtcd.utility.prompts.CloseConfirm;
import com.dgtcd.video.VideoPage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App extends JFrame  {
    public static int downloadDelay = 10; //delay between photo downloads
    public static String cameraID = ""; //preselected cameraID
    public static String cameraURL = ""; //preselected cameraURL
    public App() { //default configuration start point
        System.out.println(">------------------------------");
        System.out.println("| Camera: "+cameraURL);
        System.out.println("| Download delay: "+downloadDelay);
        System.out.println(">------------------------------");
        startApp();
    } App(int mode, String arg1) { //custom configuration for 1 parameter start point
        String camera = "https://infocar.dgt.es/etraffic/data/camaras/598.jpg";
        String delay = downloadDelay+"";
        if(mode==0) { //camera mode
            camera = arg1; 
            cameraID = DirEncode.dirEncode(camera);
            cameraURL = camera;
            System.out.println(">------------------------------");
            System.out.println("| Camera: "+cameraURL);
        } else if(mode==1) { //delay mode
            delay = arg1; 
            downloadDelay = Integer.parseInt(delay);
        }
        System.out.println("| Download delay: "+delay);
        System.out.println(">------------------------------");
        startApp();
    } App(int mode, String arg1, String arg2) { //custom configuration for 2 parameter start point
        String camera = "https://infocar.dgt.es/etraffic/data/camaras/598.jpg";
        String delay = downloadDelay+"";
        if(mode==3) { camera = arg1; delay = arg2; } //delay camera mode
        else if(mode==4) { camera = arg2; delay = arg1; } //camera delay mode
        cameraID = DirEncode.dirEncode(camera);
        cameraURL = camera;
        downloadDelay = Integer.parseInt(delay);
        System.out.println(">------------------------------");
        System.out.println("| Camera: "+cameraURL);
        System.out.println("| Download delay: "+delay);
        System.out.println(">------------------------------");
        startApp();
    }

    public static JTabbedPane tabPanel;
    public void startApp() {
        //Main window configuration
        setTitle("DGT Camera Downloader");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/ico.png")).getImage());
        setSize(910, 540);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        // JTabs
        tabPanel = new JTabbedPane();
        //tabs
        JPanel pagePhoto = new JPanel(new GridBagLayout()); 
        JPanel pageVideo = new JPanel(new GridBagLayout());
        tabPanel.addTab("Photo", pagePhoto); 
        tabPanel.addTab("Video", pageVideo); 
        //JTabs settings
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(tabPanel, c);

        new PhotoPage(pagePhoto);
        new VideoPage(pageVideo);

        //Custom close window behavior
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new CloseConfirm(e);
            }  
        });
        setVisible(true);
    }
}
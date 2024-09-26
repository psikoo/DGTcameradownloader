package com.dgtcd.utility.setup;

import java.io.File;

public class Install {
    public Install() {
        // Create dgtcd folder
        File programData = new File("C:\\ProgramData\\dgtcd");
        if (!programData.exists()){
            programData.mkdirs();
        }
        // Create assets folder
        File assets = new File("C:\\ProgramData\\dgtcd\\assets");
        if (!assets.exists()){
            assets.mkdirs();
        }
        // Create photos folder
        File photos = new File("C:\\ProgramData\\dgtcd\\assets\\photos");
        if (!photos.exists()){
            photos.mkdirs();
        }
        // Create videos folder
        File videos = new File("C:\\ProgramData\\dgtcd\\assets\\videos");
        if (!videos.exists()){
            videos.mkdirs();
        }
    }
}

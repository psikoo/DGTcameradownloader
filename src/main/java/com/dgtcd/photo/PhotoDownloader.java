package com.dgtcd.photo;
import com.dgtcd.App;
import com.dgtcd.utility.requests.Get;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class PhotoDownloader implements Runnable {
    @Override
    public void run() {
        int numberOfFiles = 0;
        try { numberOfFiles = new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+App.cameraID).list().length; } catch(NullPointerException e) {};
        System.out.println("> Downloading: "+App.cameraURL+" - "+numberOfFiles+".jpg");
        try { Get.getImgFromURL(App.cameraURL, "C:\\ProgramData\\dgtcd\\assets\\photos\\"+App.cameraID+"\\"+numberOfFiles+".jpg");
        } catch (IOException ignore) {} catch (URISyntaxException e) { e.printStackTrace(); }
        //PhotoPage.reloadTable();
    }
}

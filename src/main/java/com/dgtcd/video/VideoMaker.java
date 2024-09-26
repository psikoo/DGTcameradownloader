package com.dgtcd.video;

import java.io.File;
import java.io.IOException;

import com.dgtcd.utility.DirEncode;

public class VideoMaker implements Runnable { //TODO fully redo with a wrapper pls kill me
    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        System.out.println(" <-- Video Maker Started: "+VideoPage.cameraURL+" -->");
        int numberOfFiles = 0;
        try { numberOfFiles = new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(VideoPage.cameraURL)).list().length; } catch(NullPointerException ignore) {}
        
        if(numberOfFiles != 0) {
            //first command
            int counter = 0;
            String cmdPre = "ffmpeg -framerate 1 ";
            String[] inputsArr = new String[2*numberOfFiles]; // -i file
            String cmdPos = " -y -vf pad=ceil(iw/2)*2:ceil(ih/2)*2 -c:v libx264 -r 30 -pix_fmt yuv420p C:\\ProgramData\\dgtcd\\assets\\videos\\video.mp4";
            //command builder
            for(int i=0; i<inputsArr.length; i++) {
                if (i%2 == 1) { 
                    inputsArr[i] = "C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(VideoPage.cameraURL)+"\\"+counter+".jpg"; 
                    counter++; //evens
                } else if (i%2 == 0) { inputsArr[i] = "-i"; } //odds
                else {System.out.print("Fuck!");} //TODO remove
            }
            String inputs = String.join(" ", inputsArr);
            String cmdFull = cmdPre + inputs + cmdPos;
            System.out.println("🟧 "+cmdFull);
            // run the ffmpeg commands
            try {
                Runtime.getRuntime().exec(cmdFull);
            } catch (IOException e) {e.printStackTrace();}
        } else { System.out.println("> NO PHOTOS IN THE GIVEN DIRECTORY <"); }
    }
}

// let images = new Array(fileNumber);
// for (let i=0; i < images.length; i++) {
//   images[i] = { path: path+`\\img\\${i}.jpg`, caption: `image ${i+1} out of ${fileNumber}` };
// }

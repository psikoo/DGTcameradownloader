package com.dgtcd.video;

import java.io.File;
import java.io.IOException;

import com.dgtcd.utility.DirEncode;

import net.bramp.ffmpeg.*;
import net.bramp.ffmpeg.builder.*;

public class VideoMaker implements Runnable { //TODO fully redo with a wrapper pls kill me
    @Override
    public void run() {
        System.out.println(" <-- Video Maker Started: "+VideoPage.cameraURL+" -->");
        int numberOfFiles = 0;
        try { numberOfFiles = new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(VideoPage.cameraURL)).list().length; } catch(NullPointerException ignore) {}
        
        if(numberOfFiles != 0) {
            System.out.println("> Number of files detected: "+numberOfFiles);
            try {
                FFmpeg ffmpeg = new FFmpeg(System.getProperty("user.dir")+"\\lib\\ffmpeg\\ffmpeg.exe");
                FFprobe ffprobe = new FFprobe(System.getProperty("user.dir")+"\\lib\\ffmpeg\\");
                File dir = new File("C:\\ProgramData\\dgtcd\\assets\\videos\\"+DirEncode.dirEncode(VideoPage.cameraURL));
                if (!dir.exists()){
                    dir.mkdirs();
                }
                FFmpegBuilder builder = new FFmpegBuilder()
                    .addExtraArgs("-y")
                    .setVideoFilter("pad=ceil(iw/2)*2:ceil(ih/2)*2")
                    .addExtraArgs("-framerate", "1") //set framerate
                    .addInput("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(VideoPage.cameraURL)+"\\%d.jpg")
                    // .addExtraArgs("-r", "1") // test if this flag works to change framerate
                    .addOutput("output.mp4")
                    .done(); //TODO supress errors 
                    //TODO test if ffmpeg needs to be in path
                for(int i=0; i<numberOfFiles; i++) {
                    //builder.addInput()
                    System.err.println(i+".jpg");
                }
                FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
                executor.createJob(builder).run();
            } catch (IOException e) { e.printStackTrace(); }
        } else { System.out.println("> NO PHOTOS IN THE GIVEN DIRECTORY <"); }



        // if(numberOfFiles != 0) {
        //     //first command
        //     int counter = 0;
        //     String cmdPre = "ffmpeg -framerate 1 ";
        //     String[] inputsArr = new String[2*numberOfFiles]; // -i file
        //     String cmdPos = " -y -vf pad=ceil(iw/2)*2:ceil(ih/2)*2 -c:v libx264 -r 30 -pix_fmt yuv420p C:\\ProgramData\\dgtcd\\assets\\videos\\video.mp4";
        //     //command builder
        //     for(int i=0; i<inputsArr.length; i++) {
        //         if (i%2 == 1) { 
        //             inputsArr[i] = "C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(VideoPage.cameraURL)+"\\"+counter+".jpg"; 
        //             counter++; //evens
        //         } else if (i%2 == 0) { inputsArr[i] = "-i"; } //odds
        //         else {System.out.print("Fuck!");} //TODO remove
        //     }
        //     String inputs = String.join(" ", inputsArr);
        //     String cmdFull = cmdPre + inputs + cmdPos;
        //     System.out.println("🟧 "+cmdFull);
        //     // run the ffmpeg commands
        //     try {
        //         Runtime.getRuntime().exec(cmdFull);
        //     } catch (IOException e) {e.printStackTrace();}
        // } else { System.out.println("> NO PHOTOS IN THE GIVEN DIRECTORY <"); }
    }
}

// let images = new Array(fileNumber);
// for (let i=0; i < images.length; i++) {
//   images[i] = { path: path+`\\img\\${i}.jpg`, caption: `image ${i+1} out of ${fileNumber}` };
// }

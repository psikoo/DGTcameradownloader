package com.dgtcd.video;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.dgtcd.utility.DirEncode;

import net.bramp.ffmpeg.*;
import net.bramp.ffmpeg.builder.*;
import net.bramp.ffmpeg.job.FFmpegJob;

public class VideoMaker implements Runnable {
    public static ScheduledExecutorService statusChecker;
    public static FFmpegJob job;
    @Override
    public void run() {
        System.out.println(" <-- Video Maker Started: "+VideoPage.cameraURL+" -->");
        int numberOfFiles = 0;
        try { numberOfFiles = new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(VideoPage.cameraURL)).list().length; } catch(NullPointerException ignore) {}
        
        if(numberOfFiles != 0) {
            System.out.println("> Number of files detected: "+numberOfFiles);
            try {
                FFmpeg ffmpeg = new FFmpeg(System.getProperty("user.dir")+"\\lib\\ffmpeg\\ffmpeg.exe");
                File dir = new File("C:\\ProgramData\\dgtcd\\assets\\videos\\"+DirEncode.dirEncode(VideoPage.cameraURL));
                if (!dir.exists()){
                    dir.mkdirs();
                }
                FFmpegBuilder builder = new FFmpegBuilder()
                    .addExtraArgs("-y")
                    .setVideoFilter("pad=ceil(iw/2)*2:ceil(ih/2)*2")
                    .addExtraArgs("-loglevel", "panic", "-framerate", "1") //set framerate
                    .addInput("C:\\ProgramData\\dgtcd\\assets\\photos\\"+DirEncode.dirEncode(VideoPage.cameraURL)+"\\%d.jpg")
                    //.addExtraArgs("-r", "1") // test if this flag works to change framerate
                    .addOutput("output.mp4")
                    .done();
                System.out.println("> Executing ffmpeg command, this could take a while (do not close the app)");
                FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
                job = executor.createJob(builder);
                job.run();
                //Start Status checker
                statusChecker = Executors.newScheduledThreadPool(1);
                Runnable statusCheckerRunnable = new VideoStatusChecker();
                long timeDelay = 5;
                statusChecker.scheduleAtFixedRate(statusCheckerRunnable, 1,timeDelay, TimeUnit.SECONDS);    
            } catch (IOException e) { e.printStackTrace(); }
        } else { System.out.println("> NO PHOTOS IN THE GIVEN DIRECTORY <"); }
    }
}

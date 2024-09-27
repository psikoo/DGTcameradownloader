package com.dgtcd.video;

import net.bramp.ffmpeg.job.FFmpegJob.State;;

public class VideoStatusChecker implements Runnable {
    @Override
    public void run() {
        if((VideoMaker.job.getState()!=State.FINISHED) && (VideoMaker.job.getState()!=State.FAILED)) {
            System.err.println("> Command Status: "+VideoMaker.job.getState());
        } else {
            System.err.println("> Command Status: "+VideoMaker.job.getState());
            shutdown();
        }
    }
    public void shutdown() { 
        VideoMaker.statusChecker.shutdown();
    }
}

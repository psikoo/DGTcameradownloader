package com.dgtcd.photo;

import java.io.File;

import com.dgtcd.utility.CalculateMD5;

public class PhotoDeleteDuplicates {
    public static void deleteAndRenamePhotos(String cameraID) {
        deletePhotos(cameraID);
        renamePhotos(cameraID);
    }

    public static void deletePhotos(String cameraID) {
        int numberOfFiles = 0;
        try { numberOfFiles = new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID).list().length; } catch(NullPointerException ignore) {}
        for(int i=1; i<numberOfFiles; i++) {
            String oldMD5 = CalculateMD5.getMD5("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+(i-1)+".jpg");
            String currentMD5 = CalculateMD5.getMD5("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+i+".jpg");
            if(oldMD5.equals(currentMD5)) {
                System.out.println("> Deleting: C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+(i-1)+".jpg");
                new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+(i-1)+".jpg").delete(); 
            }
        }
    }

    public static void renamePhotos(String cameraID) {
        int counter = 0;
        for(int i=0; i<10000; i++) {
            if((new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+i+".jpg")).exists()) {
                File oldName = new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+i+".jpg");
                File newName = new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+counter+".jpg");
                oldName.renameTo(newName);
                counter++;
            }
        }
    }
}

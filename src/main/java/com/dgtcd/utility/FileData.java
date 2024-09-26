package com.dgtcd.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import javax.imageio.ImageIO;

public class FileData {
    public static String getCreationTime(String filePath) {
        try {
            //Get file attributes
            Path finalFilePath = Path.of(filePath);
            BasicFileAttributes attr = Files.readAttributes(finalFilePath, BasicFileAttributes.class);
            //Format date string
            String fullDate = attr.creationTime()+"";
            String formattedDate = fullDate.substring(0, fullDate.lastIndexOf("T"))+" - "+fullDate.substring(fullDate.lastIndexOf("T")+1, fullDate.lastIndexOf("."));
            return formattedDate;
        } catch (IOException e) { return ""; }
    }

    public static int getHeight(String filePath) {
        try {
            //Get file attributes
            BufferedImage file = ImageIO.read(new File(filePath));
            int height = file.getHeight();
            return height;
        } catch (IOException e) { return 1000; }
    }
    public static int getWidth(String filePath) {
        try {
            //Get file attributes
            BufferedImage file = ImageIO.read(new File(filePath));
            int width = file.getWidth();
            return width;
        } catch (IOException e) { return 1000; }
    }
}

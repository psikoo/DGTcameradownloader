package com.dgtcd.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalculateMD5 {
    public static String getMD5(String filepath) {
        try {
            FileInputStream fileStream = new FileInputStream(filepath);
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            byte[] buffer = new byte[1024];

            int n = 0;  
            while ((n = fileStream.read(buffer)) != -1) {  
                md.update(buffer, 0, n);  
            }
            byte[] digest = md.digest(); 
            StringBuffer sb = new StringBuffer();  
            for (byte b : digest) {  
                sb.append(String.format("%02x", b & 0xff));  
            }

            fileStream.close();
            return sb.toString();  
        } 
        catch (FileNotFoundException e) { e.printStackTrace(); return ""; }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); return ""; }
        catch (IOException e) { e.printStackTrace(); return ""; }
    }
}

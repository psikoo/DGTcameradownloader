package com.dgtcd.utility;

public class DirEncode {
    public static String dirEncode(String string) {
        String regexPattern = "[\\/:?<>|]";
        String outputString = string.replaceAll(regexPattern, "_");
        return outputString;
    }
}

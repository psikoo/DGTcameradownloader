package com.dgtcd;

public class Test {
    public static void main(String[] args)  { //Quick testing for code snippets
        System.out.println("> Test.java");
        String test = "https://infocar.dgt.es/etraffic/data/camaras/598.jpg";
        String regexPattern = "[\\/:?<>|]";
        String outputString = test.replaceAll(regexPattern, "_");
        System.out.println(outputString);
    }
}

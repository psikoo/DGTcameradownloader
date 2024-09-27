package com.dgtcd;
import com.dgtcd.utility.setup.CustomHCTheme;
import com.dgtcd.utility.setup.Install;

import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) { // TODO fix .gzip not compressing correctly
        // TODO add controlls to go tot the next foto in IMGpopup
        new Install(); // Create directories "C:\ProgramData\dgtcd"
        CustomHCTheme.setup(); // Set look and feel "/resources/assets/themes/CustomHCTheme.theme.json"
        //2 launch parameters
        if(args.length == 4) {
            //camera delay mode
            if(args[0].equals("-camera")) {
                if(args[2].equals("-delay")) {
                    if(StringUtils.isNumeric(args[3])) { 
                        new App(3, args[1], args[3]); 
                    } else { System.out.println("Invalid launch parameters"); } 
                } 
                else { System.out.println("Invalid launch parameters"); }
            } 
            //delay camera mode
            else if(args[0].equals("-delay")) {
                if(args[2].equals("-camera")) {
                    if(StringUtils.isNumeric(args[1])) { 
                        new App(4, args[1], args[3]); 
                    } else { System.out.println("Invalid launch parameters"); } 
                } else { System.out.println("Invalid launch parameters"); }
            } else { System.out.println("Invalid launch parameters"); }
        } 
        //1 launch parameter
        else if(args.length == 2) {
            //camera mode
            if(args[0].equals("-camera")) {
                new App(0, args[1]);
            }
            //delay mode
            else if(args[0].equals("-delay")) {
                if(StringUtils.isNumeric(args[1])) {new App(1, args[1]);}
                else { System.out.println("Invalid launch parameters"); } 
            } else { System.out.println("Invalid launch parameters"); }
        } 
        //0 launch parameters
        else if(args.length == 0) {
            new App(); // Start swing gui program
        } else { System.out.println("Invalid launch parameters"); }
    }
}

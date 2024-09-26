package com.dgtcd.utility.setup;

import java.io.IOException;
import javax.swing.UIManager;
import com.formdev.flatlaf.IntelliJTheme;

public class CustomHCTheme extends IntelliJTheme.ThemeLaf {
	public static final String NAME = "CustomHC";

	public static boolean setup() {
		//Custom UIManager theme
		UIManager.put( "Component.focusWidth", 1 );
        UIManager.put( "Component.innerFocusWidth", 0 );
        UIManager.put( "Button.focusWidth", 1 );
        UIManager.put( "Button.innerFocusWidth", 0 );
		//Theme
		try {
			return setup( new CustomHCTheme() );
		} catch( RuntimeException ex ) {
			return false;
		}
	}

	public static void installLafInfo() { //install theme to LafInfo
		installLafInfo( NAME, CustomHCTheme.class );
	}

	public CustomHCTheme() { //load theme from .json file
		super( loadTheme( "CustomHCTheme.theme.json" ) );
	}

	@Override
	public String getName() {
		return NAME;
	}

	//Load theme from .json file
	public static IntelliJTheme loadTheme( String name ) {
		try {
			return new IntelliJTheme(CustomHCTheme.class.getResourceAsStream("/assets/themes/" + name));
		} catch( IOException e ) {
			String msg = "FlatLaf: Failed to load IntelliJ theme '" + name + "'";
			System.out.println(msg);
			throw new RuntimeException( msg, e );
		}
	}
}

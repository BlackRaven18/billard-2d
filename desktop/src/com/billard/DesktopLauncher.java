package com.billard;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.billard.Main;
import utils.Constants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Billard-2D");
		config.setWindowedMode(Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
		new Lwjgl3Application(new Main(), config);
	}
}

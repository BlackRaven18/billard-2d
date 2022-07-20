package com.billard;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(Application.APP_TITLE +  " " + Application.APP_VERSION);
		config.setWindowedMode(Application.WORLD_PIXEL_WIDTH, Application.WORLD_PIXEL_HEIGHT);
		config.setForegroundFPS(Application.APP_FPS);
		config.setIdleFPS(Application.APP_FPS);
		new Lwjgl3Application(new Application(), config);
	}
}

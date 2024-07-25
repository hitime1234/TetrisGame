package com.mygdx.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tetris game";
		config.width = 800;
		config.height = 480;
		config.samples = 2;
		config.vSyncEnabled = true;
		config.backgroundFPS = 61;
		config.foregroundFPS = 61;
		new LwjglApplication(new MyGdxGame(), config);
	}
}

package model;

import javafx.scene.media.AudioClip;

public class Sound {

	public static AudioClip enterSound = new AudioClip(ClassLoader.getSystemResource("audio/enterButton.wav").toString());
	public static AudioClip pressSound = new AudioClip(ClassLoader.getSystemResource("audio/pressButton.wav").toString());
	public static AudioClip punchSound = new AudioClip(ClassLoader.getSystemResource("audio/punch.wav").toString());
	public static AudioClip explosionSound = new AudioClip(ClassLoader.getSystemResource("audio/explosion.wav").toString());
	
}

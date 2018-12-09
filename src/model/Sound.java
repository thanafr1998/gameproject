package model;

import javafx.scene.media.AudioClip;

public class Sound {

	public static AudioClip enterSound = new AudioClip(ClassLoader.getSystemResource("audio/enterButton.wav").toString());
	public static AudioClip pressSound = new AudioClip(ClassLoader.getSystemResource("audio/pressButton.wav").toString());
	
	public static AudioClip punchSound = new AudioClip(ClassLoader.getSystemResource("audio/punch.wav").toString());
	public static AudioClip kickSound = new AudioClip(ClassLoader.getSystemResource("audio/kick.wav").toString());
	public static AudioClip blockSound = new AudioClip(ClassLoader.getSystemResource("audio/block.wav").toString());
	public static AudioClip missSound = new AudioClip(ClassLoader.getSystemResource("audio/miss.wav").toString());
	
	public static AudioClip explosionSound = new AudioClip(ClassLoader.getSystemResource("audio/explosion.wav").toString());
	
}

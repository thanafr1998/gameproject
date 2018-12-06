package model;

import javafx.scene.media.AudioClip;

public class Sound {

	public static AudioClip enterSound = new AudioClip(ClassLoader.getSystemResource("audio/enterButton.wav").toString());
	public static AudioClip pressSound = new AudioClip(ClassLoader.getSystemResource("audio/pressButton.wav").toString());

	
}

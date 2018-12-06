package model;

import javafx.scene.image.Image;
import view.GameViewManager;

public class Character {
	public static final int WIDTH = 60;
	public static final int HEIGHT = 100;
	public static final int ATTACK_RANGE = 70;
	public static final int ATTACK_COOLDOWN = 3;
	public static final int[] FLOOR_LEVEL = {GameViewManager.height - 120,GameViewManager.height - 300,GameViewManager.height - 500};
	
	public static final Image SOLID_GREY = new Image(ClassLoader.getSystemResource("image/SOLID_GREY.png").toString());
	
	public static final Image IDLE = new Image(ClassLoader.getSystemResource("image/IDLE.png").toString());
	public static final Image JUMP = new Image(ClassLoader.getSystemResource("image/JUMP.png").toString());
	public static final Image CROUCH = new Image(ClassLoader.getSystemResource("image/CROUCH.png").toString());
	public static final Image BLOCK = new Image(ClassLoader.getSystemResource("image/BLOCK.png").toString());
	public static final Image walkR1 = new Image(ClassLoader.getSystemResource("image/WALK_RIGHT_1.png").toString());
	public static final Image walkR2 = new Image(ClassLoader.getSystemResource("image/WALK_RIGHT_2.png").toString());
	public static final Image walkR3 = new Image(ClassLoader.getSystemResource("image/WALK_RIGHT_3.png").toString());
	public static final Image walkL1 = new Image(ClassLoader.getSystemResource("image/WALK_LEFT_1.png").toString());
	public static final Image walkL2 = new Image(ClassLoader.getSystemResource("image/WALK_LEFT_2.png").toString());
	public static final Image walkL3 = new Image(ClassLoader.getSystemResource("image/WALK_LEFT_3.png").toString());
	public static final Image C_punchL1 = new Image(ClassLoader.getSystemResource("image/CROUCH_PUNCH_LEFT_1.png").toString());
	public static final Image C_punchL2 = new Image(ClassLoader.getSystemResource("image/CROUCH_PUNCH_LEFT_2.png").toString());
	public static final Image C_punchR1 = new Image(ClassLoader.getSystemResource("image/CROUCH_PUNCH_RIGHT_1.png").toString());
	public static final Image C_punchR2 = new Image(ClassLoader.getSystemResource("image/CROUCH_PUNCH_RIGHT_2.png").toString());
	public static final Image punchL1 = new Image(ClassLoader.getSystemResource("image/PUNCH_LEFT_1.png").toString());
	public static final Image punchL2 = new Image(ClassLoader.getSystemResource("image/PUNCH_LEFT_2.png").toString());
	public static final Image punchR1 = new Image(ClassLoader.getSystemResource("image/PUNCH_RIGHT_1.png").toString());
	public static final Image punchR2 = new Image(ClassLoader.getSystemResource("image/PUNCH_RIGHT_2.png").toString());
	public static final Image kickL1 = new Image(ClassLoader.getSystemResource("image/KICK_LEFT_1.png").toString());
	public static final Image kickL2 = new Image(ClassLoader.getSystemResource("image/KICK_LEFT_2.png").toString());
	public static final Image kickR1 = new Image(ClassLoader.getSystemResource("image/KICK_RIGHT_1.png").toString());
	public static final Image kickR2 = new Image(ClassLoader.getSystemResource("image/KICK_RIGHT_2.png").toString());
	
	public static final Image RED_IDLE = new Image(ClassLoader.getSystemResource("image/RED_IDLE.png").toString());
	public static final Image RED_BLOCK = new Image(ClassLoader.getSystemResource("image/RED_BLOCK.png").toString());
	public static final Image RED_walkR1 = new Image(ClassLoader.getSystemResource("image/RED_WALK_RIGHT_1.png").toString());
	public static final Image RED_walkR2 = new Image(ClassLoader.getSystemResource("image/RED_WALK_RIGHT_2.png").toString());
	public static final Image RED_walkR3 = new Image(ClassLoader.getSystemResource("image/RED_WALK_RIGHT_3.png").toString());
	public static final Image RED_walkL1 = new Image(ClassLoader.getSystemResource("image/RED_WALK_LEFT_1.png").toString());
	public static final Image RED_walkL2 = new Image(ClassLoader.getSystemResource("image/RED_WALK_LEFT_2.png").toString());
	public static final Image RED_walkL3 = new Image(ClassLoader.getSystemResource("image/RED_WALK_LEFT_3.png").toString());
	public static final Image RED_punchL1 = new Image(ClassLoader.getSystemResource("image/RED_PUNCH_LEFT_1.png").toString());
	public static final Image RED_punchL2 = new Image(ClassLoader.getSystemResource("image/RED_PUNCH_LEFT_2.png").toString());
	public static final Image RED_punchR1 = new Image(ClassLoader.getSystemResource("image/RED_PUNCH_RIGHT_1.png").toString());
	public static final Image RED_punchR2 = new Image(ClassLoader.getSystemResource("image/RED_PUNCH_RIGHT_2.png").toString());
	public static final Image RED_kickL1 = new Image(ClassLoader.getSystemResource("image/RED_KICK_LEFT_1.png").toString());
	public static final Image RED_kickL2 = new Image(ClassLoader.getSystemResource("image/RED_KICK_LEFT_2.png").toString());
	public static final Image RED_kickR1 = new Image(ClassLoader.getSystemResource("image/RED_KICK_RIGHT_1.png").toString());
	public static final Image RED_kickR2 = new Image(ClassLoader.getSystemResource("image/RED_KICK_RIGHT_2.png").toString());
	
	public static final Image BLUE_IDLE = new Image(ClassLoader.getSystemResource("image/BLUE_IDLE.png").toString());
	public static final Image BLUE_BLOCK = new Image(ClassLoader.getSystemResource("image/BLUE_BLOCK.png").toString());
	public static final Image BLUE_walkR1 = new Image(ClassLoader.getSystemResource("image/BLUE_WALK_RIGHT_1.png").toString());
	public static final Image BLUE_walkR2 = new Image(ClassLoader.getSystemResource("image/BLUE_WALK_RIGHT_2.png").toString());
	public static final Image BLUE_walkR3 = new Image(ClassLoader.getSystemResource("image/BLUE_WALK_RIGHT_3.png").toString());
	public static final Image BLUE_walkL1 = new Image(ClassLoader.getSystemResource("image/BLUE_WALK_LEFT_1.png").toString());
	public static final Image BLUE_walkL2 = new Image(ClassLoader.getSystemResource("image/BLUE_WALK_LEFT_2.png").toString());
	public static final Image BLUE_walkL3 = new Image(ClassLoader.getSystemResource("image/BLUE_WALK_LEFT_3.png").toString());
	public static final Image BLUE_punchL1 = new Image(ClassLoader.getSystemResource("image/BLUE_PUNCH_LEFT_1.png").toString());
	public static final Image BLUE_punchL2 = new Image(ClassLoader.getSystemResource("image/BLUE_PUNCH_LEFT_2.png").toString());
	public static final Image BLUE_punchR1 = new Image(ClassLoader.getSystemResource("image/BLUE_PUNCH_RIGHT_1.png").toString());
	public static final Image BLUE_punchR2 = new Image(ClassLoader.getSystemResource("image/BLUE_PUNCH_RIGHT_2.png").toString());
	public static final Image BLUE_kickL1 = new Image(ClassLoader.getSystemResource("image/BLUE_KICK_LEFT_1.png").toString());
	public static final Image BLUE_kickL2 = new Image(ClassLoader.getSystemResource("image/BLUE_KICK_LEFT_2.png").toString());
	public static final Image BLUE_kickR1 = new Image(ClassLoader.getSystemResource("image/BLUE_KICK_RIGHT_1.png").toString());
	public static final Image BLUE_kickR2 = new Image(ClassLoader.getSystemResource("image/BLUE_KICK_RIGHT_2.png").toString());
	
	public static final Image GREY_IDLE = new Image(ClassLoader.getSystemResource("image/GREY_IDLE.png").toString());
	public static final Image GREY_BLOCK = new Image(ClassLoader.getSystemResource("image/GREY_BLOCK.png").toString());
	public static final Image GREY_walkR1 = new Image(ClassLoader.getSystemResource("image/GREY_WALK_RIGHT_1.png").toString());
	public static final Image GREY_walkR2 = new Image(ClassLoader.getSystemResource("image/GREY_WALK_RIGHT_2.png").toString());
	public static final Image GREY_walkR3 = new Image(ClassLoader.getSystemResource("image/GREY_WALK_RIGHT_3.png").toString());
	public static final Image GREY_walkL1 = new Image(ClassLoader.getSystemResource("image/GREY_WALK_LEFT_1.png").toString());
	public static final Image GREY_walkL2 = new Image(ClassLoader.getSystemResource("image/GREY_WALK_LEFT_2.png").toString());
	public static final Image GREY_walkL3 = new Image(ClassLoader.getSystemResource("image/GREY_WALK_LEFT_3.png").toString());
	public static final Image GREY_punchL1 = new Image(ClassLoader.getSystemResource("image/GREY_PUNCH_LEFT_1.png").toString());
	public static final Image GREY_punchL2 = new Image(ClassLoader.getSystemResource("image/GREY_PUNCH_LEFT_2.png").toString());
	public static final Image GREY_punchR1 = new Image(ClassLoader.getSystemResource("image/GREY_PUNCH_RIGHT_1.png").toString());
	public static final Image GREY_punchR2 = new Image(ClassLoader.getSystemResource("image/GREY_PUNCH_RIGHT_2.png").toString());
	public static final Image GREY_kickL1 = new Image(ClassLoader.getSystemResource("image/GREY_KICK_LEFT_1.png").toString());
	public static final Image GREY_kickL2 = new Image(ClassLoader.getSystemResource("image/GREY_KICK_LEFT_2.png").toString());
	public static final Image GREY_kickR1 = new Image(ClassLoader.getSystemResource("image/GREY_KICK_RIGHT_1.png").toString());
	public static final Image GREY_kickR2 = new Image(ClassLoader.getSystemResource("image/GREY_KICK_RIGHT_2.png").toString());
}

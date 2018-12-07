package buff;

import javafx.scene.canvas.GraphicsContext;
import model.StickMan;
import model.Character;

public class HealBuff extends Buffs{
	
	public HealBuff() {
		super();
	}

	@Override
	public boolean pickUpBy(StickMan sm, double pickTime) {
		if(sm.getY() + 75 != super.getY()) return false;
		if( !(super.getX() > sm.getX() + Character.WIDTH || super.getX() + 25 < sm.getX()) ) {
			sm.setHp(sm.getHp() + StickMan.MAX_HP/5);
			return true;
		}
		return false;
	}


	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(Character.GreenCross, super.getX(), super.getY());
		
	}

}

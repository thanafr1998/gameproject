package buff;

import javafx.scene.canvas.GraphicsContext;
import model.StickMan;
import model.Character;

public class DefenceBuff extends Buffs {
	public DefenceBuff() {
		super();
	}

	@Override
	public boolean pickUpBy(StickMan sm, double pickTime) {
		if(sm.getY() + 75 != super.getY()) return false;
		if( !(super.getX() > sm.getX() + Character.WIDTH || super.getX() + 25 < sm.getX()) ) {
			sm.increaseDefence();
			sm.setBuffedDefence(true);
			sm.setDefenceBuffExpire(pickTime + 5);
			return true;
		}
		return false;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(Character.BlueShield, super.getX(), super.getY());
		
	}
}

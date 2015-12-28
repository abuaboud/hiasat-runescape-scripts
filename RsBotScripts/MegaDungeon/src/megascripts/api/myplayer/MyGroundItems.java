package megascripts.api.myplayer;

import java.awt.Rectangle;


import megascripts.api.Calc;
import megascripts.api.ulits;

import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class MyGroundItems {

	public static boolean isOnScreen(GroundItem p){
		Rectangle ScreenArea = new Rectangle(0, 52, 521, 259);
		return ScreenArea.contains(p.getCentralPoint());
	}
	public static boolean There(int e){
		GroundItem n = GroundItems.getNearest(e);
		return n !=null && Calc.Reach(n);
	}
}

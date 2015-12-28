package shadowscripts.grotworms.api;

import java.awt.Point;
import java.awt.Rectangle;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

public class calc {

	public static int distanceBetween(Tile t , Tile r){
		int x = t.getX(),x1 = r.getX(),y = t.getY() ,y1 = r.getY();
		return (int)Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y - y1, 2));
	}
	public static int distanceTo(Tile a){
		return distanceBetween(Players.getLocal().getLocation(), a);
	}
	public static boolean isPointOnScreen(Point p){
		return new Rectangle(0, 52, 519, 288).contains(p);
	}
}

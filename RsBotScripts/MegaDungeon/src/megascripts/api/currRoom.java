package megascripts.api;

import java.awt.Point;
import java.awt.Rectangle;


import megascripts.api.myplayer.MyPlayer;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;


public class currRoom {

	public static Area getArea() {
		return Flood.getArea();
	}

	public static boolean contains(Tile e) {
		if (Calc.Reach(e)) {
			return true;
		}

		return false;
	}

	public static boolean isOnScreen(Point p) {
		Rectangle ScreenArea = new Rectangle(0, 52, 521, 259);
		return ScreenArea.contains(p);
	}

	public static boolean MatchTile(Tile e) {
		int px = MyPlayer.getLocation().getX();
		int py = MyPlayer.getLocation().getY();
		int ey = e.getY();
		int ex = e.getX();
		return px == ex && py == ey;
	}

	public static boolean MatchTile(Tile e, Tile x) {
		int px = x.getX();
		int py = x.getY();
		int ey = e.getY();
		int ex = e.getX();
		return px == ex && py == ey;
	}

	public static Tile getCentralTile() {
		return Flood.getArea().getCentralTile();
	}

	public static Area NextRoomArea() {
		Tile t1 = Flood.getArea().getBoundingTiles()[0];
		Tile t2 = Flood.getArea().getBoundingTiles()[1];
		Tile t3 = Flood.getArea().getBoundingTiles()[2];
		Tile t4 = Flood.getArea().getBoundingTiles()[3];
		int Orientation = Math.round(Players.getLocal().getOrientation() / 90);
		Tile n1, n2, n3, n4;

		if (Orientation == 0) {
			n1 = new Tile(t1.getX() + 15, t1.getY(), 0);
			n2 = new Tile(t1.getX() + 15, t1.getY(), 0);
			n3 = new Tile(t1.getX(), t1.getY() + 1, 0);
			n4 = new Tile(t1.getX(), t1.getY() + 1, 0);
		} else if (Orientation == 1) {
			n1 = new Tile(t1.getX(), t1.getY() + 15, 0);
			n2 = new Tile(t1.getX(), t1.getY() + 15, 0);
			n3 = new Tile(t1.getX() + 1, t1.getY(), 0);
			n4 = new Tile(t1.getX() + 1, t1.getY(), 0);
		} else if (Orientation == 2) {
			n1 = new Tile(t1.getX() - 15, t1.getY(), 0);
			n2 = new Tile(t1.getX() - 15, t1.getY(), 0);
			n3 = new Tile(t1.getX(), t1.getY() - 1, 0);
			n4 = new Tile(t1.getX(), t1.getY() - 1, 0);
		} else if (Orientation == 3) {
			n1 = new Tile(t1.getX(), t1.getY() - 15, 0);
			n2 = new Tile(t1.getX(), t1.getY() - 15, 0);
			n3 = new Tile(t1.getX() - 1, t1.getY(), 0);
			n4 = new Tile(t1.getX() - 1, t1.getY(), 0);
		} else {
			n1 = t1;
			n2 = t2;
			n3 = t3;
			n4 = t4;
		}
		return new Area(new Tile[] { n1, n2, n3, n4 });
	}

	public static Area getArea(final Tile[] flood) {
		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MAX_VALUE;
		for (final Tile tile : flood) {
			if (tile.getX() < minX)
				minX = tile.getX();
			if (tile.getY() < minY)
				minY = tile.getY();
			if (tile.getX() > maxX)
				maxX = tile.getX();
			if (tile.getY() > maxY)
				maxY = tile.getY();
		}
		return new Area(new Tile(minX, minY, flood[0].getPlane()), new Tile(
				maxX, maxX, flood[0].getPlane()));
	}
}

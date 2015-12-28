package megascripts.dungeon.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


import megascripts.api.Calc;
import megascripts.api.Flood;
import megascripts.api.currRoom;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.LocalPath;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.map.LocalPath.Vertex;
import org.powerbot.game.api.wrappers.map.Path.TraversalOption;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.LogHandler;

public class ToxinMaze extends Puzzle{

	public static int[] CHESTS = { 49345, 49346, 49347,54407 };
	public static int[] Barrels = { 49344, 49341, 49353 };
	public static Tile[] Path = null;

	public static ArrayList<Tile> PASSED = new ArrayList<Tile>();
	public static Tile Solve = null;
	public static boolean MAZE_ACTIVE = false;

    @Override
	public String getName() {
		return "Maze";
	}
    @Override
	public String getAuthor() {
		return "Ramy";
	}
    @Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}
    @Override
	public boolean isValid() {
		SceneObject n = SceneEntities.getNearest(CHESTS);
		SceneObject lever = SceneEntities
				.getNearest(49351, 49352, 49353, 54409);
		if (n == null || lever == null) {
			return false;
		}
		return ulits.tileinroom(n.getLocation()) || ulits.tileinroom(lever.getLocation());
	}
    @Override
	public boolean isSolved() {
		return !isValid();
	}
    @Override
	public void solve() {
    	Constants.LeaveDungeon = true;
    	MAZE_ACTIVE = true;
		ShadowDungeon.setStatus(getStatus());

		 int[] centerPath = null, currentPath = null, edgePath = null;
		Tile[] barriers;
		int mX = 1, mY = 1;
		boolean y = false;
		SceneObject chest = SceneEntities.getNearest(CHESTS);
		SceneObject lever = SceneEntities
				.getNearest(49351, 49352, 49353, 54409);
		if (chest == null || lever == null) {
			return;
		}
		Tile cTile = chest.getLocation(), sTile = lever.getLocation(), hT = null;
		if (cTile.getX() - sTile.getX() < -3) {
			hT = Flood.getArea().getNearest(new Tile(0, 0, 0));
		} else if (cTile.getX() - sTile.getX() > 4) {
			mX = -1;
			mY = -1;
			hT = Flood.getArea().getNearest(
					new Tile(20000, 20000, Game.getPlane()));
		} else if (cTile.getY() - sTile.getY() < -4) {
			mX = -1;
			hT = Flood.getArea()
					.getNearest(new Tile(20000, 0, Game.getPlane()));
			y = true;
		} else if (cTile.getY() - sTile.getY() > 4) {
			mY = -1;
			hT = Flood.getArea()
					.getNearest(new Tile(0, 20000, Game.getPlane()));
			y = true;
		}
		if (hT == null)
			return;
		int hX = hT.getX(), hY = hT.getY();
		Tile[] barriersNS = new Tile[] {
				new Tile(hX + 14 * mX, hY + 5 * mY, Game.getPlane()),
				new Tile(hX + 14 * mX, hY + 7 * mY, Game.getPlane()),
				new Tile(hX + 14 * mX, hY + 12 * mY, Game.getPlane()),
				new Tile(hX + 13 * mX, hY + 8 * mY, Game.getPlane()),
				new Tile(hX + 13 * mX, hY + 6 * mY, Game.getPlane()),
				new Tile(hX + 11 * mX, hY + 2 * mY, Game.getPlane()),
				new Tile(hX + 11 * mX, hY + 7 * mY, Game.getPlane()),
				new Tile(hX + 10 * mX, hY + 3 * mY, Game.getPlane()),
				new Tile(hX + 10 * mX, hY + 8 * mY, Game.getPlane()),
				new Tile(hX + 10 * mX, hY + 13 * mY, Game.getPlane()),
				new Tile(hX + 8 * mX, hY + 1 * mY, Game.getPlane()),
				new Tile(hX + 8 * mX, hY + 4 * mY, Game.getPlane()),
				new Tile(hX + 8 * mX, hY + 10 * mY, Game.getPlane()),
				new Tile(hX + 8 * mX, hY + 14 * mY, Game.getPlane()),
				new Tile(hX + 7 * mX, hY + 5 * mY, Game.getPlane()),
				new Tile(hX + 6 * mX, hY + 3 * mY, Game.getPlane()),
				new Tile(hX + 6 * mX, hY + 11 * mY, Game.getPlane()),
				new Tile(hX + 5 * mX, hY + 2 * mY, Game.getPlane()),
				new Tile(hX + 5 * mX, hY + 7 * mY, Game.getPlane()),
				new Tile(hX + 5 * mX, hY + 12 * mY, Game.getPlane()),
				new Tile(hX + 4 * mX, hY + 6 * mY, Game.getPlane()),
				new Tile(hX + 4 * mX, hY + 9 * mY, Game.getPlane()),
				new Tile(hX + 4 * mX, hY + 13 * mY, Game.getPlane()),
				new Tile(hX + 4 * mX, hY + 14 * mY, Game.getPlane()),
				new Tile(hX + 3 * mX, hY + 9 * mY, Game.getPlane()),
				new Tile(hX + 2 * mX, hY + 7 * mY, Game.getPlane()),
				new Tile(hX + 1 * mX, hY + 3 * mY, Game.getPlane()),
				new Tile(hX + 1 * mX, hY + 9 * mY, Game.getPlane()),
				new Tile(hX + 1 * mX, hY + 11 * mY, Game.getPlane()) };
		Tile[] barriersEW = new Tile[] {
				new Tile(hX + 5 * mX, hY + 14 * mY, Game.getPlane()),
				new Tile(hX + 7 * mX, hY + 14 * mY, Game.getPlane()),
				new Tile(hX + 12 * mX, hY + 14 * mY, Game.getPlane()),
				new Tile(hX + 8 * mX, hY + 13 * mY, Game.getPlane()),
				new Tile(hX + 6 * mX, hY + 12 * mY, Game.getPlane()),
				new Tile(hX + 2 * mX, hY + 11 * mY, Game.getPlane()),
				new Tile(hX + 7 * mX, hY + 11 * mY, Game.getPlane()),
				new Tile(hX + 3 * mX, hY + 10 * mY, Game.getPlane()),
				new Tile(hX + 8 * mX, hY + 10 * mY, Game.getPlane()),
				new Tile(hX + 13 * mX, hY + 10 * mY, Game.getPlane()),
				new Tile(hX + 1 * mX, hY + 8 * mY, Game.getPlane()),
				new Tile(hX + 4 * mX, hY + 8 * mY, Game.getPlane()),
				new Tile(hX + 10 * mX, hY + 8 * mY, Game.getPlane()),
				new Tile(hX + 14 * mX, hY + 8 * mY, Game.getPlane()),
				new Tile(hX + 5 * mX, hY + 7 * mY, Game.getPlane()),
				new Tile(hX + 3 * mX, hY + 6 * mY, Game.getPlane()),
				new Tile(hX + 11 * mX, hY + 6 * mY, Game.getPlane()),
				new Tile(hX + 2 * mX, hY + 5 * mY, Game.getPlane()),
				new Tile(hX + 7 * mX, hY + 5 * mY, Game.getPlane()),
				new Tile(hX + 12 * mX, hY + 5 * mY, Game.getPlane()),
				new Tile(hX + 6 * mX, hY + 4 * mY, Game.getPlane()),
				new Tile(hX + 9 * mX, hY + 4 * mY, Game.getPlane()),
				new Tile(hX + 13 * mX, hY + 4 * mY, Game.getPlane()),
				new Tile(hX + 14 * mX, hY + 4 * mY, Game.getPlane()),
				new Tile(hX + 9 * mX, hY + 3 * mY, Game.getPlane()),
				new Tile(hX + 7 * mX, hY + 2 * mY, Game.getPlane()),
				new Tile(hX + 3 * mX, hY + 1 * mY, Game.getPlane()),
				new Tile(hX + 9 * mX, hY + 1 * mY, Game.getPlane()),
				new Tile(hX + 11 * mX, hY + 1 * mY, Game.getPlane()) };

		SceneObject o1, o2, o3, o4, o5;

		if (y) {
			barriers = barriersNS;
			o1 = SceneEntities.getAt(new Tile(hX + 4 * mX, hY + 5 * mY, Game
					.getPlane()));
			o2 = SceneEntities.getAt(new Tile(hX + 9 * mX, hY + 2 * mY, Game
					.getPlane()));
			o3 = SceneEntities.getAt(new Tile(hX + 5 * mX, hY + 2 * mY, Game
					.getPlane()));
			o4 = SceneEntities.getAt(new Tile(hX + 3 * mX, hY + 5 * mY, Game
					.getPlane()));
			o5 = SceneEntities.getAt(new Tile(hX + 3 * mX, hY + 1 * mY, Game
					.getPlane()));
		} else {
			barriers = barriersEW;
			o1 = SceneEntities.getAt(new Tile(hX + 5 * mX, hY + 4 * mY, Game
					.getPlane()));
			o2 = SceneEntities.getAt(new Tile(hX + 2 * mX, hY + 9 * mY, Game
					.getPlane()));
			o3 = SceneEntities.getAt(new Tile(hX + 2 * mX, hY + 5 * mY, Game
					.getPlane()));
			o4 = SceneEntities.getAt(new Tile(hX + 5 * mX, hY + 3 * mY, Game
					.getPlane()));
			o5 = SceneEntities.getAt(new Tile(hX + 1 * mX, hY + 3 * mY, Game
					.getPlane()));
		}

		if (o1 != null && o1.getType() == 0x1) {
			centerPath = new int[] { 14, 29, 27, 26, 16, 7, 15 };
			edgePath = new int[] { 15, 7, 16, 26, 27 };
		} else if (o2 != null && o2.getType() == 0x1) {
			centerPath = new int[] { 14, 26, 16, 8, 4, 10, 20, 7, 13 };
			edgePath = new int[] { 15, 7, 20, 10, 1 };
		} else if (o3 != null && o3.getType() == 0x1) {
			centerPath = new int[] { 14, 24, 29, 26, 10, 4, 8, 7, 15 };
			edgePath = new int[] { 13, 7, 8, 4, 1 };
		} else if (o4 != null && o4.getType() == 0x1) {
			centerPath = new int[] { 14, 1, 11, 27, 29, 26, 16, 22, 13 };
			edgePath = new int[] { 15, 22, 16, 26, 29 };
		} else if (o5 != null && o5.getType() == 0x1) {
			centerPath = new int[] { 14, 29, 27, 4, 8, 22, 15 };
			edgePath = new int[] { 15, 22, 8, 4, 1 };
		}

		while (isValid()) {
			if (Calculations.distanceTo(sTile) < 4) {
				currentPath = centerPath;
			} else if (Calculations.distanceTo(cTile) < 3) {
				currentPath = edgePath;
			}else{
				LogHandler.Print(Calculations.distanceTo(cTile) + "   " +Calculations.distanceTo(sTile) );
			}
			Path = getPath(currentPath, barriers);
			Solve = Path[0];
			if (lever != null && !MAZE_ACTIVE) {
				if (MyObjects.isOnScreen(lever)) {
					lever.interact("Pull");
					ShadowDungeon.SleepTillStop();
				} else {
					MyPlayer.WalkTo(lever.getLocation());
				}
			} else if (chest != null && Calc.Reach(chest)) {
				if (MyObjects.isOnScreen(chest)) {
					chest.interact("Open");
					ShadowDungeon.SleepTillStop();
				} else {
					Camera.turnTo(chest);
				}
			} else {
				Path = getPath(currentPath, barriers);
				Solve = Path[0];
				SceneObject n = getBarrier(Path);
				if (currRoom.isOnScreen(n.getCentralPoint())) {
					ShadowDungeon.SleepWhile(MyPlayer.isMoving());
					ShadowDungeon.SleepWhile(MyPlayer.isMoving());
					ShadowDungeon.SleepWhile(MyPlayer.isMoving());
					ShadowDungeon.SleepTillStop();
					
					if (n.interact("Go-through")) {
						ShadowDungeon.SleepWhile(MyPlayer.isMoving());
						ShadowDungeon.SleepWhile(MyPlayer.isMoving());
						ShadowDungeon.SleepWhile(MyPlayer.isMoving());
						PASSED.add(n.getLocation());
					}
				} else {
					Camera.turnTo(n);
				}
			}
			if(ShadowDungeon.Message.contains("The switch")){
				MAZE_ACTIVE = true;
				ShadowDungeon.Message = null;
			}
		}

	}


	private static SceneObject getBarrier(Tile[] i) {
		Tile solve = i[0];
		Solve = solve;
		System.out.println(PASSED.contains(solve));
		return SceneEntities.getAt(solve);
	}

	private static Tile getNearstTile(Tile[] i) {
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		Dist.clear();
		for (Tile e : i) {
			if (e != null) {
				int d = (int) Calculations.distanceTo(e);
				Dist.add(d);
			}
		}
		if(Dist.isEmpty()){
			return null;
		}
		int index = Dist.indexOf(Collections.min(Dist));
		return i[index];
	}

	private static Tile[] getPath(int[] currentPath, Tile[] barriers) {
		ArrayList<Tile> d = new ArrayList<Tile>();
		d.clear();
		for (int e : currentPath) {
             if(!PASSED.contains(barriers[e - 1]) ){
				d.add(barriers[e - 1]);
             }
		}
		return d.toArray(new Tile[d.size()]);
	}

}

package megascripts.dungeon.puzzle;

import java.util.ArrayList;


import megascripts.api.Calc;
import megascripts.api.Flood;
import megascripts.api.currRoom;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class SlidingPuzzle extends Puzzle{

	public static final int[] TILE_1 = { 12125, 12133, 12141, 12149 },
			TILE_2 = { 12126, 12134, 12142, 12150 }, TILE_3 = { 12127, 12135,
					12143, 12151 };
	public static final int[] TILE_4 = { 12128, 12136, 12144, 12152 },
			TILE_5 = { 12129, 12137, 12145, 12153 }, TILE_6 = { 12130, 12138,
					12146, 12154 };
	public static final int[] TILE_7 = { 12131, 12139, 12147, 12155 },
			TILE_8 = { 12132, 12140, 12148, 12156 }, TILE_9 = null;
	public static final int[][] ID_ROW_1 = { TILE_1, TILE_2, TILE_3 };
	public static final int[][] ID_ROW_2 = { TILE_4, TILE_5, TILE_6 };
	public static final int[][] ID_ROW_3 = { TILE_7, TILE_8, TILE_9 };
	public static final int[][][] ID_SPOTS = { ID_ROW_1, ID_ROW_2, ID_ROW_3 };
	public static final int[] BACKGROUNDS = { 54321, 54322, 54323 };

	public static int[] All_Tile = { 12125, 12133, 12141, 12149, 12126, 12134,
			12142, 12150, 12127, 12135, 12143, 12151, 12128, 12136, 12144,
			12152, 12129, 12137, 12145, 12153, 12130, 12138, 12146, 12154,
			12131, 12139, 12147, 12155, 12132, 12140, 12148, 12156 };

	public static boolean Solved = false;
	public static ArrayList<Tile> Full_Area = new ArrayList<Tile>();
	public static ArrayList<Tile> UnFull_Area = new ArrayList<Tile>();
	public static ArrayList<Tile> EMPTY_AREA = new ArrayList<Tile>();

	@Override
	public String getName() {
		return "Siliding Puzzle";
	}
	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}
	@Override
	public String getAuthor() {
		return "Magorium";
	}
	@Override
	public boolean isValid() {
		if (Solved) {
			return false;
		}
		SceneObject n = SceneEntities.getNearest(BACKGROUNDS);
		if (n == null) {
			return false;
		}
		return ulits.tileinroom(n.getLocation());
	}
	@Override
	public boolean isSolved() {
		return !isValid();
	}
	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		EMPTY_AREA.clear();
		UnFull_Area.clear();
		Full_Area.clear();
		for (SceneObject d : SceneEntities.getLoaded(BACKGROUNDS)) {
			Full_Area.add(d.getLocation());
		}

		for (NPC d : NPCs.getLoaded(All_Tile)) {
			int dx = d.getLocation().getX();
			int dy = d.getLocation().getY();
			UnFull_Area.add(d.getLocation());
			UnFull_Area.add(new Tile(dx, dy - 1, 0));
			UnFull_Area.add(new Tile(dx - 1, dy, 0));
			UnFull_Area.add(new Tile(dx - 1, dy - 1, 0));
		}
		for (Tile z : Full_Area) {
			if (!UnFull_Area.contains(z)) {
				EMPTY_AREA.add(z);
			}
		}
		// Sliding Part
		for (int i = 0; i < ID_SPOTS.length; i++) {
			for (int j = 0; j < ID_SPOTS[i].length; j++) {

				NPC t1 = NPCs.getNearest(ID_SPOTS[i][j]);
				int dx = t1.getLocation().getX();
				int dy = t1.getLocation().getY();
				Tile[] Tiles = new Tile[] { t1.getLocation(),
						new Tile(dx, dy - 1, 0), new Tile(dx - 1, dy, 0),
						new Tile(dx - 1, dy - 1, 0) };
				for (Tile t2 : Tiles) {
					for (Tile t3 : EMPTY_AREA) {
						if (Calculations.distance(t2, t3) == 1) {
							if (!currRoom.isOnScreen(t1.getCentralPoint())) {
								MyPlayer.WalkTo(t1.getLocation());
							}
							t1.interact("Move");
							Task.sleep(1000, 1500);
							for (int sl = 0; sl < 40 && t1.isMoving(); sl++, Task
									.sleep(100, 150))
								;
						}
					}
				}
			}
		}

	}

}

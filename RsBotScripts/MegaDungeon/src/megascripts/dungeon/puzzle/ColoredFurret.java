package megascripts.dungeon.puzzle;

import static org.powerbot.game.api.wrappers.Tile.Flag.BLOCKED;
import static org.powerbot.game.api.wrappers.Tile.Flag.WALL_EAST;
import static org.powerbot.game.api.wrappers.Tile.Flag.WALL_NORTH;
import static org.powerbot.game.api.wrappers.Tile.Flag.WALL_NORTHEAST;
import static org.powerbot.game.api.wrappers.Tile.Flag.WALL_NORTHWEST;
import static org.powerbot.game.api.wrappers.Tile.Flag.WALL_SOUTH;
import static org.powerbot.game.api.wrappers.Tile.Flag.WALL_SOUTHEAST;
import static org.powerbot.game.api.wrappers.Tile.Flag.WALL_SOUTHWEST;
import static org.powerbot.game.api.wrappers.Tile.Flag.WALL_WEST;

import java.awt.Point;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


import megascripts.api.PathFinder;
import megascripts.api.currRoom;
import megascripts.api.PathFinder.Vertex;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.map.Path.TraversalOption;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class ColoredFurret extends Puzzle {

	public static int PRESURE[] = { 54380, 54372 };
	public static Tile Room;
	final static int[] BAD_PLATES = { 54365, 54367, 54369, 54371, 54373, 54375,
			54377, 54379, 54381, 54383, 54385, 54387, 54389, 54391, 54393,
			54395, 54397, 54399, 54401, 54403 };
	private final static int[] RED_TILE = { 54364, 54366, 54368, 54370 },
			BLUE_TILE = { 54372, 54374, 54376, 54378 }, GREEN_TILE = { 54380,
					54382, 54384, 54386 }, YELLOW_TILE = { 54388, 54390, 54392,
					54394 }, ORANGE_TILE = { 54396, 54398, 54400, 54402 };
	private final static int[][] COLORED_PLATES = { BLUE_TILE, RED_TILE,
			YELLOW_TILE, GREEN_TILE, ORANGE_TILE };
	private final static int[] COLORED_FERRETS = { 12167, 12165, 12171, 12169,
			12173 };
	public static Tile[] Path = null;

	@Override
	public String getName() {
		return "Colored Furrets";
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
		return MyObjects.There(COLORED_PLATES[1])
				|| MyObjects.There(COLORED_PLATES[0])
				|| MyObjects.There(COLORED_PLATES[2])
				|| MyObjects.There(COLORED_PLATES[3])
				|| MyObjects.There(COLORED_PLATES[4]);
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		int index = getIndex();
		if (index == -1) {
			
			Path = null;
			Room = null;
		} else {
			NPC Ferrets = NPCs.getNearest(COLORED_FERRETS[index]);
			SceneObject Tile = SceneEntities.getNearest(COLORED_PLATES[index]);
			if (Ferrets == null || Tile == null) {
		
			}
			for (int e = 0; e < 100 && Ferrets.isMoving(); e++)
				;
			Path = PathFinder.findPath(Ferrets.getLocation(),
					Tile.getLocation());
			Tile next = Path[0];
			Tile scareTile = getScareTile(next, Tile.getLocation());
			Room = scareTile;
			if (currRoom.MatchTile(scareTile)) {
				Ferrets.interact("Scare");
				Task.sleep(1000, 1500);
				ShadowDungeon.SleepTillStop();
				ShadowDungeon.SleepWhile(Ferrets.isMoving());
				ShadowDungeon.SleepWhile(Ferrets.isMoving());
				ShadowDungeon.SleepWhile(Ferrets.isMoving());
			} else {
				if (currRoom.isOnScreen(scareTile.getCentralPoint())) {
					scareTile.interact("Walk here");
					ShadowDungeon.SleepTillStop();
					ShadowDungeon.sleep(1000, 1500);
				} else {
					MyPlayer.WalkTo(scareTile);
				}
			}
		}
		if (isSolved()) {
			ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
			Path = null;
			Room = null;
		}
	}

	private static Tile getScareTile(Tile next, Tile Pad) {
		int tx = next.getX();
		int ty = next.getY();
		int px = Pad.getX();
		int py = Pad.getY();
		int x = 0;
		int y = 0;
		if (px > tx) {
			if (isGoodTile(new Tile(tx - 1, ty, 0))) {
				x = tx - 1;
			} else {
				x = tx;
			}
		} else {
			if (isGoodTile(new Tile(tx + 1, ty, 0))) {
				x = tx + 1;
			} else {
				x = tx;
			}
		}
		if (py > ty) {
			if (isGoodTile(new Tile(tx, ty - 1, 0))) {
				y = ty - 1;
			} else {
				y = ty;
			}
		} else {
			if (isGoodTile(new Tile(tx, ty + 1, 0))) {

				y = ty + 1;
			} else {
				y = ty;
			}
		}
		return new Tile(x, y, 0);
	}

	private static SceneObject getObject(Tile tile) {
		return SceneEntities.getAt(tile);
	}

	private static boolean isGoodTile(Tile e) {
		SceneObject r = getObject(e);
		if (r != null) {
			for (int x : BAD_PLATES) {
				if (r.getId() == x) {
					return true;
				}
			}
		}
		return r == null;
	}

	private static int getIndex() {
		for (int x = 0; x < COLORED_FERRETS.length; x++) {
			NPC Ferrets = NPCs.getNearest(COLORED_FERRETS[x]);
			SceneObject Tile = SceneEntities.getNearest(COLORED_PLATES[x]);
			if (Tile != null || Ferrets != null) {
				if (!currRoom.MatchTile(Ferrets.getLocation(),
						Tile.getLocation())) {
					return x;
				}
			}
		}
		return -1;
	}


}

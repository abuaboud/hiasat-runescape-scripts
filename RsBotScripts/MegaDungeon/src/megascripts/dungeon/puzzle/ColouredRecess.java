package megascripts.dungeon.puzzle;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;


import megascripts.api.Calc;
import megascripts.api.currRoom;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.LocalPath;
import org.powerbot.game.api.wrappers.map.LocalPath.Vertex;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class ColouredRecess extends Puzzle {

	public static final int[] BLUE_TILES = { 54504, 54546, 54623, 56060 },
			GREEN_TILES = { 54506, 54548, 54625, 56062 }, YELLOW_TILES = {
					54508, 54550, 54627, 56064 }, VIOLET_TILES = { 54510,
					54552, 54629, 56066 };
	public static final int[][] COLOR_TILES = { BLUE_TILES, GREEN_TILES,
			YELLOW_TILES, VIOLET_TILES };
	public static int[] Shalves = { 35246, 35245, 35242, 35241 };

	public static Tile[] tile = null;
	public static Tile NpcTile = null;
	public static Tile[] colortile = null;

	public static int BLUE_COLOR = 19869, GREEN_COLOR = 19871,
			YELLOW_COLOR = 19873, VIOLET_COLOR = 19875;

	@Override
	public String getName() {
		return "Coloured Recess";
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
		NPC n = NPCs.getNearest("Block");
		if (n == null) {
			return false;
		}
		return Calc.Reach(n);
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		if (getCurrentIndex() != 100) {
			NPC bl = NPCs.getNearest("Block");
			int[] CurColor = COLOR_TILES[getCurrentIndex()];
			NPC n = getNearstNPC(CurColor, bl.getId());
			NpcTile = n.getLocation();
			SceneObject e = SceneEntities.getNearest(CurColor);
			tile = megascripts.api.PathFinder.findPath(n.getLocation(),
					e.getLocation());
			Tile curtile = tile[1];
			if (MatchTile(curtile)) {
				n.interact("Pull");
				Task.sleep(2000, 2500);
				ShadowDungeon.SleepTillStop();
			} else {
				if (isOnScreen(curtile.getCentralPoint())) {
					curtile.interact("Walk here");
				} else {
					Walking.walk(curtile);
				}
				Task.sleep(2000, 2500);
				ShadowDungeon.SleepTillStop();
			}
		} else {
			int BottleID = getBottleColor();
			int[] CurrColor = getTileObject();
			if (MyItems.contains(BottleID)) {
				SceneObject COLOR_Tile = SceneEntities.getNearest(CurrColor);
				if (COLOR_Tile != null) {
					if (MyObjects.isOnScreen(COLOR_Tile)) {
						MyItems.Interact(BottleID, "Use");
						if (COLOR_Tile.interact("Use")) {
							ShadowDungeon.SleepTillStop();
							if (BottleID == VIOLET_COLOR) {
								Task.sleep(2400, 3000);
								ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());

							}
						}
					} else {
						Walking.walk(COLOR_Tile);
						Camera.turnTo(COLOR_Tile);
					}
				}
			} else {
				int o = getCurrentWidget(BottleID);
				grabBottle(o);
			}
		}
	}

	public static NPC getNearstNPC(int[] o, int npc) {
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		ArrayList<NPC> objects = new ArrayList<NPC>();
		Dist.clear();
		objects.clear();
		SceneObject obj = SceneEntities.getNearest(o);
		if (obj == null) {
			return null;
		}
		for (NPC x : NPCs.getLoaded(npc)) {
			if (x != null && Calc.Reach(x) && NotCompleted(x)) {
				int e = (int) Calculations.distance(x, obj);
				Dist.add(e);
				objects.add(x);
			}
		}
		if (Dist.isEmpty()) {
			return null;
		}
		int m = Collections.min(Dist);
		int re = Dist.indexOf(m);
		if (objects.get(re) == null) {
			return null;
		}
		return objects.get(re);
	}

	private static boolean NotCompleted(NPC x) {
		Tile[] tiles = getTile();
		colortile = tiles;
		for (Tile e : tiles) {
			if (e != null && x != null) {
				if (currRoom.MatchTile(x.getLocation(), e)) {
					return false;
				}
			}
		}
		return true;
	}

	private static Tile[] getTile() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		for (SceneObject n : SceneEntities.getLoaded(ulits
				.convertInt(COLOR_TILES))) {
			if (n != null) {
				tiles.add(n.getLocation());
			}
		}
		return tiles.toArray(new Tile[tiles.size()]);
	}

	private static void grabBottle(int o) {
		SceneObject n = SceneEntities.getNearest(Shalves);
		WidgetChild Option = Widgets.get(1188, o);
		if (n != null) {
			if (MyObjects.isOnScreen(n)) {
				if (Option.validate()) {
					Option.click(true);
					Task.sleep(1000, 1500);
				} else {
					n.interact("Take-Bottle");
					Task.sleep(1000, 1500);
					ShadowDungeon.SleepTillStop();
				}
			} else {
				if (!Players.getLocal().isMoving()) {
					Walking.walk(n);
					Camera.turnTo(n);
					ShadowDungeon.SleepTillStop();
				}
			}
		}
	}

	private static boolean ThereNpc(SceneObject b) {
		for (NPC n : NPCs.getLoaded()) {
			if (n != null && Calc.Reach(n)
					&& MatchTile(b.getLocation(), n.getLocation())) {
				return true;
			}
		}
		return false;
	}

	private static int getCurrentWidget(int bi) {
		if (bi == BLUE_COLOR) {
			return 3;
		} else if (bi == GREEN_COLOR) {
			return 13;
		} else if (bi == YELLOW_COLOR) {
			return 14;
		} else if (bi == VIOLET_COLOR) {
			return 15;
		} else {
			return 16;

		}
	}

	private static int getBottleColor() {
		NPC bl = NPCs.getNearest("Block");
		int Block = bl.getId();
		int ID = getObject(NPCs.getNearest(Block).getLocation()).getId();
		if (MatchId(ID, COLOR_TILES[0])) {
			return BLUE_COLOR;
		} else if (MatchId(ID, COLOR_TILES[1])) {
			return GREEN_COLOR;
		} else if (MatchId(ID, COLOR_TILES[2])) {
			return YELLOW_COLOR;
		} else if (MatchId(ID, COLOR_TILES[3])) {
			return VIOLET_COLOR;
		}
		return 0;
	}

	private static int[] getTileObject() {
		NPC bl = NPCs.getNearest("Block");
		int Block = bl.getId();
		int ID = getObject(NPCs.getNearest(Block).getLocation()).getId();
		if (MatchId(ID, COLOR_TILES[0])) {
			return COLOR_TILES[0];
		} else if (MatchId(ID, COLOR_TILES[1])) {
			return COLOR_TILES[1];
		} else if (MatchId(ID, COLOR_TILES[2])) {
			return COLOR_TILES[2];
		} else if (MatchId(ID, COLOR_TILES[3])) {
			return COLOR_TILES[3];
		}
		return null;
	}

	private static boolean MatchId(int iD, int[] is) {
		for (int r : is) {
			if (r == iD) {
				return true;
			}
		}
		return false;
	}

	public static SceneObject getObject(Tile tile) {
		return SceneEntities.getAt(tile);
	}

	public static boolean isOnScreen(Point p) {
		Rectangle ScreenArea = new Rectangle(0, 47, 519, 284);
		return ScreenArea.contains(p);
	}

	private static int getCurrentIndex() {
		NPC bl = NPCs.getNearest("Block");
		int Block = bl.getId();
		for (int x = 0; x < COLOR_TILES.length; x++) {
			SceneObject n = SceneEntities.getNearest(COLOR_TILES[x]);
			NPC e = MyObjects.getNearstNPC(COLOR_TILES[x], Block);
			NpcTile = e.getLocation();
			boolean r = MatchTile(e.getLocation(), n.getLocation());
			if (!r && !ThereNpc(n)) {
				return x;
			}
		}
		return 100;
	}

	public static boolean MatchTile(Tile r) {
		int rx = r.getX();
		int ry = r.getY();
		int px = Players.getLocal().getLocation().getX();
		int py = Players.getLocal().getLocation().getY();
		return px == rx && ry == py;
	}

	public static boolean MatchTile(Tile r, Tile p) {
		int rx = r.getX();
		int ry = r.getY();
		int px = p.getX();
		int py = p.getY();
		return px == rx && ry == py;
	}

}

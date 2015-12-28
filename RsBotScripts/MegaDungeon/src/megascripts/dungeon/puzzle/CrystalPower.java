package megascripts.dungeon.puzzle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import megascripts.api.Calc;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Dungeon_Doors;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.LogHandler;

public class CrystalPower extends Puzzle {

	public static int OFF_CRYSTAL[] = { 49491, 34852, 34863, 49471, 49472,
			49473, 49490, 49481, 49489, 49480, 49500, 49482 , 54263, 54269, 54266};
	public static int BIG_CRYSTAL[] = { 34866, 35070, 49510, 49507, 49508,
			49511, 49509, 49512 ,54276,54275};

	public static int PAD[] = { 35232, 52206 , 54282};
	public static int Pad;
	public static int OFF_TILE[] = { 49466, 49465, 34317 ,54261};
	public static int[] RED = { 49479, 49478, 49477, 34848,54265 }, YELLOW = { 49497,
			49496, 49495, 34862 ,54271}, GREEN = { 49488, 49487, 49486, 34856 ,54268},
			BLUE = { 34319, 49470, 49469, 49468 ,54262};
	public static int[][] LIGHTS = { RED, YELLOW, GREEN, BLUE };
	public static int ri = 0;
	public static int gi = 0;
	public static int yi = 0;
	public static int bi = 0;
	public static ArrayList<Integer> Am = new ArrayList<Integer>();

	public static boolean RED_LIGHT = false, GREEN_LIGHT = false,
			YELLOW_LIGHT = false, BLUE_LIGHT = false;

	@Override
	public String getName() {
		return "Crystal Power";
	}

	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}

	@Override
	public boolean isValid() {
		SceneObject crystal = SceneEntities.getNearest(BIG_CRYSTAL);
		if (crystal == null) {
			return false;
		}
		return Calc.Reach(crystal);
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	public static int getMostRepeated(ArrayList<Integer> array) {
		int max = 0;
		int last = 0;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < array.size(); i++) {
			if (map.containsKey(i)) {
				int count = map.get(i) + 1;
				map.remove(i);
				map.put(i, count);
				if (count > last) {
					max = i;
					last = count;
				}
				continue;
			}
			map.put(i, 1);
		}
		return array.get(max);
	}

	@Override
	public void solve() {
		Pad = SceneEntities.getNearest(PAD).getId();
		ShadowDungeon.setStatus(getStatus());
		if (ThereUnLight()) {
			Light();
		} else {
			ArrayList<Integer> ColorIndex = new ArrayList<Integer>();
			ColorIndex.clear();
			getNumber();
			ColorIndex.add(ri);
			ColorIndex.add(yi);
			ColorIndex.add(gi);
			ColorIndex.add(bi);
			int ms = getMostRepeated(ColorIndex);
			if (ri != ms) {
				RED_LIGHT = true;
			} else {
				RED_LIGHT = false;
			}
			if (gi != ms) {
				GREEN_LIGHT = true;
			} else {
				GREEN_LIGHT = false;
			}
			if (yi != ms) {
				YELLOW_LIGHT = true;
			} else {
				YELLOW_LIGHT = false;
			}
			if (bi != ms) {
				BLUE_LIGHT = true;
			} else {
				BLUE_LIGHT = false;
			}
			if (!Players.getLocal().isMoving()) {
				if (RED_LIGHT) {
					if (!PlayerOn(getNearstObject(Pad, RED))) {
						LogHandler.Print("Pausing red Light", Color.red);
						pause(Pad, RED);
					} else if (ri == currentmsNumber(ms + 2)) {
						LogHandler.Print("Resuming Red Light", Color.red);
						unpause(Pad, RED);
					}
				} else if (GREEN_LIGHT) {
					if (!PlayerOn(getNearstObject(Pad, GREEN))) {
						LogHandler.Print("Pausing Green Light", Color.green);
						pause(Pad, GREEN);
					} else if (gi == currentmsNumber(ms + 2)) {
						LogHandler.Print("Resuming Green Light", Color.green);
						unpause(Pad, GREEN);
					}
				} else if (BLUE_LIGHT) {
					ShadowDungeon.log("Blue Light");
					if (!PlayerOn(getNearstObject(Pad, BLUE))) {
						LogHandler.Print("Pausing Blue Light", Color.BLUE);
						pause(Pad, BLUE);

					} else if (bi == currentmsNumber(ms + 2)) {
						LogHandler.Print("Resuming Blue Light", Color.BLUE);
						unpause(Pad, BLUE);
					}
				} else if (YELLOW_LIGHT) {
					if (!PlayerOn(getNearstObject(Pad, YELLOW))) {
						LogHandler.Print("Pausing Yellow Light", Color.yellow);
						pause(Pad, YELLOW);
					} else if (yi == currentmsNumber(ms + 2)) {
						LogHandler.Print("Resuming Yellow Light", Color.yellow);
						unpause(Pad, YELLOW);
					}
				}
			}
			if (MyObjects.getCount(ulits.convertInt(LIGHTS)) == 0) {
				ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
			}
		}
	}

	private static int currentmsNumber(int s) {
		if (s == 5) {
			return 0;
		} else if (s == 6) {
			return 1;
		} else if (s == -1) {
			return 4;
		} else if (s == -2) {
			return 3;
		}
		return s;
	}

	private static boolean PlayerOn(SceneObject nearstObject) {
		Tile play = Players.getLocal().getLocation();
		Tile loc = nearstObject.getLocation();
		int px = play.getX();
		int py = play.getY();
		int lx = loc.getX();
		int ly = loc.getY();
		return (lx == px) && (ly == py);
	}

	private static void getNumber() {
		ri = getLightIndex(Pad, RED);
		gi = getLightIndex(Pad, GREEN);
		yi = getLightIndex(Pad, YELLOW);
		bi = getLightIndex(Pad, BLUE);

	}

	private static void unpause(int pad, int[] red) {
		SceneObject PAD = getNearstObject(pad, red);
		if (PAD != null) {
			Tile d = new Tile(PAD.getLocation().getX() - 1, PAD.getLocation()
					.getY(), 0);
			d.click(true);
			Task.sleep(900, 1600);
		}
	}

	private static void pause(int pad, int[] red) {
		SceneObject PAD = getNearstObject(pad, red);
		if (PAD != null) {
			if (MyObjects.isOnScreen(PAD)) {
				if (!Players.getLocal().isMoving()
						&& !Players.getLocal().getLocation()
								.equals(PAD.getLocation())) {
					PAD.getLocation().click(true);
				}
			} else {
				Walking.walk(PAD);
			}
		}
	}

	private static SceneObject getNearstObject(int b, int[] red2) {
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		ArrayList<SceneObject> objects = new ArrayList<SceneObject>();
		Dist.clear();
		objects.clear();
		SceneObject object = SceneEntities.getNearest(red2);
		for (SceneObject x : SceneEntities.getLoaded(b)) {
			if (x != null && Calc.Reach(x)) {
				int e = (int) Calculations.distance(x, object);
				Dist.add(e);
				objects.add(x);
			}
		}
		int m = Collections.min(Dist);
		int re = Dist.indexOf(m);
		if (objects.get(re) == null) {
			return null;
		}
		return objects.get(re);
	}

	private static int getLightIndex(int b, int[] sec) {
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		ArrayList<SceneObject> objects = new ArrayList<SceneObject>();
		Dist.clear();
		objects.clear();
		SceneObject object = SceneEntities.getNearest(sec);
		for (SceneObject x : SceneEntities.getLoaded(b)) {
			if (object == null) {
				return 0;
			}
			if (x != null && object != null && Calc.Reach(x)) {
				int e = (int) Calculations.distance(x, object);
				Dist.add(e);
				objects.add(x);
			}
		}
		if (Dist.isEmpty()) {
			return -1;
		}
		int m = Collections.min(Dist);
		int re = Dist.indexOf(m);
		if (objects.get(re) == null) {
			return 0;
		}
		return (int) Calculations.distance(objects.get(re), object);
	}

	private static void Light() {
		SceneObject n = SceneEntities.getNearest(OFF_CRYSTAL);
		if (n != null) {
			if (MyObjects.isOnScreen(n)) {
				n.interact("Power-up");
				Task.sleep(600, 900);
			} else {
				if (!Players.getLocal().isMoving()) {
					Walking.walk(n);
					Camera.turnTo(n);
				}
			}
		}
	}

	private static boolean ThereUnLight() {
		SceneObject n = SceneEntities.getNearest(OFF_CRYSTAL);
		return n != null && n.validate() && Calc.Reach(n);
	}

}
